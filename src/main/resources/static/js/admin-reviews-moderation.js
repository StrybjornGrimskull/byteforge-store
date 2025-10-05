// Admin Reviews Moderation JavaScript
// Global variables
let currentPage = 0;
let pageSize = 10;
let totalPages = 0;
let totalElements = 0;
let currentReviewId = null;

// Initialize page when document is ready
$(document).ready(function() {
    loadPendingReviews(currentPage);
    
    // Add event listeners for modal buttons
    $('#confirmApprove').on('click', confirmApproveReview);
    $('#confirmDelete').on('click', confirmDeleteReview);
});

// Load pending reviews with pagination
function loadPendingReviews(page) {
    $.ajax({
        url: `/api/reviews/pending?page=${page}&size=${pageSize}`,
        method: 'GET',
        beforeSend: function() {
            showLoading(true);
        },
        success: function(data) {
            displayReviews(data.content);
            updatePagination(data);
        },
        error: function(xhr, status, error) {
            console.error('Error loading reviews:', error);
            showError('Error loading reviews');
        },
        complete: function() {
            showLoading(false);
        }
    });
}

// Display reviews
function displayReviews(reviews) {
    const $container = $('#reviewsContainer');
    const $noReviewsMessage = $('#noReviewsMessage');

    if (reviews.length === 0) {
        $container.empty();
        $noReviewsMessage.show();
        $('#paginationContainer').hide();
        return;
    }

    $noReviewsMessage.hide();
    const reviewCards = reviews.map(review => createReviewCard(review)).join('');
    $container.html(reviewCards);
}

