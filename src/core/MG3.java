package core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import pom.Medicines;
import utilities.DBManager;

import org.apache.poi.hpsf.VariantSupport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MG3 {
	private WebDriver driver;
	WebDriverWait wait;

	JSONArray Alphabet = new JSONArray();
	JSONObject Medicine = new JSONObject();
	JSONObject Variant = new JSONObject();
	JSONObject Drug = new JSONObject();

	String LiveDB_Path = "jdbc:sqlserver://10.0.10.42:1433;DatabaseName=PBCroma";
	private String Liveusename = "PBLIVE";
	private String Livepassword = "PB123Live";

	String tableName = "Automation.Practo1MG";

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

		dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);
	}

	@Test(priority = 1)
	public void testUntitledTestCase() throws Exception {

		for (char alphabet = 'E'; alphabet <= 'F'; alphabet++) {

			try {

				driver.manage().window().maximize();

				String baseUrl = "https://www.1mg.com/brand-list?name=";

				System.out.println("Current Alphabet : " + alphabet);

				baseUrl = baseUrl + alphabet + "#";

				driver.get(baseUrl);

				Thread.sleep(3000);

				driver.navigate().refresh();

				WebElement next = wait.until(ExpectedConditions.visibilityOf(
						driver.findElement(By.xpath("//*[@aria-label='Next' and @href='#']//parent::li"))));

				System.out.println("Attribute value of next button  " + next.getAttribute("Class"));

				String parentWin = driver.getWindowHandle();

				while (next.getAttribute("Class") == null) {

					try {

						int pageno = 1;

						int medcount = 0;

						try {

							medcount = driver.findElements(By.xpath("//*[@id='srchRslt']/div")).size();

						} catch (Exception e) {
							e.printStackTrace();
						}

						System.out.println("Total number of medicines on current page : " + medcount);

						if (medcount >= 1) {

							for (int i = 1; i <= medcount; i++) {

								try {

									Thread.sleep(2000);

									System.out.println("getting medicine name for : " + i);

									String s = Medicines.medicine(driver, i).getText();

									System.out.println("Medicine name : " + s);

									try {

										if (s.isEmpty() || s.equals(null)) {

											System.out.println("Med Name Blank");
											Thread.sleep(2000);

											s = Medicines.medicine(driver, i).getText();
										}

									} catch (Exception e) {

									}

									if (s.charAt(0) == alphabet) {

										String medNameURL = Medicines.medicine(driver, i).getAttribute("href");

										System.out.println("Med URL : " + medNameURL);

										((JavascriptExecutor) driver).executeScript("window.open()");

										ArrayList<String> handles2 = new ArrayList<String>(driver.getWindowHandles());

										System.out.println("Tabs :" + handles2.size());

										if (handles2.size() > 1) {

											driver.switchTo().window(handles2.get(1));

											driver.get(medNameURL);

										}

										Thread.sleep(3000);

										int brandcount = 0;

										try {

											brandcount = driver.findElements(By.xpath("//*[@class='row brand-item']"))
													.size();

										} catch (Exception e) {
											e.printStackTrace();
										}

										System.out.println("variants available " + brandcount);

										for (int j = 1; j <= brandcount; j++) {

											try {

												if (!driver
														.findElements(By
																.xpath("//*[@class='pgntnCntnrBar btn btn-primary text-small button-text']"))
														.isEmpty()) {

													Medicines.loadmore(driver).click();

												}

												WebElement brand = wait.until(ExpectedConditions
														.elementToBeClickable(Medicines.brand(driver, j)));

												String quantity = "";

												try {

													quantity = driver
															.findElement(By
																	.xpath(".//*[@id='content-container']/div/div/div/div/div[2]/div/div/ul/li["
																			+ j + "]/div/div[2]/div[1]/div[1]"))
															.getText();

												} catch (Exception e) {
													e.printStackTrace();
												}

												System.out.println("Name of medicine " + brand.getText());

												brand.click();

												Thread.sleep(2000);

												ArrayList<String> handles1 = new ArrayList<String>(
														driver.getWindowHandles());

												System.out.println("Tabs :" + handles1.size());

												if (handles1.size() > 2) {

													driver.switchTo().window(handles1.get(2));

												}

												String url = driver.getCurrentUrl();

												String medPageURL = driver.getCurrentUrl();

												System.out.println("Medicine Details window URL " + medPageURL);

												JSONObject MedData = new JSONObject();

												try {
													if (url.contains("/drugs/")) {
														drugsurl(MedData);
													} else if (url.contains("/otc/")) {
														otcurl(MedData);
													}

													MedData.put("Quantity", quantity);

													driver.close();

													driver.switchTo().window(handles1.get(1));

													System.out.println("Current window " + driver.getTitle());

													System.out.println("Current URL : " + driver.getCurrentUrl());

													try {

														if (!(driver.findElement(By.id("Capa_1"))).equals(null)) {
															driver.findElement(By.id("Capa_1")).click();
														}

													} catch (Exception e) {

													}

												} catch (Exception e) {
													e.printStackTrace();
												}

												Drug.put("Medicine Data", MedData);
												String jsonData1 = Drug.toString();

												String variantName = brand.getText();

												String remarks = String.valueOf(pageno) + " - " + medPageURL;

												try {

													System.out.println("Going to insert data in table");

													dbm.SetPractoLabData(s, variantName, jsonData1, remarks, tableName);

												} catch (SQLServerException e) {

													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

													// e.printStackTrace();

												} catch (Exception e) {

													dbm.DBConnection(LiveDB_Path, Liveusename, Livepassword);

													e.printStackTrace();

												}

												System.out.println("JSON Data " + jsonData1);
											} catch (Exception e) {

											}

										}
										// Variant.put("Drug type", Drug);

										// Medicine.put("Variant type",
										// Variant);

										driver.close();
										driver.switchTo().window(parentWin);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}

							}

						}

						driver.findElement(By.xpath("//*[@aria-label='Next']/span")).click();

						Thread.sleep(1500);

						System.out.println("attribute value" + next.getAttribute("Class"));

						pageno = pageno + 1;

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// Alphabet.put(Medicine);
				// String jsonData = Alphabet.toString();

				// System.out.println("JSON Data " + jsonData);

			} catch (Exception e) {

			}
		}

	}

	public void drugsurl(JSONObject MedData) {

		try {
			String medname = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div/h1")).getText();
			System.out.println(medname);
			MedData.put("Medicine Name", medname);
		} catch (Exception e) {

		}
		try {
			String companyname = driver
					.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div[2]/div/a/div")).getText();
			System.out.println(companyname);
			MedData.put("Company Name", companyname);
		} catch (Exception e) {

		}
		try {
			String saltname = driver.findElement(By.xpath("//*[@class='saltInfo DrugInfo__salt-name___2-9Vh']"))
					.getText();
			System.out.println(saltname);
			MedData.put("Salt Name", saltname);
		} catch (Exception e) {

		}
		try {
			String presc = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div/div/div[2]/div[2]"))
					.getText();
			System.out.println(presc);
			MedData.put("Prescription", presc);
		} catch (Exception e) {

		}

		try {
			String usedfor = driver.findElement(By.xpath("//*[@class='DrugInfo__uses___381Re']")).getText();
			System.out.println(usedfor);
			MedData.put("Used For", usedfor);
		} catch (Exception e) {

		}
		// driver.findElement(By.xpath("//div[@id='container']/div/div[2]/div[2]")).click();
		try {
			String mrp = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[2]")).getText();
			System.out.println(mrp);
			MedData.put("MRP", mrp);
		} catch (Exception e) {

		}
		try {
			String mrppertab = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[3]/div"))
					.getText();
			System.out.println(mrppertab);
			MedData.put("MRP per Tab", mrppertab);
		} catch (Exception e) {

		}
		try {
			// driver.findElement(By.xpath("//div[@id='top_row']/div/div/div/div")).click();
			String pack = driver.findElement(By.xpath("//div[@id='top_row']/div/div/div[2]/div/div/div[4]")).getText();
			System.out.println(pack);
			MedData.put("Pack available", pack);

		} catch (Exception e) {

		}
		try {

			String uses = driver.findElement(By.xpath("//div[@id='uses_0']/div[2]")).getText();
			System.out.println(uses);
			MedData.put("Uses ", uses);
		} catch (Exception e) {

		}
		try {
			// driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
			String sideeffects = driver.findElement(By.xpath("//div[@id='side_effects_0']/div[2]/div/p")).getText();
			System.out.println(sideeffects);
			MedData.put("Side Effects", sideeffects);
		} catch (Exception e) {

		}
		try {
			// driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
			String howtouse = driver.findElement(By.xpath("//div[@id='how_to_use_0']/div[2]")).getText();
			System.out.println(howtouse);
			MedData.put("How to use", howtouse);

		} catch (Exception e) {

		}
		try {
			// driver.findElement(By.xpath("//div[@id='overview']/div/div")).click();
			String howitworks = driver.findElement(By.xpath("//div[@id='how_it_works_0']/div")).getText();
			Thread.sleep(1500);
			System.out.println(howitworks);
			Thread.sleep(1500);
			MedData.put("How it Works", howitworks);
		} catch (Exception e) {

		}
		try {
			String Alcohol = driver.findElement(By.xpath("//div[@id='precautions']/div/div/div/div[4]")).getText();
			System.out.println("Alcohol" + Alcohol);
			MedData.put("Alcohol ", Alcohol);
		} catch (Exception e) {

		}
		try {
			String Pregnancy = driver.findElement(By.xpath("//div[@id='precautions']/div/div[2]/div/div[4]")).getText();
			System.out.println("Pregnancy" + Pregnancy);
			MedData.put("Pregnancy", Pregnancy);
		} catch (Exception e) {

		}
		try {
			String Lactation = driver.findElement(By.xpath("//div[@id='precautions']/div/div[3]/div/div[4]")).getText();
			System.out.println("Lactation" + Lactation);
			MedData.put("Lactation", Lactation);
		} catch (Exception e) {

		}
		try {
			String Driving = driver.findElement(By.xpath("//div[@id='precautions']/div/div[4]/div/div[4]")).getText();
			System.out.println("Driving" + Driving);
			MedData.put("Driving", Driving);
		} catch (Exception e) {

		}
		try {
			String Kidney = driver.findElement(By.xpath("//div[@id='precautions']/div[2]/div/div/div[4]")).getText();
			System.out.println("Kidney" + Kidney);
			MedData.put("Kidney", Kidney);
		} catch (Exception e) {

		}
		try {
			String Liver = driver.findElement(By.xpath("//div[@id='precautions']/div[2]/div[2]/div/div[4]")).getText();
			System.out.println("Liver" + Liver);
			MedData.put("Liver", Liver);
		} catch (Exception e) {

		}

	}

	public void otcurl(JSONObject MedData) {

		try {
			String medname = driver.findElement(By.xpath(".//*[@id='container']/div/div[1]/div[3]/div[1]/div[1]/h1"))
					.getText();
			System.out.println(medname);
			MedData.put("Medicine Name", medname);
		} catch (Exception e) {

		}
		try {
			String companyname = driver.findElement(By.xpath(".//*[@id='container']/div/div[1]/div[3]/div[1]/div[2]/a"))
					.getText();
			System.out.println(companyname);
			MedData.put("Company Name", companyname);
		} catch (Exception e) {

		}
		try {

			int packcount = 0;

			packcount = driver.findElements(By.xpath(".//*[@id='container']/div/div[1]/div[3]/div[3]/div/div']"))
					.size();

			packcount = packcount - 1;

			System.out.println("Pack count : " + packcount);

			for (int i = 1; i <= packcount; i++) {

				String packsize = driver.findElement(By.xpath(
						".//*[@id='container']/div/div[1]/div[3]/div[3]/div/div[" + (i + 1) + "]/div/div/div[1]/div"))
						.getText();
				String packsizeinput = "Pack Size" + String.valueOf(i);

				System.out.println("Pack Size : " + packsize);
				MedData.put(packsizeinput, packsize);

				String packcost = driver.findElement(By.xpath(".//*[@id='container']/div/div[1]/div[3]/div[3]/div/div["
						+ (i + 1) + "]/div/div/div[2]/div[1]")).getText();
				String packCostinput = "Pack Cost" + String.valueOf(i);

				System.out.println("Pack Cost : " + packcost);
				MedData.put(packCostinput, packcost);

			}
		} catch (Exception e) {

		}

		try {
			String cost = driver.findElement(By.xpath(".//*[@id='container']/div/div[1]/div[4]/div[1]/div/div[1]"))
					.getText();
			System.out.println(cost);
			MedData.put("mrp", cost);
		} catch (Exception e) {

		}

		try {
			String howitworks = driver.findElement(By.xpath(".//*[@id='container']/div/div[2]/div[1]/div[1]/div[2]"))
					.getText();
			System.out.println(howitworks);
			MedData.put("How it Works", howitworks);
		} catch (Exception e) {

		}
	}

}