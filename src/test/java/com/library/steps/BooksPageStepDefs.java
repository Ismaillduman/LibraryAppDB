package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BooksPageStepDefs {

    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
    BookPage bookPage = new BookPage();

    List<String> actualRealBookCategories = new ArrayList<>();


    @When("the user navigates to {string} page")
    public void theUserNavigatesToPage(String booksPage) {

        bookPage.navigateModule(booksPage);
        wait.until(ExpectedConditions.elementToBeClickable(bookPage.mainCategoryElement));

    }

    @And("the user clicks book categories")
    public void theUserClicksBookCategories() {
        bookPage.mainCategoryElement.click();
        BrowserUtil.waitFor(2);

        List<String> actualBookCategories = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        for (String eachBookCategory : actualBookCategories) {
            if (!eachBookCategory.equals("ALL")) {
                actualRealBookCategories.add(eachBookCategory);
            }
        }

    }

    @Then("verify book categories must match book_categories table from db")
    public void verifyBookCategoriesMustMatchBook_categoriesTableFromDb() {
        DB_Util.createConnection();
        DB_Util.runQuery("SELECT name from book_categories");
        List<String> expectedBookCategories = DB_Util.getColumnDataAsList(1);
        Assert.assertEquals(expectedBookCategories, actualRealBookCategories);


    }

    @When("the user searches for {string} book")
    public void theUserSearchesForBook(String book) {
        bookPage.search.sendKeys(book);
    }

    @And("the user clicks edit book button")
    public void theUserClicksEditBookButton() {
        bookPage.editCleanCode.click();
        BrowserUtil.waitFor(1);
    }

    @Then("book information must match the Database")
    public void bookInformationMustMatchTheDatabase() {
        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualIsbn = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualAuthor = bookPage.author.getAttribute("value");
        String actualDescription = bookPage.description.getAttribute("value");


        DB_Util.createConnection();
        DB_Util.runQuery("SELECT  b.name, isbn, year, author, b.description\n" +
                "FROM books b \n" +
                "where b.name='Son Ada'");
        Map<String, String> bookInfo = new LinkedHashMap<>();

        bookInfo = DB_Util.getRowMap(1);


        String expectedBookName = bookInfo.get("name");
        String expectedAuthorName = bookInfo.get("author");
        String expectedISBN = bookInfo.get("isbn");
        String expectedYear = bookInfo.get("year");
        String expectedDesc = bookInfo.get("description");



        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedAuthorName,actualAuthor);
        Assert.assertEquals(expectedISBN,actualIsbn);
        Assert.assertEquals(expectedYear,actualYear);
        Assert.assertEquals(expectedDesc,actualDescription);


    }
}
