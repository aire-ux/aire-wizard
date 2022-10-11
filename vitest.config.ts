import {defineConfig} from "vite";
import tsconfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
  test: {
    globals: true,
    environment: 'happy-dom',
    clearMocks: true,
    globalSetup:'src/test/typescript/setup.ts',
    include: [
      "./src/test/typescript/**/*.{test,spec}.ts"
    ],
  },
  plugins: [tsconfigPaths({
    projects: ["./tsconfig.json"],
  })]
});