// Admin Add Brand JavaScript
// Form validation and API submission
(function() {
    'use strict';
    
    const brandForm = document.getElementById('brandForm');
    if (brandForm) {
        brandForm.addEventListener('submit', async function(event) {
            event.preventDefault();
            
            if (!brandForm.checkValidity()) {
                event.stopPropagation();
                brandForm.classList.add('was-validated');
                return;
            }
            
            brandForm.classList.add('was-validated');
            
            // Show loading state
            const submitBtn = brandForm.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '<i class="bi bi-hourglass-split me-1"></i>Creating...';
            submitBtn.disabled = true;
            
            try {
                // Prepare form data
                const formData = new FormData();
                formData.append('name', document.getElementById('brandName').value);
                
                const logoFile = document.getElementById('brandLogo').files[0];
                formData.append('logo', logoFile);
                
                // Send API request
                const response = await fetch('/api/brands', {
                    method: 'POST',
                    body: formData,
                    credentials: 'include'
                });
                
                if (response.ok) {
                    // Show success message
                    showAlert('success', 'Brand created successfully!');
                    // Reset form
                    brandForm.reset();
                    brandForm.classList.remove('was-validated');
                } else {
                    // Get error message from server
                    let errorMessage = 'Failed to create brand';
                    try {
                        const errorText = await response.text();
                        if (errorText) {
                            errorMessage = errorText;
                        }
                    } catch (e) {
                        console.error('Error reading error message:', e);
                    }
                    showAlert('error', errorMessage);
                }
                
            } catch (error) {
                console.error('Error creating brand:', error);
                showAlert('error', 'Failed to create brand. Please try again.');
            } finally {
                // Restore button state
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }
        });
    }
    
    function showAlert(type, message) {
        // Remove existing alerts
        const existingAlerts = document.querySelectorAll('.alert');
        existingAlerts.forEach(alert => alert.remove());
        
        // Create new alert
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type === 'success' ? 'success' : 'danger'} alert-dismissible fade show`;
        alertDiv.setAttribute('role', 'alert');
        
        const icon = type === 'success' ? 'bi-check-circle' : 'bi-exclamation-triangle';
        alertDiv.innerHTML = `
            <i class="bi ${icon} me-2"></i>
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        // Insert after page header
        const pageHeader = document.querySelector('.d-flex.justify-content-between.align-items-center.mb-4');
        pageHeader.parentNode.insertBefore(alertDiv, pageHeader.nextSibling);
        
        // Auto-dismiss success messages after 5 seconds
        if (type === 'success') {
            setTimeout(() => {
                if (alertDiv.parentNode) {
                    alertDiv.remove();
                }
            }, 5000);
        }
    }
})();
