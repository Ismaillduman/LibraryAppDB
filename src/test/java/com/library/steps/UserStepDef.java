package com.library.steps;

import com.library.pages.LoginPage;
import com.library.pages.UsersPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class UserStepDef extends UsersPage {


    String actualUserCount;

    List<String> allActualColumnNames;

    @Given("Establish the database connection")
    public void establish_the_database_connection() {
        // DB_Util.createConnection(); i have create hook setup for db
    }

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        String query = "select  count(id) from users";
        DB_Util.runQuery(query);
        actualUserCount = DB_Util.getFirstRowFirstColumn();
        System.out.println(actualUserCount);
    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        String query = "select distinct count(id) from users";
        DB_Util.runQuery(query);
        String expectedUserCount = DB_Util.getFirstRowFirstColumn();
        System.out.println(expectedUserCount);
        Assert.assertEquals(expectedUserCount, actualUserCount);

        DB_Util.destroy();
    }

    @When("Execute query to get all columns")
    public void executeQueryToGetAllColumns() {
        String query = "SELECT * from users";
        DB_Util.runQuery(query);
        allActualColumnNames = DB_Util.getAllColumnNamesAsList();
//        for (String eachActualColumnName : allActualColumnNames) {
//            System.out.println(eachActualColumnName);
//        }
    }

    @Then("verify the below columns are listed in result")
    public void verifyTheBelowColumnsAreListedInResult(List<String> expectedColumnName) {
        Assert.assertEquals(expectedColumnName, allActualColumnNames);
        DB_Util.destroy();
    }


    @When("the user selected status {string}")
    public void theUserSelectedStatus(String active) {

        BrowserUtil.selectOptionDropdown(status, active);
        BrowserUtil.waitFor(2);

    }

    String userCount;

    @And("the gets number of users")
    public void theGetsNumberOfUsers() {
        //Showing 1 to 10 of 1,022 entries
        String[] info = allUsersInfo.getText().split(" ");
        userCount = info[5];
        userCount = userCount.replace(",", "");
    }

    @Then("verify {string} status users count matching with DB")
    public void verifyStatusUsersCountMatchingWithDB(String status) {
        DB_Util.createConnection();
        DB_Util.runQuery("select status, count(*) from users\n" +
                "where status='"+status+"'");
        String expectedCount = DB_Util.getCellValue(1,2);
        Assert.assertEquals(expectedCount,userCount);
    }
}
