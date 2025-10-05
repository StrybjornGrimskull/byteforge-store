// Admin Orders Management JavaScript

// Common functions
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

function createOrderCard(order, isArchived = false) {
    const statusBadge = isArchived 
        ? '<span class="badge bg-success">Completed</span>'
        : '<span class="badge bg-warning text-dark">Processing</span>';
        
    const actionButtons = isArchived
        ? `<button class="btn btn-sm btn-outline-primary view-order-btn" data-order-id="${order.id}">
                <i class="bi bi-eye"></i> View
           </button>`
        : `<div class="btn-group">
                <button class="btn btn-sm btn-outline-primary view-order-btn" data-order-id="${order.id}">
                    <i class="bi bi-eye"></i> View
                </button>
                <button class="btn btn-sm btn-outline-success complete-order-btn" data-order-id="${order.id}">
                    <i class="bi bi-check2"></i> Complete
                </button>
           </div>`;

    return `
        <div class="col-12 col-md-6 col-lg-4 col-xl-3">
            <div class="card order-card h-100 shadow-sm">
                <div class="card-header bg-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="fw-bold">#${order.id}</span>
                        ${statusBadge}
                    </div>
                    <div class="small text-muted">${formatOrderDate(order.date)}</div>
                </div>
                <div class="card-body d-flex flex-column">
                    <h6 class="card-title">${order.firstName || ''} ${order.lastName || ''}</h6>
                    <p class="card-text small text-muted mb-2">
                        <i class="bi bi-envelope"></i> ${order.email || 'No email provided'}
                    </p>
                    <p class="card-text small text-muted mb-2">
                        <i class="bi bi-telephone"></i> ${order.phoneNumber || 'No phone provided'}
                    </p>
                    <p class="card-text small text-muted mb-3">
                        <i class="bi bi-geo-alt"></i> ${order.city || ''}, ${order.address || ''}
                    </p>

                    <div class="mt-auto d-flex justify-content-between align-items-center">
                        <span class="fw-bold">${order.totalPrice ? order.totalPrice.toFixed(2) : '0.00'} $</span>
                        ${actionButtons}
                    </div>
                </div>
            </div>
        </div>`;
}

function showNoOrdersMessage(containerId, message = 'No orders found.') {
    const container = document.getElementById(containerId);
    container.innerHTML = `
        <div class="col-12 text-center py-4 text-muted">
            <i class="bi bi-inbox fs-1 d-block mb-2"></i>
            ${message}
        </div>`;
}

function viewOrderDetails(orderId) {
    window.location.href = `/admin/dashboard/order-details?id=${orderId}`;
}
