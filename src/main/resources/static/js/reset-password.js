// Simple password confirmation check
const password = document.getElementById('password');
const confirmPassword = document.getElementById('confirmPassword');
const message = document.getElementById('password-message');
const button = document.getElementById('submit-btn');

function checkPasswords() {
    if (password.value && confirmPassword.value) {
        if (password.value !== confirmPassword.value) {
            message.innerHTML = '<span class="text-danger">Passwords do not match</span>';
            button.disabled = true;
        } else {
            message.innerHTML = '<span class="text-success">Passwords match</span>';
            button.disabled = false;
        }
    } else {
         message.innerHTML = '';
         button.disabled = false;
    }
}
password.addEventListener('keyup', checkPasswords);
confirmPassword.addEventListener('keyup', checkPasswords);
