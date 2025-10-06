$(function() {
    // Cache jQuery selectors at the top level
    const $signupForm = $('#signupForm');
    const $firstName = $('#firstName');
    const $lastName = $('#lastName');
    const $email = $('#email');
    const $password = $('#password');
    const $confirmPassword = $('#confirmPassword');
    const $terms = $('#terms');
    const $submitBtn = $('.btn-submit');
    const $passwordFields = $('.password-field-wrapper');

    $signupForm.on('submit', function(e) {
        e.preventDefault();
        
        const firstName = $firstName.val();
        const lastName = $lastName.val();
        const email = $email.val();
        const password = $password.val();
        const confirmPassword = $confirmPassword.val();
        const terms = $terms.is(':checked');
        
        // Скрываем все ошибки при отправке
        hidePasswordError();
        hideConfirmPasswordError();
        hideEmailError();
        hideFirstNameError();
        hideLastNameError();
        
        // Проверяем только terms checkbox, остальное валидирует сервер
        if (!terms) {
            alert('Please accept the terms and conditions');
            return;
        }

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
            success: function() {
                // Successful registration - hide errors and redirect
                hideEmailError();
                window.location.href = `/auth/verify-email?email=${email}`;
            },
            error: function(xhr) {
                handleRegistrationError(xhr);
            },
            complete: function() {
                $submitBtn.prop('disabled', false).html(originalText);
            }
        });
    });


    // Email validation in real-time
    $email.on('blur', function() {
        const email = $(this).val();
        if (email && isValidEmail(email)) {
            checkEmailExists(email);
        } else {
            hideEmailError();
        }
    });

    // Clear errors when user starts typing
    $email.on('input', hideEmailError);
    $password.on('input', hidePasswordError);
    $confirmPassword.on('input', hideConfirmPasswordError);
    $firstName.on('input', hideFirstNameError);
    $lastName.on('input', hideLastNameError);

    // Password toggle functionality
    window.togglePassword = function(fieldId) {
        const input = document.getElementById(fieldId);
        const wrapper = input.closest('.password-field-wrapper');
        
        if (input.type === 'password') {
            input.type = 'text';
            wrapper.classList.add('show-password');
        } else {
            input.type = 'password';
            wrapper.classList.remove('show-password');
        }
    };

    // Add click event listeners to password field wrappers
    $(document).ready(function() {
        $passwordFields.on('click', function(e) {
            // Check if click was on the eye icon area (right side)
            const rect = this.getBoundingClientRect();
            const clickX = e.clientX - rect.left;
            const fieldWidth = rect.width;
            
            // If click is in the right 40px area, toggle password visibility
            if (clickX > fieldWidth - 40) {
                const input = this.querySelector('.password-input');
                togglePassword(input.id);
            }
        });
    });

    // Password strength indicator (optional enhancement)
    $password.on('input', function() {
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
        $password.removeClass('weak medium strong');
        
        if (strength >= 4) {
            $password.addClass('strong');
        } else if (strength >= 2) {
            $password.addClass('medium');
        } else if (strength >= 1) {
            $password.addClass('weak');
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

    function showEmailError(message = 'This email is already registered') {
        $('#email-error-text').text(message);
        $('#email-error').show();
        $email.addClass('error-border');
    }
    
    function hideEmailError() {
        $('#email-error').hide();
        $email.removeClass('error-border');
    }

    function showFirstNameError(message) {
        $('#firstName-error-text').text(message);
        $('#firstName-error').show();
        $firstName.addClass('error-border');
    }
    
    function hideFirstNameError() {
        $('#firstName-error').hide();
        $firstName.removeClass('error-border');
    }

    function showLastNameError(message) {
        $('#lastName-error-text').text(message);
        $('#lastName-error').show();
        $lastName.addClass('error-border');
    }
    
    function hideLastNameError() {
        $('#lastName-error').hide();
        $lastName.removeClass('error-border');
    }
    
    function showPasswordError(message) {
        $('#password-error-text').text(message);
        $('#password-error').show();
        $password.addClass('error-border');
    }
    
    function hidePasswordError() {
        $('#password-error').hide();
        $password.removeClass('error-border');
    }
    
    function showConfirmPasswordError(message) {
        $('#confirmPassword-error-text').text(message);
        $('#confirmPassword-error').show();
        $confirmPassword.addClass('error-border');
    }
    
    function hideConfirmPasswordError() {
        $('#confirmPassword-error').hide();
        $confirmPassword.removeClass('error-border');
    }

    // Обработка ошибок регистрации
    function handleRegistrationError(xhr) {
        if (xhr.status === 409) {
            handleEmailExistsError();
        } else if (xhr.status === 400) {
            handleValidationError(xhr);
        } else {
            alert('An error occurred during registration. Please try again.');
        }
    }

    function handleEmailExistsError() {
        showEmailError();
    }

    function handleValidationError(xhr) {
        try {
            const errorData = JSON.parse(xhr.responseText);
            if (Array.isArray(errorData)) {
                handleValidationArrayError(errorData);
            } else {
                handleTextError(xhr.responseText);
            }
        } catch {
            handleTextError(xhr.responseText);
        }
    }

    function handleValidationArrayError(errorData) {
        errorData.forEach(err => {
            switch (err.field) {
                case 'password':
                    showPasswordError(err.message);
                    break;
                case 'email':
                    showEmailError(err.message);
                    break;
                case 'firstName':
                    showFirstNameError(err.message);
                    break;
                case 'lastName':
                    showLastNameError(err.message);
                    break;
                case 'confirmPassword':
                    showConfirmPasswordError(err.message);
                    break;
                default:
                    console.error(`Unknown field error: ${err.field} - ${err.message}`);
            }
        });
    }

    function handleTextError(responseText) {
        if (!responseText) return;

        if (responseText.includes('compromised') || responseText.includes('data breaches')) {
            showPasswordError(responseText);
        } else if (responseText.includes('Passwords do not match')) {
            showConfirmPasswordError('Passwords do not match');
        } else if (responseText.includes('Password must be between 8 and 100 characters')) {
            showPasswordError('Password must be between 8 and 100 characters');
        } else if (responseText.includes('Password must contain at least one')) {
            showPasswordError('Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character');
        } else {
            alert('Invalid data provided. Please check your input.');
        }
    }
});
