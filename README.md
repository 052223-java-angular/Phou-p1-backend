# Yield - A P&L generator for your trades

## Introduction

Yield is an web application that allows a user to upload a .csv file of their trades and generate a profit and loss .csv report. In addition to generating a
.csv report, the user can view the generated results within a web-browser like Chrome.

As part of the integration, yield will utilize the Coinmarketcap or similar API to extract past and current price data of crypto assets for calculating figures.

Although the app is free to use, yield may also integrate the Coinbase API for accepting crypto payment / donations for those feeling the need to donate some of
those Gainz!

## BE User Stories

- **As a user**, I want to be able to register for an account, so I can save my trade history and view them at a later time.
- **As a user**, I want to be able to login to my account, so that I can save, modify and / or request the data I had saved into my account.
- **As a user**, I want to know which assets are supported, so that I know which assets I can save data for and include in my P & L analysis.
- **As a user**, I want to be able to query my trade history, so that I am able to extract / view my data by asset name, price range and date.
- **As a user**, I want the most up to date information on my asset, so that I have the data to assess P & L expectations.

## MVP (Minimum Viable Product)

- Api endpoint that will accept and / or properly handle errors for a user registration request and create a new account for a new user.
- Api endpoint that will accept and / or properly handle errors for a user login request and authenticate using jwt authentication.
- Api endpoint that will accept and / or properly handle errors for a registered users request to store / add their trade history data.
- Api endpoint that will accept and / or properly handle errors for modifying trade / order history.
- Api endpoint that will accept users requests to retrieve data for a crypto asset by criteria name, price range, marketcap, date.
- Api endpoint that will accept an authenticated users request to update their profile info.
- Server is able to fetch and properly handle errors when retrieving crypto asset data from an external api, i.e. coingecko
- Server is able to store / persist crypto asset data received from an external api

## Stretch Goals

- Generating P&L report and sending the report back the user in .csv and / or .json format.
- Add asynchronous processing or threads for fetching API data.
- Add a recommendation engine utilizing crypto / asset data from coingecko i.e. add additional endpoints to extract more data and trade history.
- Generate report and send to registered email.
- Implement threads when generating reports.

## Tech Stack

### **Back-end tech stack**

- **Java**: The main programming language used for building the application.
- **PostgreSQL**: Used as the database to store user reports and cryptocurrency related data.
- **JDBC (Java Database Connectivity)**: An API for connecting and executing queries on the database.
- **Maven or Gradle**: Used for managing project dependencies.
- **BCrypt**: A Java library for hashing and checking passwords for security.
- **Log4j**: A logging utility for debugging purposes.
- **JUnit**: A testing framework for Java applications, used to ensure our code works as expected.
- **JUnit, Mockito, and PowerMock**: Used for unit and integration testing.
- **Git and GitHub**: Used for version control.

## Requirements

- **Clean Codebase**: All code should be clean and well-documented. The repository should not include any unnecessary files or folders such as the `target/`,
  `.DS_Store`, etc. All files and directories should be appropriately named and organized.

- **Database Design**: The database should be designed following the principles of the 3rd Normal Form (3NF) to ensure data integrity and efficiency. An Entity
  Relationship Diagram (ERD) should be included in the documentation.

- **Secure**: All sensitive user data such as passwords must be securely hashed before storing it in the database. The application should not display any
  sensitive information in error messages.

- **Error Handling**: The application should handle potential errors gracefully and provide clear and helpful error messages to the users.

- **Testing**: The application should have a high test coverage. Unit tests and integration tests should be implemented using JUnit, Mockito, and PowerMock.

- **Version Control**: The application should be developed using a version control system, preferably Git, with regular commits denoting progress.

- **Documentation**: The repository should include a README file with clear instructions on how to run the application. Code should be well-commented to allow
  for easy understanding and maintenance.

- **Scalable**: The design of the application should be scalable, allowing for easy addition of new features or modifications in the future.
