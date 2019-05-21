package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Medicines {

	WebDriver driver;
	
	public static WebElement seemore(WebDriver driver){
		
		WebElement sm =driver.findElement(By.id("brand-see-more"));
		
		return sm;
	}
	
	public static WebElement medicine(WebDriver driver, int divno){
		
	WebElement medname= driver.findElement(By.xpath("//*[@id='srchRslt']/div["+divno+"]/a"));
		
		return medname;
	}
	
	
	public static WebElement brand(WebDriver driver,int row){
		
		WebElement brand= driver.findElement(By.xpath("//*[@class='brand-list']/li["+row+"]/div[1]/div[1]/div/a"));
			
			return brand;
		}
	
	public static WebElement loadmore(WebDriver driver){
		
		WebElement loadmore= driver.findElement(By.xpath("//*[@class='pgntnCntnrBar btn btn-primary text-small button-text']"));
			
			return loadmore;
		}
}
