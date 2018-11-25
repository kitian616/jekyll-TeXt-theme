(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $body = $('body'), $window = $(window);
    var $pageRoot = $('.js-page-root'), $pageMain = $('.js-page-main');
    var activeCount = 0;
    function modal(isShow) {
      var $root = this, _isShow = isShow === undefined ? false : show;
      var scrollTop;
      function init() {
        setState(_isShow);
      }
      function windowScroll() {
        setState(false);
      }
      function setState(isShow) {
        if (isShow === _isShow) {
          return;
        }
        _isShow = isShow;
        if (_isShow) {

          activeCount ++;
          scrollTop = $(window).scrollTop() || $pageMain.scrollTop();
          $root.addClass('modal--show');
          $pageMain.scrollTop(scrollTop);
          activeCount === 1 && ($pageRoot.addClass('show-modal'), $body.addClass('of-hidden'));
          $window.on('scroll', windowScroll);
        } else {
          activeCount > 0 && activeCount --;
          $root.removeClass('modal--show');
          $window.scrollTop(scrollTop);
          activeCount === 0 && ($pageRoot.removeClass('show-modal'), $body.removeClass('of-hidden'));
          $window.off('scroll', windowScroll);
        }
      }
      function show(callback) {
        setState(true);
        callback && callback();
      }
      function hide(callback) {
        setState(false);
        callback && callback();
      }
      function toggle(callback) {
        setState(!_isShow);
        callback && callback();
      }
      init();
      return {
        show: show,
        hide: hide,
        toggle: toggle

      };
    }
    $.fn.modal = modal;
  });
})();