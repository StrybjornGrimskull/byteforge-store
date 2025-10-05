document.addEventListener('DOMContentLoaded', function() {
    let currentUserId = null;

    // Helper functions to extract nested ternary operations

    function handleDeleteAllResponse(allSuccessful) {
        if (allSuccessful) {
            // Reload notifications
            loadNotifications();
        } else {
            alert('Some notifications could not be deleted');
        }
    }

    // Get current user ID from the page or API
    fetch('/api/profile')
        .then(response => {
            console.log('Profile response status:', response.status);
            if (!response.ok) {
                throw new Error(`HTTP ${response.status}: ${response.statusText}`);
            }
            return response.json();
        })
        .then(profile => {
            console.log('Profile data:', profile);
            currentUserId = profile.userId;
            console.log('Current user ID:', currentUserId);
            loadNotifications();
        })
        .catch(error => {
            console.error('Error getting user profile:', error);
            document.getElementById('loading-state').innerHTML = 
                `<div class="alert alert-danger">Failed to load user profile: ${error.message}</div>`;
        });

    function loadNotifications() {
        if (!currentUserId) return;

        fetch(`/api/notifications/user/${currentUserId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP ${response.status}: ${response.statusText}`);
                }
                return response.json();
            })
            .then(notifications => {
                console.log('Notifications loaded:', notifications);
                displayNotifications(notifications);
                updateUnreadCount(notifications);
            })
            .catch(error => {
                console.error('Error loading notifications:', error);
                document.getElementById('loading-state').innerHTML = 
                    `<div class="alert alert-danger">Failed to load notifications: ${error.message}</div>`;
            });
    }

    function displayNotifications(notifications) {
        const loadingState = document.getElementById('loading-state');
        const emptyState = document.getElementById('empty-state');
        const container = document.getElementById('notifications-container');

        loadingState.classList.add('d-none');

        if (!notifications || notifications.length === 0) {
            emptyState.classList.remove('d-none');
            container.classList.add('d-none');
            return;
        }

        emptyState.classList.add('d-none');
        container.classList.remove('d-none');

        container.innerHTML = notifications.map(notification => {
            const itemClass = notification.isRead ? 'notification-item' : 'notification-item unread';
            
            return `
                <div class="card mb-3 ${itemClass}"
                     data-notification-id="${notification.id}">
                    <div class="card-body">
                        <div class="d-flex align-items-start">
                            ${!notification.isRead ? '<div class="new-badge">NEW</div>' : ''}
                            <div class="flex-grow-1">
                                <p class="mb-2">${notification.message}</p>
                                <small style="color: rgba(255, 255, 255, 0.7);">
                                    <i class="bi bi-clock me-1"></i>
                                    ${formatDate(notification.createdAt)}
                                </small>
                            </div>
                            <div class="d-flex flex-column gap-2">
                                ${!notification.isRead ? `
                                    <button class="btn btn-outline-light btn-sm" onclick="markAsRead(${notification.id})">
                                        Read
                                    </button>
                                ` : ''}
                                <button class="btn btn-outline-danger btn-sm" onclick="deleteNotification(${notification.id})">
                                    <i class="bi bi-trash"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `;
        }).join('');
    }

    function updateUnreadCount(notifications) {
        const unreadCount = notifications.filter(n => !n.isRead).length;
        const countElement = document.getElementById('unread-count');
        if (countElement) {
            countElement.textContent = `${unreadCount} unread`;
        }
    }

    function formatDate(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString('ru-RU', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    window.markAsRead = function(notificationId) {
        fetch(`/api/notifications/${notificationId}/read`, {
            method: 'PUT'
        })
        .then(response => {
            if (response.ok) {
                // Update UI
                const notificationElement = document.querySelector(`[data-notification-id="${notificationId}"]`);
                if (notificationElement) {
                    notificationElement.classList.remove('unread');
                    notificationElement.classList.add('notification-item');
                    // Remove mark as read button
                    const markAsReadBtn = notificationElement.querySelector('button[onclick*="markAsRead"]');
                    if (markAsReadBtn) {
                        markAsReadBtn.remove();
                    }
                }
                // Reload notifications to update count
                loadNotifications();
            }
        })
        .catch(error => {
            console.error('Error marking notification as read:', error);
            alert('Failed to mark notification as read');
        });
    };

    window.markAllAsRead = function() {
        if (!currentUserId) return;
        
        fetch(`/api/notifications/user/${currentUserId}/read-all`, {
            method: 'PUT'
        })
        .then(response => {
            if (response.ok) {
                // Reload notifications
                loadNotifications();
            }
        })
        .catch(error => {
            console.error('Error marking all notifications as read:', error);
            alert('Failed to mark all notifications as read');
        });
    };

    window.deleteNotification = function(notificationId) {
        if (!confirm('Are you sure you want to delete this notification?')) {
            return;
        }
        
        fetch(`/api/notifications/${notificationId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                // Remove the notification from UI
                const notificationElement = document.querySelector(`[data-notification-id="${notificationId}"]`);
                if (notificationElement) {
                    notificationElement.remove();
                }
                // Reload notifications to update count
                loadNotifications();
            }
        })
        .catch(error => {
            console.error('Error deleting notification:', error);
            alert('Failed to delete notification');
        });
    };

    function processDeleteAllNotifications(notifications) {
        if (!notifications || notifications.length === 0) {
            alert('No notifications to delete');
            return;
        }
        
        // Delete each notification
        const deletePromises = notifications.map(notification => 
            fetch(`/api/notifications/${notification.id}`, {
                method: 'DELETE'
            })
        );
        
        Promise.all(deletePromises)
            .then(responses => {
                const allSuccessful = responses.every(response => response.ok);
                handleDeleteAllResponse(allSuccessful);
            })
            .catch(error => {
                console.error('Error deleting notifications:', error);
                alert('Failed to delete notifications');
            });
    }

    window.deleteAllNotifications = function() {
        if (!currentUserId) return;
        
        if (!confirm('Are you sure you want to delete ALL notifications? This action cannot be undone.')) {
            return;
        }
        
        // Get all notification IDs first
        fetch(`/api/notifications/user/${currentUserId}`)
            .then(response => response.json())
            .then(processDeleteAllNotifications)
            .catch(error => {
                console.error('Error loading notifications for deletion:', error);
                alert('Failed to load notifications');
            });
    };
});
