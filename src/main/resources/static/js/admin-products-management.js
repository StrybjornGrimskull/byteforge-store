// Admin Products Management JavaScript
document.addEventListener('DOMContentLoaded', function() {
    // Add any products management specific functionality here
    console.log('Admin Products Management loaded');
    
    // Example: Add click tracking for dashboard cards
    const dashboardCards = document.querySelectorAll('.dashboard-card');
    dashboardCards.forEach(card => {
        card.addEventListener('click', function(e) {
            // Only track if not clicking on a link or button
            if (!e.target.closest('a, button')) {
                console.log('Products management card clicked:', this.querySelector('.card-title').textContent);
            }
        });
    });
});
