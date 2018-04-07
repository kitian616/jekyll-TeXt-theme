/* global mermaid */
(function () {
  var SOURCES = window.TEXT_VARIABLES.sources;
  var PAGE = window.TEXT_VARIABLES.page;
  if (PAGE.mermaid) {
    window.Lazyload.js(SOURCES.mermaid, function() {
      mermaid.init(undefined, '.language-mermaid');
      mermaid.initialize({ startOnLoad: true });
    });
  }
})();