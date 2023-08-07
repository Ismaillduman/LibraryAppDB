package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditBook {
    BookPage bookPage = new BookPage();

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


        Select dropdown = new Select(bookPage.categoryDropdown);
        WebElement selectedOption = dropdown.getFirstSelectedOption();
        String actualCategory = selectedOption.getText();



        DB_Util.createConnection();
        DB_Util.runQuery("SELECT  b.name , isbn, year, author, b.description,bc.name as category\n" +
                "                FROM books b join book_categories bc on b.book_category_id = bc.id\n" +
                "                where b.name='Son Ada';");
        Map<String, String> bookInfo = new LinkedHashMap<>();

        bookInfo = DB_Util.getRowMap(1);


        String expectedBookName = bookInfo.get("name");
        String expectedAuthorName = bookInfo.get("author");
        String expectedISBN = bookInfo.get("isbn");
        String expectedYear = bookInfo.get("year");
        String expectedDesc = bookInfo.get("description");
        String expectedCategory_name = bookInfo.get("category");


        Assert.assertEquals(expectedBookName, actualBookName);
        Assert.assertEquals(expectedAuthorName, actualAuthor);
        Assert.assertEquals(expectedISBN, actualIsbn);
        Assert.assertEquals(expectedYear, actualYear);
        Assert.assertEquals(expectedDesc, actualDescription);
        Assert.assertEquals(expectedCategory_name,actualCategory);


    }
}
