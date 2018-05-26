(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  var TOC_SELECTOR = window.TEXT_VARIABLES.site.toc.selectors;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window);
    var $pageMain = $('.js-page-main');
    var $pageFooter = $('.js-page-footer');
    var $articleContent = $('.js-article-content');
    var $articleAside = $('.js-article-aside');
    var $tocRoot = $('.js-toc-root');
    var $col2 = $('.js-col-2');
    var toc, affix;
    var hasSidebar = $('.js-page-root').hasClass('layout--page--sidebar');
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
      !$pageMain.hasClass('has-toc') && $pageMain.addClass('has-toc');
    }
    tocDisabled = disabled();

    setTimeout(function() {
      toc = $tocRoot.toc({
        selectors: TOC_SELECTOR,
        container: $articleContent,
        scrollTarget: hasSidebar ? '.js-page-main' : null,
        scroller: hasSidebar ? '.js-page-main' : null,
        disabled: tocDisabled
      });
      affix = $articleAside.affix({
        offsetBottom: $pageFooter.outerHeight(),
        scrollTarget: hasSidebar ? '.js-page-main' : null,
        scroller: hasSidebar ? '.js-page-main' : null,
        scroll: hasSidebar ? $('.js-page-main').children() : null,
        disabled: tocDisabled
      });
    }, 1000);
  });
})();