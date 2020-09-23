package pages;

import objects.Account;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class LoginPage extends BasePage {
    WebDriver driver;
    @FindBy(id = "loginEmail")
    WebElement txt_email;
    @FindBy(xpath = "//input[@id='password' and @placeholder='Enter Password']")
    WebElement txt_password;
    @FindBy(xpath = "//button[text()='Login']")
    WebElement btn_Login;
    @FindBy(name = "remember")
    WebElement checkbox_RememberMe;
    @FindBy(xpath = "//a[@href='/logout']")
    WebElement link_logout;
    @FindBy(xpath = "//p[contains(@class,'alert-danger')]/strong")
    WebElement lbl_message;

    /**
     * All WebElements are identified by @FindBy annotation
     */
    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }

    /**
     * Set username
     *
     * @param email the email value
     */
    private void setEmail(String email) {
        Reporter.log("Set Email with value: " + email);
        this.setText(txt_email, email);
    }

    /**
     * Set Password
     *
     * @param password the password value
     */
    private void setPassword(String password) {
        Reporter.log("Set Password with value: " + password);
        this.setText(txt_password, password);
    }

    /**
     * Click Login button
     */
    private void clickRememberMe() {
        Reporter.log("Click Remember Me checkbox");
        this.click(checkbox_RememberMe);
    }

    /**
     * Click Login button
     */
    private void clickLogin() {
        Reporter.log("Click Login button");
        this.click(btn_Login);
    }

    /**
     * Login into the website with an account
     *
     * @param account
     */
    public void login(Account account) {
        this.setEmail(account.getEmail());
        this.setPassword(account.getPassword());
        this.clickLogin();
    }

    /**
     * Get the Logout link
     */
    public boolean doesLogoutLinkDisplay() {
        return this.isDisplayed(link_logout);
    }

    /**
     * Get the Message
     */
    public String getMessage() {
        return this.getTextFromElement(lbl_message);
    }

    /**
     * Get the current URL
     */
    public String getCurrentURL() {
        return this.getURL();
    }
}
