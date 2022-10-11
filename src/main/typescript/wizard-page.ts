import {css, html, HTMLTemplateResult, LitElement} from "lit";


import {customElement} from 'lit/decorators/custom-element.js'
@customElement('aire-wizard-page')
export class WizardPage extends LitElement {



  // language=CSS
  static styles = css`
    section {
      width: 100%;
      display: flex;
      flex-direction: column;
    }
    ::slotted(footer) {
      align-self: flex-end;
    }
  `;
  render(): HTMLTemplateResult {
    return html`
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
}

// window.customElements.define('aire-wizard-page', WizardPage);