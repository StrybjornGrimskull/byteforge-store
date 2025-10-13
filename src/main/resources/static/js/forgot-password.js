$(function() {
    const $form = $('#forgotPasswordForm');
    const $email = $('#email');
    const $submitBtn = $('.btn-submit');
    const $messageContainer = $('#message-container');

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
            url: '/api/auth/forgot-password',
            method: 'POST',
            data: { email: email },
            success: function(response) {
                showSuccess(response.message || 'A password reset link has been sent to your email.');
                $email.val(''); // Очищаем поле после успеха
            },
            error: function(xhr) {
                const errorMessage = xhr.responseText || 'Failed to send reset link. Please try again.';
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

