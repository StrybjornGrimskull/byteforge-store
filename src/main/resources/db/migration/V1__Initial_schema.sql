-- Main tables (common for all products)
CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,  
    slug VARCHAR(100) NOT NULL UNIQUE   
);

CREATE TABLE brands (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE, 
    logo_url VARCHAR(255)
);


CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL, 
    price DECIMAL(10,2) NOT NULL,
    category_id INT NOT NULL REFERENCES categories(id) ON DELETE CASCADE,
    brand_id INT NOT NULL REFERENCES brands(id) ON DELETE CASCADE,
    warranty_months INT NOT NULL DEFAULT 24,
    release_year INT NOT NULL,
    short_description TEXT NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

-- Product-specific tables
CREATE TABLE gpu_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    memory_size INT NOT NULL,           -- 24 (GB)
    memory_type VARCHAR(20) NOT NULL,   -- "GDDR6X"
    bus_width INT NOT NULL,             -- 384 (bit)
    base_clock INT NOT NULL,                     -- 2235 (MHz)
    boost_clock INT NOT NULL,                    -- 2520 (MHz)
    tdp INT NOT NULL,                   -- 450 (W)
    length INT NOT NULL,                         -- 304 (mm)
    display_outputs VARCHAR(255) NOT NULL        -- "3x DisplayPort, 1x HDMI"
);

CREATE TABLE cpu_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    cores INT NOT NULL,                 -- 16
    threads INT NOT NULL,               -- 32
    base_clock DECIMAL(4,2) NOT NULL,   -- 4.5 (GHz)
    boost_clock DECIMAL(4,2) NOT NULL,           -- 5.7 (GHz)
    socket VARCHAR(50) NOT NULL,        -- "AM5"
    cache_size INT NOT NULL,                     -- 80 (MB)
    tdp INT NOT NULL,                   -- 170 (W)
    integrated_gpu BOOLEAN DEFAULT FALSE
);

CREATE TABLE motherboard_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    socket VARCHAR(50) NOT NULL,        -- "LGA 1700"
    chipset VARCHAR(50) NOT NULL,       -- "Z790"
    form_factor VARCHAR(20) NOT NULL,   -- "ATX", "mATX"
    memory_slots INT NOT NULL,          -- 4
    max_memory INT NOT NULL,                     -- 128 (GB)
    memory_type VARCHAR(20) NOT NULL,            -- "DDR5"
    m2_slots INT NOT NULL,                       -- 3
    sata_ports INT NOT NULL                     -- 6
);

CREATE TABLE ram_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    memory_size INT NOT NULL,           -- 32 (GB)
    modules_count INT NOT NULL,         -- 2 (кита)
    speed INT NOT NULL,                 -- 6000 (MHz)
    type VARCHAR(20) NOT NULL,          -- "DDR5"
    timings VARCHAR(20) NOT NULL,                -- "CL36-36-36-96"
    voltage DECIMAL(3,2) NOT NULL                -- 1.35 (V)
);

CREATE TABLE psu_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    wattage INT NOT NULL,               -- 850 (W)
    form_factor VARCHAR(20) NOT NULL,   -- "ATX"
    efficiency_cert VARCHAR(20) NOT NULL,        -- "80+ Gold"
    modularity VARCHAR(20) NOT NULL,             -- "Полномодульный"
    pcie_8pin_connectors INT NOT NULL,           -- 3
    sata_connectors INT NOT NULL                 -- 8
);

CREATE TABLE case_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    form_factor VARCHAR(50) NOT NULL,   -- "Mid-Tower"
    motherboard_support VARCHAR(255) NOT NULL,   -- "ATX, mATX, Mini-ITX"
    max_gpu_length INT NOT NULL,                 -- 350 (mm)
    max_cpu_cooler_height INT NOT NULL,          -- 165 (mm)
    fans_included INT NOT NULL,                  -- 3
    radiator_support VARCHAR(255) NOT NULL       -- "360mm (Front)"
);

CREATE TABLE monitor_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    screen_size DECIMAL(4,1) NOT NULL,          -- 27.0 (дюймов)
    resolution VARCHAR(20) NOT NULL,            -- "2560x1440"
    panel_type VARCHAR(30) NOT NULL,            -- "IPS", "VA", "OLED"
    refresh_rate INT NOT NULL,                  -- 144 (Hz)
    response_time INT NOT NULL                           -- 1 (ms)
);

CREATE TABLE ssd_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    capacity INT NOT NULL,               -- 1000 (GB)
    form_factor VARCHAR(20) NOT NULL,    -- "M.2", "2.5""
    interface VARCHAR(20) NOT NULL,      -- "NVMe PCIe 4.0", "SATA III"
    read_speed INT NOT NULL,             -- 7000 (MB/s)
    write_speed INT NOT NULL,            -- 5000 (MB/s)
    memory_type VARCHAR(30) NOT NULL,              -- "TLC", "QLC", "3D NAND"
    endurance_tbw INT NOT NULL,                   -- 600 (TBW)
    dram_cache BOOLEAN DEFAULT FALSE,                  -- TRUE/FALSE
    encryption VARCHAR(30) NOT NULL,              -- "AES 256-bit", "None"
    thickness DECIMAL(3,1) NOT NULL               -- 2.5 (mm) для M.2
);

