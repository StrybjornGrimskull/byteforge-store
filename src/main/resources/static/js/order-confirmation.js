// Order Confirmation JavaScript
$(function() {
    // Initialize order confirmation functionality
    initOrderConfirmation();
});

function initOrderConfirmation() {
    // Add any specific initialization for order confirmation page
    console.log('Order confirmation page initialized');
}

// Enhanced product item generation with dark theme
function generateProductItemHTML(product, item, itemTotal) {
    return `
        <div class="d-flex align-items-center mb-3 p-3 border rounded-3 shadow-sm product-item-dark">
            <div class="me-3 product-image-container">
                <img src="/uploads/${product.imageUrl}" alt="${product.name}" class="product-image">
            </div>
            <div class="flex-grow-1">
                <h6 class="mb-1 fw-bold">${product.name}</h6>
                <small>Quantity: ${item.quantity}</small>
            </div>
            <div class="text-end">
                <div class="fw-bold price">€${itemTotal.toFixed(2)}</div>
                <small>€${product.price.toFixed(2)} each</small>
            </div>
        </div>`;
}

// Enhanced order confirmation display
function showEnhancedOrderConfirmation(order, orderData) {
    // Hide checkout form and show confirmation
    $('#checkout-form-container').hide();
    $('#order-confirmation').show();
    
    // Update order details
    $('#order-number').text('#' + (order.id || '0000'));
    $('#order-date').text(new Date(order.createdAt || Date.now()).toLocaleString());
    $('#confirmation-email').text(orderData.email);
    
    // Update shipping address
    $('#shipping-address').html(`
        ${orderData.firstName} ${orderData.lastName}<br>
        ${orderData.address}<br>
        ${orderData.city}, ${orderData.postIndex}
    `);

    // Display ordered items
    if (order.orderProducts && order.orderProducts.length > 0) {
        let itemsHtml = '';
        let total = 0;

        order.orderProducts.forEach(item => {
            const product = item.product;
            const itemTotal = product.price * item.quantity;
            total += itemTotal;
            itemsHtml += generateProductItemHTML(product, item, itemTotal);
        });

        $('#ordered-items').html(itemsHtml);
        $('#order-total').text('€' + total.toFixed(2));
    }
    
    // Add success animation trigger
    setTimeout(() => {
        $('.success-animation').addClass('animate-in');
    }, 100);
}
