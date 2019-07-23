
(function () {
  // 1. SOURCES var
  var SOURCES = window.TEXT_VARIABLES.sources;

  // 2. window function
  window.Lazyload.js(SOURCES.jquery, function() {
    // 2.1 search panel
    var search = (window.search || (window.search = {}));
    var useDefaultSearchBox = window.useDefaultSearchBox === undefined ? true : window.useDefaultSearchBox ;

    // 2.2 search modal setting
    var $searchModal = $('.js-page-search-modal');
    var $searchToggle = $('.js-search-toggle');  // s, / : toggle key
    var searchModal = $searchModal.modal({ onChange: handleModalChange, hideWhenWindowScroll: true });
    var modalVisible = false;
    search.searchModal = searchModal;
    var $searchBox = null;
    var $searchInput = null;
    var $searchClear = null;

    // 2.3 getMOdalVisible function
    function getModalVisible() {
      return modalVisible;
    }

    search.getModalVisible = getModalVisible;

    // 2.4 handleModalChange function
    function handleModalChange(visible) {
      modalVisible = visible;
      if (visible) {
        search.onShow && search.onShow();
        useDefaultSearchBox && $searchInput[0] && $searchInput[0].focus();
      } else {
        search.onShow && search.onHide();
        useDefaultSearchBox && $searchInput[0] && $searchInput[0].blur();
        setTimeout(function() {
          useDefaultSearchBox && ($searchInput.val(''), $searchBox.removeClass('not-empty'));
          search.clear && search.clear();
          window.pageAsideAffix && window.pageAsideAffix.refresh();
        }, 400);
      }
    }

    // 2.5 'click' on function
    $searchToggle.on('click', function() {
      modalVisible ? searchModal.hide() : searchModal.show();
    });

    // 2.6 'keyup' on function
    // Char Code: 83  S, 191 /
    $(window).on('keyup', function(e) {
      if (!modalVisible && !window.isFormElement(e.target || e.srcElement) && (e.which === 83 || e.which === 191)) {
        modalVisible || searchModal.show();
      }
    });

    // 2.7 flow continue
    if (useDefaultSearchBox) {
      $searchBox = $('.js-search-box');
      $searchInput = $searchBox.children('input');
      $searchClear = $searchBox.children('.js-icon-clear');

      // Input 입력
      search.getSearchInput = function() {
        return $searchInput.get(0);
      };

      // get function
      search.getVal = function() {
        return $searchInput.val();
      };

      // set function
      search.setVal = function(val) {
        $searchInput.val(val);
      };

      // 'focus' on function
      $searchInput.on('focus', function() {
        $(this).addClass('focus');
      });

      // 'blur' on function
      $searchInput.on('blur', function() {
        $(this).removeClass('focus');
      });

      // 'input' on function
      $searchInput.on('input', window.throttle(function() {
        var val = $(this).val();
        if (val === '' || typeof val !== 'string') {
          search.clear && search.clear();
        } else {
          // 정상적인 값을 입력받은 경우
          $searchBox.addClass('not-empty');
          search.onInputNotEmpty && search.onInputNotEmpty(val);
        }
      }, 400));

      // 'click' on function (초기화)
      $searchClear.on('click', function() {
        $searchInput.val(''); $searchBox.removeClass('not-empty');
        search.clear && search.clear();
      });
    }

  });
})();
