package io.sunshower.aire.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.aire.ux.test.AireTest;
import com.aire.ux.test.Context;
import com.aire.ux.test.Navigate;
import com.aire.ux.test.RouteLocation;
import com.aire.ux.test.Select;
import com.aire.ux.test.TestContext;
import com.aire.ux.test.ViewTest;
import com.aire.ux.test.spring.EnableSpring;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import io.sunshower.aire.wizard.WizardDemoApplication;
import io.sunshower.aire.wizard.routes.SimpleWizardExample;
import lombok.val;
import org.springframework.boot.test.context.SpringBootTest;

@AireTest
@EnableSpring
@RouteLocation(scanPackage = "io.sunshower.aire.wizard.routes")
@SpringBootTest(classes = WizardDemoApplication.class)
class WizardTest {

  @ViewTest
  @Navigate("wizard/simple")
  void ensureWizardHostIsInjectable(@Select SimpleWizardExample ex) {
    assertNotNull(ex);
  }


  @ViewTest
  @Navigate("wizard/simple")
  void ensureNavigatingToNextPageWorks(@Context TestContext $) {

    val button = $.selectFirst(
        "footer > vaadin-button[key=next]",
        Button.class).get();


    assertNotNull(button);
    button.click();

    assertTrue($.selectFirst(".page-2").isPresent());
    assertFalse($.selectFirst(".page-1").isPresent());
  }
}
