(function () {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function () {
    $.fn.inViewport = function (cb) {
      return this.each(function (i, el) {
        function visPx() {
          var H = $(this).height(),
            r = el.getBoundingClientRect(), t = r.top, b = r.bottom;
          return cb.call(el, Math.max(0, t > 0 ? H - t : (b < H ? b : H)));
        } visPx();
        $(window).on("resize scroll", visPx);
      });
    }
      ;

    $(function () {
      $("[fade-in]:not([fade-in=none],[viewed=true])").inViewport(function (px) {
        if (px) $(this).attr("viewed", true);
      });
    });
  })
})();
