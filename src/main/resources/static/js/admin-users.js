// Admin Users Management JavaScript
// Global variables
let currentPage = 0;
let totalPages = 0;
let totalUsers = 0;
let usersPerPage = 20; // Spring Data JPA default page size

// Type definitions for Bootstrap
const bootstrap = window.bootstrap || {};

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    loadUsers();
    setupEventListeners();
});

function setupEventListeners() {
    // Search functionality with debounce
    let searchTimeout;
    document.getElementById('searchInput').addEventListener('input', function() {
        clearTimeout(searchTimeout);
        searchTimeout = setTimeout(() => {
            currentPage = 0;
            loadUsers();
        }, 500); // Wait 500ms after user stops typing
    });

    // Role filter
    document.getElementById('roleFilter').addEventListener('change', function() {
        currentPage = 0;
        loadUsers();
    });

    // Refresh button
    document.getElementById('refreshBtn').addEventListener('click', function() {
        currentPage = 0;
        loadUsers();
    });

    // Clear filters button
    document.getElementById('clearFiltersBtn').addEventListener('click', function() {
        document.getElementById('searchInput').value = '';
        document.getElementById('roleFilter').value = '';
        currentPage = 0;
        loadUsers();
    });

    // Save roles button
    document.getElementById('saveRolesBtn').addEventListener('click', function() {
        saveUserRoles();
    });
}

