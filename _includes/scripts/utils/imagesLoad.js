(function() {
  var images = document.getElementsByTagName('img'), image;
  var imagesCount = images.length, loadedCount = 0;
  var i, j, loaded = false, cbs = [];
  imagesCount < 1 && (loaded = true);
  for (i = 0; i < imagesCount; i++) {
    image = images[i];
    image.complete ? handleImageLoad() : image.addEventListener('load', handleImageLoad);
  }
  function handleImageLoad() {
    loadedCount++;
    if (loadedCount === imagesCount) {
      loaded = true;
      if (cbs.length > 0) {
        for (j = 0; j < cbs.length; j++) {
          cbs[j]();
        }
      }
    }
  }
  window.imagesLoad = {
    then: function(cb) {
      cb && (loaded ? cb() : (cbs.push(cb)));
    }
  };
})();