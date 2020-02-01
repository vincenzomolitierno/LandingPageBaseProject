$(function() {
    $.scrollify({
        section : ".posti-section",
        scrollSpeed : 1000
    });
});

$(".scroll-next").click(function(e) {
    e.preventDefault();
    $.scrollify.next();
});

$(".scroll-prev").click(function(e) {
    e.preventDefault();
    $.scrollify.previous();
});