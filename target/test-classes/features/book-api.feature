Feature: Fetch book details for a given ISBN

  @Regression
  Scenario: Fetch book details for a valid ISBN and verify response status code
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The response status code should be 200

  @Regression
  Scenario: Validate key fields in the JSON response for given ISBN
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The title should be "The Fellowship of the Ring"
    And The author name should be "J.R.R. Tolkien"
    And The number of pages should be 407
    And The publisher should be "HarperCollinsPublishers"
    And The publish date should be "2011"

  @Regression
  Scenario: Validate ISBN exists in the response
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The ISBN "9780261103573" should be present in the response

  Scenario: Validate subject names in the response for given ISBN
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The response should contain subjects "Elves, Dwarves, evil, fear"

  Scenario: Validate book cover image links are available
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The response should contain a small cover image URL
    And The response should contain a medium cover image URL
    And The response should contain a large cover image URL

  Scenario: Validate the URL of the book in the response
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The response should contain the book URL "https://openlibrary.org/books/OL32559371M/The_Fellowship_of_the_Ring"

  @Regression
  Scenario: Validate the response time is within acceptable limits
    Given The request is sent for ISBN "9780261103573"
    When The response is received
    Then The response time should be less than 2000 milliseconds

  @Regression
  Scenario: Validate multiple books details for given ISBNs
    Given The request is sent for ISBNs "9780261103573","9780140328721"
    When The response is received
    Then The response should contain details for "The Fellowship of the Ring" and "Fantastic Mr. Fox"
    And The title of the first book should be "The Fellowship of the Ring"
    And The title of the second book should be "Fantastic Mr. Fox"
