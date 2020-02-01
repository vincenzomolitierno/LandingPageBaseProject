
$(function() {
    $('a[href*=\\#]').on('click', function(e) {
        // e.preventDefault();
        $('html, body').animate({ scrollTop: $($(this).attr('href')).offset().top}, 1000, 'linear');
    });
});

window.onload = function() {
        if (window.jQuery) {
            // jQuery is loaded
            alert("Yeah!");
        } else {
            // jQuery is not loaded
            alert("Doesn't Work");
        }
    }