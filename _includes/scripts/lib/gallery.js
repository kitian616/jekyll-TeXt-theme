(function() {
  {%- include scripts/lib/swiper.js -%}
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var MUSTACHE_ITEMS = '{items}';
    var template =
      '<div class="swiper gallery__swiper">' +
        '<div class="swiper__wrapper">' +
          MUSTACHE_ITEMS +
        '</div>' +
        '<div class="swiper__button swiper__button--prev fas fa-chevron-left"></div>' +
        '<div class="swiper__button swiper__button--next fas fa-chevron-right"></div>' +
      '</div>';
    function Gallery(root, items, options) {
      this.$root = $(root);
      this.items = items;
      this.$swiper = null;
      this.swiper = null;
    }
    Gallery.prototype.init = function() {
      var i, item, items = this.items, itemsSnippet = '';
      for (i = 0; i < items.length; i++) {
        item = items[i];
        itemsSnippet = itemsSnippet +
          '<div class="swiper__slide">' +
            '<div class="gallery-item">' +
              '<div class="gallery-item__content">' +
                '<img src="' + item.src + '"/>' +
              '</div>' +
            '</div>' +
          '</div>';
      }
      this.$root.append(template.replace(MUSTACHE_ITEMS, itemsSnippet));
      this.$swiper = this.$root.find('.gallery__swiper');
      this.swiper = this.$swiper && this.$swiper.swiper();
    };
    Gallery.prototype.refresh = function() {
      this.swiper && this.swiper.refresh();
    };
    Gallery.prototype.setOptions = function(options) {
      this.swiper && this.swiper.setOptions(options);
    };
    window.Gallery = Gallery;
  });
})();