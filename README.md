# Online Shop Backend

Backend REST API for an online shop management system.

## Tech Stack
- Java 26
- Spring Boot 4.0.6
- H2 In-Memory Database
- Lombok

## How to Run
1. Open project in IntelliJ IDEA
2. Run `OnlineShopBackendApplication.java`
3. Server starts on `http://localhost:8080`

## Default Admin
- Email: `admin@email.com`
- Password: `admin`

## API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | /auth/registerBuyer | Register a new buyer |
| POST | /auth/registerSeller | Register a new seller |
| POST | /auth/login | Login |

### Products
| Method | URL | Description |
|--------|-----|-------------|
| GET | /products | Get all products |
| POST | /products | Add a product (approved seller only) |
| DELETE | /products/{id}?sellerEmail= | Delete own product |

### Offers
| Method | URL | Description |
|--------|-----|-------------|
| POST | /offers | Make an offer |
| PUT | /offers/{id}/approve?sellerEmail= | Approve offer |
| PUT | /offers/{id}/reject?sellerEmail= | Reject offer |

### Purchase
| Method | URL | Description |
|--------|-----|-------------|
| POST | /purchase/{productId}?buyerEmail= | Purchase a product |
| GET | /history | Get sale history |

### Admin
| Method | URL | Description |
|--------|-----|-------------|
| GET | /admin/sellers | Get all sellers |
| PUT | /admin/approveSeller/{id} | Approve a seller |
| PUT | /admin/deactivateSeller/{id} | Deactivate a seller |

## JSON Examples

### Register Buyer/Seller
```json
{
  "email": "user@test.com",
  "password": "1234"
}
```

### Add Product
```json
{
  "name": "Phone",
  "price": 800,
  "description": "Samsung Galaxy",
  "negotiable": true,
  "minimumPrice": 600,
  "sellerEmail": "seller@test.com"
}
```

### Make Offer
```json
{
  "productId": 1,
  "buyerEmail": "buyer@test.com",
  "offeredPrice": 700
}
```

## Business Rules
- Buyer accounts are active immediately after registration
- Seller accounts require admin approval before adding products
- Admin can deactivate a seller at any time (account remains in DB)
- Offers below minimum price are automatically rejected
- When a product is purchased, it and all its offers are deleted
- Sale history is preserved even after product deletion