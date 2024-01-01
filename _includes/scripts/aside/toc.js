function waitForElm(selector) {
  return new Promise(resolve => {
      if (document.querySelector(selector)) {
          return resolve(document.querySelector(selector));
      }

      const observer = new MutationObserver(mutations => {
          if (document.querySelector(selector)) {
              observer.disconnect();
              resolve(document.querySelector(selector));
          }
      });

      observer.observe(document.body, {
          childList: true,
          subtree: true
      });
  });
}




class ClassWatcher {

  constructor(targetNode, classToWatch, classAddedCallback) {
      this.targetNode = targetNode
      this.classToWatch = classToWatch
      this.classAddedCallback = classAddedCallback
      this.observer = null
      this.init()
  }

  init() {
      this.observer = new MutationObserver(this.mutationCallback)
      this.observe()
  }

  observe() {
      this.observer.observe(this.targetNode, { attributes: true, childList: true, subtree: true })
  }

  disconnect() {
      this.observer.disconnect()
  }

  mutationCallback = mutationsList => {
      for(let mutation of mutationsList) {
          if (mutation.target.nodeName === 'LI') {
            if (mutation.type === 'attributes' && mutation.attributeName === 'class') {
              let currentClassState = mutation.target.classList.contains(this.classToWatch)
              if(currentClassState) {
                this.classAddedCallback(mutation.target);
              }
            }
          }
      }
  }
}

(function() {
  var SOURCES = window.TEXT_VARIABLES.sources;
  var TOC_SELECTOR = window.TEXT_VARIABLES.site.toc.selectors;
  window.Lazyload.js(SOURCES.jquery, function() {
    var $window = $(window);
    var $articleContent = $('.js-article-content');
    var $tocRoot = $('.js-toc-root'), $col2 = $('.js-col-aside');
    var toc;
    var tocDisabled = false;
    var hasSidebar = $('.js-page-root').hasClass('layout--page--sidebar');
    var hasToc = $articleContent.find(TOC_SELECTOR).length > 0;

    function disabled() {
      return $col2.css('display') === 'none' || !hasToc;
    }

    tocDisabled = disabled();

    toc = $tocRoot.toc({
      selectors: TOC_SELECTOR,
      container: $articleContent,
      scrollTarget: hasSidebar ? '.js-page-main' : null,
      scroller: hasSidebar ? '.js-page-main' : null,
      disabled: tocDisabled
    });

    $window.on('resize', window.throttle(function() {
      tocDisabled = disabled();
      toc && toc.setOptions({
        disabled: tocDisabled
      });
    }, 100));

  });

  document.addEventListener('DOMContentLoaded', () => {
    waitForElm('ul.toc').then((toc) => {
      let onAddActive = (target) => {
        const isH4 = target.classList.contains('toc-h4');
        if (isH4 && target.classList.contains('visible')) {
          return;
        }

        const h4s = toc.querySelectorAll('.toc-h4');
        h4s.forEach(h4 => h4.classList.remove('visible'));

        while (target.classList.contains('toc-h4')) {
          console.log(target);
          target = target.previousSibling;
        }

        target = target.nextSibling;
        while (target && target.classList.contains('toc-h4')) {
          target.classList.add('visible');
          target = target.nextSibling;
        }
      };

      let activeWatcher = new ClassWatcher(toc, 'active', onAddActive);
    })
  });
})();
