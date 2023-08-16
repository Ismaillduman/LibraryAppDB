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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BooksPageStepDefs {

    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 5);
    BookPage bookPage = new BookPage();
    String actualMostPopularBookCategory;

    List<String> actualRealBookCategories = new ArrayList<>();



    @When("the user navigates to {string} page")
    public void theUserNavigatesToPage(String booksPage) {

        bookPage.navigateModule(booksPage);
        //wait.until(ExpectedConditions.elementToBeClickable(bookPage.mainCategoryElement));

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



    @When("I execute query to find most popular book genre")
    public void iExecuteQueryToFindMostPopularBookGenre() {
        DB_Util.createConnection();
        DB_Util.runQuery("select bc.name,count(*) from books b join book_categories bc on b.book_category_id = bc.id\n" +
                "                        join book_borrow bb on b.id = bb.book_id\n" +
                "\n" +
                "group by name\n" +
                "order by 2 desc;\n");
        actualMostPopularBookCategory = DB_Util.getCellValue(1, "name");

    }

    @Then("verify {string} is the most popular book genre.")
    public void verifyIsTheMostPopularBookGenre(String ExpectedBookCategory) {
        Assert.assertEquals(ExpectedBookCategory, actualMostPopularBookCategory);
    }
}
