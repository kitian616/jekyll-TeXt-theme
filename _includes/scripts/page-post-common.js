(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  function scrollAnimateTo(destination, duration, callback) {
    var $body = $('html, body'), bodyScrollTop = $body.scrollTop();
    if(bodyScrollTop === destination) { return; }
    $body.animate({ scrollTop: destination }, duration, callback);
  }
  window.scrollTopAnchor = function(anchor, callback) {
    scrollAnimateTo($(anchor).offset().top, 400, function() {
      window.history.replaceState(null, '', window.location.href.split('#')[0] + anchor);
      callback && callback();
    });
  };
  window.Lazyload.js(SOURCES.jquery, function() {
    var $articleContent = $('.m-post, .m-page').find('.m-article-content'), $this;
    $articleContent.children('.highlight').each(function() {
      $this = $(this);
      $this.attr('data-lang', $this.find('code').attr('data-lang'));
    });

    $articleContent.children('h1, h2, h3, h4, h5, h6').each(function() {
      $this = $(this);
      $this.append($('<a class="anchor" aria-hidden="true"></a>').html('{%- include svg/icon/link.svg -%}'));
    });
    $articleContent.on('click', '.anchor', function() {
      window.scrollTopAnchor('#' + $(this).parent().attr('id'));
    });
  });
})();