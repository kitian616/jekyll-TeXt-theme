(function() {
  var ENVIRONMENT = window.TEXT_VARIABLES.jekyll.environment;
  var SOURCES = window.TEXT_VARIABLES.sources;
  var LEANCLOUD = window.TEXT_VARIABLES.site.leancloud;
  if (
    LEANCLOUD.app_id &&
    LEANCLOUD.app_key &&
    LEANCLOUD.app_class &&
    ENVIRONMENT !== 'development'){
    window.Lazyload.js([SOURCES.jquery, SOURCES.leancloud_js_sdk], function() {
      var AV = window.AV;
      var pageview = window.pageview(AV, {
        appId: LEANCLOUD.app_id,
        appKey: LEANCLOUD.app_key,
        appClass: LEANCLOUD.app_class
      });
      $('.article-view').each(function() {
        var $this = $(this);
        var key = this.id.substr(9);
        pageview.get(key, function(view) {
          $this.text(view);
        });
      });
    });
  }
})();