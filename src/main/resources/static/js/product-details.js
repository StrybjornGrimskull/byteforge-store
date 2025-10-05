// Глобальная переменная для отслеживания загрузки спецификаций
var specsLoaded = false;

document.addEventListener('DOMContentLoaded', function() {
    // Не загружаем спецификации автоматически при загрузке страницы
    // Они будут загружены при нажатии на выпадающее меню

    // Если в шаблоне есть данные о продукте, сохраняем их
    if (window.productData && window.productData.spec) {
        specsLoaded = true;
        // Не рендерим спецификации сразу, они будут отображены при открытии выпадающего меню
    }
});

function renderSpecifications(spec, categoryId) {
    const grid = document.getElementById('techSpecsGrid');

    // Очищаем сетку перед добавлением новых данных
    grid.innerHTML = '';

    switch (categoryId) {
        case 1: // GPU
            appendSpecCard(grid, 'Memory Size', spec.memorySize, ' GB');
            appendSpecCard(grid, 'Memory Type', spec.memoryType);
            appendSpecCard(grid, 'Bus Width', spec.busWidth, ' bit');
            appendSpecCard(grid, 'Base Clock', spec.baseClock, ' MHz');
            appendSpecCard(grid, 'Boost Clock', spec.boostClock, ' MHz');
            appendSpecCard(grid, 'Thermal Design Power', spec.tdp, ' W');
            appendSpecCard(grid, 'Length', spec.length, ' mm');
            appendSpecCard(grid, 'Display Outputs', spec.displayOutputs);
            break;

        case 2: // CPU
            appendSpecCard(grid, 'Cores', spec.cores);
            appendSpecCard(grid, 'Threads', spec.threads);
            appendSpecCard(grid, 'Base Clock', spec.baseClock, ' GHz');
            appendSpecCard(grid, 'Boost Clock', spec.boostClock, ' GHz');
            appendSpecCard(grid, 'Socket', spec.socket);
            appendSpecCard(grid, 'Cache Size', spec.cacheSize, ' MB');
            appendSpecCard(grid, 'Thermal Design Power', spec.tdp, ' W');
            appendSpecCard(grid, 'Integrated GPU', spec.integratedGpu);
            break;

        case 3: // Motherboard
            appendSpecCard(grid, 'Socket', spec.socket);
            appendSpecCard(grid, 'Chipset', spec.chipset);
            appendSpecCard(grid, 'Form Factor', spec.formFactor);
            appendSpecCard(grid, 'Memory Slots', spec.memorySlots);
            appendSpecCard(grid, 'Max Memory', spec.maxMemory, ' GB');
            appendSpecCard(grid, 'Memory Type', spec.memoryType);
            appendSpecCard(grid, 'M.2 Slots', spec.m2Slots);
            appendSpecCard(grid, 'SATA Ports', spec.sataPorts);
            break;

        case 4: // RAM
            appendSpecCard(grid, 'Memory Size', spec.memorySize, ' GB');
            appendSpecCard(grid, 'Modules Count', spec.modulesCount);
            appendSpecCard(grid, 'Speed', spec.speed, ' MHz');
            appendSpecCard(grid, 'Type', spec.type);
            appendSpecCard(grid, 'Timings', spec.timings);
            appendSpecCard(grid, 'Voltage', spec.voltage, ' V');
            break;

        case 5: // PSU
            appendSpecCard(grid, 'Wattage', spec.wattage, ' W');
            appendSpecCard(grid, 'Form Factor', spec.formFactor);
            appendSpecCard(grid, 'Efficiency Certification', spec.efficiencyCert);
            appendSpecCard(grid, 'Modularity', spec.modularity);
            appendSpecCard(grid, 'PCIe 8-pin Connectors', spec.pcie8pinConnectors);
            appendSpecCard(grid, 'SATA Connectors', spec.sataConnectors);
            break;

        case 6: // Case
            appendSpecCard(grid, 'Form Factor', spec.formFactor);
            appendSpecCard(grid, 'Motherboard Support', spec.motherboardSupport);
            appendSpecCard(grid, 'Max GPU Length', spec.maxGpuLength, ' mm');
            appendSpecCard(grid, 'Max CPU Cooler Height', spec.maxCpuCoolerHeight, ' mm');
            appendSpecCard(grid, 'Included Fans', spec.fansIncluded);
            appendSpecCard(grid, 'Radiator Support', spec.radiatorSupport);
            break;

        case 7: // Monitor
            appendSpecCard(grid, 'Screen Size', spec.screenSize, ' inches');
            appendSpecCard(grid, 'Resolution', spec.resolution);
            appendSpecCard(grid, 'Panel Type', spec.panelType);
            appendSpecCard(grid, 'Refresh Rate', spec.refreshRate, ' Hz');
            appendSpecCard(grid, 'Response Time', spec.responseTime, ' ms');
            break;

        case 8: // SSD
            appendSpecCard(grid, 'Capacity', spec.capacity, ' GB');
            appendSpecCard(grid, 'Form Factor', spec.formFactor);
            appendSpecCard(grid, 'Interface', spec.interfaceType);
            appendSpecCard(grid, 'Read Speed', spec.readSpeed, ' MB/s');
            appendSpecCard(grid, 'Write Speed', spec.writeSpeed, ' MB/s');
            appendSpecCard(grid, 'Memory Type', spec.memoryType);
            appendSpecCard(grid, 'Endurance', spec.enduranceTbw, ' TBW');
            appendSpecCard(grid, 'DRAM Cache', spec.dramCache);
            appendSpecCard(grid, 'Encryption', spec.encryption);
            appendSpecCard(grid, 'Thickness', spec.thickness, ' mm');
            break;

        case 9: // Wired Keyboard
            appendSpecCard(grid, 'Layout', spec.layout);
            appendSpecCard(grid, 'Switch Type', spec.switchType);
            appendSpecCard(grid, 'Switch Brand', spec.switchBrand);
            appendSpecCard(grid, 'Switch Model', spec.switchModel);
            appendSpecCard(grid, 'RGB Lighting', spec.rgbLighting);
            appendSpecCard(grid, 'Hot Swappable', spec.hotSwappable);
            appendSpecCard(grid, 'Actuation Force', spec.actuationForce, ' g');
            appendSpecCard(grid, 'Travel Distance', spec.travelDistance, ' mm');
            appendSpecCard(grid, 'Weight', spec.weight, ' g');
            appendSpecCard(grid, 'Cable Length', spec.cableLength, ' m');
            appendSpecCard(grid, 'USB Passthrough', spec.usbPassthrough);
            appendSpecCard(grid, 'Detachable Cable', spec.detachableCable);
            break;

        case 10: // Wireless Keyboard
            appendSpecCard(grid, 'Layout', spec.layout);
            appendSpecCard(grid, 'Switch Type', spec.switchType);
            appendSpecCard(grid, 'Switch Brand', spec.switchBrand);
            appendSpecCard(grid, 'Switch Model', spec.switchModel);
            appendSpecCard(grid, 'Wireless Technology', spec.wirelessTech);
            appendSpecCard(grid, 'RGB Lighting', spec.rgbLighting);
            appendSpecCard(grid, 'Hot Swappable', spec.hotSwappable);
            appendSpecCard(grid, 'Actuation Force', spec.actuationForce, ' g');
            appendSpecCard(grid, 'Travel Distance', spec.travelDistance, ' mm');
            appendSpecCard(grid, 'Weight', spec.weight, ' g');
            appendSpecCard(grid, 'Battery Life', spec.batteryLife, ' hours');
            appendSpecCard(grid, 'Charging Type', spec.chargingType);
            appendSpecCard(grid, 'Multi-device Pairing', spec.multiDevicePairing);
            break;

        case 11: // Wired Mouse
            appendSpecCard(grid, 'Sensor Type', spec.sensorType);
            appendSpecCard(grid, 'Sensor Model', spec.sensorModel);
            appendSpecCard(grid, 'Max DPI', spec.maxDpi);
            appendSpecCard(grid, 'Adjustable DPI', spec.adjustableDpi);
            appendSpecCard(grid, 'Buttons', spec.buttons);
            appendSpecCard(grid, 'Cable Length', spec.cableLength, ' m');
            appendSpecCard(grid, 'Cable Type', spec.cableType);
            appendSpecCard(grid, 'USB Connector', spec.usbConnector);
            appendSpecCard(grid, 'Weight', spec.weight, ' g');
            appendSpecCard(grid, 'RGB Lighting', spec.rgbLighting);
            appendSpecCard(grid, 'Onboard Memory', spec.onboardMemory);
            break;

        case 12: // Wireless Mouse
            appendSpecCard(grid, 'Sensor Type', spec.sensorType);
            appendSpecCard(grid, 'Sensor Model', spec.sensorModel);
            appendSpecCard(grid, 'Max DPI', spec.maxDpi);
            appendSpecCard(grid, 'Buttons', spec.buttons);
            appendSpecCard(grid, 'Wireless Technology', spec.wirelessTech);
            appendSpecCard(grid, 'Polling Rate', spec.pollingRate, ' Hz');
            appendSpecCard(grid, 'Weight', spec.weight, ' g');
            appendSpecCard(grid, 'RGB Lighting', spec.rgbLighting);
            appendSpecCard(grid, 'Battery Type', spec.batteryType);
            appendSpecCard(grid, 'Battery Life', spec.batteryLife, ' hours');
            appendSpecCard(grid, 'Standby Battery Life', spec.standbyBatteryLife, ' hours');
            appendSpecCard(grid, 'Charging Time', spec.chargingTime, ' hours');
            appendSpecCard(grid, 'Onboard Memory', spec.onboardMemory);
            break;

        default:
            for (const [key, value] of Object.entries(spec)) {
                if (value !== null && value !== undefined) {
                    appendSpecCard(grid, key, value);
                }
            }
    }
}

