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
    function setState($item, zoom, translate) {
      $item.css('transform', 'scale(' + zoom + ') translate(' + translate.x +  'px,' + translate.y + 'px)');
    }
    function Gallery(root, items) {
      this.$root = $(root);
      this.$swiper = null;
      this.$swiperWrapper = null;
      this.$activeItem = null;
      this.swiper = null;
      this.$items = [];
      this.items = items;
      this.disabled = false;
      this.contentWidth = 0;
      this.contentHeight = 0;
      this.PreRect = null;
      this.rect = null;
      this.preVector = null;
      this.vector = null;
      this.preZoom = 1;
      this.zoom = 1;
    }
    Gallery.prototype._handleChangeEnd = function(index, $dom, preIndex, $preDom) {
      this.PreRect = null; this.rect = null;
      this.preVector = null; this.vector = { x: 0, y:0 };
      this.preZoom = 1; this.zoom = 1;
      this.$activeItem = $dom.find('.gallery-item__content');
      setState($preDom.find('.gallery-item__content'), this.zoom, this.vector);
    };
    Gallery.prototype.init = function() {
      var i, item, items = this.items, size, self = this;
      this.$root.append(template);
      this.$swiper = this.$root.find('.gallery__swiper');
      this.$swiperWrapper = this.$root.find('.swiper__wrapper');
      this.contentWidth = this.$swiperWrapper && this.$swiperWrapper.width();
      this.contentHeight = this.$swiperWrapper && this.$swiperWrapper.height();
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
      this.$swiperWrapper && this.$swiperWrapper.append(this.$items);
      this.swiper = this.$swiper && this.$swiper.swiper({
        onChangeEnd: function() {
          self._handleChangeEnd.apply(self, Array.prototype.slice.call(arguments));
        }
      });
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
      function getRect(touch0, touch1) {
        return {
          o: {
            x: (touch0.pageX + touch1.pageX) / 2,
            y: (touch0.pageY + touch1.pageY) / 2
          },
          w: Math.abs(touch0.pageX - touch1.pageX),
          h: Math.abs(touch0.pageY - touch1.pageY)
        };
      }
      function minusVector(vector0, vector1) {
        return {
          x: vector0.x - vector1.x,
          y: vector0.y - vector1.y
        };
      }
      function addVector(vector0, vector1) {
        return {
          x: vector0.x + vector1.x,
          y: vector0.y + vector1.y
        };
      }

      self.$swiperWrapper.on('touchstart', function(e) {
        if (e.touches && e.touches.length > 1) {
          var touch0 = e.touches[0];
          var touch1 = e.touches[1];
          self.PreRect = self.rect = getRect(touch0, touch1);
        }
      });
      self.$swiperWrapper.on('touchmove', function(e) {
        if (e.touches && e.touches.length > 1) {
          var touch0 = e.touches[0];
          var touch1 = e.touches[1];
          var curRect = getRect(touch0, touch1);
          self.vector = curRect.o && self.rect.o && self.preVector ? addVector(minusVector(curRect.o, self.rect.o), self.preVector) : { x: 0, y: 0 };
          self.rect = curRect; self.preVector = self.vector;
          self.zoom = Math.max(curRect.w / self.PreRect.w, curRect.h / self.PreRect.h) * self.preZoom;
          setState(self.$activeItem, self.zoom, self.vector);
        }
      });
      self.$swiperWrapper.on('touchend', function() {
        self.preZoom = self.zoom;
      });
      this.$root.on('touchmove', function(e) {
        if (self.disabled) { return; }
        e.preventDefault();
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
      this.contentWidth = this.$swiperWrapper && this.$swiperWrapper.width();
      this.contentHeight = this.$swiperWrapper && this.$swiperWrapper.height();
      if ($items.length < 1) { return; }
      for (i = 0; i < $items.length; i++) {
        item = this.items[i], $item = $items[i];
        size = this.calculateImageSize(item.w, item.h);
        $item && $item.find('img').css({ width: size.w, height: size.h });
      }
    };
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