// Update pagination
function updatePagination(pageData) {
    currentPage = pageData.number;
    totalPages = pageData.totalPages;
    totalElements = pageData.totalElements;
    
    const $paginationContainer = $('#paginationContainer');
    const $pagination = $('#pagination');
    
    if (totalPages <= 1) {
        $paginationContainer.hide();
        return;
    }
    
    $paginationContainer.show();
    
    let paginationHTML = '';
    
    // Previous button
    if (currentPage > 0) {
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="#" data-page="${currentPage - 1}">
                    <i class="bi bi-chevron-left"></i>
                </a>
            </li>
        `;
    } else {
        paginationHTML += `
            <li class="page-item disabled">
                <span class="page-link">
                    <i class="bi bi-chevron-left"></i>
                </span>
            </li>
        `;
    }
    
    // Page numbers
    const startPage = Math.max(0, currentPage - 2);
    const endPage = Math.min(totalPages - 1, currentPage + 2);
    
    for (let i = startPage; i <= endPage; i++) {
        if (i === currentPage) {
            paginationHTML += `
                <li class="page-item active">
                    <span class="page-link">${i + 1}</span>
                </li>
            `;
        } else {
            paginationHTML += `
                <li class="page-item">
                    <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                </li>
            `;
        }
    }
    
    // Next button
    if (currentPage < totalPages - 1) {
        paginationHTML += `
            <li class="page-item">
                <a class="page-link" href="#" data-page="${currentPage + 1}">
                    <i class="bi bi-chevron-right"></i>
                </a>
            </li>
        `;
    } else {
        paginationHTML += `
            <li class="page-item disabled">
                <span class="page-link">
                    <i class="bi bi-chevron-right"></i>
                </span>
            </li>
        `;
    }
    
    $pagination.html(paginationHTML);
    
    // Add click handlers for pagination links
    $pagination.find('a[data-page]').on('click', function(e) {
        e.preventDefault();
        const page = parseInt($(this).data('page'));
        loadPendingReviews(page);
    });
}

// Create review card HTML
function createReviewCard(review) {
    const stars = '★'.repeat(review.rating) + '☆'.repeat(5 - review.rating);
    const createdAt = new Date(review.createdAt).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });

    return `
        <div class="card review-card" id="review-${review.reviewId}">
            <div class="review-header">
                <div class="row align-items-center">
                    <div class="col-md-8">
                        <h6 class="mb-1">
                            <i class="bi bi-person-circle me-2"></i>
                            ${review.userFirstName}
                        </h6>
                        <div class="rating-stars mb-1">${stars}</div>
                        <div class="review-meta">
                            <i class="bi bi-calendar me-1"></i>${createdAt}
                        </div>
                    </div>
                    <div class="col-md-4 text-end">
                        <div class="action-buttons">
                            <button class="btn btn-sm btn-approve text-white" data-review-id="${review.reviewId}">
                                <i class="bi bi-check-circle me-1"></i>Approve
                            </button>
                            <button class="btn btn-sm btn-delete text-white" data-review-id="${review.reviewId}">
                                <i class="bi bi-trash me-1"></i>Delete
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card-body">
                <div class="product-info">
                    <h6 class="mb-1">
                        <i class="bi bi-box-seam me-2"></i>
                        Product: ${review.productName}
                    </h6>
                </div>
                <div class="review-text">
                    <p class="mb-0">${review.text}</p>
                </div>
            </div>
        </div>
    `;
}

// Approve review - show modal
function approveReview(reviewId) {
    currentReviewId = reviewId;
    const approveModal = new bootstrap.Modal($('#approveModal')[0]);
    approveModal.show();
}

// Confirm approve review
function confirmApproveReview() {
    if (!currentReviewId) return;

    $.ajax({
        url: `/api/reviews/${currentReviewId}/approve`,
        method: 'PUT',
        contentType: 'application/json',
        success: function() {
            // Hide modal
            const approveModal = bootstrap.Modal.getInstance($('#approveModal')[0]);
            approveModal.hide();

            // Reload current page
            loadPendingReviews(currentPage);
            
            // Show success message
            showSuccess('Review approved successfully');
        },
        error: function(xhr, status, error) {
            console.error('Error approving review:', error);
            showError('Failed to approve review');
        },
        complete: function() {
            currentReviewId = null;
        }
    });
}

// Delete review - show modal
function deleteReview(reviewId) {
    currentReviewId = reviewId;
    const deleteModal = new bootstrap.Modal($('#deleteModal')[0]);
    deleteModal.show();
}

// Confirm delete review
function confirmDeleteReview() {
    if (!currentReviewId) return;

    $.ajax({
        url: `/api/reviews/${currentReviewId}`,
        method: 'DELETE',
        success: function() {
            // Hide modal
            const deleteModal = bootstrap.Modal.getInstance($('#deleteModal')[0]);
            deleteModal.hide();

            // Reload current page
            loadPendingReviews(currentPage);
            
            // Show success message
            showSuccess('Review deleted successfully');
        },
        error: function(xhr, status, error) {
            console.error('Error deleting review:', error);
            showError('Failed to delete review');
        },
        complete: function() {
            currentReviewId = null;
        }
    });
}

// Show/hide loading spinner
function showLoading(show) {
    if (show) {
        $('#loadingSpinner').show();
    } else {
        $('#loadingSpinner').hide();
    }
}

// Show success message
function showSuccess(message) {
    const toast = $(`
        <div class="toast align-items-center text-white bg-success border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="bi bi-check-circle me-2"></i>${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `);
    
    // Add to toast container
    $('#toastContainer').append(toast);
    
    // Show toast
    const bsToast = new bootstrap.Toast(toast[0]);
    bsToast.show();
    
    // Remove after hide
    toast.on('hidden.bs.toast', function() {
        $(this).remove();
    });
}

// Show error message
function showError(message) {
    const toast = $(`
        <div class="toast align-items-center text-white bg-danger border-0" role="alert">
            <div class="d-flex">
                <div class="toast-body">
                    <i class="bi bi-exclamation-triangle me-2"></i>${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        </div>
    `);
    
    // Add to toast container
    $('#toastContainer').append(toast);
    
    // Show toast
    const bsToast = new bootstrap.Toast(toast[0]);
    bsToast.show();
    
    // Remove after hide
    toast.on('hidden.bs.toast', function() {
        $(this).remove();
    });
}

// Event delegation for dynamically created buttons
$(document).on('click', '.btn-approve', function() {
    const reviewId = $(this).data('review-id');
    approveReview(reviewId);
});

$(document).on('click', '.btn-delete', function() {
    const reviewId = $(this).data('review-id');
    deleteReview(reviewId);
});
