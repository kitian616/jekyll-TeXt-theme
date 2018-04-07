(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window), $tocUl = $('<ul></ul>'), $tocLi, $headings, $activeLast, $activeCur;
    var selectors = 'h1,h2,h3', container = 'body', disabled = false;
    var headingsPos, scrolling = false, rendered = false;
    function setOptions(options) {
      var _options = options || {};
      _options.selectors && (selectors = _options.selectors);
      _options.container && (container = _options.container);
      _options.disabled !== undefined && (disabled = _options.disabled);
      $headings = $(container).find(selectors);
      calc();
    }
    function calc() {
      headingsPos = [];
      $headings.each(function() {
        headingsPos.push(Math.floor($(this).offset().top));
      });
    }
    function setActiveHeading(element, disabled) {
      var scrollTop = $window.scrollTop(), i;
      if (disabled || !headingsPos || headingsPos.length < 1) { return; }
      if (element) {
        $activeCur = element;
      } else {
        for (i = 0; i < headingsPos.length; i++) {
          if (scrollTop >= headingsPos[i]) {
            $activeCur = $tocLi.eq(i);
          } else {
            $activeCur || ($activeCur = $tocLi.eq(i));
            break;
          }
        }
      }
      $activeLast && $activeLast.removeClass('toc-active');
      ($activeLast = $activeCur).addClass('toc-active');
    }
    function render($root) {
      $root.append($tocUl);
      $headings.each(function() {
        var $this = $(this);
        $tocUl.append($('<li></li>').addClass('toc-' + $this.prop('tagName').toLowerCase())
          .append($('<a></a>').text($this.text()).attr('href', '#' + $this.prop('id'))));
      });
      $tocLi = $tocUl.children('li');
      $tocUl.on('click', 'a', function(e) {
        e.preventDefault();
        var $this = $(this);
        scrolling = true;
        setActiveHeading($this.parent());
        window.scrollTopAnchor($this.attr('href'), function() {
          scrolling = false;
        });
      });
      rendered = true;
    }
    function toc(options) {
      setOptions(options);
      disabled || (render(this), setActiveHeading(null, scrolling),
        $window.on('load', function() {
          calc();
        })
      );
      $window.on('resize', window.throttle(function() {
        if (!disabled) {
          rendered || render();
          calc();
          setActiveHeading(null, scrolling);
        }
      }));
      $window.on('scroll', function() {
        disabled || setActiveHeading(null, scrolling);
      });
      return {
        setOptions: setOptions
      };
    }
    toc.setOptions = setOptions;
    $.fn.toc = toc;
  });
})();