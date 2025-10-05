// Admin Archived Orders JavaScript
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

function createOrderCard(order) {
    return `
        <div class="col-12 col-md-6 col-lg-4 col-xl-3 mb-3">
            <div class="card order-card h-100 shadow-sm">
                <div class="card-header order-header bg-white">
                    <div class="d-flex justify-content-between align-items-center">
                        <span class="fw-bold">#${order.id}</span>
                        <span class="badge archived-badge">Completed</span>
                    </div>
                    <div class="order-meta">${formatOrderDate(order.date)}</div>
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
                        <span class="order-price">${order.totalPrice ? order.totalPrice.toFixed(2) : '0.00'} $</span>
                        <button class="btn btn-sm btn-outline-primary view-order-btn"
                                data-order-id="${order.id}" title="View Details">
                            <i class="bi bi-eye"></i> View
                        </button>
                    </div>
                </div>
            </div>
        </div>`;
}

function showNoOrdersMessage(message = 'No archived orders found.') {
    return `
        <div class="col-12 no-orders">
            <i class="bi bi-archive fs-1 d-block mb-2"></i>
            ${message}
        </div>`;
}

function viewOrderDetails(orderId) {
    window.location.href = `/admin/dashboard/orders/archived/order-details?id=${orderId}`;
}

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

function updateLoadMoreButton(hasMorePages) {
    const loadMoreBtn = document.getElementById('loadMoreBtn');
    const loadMoreContainer = document.getElementById('loadMoreContainer');

    if (hasMorePages) {
        loadMoreBtn.innerHTML = '<i class="bi bi-arrow-down-circle me-2"></i>Load More Orders';
        loadMoreBtn.onclick = loadMoreOrders;
    } else {
        loadMoreBtn.innerHTML = '<i class="bi bi-arrow-up-circle me-2"></i>Back to Top';
        loadMoreBtn.onclick = scrollToTop;
    }
    loadMoreContainer.style.display = 'block';
}

let currentPage = 0;
let totalPages = 0;
let totalElements = 0;
const pageSize = 12;
let currentSearchQuery = '';

async function fetchOrders(page = 0, searchQuery = '') {
    const container = document.getElementById('ordersContainer');
    currentSearchQuery = searchQuery;

    if (page === 0) {
        container.innerHTML = `
            <div class="text-center py-4">
                <p class="mt-2 mb-0">Loading orders...</p>
            </div>
        `;
    }

    try {
        let url = `/api/orders/archived?page=${page}&size=${pageSize}`;
        if (searchQuery) {
            url += `&search=${encodeURIComponent(searchQuery)}`;
        }

        const response = await fetch(url);

        if (!response.ok) {
            console.error('Failed to fetch orders:', response.status);
            showNoOrdersMessage('Failed to load orders');
            return;
        }

        const data = await response.json();

        if (data.content.length === 0 && page === 0) {
            container.innerHTML = showNoOrdersMessage();
            document.getElementById('loadMoreContainer').style.display = 'none';
            return;
        }

        const ordersHTML = data.content.map(order => createOrderCard(order)).join('');

        if (page === 0) {
            container.innerHTML = `<div class="row g-3" id="ordersGrid">${ordersHTML}</div>`;
        } else {
            const ordersGrid = document.getElementById('ordersGrid');
            ordersGrid.insertAdjacentHTML('beforeend', ordersHTML);
        }

        totalPages = data.totalPages;
        totalElements = data.totalElements;
        currentPage = page;

        document.getElementById('ordersCount').textContent = totalElements;

        const hasMorePages = currentPage < totalPages - 1;
        updateLoadMoreButton(hasMorePages);

    } catch (error) {
        console.error('Error fetching orders:', error);
        if (page === 0) {
            container.innerHTML = showNoOrdersMessage('Error loading orders');
            document.getElementById('loadMoreContainer').style.display = 'none';
        }
    }
}

async function searchOrder(orderId) {
    const container = document.getElementById('ordersContainer');
    document.getElementById('loadMoreContainer').style.display = 'none';

    if (!orderId) {
        fetchOrders(0);
        return;
    }

    container.innerHTML = `
        <div class="text-center py-4">
            <p class="mt-2 mb-0">Searching for order #${orderId}...</p>
        </div>
    `;

    try {
        const response = await fetch(`/api/orders/archived/${orderId}`);

        if (!response.ok) {
            console.error('Order not found:', response.status);
            showNoOrdersMessage('Order not found');
            return;
        }

        const order = await response.json();
        container.innerHTML = `<div class="row g-3">${createOrderCard(order)}</div>`;
        document.getElementById('ordersCount').textContent = 1;
    } catch (error) {
        console.error('Error searching order:', error);
        container.innerHTML = showNoOrdersMessage('Order not found');
        document.getElementById('ordersCount').textContent = 0;
    }
}

async function loadMoreOrders() {
    const loadMoreBtn = document.getElementById('loadMoreBtn');
    loadMoreBtn.disabled = true;
    loadMoreBtn.innerHTML = 'Loading...';

    try {
        await fetchOrders(currentPage + 1, currentSearchQuery);
    } catch (error) {
        console.error('Error loading more orders:', error);
    } finally {
        loadMoreBtn.disabled = false;
        const hasMorePages = currentPage < totalPages - 1;
        updateLoadMoreButton(hasMorePages);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    document.addEventListener('click', function(e) {
        if (e.target.closest('.view-order-btn')) {
            const button = e.target.closest('.view-order-btn');
            const orderId = button.getAttribute('data-order-id');
            viewOrderDetails(orderId);
        }
    });

    document.getElementById('searchOrderBtn').addEventListener('click', () => {
        const orderId = document.getElementById('orderSearchInput').value.trim();
        searchOrder(orderId);
    });

    document.getElementById('showAllOrdersBtn').addEventListener('click', () => {
        document.getElementById('orderSearchInput').value = '';
        fetchOrders(0);
    });

    document.getElementById('orderSearchInput').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            const orderId = e.target.value.trim();
            searchOrder(orderId);
        }
    });

    fetchOrders(0);
});
