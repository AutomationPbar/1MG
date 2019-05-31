package pom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Labs {
	
WebDriver driver;
	
	public static WebElement searchbox(WebDriver driver){
		
		WebElement sm =driver.findElement(By.id("search-tests-input"));
		
		return sm;
	}
	
	
public static WebElement firsttest(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='search-test-dropdown']/div[1]/div[2]/div"));
		
		return sm;
	}

	public static WebElement labcount(WebDriver driver){
	
	WebElement sm =driver.findElement(By.xpath("//*[@id='body-container']/div/div/div[3]/div/div/div[1]/div/div[1]/div[1]/h1"));
	
	return sm;
	}
	
	public static WebElement needtoprovide(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Test Detail']/div[3]/div[1]/div/div[2]"));
		
		return sm;
		}
	
	public static WebElement testsfor(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Test Detail']/div[3]/div[2]/div/div[2]"));
		
		return sm;
		}

	public static List<WebElement> preparation(WebDriver driver){
		
		List<WebElement> sm =driver.findElements(By.xpath("//*[@id='Test Detail']/div[4]/ol/li"));
		
		return sm;
		}
	
	public static  WebElement preparationitem(WebDriver driver,int i){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Test Detail']/div[4]/ol/li["+i+"]"));
		
		return sm;
		}
	
	public static  WebElement overview(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Overview']/div[2]"));
		
		return sm;
		}
		
	public static  WebElement interpretingresult(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Interpreting Results']/div[2]"));
		
		return sm;
		}
	
	public static  WebElement testsincluded(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='Tests Included']/div[2]"));
		
		return sm;
		}
	
	public static List<WebElement> frequentlybooked(WebDriver driver){
		
		List<WebElement> sm =driver.findElements(By.xpath("//*[@id='body-container']/div/div/div[3]/div/div/div[2]/div/div[3]/div/div[2]/div/div"));
		
		return sm;
		}
	
	
	public static  WebElement frequentlybookeditem(WebDriver driver,int i){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='body-container']/div/div/div[3]/div/div/div[2]/div/div[3]/div/div[2]/div/div["+i+"]/div/div/div/div/a/div/div[1]"));
		
		return sm;
		}
	
	public static  WebElement selectlab(WebDriver driver){
		
		WebElement sm =driver.findElement(By.xpath("//*[@id='body-container']/div/div/div[3]/div/div/div[1]/div/div[2]/div[1]/div/div/div[1]/a/div/span"));
		
		return sm;
		}

	public static  WebElement includes(WebDriver driver){
	
	WebElement sm =driver.findElement(By.xpath("//*[@id='Tests Included']/div[1]/h2/span"));
	
	return sm;
	}
}
