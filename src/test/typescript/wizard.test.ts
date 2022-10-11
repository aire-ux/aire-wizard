import "./harness";

import {
  fixture,
  expect, nextFrame,
} from "@open-wc/testing";

// import {
//   expect
// } from "chai";
//
import {
  beforeEach,
  describe,
  it,
  vi
} from "vitest";
import {
  Wizard,
} from "@aire-ux/aire-wizard/wizard"

import {
  WizardPage,
} from "@aire-ux/aire-wizard/wizard-page"

import {html} from "lit";


describe('a wizard', async () => {
  let element: Wizard;
  beforeEach(async () => {
    element = await fixture(html`
      <aire-wizard>
        <aire-wizard-page slot="page">
          <h1 slot="header">Hello!</h1>
        </aire-wizard-page>
      </aire-wizard>`);

    await nextFrame();
  });

  it('should mount the component', () => {
    let elements = document.querySelectorAll('aire-wizard');
    expect(elements.length).to.equal(1);
  });

  it('should mount the child', () => {
    let elements = document.querySelectorAll('h1[slot=header]');
    expect(elements.length).to.eq(1);
    let h1 = elements[0];
    expect(h1.innerHTML).to.eq("Hello!");
  });

  it('should mount in the DOM correctly', () => {
    let elements = document.querySelectorAll('h1[slot=header]');
    expect(elements.length).to.eq(1);
    let h1 = elements[0];
    expect(h1.innerHTML).to.eq("Hello!");
  });

  it('should mount in the shadow dom correctly', () => {
    expect(element).shadowDom.to.equal(`
      <article>
        <slot name="header" part="header"></slot>
        <slot name="page"></slot>
      </article>
    `);

  });
});