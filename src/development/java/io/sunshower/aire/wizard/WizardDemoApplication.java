package io.sunshower.aire.wizard;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import io.sunshower.aire.wizard.routes.SimpleWizardExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@PWA(name = "Aire Wizard Demo", shortName = "Wizard Demo")
@ComponentScan(basePackageClasses = SimpleWizardExample.class)
public class WizardDemoApplication extends VerticalLayout implements AppShellConfigurator {

  public static void main(String[] args) {
    SpringApplication.run(WizardDemoApplication.class, args);
  }


}
