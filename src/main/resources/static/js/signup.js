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
            showConfirmPasswordError('Passwords do not match');
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
                    // Обрабатываем ошибки валидации
                    try {
                        const errorData = JSON.parse(xhr.responseText);
                        if (Array.isArray(errorData)) {
                            let errorMessage = '';
                            errorData.forEach(err => {
                                if (err.field === 'password') {
                                    showPasswordError(err.message);
                                } else if (err.field === 'email') {
                                    errorMessage += 'Email: ' + err.message + '\n';
                                } else if (err.field === 'firstName') {
                                    errorMessage += 'First Name: ' + err.message + '\n';
                                } else if (err.field === 'lastName') {
                                    errorMessage += 'Last Name: ' + err.message + '\n';
                                } else {
                                    errorMessage += err.field + ': ' + err.message + '\n';
                                }
                            });
                            if (errorMessage.trim()) {
                                alert(errorMessage.trim());
                            }
                        } else {
                            // Обрабатываем текстовые ошибки от сервера
                            const responseText = xhr.responseText;
                            if (responseText && responseText.includes('compromised')) {
                                showPasswordError('Password has been compromised. Please choose a different password.');
                            } else if (responseText && responseText.includes('Passwords do not match')) {
                                showConfirmPasswordError('Passwords do not match');
                            } else if (responseText && responseText.includes('Password must be between 8 and 100 characters')) {
                                showPasswordError('Password must be between 8 and 100 characters');
                            } else if (responseText && responseText.includes('Password must contain at least one')) {
                                showPasswordError('Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character');
                            } else {
                                alert('Invalid data provided. Please check your input.');
                            }
                        }
                    } catch (e) {
                        // Обрабатываем текстовые ошибки от сервера
                        const responseText = xhr.responseText;
                        if (responseText && responseText.includes('compromised')) {
                            showPasswordError('Password has been compromised. Please choose a different password.');
                        } else if (responseText && responseText.includes('Passwords do not match')) {
                            showConfirmPasswordError('Passwords do not match');
                        } else if (responseText && responseText.includes('Password must be between 8 and 100 characters')) {
                            showPasswordError('Password must be between 8 and 100 characters');
                        } else if (responseText && responseText.includes('Password must contain at least one')) {
                            showPasswordError('Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character');
                        } else {
                            alert('Invalid data provided. Please check your input.');
                        }
                    }
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
    
    // Clear password error when user starts typing
    $('#password').on('input', function() {
        hidePasswordError();
    });
    
    // Clear confirm password error when user starts typing
    $('#confirmPassword').on('input', function() {
        hideConfirmPasswordError();
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
    
    function showPasswordError(message) {
        $('#password-error-text').text(message);
        $('#password-error').show();
        $('#password').addClass('error-border');
    }
    
    function hidePasswordError() {
        $('#password-error').hide();
        $('#password').removeClass('error-border');
    }
    
    function showConfirmPasswordError(message) {
        $('#confirmPassword-error-text').text(message);
        $('#confirmPassword-error').show();
        $('#confirmPassword').addClass('error-border');
    }
    
    function hideConfirmPasswordError() {
        $('#confirmPassword-error').hide();
        $('#confirmPassword').removeClass('error-border');
    }
});
