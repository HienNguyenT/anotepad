package testcases;

import data.Data;
import objects.Account;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.BasePage;
import pages.LoginPage;
import utils.Utils;

public class Verify_Login {
    WebDriver driver;
    Account account = new Account();
    LoginPage loginPage;

    @Parameters({"browser"})
    @BeforeMethod
    public void SetUp(@Optional("chrome") String browser) {
        BasePage basePage = new BasePage(driver);
        driver = basePage.setupDriver(browser);
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 0, description = "Verify login function")
    public void test_login_successfully() {
        account = Data.standardUser();
        loginPage.login(account);
        boolean doesLogoutLinkDisplay = loginPage.doesLogoutLinkDisplay();
        Assert.assertTrue(doesLogoutLinkDisplay, "The Logout link does not display.");
    }

    @Test(priority = 1, description = "Verify login function with invalid email")
    public void test_login_with_invalid_email() {
        account = Data.standardUser();
        account.setEmail("");
        loginPage.login(account);
        String getMsg = loginPage.getMessage();
        Assert.assertEquals(getMsg, "Email and password are required");
    }

    @Test(priority = 2, description = "Verify login function with invalid password")
    public void test_login_with_invalid_password() {
        account = Data.standardUser();
        account.setPassword("");
        loginPage.login(account);
        String getMsg = loginPage.getMessage();
        Assert.assertEquals(getMsg, "Email and password are required");
    }

    @Test(priority = 3, description = "Verify login function with empty data")
    public void test_login_with_empty_data() {
        account = new Account();
        account.setEmail("");
        account.setPassword("");
        loginPage.login(account);
        String getMsg = loginPage.getMessage();
        Assert.assertEquals(getMsg, "Email and password are required");
    }

    @Test(priority = 4, description = "Verify login function successfully with json data")
    public void test_login_successfully_with_json_data() {
        account = Utils.account();
        loginPage.login(account);
        boolean doesLogoutLinkDisplay = loginPage.doesLogoutLinkDisplay();
        Assert.assertTrue(doesLogoutLinkDisplay, "The Logout link does not display.");
    }

    @AfterMethod
    public void tearDown() {
        loginPage.cleanUp();
    }
}