function appendSpecCard(grid, label, value, suffix = '') {
    if (value == null || value === '') return;

    let displayValue;

    switch (typeof value) {
        case 'boolean':
            displayValue = value ? 'Yes' : 'No';
            break;
        case 'number':
            displayValue = value;
            if (suffix) displayValue += suffix;
            break;
        case 'string':
            displayValue = value;
            break;
        case 'object':
            if (Array.isArray(value)) {
                displayValue = value.join(', ');
            } else {
                return;
            }
            break;
        default:
            return;
    }

    if (displayValue !== undefined && displayValue !== '') {
        const card = document.createElement('div');
        card.className = 'spec-card';

        const labelDiv = document.createElement('div');
        labelDiv.className = 'spec-label';
        labelDiv.textContent = formatLabel(label);

        const valueDiv = document.createElement('div');
        valueDiv.className = 'spec-value';
        valueDiv.textContent = displayValue;

        card.appendChild(labelDiv);
        card.appendChild(valueDiv);
        grid.appendChild(card);
    }
}

function formatLabel(label) {
    return label
        .replace(/^./, str => str.toUpperCase())
        .replace(/Id$/i, 'ID')
        // Сохраняем полное написание, если уже передано
        .replace(/Dpi/i, 'DPI')
        .replace(/Rgb/i, 'RGB')
        .replace(/Usb/i, 'USB')
        .replace(/M 2/i, 'M.2')
        .replace(/Sata/i, 'SATA')
        .replace(/Pcie/i, 'PCIe');
}

