import {Wizard} from "@aire-ux/aire-wizard/wizard";
import {WizardPage} from "@aire-ux/aire-wizard/wizard-page";

export default function setup() {


  window.customElements.define('aire-wizard', Wizard);
  window.customElements.define('aire-wizard-page', WizardPage);
  Object.defineProperty(window.location, 'href', {
    writable: true,
    value: 'https://localhost'
  });
  // (window as any).chai.use(chaiDomDiff);
}
setup();