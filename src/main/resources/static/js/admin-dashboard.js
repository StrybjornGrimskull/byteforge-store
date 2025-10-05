// Admin Dashboard JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Add any dashboard-specific functionality here
    console.log('Admin Dashboard loaded');
    
    // Example: Add click tracking for dashboard cards
    const dashboardCards = document.querySelectorAll('.dashboard-card');
    dashboardCards.forEach(card => {
        card.addEventListener('click', function(e) {
            // Only track if not clicking on a link or button
            if (!e.target.closest('a, button')) {
                console.log('Dashboard card clicked:', this.querySelector('.card-title').textContent);
            }
        });
    });
});
