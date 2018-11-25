(function () {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    window.pageLoad.then(function() {
      /* global Gallery */
      var $pageGalleryModal = $('.js-page-gallery-modal');
      var pageGalleryModal = $pageGalleryModal.modal();
      var $images = $('.page__content').find('img');
      var i, items = [], image, item;
      if($images && $images.length > 0) {
        for (i = 0; i < $images.length; i++) {
          image = $images.eq(i);
          if (image.get(0).naturalWidth > 800) {
            items.push({ src: image.attr('src'), $el: image});
          }
        }
      }

      if(items.length > 0) {
        var gallery = new Gallery('.gallery', items);
        gallery.init();
        for (i = 0; i < items.length; i++) {
          item = items[i];
          item.$el && item.$el.on('click', (function() {
            var index = i;
            return function() {
              pageGalleryModal.show();
              gallery.setOptions({ initialSlide: index });
              gallery.refresh(true, { animation: false });
            };
          })());
        }
      }
      $pageGalleryModal.on('click', function() {
        pageGalleryModal.hide();
      });
      $pageGalleryModal.on('touchmove', function(e) {
        if (e.cancelable) {
          e.preventDefault();
        }
      });
    });
  });
})();