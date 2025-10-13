$(function() {
    // Получаем токен из URL
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    
    const $messageContainer = $('#message-container');
    const $loadingSpinner = $('#loading-spinner');
    
    if (!token) {
        showError('Invalid verification link. Please check your email and try again.');
        return;
    }

    // Показываем спиннер загрузки
    $loadingSpinner.show();
    
    // Отправляем запрос на верификацию
    $.ajax({
        url: '/api/auth/verify',
        method: 'POST',
        data: { token: token },
        success: function(response) {
            showSuccess(response.message || 'Email verified successfully! You can now log in.');
        },
        error: function(xhr) {
            const errorMessage = xhr.responseText || 'Verification failed. The link may be invalid or expired.';
            showError(errorMessage);
        },
        complete: function() {
            $loadingSpinner.hide();
        }
    });

    function showSuccess(message) {
        $messageContainer.html(`
            <div class="alert alert-success">
                <i class="bi bi-check-circle-fill me-2"></i>
                ${message}
            </div>
            <div class="text-center mt-3">
                <a href="/auth/login" class="btn btn-primary">
                    Go to Login
                </a>
            </div>
        `);
    }

    function showError(message) {
        $messageContainer.html(`
            <div class="alert alert-danger">
                <i class="bi bi-x-circle-fill me-2"></i>
                ${message}
            </div>
            <div class="text-center mt-3">
                <a href="/auth/resend-verification" class="btn btn-secondary">
                    Resend Verification Email
                </a>
            </div>
        `);
    }
});

