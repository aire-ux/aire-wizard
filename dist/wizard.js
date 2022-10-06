var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { css, customElement, html, LitElement } from "lit-element";
let Wizard = class Wizard extends LitElement {
    render() {
        return html `
      <article>
        <slot name="header" part="header"></slot>
        <slot name="page"></slot>
      </article>
    `;
    }
};
// language=CSS
Wizard.styles = css `
    :host {
      width: 100%;
      height: 100%;
      display: block;
    }
    article {
      display: flex;
      flex-direction: column;
      width: 100%;
      height: 100%;
    }
    
    
  
  `;
Wizard = __decorate([
    customElement('aire-wizard')
], Wizard);
export { Wizard };
