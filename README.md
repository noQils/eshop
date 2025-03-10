# Module 1
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

# Module 2
## Reflection
1. **Code quality issues and how it was fixed:**
    * **Empty Function:** fill the function with comment explaining why the function is empty
        * Issue:
             ```
            @BeforeEach
            void setUp() {
            }
             ```
        * Fix:
             ```
            @BeforeEach
            void setUp() {
                // This method is kept for maintainability, could be useful for future needs
            }
             ```
    * **Unnecessary Exception Throw:** Deleted the exception throw as it is not necessary
        * Issue:
            ```
            @Test
            void testCreateProductPage() throws Exception{
                String viewName = productController.createProductPage(model);
       
                // verify expected view name
                assertEquals("CreateProduct", viewName);
            }
            ```
        * Fix:
            ```
            @Test
            void testCreateProductPage(){
                String viewName = productController.createProductPage(model);
       
                // verify expected view name
                assertEquals("CreateProduct", viewName);
            }
            ```
    * **Unused Variable:** Deleted all unused variable
    * **Field injection:** Used constructor injection instead
        * Issue:
            ```
            @Component
            public class ProductServiceImpl implements ProductService {
               
                @Autowired
                private ProductRepository productRepository; // field injection
            }
       
            ```
        * Fix:
            ```
            @Component
            public class ProductServiceImpl implements ProductService {
       
                private final ProductRepository productRepository;
           
                @Autowired
                public ProductServiceImpl(ProductRepository productRepository) { // constructor injection
                    this.productRepository = productRepository;
                }
            }
            ```
            * **Unused Imports:** Removed unnecessary imports


2. **CI/CD Workflows Implementation:**
    * My CI/CD implementation effectively supports Continuous Integration and Continuous Deployment by automating testing, security checks, and deployment. The ci.yml workflow ensures that unit tests run on all branches to maintain code quality before merging, while sonar.yml adds an extra layer of verification by performing code analysis. For Continuous Deployment, koyeb_deploy.yml automatically deploys the application to Koyeb whenever changes are pushed to the main branch, ensuring a smooth and efficient release process. Additionally, scorecard.yml enhances security by analyzing supply chain risks, reinforcing the reliability of my pipeline. Overall, my CI/CD setup streamlines development, maintains quality, and enables rapid deployment, aligning well with best practices in software development.

# Module 3
## Reflection
1. **Principles I applied in my project**
   * **SRP (Single Responsibility Principle):**
     * Moved CarController class from ProductController.java to its own file CarController.java
     * Removed the ProductController extension in CarController class
   * **OCP (Open-Closed Principle):**
     * Added interfaces for extension
     * Added abstract classes in repository, service, and controller to allow entities extension and prevent modification
     * Implemented constructor injection for classes in repository, service, and controller
   * **ISP (Interface Segregation Principle):**
     * Split the interface in repository into two groups, grouped by CUD and Read methods
     * Split the interface in service into two groups, grouped by Get and Post methods


2. **Advantages of applying SOLID principles**  
Applying SOLID principles in this project provides significant advantages, making the codebase maintainable, scalable, and flexible. Here’s how each principle applied (SRP, OCP, ISP) benefits the system:

   * **Single Responsibility Principle (SRP):** Easier Maintenance & Debugging  
   Each class in this project has **a single responsibility**, ensuring that modifications are isolated to specific parts of the system.
      - **Example:** `CarRepository` is solely responsible for database operations related to `Car`, while `CarServiceImpl` contains the business logic.
      - **Advantage:** If changes are needed in data storage, only the repository class requires modification, without affecting the service layer.

   * **Open-Closed Principle (OCP):** Easier Feature Expansion Without Code Modification  
   The system is **open for extension but closed for modification**, allowing new features to be introduced without altering existing code.
      - **Example:** `AbstractRepository` provides generic CRUD operations, enabling repositories like `CarRepository` and `ProductRepository` to extend it.
      - **Advantage:** If a new repository, such as `BikeRepository`, needs to be added, it can be implemented without modifying existing repository logic, reducing the risk of introducing bugs.

   * **Interface Segregation Principle (ISP):** Clean & Focused Code  
   The project follows **small and specific interfaces**, preventing unnecessary dependencies and ensuring that classes implement only the methods they require.
      - **Example:** Instead of a single, bloated interface, `ObjectGetService` is responsible for **retrieval operations** (`findAll()`, `findById()`), while `ObjectPostService` handles **modification operations** (`create()`, `edit()`, `delete()`).
      - **Advantage:** This separation ensures that classes using `ObjectGetService` are not forced to implement unnecessary methods related to object creation and deletion.


3. **Disadvantages of NOT applying SOLID principles**  
   * **Violating Single Responsibility Principle (SRP):** Difficult Maintenance & Debugging  
   When a class handles **multiple responsibilities**, any change affects unrelated functionalities, making maintenance complex.
     - **Example:** If `CarRepository` handled both **database operations and business logic**, modifying how cars are stored would also impact how they are validated or manipulated.
     - **Problem:** A small change in database logic could unexpectedly break business rules, increasing the risk of **bugs and unintended side effects**.  

   * **Violating Open-Closed Principle (OCP):** Harder to Add New Features  
     When code is not **open for extension but closed for modification**, every new feature requires **modifying existing classes**, increasing the risk of introducing bugs.
     - **Example:** If `AbstractRepository` did not exist and each repository had **duplicated CRUD methods**, adding a new repository (`BikeRepository`) would require copying and modifying code instead of simply extending a base class.
     - **Problem:** Code duplication leads to **more maintenance overhead**, and every change requires modifying multiple files instead of just extending functionality.  

   * **Violating Interface Segregation Principle (ISP):** Unnecessary Dependencies & Code Bloat   
     If interfaces are **too large and force classes to implement unused methods**, the project becomes bloated and harder to manage.
     - **Example:** If `ObjectGetService` and `ObjectPostService` were combined into one large interface, `findAll()` and `findById()` would be forced into repositories that only need `create()`, `edit()`, and `delete()`.
     - **Problem:** Classes end up **implementing methods they don’t need**, leading to unnecessary dependencies and **violating encapsulation**.  
