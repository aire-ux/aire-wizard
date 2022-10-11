var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { css, html, LitElement } from "lit";
import { customElement } from 'lit/decorators/custom-element.js';
let WizardPage = class WizardPage extends LitElement {
    render() {
        return html `
      <section>
        <slot name="header"></slot>
        <slot name="content" part="content"></slot>
        <slot name="footer"></slot>
      </section>
    `;
    }
    connectedCallback() {
        console.log("DONE");
        super.connectedCallback();
    }
};
// language=CSS
WizardPage.styles = css `
    section {
      width: 100%;
      display: flex;
      flex-direction: column;
    }
    ::slotted(footer) {
      align-self: flex-end;
    }
  `;
WizardPage = __decorate([
    customElement('aire-wizard-page')
], WizardPage);
export { WizardPage };
// window.customElements.define('aire-wizard-page', WizardPage);
