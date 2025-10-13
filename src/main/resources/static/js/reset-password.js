$(function() {
    const $form = $('#resetPasswordForm');
    const $password = $('#password');
    const $confirmPassword = $('#confirmPassword');
    const $message = $('#password-message');
    const $submitBtn = $('#submit-btn');
    const $messageContainer = $('#message-container');

    // Получаем токен из URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (!token) {
        showError('Invalid reset link. Please request a new password reset.');
        $form.find('input, button').prop('disabled', true);
        return;
    }

    // Проверка совпадения паролей
    function checkPasswords() {
        if ($password.val() && $confirmPassword.val()) {
            if ($password.val() !== $confirmPassword.val()) {
                $message.html('<span class="text-danger">Passwords do not match</span>');
                $submitBtn.prop('disabled', true);
            } else {
                $message.html('<span class="text-success">Passwords match</span>');
                $submitBtn.prop('disabled', false);
            }
        } else {
            $message.html('');
            $submitBtn.prop('disabled', false);
        }
    }

    $password.on('keyup', checkPasswords);
    $confirmPassword.on('keyup', checkPasswords);

    // Обработка отправки формы
    $form.on('submit', function(e) {
        e.preventDefault();
        
        const password = $password.val();
        const confirmPassword = $confirmPassword.val();
        
        if (!password || !confirmPassword) {
            showError('Please fill in all fields');
            return;
        }

        if (password !== confirmPassword) {
            showError('Passwords do not match');
            return;
        }

        const originalText = $submitBtn.html();
        
        $submitBtn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Resetting...');

        $.ajax({
            url: '/api/auth/reset-password',
            method: 'POST',
            data: { 
                token: token,
                password: password,
                confirmPassword: confirmPassword
            },
            success: function(response) {
                showSuccess(response.message || 'Your password has been reset successfully!');
                
                // Перенаправляем на страницу логина через 2 секунды
                setTimeout(function() {
                    window.location.href = '/auth/login';
                }, 2000);
            },
            error: function(xhr) {
                let errorMessage = 'Failed to reset password. Please try again.';
                
                if (xhr.responseText) {
                    try {
                        const errorData = JSON.parse(xhr.responseText);
                        errorMessage = errorData.error || xhr.responseText;
                    } catch {
                        errorMessage = xhr.responseText;
                    }
                }
                
                showError(errorMessage);
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });

    function showSuccess(message) {
        $messageContainer.html(`
            <div class="alert alert-success">
                <i class="bi bi-check-circle-fill me-2"></i>
                ${message}
                <div class="mt-2">Redirecting to login page...</div>
            </div>
        `).show();
        
        // Скрываем форму после успеха
        $form.hide();
    }

    function showError(message) {
        $messageContainer.html(`
            <div class="alert alert-danger">
                <i class="bi bi-x-circle-fill me-2"></i>
                ${message}
            </div>
        `).show();
    }

    // Добавляем CSS для анимации вращения
    $('<style>')
        .prop('type', 'text/css')
        .html('.spin { animation: spin 1s linear infinite; } @keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }')
        .appendTo('head');
});
