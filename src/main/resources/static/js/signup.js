$(function() {
    $('#signupForm').on('submit', function(e) {
        e.preventDefault();
        
        const firstName = $('#firstName').val();
        const lastName = $('#lastName').val();
        const email = $('#email').val();
        const password = $('#password').val();
        const confirmPassword = $('#confirmPassword').val();
        const terms = $('#terms').is(':checked');
        
        if (!firstName || !lastName || !email || !password || !confirmPassword) {
            alert('Please fill in all fields');
            return;
        }

        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }

        if (!terms) {
            alert('Please accept the terms and conditions');
            return;
        }

        const $submitBtn = $('.btn-submit');
        const originalText = $submitBtn.html();
        
        $submitBtn.prop('disabled', true)
            .html('<i class="bi bi-arrow-repeat spin me-2"></i>Creating Account...');

        $.ajax({
            url: '/api/auth/register',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                confirmPassword: confirmPassword
            }),
            success: function(response) {
                // Successful registration - hide errors and redirect
                hideEmailError();
                window.location.href = `/auth/verify-email?email=${email}`;
            },
            error: function(xhr) {
                if (xhr.status === 409) {
                    // Показываем ошибку под полем email
                    $('#email-error').show();
                    $('#email').addClass('error-border');
                    alert('Email already exists. Please use a different email or sign in.');
                } else if (xhr.status === 400) {
                    alert('Invalid data provided. Please check your input.');
                } else {
                    alert('An error occurred during registration. Please try again.');
                }
            },
            complete: function() {
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });

    // Email validation in real-time
    $('#email').on('blur', function() {
        const email = $(this).val();
        if (email && isValidEmail(email)) {
            checkEmailExists(email);
        } else {
            hideEmailError();
        }
    });

    // Clear email error when user starts typing
    $('#email').on('input', function() {
        hideEmailError();
    });

    // Password strength indicator (optional enhancement)
    $('#password').on('input', function() {
        const password = $(this).val();
        const strength = getPasswordStrength(password);
        updatePasswordStrengthIndicator(strength);
    });

    function getPasswordStrength(password) {
        let score = 0;
        if (password.length >= 8) score++;
        if (/[a-z]/.test(password)) score++;
        if (/[A-Z]/.test(password)) score++;
        if (/\d/.test(password)) score++;
        if (/[^A-Za-z0-9]/.test(password)) score++;
        return score;
    }

    function updatePasswordStrengthIndicator(strength) {
        // Remove existing strength classes
        $('#password').removeClass('weak medium strong');
        
        if (strength >= 4) {
            $('#password').addClass('strong');
        } else if (strength >= 2) {
            $('#password').addClass('medium');
        } else if (strength >= 1) {
            $('#password').addClass('weak');
        }
    }

    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function checkEmailExists(email) {
        $.ajax({
            url: '/api/auth/check-email',
            method: 'POST',
            data: { email: email },
            success: function(exists) {
                if (exists) {
                    showEmailError();
                } else {
                    hideEmailError();
                }
            },
            error: function() {
                // В случае ошибки API, скрываем ошибку
                hideEmailError();
            }
        });
    }

    function showEmailError() {
        $('#email-error').show();
        $('#email').addClass('error-border');
    }

    function hideEmailError() {
        $('#email-error').hide();
        $('#email').removeClass('error-border');
    }
});
