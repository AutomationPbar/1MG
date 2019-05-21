package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.DBManager;

public class LabDataScrapperTor {
	
	
	private WebDriver driver;
	WebDriverWait wait;

	static String LiveDB_Path = "jdbc:sqlserver://automation-data.cma4hvr5eoya.ap-south-1.rds.amazonaws.com:1433;DatabaseName=PBCroma";
	private static String Liveusename = "admin";
	private static String Livepassword = "DBauto!#$asd";

	String tableName = "[Automation].[1MGData]";
	
	String baseUrl = "https://www.1mg.com/labs?utm_source=1mg&utm_medium=jewel&utm_campaign=labsgrowth";
	
	String excelpath = "C:\\docprime\\TestsMappingDocprime1MG.xlsx";
	String excelpath_update = "C:\\docprime\\datamap.xlsx";
	String sheetname = "Base Template";
	
	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;

	DBManager dbm = new DBManager();
	
	String resultdata[] = new String[14];
	
	

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		try{
			FirefoxOptions options = new FirefoxOptions();
            
			FirefoxProfile profile = new FirefoxProfile();
			        
			        profile.setPreference("webdriver.load.strategy", "unstable");
			        profile.setPreference("network.proxy.type", 1);
			        profile.setPreference("network.proxy.socks", "127.0.0.1");
			        profile.setPreference("network.proxy.socks_port", 9050);
			        profile.setPreference("dom.webnotifications.enabled", false);
			        options.setProfile(profile);
			        options.setBinary("C:\\Tor\\Browser\\firefox.exe");
			        
			        System.setProperty("webdriver.gecko.driver", "C:\\Tor\\geckodriver.exe");
			     
			        
			        driver = new FirefoxDriver(options);
			        driver.manage().window().maximize();

		wait = new WebDriverWait(driver, 10);

		//dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
		
		FileInputStream fis = new FileInputStream(excelpath);
		workbook = new XSSFWorkbook(fis);
		modelsheet = workbook.getSheetAt(0);

		
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		Date datedd = new Date();
		System.out.println(formatter.format(datedd));
		String localDate11 = formatter.format(datedd).toString();
		excelpath_update = "C:\\docprime\\datamap" + localDate11 + ".xlsx";
		//SetExcelFile(excelpath_update, sheetname);

		row = modelsheet.getRow(0);

		int colCount = row.getLastCellNum();
		System.out.println("Column Count :- " + colCount);

		rowCount = modelsheet.getLastRowNum() + 1;
		System.out.println("Row Count :- " + rowCount);
		 dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
		 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test 
	public void gettestnames(){
		
		try {
			driver.get(baseUrl);
			
			driver.manage().window().maximize();
			
			Thread.sleep(5000);
			
			for(int i = 204; i<=rowCount;i++){
				
				driver.get(baseUrl);
				try{
					driver.findElement(By.xpath(".//*[@id='labs-header']/div/div[3]/div/div/div/div/div/div/div/span[2]")).click();
				}catch(Exception exp){
					//exp.printStackTrace();
				}
								
				try{
					String fbtogether = "";
					String testsfor = "";
					String needed = "";
					String overview = "";
					String preparation = "";
					String result = "";
					String includes = "";
					String testsincluded = "";
					String url= "";
					String seourl= "";
					String remarks = "";
					StringBuilder sb= new StringBuilder();
					StringBuilder sb2= new StringBuilder();
					driver.get(baseUrl);
					remarks = "excel row number is " +String.valueOf(i);
					Thread.sleep(6000);
					
					row = modelsheet.getRow(i);
			        // Create an object reference of 'Cell' class
						Cell cell1=  row.getCell(0);
			            Cell cell = row.getCell(1);
			            Cell cellmg = row.getCell(2);
			            CellType modelcell = cell1.getCellTypeEnum();
			            String docid= null;
			            
			            
			           if(modelcell == CellType.NUMERIC){
			            
			           
			            docid=modelsheet.getRow(i).getCell(0).getRawValue().toString();
			            System.out.println("doc id is " + docid);
			            }
			            String testname = cell.getStringCellValue();
			            
			            System.out.println("the test name is " + testname);
			            
			            driver.navigate().refresh();
			            pom.Labs.searchbox(driver).click();
			            
			            Thread.sleep(5000);
			            String mgtestname = null;
			            mgtestname= cellmg.getStringCellValue();
			            System.out.println("1 mg testname is " + mgtestname);
			            
			            sendHumanKeys(pom.Labs.searchbox(driver), mgtestname);
			            
			          //  pom.Labs.searchbox(driver).sendKeys(mgtestname);
			            Thread.sleep(5000);
			            pom.Labs.firsttest(driver).click();
			            
			            Thread.sleep(5000);
			            try{
							
							driver.findElement(By.xpath("//*[@id='labs-header']/div/div[2]/div[1]/div/div/div/div[1]/span")).click();
							}catch(Exception ele){
								
							}
			            String labc = pom.Labs.labcount(driver).getText();
			            
			            labc = labc.substring(labc.indexOf("(") + 1);
			            labc = labc.substring(0, labc.indexOf(")"));
			            
			            System.out.println("the lab count is  " + labc);
			            
			            List <WebElement>frequentb = pom.Labs.frequentlybooked(driver);
			            int frequentbcount = frequentb.size();
			            
			            for(int f=1;f<=frequentbcount;f++){
			            	String fb = "";
			            	fb = pom.Labs.frequentlybookeditem(driver, f).getText();
			            	sb.append(fb).append(",");
			            	
			            }
			            fbtogether = sb.toString();
		            	System.out.println("the frequently booked tests are " + fbtogether);
		            	
		            	pom.Labs.selectlab(driver).click();
		            	Thread.sleep(5000);
		            	try{
							
							driver.findElement(By.xpath("//*[@id='labs-header']/div/div[2]/div[1]/div/div/div/div[1]/span")).click();
							}catch(Exception ele){
								
							}
		            	try{
		            	includes = pom.Labs.includes(driver).getText();
		            	includes = includes.substring(includes.indexOf("(") + 1);
		            	includes = includes.substring(0, includes.indexOf(")"));
		            	
		            	System.out.println("the number of tests included is " + includes);
		            	testsincluded = pom.Labs.testsincluded(driver).getText();
		            	
		            	System.out.println("The tests included are " + testsincluded);
		            	}catch(Exception e){
		            		
		            	}
		            	
		            	url = driver.getCurrentUrl();
		            	
		            	seourl=driver.getCurrentUrl();
		            	
		            	overview = pom.Labs.overview(driver).getText();
		            	Thread.sleep(2000);
		            	testsfor = pom.Labs.testsfor(driver).getText();
		            	Thread.sleep(2000);
		            	needed = pom.Labs.needtoprovide(driver).getText();
		            	Thread.sleep(2000);
		            	
		            	result = pom.Labs.interpretingresult(driver).getText();
		            	System.out.println("overview is " +overview);
		            	System.out.println("interpretig result " + result);
		            	System.out.println("need to provide " + needed);
		            	System.out.println("tests for  " + testsfor);
		            	
		            	 List <WebElement>prepare = pom.Labs.preparation(driver);
				            int preparecount = prepare.size();
				            
				            for(int p=1;p<=preparecount;p++){
				            	String pb = "";
				            	pb = pom.Labs.preparationitem(driver, p).getText();
				            	sb2.append(pb).append(",");
				            	
				            }
				            preparation = sb2.toString();
			            	System.out.println("the preparations are " +preparation);
			            	try{
			            	System.out.println("Going to insert data in table");
			            	dbm.SetMgLabData(testname,docid,"",mgtestname,url,seourl, includes,needed,testsfor, preparation,labc, overview,result,testsincluded,fbtogether,remarks,tableName );
			            	}catch(Exception sq){
			            		sq.printStackTrace();
			            	}
			            	
			            	
			           
				}catch(Exception e){
					try{
					
					driver.findElement(By.xpath("//*[@id='labs-header']/div/div[2]/div[1]/div/div/div/div[1]/span")).click();
					}catch(Exception ele){
						
					}
				//e.printStackTrace();
				}
			}		
		}catch(Exception ex){
			
			ex.printStackTrace();
			}
				}

 	

	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File
			XSSFWorkbook wb = new XSSFWorkbook();
			
			XSSFSheet sh = wb.createSheet(sheetName);
			
			 sh = wb.getSheet(sheetName);

			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
            fileOut.close();
            wb.close();
            System.out.println("Your excel file has been generated!");

		} catch (Exception e) {
			throw (e);
		}

	}
	
	
	public static void sendHumanKeys(WebElement element, String text) {
	    Random r = new Random();
	    for(int i = 0; i < text.length(); i++) {
	        try {
	            Thread.sleep((int)(r.nextGaussian() * 15 + 100));
	        } catch(InterruptedException e) {}
	        String s = new StringBuilder().append(text.charAt(i)).toString();
	        element.sendKeys(s);
	    }
	}
	}


	