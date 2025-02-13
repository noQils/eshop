## Reflection 1
* ### Clean Code Principles  
This project follows clean coding practices to keep everything organized and easy to manage. Each part of the code has a clear job: `ProductController` handles requests, `ProductService` takes care of the business logic, and `ProductRepository` deals with data storage. Keeping things separate like this makes the code easier to read and update.

Meaningful names are used for everything methods like `createProductPost` and `editProductPost` clearly describe what they do. To avoid repeating the same logic in multiple places, shared functionality is kept in the service and repository layers, following the DRY (Don’t Repeat Yourself) principle.

Dependency injection, using `@Autowired`, keeps components loosely connected. This makes the code more flexible and easier to test.

* ### Security and Reliability  
Some security best practices are in place to keep the app reliable. The `Product` model automatically assigns a unique `productId` with `UUID.randomUUID().toString()`, preventing duplicate IDs. That said, input validation could be improved like making sure `productName` isn’t empty and `productQuantity` isn’t negative.

Basic error handling is included in `ProductServiceImpl`. If a product isn’t found during an edit, the code throws an exception instead of failing silently. Adding custom exceptions would make error messages more helpful and specific.

To keep things secure, internal data structures aren’t exposed directly. Instead, `ProductController` passes `Product` objects between layers, keeping a clean separation between the API and the database.  
