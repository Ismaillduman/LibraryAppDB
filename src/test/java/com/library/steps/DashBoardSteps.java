package com.library.steps;

import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class DashBoardSteps {
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String actualBorrowBooks;

    @Given("the {string} on the home page")
    public void theOnTheHomePage(String userType) {
        loginPage.login(userType);
        wait.until(ExpectedConditions.titleIs("Library"));
        Assert.assertEquals("Library", Driver.getDriver().getTitle());

    }

    @When("the librarian gets borrowed books number")
    public void theLibrarianGetsBorrowedBooksNumber() {
        BrowserUtil.waitFor(2);
        actualBorrowBooks = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println(actualBorrowBooks);

    }

    @Then("borrowed books number information must match with DB")
    public void borrowedBooksNumberInformationMustMatchWithDB() {
        DB_Util.runQuery("select count(id) from book_borrow where is_returned=0;");
        String expectedBorrowBooks=DB_Util.getFirstRowFirstColumn();

        Assert.assertEquals("data is not match each other",expectedBorrowBooks,actualBorrowBooks);


    }


}
