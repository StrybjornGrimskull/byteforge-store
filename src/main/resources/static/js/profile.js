document.addEventListener('DOMContentLoaded', function() {
    // Load Active Orders for the current user
    fetch('/api/orders/my-active')
        .then(res => res.json())
        .then(orders => {
            const block = document.getElementById('active-orders-block');
            if (!orders || orders.length === 0) {
                block.innerHTML = '<div class="alert alert-info">No active orders</div>';
            } else {
                block.innerHTML = '<div class="fw-bold mb-2">Active Orders:</div>' + orders.map(order => `
                    <div class="alert alert-info mb-2">
                        <b>Order #${order.id}</b><br>
                        Total: ${order.totalPrice} $<br>
                        Date: ${new Date(order.date).toLocaleString()}
                    </div>
                `).join('');
            }
        })
        .catch(() => {
            document.getElementById('active-orders-block').innerHTML =
                '<div class="alert alert-danger">Failed to load active orders</div>';
        });

    // Edit Profile Modal logic
    const editBtn = document.getElementById('editProfileBtn');
    const modalEl = document.getElementById('editProfileModal');
    const modal = new bootstrap.Modal(modalEl);
    const errorBox = document.getElementById('editProfileError');
    const successBox = document.getElementById('editProfileSuccess');

    function clearFieldErrors() {
        // Clear all field errors
        document.querySelectorAll('.invalid-feedback').forEach(el => {
            el.textContent = '';
            el.style.display = 'none';
        });
        // Remove invalid class from all inputs
        document.querySelectorAll('.form-control').forEach(el => {
            el.classList.remove('is-invalid');
        });
    }

    function showFieldError(fieldName, message) {
        // Convert friendly field names to actual field IDs
        const fieldIdMap = {
            'First Name': 'firstName',
            'Last Name': 'lastName',
            'Phone Number': 'phone',
            'City': 'city',
            'Address': 'address',
            'Postal Code': 'postIndex',
            'Birth Date': 'birthDate'
        };
        
        const actualFieldName = fieldIdMap[fieldName] || fieldName;
        const errorElement = document.getElementById(actualFieldName + '-error');
        const inputElement = document.getElementById(actualFieldName);
        
        if (errorElement && inputElement) {
            errorElement.textContent = message;
            errorElement.style.display = 'block';
            inputElement.classList.add('is-invalid');
        }
    }

    function showError(msg) {
        // Hide general error box
        errorBox.classList.add('d-none');
        successBox.classList.add('d-none');
        
        // Clear previous field errors
        clearFieldErrors();
        
        // Parse error message and show field-specific errors
        if (msg && typeof msg === 'string') {
            const lines = msg.split('\n');
            lines.forEach(line => {
                if (line.trim()) {
                    const colonIndex = line.indexOf(':');
                    if (colonIndex > 0) {
                        const fieldName = line.substring(0, colonIndex).trim();
                        const errorMessage = line.substring(colonIndex + 1).trim();
                        showFieldError(fieldName, errorMessage);
                    }
                }
            });
        }
    }
    
    function showSuccess() {
        clearFieldErrors();
        successBox.classList.remove('d-none');
        errorBox.classList.add('d-none');
    }
    
    function getUserFriendlyFieldName(fieldName) {
        const fieldMap = {
            'firstName': 'First Name',
            'lastName': 'Last Name',
            'phone': 'Phone Number',
            'city': 'City',
            'address': 'Address',
            'postIndex': 'Postal Code',
            'birthDate': 'Birth Date'
        };
        return fieldMap[fieldName] || fieldName;
    }


    if (editBtn) {
        editBtn.addEventListener('click', function(e) {
            e.preventDefault();
            // Fetch current profile
            fetch('/api/profile')
                .then(r => {
                    if (!r.ok) throw new Error('Failed to load profile');
                    return r.json();
                })
                .then(p => {
                    // Prefill form
                    document.getElementById('firstName').value = p.firstName ?? '';
                    document.getElementById('lastName').value = p.lastName ?? '';
                    document.getElementById('phone').value = p.phone ?? '';
                    document.getElementById('city').value = p.city ?? '';
                    document.getElementById('address').value = p.address ?? '';
                    document.getElementById('postIndex').value = p.postIndex ?? '';
                    document.getElementById('birthDate').value = p.birthDate ? p.birthDate : '';

                    errorBox.classList.add('d-none');
                    successBox.classList.add('d-none');
                    modal.show();
                })
                .catch(err => showError(err.message));
        });
    }

    // Save profile
    const saveBtn = document.getElementById('saveProfileBtn');
    saveBtn.addEventListener('click', function() {
        const dto = {
            firstName: document.getElementById('firstName').value?.trim() || null,
            lastName: document.getElementById('lastName').value?.trim() || null,
            phone: document.getElementById('phone').value?.trim() || null,
            city: document.getElementById('city').value?.trim() || null,
            address: document.getElementById('address').value?.trim() || null,
            postIndex: document.getElementById('postIndex').value?.trim() || null,
            birthDate: (function(){
                const v = document.getElementById('birthDate').value;
                return v === '' ? null : v;
            })()
        };

        fetch('/api/profile', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        })
        .then(r => {
            if (!r.ok) {
                return r.text().then(t => { 
                    // Обрабатываем ошибки валидации
                    let errorMessage = t || 'Failed to update profile';
                    
                    // Если это JSON с ошибками валидации, парсим его
                    try {
                        const errorData = JSON.parse(t);
                        if (Array.isArray(errorData)) {
                            errorMessage = errorData.map(err => {
                                const fieldName = getUserFriendlyFieldName(err.field);
                                return `${fieldName}: ${err.message}`;
                            }).join('\n');
                        }
                    } catch (e) {
                        // Если не JSON, используем текст как есть
                        errorMessage = t;
                    }
                    
                    throw new Error(errorMessage);
                });
            }
            // Optimistically update visible fields
            const fn = dto.firstName || '';
            const ln = dto.lastName || '';
            document.getElementById('view-fullname').textContent = (fn + ' ' + ln).trim() || document.getElementById('view-fullname').textContent;
            document.getElementById('view-phone').textContent = dto.phone || 'Not specified';
            document.getElementById('view-address').textContent = dto.address || 'Not specified';
            document.getElementById('view-city').textContent = dto.city || 'Not specified';
            const postIndexEl = document.getElementById('view-postIndex');
            if (postIndexEl) {
                if (dto.postIndex != null && !isNaN(dto.postIndex)) {
                    postIndexEl.textContent = ', ' + dto.postIndex;
                } else {
                    postIndexEl.textContent = '';
                }
            }
            showSuccess();
            setTimeout(() => modal.hide(), 700);
        })
        .catch(err => showError(err.message));
    });

    // Enforce digits-only for phone input
    const phoneInput = document.getElementById('phone');
    if (phoneInput) {
        phoneInput.addEventListener('input', () => {
            const digits = phoneInput.value.replace(/\D/g, '');
            phoneInput.value = digits;
        });
    }

    // Enforce digits-only for other digit-only inputs
    document.querySelectorAll('input.digit-only').forEach(inp => {
        inp.addEventListener('input', () => {
            const digits = inp.value.replace(/\D/g, '');
            const max = inp.getAttribute('maxlength');
            inp.value = max ? digits.slice(0, parseInt(max, 10)) : digits;
        });
    });


    // Clear field errors when user starts typing
    document.querySelectorAll('.form-control').forEach(input => {
        input.addEventListener('input', function() {
            const fieldName = this.id;
            const errorElement = document.getElementById(fieldName + '-error');
            if (errorElement) {
                errorElement.style.display = 'none';
                this.classList.remove('is-invalid');
            }
        });
    });
});
