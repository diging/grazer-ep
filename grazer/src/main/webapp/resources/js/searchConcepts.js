$(document).ready(function() {
    $("#conceptQuery").autocomplete({
        minLength: 1,
        autofocus: true,
        source: function (request, response) {
            $.getJSON(root + '/searchPage/getConcepts', request, function(result) {
                response($.map(result, function(item) {
                    return {
                        label: item.word,
                        value: item.word,
                        tag_url: root + "/concept/" + item.id
                    }
                }));
            });
        },
        select: function(event, ui) {
            $("#conceptQuery").val(ui.item.label);
            $("#searchLink").html("");
            $("#searchLink").append('<a href="' + ui.item.tag_url + '"><i class="fa fa-search"></i></a>');
            return false;
        }
    });
});
