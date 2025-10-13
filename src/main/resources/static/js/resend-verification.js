$(function() {
    const $form = $('#resendForm');
    const $email = $('#email');
    const $submitBtn = $('.btn-submit');
    const $messageContainer = $('#message-container');

    // Получаем email из URL, если есть
    const urlParams = new URLSearchParams(window.location.search);
    const emailFromUrl = urlParams.get('email');
    if (emailFromUrl) {
        $email.val(emailFromUrl);
    }

    $form.on('submit', function(e) {
        e.preventDefault();
        
        const email = $email.val().trim();
        
        if (!email) {
            showError('Please enter your email address');
            return;
        }

        if (!isValidEmail(email)) {
            showError('Please enter a valid email address');
            return;
        }

        const originalText = $submitBtn.html();
        
        $submitBtn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Sending...');

        $.ajax({
            url: '/api/auth/resend-verification',
            method: 'POST',
            data: { email: email },
            success: function(response) {
                showSuccess(response.message || 'Verification email sent successfully! Please check your inbox.');
                $email.val(''); // Очищаем поле
            },
            error: function(xhr) {
                const errorMessage = xhr.responseText || 'Failed to send verification email. Please try again.';
                showError(errorMessage);
            },
            complete: function() {
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });

    // Очистка сообщений при вводе
    $email.on('input', function() {
        hideMessage();
    });

    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function showSuccess(message) {
        $messageContainer.html(`
            <div class="alert alert-success">
                <i class="bi bi-check-circle-fill me-2"></i>
                ${message}
            </div>
        `).show();
    }

    function showError(message) {
        $messageContainer.html(`
            <div class="alert alert-danger">
                <i class="bi bi-x-circle-fill me-2"></i>
                ${message}
            </div>
        `).show();
    }

    function hideMessage() {
        $messageContainer.hide();
    }

    // Добавляем CSS для анимации вращения
    $('<style>')
        .prop('type', 'text/css')
        .html('.spin { animation: spin 1s linear infinite; } @keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }')
        .appendTo('head');
});

