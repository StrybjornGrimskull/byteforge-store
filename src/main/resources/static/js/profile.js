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
            'firstName': 'firstName',
            'lastName': 'lastName',
            'phone': 'phone',
            'city': 'city',
            'address': 'address',
            'postIndex': 'postIndex',
            'birthDate': 'birthDate'
        };
        return fieldMap[fieldName] || fieldName;
    }

    // Update country flag
    function updateCountryFlag() {
        const select = document.getElementById('phoneCountry');
        const flagImg = document.getElementById('countryFlag');
        const countryCode = select.value;
        
        const flagMap = {
            '380': 'ua', '1': 'us', '44': 'gb', '49': 'de', '33': 'fr', '39': 'it',
            '34': 'es', '31': 'nl', '46': 'se', '47': 'no', '45': 'dk', '41': 'ch',
            '43': 'at', '32': 'be', '48': 'pl', '420': 'cz', '421': 'sk', '36': 'hu',
            '40': 'ro', '359': 'bg', '385': 'hr', '386': 'si', '371': 'lv', '372': 'ee',
            '370': 'lt', '7': 'ru', '375': 'by', '373': 'md', '90': 'tr', '30': 'gr',
            '351': 'pt', '353': 'ie', '358': 'fi', '81': 'jp', '82': 'kr', '86': 'cn',
            '91': 'in', '55': 'br', '52': 'mx', '61': 'au', '64': 'nz', '27': 'za',
            '20': 'eg', '971': 'ae', '966': 'sa', '972': 'il', '60': 'my', '65': 'sg',
            '66': 'th', '84': 'vn', '63': 'ph', '62': 'id'
        };
        
        const flagCode = flagMap[countryCode] || 'ua';
        flagImg.src = `https://flagcdn.com/w20/${flagCode}.png`;
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
                    // Prefill phone parts
                    (function() {
                        const raw = (p.phone ?? '').toString();
                        const digits = raw.replace(/\D/g, '');
                        let cc = '', local = '';
                        if (digits.length >= 10) {
                            local = digits.slice(-10);
                            cc = digits.slice(0, digits.length - 10);
                        }
                        // Set country code in select
                        const countrySelect = document.getElementById('phoneCountry');
                        if (cc) {
                            const option = Array.from(countrySelect.options).find(opt => opt.value === cc);
                            if (option) {
                                countrySelect.value = cc;
                            } else {
                                countrySelect.value = '380'; // Default to Ukraine
                            }
                        } else {
                            countrySelect.value = '380'; // Default to Ukraine
                        }
                        updateCountryFlag(); // Update flag after setting value
                        document.getElementById('phonePart1').value = local ? local.slice(0,3) : '';
                        document.getElementById('phonePart2').value = local ? local.slice(3,6) : '';
                        document.getElementById('phonePart3').value = local ? local.slice(6,8) : '';
                        document.getElementById('phonePart4').value = local ? local.slice(8,10) : '';
                    })();
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
        // Build phone from parts
        const cc = document.getElementById('phoneCountry').value || '';
        const p1 = (document.getElementById('phonePart1').value || '').replace(/\D/g, '');
        const p2 = (document.getElementById('phonePart2').value || '').replace(/\D/g, '');
        const p3 = (document.getElementById('phonePart3').value || '').replace(/\D/g, '');
        const p4 = (document.getElementById('phonePart4').value || '').replace(/\D/g, '');
        let phoneVal = '';
        const allEmpty = !cc && !p1 && !p2 && !p3 && !p4;
        if (!allEmpty) {
            if (!(p1.length === 3 && p2.length === 3 && p3.length === 2 && p4.length === 2)) {
                showError('Complete phone number: 3-3-2-2 digits.');
                if (p1.length !== 3) document.getElementById('phonePart1').focus();
                else if (p2.length !== 3) document.getElementById('phonePart2').focus();
                else if (p3.length !== 2) document.getElementById('phonePart3').focus();
                else document.getElementById('phonePart4').focus();
                return;
            }
            phoneVal = `+${cc}${p1}${p2}${p3}${p4}`;
        }
        const dto = {
            firstName: document.getElementById('firstName').value?.trim() || null,
            lastName: document.getElementById('lastName').value?.trim() || null,
            phone: phoneVal,
            city: document.getElementById('city').value?.trim() || null,
            address: document.getElementById('address').value?.trim() || null,
            postIndex: (function(){
                const v = document.getElementById('postIndex').value;
                return v === '' ? null : Number(v);
            })(),
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

    // Enforce digits-only for specific inputs
    document.querySelectorAll('input.digit-only').forEach(inp => {
        inp.addEventListener('input', () => {
            const digits = inp.value.replace(/\D/g, '');
            const max = inp.getAttribute('maxlength');
            inp.value = max ? digits.slice(0, parseInt(max, 10)) : digits;
        });
    });

    // Add event listener for country select
    document.getElementById('phoneCountry').addEventListener('change', updateCountryFlag);

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
