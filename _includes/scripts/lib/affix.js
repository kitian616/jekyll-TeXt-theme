(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window), $document = $(window.document), $root;
    var rootTop, rootLeft, rootHeight, scrollBottom, rootBottomTop, lastScrollTop;
    var offsetBottom = 0, disabled = false;

    function setOptions(options) {
      var _options = options || {};
      _options.offsetBottom && (offsetBottom = _options.offsetBottom);
      _options.disabled !== undefined && (disabled = _options.disabled);
      calc(true);
    }
    function init() {
      top();
      var rootOffset = $root.offset();
      rootHeight = $root.outerHeight();
      rootTop = rootOffset.top;
      rootLeft = rootOffset.left;
    }
    function calc(needInit) {
      needInit && init();
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

    function affix(options) {
      $root = this;
      setOptions(options);
      setTimeout(function() {
      }, 100);
      !disabled && setState();
      setTimeout(function() {
        calc();
        $window.on('load', function() {
          calc();
        });
      }, 2000);
      $window.on('scroll', function() {
        !disabled && setState();
      });
      $window.on('resize', window.throttle(function() {
        !disabled && (calc(true), setState(true));
      }, 100));
      return {
        setOptions: setOptions
      };
    }
    $.fn.affix = affix;
  });
})();