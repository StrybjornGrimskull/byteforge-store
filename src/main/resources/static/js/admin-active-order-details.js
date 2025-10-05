// Admin Active Order Details JavaScript
// Get order ID from URL
function getOrderIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}

// Format date
function formatOrderDate(dateString) {
    const orderDate = new Date(dateString);
    return orderDate.toLocaleString('en-GB', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Format currency
function formatCurrency(amount) {
    return `$${amount?.toFixed(2) || '0.00'}`;
}

// Load order details
async function loadOrderDetails() {
    const orderId = getOrderIdFromUrl();
    const loadingSpinner = document.getElementById('loadingSpinner');
    const orderDetails = document.getElementById('orderDetails');

    try {
        // First try active orders endpoint
        let response = await fetch(`/api/orders/active/${orderId}`, {
            headers: {
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        // If not found in active orders, try archived orders
        if (response.status === 404) {
            response = await fetch(`/api/orders/archived/${orderId}`, {
                headers: {
                    'Accept': 'application/json'
                },
                credentials: 'include'
            });
        }

        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error response:', errorText);
            showError(`Failed to load order: ${response.status}`);
            return;
        }
        
        const order = await response.json();
        displayOrderDetails(order);
        
        // Hide loading spinner and show order details
        loadingSpinner.style.display = 'none';
        orderDetails.style.display = 'block';
        
    } catch (error) {
        console.error('Error loading order details:', error);
        loadingSpinner.style.display = 'none';
        showError('Failed to load order details: ' + error.message);
    }
}

// Function to display order details on the page
function displayOrderDetails(order) {
    
    // Update order summary
    document.getElementById('orderId').textContent = order.id || 'N/A';
    document.getElementById('orderDate').textContent = order.date ? formatOrderDate(order.date) : 'N/A';
    document.getElementById('orderStatus').textContent = 'Processing'; // Default status since it's not in the DTO
    document.getElementById('totalPrice').textContent = order.totalPrice ? formatCurrency(order.totalPrice) : '$0.00';

    // Update customer information
    document.getElementById('customerName').textContent = `${order.firstName || ''} ${order.lastName || ''}`.trim() || 'N/A';
    document.getElementById('customerEmail').textContent = order.email || 'N/A';
    document.getElementById('customerPhone').textContent = order.phoneNumber || 'N/A';
    document.getElementById('customerAddress').textContent = order.address || 'N/A';
    document.getElementById('customerCity').textContent = order.city || 'N/A';
    document.getElementById('customerPostalCode').textContent = order.postIndex || 'N/A';

    // Update order items
    const orderItemsContainer = document.getElementById('orderItemsTable');
    orderItemsContainer.innerHTML = ''; // Clear existing items

    if (order.orderProducts?.length > 0) {
        let subtotal = 0;
        
        order.orderProducts.forEach(item => {
            if (item?.product) {
                const itemPrice = item.product?.price || 0;
                const itemQuantity = item.quantity || 1;
                const itemTotal = itemPrice * itemQuantity;
                subtotal += itemTotal;
                
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <img src="${item.product.imageUrl || '/static/uploads/no-image.webp'}" 
                             alt="${item.product.name || 'Product'}" 
                             class="product-image">
                    </td>
                    <td>
                        <div class="product-info">
                            <h6>${item.product.name || 'Unknown Product'}</h6>
                            <small class="text-muted">SKU: ${item.product.sku || 'N/A'}</small>
                        </div>
                    </td>
                    <td class="text-center">
                        <span class="badge bg-primary">${itemQuantity}</span>
                    </td>
                    <td class="text-end price-cell">${formatCurrency(itemPrice)}</td>
                    <td class="text-end price-cell">${formatCurrency(itemTotal)}</td>
                `;
                orderItemsContainer.appendChild(row);
            }
        });

        // Update totals
        document.getElementById('subtotal').textContent = formatCurrency(subtotal);
        document.getElementById('total').textContent = formatCurrency(order.totalPrice || subtotal);
    } else {
        // Show no items message
        const row = document.createElement('tr');
        row.innerHTML = `
            <td colspan="5" class="text-center text-muted py-4">
                <i class="bi bi-inbox fs-1 d-block mb-2"></i>
                No items found in this order
            </td>
        `;
        orderItemsContainer.appendChild(row);
    }
}

// Complete order function
async function completeOrder() {
    const orderId = getOrderIdFromUrl();
    const completeBtn = document.getElementById('completeOrderBtn');
    
    if (!orderId) {
        showError('Order ID not found');
        return;
    }

    // Confirm action
    if (!confirm('Are you sure you want to complete this order?')) {
        return;
    }

    // Disable button and show loading state
    const originalText = completeBtn.innerHTML;
    completeBtn.disabled = true;
    completeBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Completing...';

    try {
        const response = await fetch(`/api/orders/${orderId}/complete`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        });

        if (response.ok) {
            // Show success message
            showSuccess('Order completed successfully!');
            
            // Update UI to reflect completed status
            document.getElementById('orderStatus').textContent = 'Completed';
            document.getElementById('orderStatus').className = 'badge status-badge status-completed';
            
            // Hide complete button
            completeBtn.style.display = 'none';
            
            // Redirect back to orders list after a delay
            setTimeout(() => {
                window.location.href = '/admin/dashboard/orders/active';
            }, 2000);
            
        } else {
            const errorText = await response.text();
            console.error(`HTTP error! status: ${response.status}, body: ${errorText}`);
            showError('Failed to complete order');
        }
        
    } catch (error) {
        console.error('Error completing order:', error);
        showError('Failed to complete order: ' + error.message);
        
        // Re-enable button
        completeBtn.disabled = false;
        completeBtn.innerHTML = originalText;
    }
}

// Show error message
function showError(message) {
    const orderDetails = document.getElementById('orderDetails');
    orderDetails.innerHTML = `
        <div class="error-message">
            <i class="bi bi-exclamation-triangle me-2"></i>
            ${message}
        </div>
    `;
    orderDetails.style.display = 'block';
}

// Show success message
function showSuccess(message) {
    const alertDiv = document.createElement('div');
    alertDiv.className = 'alert alert-success alert-dismissible fade show';
    alertDiv.innerHTML = `
        <i class="bi bi-check-circle me-2"></i>
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const container = document.querySelector('.container');
    container.insertBefore(alertDiv, container.firstChild);
    
    // Auto-hide after 5 seconds
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, 5000);
}

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    // Load order details when page loads
    loadOrderDetails();
    
    // Add event listener for complete order button
    document.getElementById('completeOrderBtn').addEventListener('click', completeOrder);
});
