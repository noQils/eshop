## Reflection 1
* ### Clean Code Principles  
This project follows clean coding practices to keep everything organized and easy to manage. Each part of the code has a clear job: `ProductController` handles requests, `ProductService` takes care of the business logic, and `ProductRepository` deals with data storage. Keeping things separate like this makes the code easier to read and update.

Meaningful names are used for everything methods like `createProductPost` and `editProductPost` clearly describe what they do. To avoid repeating the same logic in multiple places, shared functionality is kept in the service and repository layers, following the DRY (Don’t Repeat Yourself) principle.

Dependency injection, using `@Autowired`, keeps components loosely connected. This makes the code more flexible and easier to test.

* ### Security and Reliability  
Some security best practices are in place to keep the app reliable. The `Product` model automatically assigns a unique `productId` with `UUID.randomUUID().toString()`, preventing duplicate IDs. That said, input validation could be improved like making sure `productName` isn’t empty and `productQuantity` isn’t negative.

Basic error handling is included in `ProductServiceImpl`. If a product isn’t found during an edit, the code throws an exception instead of failing silently. Adding custom exceptions would make error messages more helpful and specific.

To keep things secure, internal data structures aren’t exposed directly. Instead, `ProductController` passes `Product` objects between layers, keeping a clean separation between the API and the database.  

## Reflection 2
* ### Clean Code and Maintainability

The functional test suite was designed with clean code principles in mind, ensuring that each test has a clear purpose and follows a structured approach. The separation of concerns is well maintained, with `CreateProductFunctionalTest` focusing on product creation, `HomePageFunctionalTest` validating homepage behavior, and `ProductTest` ensuring model correctness.

Efforts were made to keep the tests readable and easy to maintain. Each test case follows a consistent format, making it straightforward to understand the intent behind each validation. The use of Selenium for UI testing is well-structured, allowing automated checks on key user interactions. Additionally, dependency injection is used effectively with `@ExtendWith(SeleniumJupiter.class)`, making the tests more flexible and scalable.

* ### Code Quality and Improvements

While the tests are effective, there are still a few small refinements that could further enhance maintainability. Some setup logic, such as initializing `baseUrl`, appears in multiple test files. Extracting this into a common utility or base class would improve reusability and reduce duplication.

Another minor improvement would be replacing hardcoded values (like `"Sampo Cap Bambang"` and `"ADV Shop"`) with constants. This would make updates easier and improve clarity. The locator strategies in Selenium tests are functional, but creating helper methods for frequently used element selectors could further improve readability.

* ### Overall Impact

The addition of this functional test suite significantly improves test coverage and ensures the application behaves as expected across different scenarios. The structure is clean, the responsibilities are well-defined, and the tests provide reliable validation for the core functionality. With a few refinements, the maintainability of the suite can be improved even further, making future enhancements smoother.  