(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    function swiper(options) {
      var $window = $(window), $root = this, $swiperWrapper, $swiperButtonPrev, $swiperButtonNext,
        initialSlide, width, height, animation,
        rootWidth, rootHeight, count, curIndex;

      function setOptions(options) {
        var _options = options || {};
        initialSlide = _options.initialSlide || 0;
        animation = _options.animation === undefined && true;
        width = _options.width === undefined ||
          typeof _options.width === 'string' ? _options.width :
          typeof _options.width === 'number' ? _options.width + 'px' : undefined;
        height = _options.height  === undefined ||
          typeof _options.height === 'string' ? _options.height :
          typeof _options.height === 'number' ? _options.height + 'px' : undefined;
        init();
      }

      function init() {
        $swiperWrapper = $root.find('.swiper__wrapper');
        $swiperButtonPrev = $root.find('.swiper__button--prev');
        $swiperButtonNext = $root.find('.swiper__button--next');
        count = $swiperWrapper.children('.swiper__slide').length;
        curIndex = initialSlide || 0;
        var _cssObj = {};
        width === undefined || (_cssObj.width = width);
        height === undefined || (_cssObj.height = height);
        $root.css(_cssObj);
        animation && $swiperWrapper.addClass('swiper__wrapper--animation');
      }

      function preCalc() {
        rootWidth = $root.width();
        rootHeight = $root.height();
      }

      var calc = (function() {
        var lastResizing;
        return function (needPreCalc, params) {
          needPreCalc && preCalc();
          var resizing = params && params.resizing || false;
          if (animation && (lastResizing === undefined || lastResizing !== resizing)) {
            lastResizing = resizing ? $swiperWrapper.removeClass('swiper__wrapper--animation') :
              $swiperWrapper.addClass('swiper__wrapper--animation');
          }
          var translateX = curIndex <= 0 ? 0 : - rootWidth * curIndex;
          $swiperWrapper.css('transform', 'translate(' + translateX + 'px, 0)');
          if (curIndex <= 0) {
            $swiperButtonPrev.addClass('disabled');
          } else if (curIndex < count - 1) {
            $swiperButtonPrev.removeClass('disabled');
            $swiperButtonNext.removeClass('disabled');
          } else {
            $swiperButtonNext.addClass('disabled');
          }
        };
      })();

      function move(type) {
        var nextIndex = curIndex;
        if (type === 'prev') {
          nextIndex > 0 && nextIndex--;
        } else if (type === 'next') {
          nextIndex < count - 1 && nextIndex++;
        }
        if (nextIndex !== curIndex) {
          curIndex = nextIndex; calc();
        }
      }

      setOptions(options);
      $swiperButtonPrev.on('click', function() {
        move('prev');
      });
      $swiperButtonNext.on('click', function() {
        move('next');
      });
      $window.on('resize', function() {
        calc(true, { resizing: true });
      });
      calc(true);
      return {
        setOptions: setOptions
      };
    }
    $.fn.swiper = swiper;
  });
})();