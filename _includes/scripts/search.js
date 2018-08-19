var SOURCES = window.TEXT_VARIABLES.sources;
var PAHTS = window.TEXT_VARIABLES.paths;
window.Lazyload.js([SOURCES.jquery, PAHTS.search_js], function() {
  var searchData = window.TEXT_SEARCH_DATA ? initData(window.TEXT_SEARCH_DATA) : {};

  function memorize(f) {
    var cache = {};
    return function () {
      var key = Array.prototype.join.call(arguments, ',');
      if (key in cache) return cache[key];
      else return cache[key] = f.apply(this, arguments);
    };
  }

  function initData(data) {
    var _data = [], i, j, key, keys, cur;
    keys = Object.keys(data);
    for (i = 0; i < keys.length; i++) {
      key = keys[i], _data[key] = [];
      for (j = 0; j < data[key].length; j++) {
        cur = data[key][j];
        cur.title = window.decodeUrl(cur.title);
        cur.url = window.decodeUrl(cur.url);
        _data[key].push(cur);
      }
    }
    return _data;
  }

  /// search
  function searchByQuery(query) {
    var i, j, key, keys, cur, _title, result = {};
    keys = Object.keys(searchData);
    for (i = 0; i < keys.length; i++) {
      key = keys[i];
      for (j = 0; j < searchData[key].length; j++) {
        cur = searchData[key][j], _title = cur.title;
        if ((result[key] === undefined || result[key] && result[key].length < 4 )
          && _title.toLowerCase().indexOf(query.toLowerCase()) >= 0) {
          if (result[key] === undefined) {
            result[key] = [];
          }
          result[key].push(cur);
        }
      }
    }
    return result;
  }

  var renderHeader = memorize(function(header) {
    return $('<p class="search-result__header">' + header + '</p>');
  });

  var renderItem = memorize(function(key, title, url) {
    return $('<li class="search-result__item"><a href="' + url + '">' + title + '</a></li>');
  });

  function render(data) {
    if (!data) {
      return null;
    }
    var $root = $('<ul></ul>'),  i, j, key, keys, cur;
    keys = Object.keys(data);
    for (i = 0; i < keys.length; i++) {
      key = keys[i];
      $root.append(renderHeader(key));
      for (j = 0; j < data[key].length; j++) {
        cur = data[key][j];
        $root.append(renderItem(cur.key, cur.title, cur.url));
      }
    }
    return $root;
  }

  // search box
  var $searchBox = $('.js-search-box');
  var $searchInput = $searchBox.children('input');
  var $searchClear = $searchBox.children('.js-icon-clear');
  var $result = $('.js-search-result');

  function searchBoxEmpty() {
    $searchBox.removeClass('not-empty'); $result.html(null);
  }

  $searchInput.on('input', window.throttle(function() {
    var val = $(this).val();
    if (val === '' || typeof val !== 'string') {
      searchBoxEmpty();
    } else {
      $searchBox.addClass('not-empty'); $result.html(render(searchByQuery(val)));
    }
  }, 400));
  $searchInput.on('focus', function() {
    $(this).addClass('focus');
  });
  $searchInput.on('blur', function() {
    $(this).removeClass('focus');
  });
  $searchClear.on('click', function() {
    $searchInput.val(''); searchBoxEmpty();
  });
});