function toggleTechSpecs() {
    const content = document.getElementById('techSpecsContent');
    const icon = document.getElementById('techSpecsIcon');

    if (content.style.display === 'none') {
        // Show content - стрелка вверх
        content.style.display = 'block';
        icon.classList.remove('bi-chevron-down');
        icon.classList.add('bi-chevron-up');

        // Always load specifications
        const productId = window.location.pathname.split('/').pop();
        const categoryId = window.productData?.categoryId;

        if (categoryId) {
            const specType = getSpecType(categoryId);
            if (specType) {
                loadSpecifications(specType, productId, categoryId);
            } else {
                fallbackToGeneralApi(productId);
            }
        }
    } else {
        // Hide content - стрелка вниз
        content.style.display = 'none';
        icon.classList.remove('bi-chevron-up');
        icon.classList.add('bi-chevron-down');
    }
}

// Helper function to get spec type from category ID
function getSpecType(categoryId) {
    const specTypes = {
        1: 'gpu', 2: 'cpu', 3: 'motherboard', 4: 'ram',
        5: 'psu', 6: 'case', 7: 'monitor', 8: 'ssd',
        9: 'wired-keyboard', 10: 'wireless-keyboard',
        11: 'wired-mouse', 12: 'wireless-mouse'
    };
    return specTypes[categoryId] || '';
}

// Function to load specifications
function loadSpecifications(specType, productId, categoryId) {
    fetch(`/api/specifications/${specType}/${productId}`)
        .then(response => response.json())
        .then(specData => {
            if (!window.productData) window.productData = {};
            window.productData.spec = specData;
            renderSpecifications(specData, categoryId);
        })
        .catch(() => fallbackToGeneralApi(productId));
}

