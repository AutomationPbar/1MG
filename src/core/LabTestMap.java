package core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import java.util.Date;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.DBManager;

public class LabTestMap {
	
	
	private WebDriver driver;
	WebDriverWait wait;


	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	String tableName = "Automation.Practo1MG";
	
	String baseUrl = "https://www.1mg.com/labs?utm_source=1mg&utm_medium=jewel&utm_campaign=labsgrowth";
	
	String excelpath = "C:\\docprime\\LabTestsScraper.xlsx";
	String excelpath_update = "C:\\docprime\\dataentry.xlsx";
	String sheetname = "Base Template";
	
	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;

	DBManager dbm = new DBManager();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {

		System.setProperty("webdriver.chrome.driver", "C://eclipse//chromedriver.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(capabilities);

		wait = new WebDriverWait(driver, 10);

		//dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
		
		FileInputStream fis = new FileInputStream(excelpath);
		workbook = new XSSFWorkbook(fis);
		modelsheet = workbook.getSheetAt(0);

		
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
		Date datedd = new Date();
		System.out.println(formatter.format(datedd));
		String localDate11 = formatter.format(datedd).toString();
		excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry_cmt" + localDate11 + ".xlsx";
		SetExcelFile(excelpath_update, sheetname);

		row = modelsheet.getRow(0);

		int colCount = row.getLastCellNum();
		System.out.println("Column Count :- " + colCount);

		rowCount = modelsheet.getLastRowNum() + 1;
		System.out.println("Row Count :- " + rowCount);
	}
	
	
	@Test 
	public void gettestnames(){
		
		
		

		try {
			driver.get(baseUrl);
			driver.manage().window().maximize();
			
			Thread.sleep(5000);
			
			for(int i = 1; i<=rowCount;i++){
				
					
					
					row = modelsheet.getRow(i);
			        // Create an object reference of 'Cell' class
			            Cell cell = row.getCell(1);
			            CellType modelcell = cell.getCellTypeEnum();
			            
			            String testname = cell.getStringCellValue();
			            
			            System.out.println("the test name is " + testname);
				 
				
				
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


}
