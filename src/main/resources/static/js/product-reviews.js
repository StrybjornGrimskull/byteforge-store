(function() {
    'use strict';
    
    // Get product ID from hidden input
    const productIdInput = document.getElementById('product-id');
    const productId = productIdInput ? productIdInput.value : null;
    
    if (!productId) {
        showError('Product ID not found');
        return;
    }

    // Load product and reviews data
    loadProductAndReviews();

    async function loadProductAndReviews() {
        try {
            // Load product info and reviews in parallel
            const [productResponse, reviewsResponse] = await Promise.all([
                fetch(`/api/products/${productId}`),
                fetch(`/api/reviews/product/${productId}`)
            ]);

            if (!productResponse.ok) {
                console.error(`Failed to load product: ${productResponse.status}`);
                return;
            }
            if (!reviewsResponse.ok) {
                console.error(`Failed to load reviews: ${reviewsResponse.status}`);
                return;
            }

            const product = await productResponse.json();
            const reviews = await reviewsResponse.json();

            // Update page content
            updateProductInfo(product);
            displayReviews(reviews);

        } catch {
            showError('Failed to load product information and reviews');
        }
    }

    function updateProductInfo(product) {
        // Update page title
        document.getElementById('page-title').textContent = `Reviews for ${product.name}`;

        // Update breadcrumbs
        const categoryBreadcrumb = document.getElementById('category-breadcrumb');
        const productBreadcrumb = document.getElementById('product-breadcrumb');
        const categoryLink = document.getElementById('category-link');
        const productLink = document.getElementById('product-link');

        if (product.categoryId && product.categoryName) {
            categoryLink.textContent = product.categoryName;
            categoryLink.href = `/products/list?categoryId=${product.categoryId}`;
            categoryBreadcrumb.style.display = 'block';
        }

        if (product.id && product.name) {
            productLink.textContent = product.name;
            productLink.href = `/products/details/${product.id}`;
            productBreadcrumb.style.display = 'block';
        }
    }

    function displayReviews(reviews) {
        const noReviews = document.getElementById('no-reviews');
        const reviewsContainer = document.getElementById('reviews-container');

        if (!reviews || reviews.length === 0) {
            noReviews.style.display = 'block';
            return;
        }

        // Display reviews
        reviewsContainer.innerHTML = '';
        reviews.forEach(review => {
            const reviewCard = createReviewCard(review);
            reviewsContainer.appendChild(reviewCard);
        });
        reviewsContainer.style.display = 'block';
    }

    function createReviewCard(review) {
        const card = document.createElement('div');
        card.className = 'card mb-4';

        // Create stars HTML
        const starsHtml = Array.from({length: 5}, (_, i) => 
            `<i class="bi ${i < review.rating ? 'bi-star-fill' : 'bi-star'}"></i>`
        ).join('');

        // Format date
        const date = new Date(review.createdAt);
        const formattedDate = date.toLocaleDateString('en-US', {
            month: 'short',
            day: 'numeric',
            year: 'numeric'
        });

        card.innerHTML = `
            <div class="card-header">
                <div class="d-flex align-items-center mb-2">
                    <h5 class="mb-0 me-2">${review.userFirstName || 'Anonymous User'}</h5>
                    <div class="rating" aria-label="Rating: ${review.rating} out of 5 stars">
                        ${starsHtml}
                        <span class="visually-hidden">${review.rating} out of 5 stars</span>
                    </div>
                </div>
                <small class="text-muted">${formattedDate}</small>
            </div>
            <div class="card-body">
                <p class="card-text">${review.text}</p>
            </div>
        `;

        return card;
    }

    function showError(message) {
        const errorMessage = document.getElementById('error-message');
        const errorText = document.getElementById('error-text');

        errorText.textContent = message;
        errorMessage.style.display = 'block';
    }
})();
