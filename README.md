# Yield - A P&L generator for your trades

## Introduction

The backend of Yield is an api that allows a user to save and then retrieve their trade history and / or generate a profit and loss report. In addition to reports, 
the api will allow users to retrieve the data of various assets.

In order to get the asset data, the api will utilize the CoinGecko api to extract data of the crypto assets and save this to our backend DB to serve.

## BE User Stories

- **As a user**, I want to be able to register for an account, so I can save my trade history and view it at a later time.
- **As a user**, I want to be able to login to my account, so that I can save, modify and / or retrieve the data I had saved to my account.
- **As a user**, I want to know which assets are supported, so that I know which assets I can include in my P & L analysis.
- **As a user**, I want to be able to query my trade history, so that I am able to extract / view my data by asset name, price range and date.
- **As a user**, I want the most up-to-date information on my assets, so that I have the data to properly generate and assess the P & L of my trades.

## MVP (Minimum Viable Product)

- Api endpoint accepts and properly handles errors for a user registration request and creates a new account for a new user.
- Api endpoint accepts and properly handles errors for a user login request and authenticates the user using jwt authentication.
- Api endpoint accepts and properly handles errors for a registered users request to store / save their trade history data.
- Api endpoint accepts and properly handles errors for modifying trade / order history.
- Api endpoint accepts users requests to retrieve data for a crypto asset by criteria. (i.e. name, price range, marketcap, date)
- Api endpoint accepts an authenticated users request to update their profile info.
- Server is able to fetch and properly handles errors when retrieving crypto asset data from an external api. (i.e. Coingecko)
- Server is able to store / persist crypto asset data received from an external api. (i.e. Coingecko)

## Stretch Goals

- Generating P&L report and sending the report back the user in .csv and / or .json format.
- Add asynchronous processing or threads for fetching API data.
- Add a recommendation engine utilizing crypto / asset data from Coingecko (e.g. add additional endpoints to extract more data from Coingecko, use trade history)
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
- **Mockito, and PowerMock**: Used for unit and integration testing.
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
