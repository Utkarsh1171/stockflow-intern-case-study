-- Companies
CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

-- Warehouses
CREATE TABLE warehouses (
    id SERIAL PRIMARY KEY,
    company_id INT REFERENCES companies(id),
    name VARCHAR NOT NULL
);

-- Products
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    sku VARCHAR UNIQUE NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    is_bundle BOOLEAN DEFAULT FALSE,
    low_stock_threshold INT DEFAULT 20
);

-- Inventory
CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    product_id INT REFERENCES products(id),
    warehouse_id INT REFERENCES warehouses(id),
    quantity INT NOT NULL CHECK (quantity >= 0),
    UNIQUE (product_id, warehouse_id)
);

-- Inventory Changes
CREATE TABLE inventory_changes (
    id SERIAL PRIMARY KEY,
    inventory_id INT REFERENCES inventory(id),
    change INT NOT NULL,
    reason VARCHAR,
    created_at TIMESTAMP DEFAULT now()
);

-- Suppliers
CREATE TABLE suppliers (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    contact_email VARCHAR
);

-- Product-Supplier Link
CREATE TABLE product_suppliers (
    id SERIAL PRIMARY KEY,
    product_id INT REFERENCES products(id),
    supplier_id INT REFERENCES suppliers(id)
);

-- Product Bundles
CREATE TABLE product_bundles (
    id SERIAL PRIMARY KEY,
    bundle_id INT REFERENCES products(id),
    component_id INT REFERENCES products(id),
    quantity INT NOT NULL
);
