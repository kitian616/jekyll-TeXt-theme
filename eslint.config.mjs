import js from "@eslint/js";
import globals from "globals";

export default [
  js.configs.recommended,
  {
    ignores: [
      "_includes/scripts/lib/gallery.js",
      "_includes/scripts/components/lightbox.js",
      "_includes/search-providers/**",
    ],
  },
  {
    languageOptions: {
      globals: {
        ...globals.browser,
        ...globals.jquery,
        ...globals.node,
      },
    },
    rules: {
      "no-console": "error",
      semi: ["error", "always"],
      quotes: ["error", "single"],
      "comma-dangle": ["error", "never"],
      "block-scoped-var": "error",
      "default-case": "error",
      "no-extra-bind": "error",
      camelcase: "error",
      indent: ["error", 2, { SwitchCase: 1 }],
      "eol-last": ["error", "always"],
    },
  },
];
