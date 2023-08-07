package com.library.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BorrowedBooksPage extends BasePage{


    @FindBy(xpath = "//tbody//td[2]")
    public List<WebElement> allBorrowedBooksName;

    @FindBy(xpath = "//tbody//td[6]")
    public List<WebElement> allBorrowBooksStatus;



    @FindBy(css = ".nav-link.dropdown-toggle")
    public WebElement userName;
}
