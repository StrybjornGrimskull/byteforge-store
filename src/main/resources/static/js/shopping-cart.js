$(function () {
    // Price formatting function
    function formatPrice(price) {
        if (!price) return '$0.00';
        return '$' + parseFloat(price).toFixed(2);
    }

    // Date formatting function
    function formatDate(dateString) {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
    }

    // Show empty cart state
    function showEmptyCart() {
        $('#cart-items-container').html(`
            <div class="empty-state text-center py-5">
                <div class="mb-4">
                    <i class="bi bi-cart" style="font-size: 4rem; color: white;"></i>
                </div>
                <h3 class="h4 mb-3" style="color: white;">Your shopping cart is empty</h3>
                <p class="mb-4" style="color: white;">Start adding products to your cart</p>
                <a class="btn btn-primary btn-lg px-4" href="/">
                    <i class="bi bi-arrow-right me-2"></i>Browse Products
                </a>
            </div>
        `);
    }

    // Disable checkout button
    function disableCheckoutButton() {
        $('#checkout-btn')
            .addClass('disabled')
            .attr('aria-disabled', 'true')
            .removeAttr('href')
            .css('pointer-events', 'none');
    }

    // Enable checkout button
    function enableCheckoutButton() {
        $('#checkout-btn')
            .removeClass('disabled')
            .attr('aria-disabled', 'false')
            .attr('href', '/checkout')
            .css('pointer-events', 'auto');
    }

    // Load cart items
    function loadCartItems() {
        $.ajax({
            url: '/api/shopping-cart',
            method: 'GET',
            dataType: 'json',
            success: function (response) {
                if (!response || response.length === 0) {
                    showEmptyCart();
                    return;
                }

                let html = '';
                response.forEach(item => {
                    const currentQuantity = item.quantity;
                    const maxQuantity = item.stockQuantity;

                    // Generate select options
                    let options = '';
                    for (let i = 1; i <= maxQuantity; i++) {
                        options += `<option value="${i}" ${currentQuantity === i ? 'selected' : ''}>${i}</option>`;
                    }

                    html += `
                        <div class="cart-item p-4" data-id="${item.productId}">
                            <div class="row">
                                <div class="col-md-2">
                                    <div class="product-img-container">
                                        <img src="${'/uploads/'+ item.imageUrl}" alt="${item.productName}" class="product-img">
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <h5 class="mb-2">${item.productName}</h5>
                                    <p class="mb-2" style="color: white;">Added: ${formatDate(item.addedDate)}</p>
                                    <button class="btn-remove remove-item-btn" data-id="${item.productId}">
                                        <i class="bi bi-trash me-1"></i> Remove
                                    </button>
                                </div>
                                <div class="col-md-4 text-end">
                                    ${maxQuantity === 0 ? 
                                        '<div class="d-flex flex-column justify-content-center align-items-center h-100"><h5 class="mb-2 text-danger">Product Unavailable</h5><small style="color: white;">Out of Stock</small></div>' :
                                        `<h5 class="mb-3 item-price" data-price="${item.price}">${formatPrice(item.price)}</h5>
                                        <div class="d-flex justify-content-end align-items-center">
                                            <select class="form-select quantity-select" style="width: 80px;">
                                                ${options}
                                            </select>
                                        </div>
                                        <div class="mt-2">
                                            <small style="color: white;">Available: ${maxQuantity}</small>
                                        </div>`
                                    }
                                </div>
                            </div>
                        </div>
                    `;
                });

                $('#cart-items-container').html(html);
                updateTotals();
            },
            error: function (xhr, status, error) {
                console.error('Error loading cart:', status, error);
                $('#cart-items-container').html(`
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i>
                        <strong>Error loading cart items</strong>. Please try again later.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <div class="text-center mt-4">
                        <a href="javascript:location.reload()" class="btn btn-outline-primary">
                            <i class="bi bi-arrow-repeat me-1"></i> Reload Page
                        </a>
                    </div>
                `);
                disableCheckoutButton();
            }
        });
    }

    // Update totals with discount and shipping calculation
    function updateTotals() {
        let subtotal = 0;
        const cartItems = $('.cart-item');

        // Calculate subtotal
        cartItems.each(function() {
            const $item = $(this);
            const $quantitySelect = $item.find('.quantity-select');
            const $priceElement = $item.find('.item-price');
            
            const quantity = parseInt($quantitySelect.val()) || 0;
            const price = parseFloat($priceElement.data('price')) || 0;
            const itemTotal = quantity * price;
            subtotal += itemTotal;
        });

        const total = subtotal;

        // Update summary section
        $('#subtotal').text(formatPrice(subtotal));
        $('#total').text(formatPrice(total));

        // Update checkout button state
        if (subtotal > 0) {
            enableCheckoutButton();
        } else {
            disableCheckoutButton();
        }
    }

    // Handler for quantity select change
    $(document).on('change', '.quantity-select', function() {
        const $item = $(this).closest('.cart-item');
        const productId = $item.data('id');
        const newQuantity = parseInt($(this).val());

        $.ajax({
            url: `/api/shopping-cart/${productId}/quantity?quantity=${newQuantity}`,
            method: 'PUT',
            success: function() {
                updateTotals();
            },
            error: function(xhr) {
                alert('Error updating quantity. Please try again.');
                loadCartItems(); // Reload to get correct values
            }
        });
    });

    // Handler for removing items
    $(document).on('click', '.remove-item-btn', function () {
        const productId = $(this).data('id');
        const $button = $(this);
        const $item = $(this).closest('.cart-item');

        $button.prop('disabled', true).html('<i class="bi bi-arrow-repeat"></i> Removing...');

        $.ajax({
            url: `/api/shopping-cart/${productId}`,
            method: 'DELETE',
            success: function() {
                $item.fadeOut(300, function() {
                    $(this).remove();
                    // Check if cart is empty
                    if ($('.cart-item').length === 0) {
                        showEmptyCart();
                    } else {
                        updateTotals();
                    }
                });
            },
            error: function(xhr) {
                $button.prop('disabled', false).html('<i class="bi bi-trash me-1"></i> Remove');
                if (xhr.status === 404) {
                    alert('Product not found in your cart');
                } else {
                    alert('Error removing product from cart');
                }
            }
        });
    });

    // Initialize cart loading
    loadCartItems();
    
    // Initially disable checkout button until cart is loaded
    disableCheckoutButton();
    
    // Update totals after a short delay to check cart state
    setTimeout(function() {
        updateTotals();
    }, 1000);
});
