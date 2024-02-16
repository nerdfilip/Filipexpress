# Filipexpress Car Rental System
Filipexpress is a comprehensive car rental system designed to streamline administrative tasks related to managing rentals, clients, vehicles, and audits. Built with Java, PostgreSQL, Hibernate, Vaadin, and Spring Boot, this web application provides an intuitive interface for administrators to access and manipulate data efficiently.

# Features:
- **Entity Creation:** All essential entities including rentals, clients, vehicles, and audits have been created and verified.
- **Database Initialization:** The database initializer ensures the smooth setup of the system.
- **Login Service:** Implemented a secure login service with username and password matching.
- **Client CRUD View:** Created a client CRUD (Create, Read, Update, Delete) view with thorough verification.
- **Car CRUD View:** Developed a car CRUD view allowing easy management of vehicle data.
- **Rentals Service Enhancement:** Re-created the rentals service, including foreign key constraints for vehicle and client IDs.
- **Rent CRUD View:** Designed a rent CRUD view with various verifications such as checking for existing client and vehicle IDs, availability of vehicles, and added functionality for fines and total calculation.
- **Menu View:** Implemented a menu view with buttons for easy navigation across different sections of the application.
- **Audit View:** Created an audit view for viewing historical data without modification capabilities.
- **Audit Class Update:** Updated the audit class to reflect changes accurately.
- **About View:** Provided an about view for additional information about the application.
- **Database Initialization:** Added additional records in the database initializer.
- **Custom Error Messages:** Implemented custom error messages and ensured thorough verification of all errors and restrictions.
- **Bug Fixes:** Resolved bugs related to deleting records from car and rental panels, and updating records in the rental panel.

# Technology Stack:
- Java
- PostgreSQL
- Hibernate
- Vaadin
- Spring Boot

# How to Use:
To utilize the Filipexpress Car Rental System, follow these simple steps:

1. **Clone the Repository:** Start by cloning the repository containing the project files to your local machine.
2. **Open in IntelliJ IDEA:** Import the project into IntelliJ IDEA, ensuring you have the necessary plugins and dependencies installed.
3. **Create database in Postgres:** You need to create a new database in Postgres with these:
```bash
  name: database
  username: postgres
  password: 12345qwert
```
Or you can edit **application.properties** with your Postgres settings.

4. **Run the Application:** Simply run the compiler on **Application** class in IntelliJ IDEA to start the application. The necessary databases will be created automatically with predefined data, streamlining the setup process.
5. **Explore the Functionality:** Once the application is up and running, you can explore its various functionalities. Navigate through the user-friendly interface to manage rentals, clients, vehicles, and audits effortlessly.
You can use these credidentials to login on Filipexpress:
```txt
username: Administrator
password: Adm*123
```
6. **Customize as Needed:** Feel free to customize the application according to your preferences and requirements. You can modify the code, add new features, or adapt existing functionalities to suit your specific use case.
7. **Enjoy Seamless Car Rental Management:** With Filipexpress, you have a powerful tool at your disposal for efficient and streamlined car rental management. Whether you're a small rental agency or a large enterprise, Filipexpress simplifies administrative tasks and enhances productivity.
8. **Feedback and Contributions:** Your feedback is valuable! If you encounter any issues, have suggestions for improvements, or would like to contribute to the project, don't hesitate to reach out to the development team. Your input helps make Filipexpress even better for all users.

With these simple steps, you can quickly set up and start using the Filipexpress Car Rental System to manage your car rental business with ease. Enjoy the convenience and efficiency that Filipexpress brings to your operations!
