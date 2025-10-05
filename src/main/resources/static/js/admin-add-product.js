// Admin Add Product JavaScript
// Form validation and dynamic loading
(function() {
    'use strict';
    
    const productForm = document.getElementById('productForm');
    const categorySelect = document.getElementById('productCategory');
    const brandSelect = document.getElementById('productBrand');
    const categorySpinner = document.getElementById('categorySpinner');
    const brandSpinner = document.getElementById('brandSpinner');
    
    
    // Load data on page load
    document.addEventListener('DOMContentLoaded', function() {
        loadCategories();
        loadBrands();
        
        // Add category change listener
        // Add event listeners to clear validation errors when user starts typing/selecting
        const basicFields = [
            'productName', 'productCategory', 'productBrand', 'originalPrice', 
            'warrantyMonths', 'releaseYear', 'shortDescription', 'productImage'
        ];
        
        basicFields.forEach(fieldId => {
            const field = document.getElementById(fieldId);
            if (field) {
                field.addEventListener('input', clearFieldError);
                field.addEventListener('change', clearFieldError);
            }
        });
        
        categorySelect.addEventListener('change', function() {
            showSpecificationsForCategory(this.value);
        });
        
        // Add image preview functionality
        const imageInput = document.getElementById('productImage');
        const imagePreview = document.getElementById('imagePreview');
        const imagePreviewRow = document.getElementById('imagePreviewRow');
        
            if (imageInput && imagePreview && imagePreviewRow) {
            imageInput.addEventListener('change', function(event) {
                const file = event.target.files[0];
                
                // Очищаем ошибки при выборе файла
                this.classList.remove('is-invalid');
                const errorDiv = this.parentNode.querySelector('.invalid-feedback');
                if (errorDiv) {
                    errorDiv.textContent = '';
                }
                
                if (file) {
                    // Validate file size (5MB max)
                    if (file.size > 5 * 1024 * 1024) {
                        this.classList.add('is-invalid');
                        let errorDiv = this.parentNode.querySelector('.invalid-feedback');
                        if (!errorDiv) {
                            errorDiv = document.createElement('div');
                            errorDiv.className = 'invalid-feedback';
                            this.parentNode.appendChild(errorDiv);
                        }
                        errorDiv.textContent = 'File size must be less than 5MB';
                        this.value = '';
                        imagePreviewRow.style.display = 'none';
                        return;
                    }
                    
                    // Validate file type - only WebP
                    if (file.type !== 'image/webp') {
                        this.classList.add('is-invalid');
                        let errorDiv = this.parentNode.querySelector('.invalid-feedback');
                        if (!errorDiv) {
                            errorDiv = document.createElement('div');
                            errorDiv.className = 'invalid-feedback';
                            this.parentNode.appendChild(errorDiv);
                        }
                        errorDiv.textContent = 'Please select a WebP image file only';
                        this.value = '';
                        imagePreviewRow.style.display = 'none';
                        return;
                    }
                    
                    // Show preview
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        imagePreview.src = e.target.result;
                        imagePreviewRow.style.display = 'block';
                    };
                    reader.readAsDataURL(file);
                } else {
                    imagePreviewRow.style.display = 'none';
                }
            });
        }
        
    });
    
    // Function to clear validation error for a specific field (глобальная область видимости)
    function clearFieldError(event) {
        const field = event.target;
        field.classList.remove('is-invalid');
        const errorDiv = field.parentNode.querySelector('.invalid-feedback');
        if (errorDiv) {
            errorDiv.textContent = '';
        }
    }
    
    // Функция для получения видимой секции спецификаций (глобальная область видимости)
    function getVisibleSpecificationSection() {
        const specSections = [
            'caseSpecsSection',
            'cpuSpecsSection',
            'gpuSpecsSection',
            'motherboardSpecsSection',
            'monitorSpecsSection',
            'ssdSpecsSection',
            'psuSpecsSection',
            'ramSpecsSection',
            'wiredKeyboardSpecsSection',
            'wirelessKeyboardSpecsSection',
            'wiredMouseSpecsSection',
            'wirelessMouseSpecsSection'
        ];
        
        for (const sectionId of specSections) {
            const section = document.getElementById(sectionId);
            if (section?.style.display !== 'none') {
                return section;
            }
        }
        
        return null;
    }
    
    // Load categories from API
    function loadCategories() {
        showSpinner(categorySpinner);
        
        fetch('/api/categories')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(categories => {
                populateSelect(categorySelect, categories, 'name');
                hideSpinner(categorySpinner);
            })
            .catch(error => {
                showError(categorySelect, 'Failed to load categories: ' + error.message);
                hideSpinner(categorySpinner);
            });
    }
    
    // Load brands from API
    function loadBrands() {
        showSpinner(brandSpinner);
        
        fetch('/api/brands')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(brands => {
                populateSelect(brandSelect, brands, 'name');
                hideSpinner(brandSpinner);
            })
            .catch(error => {
                showError(brandSelect, 'Failed to load brands: ' + error.message);
                hideSpinner(brandSpinner);
            });
    }
    
    
    // Generic function to populate select element
    function populateSelect(selectElement, dataArray, nameField) {
        // Clear existing options
        selectElement.innerHTML = '<option value="">Select an option</option>';
        
        // Add options from data
        dataArray.forEach(item => {
            const option = document.createElement('option');
            option.value = item.id;
            option.textContent = item[nameField];
            selectElement.appendChild(option);
        });
    }
    
    // Show error state in select element
    function showError(selectElement, errorMessage) {
        selectElement.innerHTML = `<option value="">${errorMessage}</option>`;
        selectElement.disabled = true;
        selectElement.classList.add('is-invalid');
        
        // Add error message below select
        const errorDiv = document.createElement('div');
        errorDiv.className = 'invalid-feedback';
        errorDiv.textContent = errorMessage;
        selectElement.parentNode.appendChild(errorDiv);
    }
    
    // Show loading spinner
    function showSpinner(spinnerElement) {
        if (spinnerElement) {
            spinnerElement.style.display = 'block';
        }
    }
    
    // Hide loading spinner
    function hideSpinner(spinnerElement) {
        if (spinnerElement) {
            spinnerElement.style.display = 'none';
        }
    }
    
    // Show specifications based on selected category
    function showSpecificationsForCategory(categoryId) {
        // Hide all specification sections
        hideAllSpecifications();
        
        if (!categoryId) {
            return;
        }
        
        // Get category name from select option
        const categoryOption = categorySelect.querySelector(`option[value="${categoryId}"]`);
        const categoryName = categoryOption ? categoryOption.textContent : '';
        
        // Show specifications based on category
        switch (categoryName.toLowerCase()) {
            case 'pc cases':
            case 'case':
                document.getElementById('caseSpecsSection').style.display = 'block';
                break;
            case 'central processing unit':
            case 'cpu':
            case 'processor':
                document.getElementById('cpuSpecsSection').style.display = 'block';
                break;
            case 'graphics cards':
            case 'gpu':
            case 'graphics card':
            case 'video card':
                document.getElementById('gpuSpecsSection').style.display = 'block';
                break;
            case 'motherboards':
            case 'motherboard':
            case 'mainboard':
                document.getElementById('motherboardSpecsSection').style.display = 'block';
                break;
            case 'monitors':
            case 'monitor':
            case 'display':
                document.getElementById('monitorSpecsSection').style.display = 'block';
                break;
            case 'solid state drives':
            case 'ssd':
            case 'solid state drive':
            case 'storage':
                document.getElementById('ssdSpecsSection').style.display = 'block';
                break;
            case 'power supplies':
            case 'psu':
            case 'power supply':
            case 'power supply unit':
                document.getElementById('psuSpecsSection').style.display = 'block';
                break;
            case 'memory':
            case 'ram':
            case 'random access memory':
            case 'memory modules':
                document.getElementById('ramSpecsSection').style.display = 'block';
                break;
            case 'wired keyboards':
            case 'wired keyboard':
            case 'keyboard':
                document.getElementById('wiredKeyboardSpecsSection').style.display = 'block';
                break;
            case 'wireless keyboards':
            case 'wireless keyboard':
                document.getElementById('wirelessKeyboardSpecsSection').style.display = 'block';
                break;
            case 'wired mice':
            case 'wired mouse':
            case 'mouse':
                document.getElementById('wiredMouseSpecsSection').style.display = 'block';
                break;
            case 'wireless mice':
            case 'wireless mouse':
                document.getElementById('wirelessMouseSpecsSection').style.display = 'block';
                break;
        }
        
        // Add event listeners to clear validation errors for specification fields
        setTimeout(() => {
            const visibleSpecSection = getVisibleSpecificationSection();
            if (visibleSpecSection) {
                const specInputs = visibleSpecSection.querySelectorAll('input, select, textarea');
                specInputs.forEach(input => {
                    input.addEventListener('input', clearFieldError);
                    input.addEventListener('change', clearFieldError);
                });
            }
        }, 100);
    }
    
    // Hide all specification sections
    function hideAllSpecifications() {
        const specSections = [
            'caseSpecsSection',
            'cpuSpecsSection',
            'gpuSpecsSection',
            'motherboardSpecsSection',
            'monitorSpecsSection',
            'ssdSpecsSection',
            'psuSpecsSection',
            'ramSpecsSection',
            'wiredKeyboardSpecsSection',
            'wirelessKeyboardSpecsSection',
            'wiredMouseSpecsSection',
            'wirelessMouseSpecsSection'
        ];
        
        specSections.forEach(sectionId => {
            const section = document.getElementById(sectionId);
            if (section) {
                section.style.display = 'none';
            }
        });
    }
    
    if (productForm) {
        // Form submission handler
        productForm.addEventListener('submit', function(event) {
            event.preventDefault();
            
            // Очищаем предыдущие ошибки валидации
            clearValidationErrors();
            
            const formData = new FormData();
            
            // Основные поля продукта
            formData.append('productName', document.getElementById('productName').value);
            formData.append('productCategory', document.getElementById('productCategory').value);
            formData.append('productBrand', document.getElementById('productBrand').value);
            formData.append('originalPrice', document.getElementById('originalPrice').value);
            formData.append('warrantyMonths', document.getElementById('warrantyMonths').value);
            formData.append('releaseYear', document.getElementById('releaseYear').value);
            
            // Добавляем stockQuantity только если заполнено
            const stockQuantity = document.getElementById('stockQuantity').value;
            if (stockQuantity?.trim() !== '') {
                formData.append('stockQuantity', stockQuantity);
            }
            
            formData.append('shortDescription', document.getElementById('shortDescription').value);
            
            // Добавляем productImage только если выбрано
            const productImageInput = document.getElementById('productImage');
            if (productImageInput.files.length > 0) {
                formData.append('productImage', productImageInput.files[0]);
            }
            
            // Собираем все поля спецификаций из видимой секции
            const visibleSpecSection = getVisibleSpecificationSection();
            if (visibleSpecSection) {
                const specInputs = visibleSpecSection.querySelectorAll('input, select, textarea');
                specInputs.forEach(input => {
                    if (input.name) {
                        formData.append(input.name, input.value.trim());
                    }
                });
            }
            
            console.log('Form data being sent:', Array.from(formData.entries()));
            
            fetch('/api/products', {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    // Пытаемся получить JSON ошибки
                    return response.text().then(text => {
                        try {
                            const errors = JSON.parse(text);
                            return Promise.reject(new Error(JSON.stringify(errors)));
                        } catch {
                            // Если не JSON, то это обычная текстовая ошибка
                            return Promise.reject(new Error(text));
                        }
                    });
                }
                return response.text();
            })
            .then(() => {
                // Success: redirect without alert
                window.location.href = '/admin/dashboard/products';
            })
            .catch(error => {
                hideGeneralError();
                try {
                    const errors = JSON.parse(error.message);
                    if (Array.isArray(errors)) {
                        showValidationErrors(errors);
                    } else if (errors?.message) {
                        showGeneralError(errors.message);
                    } else {
                        showGeneralError(String(error.message || 'Unexpected error'));
                    }
                } catch {
                    showGeneralError(String(error.message || 'Unexpected error'));
                }
            });
        });
        
        /**
         * @param {Array<{field: string, message: string}>} errors
         */
        function showValidationErrors(errors) {
            // Сначала очищаем все предыдущие ошибки
            clearValidationErrors();
            
            console.log('Validation errors received:', errors);
            
            errors.forEach(error => {
                if (!error || typeof error !== 'object') return;
                
                const { field = '', message = 'Invalid value' } = error;
                
                if (!field) return; // Пропускаем ошибки без поля
                
                // Ищем поле по имени (включая вложенные поля спецификаций)
                let input = document.querySelector(`[name="${field}"]`);
                
                // Если не найдено, пробуем найти по частичному имени (для вложенных полей)
                if (!input && field?.includes('.')) {
                    const fieldName = field.split('.').pop(); // Берем последнюю часть после точки
                    input = document.querySelector(`[name*="${fieldName}"]`);
                }
                
                // Если не найдено, ищем по ID
                if (!input) {
                    input = document.getElementById(field);
                }
                
                // Если поле найдено, показываем ошибку
                if (input) {
                    console.log(`Found field for error: ${field} -> ${input.name}`);
                    input.classList.add('is-invalid');
                    
                    // Находим или создаем div для ошибки
                    let errorDiv = input.parentNode.querySelector('.invalid-feedback');
                    if (!errorDiv) {
                        errorDiv = document.createElement('div');
                        errorDiv.className = 'invalid-feedback';
                        input.parentNode.appendChild(errorDiv);
                    }
                    errorDiv.textContent = message;
                } else {
                    console.log(`Field not found for error: ${field}`);
                }
            });
        }
        
        // Функция для очистки ошибок валидации
        function clearValidationErrors() {
            const inputs = document.querySelectorAll('.is-invalid');
            inputs.forEach(input => {
                input.classList.remove('is-invalid');
            });
            
            const errorDivs = document.querySelectorAll('.invalid-feedback');
            errorDivs.forEach(div => {
                div.textContent = '';
            });
            hideGeneralError();
        }

        function showGeneralError(message) {
            const box = document.getElementById('formGeneralError');
            if (box) {
                box.textContent = message;
                box.style.display = 'block';
            }
        }

        function hideGeneralError() {
            const box = document.getElementById('formGeneralError');
            if (box) {
                box.textContent = '';
                box.style.display = 'none';
            }
        }
        
    }
    
})();
