$(function() {
    // Функция для показа сообщений об ошибках
    function showErrorMessage(message) {
        // Удаляем предыдущие сообщения об ошибках
        $('.alert-error').remove();
        
        // Создаем новое сообщение об ошибке
        const errorAlert = $(`
            <div class="alert alert-error">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                ${message}
            </div>
        `);
        
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
                if (xhr.status === 403) {
                    // Показываем сообщение об ошибке для неверифицированного пользователя
                    showErrorMessage('Invalid email or password. Please check your credentials and try again.');
                } else if (xhr.status === 401) {
                    // Показываем сообщение об ошибке вместо alert
                    showErrorMessage('Invalid email or password. Please check your credentials and try again.');
                } else {
                    showErrorMessage('An error occurred. Please try again.');
                }
            },
            complete: function() {
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });
});
