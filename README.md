# StockFlow Backend Engineering Intern Case Study

## Overview

This is my solution to the Backend Engineering Intern Take-Home Case Study for **StockFlow**, a B2B inventory management platform.

The case covers:

- ✅ Part 1: Code Review & Debugging (Java)
- ✅ Part 2: Database Design (SQL)
- ✅ Part 3: API Implementation (Java)

---

## 🧩 Part 1: Create Product API

See [`part1_create_product.java`](./part1_create_product.java)

### Key Fixes
- Validates input
- Enforces SKU uniqueness
- Atomic DB commits
- Uses BigDecimal for price
- Handles exceptions

---

## 🧱 Part 2: Database Schema

See [`schema.sql`](./schema.sql)

### Features
- Companies with multiple warehouses
- Products stored in multiple warehouses
- Tracks inventory changes
- Supports product bundles and suppliers

---

## 🔔 Part 3: Low Stock Alert API

See [`part3_low_stock_alerts.java`](./part3_low_stock_alerts.java)

### Features
- Alerts based on recent sales
- Per-product thresholds
- Multi-warehouse handling
- Includes supplier info

---

## 🚧 Assumptions

- SKU is globally unique
- Bundles not nested
- Low stock threshold is per product
- Sales activity = last 30 days

---

## 📁 Technologies

- Java (Spring Boot style)
- SQL (PostgreSQL-style DDL)
- Markdown documentation
