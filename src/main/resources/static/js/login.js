$(function() {
    // Функция для показа сообщений об ошибках
    function showErrorMessage(message, errorType = 'error') {
        // Удаляем предыдущие сообщения об ошибках
        $('.alert-error, .alert-warning').remove();
        
        // Определяем класс и иконку в зависимости от типа ошибки
        const alertClass = errorType === 'disabled' ? 'alert-warning' : 'alert-error';
        const iconClass = errorType === 'disabled' ? 'bi-envelope-exclamation-fill' : 'bi-exclamation-triangle-fill';
        
        // Создаем новое сообщение об ошибке
        let errorAlert = $(`
            <div class="alert ${alertClass}">
                <i class="bi ${iconClass} me-2"></i>
                ${message}
            </div>
        `);
        
        // Если это ошибка неактивного аккаунта, добавляем кнопку для повторной отправки
        if (errorType === 'disabled') {
            errorAlert.append(`
                <div class="mt-2">
                    <a href="/auth/resend-verification" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-envelope me-1"></i>
                        Resend verification email
                    </a>
                </div>
            `);
        }
        
        // Вставляем сообщение перед формой
        $('#loginForm').before(errorAlert);
        
        // Прокручиваем к сообщению об ошибке
        $('html, body').animate({
            scrollTop: errorAlert.offset().top - 100
        }, 500);
    }
    
    $('#loginForm').on('submit', function(e) {
        e.preventDefault();
        
        const email = $('#email').val();
        const password = $('#password').val();
        
        if (!email || !password) {
            showErrorMessage('Please fill in all fields');
            return;
        }

        // Очищаем предыдущие сообщения об ошибках
        $('.alert-error').remove();
        
        const $submitBtn = $('.btn-submit');
        const originalText = $submitBtn.html();
        
        $submitBtn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Signing In...');

        $.ajax({
            url: '/api/auth/login',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                email: email,
                password: password
            }),
            success: function(response) {
                // Успешный логин - JWT токен уже установлен в HttpOnly cookie
                // Simply redirect to the main page
                window.location.href = '/';
            },
            error: function(xhr) {
                let errorMessage = 'An error occurred. Please try again.';
                let errorType = 'error';
                
                // Обработка HTTP статусов
                if (xhr.status === 403) {
                    // Account disabled (email not verified)
                    errorMessage = 'Your account is not activated. Please check your email and verify your account.';
                    errorType = 'disabled';
                } else if (xhr.status === 401) {
                    // Bad credentials
                    errorMessage = 'Invalid email or password. Please try again.';
                    errorType = 'badCredentials';
                }
                
                // Пытаемся получить детальную информацию из responseJSON
                if (xhr.responseJSON) {
                    if (xhr.responseJSON.message) {
                        errorMessage = xhr.responseJSON.message;
                    }
                    if (xhr.responseJSON.errorType) {
                        errorType = xhr.responseJSON.errorType;
                    }
                }
                
                showErrorMessage(errorMessage, errorType);
            },
            complete: function() {
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });
});