// Fallback function for general API
function fallbackToGeneralApi(productId) {
    fetch(`/api/products/${productId}/specifications`)
        .then(response => response.json())
        .then(specData => {
            if (!window.productData) window.productData = {};
            window.productData.spec = specData;
            renderSpecifications(specData, window.productData.categoryId);
        })
        .catch(error => {
            console.error('Error loading specifications:', error);
        });
}

// Prefill edit modal from front data and load specs from API
function clamp(value, min, max){
    if (value == null || isNaN(value)) return min;
    return Math.min(Math.max(value, min), max);
}

function toMoney(value){
    if (value == null || isNaN(value)) return '';
    return Number(value).toFixed(2);
}

function updateCalculatedPrice(){
    const original = parseFloat($('#editOriginalPrice').val());
    let discount = parseFloat($('#editDiscountPercentage').val());
    discount = clamp(discount, 0, 100);
    if (!isNaN(discount)) {
        $('#editDiscountPercentage').val(String(Math.round(discount)));
    }
    if (isNaN(original)) {
        $('#editPrice').val('');
        return;
    }
    const price = original * (1 - (isNaN(discount) ? 0 : discount) / 100);
    $('#editPrice').val(toMoney(price));
}

function prefillEditModal() {
    const p = window.productData || {};
    $('#editName').val(p.name || '');
    $('#editPrice').val(p.price ?? '');
    $('#editOriginalPrice').val(p.originalPrice ?? '');
    $('#editDiscountPercentage').val(p.discountPercentage ?? '');
    $('#editShortDescription').val(p.shortDescription || '');
    $('#editStockQuantity').val(p.stockQuantity ?? '');
    $('#editReleaseYear').val(p.releaseYear || '');
    $('#editWarrantyMonths').val(p.warrantyMonths || '');
    $('#editImageUrl').val(p.imageUrl || '');
    
    // Populate Category and Brand selects (derive IDs from productData)
    populateCategoriesAndBrands();

    // Calculate current price from original and discount
    updateCalculatedPrice();

    const productId = p.id || window.location.pathname.split('/').pop();
    const categoryId = p.categoryId;
    const container = document.getElementById('editSpecsContainer');
    container.innerHTML = '<div class="col-12 text-muted">Loading specifications...</div>';

    if (categoryId) {
        const specType = getSpecType(categoryId);
        if (specType) {
            fetch(`/api/specifications/${specType}/${productId}`)
                .then(r => r.json())
                .then(spec => renderEditSpecs(container, spec))
                .catch(() => fallbackEditSpecs(container, productId, categoryId));
        } else {
            fallbackEditSpecs(container, productId, categoryId);
        }
    } else {
        container.innerHTML = '<div class="col-12 text-muted">No category to fetch specifications.</div>';
    }
}

function fallbackEditSpecs(container, productId, categoryId){
    fetch(`/api/products/${productId}/specifications`)
        .then(r => r.json())
        .then(spec => renderEditSpecs(container, spec, categoryId))
        .catch(() => { container.innerHTML = '<div class="col-12 text-muted">Failed to load specifications.</div>'; });
}

function renderEditSpecs(container, spec, categoryId){
    container.innerHTML = '';
    // Render as read-only inputs for now
    const entries = Object.entries(spec || {});
    if (entries.length === 0) {
        container.innerHTML = '<div class="col-12 text-muted">No specifications.</div>';
        return;
    }
    for (const [key, value] of entries){
        const pretty = formatLabel(key);
        const val = Array.isArray(value) ? value.join(', ') : (value ?? '');
        const block = document.createElement('div');
        block.className = 'col-md-6';
        const inputId = `spec_${key}`.replace(/[^a-zA-Z0-9_-]/g, '_');
        let inputHtml = '';
        if (typeof value === 'boolean') {
            const checked = value ? 'checked' : '';
            inputHtml = `
                <div class="form-check mt-1">
                    <input id="${inputId}" type="checkbox" class="form-check-input" ${checked}>
                    <label class="form-check-label" for="${inputId}">Enabled</label>
                    <div class="invalid-feedback"></div>
                </div>
            `;
        } else if (typeof value === 'number') {
            inputHtml = `
                <input id="${inputId}" type="number" step="0.01" class="form-control" value="${String(value)}">
                <div class="invalid-feedback"></div>
            `;
        } else {
            inputHtml = `
                <input id="${inputId}" type="text" class="form-control" value="${String(val)}">
                <div class="invalid-feedback"></div>
            `;
        }
        block.innerHTML = `
            <label class="form-label" for="${inputId}">${pretty}</label>
            ${inputHtml}
        `;
        container.appendChild(block);
    }
}

