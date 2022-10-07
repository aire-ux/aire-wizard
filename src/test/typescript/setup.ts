import {GlobalRegistrator} from "@happy-dom/global-registrator";

export async function setup() {
  GlobalRegistrator.register();

}
export async function teardown() {
  GlobalRegistrator.unregister();
}
