// this file assumes a variable nodes is used to hold the nodes of the jstree
// and a <div> with id 'jstree' as a placeholder of the tree.

function getInputValue(id, defaultValue) {
	var value = $(id).val();
	if (value.length <= 0)
		value = defaultValue;

	return parseInt(value);
}

function findLevel(node) {
	var l = node.data.level - 1;
	if (typeof levels[l] == 'undefined')
		levels[l] = [];

	levels[l].push(node)

	$.each(node.children, function(i, v) {
		findLevel(v);
	})
}

// find the node levels in the tree
var levels = [];
$.each(nodes, function(i, v) {
	findLevel(v)
});

// find the min and max year in the documents
var showTopicDocuments = typeof documents != "undefined"
		&& typeof topicMap != "undefined"

if (showTopicDocuments) {
	var minYear = 1000000;
	var maxYear = 0;
	$.each(documents, function(i, d) {
		if (d.year > maxYear)
			maxYear = d.year;
		if (d.year < minYear)
			minYear = d.year;
	})
}

function generateTopicDocumentTable(topic, max) {
	var topicDocuments = topicMap[topic];

	var rows = [];
	for (var i = 0; i < topicDocuments.length && i < max; i++) {
		var d = topicDocuments[i];
		var doc = documents[d[0]];
		rows.push("<tr><td>" + doc.source + "</td><td>" + doc.year
				+ "</td><td>" + doc.title + "</td><td>" + d[1].toFixed(2)
				+ "</td></tr>");
	}

	var table = $("<table class=\"tablesorter\"><thead><tr><th>Conf</th><th>Year</th><th>Title</th><th>Prob</th></tr></thead></table>")
			.append("<tbody/>").append(rows.join(""));

	table.tablesorter({
		theme : "bootstrap",
		widthFixed : true,
		headerTemplate : '{content} {icon}',
		widgets : [ "uitheme", "zebra" ],
		widgetOptions : {
			zebra : [ "even", "odd" ],
		}
	});

	return table;
}

function generateCountTable(topic) {
	var topicDocuments = topicMap[topic];
	var counts = {};
	for (var year = minYear; year <= maxYear; year++) {
		counts[year] = 0;
	}

	$.each(topicDocuments, function(i, d) {
		var doc = documents[d[0]];
		counts[doc.year] = counts[doc.year] + 1;
	})

	var headRow = $("<tr/>");
	var bodyRow = $("<tr/>");
	for (var year = minYear; year <= maxYear; year++) {
		headRow.append("<th>" + year + "</th>");
		bodyRow.append("<td>" + counts[year] + "</td>");
	}
	var table = $("<table class=\"table table-bordered table-condensed\"/>")
			.append("<thead/>").append("<tbody/>");
	table.children("thead").append(headRow);
	table.children("tbody").append(bodyRow);

	return table;
}

function constructTree(n) {
	$("#jstree").on("changed.jstree", function(e, data) {
		// show a pop-up when a node has been selected
		if (data.action == "select_node") {
			$("#topic-modal-title").html(
				data.node.text + " (" + data.node.id + ")")

			$("#topic-modal-body").html("")
	
			if (showTopicDocuments) {
				var topicDocuments = topicMap[data.node.id]
				max = 500
	
				$("#topic-modal-body").append("<h5>Number of documents by year:</h5>")
				$("#topic-modal-body").append(generateCountTable(data.node.id));
				$("#topic-modal-body").append("<h5>Document details (showing only the top " + max +"):</h5>")
				$("#topic-modal-body").append(generateTopicDocumentTable(
						data.node.id, max));
			} else {
				$("#topic-modal-body").append("<p>Document information is not available.</p>")
			}

			$("#topic-modal").modal()
		}
	}).jstree({
				"core" : {
					"data" : n,
					"themes" : {
						"icons" : false
					}
				},
				"search" : {
					"case_insensitive" : true,
					"show_only_matches" : true,
					"show_only_matches_children" : true
				},
				"plugins" : [ "search" ]
			});
}

// show the node within the specified range of levels.
// the nodes above the topmost level are discarded, while
// the nodes below the bottommost level are closed.
function showLevels(top, bottom) {
	var current = $('#jstree').jstree(true);
	if (typeof current != 'undefined' && current)
		current.destroy();

	for (var i = top; i > bottom; i--) {
		$.each(levels[i - 1], function(i, v) {
			v.state.opened = true;
		})
	}

	for (var i = bottom; i > 0; i--) {
		$.each(levels[i - 1], function(i, v) {
			v.state.opened = false;
		})
	}

	constructTree(levels[top - 1]);
}

function showAlert(message) {
	$("#alert-modal-message").html(message)
	$("#alert-modal").modal()
}

$(function() {
	topmost = levels.length
	bottommost = Math.max(1, levels.length - 1)

	// set the default values of the levels
	$("#top-input").val(topmost)
	$("#bottom-input").val(bottommost)

	$('[data-toggle="tooltip"]').tooltip()

	showLevels(topmost, bottommost);

	$('#level-button').click(function() {
		var top = getInputValue('#top-input', 1000000)
		var bottom = getInputValue('#bottom-input', 1)

		if (top > levels.length) {
			showAlert("The topmost level (left) cannot be larger than "
					+ levels.length + ".")
			$("#top-input").val(topmost)
		} else if (bottom < 1) {
			showAlert("The bottommost level (right) cannot be smaller than 1.")
			$("#bottom-input").val(1)
		} else if (top < bottom) {
			showAlert("The topmost level (left) cannot be smaller than the bottommost level (right).")
		} else {
			showLevels(top, bottom);
		}
	})

	$("#filter-button").click(function() {
		var searchString = $("#search-input").val();
		$('#jstree').jstree('search', searchString);
	});

	$("#clear-button").click(function() {
		$('#jstree').jstree(true).clear_search();
		$("#search-input").val("");
	});

	$.tablesorter.themes.bootstrap = {
		table : 'table table-bordered table-hover',
		caption : 'caption',
		header : 'bootstrap-header', 
		sortNone : '',
		sortAsc : '',
		sortDesc : '',
		active : '', 
		hover : '', 
		icons : '', 
		iconSortNone : 'bootstrap-icon-unsorted',
		iconSortAsc : 'glyphicon glyphicon-chevron-up',
		iconSortDesc : 'glyphicon glyphicon-chevron-down',
		filterRow : '', 
		footerRow : '',
		footerCells : '',
		even : '', 
		odd : '' 
	};

//	$('#topic-modal').on('hidden.bs.modal', function (e) {
//    	$("#jstree").focus()
//    })
});
