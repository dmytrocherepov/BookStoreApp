# <h1 align="center">Online Book Store</h1>

The primary objective of the Online Book Store app is to provide an efficient and user-friendly platform for buying books. This platform benefits both customers and administrators, allowing customers to securely explore and purchase books. Simultaneously, administrators possess the ability to perform various operations such as adding, deleting, or updating books, as well as monitoring and confirming orders.







## Project Technologies
___
`Techonologies are use to buiild Online Book Store: `
- **Programming Language:** `Java 17`
- **Spring Framework:** `Spring Boot v3.1.5, Spring Data, Spring Security v6.1.5 (Authentication using JWT token)`
- **Database Management:** `MySQL 8.0.33, Hibernate, Liquibase v4.20.0`
- **Testing:** `JUnit 5, Mockito, TestContainers v1.18.0`
- **Deployment and Cloud Services:** `Docker 3.8`
- **Additional instruments:** `Maven, Lombok, Mapstruct`
- **Documentation:** `Swagger`

## Class Diagram
___
![img.png](img.png)

<a name="entities"></a>
## Entities
_____

1. **User** - represents any user.
2. **Role** - represents user's role in app (USER, ADMIN).
3. **Book** - represents book available in the store.
4. **Category** - represents book categories.
5. **ShoppingCart** - represents a user's shopping cart that consist of cart items.
6. **CartItem** - represents items in a user's shopping cart.
7. **Order** - represents a user's order.
8. **OrderItem** - represents items in a user's order.


<a name="endpoints"></a>
## Endpoints
___

### Authentication Controller  :

| Method | Endpoint          | Description             | Roles  |
|--------|-------------------|-------------------------|--------|
| POST   | api/auth/register | register a new user     | Anyone |
| POST   | api/auth/login    | login a registered user | Anyone |

### Category Management

| Method | Endpoint                  | Description                            | Roles |
|--------|---------------------------|----------------------------------------|-------|
| POST   | api/categories            | Creates a new category                 | ADMIN |
| GET    | api/categories            | Returns a list of all categories       | USER  |
| GET    | api/categories/{id}       | Returns category with id (if exists)   | USER  |
| PUT    | api/categories/{id}       | Updates a category with id (if exists) | ADMIN |
| DELETE | api/categories/{id}       | Deletes a category with id (if exists) | ADMIN |
| GET    | api/categories/{id}/books | Returns all books by category          | USER  |

### Book Management

| Method | Endpoint         | Description                                             | Roles |
|--------|------------------|---------------------------------------------------------|-------|
| GET    | api/books        | Get a list of all available books on page with size = 5 | USER  |
| GET    | api/books/{id}   | Get book by id if present                               | USER  |
| POST   | api/books        | Creates a new book                                      | ADMIN |
| PUT    | api/books/{id}   | Updates book with id (if exists)                        | ADMIN |
| GET    | api/books/search | Searches book with parameters                           | USER  |
| DELETE | api/books/{id}   | Deletes a book with id (if exists)                      | ADMIN |


### Cart Management 

| Method | Endpoint            | Description                            | Roles |
|--------|---------------------|----------------------------------------|-------|
| GET    | api/cart            | Returns user's shopping cart           | USER  |
| POST   | api/cart            | Adds cart item to user's shopping cart | USER  |
| PUT    | api/cart-items/{id} | Updates cart item with id (if exists)  | USER  |
| DELETE | api/cart-items/{id} | Deletes cart item                      | USER  |


### Order Management

| Method | Endpoint                           | Description                                         | Roles |
|--------|------------------------------------|-----------------------------------------------------|-------|
| POST   | api/orders                         | Creates an order withing Shopping cart is not Empty | USER  |
| GET    | api/orders                         | Gets user's order                                   | USER  |
| PATCH  | api/orders/{id}                    | Updates order status                                | ADMIN |
| GET    | api/orders/{orderId}/items         | Get all items from order                            | USER  |
| GET    | api/orders{orderId}/items/{itemId} | Gets order item by id and order id                  | USER  |

## Demonstration
___
    
Here you can watch a little guide about Book Store functionality:

In progress...


## How to use
___

1. **Clone the repository from GitHub:** [GitHub repositry](https://github.com/dmytrocherepov/BookStoreApp)
2. **Create a `.env` file with necessary environment variables (check `.env.sample`)**
3. **Run `mvn clean package` int terminal**
4. **Install Docker:**
    [Docker Install](https://www.docker.com/products/docker-desktop/)
5. **Run `docker-compose build` , and `docker-compose up` to start the Docker containers**
6. **The application should be running locally at http://localhost:8082**
7. **Test the application using swagger http://localhost:8088/swagger-ui/index.html**

