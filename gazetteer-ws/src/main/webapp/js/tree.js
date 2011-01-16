var jsonURL = './resources/location/';
function leafLabelClicked(event) {
    var loc = event.data;
    pswdebug("leafLabelClicked: " + $.param(loc));
    $("#selectedItem").text("Selected location: [" + loc.name + "] of id [" + loc.id + "]");
    return false;
}
function loadedNodeLabelClicked(event) {
    pswdebug("leafLabelClicked: " + (event.data? $.param(event.data) : 'null'));
    if (!event.data) {
        return false;
    }
    $("#selectedItem").empty();
    $(this).parent().toggleClass('tree-node-collapsed').
    toggleClass('tree-node-expanded');
    $(this).next("ul").toggle('slow');
    return false;
}
function collapsedNodeLabelClicked(event) {
    var loc = event.data;
    pswdebug("collapsedNodeLabelClicked: " + $.param(loc));
    $("#selectedItem").empty();
    var clickedLabel = $(this);
    //                alert('collapsedNodeLabelClicked: '+ $(this).text());
    clickedLabel.parent().removeClass('tree-node-collapsed').addClass('tree-node-loading');
    clickedLabel.unbind('click', collapsedNodeLabelClicked).removeClass('tree-clickable');
    var url = encodeURI(jsonURL + "cascade/" + loc.id + "/" + loc.cacheItemNumber);
    pswdebug('URL: ' + url);
    $.getJSON(url, function(data, textStatus, xhr) {
        cascadeInto(data, textStatus, xhr, clickedLabel, loc);
    });
    return false;
}
function safeSearch() {
    try {
        search($('#addressSearch').val());
    } catch(err) {
        alert('Error in search: ' + err);
    }
    return false;
}
function search(address) {
    if (!address) {
        pswdebug("can't search for empty address");
        return;
    }
    pswdebug("Searching for ["+ address + "]");
    // if previous tree build, kill it
    $("#searchResults, #selectedItem").empty();
    $("#searchResults").append($("<span/>", {
        "class": "tree-node-loading"
    }));
    var url = encodeURI(jsonURL + 'search/' + address);
    pswdebug('URL: ' + url);
    $.getJSON(url, function(data, textStatus, xhr) {
        gotSearchResult(data, textStatus, xhr);
    });
}
function gotSearchResult(data, textStatus, xhr) {
    pswdebug("gotSearchResult: text status: " + textStatus);
    //    pswdebug("xhr: " + JSON.stringify(xhr));
    if (!data) {
        alert('application error, gotSearchResults called with empty element');
    }
    $("#searchResults").empty();
    if (!data || !data.location) {
        $("#searchResults").text("No results found for [" + address + "].");
        return;
    }
    pswdebug("loaded " + data.location.length + " locations.");
    var subTree = buildTree(data.location);
    $("#searchResults").append(subTree);
    subTree.show('slow');
}
function cascadeInto(data, textStatus, xhr, el, loc) {
    pswdebug("cascadeInto: text status: " + textStatus);
    //    pswdebug("xhr: " + JSON.stringify(xhr));
    if (!data) {
        alert('application error, gotSearchResults called with empty element');
    }
    if (!data || !data.location) {
        $("#searchResults").text("No results found for [" + $.param(el) + "].");
        return;
    }
    pswdebug("loaded " + data.location.length + " locations.");
    var subTree = buildTree(data.location);
    el.after(subTree);
    subTree.show('slow');
    el.parent().removeClass('tree-node-loading').addClass('tree-node-expanded');
    el.click(loc, loadedNodeLabelClicked).addClass('tree-clickable');
}
function buildTree(locations) {
    // tree initially hidden, to use effect when showing to the user
    var el = $("<ul/>", {
        "class": "tree"
    }).hide();

    for (i in locations) {
        var loc = locations[i];
        pswdebug("Loc#" + i + ": " + $.param(loc));
        if ("true" != loc.hasChildren) {
            el.append(buildLeafLocation(loc));
        } else {
            el.append(buildCollapsedCascadeLocation(loc));
        //            el.append($("<li/>").text(loc.name + " cascade"));
        }
    }
    return el;
}
function buildCollapsedCascadeLocation(loc) {
    if (!loc) {
        alert("Empty location in buildCollapsedCascadeLocation");
    }
    var el = $("<li/>", {
        "class": "tree-node-collapsed"
    }).append($("<label/>", {
        "class": "tree-clickable"
    }).click(loc, collapsedNodeLabelClicked).append(loc.name));
    return el;
}
function buildLeafLocation(loc) {
    if (!loc) {
        alert("Empty location in buildLeafLocation");
    }
    var el = $("<li/>", {
        "class": "tree-leaf"
    }).append($("<label/>", {
        "class": "tree-clickable"
    }).click(loc, leafLabelClicked).
        append($("<a/>", {
            "href": "#"
        }).text(loc.name)));
    return el;
}

function pswdebug(message) {
//    var dbgEl = $("#pswdebug");
//    if (!dbgEl.length) {
//        dbgEl = $("<ol/>", {
//            "id": "pswdebug"
//        }).appendTo("body");
//    }
//    dbgEl.prepend($("<li>").text(message));
}