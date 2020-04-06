import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;

import org.openqa.selenium.By;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.Reporter;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;

public class Appium {
    private AppiumDriver<MobileElement> driver = null;
    private String appName = "Subway Surfers";

    @BeforeTest
    public void driverSetup() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("deviceName", "GM 5");
        capabilities.setCapability("udid", "4fb3eef1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("appPackage", "com.android.vending");
        capabilities.setCapability("appActivity", "com.google.android.finsky.activities.MainActivity");
        capabilities.setCapability("noReset", "true");

        try {
            driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);

        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testApp() throws InterruptedException {

        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.vending:id/search_bar")));

        driver.findElement(By.id("com.android.vending:id/search_bar")).click();
        driver.findElement(By.id("com.android.vending:id/search_bar_text_input")).sendKeys(appName);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER)
                .withFlag(KeyEventFlag.SOFT_KEYBOARD)
                .withFlag(KeyEventFlag.KEEP_TOUCH_MODE)
                .withFlag(KeyEventFlag.EDITOR_ACTION));

        try {
            Thread.sleep(1000);
            Assert.assertTrue(driver.findElement(By.id("com.android.vending:id/right_button")).isDisplayed());

            if(driver.findElement(By.id("com.android.vending:id/right_button")).getAttribute("text").contains("Aç")) {
                System.out.println("App is already installed.");
                Reporter.log("App is already installed.");
                return;
            }

            driver.findElement(By.id("com.android.vending:id/right_button")).click();
            MobileElement button = driver.findElement(By.id("com.android.vending:id/right_button"));
            while(button.getAttribute("text").contains("İptal")) {
                Thread.sleep(5000);
                if(!button.getAttribute("text").contains("İptal")) {
                    if(driver.findElement(By.id("com.android.vending:id/right_button")).getAttribute("text").contains("Aç")) {
                        System.out.println("App found and installed successfully!");
                        Reporter.log("App found and installed successfully!");
                        return;
                    }
                }
            }
        }

        catch (Exception e) {
            List<MobileElement> elements = driver.findElements(By.id("com.android.vending:id/metadata"));
            for(MobileElement element : elements) {
                if(element.getAttribute("contentDescription").contains(appName)) {
                    element.click();
                    new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.vending:id/right_button")));
                    if(driver.findElement(By.id("com.android.vending:id/right_button")).getAttribute("text").contains("Aç")) {
                        Reporter.log("App is already installed.");
                        System.out.println("App is already installed.");
                        driver.findElement(By.id("com.android.vending:id/right_button")).click();
                        return;
                    }

                    driver.findElement(By.id("com.android.vending:id/right_button")).click();
                    MobileElement button = driver.findElement(By.id("com.android.vending:id/right_button"));
                    while(button.getAttribute("text").contains("İptal")) {
                        Thread.sleep(5000);
                        if(!button.getAttribute("text").contains("İptal")) {
                            if(driver.findElement(By.id("com.android.vending:id/right_button")).getAttribute("text").contains("Aç")) {
                                System.out.println("App found and installed successfully!");
                                Reporter.log("App found and installed successfully!");
                                driver.findElement(By.id("com.android.vending:id/right_button")).click();
                                return;
                            }
                        }
                    }
                    return;
                }
            }
        }
        System.out.println("App Couldn't Found!");
        Reporter.log("App Couldn't Found!");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
