(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $pageRoot = $('.js-page-root');
    var $sidebarButton = $('.js-sidebar-button');
    $sidebarButton.on('click', function() {
      $pageRoot.toggleClass('show-sidebar');
    });
  });
})();