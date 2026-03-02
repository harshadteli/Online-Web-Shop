# Data Flow Diagrams (DFD) - Online Watch Shop

This document provides the Level 0, Level 1, and Level 2 DFDs for the Online Watch Shop project, derived from the Java Servlets and MySQL database architecture.

## Level 0: Context Diagram
The Context Diagram shows the system as a single process and its interactions with external entities (Customer and Admin).

[Download Level 0 PNG](0.png)

```mermaid
graph TD
    User((Customer)) -- "Registration Info" --> System[Online Watch Shop System]
    User -- "Login Credentials" --> System
    User -- "Inquiry/Contact" --> System
    User -- "Order & Payment Info" --> System
    
    System -- "Browsing Data (Menu/About)" --> User
    System -- "Registration Success/Failure" --> User
    System -- "Order Confirmation" --> User
    
    Admin((Admin)) -- "Request Reports" --> System
    System -- "Sales/Order Data" --> Admin
```

---

## Level 1: Functional DFD
Level 1 breaks down the system into its primary functional processes.

[Download Level 1 PNG](1.png)

```mermaid
graph TD
    subgraph S1 [Users DB]
        D1[(Users Table)]
    end
    subgraph S2 [Orders DB]
        D2[(Orders Table)]
    end
    subgraph S3 [Contact DB]
        D3[(Inquiries Table)]
    end

    C((Customer)) -- "Sign Up" --> P1[1.0 User Registration]
    P1 -- "Store User" --> D1
    P1 -- "Success Msg" --> C

    C -- "Login" --> P2[2.0 Authentication]
    D1 -- "Verify" --> P2
    P2 -- "Access" --> C

    C -- "Place Order" --> P3[3.0 Order Management]
    P3 -- "Store Order" --> D2
    P3 -- "Confirmation" --> C

    C -- "Send Inquiry" --> P4[4.0 Contact Management]
    P4 -- "Store Msg" --> D3

    A((Admin)) -- "View Sales" --> P5[5.0 Admin Reporting]
    D2 -- "Retrieve Data" --> P5
    P5 -- "Report JSON" --> A
```

---

## Level 2: Detailed DFD (Order Processing)
Level 2 focuses on the details of the **Order Management (Process 3.0)**, involving Cart, Checkout, and Payment.

[Download Level 2 PNG](2.png)

```mermaid
graph TD
    C((Customer)) -- "Select Items" --> P3_1[3.1 Cart Mgmt]
    P3_1 -- "Update Items" --> P3_1
    P3_1 -- "Checkout Request" --> P3_2[3.2 Checkout Processing]
    
    C -- "Delivery Details" --> P3_2
    P3_2 -- "Total Amount" --> P3_3[3.3 Payment Processing]
    
    C -- "Payment Info" --> P3_3
    
    P3_3 -- "Save Record" --> D2[(Orders / Payments DB)]
    P3_3 -- "Redirect Success" --> C
```

> [!NOTE]
> These diagrams reflect the logic in [RegistrationServlet.java](file:///d:/online-watch-shop-main/WatchShop/src/java/RegistrationServlet.java), [ProcessOrderServlet.java](file:///d:/online-watch-shop-main/WatchShop/src/java/ProcessOrderServlet.java), [PaymentServlet.java](file:///d:/online-watch-shop-main/WatchShop/src/java/PaymentServlet.java), and [AdminServlet.java](file:///d:/online-watch-shop-main/WatchShop/src/java/AdminServlet/AdminServlet.java).
