(function() {
  var ENVIRONMENT = window.TEXT_VARIABLES.jekyll.environment;
  var SOURCES = window.TEXT_VARIABLES.sources;
  var TOC_SELECTOR = window.TEXT_VARIABLES.site.toc.selectors;
  var LEANCLOUD = window.TEXT_VARIABLES.site.leancloud;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window);
    var $pageStage = $('.js-page-stage');
    var $pageFooter = $('.js-page-footer');
    var $articleContent = $('.js-article-content');
    var $articleAside = $('.js-article-aside');
    var $toc = $('.js-toc');
    var $col2 = $('.js-col-2');
    var toc, affix;
    var hasToc = $articleContent.find(TOC_SELECTOR).length > 0;
    var tocDisabled = false;

    function disabled() {
      return $col2.css('display') === 'none' || !hasToc;
    }

    $window.on('resize', window.throttle(function() {
      tocDisabled = disabled();
      toc && toc.setOptions({
        disabled: tocDisabled
      });
      affix && affix.setOptions({
        disabled: tocDisabled
      });
    }, 100));

    if (hasToc) {
      !$pageStage.hasClass('has-toc') && $pageStage.addClass('has-toc');
    }
    tocDisabled = disabled();

    setTimeout(function() {
      toc = $toc.toc({
        selectors: TOC_SELECTOR,
        container: $articleContent,
        disabled: tocDisabled
      });
      affix = $articleAside.affix({
        offsetBottom: $pageFooter.outerHeight(),
        disabled: tocDisabled
      });
    }, 1000);
  });

  // page view
  if (
    LEANCLOUD.app_id &&
    LEANCLOUD.app_key &&
    LEANCLOUD.app_class &&
    '{{ page.key }}' &&
    ENVIRONMENT !== 'development' &&
    ENVIRONMENT !== 'beta'
  ) {
      window.Lazyload.js([SOURCES.jquery, SOURCES.leancloud_js_sdk], function() {
      /* global AV */
      var pageview = window.pageview(AV, {
        appId: LEANCLOUD.app_id,
        appKey: LEANCLOUD.app_key,
        appClass: LEANCLOUD.app_class
      });
      var key = '{{ page.key }}';
      var title = '{{ page.title }}';

      pageview.increase(key, title, function(view) {
        $('#post-key-{{ page.key }}').text(view);
      });
    });
  }
})();