CREATE TABLE wireless_mice_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    sensor_type VARCHAR(50) NOT NULL,      -- "Optical", "Laser"
    sensor_model VARCHAR(50),              -- "PixArt PAW3370", "Hero 25K"
    max_dpi INT NOT NULL,                  -- 16000 (максимальный DPI)
    buttons INT NOT NULL,                  -- 6 (общее количество кнопок)
    wireless_tech VARCHAR(30) NOT NULL,    -- "Bluetooth", "2.4GHz", "Dual Mode"
    polling_rate INT NOT NULL,                      -- 1000 (Hz)
    weight INT NOT NULL,                            -- 75 (grams)
    rgb_lighting BOOLEAN DEFAULT FALSE,                  -- TRUE/FALSE
    battery_type VARCHAR(30) NOT NULL,              -- "Rechargeable", "AA/AAA", "USB-C"
    battery_life INT NOT NULL,                      -- 70 (hours) в активном режиме
    standby_battery_life INT NOT NULL,              -- 300 (hours) в режиме ожидания
    charging_time INT NOT NULL,                     -- 2 (hours) время зарядки
    onboard_memory BOOLEAN DEFAULT FALSE,                -- Наличие памяти для профилей
    warranty_months INT NOT NULL                    -- 24 (месяцев гарантии)
);

CREATE TABLE wired_mice_specs (
    product_id INT PRIMARY KEY REFERENCES products(id),
    sensor_type VARCHAR(50) NOT NULL,      -- "Optical", "Laser"
    sensor_model VARCHAR(50),              -- "PixArt PMW3389", "TrueMove Core"
    max_dpi INT NOT NULL,                  -- 16000
    adjustable_dpi BOOLEAN DEFAULT FALSE,        -- TRUE/FALSE
    buttons INT NOT NULL,                  -- 6 (общее количество)
    cable_length INT NOT NULL,             -- 180 (см)
    cable_type VARCHAR(30) NOT NULL,                -- "Braided", "Rubber", "Detachable"
    usb_connector VARCHAR(20) NOT NULL,             -- "USB-A", "USB-C", "Micro-USB"
    weight INT NOT NULL,                            -- 85 (грамм)
    rgb_lighting BOOLEAN DEFAULT FALSE,                  -- TRUE/FALSE
    onboard_memory BOOLEAN DEFAULT FALSE,                -- TRUE/FALSE (память для профилей)
    warranty_months INT NOT NULL                    -- 24 (месяцев)
);

CREATE TABLE wireless_keyboards_spec (
    product_id INT PRIMARY KEY REFERENCES products(id),
    layout VARCHAR(20) NOT NULL,           -- "Full-size", "TKL", "60%"
    switch_type VARCHAR(30) NOT NULL,      -- "Mechanical", "Membrane"
    switch_brand VARCHAR(30) NOT NULL,              -- "Cherry MX", "Gateron", "Razer"
    switch_model VARCHAR(50) NOT NULL,              -- "Red", "Brown", "Blue"
    wireless_tech VARCHAR(30) NOT NULL,    -- "Bluetooth", "2.4GHz", "Both"
    rgb_lighting BOOLEAN DEFAULT FALSE,                  -- TRUE/FALSE
    hot_swappable BOOLEAN DEFAULT FALSE,                 -- TRUE/FALSE
    actuation_force DECIMAL(3,1) NOT NULL,          -- 45.0 (g)
    travel_distance DECIMAL(3,1) NOT NULL,          -- 2.0 (mm)
    weight INT NOT NULL,                            -- 900 (grams)
    battery_life INT NOT NULL,             -- 200 (hours)
    charging_type VARCHAR(20) NOT NULL,             -- "USB-C", "Micro-USB", "AA batteries"
    multi_device_pairing BOOLEAN DEFAULT FALSE         -- TRUE/FALSE
);

CREATE TABLE wired_keyboards_spec (
    product_id INT PRIMARY KEY REFERENCES products(id),
    layout VARCHAR(20) NOT NULL,           -- "Full-size", "TKL", "60%"
    switch_type VARCHAR(30) NOT NULL,      -- "Mechanical", "Membrane"
    switch_brand VARCHAR(30) NOT NULL,              -- "Cherry MX", "Gateron", "Razer"
    switch_model VARCHAR(50) NOT NULL,              -- "Red", "Brown", "Blue"
    rgb_lighting BOOLEAN DEFAULT FALSE,                  -- TRUE/FALSE
    hot_swappable BOOLEAN DEFAULT FALSE,                 -- TRUE/FALSE
    actuation_force DECIMAL(3,1) NOT NULL,          -- 45.0 (g)
    travel_distance DECIMAL(3,1) NOT NULL,          -- 2.0 (mm)
    weight INT NOT NULL,                            -- 900 (grams)
    cable_length DECIMAL(4,1) NOT NULL,             -- 1.8 (meters)
    usb_passthrough BOOLEAN DEFAULT FALSE,               -- TRUE/FALSE
    detachable_cable BOOLEAN DEFAULT FALSE               -- TRUE/FALSE
);

CREATE TABLE stock_quantity(
    product_id INT PRIMARY KEY REFERENCES products(id),
    quantity INT DEFAULT 0
);
 