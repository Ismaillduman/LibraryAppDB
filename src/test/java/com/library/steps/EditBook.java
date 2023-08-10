package com.library.steps;

import com.library.pages.BookPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;

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


        List<String> actualBookInfo = new ArrayList<>(Arrays.asList(actualBookName, actualIsbn, actualYear, actualAuthor, actualDescription, actualCategory));

        DB_Util.createConnection();
        DB_Util.runQuery("SELECT  b.name , isbn, year, author, b.description,bc.name as category\n" +
                "                FROM books b join book_categories bc on b.book_category_id = bc.id\n" +
                "                where b.name='Son Ada';");


        List<String> expectedBookInfo = new ArrayList<>();
        expectedBookInfo = DB_Util.getRowDataAsList(1);

        Assert.assertEquals(actualBookInfo, expectedBookInfo);


    }
}
