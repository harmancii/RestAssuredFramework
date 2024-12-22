package com.api.steps;

import com.api.util.Config;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.*;
public class BookApiSteps {

    private Response response;
    private String isbn;
    private String secondIsbn;

    @Given("The request is sent for ISBN {string}")
    public void requestSentForIsbn(String isbn) {
        this.isbn = isbn;
        response = given()
                .baseUri(Config.getBaseUrl())
                .queryParam("bibkeys", "ISBN:" + isbn)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .when()
                .get("/api/books");
    }

    @Given("The request is sent for ISBNs {string},{string}")
    public void requestSentForIsbns(String isbn,String secondIsbn) {
        this.isbn = isbn;
        this.secondIsbn = secondIsbn;
        response = given()
                .baseUri(Config.getBaseUrl())
                .queryParam("bibkeys", "ISBN:" + isbn + "," + secondIsbn)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .when()
                .get("/api/books");
    }

    @When("The response is received")
    public void responseReceived() {
    }

    @Then("The response status code should be 200")
    public void responseStatusCodeShouldBe200() {
        assertThat(response.statusCode(), is(200));
    }

    @Then("The title should be {string}")
    public void titleShouldBe(String expectedTitle) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        String actualTitle = (String) isbnData.get("title");
        assertThat(actualTitle, is(expectedTitle));
    }

    @Then("The author name should be {string}")
    public void authorNameShouldBe(String expectedAuthor) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        List<Map<String, Object>> authors = (List<Map<String, Object>>) isbnData.get("authors");
        String actualAuthor = (String) authors.get(0).get("name");
        assertThat(actualAuthor, is(expectedAuthor));
    }

    @Then("The number of pages should be {int}")
    public void numberOfPagesShouldBe(int expectedPages) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        int actualPages = (Integer) isbnData.get("number_of_pages");
        assertThat(actualPages, is(expectedPages));
    }

    @Then("The publisher should be {string}")
    public void publisherShouldBe(String expectedPublisher) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        List<Map<String, Object>> publishers = (List<Map<String, Object>>) isbnData.get("publishers");
        String actualPublisher = (String) publishers.get(0).get("name");
        assertThat(actualPublisher, is(expectedPublisher));
    }

    @Then("The publish date should be {string}")
    public void publishDateShouldBe(String expectedDate) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        String actualDate = (String) isbnData.get("publish_date");
        assertThat(actualDate, is(expectedDate));
    }

    @Then("The ISBN {string} should be present in the response")
    public void isbnShouldBePresentInTheResponse(String expectedIsbn) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        assertThat(jsonResponse.containsKey("ISBN:" + expectedIsbn), is(true));
    }

    @Then("The response should contain subjects {string}")
    public void responseShouldContainSubjects(String expectedSubjects) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        List<Map<String, Object>> subjectsList = (List<Map<String, Object>>) isbnData.get("subjects");
        List<String> actualSubjects = subjectsList.stream()
                .map(subject -> (String) subject.get("name"))
                .collect(Collectors.toList());
        String[] subjects = expectedSubjects.split(", ");
        for (String subject : subjects) {
            assertThat(actualSubjects, hasItem(subject));
        }
    }

    @Then("The response should contain a small cover image URL")
    public void responseShouldContainSmallCoverImageUrl() {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        Map<String, String> cover = (Map<String, String>) isbnData.get("cover");
        String actualSmallCover = cover.get("small");
        assertThat(actualSmallCover, is(notNullValue()));
    }

    @Then("The response should contain a medium cover image URL")
    public void responseShouldContainMediumCoverImageUrl() {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        Map<String, String> cover = (Map<String, String>) isbnData.get("cover");
        String actualMediumCover = cover.get("medium");
        assertThat(actualMediumCover, is(notNullValue()));
    }

    @Then("The response should contain a large cover image URL")
    public void responseShouldContainLargeCoverImageUrl() {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        Map<String, String> cover = (Map<String, String>) isbnData.get("cover");
        String actualLargeCover = cover.get("large");
        assertThat(actualLargeCover, is(notNullValue()));
    }

    @Then("The response should contain the book URL {string}")
    public void responseShouldContainBookUrl(String expectedUrl) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        String actualUrl = (String) isbnData.get("url");
        assertThat(actualUrl, is(expectedUrl));
    }

    @Then("The response time should be less than {int} milliseconds")
    public void responseTimeShouldBeLessThanMilliseconds(int time) {
        assertThat(response.time(), lessThan((long) time));
    }

    @Then("The response should contain details for {string} and {string}")
    public void responseShouldContainDetailsForAnd(String book1, String book2) {
        assertThat(response.asString(), containsString(book1));
        assertThat(response.asString(), containsString(book2));
    }

    @Then("The title of the first book should be {string}")
    public void titleOfFirstBookShouldBe(String book1Title) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get("ISBN:" + isbn);
        String actualTitle = (String) isbnData.get("title");
        assertThat(actualTitle, is(book1Title));
    }

    @Then("The title of the second book should be {string}")
    public void titleOfSecondBookShouldBe(String book2Title) {
        Map<String, Object> jsonResponse = response.jsonPath().getMap("");
        Map<String, Object> isbnData = (Map<String, Object>) jsonResponse.get(secondIsbn);
        String actualTitle = (String) isbnData.get("title");
        assertThat(actualTitle, is(book2Title));

    }
}
