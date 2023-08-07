package com.library.steps;

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
import java.util.List;

public class AddBookStepDefs {

    BookPage bookPage = new BookPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);

    @When("the librarian click to add book")
    public void theLibrarianClickToAddBook() {
        bookPage.addBook.click();
        wait.until(ExpectedConditions.elementToBeClickable(bookPage.saveChanges));
    }

    @And("the librarian enter book name {string}")
    public void theLibrarianEnterBookName(String book_name) {
        bookPage.bookName.sendKeys(book_name);
    }

    @When("the librarian enter ISBN {string}")
    public void theLibrarianEnterISBN( String isbn) {
        bookPage.isbn.sendKeys(isbn);
    }

    @And("the librarian enter year {string}")
    public void theLibrarianEnterYear( String year) {
        bookPage.year.sendKeys(year);
    }

    @When("the librarian enter author {string}")
    public void theLibrarianEnterAuthor( String author) {
        bookPage.author.sendKeys(author);
    }

    @And("the librarian choose the book category {string}")
    public void theLibrarianChooseTheBookCategory(String category) {
        bookPage.categoryDropdown.sendKeys(category);
    }

    @And("the librarian click to save changes")
    public void theLibrarianClickToSaveChanges() {
        bookPage.saveChanges.click();
        BrowserUtil.waitFor(2);
    }

    @Then("verify {string} message is displayed")
    public void verifyMessageIsDisplayed(String expectedMessage) {
        String actualMessage= bookPage.toastMessage.getText();
        System.out.println("actualMessage = " + actualMessage);
        Assert.assertEquals(expectedMessage,actualMessage);


    }

    @And("verify {string} And {string} information must match with DB")
    public void verifyAndInformationMustMatchWithDB(String expected_book_name,String expected_author_name) {
        DB_Util.createConnection();
        String query = "SELECT name, author FROM books ORDER BY id DESC;";
        DB_Util.runQuery(query);

        List<String> actualBookInfo= DB_Util.getRowDataAsList(1);
        List<String>expectedBookInfo=new ArrayList<>();
        expectedBookInfo.add(expected_book_name);
        expectedBookInfo.add(expected_author_name);
//        System.out.println("actualBookInfo = " + actualBookInfo);
//        System.out.println("expectedBookInfo = " + expectedBookInfo);
        Assert.assertEquals(expectedBookInfo,actualBookInfo);
//        for (String eachBook : bookInfo) {
//            if(eachBook.equals(expected_book_name)&&eachBook.equals(expected_author_name)){
//                expected_book_name=eachBook;
//                expected_author_name=eachBook;
//            }
//        }
//        System.out.println("expected_book_name = " + expected_book_name);
//        Assert.assertNotNull(expected_book_name);
//        Assert.assertNotNull(expected_author_name);
//        DB_Util.destroy();
//        DB_Util.createConnection();
//        DB_Util.runQuery("select * from books where name='Head First Java'");
//        System.out.println("DB_Util.getRowDataAsList(1) = " + DB_Util.getRowDataAsList(1));
    }

}
