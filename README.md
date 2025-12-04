# Library Management System (POJO Version)

This is a **plain Java implementation** of a Library Management System designed to demonstrate **layered architecture, business logic, and object-oriented principles** before moving to frameworks like Spring Boot.

---

## **Project Structure**

### 1. Entities (`org.example.entities`)
- **Book**
    - `id`, `title`, `author`, `isbn`, `availableCopies`, `totalCopies`
- **Member**
    - `id`, `name`, `email`, `membershipDate`, `borrowedBooks` (max 3)
- **Transactions**
    - `id`, `book`, `member`, `borrowDate`, `returnDate`, `status` (ACTIVE, RETURNED, OVERDUE)

### 2. Repositories (`org.example.repositories`)
In-memory data storage using `HashMap`.

- **BookRepository**: CRUD operations, search by title/author
- **MemberRepository**: CRUD operations, find by email
- **TransactionRepository**: CRUD operations, find active/overdue transactions

### 3. Services (`org.example.services`)
Business logic layer handling validation and rules.

- **BookService**
    - Add/remove books
    - Search by title/author
    - Check availability
    - Validate book data (ISBN, title, author, copies)
- **MemberService**
    - Register/remove members
    - View borrowing history
    - Validate member data (name, email, membership date)
- **TransactionService**
    - Borrow/return books
    - List all, active, or overdue transactions
    - Validate transactions (book availability, member existence)

### 4. Main Application
- **Main.java** demonstrates:
    - Manual creation of books and members
    - Borrowing and returning books
    - Checking available copies
    - Viewing borrowing history and all transactions

---

## **Key Features Demonstrated**

- **Separation of concerns**
    - Entities represent data
    - Repositories handle storage
    - Services enforce business rules
- **Business rules implemented**
    - Max 3 books per member
    - Book must have available copies
    - Transactions can be active, returned, or overdue
    - ISBN and email validation
- **Layered architecture** similar to Spring Boot applications
- **Manual testing without frameworks**
    - Provides clear understanding of data flow, service orchestration, and validation logic

---

## **Next Steps / Enhancements**

- Add **menu-driven console UI** for interactive input
- Integrate with **MySQL/PostgreSQL** for persistent storage
- Implement **overdue calculations and late fees**
- Transition to **Spring Boot**:
    - Repositories → Spring Data JPA
    - Services → `@Service`
    - Controllers → REST endpoints
    - Dependency injection via `@Autowired`
    - Automatic transaction management

---

## **How to Run**

1. Clone the repository
2. Open in your preferred IDE (IntelliJ/Eclipse)
3. Compile and run `Main.java`
4. Observe outputs for:
    - Borrowing and returning books
    - Viewing borrowing history
    - Checking book availability
    - Listing all transactions
