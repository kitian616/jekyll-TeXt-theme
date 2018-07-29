(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window), $root, $scrollTarget, $scroller, $scroll;
    var rootTop, rootLeft, rootHeight, scrollBottom, rootBottomTop;
    var offsetBottom = 0, disabled = false, scrollTarget = window, scroller = 'html, body', scroll = window.document;
    var hasInit = false, isOverallScroller = true, curState;

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
      if (curState !== 'top') {
        $root.removeClass('fixed').css({
          left: 0,
          top: 0
        });
        curState = 'top';
      }
    }
    function fixed() {
      if (curState !== 'fixed') {
        $root.addClass('fixed').css({
          left: rootLeft + 'px',
          top: 0
        });
        curState = 'fixed';
      }
    }
    function bottom() {
      if (curState !== 'bottom') {
        $root.removeClass('fixed').css({
          left: 0,
          top: rootBottomTop + 'px'
        });
        curState = 'bottom';
      }
    }
    function setState() {
      var scrollTop = $scrollTarget.scrollTop();
      if (scrollTop >= rootTop && scrollTop <= scrollBottom) {
        fixed();
      } else if (scrollTop < rootTop) {
        top();
      } else {
        bottom();
      }
    }
    function init() {
      if(!hasInit) {
        var interval, timeout;
        calc(true); setState();
        // run calc every 100 millisecond
        interval = setInterval(function() {
          calc();
        }, 100);
        timeout = setTimeout(function() {
          clearInterval(interval);
        }, 60000);
        window.pageLoad.then(function() {
          setTimeout(function() {
            clearInterval(interval);
            clearTimeout(timeout);
          }, 1500);
        });
        $scrollTarget.on('scroll', function() {
          disabled || setState();
        });
        $window.on('resize', window.throttle(function() {
          disabled || (calc(true), setState());
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