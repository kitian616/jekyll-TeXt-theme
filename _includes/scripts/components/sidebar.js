(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $pageRoot = $('.js-page-root');
    var $sidebarShow = $('.js-sidebar-show');
    var $sidebarHide = $('.js-sidebar-hide');
    $sidebarShow.on('click', function() {
      $pageRoot.addClass('show-sidebar');
    });
    $sidebarHide.on('click', function() {
      $pageRoot.removeClass('show-sidebar');
    });
  });
})();