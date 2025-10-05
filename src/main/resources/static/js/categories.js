document.addEventListener('DOMContentLoaded', function() {
    // Keyboard split view functionality
    const keyboardShopNow = document.getElementById('keyboard-shop-now');
    const keyboardSplit = document.getElementById('keyboard-split');
    const keyboardCard = document.getElementById('keyboard-card');
    
    if (keyboardShopNow && keyboardSplit) {
        keyboardShopNow.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Hide all card content
            const cardImage = keyboardCard.querySelector('.card-image');
            const cardContent = keyboardCard.querySelector('.card-content');
            const cardBody = keyboardCard.querySelector('.card-body');
            
            if (cardImage) cardImage.style.display = 'none';
            if (cardContent) cardContent.style.display = 'none';
            if (cardBody) cardBody.style.display = 'none';
            
            // Show split view
            keyboardSplit.classList.add('active');
        });
    }

    // Mice split view functionality
    const miceShopNow = document.getElementById('mice-shop-now');
    const miceSplit = document.getElementById('mice-split');
    const miceCard = document.getElementById('mice-card');
    
    if (miceShopNow && miceSplit) {
        miceShopNow.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Hide all card content
            const cardImage = miceCard.querySelector('.card-image');
            const cardContent = miceCard.querySelector('.card-content');
            const cardBody = miceCard.querySelector('.card-body');
            
            if (cardImage) cardImage.style.display = 'none';
            if (cardContent) cardContent.style.display = 'none';
            if (cardBody) cardBody.style.display = 'none';
            
            // Show split view
            miceSplit.classList.add('active');
        });
    }

    // Back arrow functionality for keyboards
    const keyboardBackArrow = document.getElementById('keyboard-back-arrow');
    if (keyboardBackArrow) {
        keyboardBackArrow.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Show all card content
            const cardImage = keyboardCard.querySelector('.card-image');
            const cardContent = keyboardCard.querySelector('.card-content');
            const cardBody = keyboardCard.querySelector('.card-body');
            
            if (cardImage) cardImage.style.display = 'block';
            if (cardContent) cardContent.style.display = 'block';
            if (cardBody) cardBody.style.display = 'block';
            
            // Hide split view
            keyboardSplit.classList.remove('active');
        });
    }

    // Back arrow functionality for mice
    const miceBackArrow = document.getElementById('mice-back-arrow');
    if (miceBackArrow) {
        miceBackArrow.addEventListener('click', function(e) {
            e.preventDefault();
            
            // Show all card content
            const cardImage = miceCard.querySelector('.card-image');
            const cardContent = miceCard.querySelector('.card-content');
            const cardBody = miceCard.querySelector('.card-body');
            
            if (cardImage) cardImage.style.display = 'block';
            if (cardContent) cardContent.style.display = 'block';
            if (cardBody) cardBody.style.display = 'block';
            
            // Hide split view
            miceSplit.classList.remove('active');
        });
    }
});
