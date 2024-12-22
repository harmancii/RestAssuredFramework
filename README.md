# Book API Automation

This project automates tests for a Book API using RestAssured, Cucumber, and Java. The tests validate various API responses based on ISBN.

## Project Structure

src/
test/java/com/api/steps/BookApiSteps.java        (Step definitions)
test/java/com/api/util/Config.java               (Configuration - Base URL)
test/java/com/api/runner/TestRunner.java         (Cucumber test runner)
feature/
BookApi.feature                                  (Cucumber feature file)
pom.xml                                             (Maven dependencies)

## Setup Prerequisites

* Java 11
* Maven

## Installation

Clone the repo and install dependencies:

    git clone https://github.com/harmancii/RestAssuredFramework
    cd project-directory
    mvn clean install

## Running Tests

Run all tests:
mvn test

Run specific tests with tags:
mvn -Dcucumber.options="--tags @Regression" test

## Configuration

The base URL for the API is configured in the Config.java file:

    public class Config {
        public static String getBaseUrl() {
            return "https://openlibrary.org";
        }
    }

## Dependencies

* RestAssured: API testing
* Cucumber: BDD framework
* JUnit: Test assertions
* Maven: Dependency management