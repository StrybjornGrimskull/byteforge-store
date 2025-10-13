let lastId = null;
let loading = false;
const limit = 12;

document.addEventListener("DOMContentLoaded", function () {
    // Получаем ID категории из URL
    const urlParams = new URLSearchParams(window.location.search);
    const urlCategoryId = urlParams.get('categoryId');

    // Загружаем категории и бренды
    loadCategories().then(() => {
        // Устанавливаем категорию в select если есть в URL
        if (urlCategoryId) {
            document.getElementById('categoryId').value = urlCategoryId;
            updateBrands();
        } else {
            // Загружаем все бренды если категория не выбрана
            loadBrands();
        }
        
        // Загружаем продукты с учетом категории из URL
        loadProducts();
    });

    // Добавляем обработчик для кнопки "Load More"
    document.getElementById('loadMoreBtn').addEventListener('click', function() {
        loadMoreProducts();
    });
});

async function loadCategories() {
    try {
        const response = await fetch('/api/categories');
        if (!response.ok) throw new Error('Failed to load categories');
        const categories = await response.json();
        
        const categorySelect = document.getElementById('categoryId');
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            categorySelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading categories:', error);
    }
}

async function loadBrands(categoryId = '') {
    try {
        const url = categoryId 
            ? `/api/brands/by-category?categoryId=${categoryId}`
            : '/api/brands';
        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to load brands');
        const brands = await response.json();
        
        const brandSelect = document.getElementById('brandId');
        brandSelect.innerHTML = '<option value="">All Brands</option>';
        brands.forEach(brand => {
            const option = document.createElement('option');
            option.value = brand.id;
            option.textContent = brand.name;
            brandSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error loading brands:', error);
    }
}

function getFilterParams() {
    const params = new URLSearchParams();

    const categoryId = document.getElementById('categoryId').value;
    if (categoryId) params.set('categoryId', categoryId);

    const name = document.getElementById('name').value;
    if (name) params.set('name', name);

    const brandId = document.getElementById('brandId').value;
    if (brandId) params.set('brandId', brandId);

    const minPrice = document.getElementById('minPrice').value;
    if (minPrice) params.set('minPrice', minPrice);

    const maxPrice = document.getElementById('maxPrice').value;
    if (maxPrice) params.set('maxPrice', maxPrice);

    params.set('limit', limit);
    if (lastId !== null) {
        params.set('lastId', lastId);
    }

    return params;
}

function loadProducts() {
    if (loading) return;
    loading = true;

    // Сбрасываем lastId при новой загрузке
    lastId = null;
    
    const params = getFilterParams();

    fetch('/api/products/lazy?' + params)
        .then(response => response.json())
        .then(products => {
            renderProducts(products);
            updateLoadMoreButton(products.length);
            if (products.length > 0) {
                lastId = products[products.length - 1].id;
            }
            loading = false;
        })
        .catch(() => {
            loading = false;
        });
}

function loadMoreProducts() {
    if (loading) return;
    loading = true;

    const params = getFilterParams();

    fetch('/api/products/lazy?' + params)
        .then(response => response.json())
        .then(products => {
            appendProducts(products);
            updateLoadMoreButton(products.length);
            if (products.length > 0) {
                lastId = products[products.length - 1].id;
            }
            loading = false;
        })
        .catch(() => {
            loading = false;
        });
}

function updateLoadMoreButton(productsCount) {
    const loadMoreContainer = document.getElementById('loadMoreContainer');
    const backToTopContainer = document.getElementById('backToTopContainer');
    
    if (productsCount < limit) {
        loadMoreContainer.style.display = 'none';
        backToTopContainer.style.display = 'block';
    } else {
        loadMoreContainer.style.display = 'block';
        backToTopContainer.style.display = 'none';
    }
}

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'auto'
    });
}

function updateBrands() {
    const categoryId = document.getElementById('categoryId').value;
    loadBrands(categoryId);
}

function createProductHtml(product) {
    return `
        <div class="product-item">
            <div class="product-image-box">
                ${product.imageUrl
        ? `<img src="/uploads/${product.imageUrl}" alt="${product.name}">`
        : '<div class="image-placeholder"></div>'
    }
            </div>
            <h6 class="mt-2 mb-1 text-center">${product.name}</h6>
            <div class="mb-1"><span class="meta-label">Brand:</span> <span class="meta-value">${product.brandName || ''}</span></div>
            <div class="mb-1"><span class="meta-label">Price:</span> <span class="meta-value">€${product.price}</span></div>
            <div class="mb-2 meta-label" style="font-weight:bold;">
                ${product.stockQuantity > 0 ? 'In stock' : 'Out of stock'}
            </div>
            <a href="/products/details/${product.id}" class="btn btn-primary w-100 mt-auto">Details</a>
        </div>
    `;
}

function renderProducts(products) {
    const container = document.getElementById('productList');
    if (!products || products.length === 0) {
        container.innerHTML = '<div class="col-12 text-center"><p class="text-muted">No products found.</p></div>';
        return;
    }
    container.innerHTML = products.map(createProductHtml).join('');
}

function appendProducts(products) {
    const container = document.getElementById('productList');
    if (!products || products.length === 0) {
        return;
    }
    container.innerHTML += products.map(createProductHtml).join('');
}

function resetFilters() {
    document.getElementById('categoryId').value = '';
    document.getElementById('name').value = '';
    document.getElementById('brandId').value = '';
    document.getElementById('minPrice').value = '';
    document.getElementById('maxPrice').value = '';
    
    // Обновляем бренды для пустой категории
    updateBrands();
    
    // Загружаем все продукты
    loadProducts();
}