function loadUsers() {
    const searchTerm = document.getElementById('searchInput').value.trim();
    const roleFilter = document.getElementById('roleFilter').value;
    
    // Show loading state
    showLoadingState();

    // Build query parameters
    const params = new URLSearchParams({
        page: currentPage.toString(),
        size: usersPerPage.toString(),
        sort: 'id,asc'
    });
    
    if (searchTerm) {
        params.append('search', searchTerm);
    }
    
    if (roleFilter) {
        params.append('role', roleFilter);
    }

    // Fetch data from server
    fetch(`/api/customers/role-management?${params.toString()}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to load users');
            }
            return response.json();
        })
        .then(data => {
            displayUsers(data);
            updatePagination(data);
            updateUserCount(data);
        })
        .catch(error => {
            console.error('Error loading users:', error);
            showErrorState();
        });
}

function displayUsers(data) {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = '';

    if (data.content?.length > 0) {
        data.content.forEach(user => {
            const row = createUserRow(user);
            tbody.appendChild(row);
        });
    } else {
        tbody.innerHTML = `
            <tr>
                <td colspan="4" class="text-center text-muted py-4">
                    <i class="bi bi-people fs-1 d-block mb-2"></i>
                    No users found
                </td>
            </tr>
        `;
    }
}

function createUserRow(user) {
    const row = document.createElement('tr');
    const searchTerm = document.getElementById('searchInput')?.value?.trim().toLowerCase() || '';
    
    // Create role badges with different colors
    const roleBadges = (user.authorities || []).map(role => {
        let badgeClass = 'bg-secondary';
        if (role === 'ROLE_ADMIN') {
            badgeClass = 'bg-danger';
        } else if (role === 'ROLE_MODERATOR') {
            badgeClass = 'bg-warning text-dark';
        } else if (role === 'ROLE_PRODUCT_MANAGER') {
            badgeClass = 'bg-success';
        }
        
        return `<span class="badge ${badgeClass} role-badge">${role}</span>`;
    }).join('');

    // Highlight search term in email
    let highlightedEmail = user.email;
        if (searchTerm && user.email?.toLowerCase().includes(searchTerm)) {
        const regex = new RegExp(`(${searchTerm})`, 'gi');
        highlightedEmail = user.email.replace(regex, '<mark>$1</mark>');
    }

    row.innerHTML = `
        <td>${user.id}</td>
        <td>${highlightedEmail}</td>
        <td>${roleBadges}</td>
        <td>
            <button class="btn btn-sm btn-outline-primary btn-role" onclick="openRoleModal('${user.id}', '${user.email}', ${JSON.stringify(user.authorities).replace(/"/g, '&quot;')})">
                <i class="bi bi-gear me-1"></i> Manage
            </button>
        </td>
    `;
    
    return row;
}

function openRoleModal(userId, userEmail, currentRoles) {
    // Set modal data
    const modalUserEmail = document.getElementById('modalUserEmail');
    if (modalUserEmail) {
        modalUserEmail.value = userEmail;
    }
    
    // Display current roles with different colors
    const currentRolesContainer = document.getElementById('currentRoles');
    if (currentRolesContainer) {
        currentRolesContainer.innerHTML = (currentRoles || []).map(role => {
            let badgeClass = 'bg-primary';
            if (role === 'ROLE_USER') {
                badgeClass = 'bg-secondary';
            } else if (role === 'ROLE_ADMIN') {
                badgeClass = 'bg-danger';
            } else if (role === 'ROLE_MODERATOR') {
                badgeClass = 'bg-warning text-dark';
            } else if (role === 'ROLE_PRODUCT_MANAGER') {
                badgeClass = 'bg-success';
            }
            
            return `<span class="badge ${badgeClass}">${role}</span>`;
        }).join('');
    }
    
    // Set checkboxes based on current roles
    const roleUser = document.getElementById('roleUser');
    const roleAdmin = document.getElementById('roleAdmin');
    const roleModerator = document.getElementById('roleModerator');
    const roleProductManager = document.getElementById('roleProductManager');
    
    if (roleUser) roleUser.checked = currentRoles.includes('ROLE_USER');
    if (roleAdmin) roleAdmin.checked = currentRoles.includes('ROLE_ADMIN');
    if (roleModerator) roleModerator.checked = currentRoles.includes('ROLE_MODERATOR');
    if (roleProductManager) roleProductManager.checked = currentRoles.includes('ROLE_PRODUCT_MANAGER');
    
    // Store user data for saving
    const roleModal = document.getElementById('roleModal');
    if (roleModal) {
        roleModal.setAttribute('data-user-id', userId);
        roleModal.setAttribute('data-current-roles', JSON.stringify(currentRoles));
    }
    
    // Show modal
    const modalElement = document.getElementById('roleModal');
    if (modalElement && bootstrap && typeof bootstrap?.Modal === 'function') {
        const modal = new bootstrap.Modal(modalElement);
        modal.show();
    }
}

function saveUserRoles() {
    // Get elements once
    const roleModal = document.getElementById('roleModal');
    const roleAdmin = document.getElementById('roleAdmin');
    const roleModerator = document.getElementById('roleModerator');
    const roleProductManager = document.getElementById('roleProductManager');
    
    if (!roleModal || !roleAdmin || !roleModerator || !roleProductManager) {
        console.error('Required elements not found');
        return;
    }
    
    // Get selected roles (ROLE_USER is always included)
    const selectedRoles = ['ROLE_USER']; // Always include ROLE_USER
    if (roleAdmin.checked) selectedRoles.push('ROLE_ADMIN');
    if (roleModerator.checked) selectedRoles.push('ROLE_MODERATOR');
    if (roleProductManager.checked) selectedRoles.push('ROLE_PRODUCT_MANAGER');
    
    const userId = roleModal.getAttribute('data-user-id');
    const currentRolesJson = roleModal.getAttribute('data-current-roles') || '[]';
    let currentRoles = [];
    
    try {
        currentRoles = JSON.parse(currentRolesJson);
    } catch (e) {
        console.error('Error parsing current roles:', e);
        return;
    }
    
    // Check if roles actually changed
    const sortedSelectedRoles = selectedRoles.toSorted((a, b) => a.localeCompare(b));
    const sortedCurrentRoles = currentRoles.toSorted((a, b) => a.localeCompare(b));
    const rolesChanged = JSON.stringify(sortedSelectedRoles) !== JSON.stringify(sortedCurrentRoles);
    
    if (!rolesChanged) {
        const modalElement = document.getElementById('roleModal');
        if (modalElement && bootstrap && typeof bootstrap?.Modal === 'function') {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) modal.hide();
        }
        return;
    }

    // Show loading state
    const saveBtn = document.getElementById('saveRolesBtn');
    if (!saveBtn) {
        console.error('Save button not found');
        return;
    }
    
    const originalBtnText = saveBtn.innerHTML;
    saveBtn.disabled = true;
    saveBtn.innerHTML = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Saving...';

    // Get CSRF token safely
    const csrfToken = document.querySelector('meta[name="_csrf"]')?.content || '';
    
    // Send request to update roles
    fetch('/api/customers/role-management', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify({
            userId: userId,
            roles: selectedRoles
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Failed to update roles');
        }
    })
    .then(() => {
        // Close modal
        const modalElement = document.getElementById('roleModal');
        if (modalElement && bootstrap && typeof bootstrap?.Modal === 'function') {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) modal.hide();
        }
        
        // Reset button
        saveBtn.innerHTML = originalBtnText;
        saveBtn.disabled = false;
        
        // Show success message
        showToast('Roles updated successfully!', 'success');
        
        // Reload users to reflect changes
        loadUsers();
    })
    .catch(error => {
        console.error('Error updating roles:', error);
        
        // Reset button
        saveBtn.innerHTML = originalBtnText;
        saveBtn.disabled = false;
        
        // Show error message
        showToast('Failed to update roles: ' + error.message, 'error');
    });
}

function showToast(message, type = 'info') {
    // Create toast container if it doesn't exist
    let toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) {
        toastContainer = document.createElement('div');
        toastContainer.id = 'toastContainer';
        toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
        toastContainer.style.zIndex = '9999';
        document.body.appendChild(toastContainer);
    }
    
    // Determine toast style based on type
    let headerClass;
    let icon;
    if (type === 'success') {
        headerClass = 'text-success';
        icon = '<i class="bi bi-check-circle me-2"></i>';
    } else if (type === 'error') {
        headerClass = 'text-danger';
        icon = '<i class="bi bi-exclamation-triangle me-2"></i>';
    } else {
        headerClass = 'text-info';
        icon = '<i class="bi bi-info-circle me-2"></i>';
    }
    
    // Create toast
    const toastId = 'toast-' + Date.now();
    const toastHtml = `
        <div id="${toastId}" class="toast" role="alert">
            <div class="toast-header ${headerClass}">
                ${icon}<strong class="me-auto">Role Management</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
            </div>
            <div class="toast-body">
                ${message}
            </div>
        </div>
    `;
    
    toastContainer.insertAdjacentHTML('beforeend', toastHtml);
    
    // Show toast
    const toastElement = document.getElementById(toastId);
    if (toastElement && bootstrap && typeof bootstrap?.Toast === 'function') {
        const toast = new bootstrap.Toast(toastElement);
        toast.show();
    }
    
    // Remove toast after it's hidden
    toastElement.addEventListener('hidden.bs.toast', () => {
        toastElement.remove();
    });
}

function updatePagination(data) {
    const pagination = document.getElementById('pagination');
    if (!pagination) return;
    
    pagination.innerHTML = '';
    
    totalPages = data.totalPages || 0;
    totalUsers = data.totalElements || 0;
    
    // Previous button
    const prevLi = document.createElement('li');
    prevLi.className = `page-item ${data.first ? 'disabled' : ''}`;
    prevLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPage - 1})">Previous</a>`;
    pagination.appendChild(prevLi);
    
    // Page numbers
    const startPage = Math.max(0, currentPage - 2);
    const endPage = Math.min(totalPages - 1, currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        const li = document.createElement('li');
        li.className = `page-item ${i === currentPage ? 'active' : ''}`;
        li.innerHTML = `<a class="page-link" href="#" onclick="changePage(${i})">${i + 1}</a>`;
        pagination.appendChild(li);
    }
    
    // Next button
    const nextLi = document.createElement('li');
    nextLi.className = `page-item ${data.last ? 'disabled' : ''}`;
    nextLi.innerHTML = `<a class="page-link" href="#" onclick="changePage(${currentPage + 1})">Next</a>`;
    pagination.appendChild(nextLi);
}

