package io.sunshower.aire.wizard.routes;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.sunshower.aire.components.Wizard;
import io.sunshower.aire.wizard.routes.simple.Page1;
import io.sunshower.aire.wizard.routes.simple.Page2;
import org.springframework.stereotype.Component;

@Component
@Route("wizard/simple")
public class SimpleWizardExample extends VerticalLayout {

  private final Wizard<String, String> wizard;

  public SimpleWizardExample() {

    wizard = new Wizard<>();
    wizard.addSteps(Page1.class, Page2.class);
    wizard.addTransition(Page1.class, Page2.class);
    wizard.setInitialStep(Page1.class);
    add(wizard);
  }
}
