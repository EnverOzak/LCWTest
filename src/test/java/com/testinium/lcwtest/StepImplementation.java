package com.testinium.lcwtest;

import com.testinium.lcwtest.backend.BackEnd;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class StepImplementation {

    public static WebDriver driver;
    Logger logger = LogManager.getLogger(StepImplementation.class);
    String[] aFav, cFav;

    @Step("Navigate to <webPage> by Url in <page>")
    public void navigateTo(String webPage, String pageObject) throws InterruptedException, IOException {
        System.setProperty("webdriver.gecko.driver",".\\driver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(BackEnd.findElementByKey(webPage,pageObject));
        Thread.sleep(2000);
    }

    @Step("Check if <webPage> is Opened by Url in <page> and print <message>")
    public void checkPageIsOpened(String webPage, String pageObject, String message) throws IOException {
        assertEquals(BackEnd.findElementByKey(webPage,pageObject), driver.getCurrentUrl());
        logger.info(message);
    }

    @Step("Hover on element <key> by id in <page>")
    public void hoverOnElementById(String key, String pageObject) throws IOException {
        Actions action = new Actions(driver);
        WebElement ManBtn = driver.findElement(By.id(BackEnd.findElementByKey(key,pageObject)));
        action.moveToElement(ManBtn).moveToElement(driver.findElement(By.id(BackEnd.findElementByKey(key,pageObject)))).build().perform();
    }

    @Step("Hover on element <key> by xpath in <page>")
    public void hoverOnElementByXpath(String key, String pageObject) throws IOException {
        Actions action = new Actions(driver);
        WebElement ManBtn = driver.findElement(By.xpath(BackEnd.findElementByKey(key,pageObject)));
        action.moveToElement(ManBtn).moveToElement(driver.findElement(By.xpath(BackEnd.findElementByKey(key,pageObject)))).build().perform();
    }

    @Step("Click on Element <key> by Id in <page>")
    public void clickById(String key,String pageObject) throws IOException {
        driver.findElement(By.id(BackEnd.findElementByKey(key,pageObject))).click();
        logger.info("clicked on " + key);
    }

    @Step("Click on Element <key> by Xpath in <page>")
    public void clickByXpath(String key,String pageObject) throws IOException {
        driver.findElement(By.xpath(BackEnd.findElementByKey(key,pageObject))).click();
        logger.info("clicked on " + key);
    }

    @Step("Add to Favorites and Print <message>")
    public void addedFavorites(String message) {
        String[] pName = new String[3];
        for (int i = 1; i < 4; i++) {
            WebElement favorite = driver.findElement(By.xpath("//*[@id=\"divModels\"]/div[9]/div/div[1]/div[" + i + "]/div[1]"));
            favorite.click();
            pName[i - 1] = driver.findElement(By.xpath("//*[@id=\"divModels\"]/div[9]/div/div[1]/div[" + i + "]/a/div[2]/div/div[1]")).getText();
        }
        logger.info(message);
        aFav = pName;
    }

    @Step("Current Favorites")
    public void currentFavorites(){
        String[] fName = new String[3];
        for (int i = 1; i < 4; i++){
            fName[i-1] = driver.findElement(By.xpath("//*[@id=\"divModels\"]/div[5]/div[3]/div["+ i +"]/a/div[2]/div")).getText();
        }
        cFav = fName;
    }

    @Step("Compare Favorites and Print <message>")
    public void compareFavorites(String message){
        boolean found = false;
        for (int i = 1; i < 4; i++)
        {
            found = false;
            for (int j = 1; j < 4; j++)
            {
                if(aFav[i - 1].equals(cFav[j - 1]))
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        logger.info(message);
    }

    @Step("Check if All Selected and print <message>")
    public void checkAll(String message){
        for (int i = 1; i < 4; i++)
        {
            Assert.assertEquals("fav-select-box", (driver.findElement(By.xpath("//*[@class=\"row c-items\"]/div[" + i + "]/div/div[2]"))).getAttribute("class"));
        }
        logger.info(message);
    }

    @Step("Check if <text> is the same with element <key> in <page> by Xpath and print <message>")
    public void checkTextByByXpath(String text, String key, String pageObject, String message) throws IOException {
        Assert.assertEquals(text, (driver.findElement(By.xpath(BackEnd.findElementByKey(key,pageObject))).getText()));
        logger.info(message);
    }

    @Step("Check if <text> is the same with element <key> in <page> by Id and print <message>")
    public void checkTextById(String text, String key, String pageObject, String message) throws IOException {
        Assert.assertEquals(text, (driver.findElement(By.id(BackEnd.findElementByKey(key,pageObject))).getText()));
        logger.info(message);
    }

    @Step("Wait <number> seconds")
    public void wait(Integer number) throws  InterruptedException {
        TimeUnit.SECONDS.sleep((long) number);
    }

    @Step("Close")
    public void close() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
