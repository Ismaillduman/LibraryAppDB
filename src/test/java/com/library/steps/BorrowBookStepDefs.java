package com.library.steps;

import com.library.pages.BasePage;
import com.library.pages.BookPage;
import com.library.pages.BorrowedBooksPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


import java.util.*;


public class BorrowBookStepDefs extends BasePage {
    BookPage bookPage = new BookPage();
    BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();


    @When("the user clicks Borrow {string} Book")
    public void theUserClicksBorrowBook(String book_name) {

        BrowserUtil.clickWithJS(bookPage.borrowBook(book_name));

    }

    @Then("verify that {string} book is shown in {string} page")
    public void verifyThatBookIsShownInPage(String book, String borrow) {

        navigateModule(borrow);
        BrowserUtil.waitForPageToLoad(5);

        String actualBorrowBookName = borrowedBooksPage.allBorrowedBooksName.get(borrowedBooksPage.allBorrowedBooksName.size() - 1).getText();
        Assert.assertEquals(book, actualBorrowBookName);

    }

    @And("verify logged student has same book in database")
    public void verifyLoggedStudentHasSameBookInDatabase() {
        String actualBorrowedBook = borrowedBooksPage.allBorrowedBooksName.get(borrowedBooksPage.allBorrowedBooksName.size() - 1).getText();

        String actualUser = borrowedBooksPage.userName.getText();
        List<String> actualBookInfo = Arrays.asList(actualUser, actualBorrowedBook);

        DB_Util.createConnection();
        DB_Util.runQuery("select full_name, name from books join book_borrow bb on books.id = bb.book_id\n" +
                "join users u on bb.user_id = u.id\n" +
                "order by borrowed_date desc;");
        List<String> expectedBorrowBookInfo = DB_Util.getRowDataAsList(1);


//        System.out.println("actualBorrowedBook = " + actualBorrowedBook);
//        System.out.println("expectedBorrowBook = " + expectedBorrowBook);
        Assert.assertEquals(expectedBorrowBookInfo, actualBookInfo);


    }


}
