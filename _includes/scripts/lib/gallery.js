(function() {
  {%- include scripts/lib/swiper.js -%}
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var template =
      '<div class="swiper gallery__swiper">' +
        '<div class="swiper__wrapper">' +
        '</div>' +
        '<div class="swiper__button swiper__button--prev fas fa-chevron-left"></div>' +
        '<div class="swiper__button swiper__button--next fas fa-chevron-right"></div>' +
      '</div>';
    function Gallery(root, items, options) {
      this.$root = $(root);
      this.$swiper = null;
      this.$itemWrapper = null;
      this.swiper = null;
      this.$items = [];
      this.items = items;
      this.disabled = false;
      this.contentWidth = 0;
      this.contentHeight = 0;
    }
    Gallery.prototype.init = function() {
      var i, item, items = this.items, itemsSnippet = '', size, self = this;
      this.$root.append(template);
      this.$swiper = this.$root.find('.gallery__swiper');
      this.$itemWrapper = this.$root.find('.swiper__wrapper');
      this.contentWidth = this.$itemWrapper && this.$itemWrapper.width();
      this.contentHeight = this.$itemWrapper && this.$itemWrapper.height();
      for (i = 0; i < items.length; i++) {
        item = items[i];
        size = this.calculateImageSize(item.w, item.h);
        this.$items.push($(
          '<div class="swiper__slide">' +
            '<div class="gallery-item">' +
              '<div class="gallery-item__content">' +
                '<img src="' + item.src + '" style="width:' + size.w + 'px;height:' + size.h +  'px"/>' +
              '</div>' +
            '</div>' +
          '</div>'
        ));
      }
      this.$itemWrapper && this.$itemWrapper.append(this.$items);
      this.swiper = this.$swiper && this.$swiper.swiper();
      $(window).on('resize', function() {
        if (self.disabled) { return; }
        self.resizeImageSize();
      });
      // Char Code: 37  ⬅, 39  ➡
      $(window).on('keyup', function(e) {
        if (window.isFormElement(e.target || e.srcElement) || self.disabled) { return; }
        if (e.which === 37) {
          self.swiper && self.swiper.previous();
        } else if (e.which === 39) {
          self.swiper && self.swiper.next();
        }
      });
    };
    Gallery.prototype.calculateImageSize = function(w, h) {
      var scale = 1;
      if (this.contentWidth > 0 && this.contentHeight > 0 && w > 0 && h > 0) {
        scale = Math.min(
          Math.min(w, this.contentWidth) / w,
          Math.min(h, this.contentHeight) / h);
      }
      return { w: Math.floor(w * scale), h: Math.floor(h * scale) };
    };
    Gallery.prototype.resizeImageSize = function() {
      var i, $item, $items = this.$items, item, size;
      this.contentWidth = this.$itemWrapper && this.$itemWrapper.width();
      this.contentHeight = this.$itemWrapper && this.$itemWrapper.height();
      if ($items.length < 1) { return; }
      for (i = 0; i < $items.length; i++) {
        item = this.items[i], $item = $items[i];
        size = this.calculateImageSize(item.w, item.h);
        $item && $item.find('img').css({ width: size.w, height: size.h });
      }
    }
    Gallery.prototype.refresh = function() {
      this.swiper && this.swiper.refresh();
      this.resizeImageSize();
    };
    Gallery.prototype.setOptions = function(options) {
      this.disabled = options.disabled;
      this.swiper && this.swiper.setOptions(options);
    };
    window.Gallery = Gallery;
  });
})();