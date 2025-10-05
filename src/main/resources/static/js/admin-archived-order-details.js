// Admin Archived Order Details JavaScript
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
        // Try archived orders endpoint first (since this is archived order details)
        let response = await fetch(`/api/orders/archived/${orderId}`, {
            headers: {
                'Accept': 'application/json'
            },
            credentials: 'include'
        });

        // If not found in archived orders, try active orders
        if (response.status === 404) {
            response = await fetch(`/api/orders/active/${orderId}`, {
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
    if (!order) {
        showError('No order data received');
        return;
    }

    // Hide loading spinner and show order details
    document.getElementById('loadingSpinner').classList.add('d-none');
    document.getElementById('orderDetails').classList.remove('d-none');

    try {
        // Order Information
        document.getElementById('orderId').textContent = order.id || 'N/A';
        document.getElementById('orderDate').textContent = order.date ? formatOrderDate(order.date) : 'N/A';

        // Customer Information
        document.getElementById('customerName').textContent = `${order.firstName || ''} ${order.lastName || ''}`.trim() || 'Guest';
        document.getElementById('customerEmail').textContent = order.email || 'N/A';
        document.getElementById('customerPhone').textContent = order.phoneNumber || 'N/A';

        // Address Information
        document.getElementById('customerAddress').textContent = order.address || 'Not specified';
        document.getElementById('customerCity').textContent = order.city || 'Not specified';
        document.getElementById('customerPostalCode').textContent = order.postIndex || 'Not specified';

        // Update order items
        const orderItemsContainer = document.getElementById('orderItemsTable');
        orderItemsContainer.innerHTML = ''; // Clear existing items

        // Calculate subtotal from order items
        let subtotal = 0;

        if (order.orderProducts?.length > 0) {
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

        // Update totals
        const total = order.totalPrice || subtotal;
        document.getElementById('subtotalPrice').textContent = formatCurrency(subtotal);
        document.getElementById('finalTotalPrice').textContent = formatCurrency(total);
        
    } catch (error) {
        console.error('Error displaying order details:', error);
        showError('Error displaying order details. Please try again.');
    }
}

// Show error message
function showError(message) {
    document.getElementById('loadingSpinner').classList.add('d-none');
    document.getElementById('errorText').textContent = message;
    document.getElementById('errorMessage').style.display = 'block';
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

// Print order function
function printOrder() {
    window.print();
}

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    // Load order details when page loads
    loadOrderDetails();
});