document.addEventListener('DOMContentLoaded', function(){
    const btn = document.getElementById('editProductBtn');
    if (btn) {
        btn.addEventListener('click', prefillEditModal);
    }
    const originalEl = document.getElementById('editOriginalPrice');
    const discountEl = document.getElementById('editDiscountPercentage');
    if (originalEl) originalEl.addEventListener('input', updateCalculatedPrice);
    if (discountEl) discountEl.addEventListener('input', updateCalculatedPrice);
    const saveBtn = document.getElementById('saveEditBtn');
    if (saveBtn) saveBtn.addEventListener('click', handleSaveEdit);
});

async function handleSaveEdit(){
    const productId = getProductId();
    const formData = buildFormData();
    
    const prodRes = await updateProduct(productId, formData);
    if (!prodRes.ok) {
        await handleProductError(prodRes);
        return;
    }

    const specSuccess = await updateSpecs(productId);
    if (specSuccess) {
        closeModalAndReload();
    }
}

function getProductId() {
    const p = window.productData || {};
    return p.id || window.location.pathname.split('/').pop();
}

function buildFormData() {
    const formData = new FormData();
    const p = window.productData || {};
    
    formData.append('name', $('#editName').val() || '');
    formData.append('shortDescription', $('#editShortDescription').val() || '');
    formData.append('stockQuantity', String(parseInt($('#editStockQuantity').val() || '0', 10)));
    formData.append('releaseYear', String(parseInt($('#editReleaseYear').val() || '0', 10)));
    formData.append('warrantyMonths', String(parseInt($('#editWarrantyMonths').val() || '0', 10)));
    
    const op = parseNumber($('#editOriginalPrice').val());
    if (op != null) formData.append('originalPrice', String(op));
    formData.append('discountPercentage', String(parseInt($('#editDiscountPercentage').val() || '0', 10)));
    
    const imageInput = $('#editImage')[0];
    if (imageInput && imageInput.files && imageInput.files[0]) {
        formData.append('productImage', imageInput.files[0]);
    }
    
    formData.append('categoryId', String(parseInt($('#editCategoryId').val() || p.categoryId, 10)));
    formData.append('brandId', String(parseInt($('#editBrandId').val() || p.brandId, 10)));
    
    return formData;
}

async function updateProduct(productId, formData) {
    return await fetch(`/api/products/${productId}`, {
        method: 'PUT',
        credentials: 'include',
        body: formData
    });
}

async function handleProductError(prodRes) {
    resetErrors();
    
    try {
        const errors = await prodRes.json();
        if (Array.isArray(errors)) {
            showErrors(errors);
            return;
        }
    } catch {
        // fall through
    }
    
    const text = await prodRes.text();
    alert('Failed to save product: ' + text);
}

function resetErrors() {
    const errIds = ['editName','editOriginalPrice','editDiscountPercentage','editShortDescription','editStockQuantity','editCategoryId','editBrandId','editReleaseYear','editWarrantyMonths'];
    errIds.forEach(id => { 
        $('#' + id).removeClass('is-invalid'); 
        $('#err-' + id).text(''); 
    });
}

function showErrors(errors) {
    const map = {
        name: 'editName',
        originalPrice: 'editOriginalPrice',
        discountPercentage: 'editDiscountPercentage',
        shortDescription: 'editShortDescription',
        stockQuantity: 'editStockQuantity',
        categoryId: 'editCategoryId',
        brandId: 'editBrandId',
        releaseYear: 'editReleaseYear',
        warrantyMonths: 'editWarrantyMonths'
    };
    
    errors.forEach(e => {
        const inputId = map[e.field];
        if (inputId) {
            $('#' + inputId).addClass('is-invalid');
            $('#err-' + inputId).text(e.message || 'Invalid value');
        }
    });
}

