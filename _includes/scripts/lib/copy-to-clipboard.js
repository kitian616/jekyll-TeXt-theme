(function() {
	// This script is built on clipboard.js, cf: https://clipboardjs.com/
	// 1. find all code blocks defined under snippet class
	var snippets = document.querySelectorAll('pre');
	[].forEach.call(snippets, function(snippet) {
		if (snippet.closest('.snippet') !== null) {
			snippet.firstChild.insertAdjacentHTML('beforebegin', '<button class="btn" data-clipboard-snippet><img class="clippy" height="20" src="/assets/clippy.svg" alt="Copy to clipboard"></button>');
		}
	});
	var clipboardSnippets = new ClipboardJS('[data-clipboard-snippet]', {
		target: function(trigger) {
			return trigger.nextElementSibling;
		}
	});
	clipboardSnippets.on('success', function(e) {
		e.clearSelection();
		showTooltip(e.trigger, 'Copied!');
	});
	clipboardSnippets.on('error', function(e) {
		showTooltip(e.trigger, fallbackMessage(e.action));
	});

	// 2. add event listener for all created copy button
	var btns = document.querySelectorAll('.btn');
	for (var i = 0; i < btns.length; i++) {
		btns[i].addEventListener('mouseleave', clearTooltip);
		btns[i].addEventListener('blur', clearTooltip);
		btns[i].addEventListener('mouseenter', showToolhint);
	}

	function clearTooltip(e) {
		e.currentTarget.setAttribute('class', 'btn');
		e.currentTarget.removeAttribute('aria-label');
	}

	function showTooltip(elem, msg) {
		elem.setAttribute('class', 'btn tooltipped tooltipped-s');
		elem.setAttribute('aria-label', msg);
	}

	function showToolhint(e) {
		e.currentTarget.setAttribute('class', 'btn tooltipped tooltipped-s');
		e.currentTarget.setAttribute('aria-label', 'copy to clipboard');
	}

	function fallbackMessage(action) {
		var actionMsg = '';
		var actionKey = (action === 'cut' ? 'X' : 'C');
		if (/iPhone|iPad/i.test(navigator.userAgent)) {
			actionMsg = 'No support :(';
		} else if (/Mac/i.test(navigator.userAgent)) {
			actionMsg = 'Press ⌘-' + actionKey + ' to ' + action;
		} else {
			actionMsg = 'Press Ctrl-' + actionKey + ' to ' + action;
		}
		return actionMsg;
	}
})();