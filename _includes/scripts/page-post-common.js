(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $this, $articleContent = $('.article-content'), $scroll;
    var hasSidebar = $('.js-page-root').hasClass('layout--page--sidebar');
    var scroll = hasSidebar ? '.js-page-main' : 'html, body';
    $scroll = $(scroll);
    $articleContent.children('.highlight').each(function() {
      $this = $(this);
      $this.attr('data-lang', $this.find('code').attr('data-lang'));
    });

    $articleContent.children('h1, h2, h3, h4, h5, h6').each(function() {
      $this = $(this);
      $this.append($('<a class="anchor" aria-hidden="true"></a>').html('<i class="fas fa-anchor"></i>'));
    });
    $articleContent.on('click', '.anchor', function() {
      $scroll.scrollToAnchor('#' + $(this).parent().attr('id'), 400);
    });
  });
})();