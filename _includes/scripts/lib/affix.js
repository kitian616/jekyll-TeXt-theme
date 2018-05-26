(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window), $root, $scrollTarget, $scroller, $scroll;
    var rootTop, rootLeft, rootHeight, scrollBottom, rootBottomTop, lastScrollTop;
    var offsetBottom = 0, disabled = false, scrollTarget = window, scroller = 'html, body', scroll = window.document;
    var hasInit = false, isOverallScroller = true;

    function setOptions(options) {
      var _options = options || {};
      _options.offsetBottom && (offsetBottom = _options.offsetBottom);
      _options.scrollTarget && (scrollTarget = _options.scrollTarget);
      _options.scroller && (scroller = _options.scroller);
      _options.scroll && (scroll = _options.scroll);
      _options.disabled !== undefined && (disabled = _options.disabled);
      $scrollTarget = $(scrollTarget);
      $scroller = $(scroller);
      isOverallScroller = window.isOverallScroller($scroller[0]);
      $scroll = $(scroll);
      calc(true);
    }
    function initData() {
      top();
      rootHeight = $root.outerHeight();
      rootTop = $root.offset().top + (isOverallScroller ? 0 :  $scroller.scrollTop());
      rootLeft = $root.offset().left;
    }
    function calc(needInitData) {
      needInitData && initData();
      scrollBottom = $scroll.outerHeight() - offsetBottom - rootHeight;
      rootBottomTop = scrollBottom - rootTop;
    }
    function top() {
      $root.removeClass('fixed').css({
        left: 0,
        top: 0
      });
    }
    function fixed() {
      $root.addClass('fixed').css({
        left: rootLeft + 'px',
        top: 0
      });
    }
    function bottom() {
      $root.removeClass('fixed').css({
        left: 0,
        top: rootBottomTop + 'px'
      });
    }
    function setState(force) {
      force !== true && (force = false);
      var scrollTop = $scrollTarget.scrollTop();
      if (scrollTop >= rootTop && scrollTop <= scrollBottom) {
        (!force && lastScrollTop >= rootTop && lastScrollTop <= scrollBottom) || fixed();
      } else if (scrollTop < rootTop) {
        (!force && lastScrollTop < rootTop) || top();
      } else {
        (!force && lastScrollTop > scrollBottom) || bottom();
      }
      lastScrollTop = scrollTop;
    }
    function init() {
      if(!hasInit) {
        var interval, timeout;
        calc(true); setState();
        // run calc every 1.5 seconds
        interval = setInterval(function() {
          calc();
        }, 1500);
        timeout = setTimeout(function() {
          clearInterval(interval);
        }, 50000);
        window.pageLoad.then(function() {
          clearInterval(interval);
          clearTimeout(timeout);
        });
        $scrollTarget.on('scroll', function() {
          disabled || setState();
        });
        $window.on('resize', window.throttle(function() {
          disabled || (calc(true), setState(true));
        }, 100));
        hasInit = true;
      }
    }

    function affix(options) {
      $root = this;
      setOptions(options);
      if (!disabled) {
        init();
      }
      $window.on('resize', window.throttle(function() {
        init();
      }, 200));
      return {
        setOptions: setOptions
      };
    }
    $.fn.affix = affix;
  });
})();