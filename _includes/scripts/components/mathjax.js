(function () {
  var SOURCES = window.TEXT_VARIABLES.sources;
  var PAGE = window.TEXT_VARIABLES.page;
  if (PAGE.mathjax) {
    var _config = { tex2jax: {
      inlineMath: [['$','$'], ['\\(','\\)']]
    }};
    if (PAGE.mathjax_autoNumber == true) {
      _config.TeX = { equationNumbers: { autoNumber: 'all' } };
    }
    window.MathJax = _config;
    window.Lazyload.js(SOURCES.mathjax);
  }
})();