/* global Chart */
(function () {
  var SOURCES = window.TEXT_VARIABLES.sources;
  var PAGE = window.TEXT_VARIABLES.page;
  if (PAGE.chart) {
    window.Lazyload.js([SOURCES.jquery, SOURCES.chart], function() {
      var $canvas = null, $this = null, _ctx = null, _text = '';
      $('.language-chart').each(function(){
        $this = $(this);
        $canvas = $('<canvas></canvas>');
        _text = $this.text();
        $this.text('').append($canvas);
        _ctx = $canvas.get(0).getContext('2d');
        (_ctx && _text) && (new Chart(_ctx, JSON.parse(_text)) && $this.attr('data-processed', true));
      });
    });
  }
})();