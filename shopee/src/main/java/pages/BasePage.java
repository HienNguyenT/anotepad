package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import profiles.DefaultProfile;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {

    private static final int TIMEOUT = 5;
    private static final int POLLING = 100;

    static WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * setup Chrome/ Firefox Driver on Mac
     */
    public WebDriver setupDriver(String browser) {
        if (browser.toLowerCase().equals("chrome")) {
            if (System.getProperty("os.name").contains("Mac")) {
                Reporter.log("Launching Google Chrome Driver for this test");
                System.setProperty("webdriver.chrome.driver", BasePage.class.getResource(DefaultProfile.MAC_CHROME_DRIVER).getFile());
                // System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

                // Now checking for existence of Chrome executable.
                if (!new File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome").exists()) {
                    throw new RuntimeException("Cannot find chromedriver file. Please download and copy to drivers folder in current project");
                }
            }

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--ignore-certificate-errors");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().pageLoadTimeout(DefaultProfile.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(DefaultProfile.IMPLICIT_TIMEOUT, TimeUnit.SECONDS);

        } else if (browser.toLowerCase().equals("firefox")) {
            Reporter.log("Launching Firefox Driver for this test");
            if (System.getProperty("os.name").contains("Mac")) {
                System.setProperty("webdriver.gecko.driver", BasePage.class.getResource(DefaultProfile.MAC_FIREFOX_DRIVER).getFile());
                // System.setProperty("webdriver.gecko.driver", "/usr/local/bin/geckodriver");

                // Now checking for existence of Firefox executable.
                if (!new File("/Applications/Firefox.app/Contents/MacOS/firefox").exists()) {
                    throw new RuntimeException("Cannot find geckodriver file. Please download and copy to drivers folder in current project");
                }
            }
            driver = new FirefoxDriver();
        }
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIMEOUT), this);

        maximizeWindows();
        get(DefaultProfile.urlUAT);

        return driver;
    }

    /**
     * Find an element
     *
     * @param by receive the driver
     */
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    /**
     * Wait an element
     *
     * @param locator receive the driver
     */
    protected void waitForElementToAppear(By locator) {
        Reporter.log("Wait for the element by locator " + locator.toString());
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait an element
     *
     * @param locator receive the driver
     */
    protected void waitForElementToDisappear(By locator) {
        Reporter.log("Wait for the element to be disappeared by locator " + locator.toString());
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait an element
     *
     * @param locator receive the driver
     * @param text    the text that we want to wait
     */
    protected void waitForTextToDisappear(By locator, String text) {
        Reporter.log("Wait for the element to be disappeared by locator " + locator.toString());
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, text)));
    }

    /**
     * Click on an element
     *
     * @param by receive the element
     */
    public void click(By by) {
        try {
            Reporter.log("Click on the element by locator " + by.toString());
            WebElement element = driver.findElement(by);
            element.click();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Click on an element
     *
     * @param element the element
     */
    public void click(WebElement element) {
        try {
            Reporter.log("Click on the element");
            element.click();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Click on an element
     *
     * @param by   the element
     * @param text the value
     */
    public void setText(By by, String text) {
        try {
            Reporter.log("Set text on the element by locator " + by.toString() + " with the value: " + text);
            WebElement element = driver.findElement(by);
            element.sendKeys(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Click on an element
     *
     * @param element the element
     * @param text    the value
     */
    public void setText(WebElement element, String text) {
        try {
            Reporter.log("Set text on the element by locator " + element.toString() + " with the value: " + text);
            element.sendKeys(text);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check the element displayed ot not
     *
     * @param by receive the element
     */
    public boolean isDisplayed(By by) {
        try {
            Reporter.log("Check the element is displayed or not with the locator " + by.toString());
            WebElement element = driver.findElement(by);
            return element.isDisplayed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Check the element displayed ot not
     *
     * @param element receive the element
     */
    public boolean isDisplayed(WebElement element) {
        try {
            Reporter.log("Check the element is displayed or not with the locator " + element.toString());
            return element.isDisplayed();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Wait for the element visible with the specific timeout
     *
     * @param by      receive the element
     * @param timeout in seconds
     */
    public void waitForElementVisible(By by, int timeout) {
        try {
            Reporter.log("Check the element is visible or not with the locator " + by.toString());
            WebElement element = driver.findElement(by);
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Wait for the element clickable with the specific timeout
     *
     * @param by      the element
     * @param timeout in seconds
     */
    public void waitForElementClickable(By by, int timeout) {
        try {
            Reporter.log("Check the element is clickable or not with the locator " + by.toString());
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Wait for the element clickable with the specific timeout
     *
     * @param element the element
     * @param timeout in seconds
     */
    public void waitForElementClickable(WebElement element, int timeout) {
        try {
            Reporter.log("Check the element is clickable or not with the locator " + element.toString());
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return the element is existed or not
     *
     * @param by receive the element
     */
    public boolean doesElementExist(By by) {
        boolean flag = false;
        try {
            Reporter.log("Check the element is existed or not with the locator " + by.toString());
            driver.findElement(by);
            flag = true;
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * Return the element is existed or not
     *
     * @param element receive the element
     */
    public boolean doesElementExist(WebElement element) {
        boolean flag = false;
        try {
            Reporter.log("Check the element is existed or not with the locator " + element.toString());

            flag = true;
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }


    /**
     * Return the element is selected or not
     *
     * @param by the element
     */
    public boolean isElementSelected(By by) {
        boolean flag = false;
        try {
            Reporter.log("Check the element is selected or not with the locator " + by.toString());
            driver.findElement(by).isSelected();
            flag = true;
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * Return the element is selected or not
     *
     * @param element the element
     */
    public boolean isElementSelected(WebElement element) {
        boolean flag = false;
        try {
            Reporter.log("Check the element is selected or not with the locator " + element.toString());
            element.isSelected();
            flag = true;
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * Return the text of element
     *
     * @param by receive the element
     */
    public String getTextFromElement(By by) {
        String text = "";
        try {
            Reporter.log("Get text of the element with the locator " + by.toString());
            text = driver.findElement(by).getText();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Return the text of element
     *
     * @param element receive the element
     */
    public String getTextFromElement(WebElement element) {
        String text = "";
        try {
            Reporter.log("Get text of the element with the locator " + element.toString());
            text = element.getText();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Return the text of element
     *
     * @param by receive the element
     */
    public String getTextFromElements(By by, int index) {
        String text = "";
        try {
            Reporter.log("Get text of the element with the locator " + by.toString() + " with index = " + index);
            List<WebElement> listOfElements = driver.findElements(by);
            text = listOfElements.get(index).getText();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Return the total elements
     *
     * @param by receive the element
     */
    public int getTotalElements(By by) {
        int total = 0;
        try {
            Reporter.log("Get total elements with the locator " + by.toString());
            List<WebElement> listOfElements = driver.findElements(by);
            return listOfElements.size();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return total;
    }

    /**
     * Maximize windows
     */
    public void maximizeWindows() {
        try {
            Reporter.log("Maximize Window");
            driver.manage().window().maximize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Go to the URL
     *
     * @param url
     */
    public void get(String url) {
        try {
            Reporter.log("Navigate to the URL " + url);
            driver.get(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get title
     */
    public String getTitle() {
        try {
            Reporter.log("Get the current title");
            return driver.getTitle();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * Get current URL
     */
    public String getURL() {
        try {
            Reporter.log("Get the current url");
            return driver.getCurrentUrl();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    /**
     * sleep for x seconds
     *
     * @param seconds
     */
    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cleanUp() {
        try {
            Reporter.log("cleanUp");
            if (driver != null) {
                driver.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scrollBy(0, 4500)");
    }
}