package io.sunshower.aire.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.SerializableTriConsumer;
import com.vaadin.flow.shared.Registration;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.sunshower.aire.components.common.Key;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;

/** base-class for commonly-themed wizard-pages */
@Tag("aire-wizard-page")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
// @JsModule("@aire-ux/aire-wizard/wizard-page")
// @CssImport("@aire-ux/aire-wizard/dist/styles/wizard-page.css")
@JsModule(Paths.WIZARD_PAGE_SOURCE)
@CssImport(Paths.WIZARD_PAGE_STYLES)
@NpmPackage(value = "@aire-ux/aire-wizard", version = Versions.AIRE_WIZARD_VERSION)
@SuppressFBWarnings
public class AbstractWizardPage<K, T> extends Component
    implements HasComponents, WizardModelSupport<K, T>, AutoCloseable {

  /** the wizard-key associated with this page */
  private final K key;

  /** the model-type associated with this page */
  private final Class<T> modelType;

  /** the header for this page. May be overridden by overriding {@code createHeader()} */
  @Getter(AccessLevel.PROTECTED)
  private final Component header;
  /** the footer for this page. May be overridden by overriding {@code createFooter()}. */
  @Getter(AccessLevel.PROTECTED)
  private final Component footer;

  /** the content for this page. May be overridden by overridding {@code createContent()} */
  @Getter(AccessLevel.PROTECTED)
  private final Component content;

  /** the list of controls. Defaults to a previous button and a next button */
  private final Map<Key, ControlDefinition<? super Component, K, T>> controls;
  /**
   * the wizard associated with this page. May be null depending on the component lifecycle. If you
   * need a non-null wizard at any point, use @Dynamic on your subclass's constructor and the
   * relevant constructor argument
   */
  private Wizard<K, T> wizard;

  private Registration nextRegistration;
  private Registration previousRegistration;

  protected AbstractWizardPage() {
    this.controls = new HashMap<>();
    val descriptor = read((Class<? extends AbstractWizardPage<K, T>>) getClass());

    this.key = (K) descriptor.key;
    this.modelType = getModelType(getClass());
    this.header = createHeader();
    this.content = createContent();
    this.footer = createFooter();
    configureNavigationMenu();
    setTitle(descriptor.title);
    if (header != null) {
      add(header);
    }
    if (content != null) {
      add(content);
    }
    if (footer != null) {
      add(footer);
    }

    //    add(header, content, footer);
  }

  protected AbstractWizardPage(@NonNull K key, @NonNull Class<T> modelType) {
    this.controls = new LinkedHashMap<>();
    this.key = key;
    this.modelType = modelType;
    this.header = createHeader();
    this.content = createContent();
    this.footer = createFooter();
    configureNavigationMenu();
    add(header, content, footer);
  }

  @SuppressWarnings("unchecked")
  protected AbstractWizardPage(Class<T> modelType) {
    this.controls = new LinkedHashMap<>();
    val descriptor = read((Class<? extends AbstractWizardPage<K, T>>) getClass());

    this.key = (K) descriptor.key;
    this.modelType = modelType;
    this.header = createHeader();
    this.content = createContent();
    this.footer = createFooter();
    configureNavigationMenu();
    setTitle(descriptor.title);
    if (header != null) {
      add(header);
    }
    if (content != null) {
      add(content);
    }
    if (footer != null) {
      add(footer);
    }
    //    add(header, content, footer);
  }

  @SuppressWarnings("unchecked")
  private Class<T> getModelType(Class<? extends AbstractWizardPage> type) {
    Class<?> c = type;
    for (; !AbstractWizardPage.class.equals(c.getSuperclass()); c = c.getSuperclass())
      ;

    val ptype = ((ParameterizedType) (c.getGenericSuperclass())).getActualTypeArguments();
    return (Class<T>) ptype[ptype.length - 1];
  }

  public void addContent(Component... contents) {
    if (content instanceof HasComponents) {
      ((HasComponents) content).add(contents);
    } else {
      Arrays.stream(contents).forEachOrdered(c -> content.getElement().appendChild(c.getElement()));
    }
  }

  @Override
  public boolean addModelElement(T value) {
    return false;
  }

  @Override
  public <V extends Component> boolean transitionTo(Class<V> type) {
    return wizard.transitionTo(type);
  }

  @Override
  public boolean transitionTo(K key) {
    return wizard.transitionTo(key);
  }

  public boolean advance() {
    try {
      if (wizard.canAdvance()) {
        wizard.advance();
        return true;
      }
      return false;
    } finally {
      updateNavigationControls(wizard);
    }
  }

  public boolean retreat() {
    try {
      if (wizard.canRetreat()) {
        wizard.retreat();
        return true;
      }
      return false;
    } finally {
      updateNavigationControls(wizard);
    }
  }

  @Override
  public void onEntered(Wizard<K, T> wizard) {
    this.wizard = wizard;
    updateNavigationControls(wizard);
  }

  @Override
  public void onExited(Wizard<K, T> wizard) {
    updateNavigationControls(wizard);
  }

  @SuppressWarnings("unchecked")
  protected <U extends Component> void addNavigationControl(
      U control, SerializableTriConsumer<U, WizardModelSupport<K, T>, Wizard<K, T>> f) {
    controls.put(
        getKey(control),
        new ControlDefinition<>(
            control,
            (SerializableTriConsumer<Component, WizardModelSupport<K, T>, Wizard<K, T>>) f));
    footer.getElement().appendChild(control.getElement());
  }

  protected <U extends Component> Key getKey(U component) {
    return Key.of(component.getElement().getAttribute("key"));
  }

  @SuppressWarnings("unchecked")
  protected <U extends Component> Optional<U> getControl(Key key) {
    return Optional.ofNullable(controls.get(key)).map(t -> (U) t.control);
  }

  protected Header createHeader() {
    val header = new Header();
    header.getElement().setAttribute("slot", "header");
    return header;
  }

  protected Component createContent() {
    val content = new VerticalLayout();
    content.getElement().setAttribute("slot", "content");
    return content;
  }

  protected Component createFooter() {
    val footer = new Footer();
    footer.getElement().setAttribute("slot", "footer");
    return footer;
  }

  protected void setTitle(String title) {
    setTitle(new Text(title));
  }

  protected void setTitle(Component title) {
    if (header != null) {
      header.getElement().appendChild(title.getElement());
    }
  }

  protected void setModelElement(T value) {
    wizard.setModel(value);
  }

  protected void configureNavigationMenu() {
    createPreviousButton();
    createNextButton();
  }

  private Button createNextButton() {
    val button = new Button("Next", VaadinIcon.ANGLE_RIGHT.create());
    button.getElement().setAttribute("key", "next");
    button.setIconAfterText(true);
    addNavigationControl(
        button,
        (control, modelSupport, wizard) -> {
          control.setEnabled(wizard.canAdvance());
        });
    nextRegistration = button.addClickListener(click -> advance());
    return button;
  }

  private Button createPreviousButton() {
    val button = new Button("Previous", VaadinIcon.ANGLE_LEFT.create());
    button.getElement().setAttribute("key", "previous");
    addNavigationControl(
        button,
        (control, modelSupport, wizard) -> {
          control.setEnabled(wizard.canRetreat());
        });
    previousRegistration = button.addClickListener(click -> retreat());
    return button;
  }

  private void updateNavigationControls(Wizard<K, T> wizard) {
    getUI()
        .orElse(UI.getCurrent())
        .access(
            () -> {
              for (val definition : controls.values()) {
                definition.f.accept((Component) definition.control, this, wizard);
              }
            });
  }

  protected K getKey() {
    return key;
  }

  @SuppressWarnings("unchecked")
  private WizardPageDescriptor read(Class<? extends AbstractWizardPage<K, T>> type) {
    val wizardPage = type.getAnnotation(WizardPage.class);
    if (wizardPage == null) {
      return new WizardPageDescriptor(type.getCanonicalName(), wizardPage.title());
    } else {
      return new WizardPageDescriptor(wizardPage.key(), wizardPage.title());
    }
  }

  @Override
  public void close() {
    nextRegistration.remove();
    previousRegistration.remove();
  }

  private static class WizardPageDescriptor {

    private final String key;
    private final String title;

    public WizardPageDescriptor(String key, String title) {
      this.key = key;
      this.title = title;
    }
  }

  record ControlDefinition<U extends Component, K, T>(
      U control, SerializableTriConsumer<U, WizardModelSupport<K, T>, Wizard<K, T>> f) {}
}
