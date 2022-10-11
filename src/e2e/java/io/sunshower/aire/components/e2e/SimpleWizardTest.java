package io.sunshower.aire.components.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.sunshower.aire.wizard.WizardDemoApplication;
import javax.inject.Inject;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

//@Testcontainers
@SpringBootTest(classes = WizardDemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SimpleWizardTest {


  @Container
  private static final BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
      .withCapabilities(new ChromeOptions());


  @LocalServerPort
  private Integer port;
  private String address;
  private RemoteWebDriver driver;


  @BeforeEach
  void setUp(@Autowired ServerProperties properties) {
    chrome.start();
    chrome.withExposedPorts(port);
    org.testcontainers.Testcontainers.exposeHostPorts(port);
  }

  @AfterEach
  void tearDown() {
    chrome.stop();
  }


  @Test
  void ensureNavigatingToPageWorks() {
  }


}