async function updateSpecs(productId) {
    const p = window.productData || {};
    const categoryId = parseInt($('#editCategoryId').val() || p.categoryId, 10);
    const specType = getSpecType(categoryId);
    
    if (!specType) return true; // Если нет спецификаций, считаем успехом
    
    const specPayload = collectSpecFormValues();
    const specRes = await fetch(`/api/specifications/${specType}/${productId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify(specPayload)
    });
    
    if (!specRes.ok) {
        await handleSpecError(specRes);
        return false; // Возвращаем false при ошибке
    }
    
    return true; // Возвращаем true при успехе
}

async function handleSpecError(specRes) {
    resetSpecErrors();
    
    try {
        const errors = await specRes.json();
        if (Array.isArray(errors)) {
            showSpecErrors(errors);
            return;
        }
    } catch {
        // fall through
    }
    
    const text = await specRes.text();
    alert('Failed to save specifications: ' + text);
}

function resetSpecErrors() {
    const $specContainer = $('#editSpecsContainer');
    $specContainer.find('input').removeClass('is-invalid');
    $specContainer.find('.invalid-feedback').text('');
}

function showSpecErrors(errors) {
    errors.forEach(e => {
        const inputId = `spec_${e.field}`.replace(/[^a-zA-Z0-9_-]/g, '_');
        const $input = $('#' + inputId);
        if ($input.length) {
            $input.addClass('is-invalid');
            // Ищем ближайший элемент invalid-feedback
            const $errorDiv = $input.siblings('.invalid-feedback');
            if ($errorDiv.length) {
                $errorDiv.text(e.message || 'Invalid value');
            }
        }
    });
}

function closeModalAndReload() {
    $('#editProductModal').modal('hide');
    setTimeout(() => { window.location.reload(); }, 1000);
}

function parseNumber(v){
    const n = parseFloat(v);
    return isNaN(n) ? null : n;
}

function collectSpecFormValues(){
    const container = document.getElementById('editSpecsContainer');
    const inputs = container.querySelectorAll('input');
    const result = {};
    inputs.forEach(inp => {
        const key = inp.id.replace(/^spec_/, '');
        if (inp.type === 'checkbox') {
            result[key] = inp.checked;
        } else if (inp.type === 'number') {
            const n = parseFloat(inp.value);
            if (!isNaN(n)) result[key] = n; else result[key] = null;
        } else {
            result[key] = inp.value;
        }
    });
    return result;
}

async function populateCategoriesAndBrands(){
    const p = window.productData || {};
    const [cats, brands] = await Promise.all([
        $.get('/api/categories').catch(()=>[]),
        $.get('/api/brands').catch(()=>[])
    ]);
    
    populateSelect('#editCategoryId', cats, p.categoryId, p.categoryName);
    populateSelect('#editBrandId', brands, p.brandId, p.brandName);
}

function populateSelect(selector, items, selectedId, selectedName) {
    const $sel = $(selector);
    if (!$sel.length) return;
    
    $sel.empty();
    items.forEach(item => {
        $sel.append($('<option>').val(item.id).text(item.name));
    });
    
    // Try to select by ID first
    if (selectedId && $sel.find(`option[value="${selectedId}"]`).length) {
        $sel.val(selectedId);
        return;
    }
    
    // Try to select by name
    if (selectedName) {
        const match = $sel.find('option').filter((i, el) => 
            $(el).text().toLowerCase() === selectedName.toLowerCase()
        );
        if (match.length) {
            $sel.val(match.val());
            return;
        }
    }
    
    // Select first item
    if (items.length > 0) {
        $sel.val(items[0].id);
    }
}

// Cart functionality
const cartApi = {
    checkCartStatus: async (productId) => {
        try {
            const response = await fetch(`/api/shopping-cart/exists/${productId}`, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include'
            });
            return response.ok ? await response.json() : false;
        } catch (error) {
            console.error('Error checking cart status:', error);
            return false;
        }
    },

    addToCart: async (productId) => {
        const response = await fetch(`/api/shopping-cart?productId=${productId}`, {
            method: 'POST',
            credentials: 'include'
        });
        if (response.status === 401) {
            const current = window.location.pathname + window.location.search;
            window.location.href = `/login?redirect=${encodeURIComponent(current)}`;
            return false;
        }
        if (!response.ok) {
            const text = await response.text();
            throw new Error(text);
        }
        return true;
    },

    removeFromCart: async (productId) => {
        const response = await fetch(`/api/shopping-cart/${productId}`, {
            method: 'DELETE',
            credentials: 'include'
        });
        if (response.status === 401) {
            const current = window.location.pathname + window.location.search;
            window.location.href = `/login?redirect=${encodeURIComponent(current)}`;
            return false;
        }
        return true;
    }
};

async function handleCartClick() {
    const cartBtn = document.getElementById('cartBtn');
    const productId = cartBtn.dataset.productId;
    const stockQuantity = parseInt(cartBtn.dataset.stockQuantity);

    if (stockQuantity <= 0) return;

    try {
        const isInCart = await cartApi.checkCartStatus(productId);

        if (isInCart) {
            const ok = await cartApi.removeFromCart(productId);
            if (ok === false) return; // redirected to login
        } else {
            const ok = await cartApi.addToCart(productId);
            if (ok === false) return; // redirected to login
        }

        updateCartButton(!isInCart);
    } catch (error) {
        alert('Не удалось обновить корзину: ' + error.message);
    }
}

function updateCartButton(isInCart) {
    const cartBtn = document.getElementById('cartBtn');
    const stockQuantity = parseInt(cartBtn.dataset.stockQuantity);

    cartBtn.disabled = isInCart || stockQuantity <= 0;

    if (isInCart || stockQuantity <= 0) {
        cartBtn.classList.add('btn-cart-disabled');
    } else {
        cartBtn.classList.remove('btn-cart-disabled');
    }
}

// Initialize cart functionality
async function initializeCart() {
    const cartBtn = document.getElementById('cartBtn');
    try {
        const isInCart = await cartApi.checkCartStatus(cartBtn.dataset.productId);
        updateCartButton(isInCart);
    } catch (error) {
        console.error('Error initializing cart:', error);
        updateCartButton(false);
    }
}

// Initialize cart
document.addEventListener('DOMContentLoaded', function() {
    const cartBtn = document.getElementById('cartBtn');
    if (cartBtn) {
        initializeCart();
        cartBtn.addEventListener('click', handleCartClick);
    }
});

// Wishlist functionality
document.addEventListener('DOMContentLoaded', function() {
    const wishlistBtn = document.getElementById('wishlistBtn');
    if (!wishlistBtn) return;
    
    const productId = wishlistBtn.dataset.productId;

    const wishlistApi = {
        checkStatus: async (productId) => {
            const response = await fetch(`/api/wishlist/exists/${productId}`, {
                method: 'GET',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include'
            });
            return response.ok ? await response.json() : false;
        },

        addToWishlist: async (productId) => {
            const response = await fetch(`/api/wishlist?productId=${productId}`, {
                method: 'POST',
                credentials: 'include'
            });
            if (response.status === 401) {
                const current = window.location.pathname + window.location.search;
                window.location.href = `/login?redirect=${encodeURIComponent(current)}`;
                return false;
            }
            if (!response.ok) {
                const text = await response.text();
                throw new Error(text);
            }
            return true;
        },

        removeFromWishlist: async (productId) => {
            const response = await fetch(`/api/wishlist/${productId}`, {
                method: 'DELETE',
                credentials: 'include'
            });
            if (response.status === 401) {
                const current = window.location.pathname + window.location.search;
                window.location.href = `/login?redirect=${encodeURIComponent(current)}`;
                return false;
            }
            return true;
        }
    };

    async function handleWishlistClick() {
        try {
            const isInWishlist = await wishlistApi.checkStatus(productId);

            if (isInWishlist) {
                const ok = await wishlistApi.removeFromWishlist(productId);
                if (ok !== false) updateWishlistButton(false);
            } else {
                const ok = await wishlistApi.addToWishlist(productId);
                if (ok !== false) updateWishlistButton(true);
            }
        } catch (error) {
            alert('Не удалось обновить вишлист: ' + error.message);
        }
    }

    async function checkWishlistStatus() {
        const isInWishlist = await wishlistApi.checkStatus(productId);
        updateWishlistButton(isInWishlist);
    }

    function updateWishlistButton(isInWishlist) {
        const icon = wishlistBtn.querySelector('i');
        if (isInWishlist) {
            icon.classList.remove('bi-heart');
            icon.classList.add('bi-heart-fill');
        } else {
            icon.classList.remove('bi-heart-fill');
            icon.classList.add('bi-heart');
        }
    }

    // Initialize wishlist
    checkWishlistStatus();
    wishlistBtn.addEventListener('click', handleWishlistClick);
});