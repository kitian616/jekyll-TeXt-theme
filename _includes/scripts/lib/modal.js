(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $body = $('body');
    var $pageRoot = $('.js-page-root'), $pageMain = $('.js-page-main');
    var scrollTop;
    function modal(isShow) {
      var $root = this, _isShow = isShow === undefined ? false : show;
      function init() {
        setState(_isShow);
      }
      function setState(isShow) {
        _isShow = isShow;
        if (_isShow) {
          $root.addClass('modal--show');
          $pageRoot.addClass('show-modal');
        } else {
          $root.removeClass('modal--show');
          $pageRoot.removeClass('show-modal');
        }
      }
      function show(callback) {
        scrollTop = $(window).scrollTop() || $pageMain.scrollTop();
        setState(true);
        $pageMain.scrollTop(scrollTop);
        $body.addClass('of-hidden');
        callback && callback();
      }
      function hide(callback) {
        setState(false);
        $(window).scrollTop(scrollTop);
        $body.removeClass('of-hidden');
        callback && callback();
      }
      function toggle() {
        setState(!_isShow);
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