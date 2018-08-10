(function() {
  window.isArray = function(val) {
    return Object.prototype.toString.call(val) === '[object Array]';
  };
  window.isString = function(val) {
    return typeof val === 'string';
  };

  window.decodeUrl = function(str) {
    return str ? decodeURIComponent(str.replace(/\+/g, '%20')) : '';
  };


  window.hasEvent = function(event) {
    return 'on'.concat(event) in window.document;
  };

  window.isOverallScroller = function(node) {
    return node === document.documentElement || node === document.body || node === window;
  };

  window.pageLoad = (function () {
    var loaded = false, cbs = [];
    window.addEventListener('load', function () {
      var i, cb; loaded = true;
      if (cbs.length > 0) {
        for (i = 0; i < cbs.length; i++) {
          cb = cbs[i]; cb();
        }
      }
    });
    return {
      then: function(cb) {
        cb && (loaded ? cb() : (cbs.push(cb)));
      }
    };
  })();
})();