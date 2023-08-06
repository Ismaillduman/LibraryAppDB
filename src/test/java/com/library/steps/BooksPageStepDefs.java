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
import java.util.List;

public class BooksPageStepDefs {

    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
    BookPage bookPage = new BookPage();

    List<String> actualRealBookCategories= new ArrayList<>();


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
            if(!eachBookCategory.equals("ALL")){actualRealBookCategories.add(eachBookCategory);}
        }

    }

    @Then("verify book categories must match book_categories table from db")
    public void verifyBookCategoriesMustMatchBook_categoriesTableFromDb() {
        DB_Util.createConnection();
        DB_Util.runQuery("SELECT name from book_categories");
        List<String> expectedBookCategories= DB_Util.getColumnDataAsList(1);
        Assert.assertEquals(expectedBookCategories,actualRealBookCategories);


    }
}
