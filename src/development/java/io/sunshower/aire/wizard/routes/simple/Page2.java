package io.sunshower.aire.wizard.routes.simple;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.component.icon.VaadinIcon;
import io.sunshower.aire.components.AbstractWizardPage;
import io.sunshower.aire.components.WizardPage;
import io.sunshower.aire.wizard.routes.simple.Page2.TIconProvider;

@WizardPage(key = "page-2", title = "world", iconFactory = TIconProvider.class)
public class Page2 extends AbstractWizardPage<String, Object> {

  public Page2() {
    getElement().getClassList().add("page-2");
  }

  public static class TIconProvider implements IconFactory {

    @Override
    public Icon create() {
      return VaadinIcon.BUG.create();
    }
  }
}
