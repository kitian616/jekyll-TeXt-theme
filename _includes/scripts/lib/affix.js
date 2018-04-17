(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window), $document = $(window.document), $root;
    var rootTop, rootLeft, rootHeight, scrollBottom, rootBottomTop, lastScrollTop;
    var offsetBottom = 0, disabled = false, hasInit = false;

    function setOptions(options) {
      var _options = options || {};
      _options.offsetBottom && (offsetBottom = _options.offsetBottom);
      _options.disabled !== undefined && (disabled = _options.disabled);
      calc(true);
    }
    function initData() {
      top();
      var rootOffset = $root.offset();
      rootHeight = $root.outerHeight();
      rootTop = rootOffset.top;
      rootLeft = rootOffset.left;
    }
    function calc(needInitData) {
      needInitData && initData();
      scrollBottom = $document.outerHeight() - offsetBottom - rootHeight;
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
      var scrollTop = $window.scrollTop();
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
        $window.on('scroll', function() {
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