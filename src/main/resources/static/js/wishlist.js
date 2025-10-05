// Utility functions
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function updateItemCount(count) {
    $('#item-count').text(count);
}

function showEmptyWishlist() {
    $('#wishlist-container').html(`
        <div class="empty-state text-center py-5">
            <div class="mb-4">
                <i class="bi bi-heart text-muted" style="font-size: 4rem;"></i>
            </div>
            <h3 class="h4 text-muted mb-3">Your wishlist is empty</h3>
            <p class="text-muted mb-4">Start adding products to create your perfect wishlist</p>
            <a class="btn btn-primary btn-lg px-4" href="/">
                <i class="bi bi-arrow-right me-2"></i>Browse Products
            </a>
        </div>
    `);
    updateItemCount(0);
}

function showError(message) {
    $('#wishlist-container').html(`
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            <strong>${message || 'Error loading wishlist'}</strong>. Please try again later.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <div class="text-center mt-4">
            <a href="javascript:location.reload()" class="btn btn-outline-primary">
                <i class="bi bi-arrow-repeat me-1"></i> Reload Page
            </a>
        </div>
    `);
}

// Cart functions
function getCartProductIds() {
    return new Promise((resolve) => {
        $.ajax({
            url: '/api/shopping-cart',
            method: 'GET',
            dataType: 'json',
            success: function(cartItems) {
                const cartProductIds = cartItems.map(item => item.productId);
                resolve(cartProductIds);
            },
            error: function() {
                resolve([]);
            }
        });
    });
}

// Product card template
function createProductCard(item, isInCart) {
    const quantity = parseInt(item.quantity) || 0;
    const isOutOfStock = quantity <= 0;

    const stockBadge = isOutOfStock ?
        '<span class="badge bg-danger mb-2">Out of Stock</span>' :
        '';

    let buttonHtml;
    if (isInCart) {
        buttonHtml = `
            <button class="btn btn-secondary btn-sm cart-btn" disabled>
                <i class="bi bi-cart-check me-1"></i> In Cart
            </button>`;
    } else if (isOutOfStock) {
        buttonHtml = `
            <button class="btn btn-secondary btn-sm cart-btn" disabled>
                <i class="bi bi-cart-x me-1"></i> Out of Stock
            </button>`;
    } else {
        buttonHtml = `
            <button class="btn btn-add-to-cart btn-sm cart-btn"
                    data-product-id="${item.productId}">
                <i class="bi bi-cart-plus me-1"></i> Add to Cart
            </button>`;
    }

    return `
        <div class="col" data-id="${item.productId}">
            <div class="card wishlist-card h-100">
                <div class="product-img-container">
                    <img src="${'/uploads/' + item.imageUrl}" class="product-img" alt="${item.productName}">
                    <div class="remove-btn-overlay">
                        <i class="bi bi-trash remove-btn" style="color: #ff4757; font-size: 1.2rem; cursor: pointer;"></i>
                    </div>
                </div>
                <div class="card-body">
                    <div class="card-content">
                        <h5 class="card-title">${item.productName}</h5>
                        <p class="product-id d-none">${item.productId}</p>
                        <p class="added-date mb-3">
                            <i class="bi bi-calendar me-1"></i>
                            ${formatDate(item.addedDate)}
                        </p>
                        ${stockBadge}
                    </div>
                    <div class="card-actions">
                        <div class="d-grid gap-2">
                            ${buttonHtml}
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
}

function renderWishlist(wishlist, cartProductIds) {
    if (!wishlist || wishlist.length === 0) {
        showEmptyWishlist();
        return;
    }

    let cards = '';
    wishlist.forEach(item => {
        const isInCart = cartProductIds.includes(item.productId);
        cards += createProductCard(item, isInCart);
    });
    
    const html = `<div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4 g-4">${cards}</div>`;
    $('#wishlist-container').html(html);
    updateItemCount(wishlist.length);
}

// Main function to load wishlist
async function loadWishlist() {
    try {
        const wishlist = await $.ajax({
            url: '/api/wishlist',
            method: 'GET',
            dataType: 'json'
        });
        
        const cartProductIds = await getCartProductIds();
        renderWishlist(wishlist, cartProductIds);
    } catch (jqXHR) {
        const message = jqXHR.status === 401 ? 'Please log in to see your wishlist' : 'Failed to load wishlist data.';
        showError(message);
    }
}

// Remove item from wishlist
function removeFromWishlist(productId, $card) {
    $.ajax({
        url: `/api/wishlist/${productId}`,
        method: 'DELETE',
        success: function() {
            $card.fadeOut(300, function() {
                $(this).remove();
                const remainingCount = $('.wishlist-card').length;
                updateItemCount(remainingCount);
                if (remainingCount === 0) showEmptyWishlist();
            });
        },
        error: function(xhr) {
            const $button = $card.find('.remove-btn');
            $button.prop('disabled', false).html('<i class="bi bi-trash"></i>');
            showError(xhr.status === 404 ? 'Product not found in your wishlist' : 'Error removing product');
        }
    });
}

// Add item to cart
function addToCart(productId, $button) {
    $button.prop('disabled', true).html('<i class="bi bi-arrow-repeat"></i> Adding...');

    $.ajax({
        url: `/api/shopping-cart?productId=${productId}`,
        method: 'POST',
        success: function() {
            $button.removeClass('btn-add-to-cart').addClass('btn-secondary')
                .html('<i class="bi bi-cart-check me-1"></i> In Cart')
                .prop('disabled', true);
        },
        error: function(xhr) {
            $button.html('<i class="bi bi-cart-plus me-1"></i> Add to Cart')
                .prop('disabled', false);
            showError(xhr.status === 401 ? 'Please login to add items to cart' : 'Error adding product to cart');
        }
    });
}

// Event handlers
function setupEventHandlers() {
    $(document).on('click', '.remove-btn', function () {
        const $card = $(this).closest('.col');
        const productId = $card.find('.product-id').text();
        const $button = $(this);

        $button.prop('disabled', true).html('<i class="bi bi-arrow-repeat"></i>');
        removeFromWishlist(productId, $card);
    });

    $(document).on('click', '.cart-btn:not(:disabled)', function () {
        const productId = $(this).data('product-id');
        addToCart(productId, $(this));
    });
}

// Initialize
$(document).ready(function() {
    setupEventHandlers();
    loadWishlist();
});
