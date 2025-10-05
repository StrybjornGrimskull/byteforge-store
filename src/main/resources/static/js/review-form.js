document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('reviewForm');
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    const errorText = document.getElementById('errorText');
    const submitButton = document.getElementById('submit-button');
    const ratingText = document.getElementById('ratingText');
    const ratingError = document.getElementById('ratingError');
    
    // Star rating functionality
    const starInputs = document.querySelectorAll('.star-input');
    const starLabels = document.querySelectorAll('.star-label');
    
    const ratingTexts = {
        1: 'Poor',
        2: 'Fair', 
        3: 'Good',
        4: 'Very Good',
        5: 'Excellent'
    };
    
    // Function to update star highlighting
    function updateStarHighlighting(rating) {
        starLabels.forEach((label, index) => {
            if (index < rating) {
                label.classList.add('active');
            } else {
                label.classList.remove('active');
            }
        });
    }

    // Function to clear all star highlighting
    function clearStarHighlighting() {
        starLabels.forEach(label => {
            label.classList.remove('active');
        });
    }

    starInputs.forEach(input => {
        input.addEventListener('change', function() {
            const rating = parseInt(this.value);
            updateStarHighlighting(rating);
            ratingText.textContent = `${rating} - ${ratingTexts[rating]}`;
            ratingError.style.display = 'none';
        });
    });
    
    // Hover effects for stars
    starLabels.forEach((label) => {
        label.addEventListener('mouseenter', function() {
            const rating = parseInt(this.dataset.rating);
            updateStarHighlighting(rating);
            ratingText.textContent = `${rating} - ${ratingTexts[rating]}`;
        });
    });
    
    // Reset rating text when mouse leaves
    document.querySelector('.star-rating').addEventListener('mouseleave', function() {
        const checkedInput = document.querySelector('.star-input:checked');
        if (checkedInput) {
            const rating = parseInt(checkedInput.value);
            updateStarHighlighting(rating);
            ratingText.textContent = `${rating} - ${ratingTexts[rating]}`;
        } else {
            clearStarHighlighting();
            ratingText.textContent = 'Select a rating';
        }
    });
    
    // Form validation
    form.addEventListener('submit', function(e) {
        // Check if rating is selected
        const selectedRating = document.querySelector('.star-input:checked');
        if (!selectedRating) {
            e.preventDefault();
            e.stopPropagation();
            ratingError.style.display = 'block';
            ratingText.style.color = 'rgba(239, 68, 68, 0.8)';
            return false;
        } else {
            ratingError.style.display = 'none';
            ratingText.style.color = 'rgba(255, 255, 255, 0.8)';
        }
        
        if (!form.checkValidity()) {
            e.preventDefault();
            e.stopPropagation();
        }
        
        form.classList.add('was-validated');
    }, false);
    
    // Form submission
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        if (!form.checkValidity()) {
            return;
        }
        
        // Get form data
        const formData = new FormData(form);
        const productId = document.getElementById('product-id').value;
        
        // Show loading state
        submitButton.disabled = true;
        submitButton.innerHTML = '<i class="bi bi-arrow-clockwise me-2"></i>Submitting...';
        
        // Hide any previous messages
        errorMessage.classList.add('d-none');
        successMessage.classList.add('d-none');
        
        // Submit review
        fetch('/api/reviews', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify({
                rating: parseInt(formData.get('rating')),
                text: formData.get('text'),
                product: { id: parseInt(productId) }
            }),
            credentials: 'same-origin'
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(err => {
                    throw new Error(err || 'Error submitting review');
                });
            }
            return response.text();
        })
        .then(() => {
            // Hide form and show success message
            form.style.display = 'none';
            successMessage.classList.remove('d-none');
            successMessage.scrollIntoView({ behavior: 'smooth' });
            
            // Redirect to my-orders page after 3 seconds
            setTimeout(() => {
                window.location.href = '/reviews/my-orders';
            }, 3000);
        })
        .catch(error => {
            // Show form again and error message
            form.style.display = 'block';
            errorText.textContent = error.message;
            errorMessage.classList.remove('d-none');
            
            // Scroll to error message
            errorMessage.scrollIntoView({ behavior: 'smooth' });
            
            // Hide error message after 5 seconds
            setTimeout(() => {
                errorMessage.classList.add('d-none');
            }, 5000);
        })
        .finally(() => {
            // Reset button state
            submitButton.disabled = false;
            submitButton.innerHTML = '<i class="bi bi-send me-1"></i>Submit Review';
        });
    });
    
    // Permission check removed as requested
});
