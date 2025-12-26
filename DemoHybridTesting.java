package com.ibm.webApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HybridTesting {
static WebDriver driver;
	
	static Alert al;
	
	static XSSFWorkbook wb;
	
	static XSSFSheet sh;
	
	static FileInputStream fin;
	
	static File fileDataSheet;
	
	
	public static void setup() {
		driver = new EdgeDriver();
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public static void getApp() {
		driver.get("https://prep-mate-full-stack.vercel.app/");
	}
	public static void tearDown() throws InterruptedException {
		Thread.sleep(4000);
		driver.quit();
	}
	
	public static void testLogin(String email, String password) throws InterruptedException {
		driver.findElement(By.linkText("Login")).click();
		WebElement loginform = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div"));
		if(loginform.isDisplayed()) {
			driver.findElement(By.id("email")).sendKeys(email);
			driver.findElement(By.id("password")).sendKeys(password.trim());
			driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[2]/form/button")).click();
		}
		Thread.sleep(4000);
		String expectedTitle = "Dashboard";
		String actualTitle = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/h1")).getText();
		if(expectedTitle.equalsIgnoreCase(actualTitle)) {
			System.out.println("Logged in successfull");
		}else {
			System.out.println("Logged in failed. Try again");
		}
	}
	public static void browseInterview(String url) throws InterruptedException {
		System.out.println(url);
		driver.get(url);
		String expectedHeader = "Interview Experiences";
		String actualHeader = driver.findElement(By.xpath("//*[@id=\"root\"]/div/main/div/div/div[1]/div[1]/h1")).getText();
		if(expectedHeader.equalsIgnoreCase(actualHeader)) {
			System.out.println("Successfully redirected to the interview page");
		}else {
			System.out.println("Redirection to the interview page fails");
		}
	}
	
	public static void toogleTheme() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"root\"]/div/nav/div/div/button[2]")).click();
		Thread.sleep(4000);
		System.out.println("Theme toggled");
	}
	
	public static void aboutUs() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    WebElement aboutUsLink = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.cssSelector("a[href='/about-us']")
	        )
	    );

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", aboutUsLink);
        
	    try {
	        aboutUsLink.click();
	    } catch (Exception e) {
	        js.executeScript("arguments[0].click();", aboutUsLink);
	    }

	    String expectedUrl = "https://prep-mate-full-stack.vercel.app/about-us";
	    wait.until(ExpectedConditions.urlToBe(expectedUrl));

	    if (driver.getCurrentUrl().equalsIgnoreCase(expectedUrl)) {
	        System.out.println("Redirection to About Us successful");
	    } else {
	        System.out.println("Redirection to About Us unsuccessful");
	    }
	}

	
	public static void openAdminPanel() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	        By.xpath("//div[contains(@class,'fixed') and contains(@class,'inset-0')]")
	    ));

	    WebElement adminLink = wait.until(
	        ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[@href='/admin']")
	        )
	    );

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", adminLink);

	    try {
	        adminLink.click();
	    } catch (Exception e) {
	        js.executeScript("arguments[0].click();", adminLink);
	    }

	    String expectedUrl = "https://prep-mate-full-stack.vercel.app/admin";
	    wait.until(ExpectedConditions.urlToBe(expectedUrl));
        Thread.sleep(4000);
	    System.out.println(driver.getCurrentUrl());
	}
    

	public static void main(String[] args) throws InterruptedException, IOException {
		setup();
		getApp();
		ExcelUtility.loadData();
		for(int i = 1; i <= ExcelUtility.rowCount(); i++) {
			String keywords = ExcelUtility.getCellValue(i, 1);
			if(keywords.equalsIgnoreCase("login")) {
				String email = ExcelUtility.getCellValue(i, 2);
				String password = ExcelUtility.getCellValue(i, 3);
				testLogin(email, password);
			}else if(keywords.equalsIgnoreCase("browse_interview")) {
				String url = ExcelUtility.getCellValue(i, 2);
				System.out.println(url);
				browseInterview(url);
			}else if(keywords.equalsIgnoreCase("open_admin_panel")) {
				openAdminPanel();
			}else if(keywords.equalsIgnoreCase("toggleTheme")) {
				toogleTheme();
			}else if(keywords.contentEquals("about_us")) {
				aboutUs();
			}
		}
		tearDown();
	}
}
