$(function() {
    // Extract email from URL parameters
    const urlParams = new URLSearchParams(window.location.search);
    const email = urlParams.get('email');
    
    if (email) {
        $('#email').val(email);
        $('#email-text').text(email);
    }

    // Handle resend verification form
    $('#resendForm').on('submit', function(e) {
        e.preventDefault();
        
        const email = $('#email').val();
        
        if (!email) {
            alert('Please enter your email address');
            return;
        }

        const $submitBtn = $('.btn-submit');
        
        $submitBtn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Sending...');

        // Use GET request to MVC endpoint instead of POST to API
        window.location.href = `/auth/resend-verification?email=${encodeURIComponent(email)}`;
    });

    // Add spinning animation for loading state
    $('<style>')
        .prop('type', 'text/css')
        .html('.spin { animation: spin 1s linear infinite; } @keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }')
        .appendTo('head');
});
