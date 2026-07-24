# Selenium UI Automation Framework Java

UI test automation framework built with **Java 21**, **Selenium 4**, **TestNG**, **Allure Report**, and **Maven**.

The project demonstrates a production-style test automation structure using the Page Object Model, reusable driver management, configuration layer, step layer, test data generation, custom TestNG listener, and Allure reporting.

## Tech Stack

- Java 21
- Selenium WebDriver 4
- TestNG
- Maven
- Allure Report
- WebDriverManager
- AssertJ
- Owner
- JavaFaker
- SLF4J + Logback

## Test Coverage

The framework currently covers UI scenarios for DemoQA:

### Home Page

- Verify that the main page is loaded
- Verify that main category cards are visible

### Student Registration Form

- Fill and submit the student registration form
- Verify submitted data in the confirmation modal
- Upload a test file
- Select date of birth, gender, hobbies, state and city

### Web Tables

- Add a new record
- Edit an existing record
- Delete a record
- Search for a record by keyword

## Project Structure

```text
src/test/java/com/qaportfolio
├── config        # Application configuration
├── data          # Test data records and factories
├── driver        # WebDriver creation and lifecycle management
├── enums         # Browser and run mode enums
├── listeners     # TestNG listeners
├── pages         # Page Object classes
├── steps         # Business-level test steps
├── tests         # Test classes
└── utils         # Utility classes and Allure attachments