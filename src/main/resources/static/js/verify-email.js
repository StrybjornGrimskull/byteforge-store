$(function() {
    // Получаем email из URL параметров
    const urlParams = new URLSearchParams(window.location.search);
    const email = urlParams.get('email');
    
    if (email) {
        $('#email-text').text(email);
    }

    // Обработка кнопки "Resend Email"
    $('#resend-btn').on('click', function(e) {
        e.preventDefault();
        
        if (!email) {
            window.location.href = '/auth/resend-verification';
            return;
        }

        const $btn = $(this);
        const originalText = $btn.html();
        
        $btn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Sending...');

        $.ajax({
            url: '/api/auth/resend-verification',
            method: 'POST',
            data: { email: email },
            success: function(response) {
                alert(response.message || 'Verification email sent successfully!');
                $btn.html('<i class="bi bi-check-circle-fill me-2"></i>Email Sent!');
                
                // Возвращаем кнопку в исходное состояние через 3 секунды
                setTimeout(function() {
                    $btn.prop('disabled', false).html(originalText);
                }, 3000);
            },
            error: function(xhr) {
                const errorMessage = xhr.responseText || 'Failed to send verification email. Please try again.';
                alert(errorMessage);
                $btn.prop('disabled', false).html(originalText);
            }
        });
    });

    // Добавляем CSS для анимации вращения
    $('<style>')
        .prop('type', 'text/css')
        .html('.spin { animation: spin 1s linear infinite; } @keyframes spin { from { transform: rotate(0deg); } to { transform: rotate(360deg); } }')
        .appendTo('head');
});