function changePage(page) {
    if (page >= 0 && page < totalPages) {
        currentPage = page;
        loadUsers();
    }
}

function updateUserCount(data) {
    const numberOfElements = data.numberOfElements || 0;
    const totalElements = data.totalElements || 0;
    const start = numberOfElements > 0 ? (data.number * data.size) + 1 : 0;
    const end = start + numberOfElements - 1;
    
    const userCount = document.getElementById('userCount');
    const showingStart = document.getElementById('showingStart');
    const showingEnd = document.getElementById('showingEnd');
    const totalUsers = document.getElementById('totalUsers');
    
    if (userCount) userCount.textContent = `${totalElements} users`;
    if (showingStart) showingStart.textContent = start.toString();
    if (showingEnd) showingEnd.textContent = end.toString();
    if (totalUsers) totalUsers.textContent = totalElements.toString();
    
    // Update filter info
    const searchInput = document.getElementById('searchInput');
    const roleFilter = document.getElementById('roleFilter');
    const filterInfoElement = document.getElementById('filterInfo');
    
    const searchTerm = searchInput?.value?.trim() || '';
    const roleFilterValue = roleFilter?.value || '';
    const filterInfo = [];
    
    if (searchTerm) {
        filterInfo.push(`Search: "${searchTerm}"`);
    }
    if (roleFilterValue) {
        filterInfo.push(`Role: ${roleFilterValue}`);
    }
    
    if (filterInfoElement) {
        if (filterInfo.length > 0) {
            filterInfoElement.textContent = `Filtered by: ${filterInfo.join(', ')}`;
        } else {
            filterInfoElement.textContent = 'Showing all users';
        }
    }
}

function showLoadingState() {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = `
        <tr>
            <td colspan="4" class="text-center py-4">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <p class="mt-2 text-muted">Loading users...</p>
            </td>
        </tr>
    `;
}

function showErrorState() {
    const tbody = document.getElementById('usersTableBody');
    tbody.innerHTML = `
        <tr>
            <td colspan="4" class="text-center text-danger py-4">
                <i class="bi bi-exclamation-triangle fs-1 d-block mb-2"></i>
                <p>Failed to load users. Please try again.</p>
                <button class="btn btn-outline-primary" onclick="loadUsers()">Retry</button>
            </td>
        </tr>
    `;
}
