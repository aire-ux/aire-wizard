package io.sunshower.aire.wizard.routes;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Component
@Route("wizard/simple")
public class SimpleWizardExample extends VerticalLayout {

  public SimpleWizardExample() {
    add(new Button("Hello"));
  }
}
