package com.comcast.vde.cm.sel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import fit.ColumnFixture;

public class VDECMImplSel extends ColumnFixture {

	String chromePath ;
	public static WebDriver driver1;
	private String streamerName1;
	String status="Error has occured";
	Boolean streamerCount=true;
	Boolean invalidKey=false;
	int streamIndexValue;
	int streamPidValue;
	int streamCountValue;
	Boolean streamCount=false;
	String streamUrlValue;
	String streamNameValue;
	String streamEnableValue;
	String updateName="adZone";
	String key;
	String updateValue;
	String sourceCidrValue;
	Boolean sourceCidr=false,duplicate=false;
	Boolean streamIndex=false,streamEnable=false;
	Boolean streamPid=false, streamUrl=false, streamName=false;
	static Properties prop;
	static InputStream input;
	String ebifObject;
	String ebifObjectValue,audioObject,audioObjectValue;
	String adsObject,inputObject,inputObjectValue;
	String adsObjectValue,programObject,programObjectValue,manifestUrl;
	Boolean invalidCheck=false;
	Boolean alertCheck=false;
	String latencyValue;
	Boolean uiCheck=false;
	String outputObject;
	String outputObjectValue;
	String parentKey;
	String childKey;
	String configValue,configObject,childConfig;

	public String loadProperties() throws IOException
	{
		try {
			prop=new Properties();
			input = null;
			input = new FileInputStream("C:\\Maneesha\\workspace\\git\\VDE-Fitnesse-master\\VDE-Fitnesse-master\\VdeFixtures\\config.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Properties loaded";
	}

	public String loginVDECm() throws InterruptedException, IOException
	{
		status="Login failed";
		System.setProperty("webdriver.chrome.driver", prop.getProperty("chromePath"));
		driver1=new ChromeDriver();
		String currentWindowHandle = driver1.getWindowHandle();
		//Switch back to to the window using the handle saved earlier
		driver1.switchTo().window(currentWindowHandle);
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver1.get(prop.getProperty("cmUrl"));
		driver1.manage().window().maximize();
		Thread.sleep(1000);
		WebElement vdeLogo=driver1.findElement(By.xpath(prop.getProperty("vdeLogo")));
		if(uiCheck==true && vdeLogo.isDisplayed() )
		{
			status="CM UI is available on the browser";
			driver1.close();
		}
		else
		{
			WebElement uname=driver1.findElement(By.xpath(prop.getProperty("uname")));
			uname.click();
			uname.sendKeys(prop.getProperty("unameValue"));
			WebElement upass=driver1.findElement(By.xpath(prop.getProperty("upass")));
			upass.click();
			upass.sendKeys(prop.getProperty("upassValue"));
			WebElement login=driver1.findElement(By.xpath(prop.getProperty("loginBt")));
			login.click();
			Thread.sleep(1000);
			WebElement logout=driver1.findElement(By.xpath(prop.getProperty("logout")));
			if(logout.isDisplayed())
			{
				status="Login successful";
			}}
		return status;		
	}

	public void searchStreamer() throws InterruptedException
	{
		WebElement searchBt=driver1.findElement(By.xpath(prop.getProperty("searchBt")));
		WebDriverWait wait = new WebDriverWait(driver1, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("searchBt"))));
		JavascriptExecutor js = (JavascriptExecutor)driver1;
		js.executeScript("window.scrollTo(0,0)"); 
		Actions actions=new Actions(driver1);
		actions.moveToElement(searchBt).click().build().perform();
		Select search=new Select(driver1.findElement(By.xpath(prop.getProperty("searchDropDown"))));
		search.selectByVisibleText("by streamer name");;
		WebElement streamerText=driver1.findElement(By.xpath(prop.getProperty("streamerTextBox")));
		streamerText.sendKeys(streamerName1);
		WebElement searchBt1=driver1.findElement(By.xpath(prop.getProperty("searchBt1")));
		searchBt1.click();		
	}

	public String searchStreamerByName() throws InterruptedException
	{
		driver1.navigate().back();
		Thread.sleep(1000);
		WebElement searchBt=driver1.findElement(By.xpath(prop.getProperty("searchBt")));
		WebDriverWait wait = new WebDriverWait(driver1, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("searchBt"))));
		JavascriptExecutor js = (JavascriptExecutor)driver1;
		js.executeScript("window.scrollTo(0,0)"); 
		Actions actions=new Actions(driver1);
		actions.moveToElement(searchBt).click().build().perform();
		Select search=new Select(driver1.findElement(By.xpath(prop.getProperty("searchDropDown"))));
		search.selectByVisibleText("by streamer name");;
		WebElement streamerText=driver1.findElement(By.xpath(prop.getProperty("streamerTextBox")));
		streamerText.sendKeys(streamerName1);
		WebElement searchBt1=driver1.findElement(By.xpath(prop.getProperty("searchBt1")));
		searchBt1.click();
		WebElement streamerName=driver1.findElement(By.linkText(streamerName1.toUpperCase()));
		/*String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();*/
		if(streamerName.getText().equalsIgnoreCase(streamerName1))
		{
			status="Streamer is present";
		}
		return status;
	}

	public String verifyVersion()
	{
		WebElement version=driver1.findElement(By.xpath(prop.getProperty("footerVersion")));
		if(version.isDisplayed() && version.getText().equals(prop.getProperty("versionValue1")));
		{
			status="The correct Version Is displayed";
		}
		return status;
	}
	
	public String deleteStreamerHome() throws InterruptedException
	{
		searchStreamer();
		Thread.sleep(1000);
		WebElement deleteBt=driver1.findElement(By.xpath(prop.getProperty("deleteBt")));
		deleteBt.click();
		WebElement deleteMsg=driver1.findElement(By.xpath(prop.getProperty("deleteMsg")));
		Thread.sleep(1000);
		if(deleteMsg.isDisplayed())
		{
			WebElement confirmDel=driver1.findElement(By.xpath(prop.getProperty("confirmDel")));
			confirmDel.click();
			status="Streamer has been deleted successfully";
			WebElement retunHome1=driver1.findElement(By.xpath(prop.getProperty("returnHome")));
			retunHome1.click();}
		return status;
		
	}

	public String searchStreamerByUrl()
	{
		driver1.navigate().back();
		WebElement searchBt=driver1.findElement(By.xpath(prop.getProperty("searchBt")));
		WebDriverWait wait = new WebDriverWait(driver1, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("searchBt"))));
		JavascriptExecutor js = (JavascriptExecutor)driver1;
		js.executeScript("window.scrollTo(0,0)"); 
		Actions actions=new Actions(driver1);
		actions.moveToElement(searchBt).click().build().perform();
		Select search=new Select(driver1.findElement(By.xpath(prop.getProperty("searchDropDown"))));
		search.selectByVisibleText("by program url");;
		WebElement streamerText=driver1.findElement(By.xpath(prop.getProperty("streamerTextBox")));
		streamerText.sendKeys(prop.getProperty("programUrl"));
		WebElement searchBt1=driver1.findElement(By.xpath(prop.getProperty("searchBt1")));
		searchBt1.click();
		WebElement streamerName=driver1.findElement(By.linkText(streamerName1.toUpperCase()));
		/*String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();*/
		if(streamerName.getText().equalsIgnoreCase(streamerName1))
		{
			status="Streamer is present";
		}
		return status;
	}

	public String duplicateStreamer() throws InterruptedException
	{
		searchStreamer();
		//driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement duplicateIcon=driver1.findElement(By.xpath(prop.getProperty("duplicateIcon")));
		duplicateIcon.click();
		WebElement duplicateNameBox=driver1.findElement(By.xpath(prop.getProperty("duplicateNameBox")));
		duplicateNameBox.click();
		duplicateNameBox.sendKeys(prop.get("duplicateName1").toString());
		Thread.sleep(1000);
		WebElement duplicateBt=driver1.findElement(By.xpath(prop.getProperty("duplicateBt")));
		duplicateBt.click();
		((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		
		WebElement createButton=driver1.findElement(By.xpath(prop.getProperty("createBt1")));
		createButton.click();
		WebElement searchBt=driver1.findElement(By.xpath(prop.getProperty("searchBt")));
		WebDriverWait wait = new WebDriverWait(driver1, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(prop.getProperty("searchBt"))));
		JavascriptExecutor js = (JavascriptExecutor)driver1;
		js.executeScript("window.scrollTo(0,0)"); 
		Actions actions=new Actions(driver1);
		actions.moveToElement(searchBt).click().build().perform();
		Select search=new Select(driver1.findElement(By.xpath(prop.getProperty("searchDropDown"))));
		search.selectByVisibleText("by streamer name");;
		WebElement streamerText=driver1.findElement(By.xpath(prop.getProperty("streamerTextBox")));
		streamerText.sendKeys(prop.getProperty("duplicateName1"));
		WebElement searchBt1=driver1.findElement(By.xpath(prop.getProperty("searchBt1")));
		searchBt1.click();
		driver1.findElement(By.linkText(prop.getProperty("duplicateName1").toUpperCase())).click();
		String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();
		if(streamerNameTxt.equalsIgnoreCase(prop.getProperty("duplicateName1")))
		{
			status="Duplicate Streamer has been created successfully";
		}
		return status;
	}

	//Temp method
	public void updateProgramError() throws InterruptedException
	{
		WebElement urlErrorMsg1=driver1.findElement(By.xpath(prop.getProperty("urlErrorMsg1")));
		Thread.sleep(1000);
		if(urlErrorMsg1.isDisplayed())
		{
			WebElement urlErrorUpdate1=driver1.findElement(By.xpath(prop.getProperty("urlErrorUpdate1")));
			Thread.sleep(1000);
			urlErrorUpdate1.click();
		}
	}
	
	public String createStreamer() throws InterruptedException
	{
		status="Streamer has not been created successfully";
		if(streamerCount==false)
		{
			Select version=new Select(driver1.findElement(By.xpath(prop.getProperty("version"))));
			version.selectByVisibleText(prop.getProperty("versionValue"));
			WebElement createBt=driver1.findElement(By.xpath(prop.getProperty("createBt")));
			createBt.click();
			Thread.sleep(1000);
		}
		else 
		{
			WebElement addStreamer=driver1.findElement(By.xpath(prop.getProperty("addStreamer")));
			addStreamer.click();
			Select version1=new Select(driver1.findElement(By.xpath(prop.getProperty("version1"))));
			version1.selectByVisibleText(prop.getProperty("versionValue"));
			Thread.sleep(2000);
			WebElement create1=driver1.findElement(By.xpath(prop.getProperty("create")));
			create1.click();
		}
		if(driver1.findElement(By.xpath(prop.getProperty("addStreamerTxt"))).getText().equals("Add New Streamer"))
		{
			WebElement streamerName=driver1.findElement(By.xpath(prop.getProperty("streamerNameTextBox")));
			streamerName.sendKeys(streamerName1);
			Thread.sleep(1000);
			if(duplicate==true)
			{
				((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				WebElement location= driver1.findElement(By.xpath(prop.getProperty("location")));
				Select location1=new Select(location);
				location1.selectByValue("MS03-Snoopy");
				Thread.sleep(1000);
				JavascriptExecutor js = (JavascriptExecutor)driver1;
				js.executeScript("window.scrollTo(0,0)");
			}
			WebElement location= driver1.findElement(By.xpath(prop.getProperty("location")));
			Select location1=new Select(location);
			location1.selectByValue("MS03-Snoopy");
			JavascriptExecutor js = (JavascriptExecutor)driver1;
			js.executeScript("window.scrollTo(0,0)");
			Thread.sleep(1000);
			WebElement sourceCidrBox=driver1.findElement(By.xpath(prop.getProperty("sourecCidrBox")));
			sourceCidrBox.click();
			sourceCidrBox.sendKeys(prop.getProperty("sourceCidrValue"));
			if(sourceCidr==true)
			{
				WebElement sourceCidrBox1=driver1.findElement(By.xpath(prop.getProperty("sourecCidrBox")));
				sourceCidrBox1.click();
				sourceCidrBox1.clear();
				sourceCidrBox1.click();
				sourceCidrBox1.sendKeys(sourceCidrValue);
			}
			WebElement destAddress=driver1.findElement(By.xpath(prop.getProperty("destAddress")));
			destAddress.clear();
			destAddress.click();
			destAddress.sendKeys(prop.getProperty("destAddressValue"));
			Thread.sleep(1000);
			WebElement dstPortBox=driver1.findElement(By.xpath(prop.getProperty("udpDstPortBox")));
			dstPortBox.clear();
			dstPortBox.click();
			dstPortBox.sendKeys(prop.getProperty("dstPortValue"));
			Thread.sleep(1000);
			/*WebElement location= driver1.findElement(By.xpath(prop.getProperty("location")));
			Select location1=new Select(location);
			location1.selectByValue("MS03-Snoopy");
			Thread.sleep(1000);*/
			// to add the program urls
			WebElement programUrl=driver1.findElement(By.xpath(prop.getProperty("programUrlTextBox")));
			programUrl.sendKeys(prop.getProperty("programUrl1"));
			Thread.sleep(1000);
			
			WebElement verifyBt=driver1.findElement(By.xpath(prop.getProperty("verifyUrl")));
			Thread.sleep(1000);
			verifyBt.click();
			Thread.sleep(2000);
			WebElement closeBt=driver1.findElement(By.xpath(prop.getProperty("closeProgram1")));
			if(closeBt.isDisplayed())
			{
				closeBt.click();
				Thread.sleep(1000);
			}
			
			ArrayList<String> list1=new ArrayList<String>();
			String program1;
			String verifyUrl;
			String closeMsg;
			
			list1.add(prop.getProperty("programUrl2"));
			list1.add(prop.getProperty("programUrl3"));
			list1.add(prop.getProperty("programUrl4"));
			list1.add(prop.getProperty("programUrl5"));
			list1.add(prop.getProperty("programUrl6"));
			list1.add(prop.getProperty("programUrl7"));
			list1.add(prop.getProperty("programUrl8"));
			list1.add(prop.getProperty("programUrl9"));
			/*list1.add(prop.getProperty("programUrl10"));
			list1.add(prop.getProperty("programUrl11"));
			list1.add(prop.getProperty("programUrl12"));*/
			int j=0;
			for(int i=3;i<=10;i++)
			{
				
				program1="/html/body/div[2]/div/div[2]/div/form/program-info/div/div["+i+"]/div[1]/ul/li[4]/input";
				verifyUrl="/html/body/div[2]/div/div[2]/div/form/program-info/div/div["+i+"]/div[1]/ul/li[4]/div[1]/span[1]";
				
				System.out.println("The program is" +program1);
				WebElement addProgram=driver1.findElement(By.xpath(prop.getProperty("addPrgPlus")));
				addProgram.click();
				Thread.sleep(1000);
				WebElement programText=driver1.findElement(By.xpath(program1));
				programText.click();
				programText.sendKeys(list1.get(j));
				Thread.sleep(1000);
				WebElement verifyUrl1=driver1.findElement(By.xpath(verifyUrl));
				verifyUrl1.click();
				closeMsg="/html/body/div[2]/div/div[2]/div/form/program-info/div/div["+i+"]/div[1]/div[2]/div/a";
				Thread.sleep(1000);
				WebElement close=driver1.findElement(By.xpath(closeMsg));
				if(close.isDisplayed())
				{
					Thread.sleep(1000);
					close.click();
					
				}
				
				j++;
			}
			
			if(driver1.findElement(By.xpath(prop.getProperty("urlErrorMsg"))).isDisplayed())
			{
				status="program URL is invalid";
			}
			else
			{
				WebElement createButton=driver1.findElement(By.xpath(prop.getProperty("createBt1")));
				createButton.click();
				searchStreamer();
				
				driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
				Thread.sleep(1000);
				updateProgramError();
				String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();
				if(streamerNameTxt.equalsIgnoreCase(streamerName1))
				{

					status="Streamer has been created successfully";
				}}}
		return status;
	}

	public String deployStreamer() throws InterruptedException
	{
		
		Thread.sleep(1000);
		WebElement deployBt=driver1.findElement(By.xpath(prop.getProperty("deployBt")));
		deployBt.click();
		Thread.sleep(1000);
		WebElement confirmMsg=driver1.findElement(By.xpath(prop.getProperty("confirmMsg")));
		Thread.sleep(1000);
		if(confirmMsg.isDisplayed())
		{
			WebElement deployLink=driver1.findElement(By.xpath(prop.getProperty("deployLink")));
			deployLink.click();
			status="Streamer has been deployed successfully";
		}
		driver1.navigate().back();
		Thread.sleep(3000);
		return status;
	}

	public String checkRunningStatus() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		Thread.sleep(1000);
		WebElement runningStatus=driver1.findElement(By.xpath(prop.getProperty("runningStatus")));
		if(runningStatus.getText().equals("Running"))
		{
			status="Streamer status is Running";
		}
		driver1.navigate().back();
		if(duplicate==true)
		{
			driver1.navigate().back();
		}
		return status;
	}

	public String deployStreamerHome() throws InterruptedException
	{
		driver1.navigate().back();
		Thread.sleep(3000);
		WebElement deployBthome=driver1.findElement(By.xpath(prop.getProperty("deployHomeBt1")));
		Actions actions2=new Actions(driver1);
		actions2.moveToElement(deployBthome).click().build().perform();
		WebElement confirmMsg=driver1.findElement(By.xpath(prop.getProperty("confirmMsg1")));
		if(confirmMsg.isDisplayed())
		{
			WebElement deploy=driver1.findElement(By.xpath(prop.getProperty("deployBt1")));
			deploy.click();
			status="Streamer has been deployed successfully";
			driver1.navigate().back();
			Thread.sleep(3000);
			driver1.navigate().forward();
			Thread.sleep(3000);
		}
		return status;
	}

	public String deleteStreamer() throws InterruptedException
	{	
		WebElement deleteBt=driver1.findElement(By.xpath(prop.getProperty("deleteBt")));
		deleteBt.click();
		WebElement deleteMsg=driver1.findElement(By.xpath(prop.getProperty("deleteMsg")));
		Thread.sleep(1000);
		if(deleteMsg.isDisplayed())
		{
			WebElement confirmDel=driver1.findElement(By.xpath(prop.getProperty("confirmDel")));
			confirmDel.click();
			status="Streamer has been deleted successfully";
			WebElement retunHome1=driver1.findElement(By.xpath(prop.getProperty("returnHome")));
			retunHome1.click();}
		return status;
	}

	public String addStream() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();
		if(streamerNameTxt.equalsIgnoreCase(streamerName1))
		{
			WebElement addStreamBt=driver1.findElement(By.xpath(prop.getProperty("addStreamBt")));
			addStreamBt.click();
			Thread.sleep(3000);
			if(streamName==true)
			{
				WebElement streamNamebox=driver1.findElement(By.xpath(prop.getProperty("streamNameBox")));
				streamNamebox.click();
				streamNamebox.sendKeys("stream1");
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="StreamName has been configured Successfully";
				}}
			else if(streamPid==true)
			{
				WebElement streamPidBox=driver1.findElement(By.xpath(prop.getProperty("streamPidBox")));
				streamPidBox.click();
				streamPidBox.clear();
				streamPidBox.click();
				streamPidBox.sendKeys(Integer.toString(streamPidValue));
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="StreamPid has been configured Successfully";
				}}
			else if(streamIndex==true)
			{
				Thread.sleep(1000);
				WebElement streamBitbox=driver1.findElement(By.xpath(prop.getProperty("streamBitBox")));
				streamBitbox.clear();
				streamBitbox.click();
				streamBitbox.sendKeys(Integer.toString(streamIndexValue));
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="StreamIndex has been configured Successfully";
				}}
			else if(streamEnable==true)
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("streamEnable")));
				enable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Stream Enable has been configured Successfully";
				}}
			else if(streamUrl==true)
			{
				WebElement streamUrlBox=driver1.findElement(By.xpath(prop.getProperty("streamUrlBox")));
				Thread.sleep(1000);
				streamUrlBox.clear();
				streamUrlBox.click();
				streamUrlBox.sendKeys(streamUrlValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="StreamUrl has been configured Successfully";
				}}
			else if(invalidKey==true)
			{
				WebElement streamUrlBox=driver1.findElement(By.xpath(prop.getProperty("streamUrlBox")));
				streamUrlBox.click();
				Thread.sleep(1000);
				streamUrlBox.clear();
				streamUrlBox.click();
				streamUrlBox.sendKeys(streamUrlValue);
				Thread.sleep(1000);
				WebElement invalidError=driver1.findElement(By.xpath(prop.getProperty("invalidErrorMsg")));
				if(invalidError.isDisplayed())
				{
					status=invalidError.getText();
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					if(updateMsg.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdate")));
						cancelUpdate.click();
						
						Thread.sleep(3000);
					}
					driver1.navigate().back();
					}
			}
			else if(streamCount==true)
			{
				for(int i=1; i<16;i++)
				{
					WebElement addStreamBt1=driver1.findElement(By.xpath(prop.getProperty("addStreamBt")));
					addStreamBt1.click();
					Thread.sleep(3000);
				}
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					updateBt.click();
					status="16 Stream Object has been added Successfully";
				}
			}
			else
			{
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					updateBt.click();
					status="Stream Object has been added Successfully";
				}}}
		return status;
	}

	public String upgradeStreamer() throws InterruptedException
	{
		WebElement upgrade=driver1.findElement(By.xpath(prop.getProperty("upgradeDropDown")));
		upgrade.click();
		Select select=new Select(upgrade);
		select.selectByIndex(1);
		Thread.sleep(1000);
		WebElement upgradeStreamerName=driver1.findElement(By.xpath(prop.getProperty("upgradeStreamerName")));
		upgradeStreamerName.click();
		upgradeStreamerName.sendKeys(prop.getProperty("upgradeName"));
		Thread.sleep(1000);
		WebElement upgradeButton=driver1.findElement(By.xpath(prop.getProperty("upgradeButton")));
		upgradeButton.click();
		WebElement upgradeMsg=driver1.findElement(By.xpath("upgradeMsg"));
		if(upgradeMsg.isDisplayed())
		{
			status="The streamer has been upgraded";
		}
		return status;
	}

	public String programUrlSelection() throws InterruptedException
	{
		Thread.sleep(1000);
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		Thread.sleep(1000);
		//((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		if(invalidCheck==true)
		{
			WebElement selectCheck1=driver1.findElement(By.xpath(prop.getProperty("selectAll")));
			selectCheck1.click();
			selectCheck1.click();
		}
		else
		{
			/*WebElement addProgram=driver1.findElement(By.xpath(prop.getProperty("addProgram")));
			addProgram.click();
			Thread.sleep(1000);
			WebElement addProgramText=driver1.findElement(By.xpath(prop.getProperty("addProgramText")));
			addProgramText.click();
			addProgramText.sendKeys(prop.getProperty("programUrl1"));
			WebElement verifyBt=driver1.findElement(By.xpath(prop.getProperty("addVerifyBt")));
			verifyBt.click();
			Thread.sleep(1000);*/
			WebElement selectCheck=driver1.findElement(By.xpath(prop.getProperty("selectAll")));
			selectCheck.click();
		}
		Thread.sleep(1000);
		//WebElement programCheck1=driver1.findElement(By.xpath(prop.getProperty("programCheck1")));
		//WebElement programCheck2=driver1.findElement(By.xpath(prop.getProperty("programCheck2")));
		String programCheck;
		int checkStatus=0;
		for(int i=2;i<=4;i++)
		{
			programCheck="/html/body/div[2]/div/div[2]/div/program-info/div/div["+i+"]/div[1]/ul/li[1]/input";
			WebElement programCheck1=driver1.findElement(By.xpath(programCheck));
			if(programCheck1.isSelected())
			{
				checkStatus++;
			}
		}
		if(checkStatus==3)
		{
			status="The Programs Listed are all enabled";
		}
		else
		{
			status="The Programs Listed are all disabled";
		}
		WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
		Thread.sleep(1000);
		if(updateMsg.isDisplayed())
		{
			WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
			Thread.sleep(1000);
			updateBt.click();
		}
		driver1.navigate().back();

		return status;
	}

	public String configureConfigObject() throws InterruptedException 
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();
		if(streamerNameTxt.equalsIgnoreCase(streamerName1))
		{
			if(configObject.equals("networkLatency"))
			{
				WebElement networkLatencyBox=driver1.findElement(By.xpath(prop.getProperty("networkLatencyBox")));
				networkLatencyBox.click();
				networkLatencyBox.clear();
				Thread.sleep(1000);
				networkLatencyBox.click();
				networkLatencyBox.sendKeys(configValue);
				Thread.sleep(2000);
				if(invalidCheck==true)
				{
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					Thread.sleep(1000);
					if(updateMsg.isDisplayed())
					{
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						Thread.sleep(1000);
						updateBt.click();
						WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("validationErrorMsg")));
						Thread.sleep(1000);
						if(validationError.isDisplayed())
						{
							WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
							cancelUpdate.click();
							driver1.navigate().back();
							status="VALIDATION ERROR: NETWORKLATENCY VALUE IS OUTSIDE THE ACCEPTABLE RANGE OF PT0S TO PT5S";
						}}}
				else
				{
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					if(updateMsg.isDisplayed())
					{
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						updateBt.click();
						status="Network latency has been configured successfully";
					}}
			}
			else if(configObject.equals("scte30Server"))
			{
				if(childConfig.equals("tcpPort")){
					WebElement scte30TcpPortBox=driver1.findElement(By.xpath(prop.getProperty("scte30TcpPortBox")));
					scte30TcpPortBox.click();
					scte30TcpPortBox.clear();
					Thread.sleep(1000);
					scte30TcpPortBox.click();
					scte30TcpPortBox.sendKeys(configValue);
					Thread.sleep(2000);
					if(invalidCheck==true)
					{
						WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
						Thread.sleep(1000);
						if(updateMsg.isDisplayed())
						{
							WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
							Thread.sleep(1000);
							updateBt.click();

							WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("scte30TcpPortError")));
							Thread.sleep(1000);
							if(validationError.isDisplayed())
							{
								/*WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
								cancelUpdate.click();*/
								driver1.navigate().back();
								status="VALIDATION ERROR: OBJECT PROPERTY 'CONFIG' VALIDATION FAILED: OBJECT PROPERTY 'SCTE30SERVER' VALIDATION FAILED: OBJECT PROPERTY 'TCPPORT' VALIDATION FAILED: NUMERIC VALUE IS GREATER THAN MAXIMUM";
							}}}
					else
					{
						WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
						if(updateMsg.isDisplayed())
						{
							WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
							updateBt.click();
							status="scte30Server tcpPort has been configured successfully";
						}}}
				else
				{
					WebElement domainBox=driver1.findElement(By.xpath(prop.getProperty("domainBox")));
					domainBox.click();
					domainBox.clear();
					Thread.sleep(1000);
					domainBox.click();
					domainBox.sendKeys(configValue);
					Thread.sleep(2000);
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					Thread.sleep(1000);
					if(updateMsg.isDisplayed())
					{
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						Thread.sleep(1000);
						updateBt.click();
						status="scte30Server domain has been configured successfully";
						driver1.navigate().back();
					}}		
			}
			else if(configObject.equals("segmentDuration"))
			{
				WebElement segmentDurationBox=driver1.findElement(By.xpath(prop.getProperty("segmentDurationBox")));
				segmentDurationBox.click();
				Thread.sleep(10000);
				segmentDurationBox.clear();
				Thread.sleep(10000);
				segmentDurationBox.click();
				Thread.sleep(1000);
				segmentDurationBox.sendKeys(configValue);
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(invalidCheck==true)
				{
					Thread.sleep(1000);
					if(updateMsg.isDisplayed())
					{
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						Thread.sleep(1000);
						updateBt.click();
						WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("validationErrorMsg")));
						Thread.sleep(1000);
						if(validationError.isDisplayed())
						{
							WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
							cancelUpdate.click();
							driver1.navigate().back();
							status="VALIDATION ERROR: SEGMENTDURATION VALUE IS OUTSIDE THE ACCEPTABLE RANGE OF PT1S TO PT10S";
						}}}
				else
				{
					if(updateMsg.isDisplayed())
					{
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						updateBt.click();
						status="Segment Duration has been configured successfully";
					}}}	

			else if(configObject.equals("fanoutProxy"))
			{
				WebElement fanOutProxyBox=driver1.findElement(By.xpath(prop.getProperty("fanOutProxyBox")));
				fanOutProxyBox.click();
				Thread.sleep(1000);
				fanOutProxyBox.sendKeys(configValue);
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					updateBt.click();
					status="FanoutProxy has been configured successfully";
				}}}
		return status;
	}

	public String deleteStream() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		String streamerNameTxt=driver1.findElement(By.xpath(prop.getProperty("streamerNameTxt"))).getText();
		if(streamerNameTxt.equalsIgnoreCase(streamerName1))
		{
			WebElement deleteBt=driver1.findElement(By.xpath(prop.getProperty("deleteStreamBt")));
			deleteBt.click();
			WebElement deleteMsg=driver1.findElement(By.xpath(prop.getProperty("deleteStreamMsg")));
			if(deleteMsg.isDisplayed())
			{
				WebElement confirmBt=driver1.findElement(By.xpath(prop.getProperty("confirmDelStream")));
				confirmBt.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Stream Object has been deleted Successfully";
				}}}
		return status;
	}

	public String inputConfiguration() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement programEdit=driver1.findElement(By.xpath(prop.getProperty("programEdit")));
		programEdit.click();
		Thread.sleep(2000);
		if(inputObject.equals("cspEnable"))
		{
			if(inputObjectValue.equals("true"))
			{
				WebElement cspEnableCheck=driver1.findElement(By.xpath(prop.getProperty("cspEnableCheck")));
				cspEnableCheck.click();
				Thread.sleep(1000);
				WebElement cspAlert=driver1.findElement(By.xpath(prop.getProperty("cspErrorMsg")));
				Thread.sleep(1000);
				if(alertCheck==true && cspAlert.isDisplayed())
				{
					String alertMsg=cspAlert.getText();
					if(alertMsg.equalsIgnoreCase("CSP: PROGRAM NAME MUST MATCH MANIFEST"));
					status="alert message is displayed";
					Thread.sleep(1000);
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					driver1.navigate().back();
				}
				else
				{
					Thread.sleep(1000);
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					Thread.sleep(1000);
					if(updateMsg.isDisplayed())
					{
						Thread.sleep(1000);
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						Thread.sleep(1000);
						updateBt.click();
						status="Input csp object has been enabled successfully";
						driver1.navigate().back();
					}}
			}
			else
			{
				WebElement cspEnableCheck=driver1.findElement(By.xpath(prop.getProperty("cspEnableCheck")));
				cspEnableCheck.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Input csp object has been disabled successfully";
					driver1.navigate().back();
				}}
		}
		return status;
	}

	public String configureEbif() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement programEdit=driver1.findElement(By.xpath(prop.getProperty("programEdit")));
		programEdit.click();
		Thread.sleep(2000);
		if(ebifObject.equals("timingMode"))
		{
			WebElement timingMode=driver1.findElement(By.xpath(prop.getProperty("timingModeSelect")));
			Select  select=new Select(timingMode);
			select.selectByVisibleText(ebifObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif timing mode has been configured Successfully";
				driver1.navigate().back();
			}
		}
		else if(ebifObject.equals("srcUrl"))
		{
			WebElement srcUrl=driver1.findElement(By.xpath(prop.getProperty("srcUrlBox")));
			srcUrl.click();
			srcUrl.clear();
			srcUrl.click();
			srcUrl.sendKeys(ebifObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif srcUrl has been configured Successfully";
				driver1.navigate().back();
			}
		}
		else if(ebifObject.equals("programNumber"))
		{
			WebElement programNumber=driver1.findElement(By.xpath(prop.getProperty("programNumberBox1")));
			programNumber.click();
			programNumber.clear();
			Thread.sleep(1000);
			programNumber.click();
			Thread.sleep(1000);
			programNumber.sendKeys(ebifObjectValue);
			Thread.sleep(1000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif programNumber has been configured Successfully";
				driver1.navigate().back();
			}
		}
		else if(ebifObject.equals("enable"))
		{
			if(ebifObjectValue.equals("true"))
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("enableCheck")));
				enable.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Ebif object has been enabled successfully";
					driver1.navigate().back();
				}
			}
			else
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("enableCheck")));
				enable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Ebif object has been disabled successfully";
					driver1.navigate().back();
				}
			}
		}
		else if(ebifObject.equals("dpiPid"))
		{
			WebElement dpiPid=driver1.findElement(By.xpath(prop.getProperty("dpiPidBox")));
			dpiPid.click();
			dpiPid.clear();
			dpiPid.click();
			dpiPid.sendKeys(ebifObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif dpiPid has been configured Successfully";
				if(Integer.parseInt(ebifObjectValue)>8190 || Integer.parseInt(ebifObjectValue)<1)
				{
					//WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("validationErrorMsg")));
					Thread.sleep(1000);
					WebElement dpiPid1=driver1.findElement(By.xpath(prop.getProperty("dpiPidBox")));
					dpiPid1.click();
					/*if(validationError.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();*/
					if(dpiPid1.getText().isEmpty())
						status="Ebif dpiPid value is not between 1 to 8190";
				}
				driver1.navigate().back();
			}
		}
		else if(ebifObject.equals("delay"))
		{
			WebElement delayBox=driver1.findElement(By.xpath(prop.getProperty("delayBox")));
			delayBox.click();
			delayBox.clear();
			delayBox.click();
			delayBox.sendKeys(ebifObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif delay has been configured Successfully";
				if(invalidCheck==true)
				{/*WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("delayErrorMsg")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();*/
					//((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
					Thread.sleep(1000);
					WebElement delayBox1=driver1.findElement(By.xpath(prop.getProperty("delayBox")));
					Thread.sleep(1000);
					delayBox1.click();
					if(delayBox1.getText().isEmpty())
						status="Ebif delay value is not between 0 to 20";	
				}
				driver1.navigate().back();
			}
		}

		else if(ebifObject.equals("bandwidthConfigName"))
		{
			WebElement bandwidthSelect=driver1.findElement(By.xpath(prop.getProperty("bandwidthSelect")));
			Select  select=new Select(bandwidthSelect);
			select.selectByVisibleText(ebifObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ebif bandwidthConfigName has been configured Successfully";
				driver1.navigate().back();
			}
		}
		return status;
	}

	public String manifestUrlCheck() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		Thread.sleep(1000);
		WebElement program=driver1.findElement(By.xpath(prop.getProperty("additionalPrg")));
		program.click();
		Thread.sleep(1000);
		((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		WebElement programUrl=driver1.findElement(By.xpath(prop.getProperty("programUrlBox1")));
		programUrl.click();
		programUrl.sendKeys(manifestUrl);
		Thread.sleep(1000);
		WebElement verifyBt=driver1.findElement(By.xpath(prop.getProperty("verifyUrl1")));
		Thread.sleep(1000);
		verifyBt.click();
		Thread.sleep(2000);
		if(driver1.findElement(By.xpath(prop.getProperty("urlError"))).isDisplayed())
		{
			status="program URL is invalid";
		}
		WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
		Thread.sleep(1000);
		if(updateMsg.isDisplayed())
		{
			Thread.sleep(1000);
			WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
			cancelUpdate.click();
			Thread.sleep(1000);
		}
		driver1.navigate().back();
		return status;
	}

	public String programConfiguration() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement programEdit=driver1.findElement(By.xpath(prop.getProperty("programEdit")));
		programEdit.click();
		if(programObject.equals("programName"))
		{
			WebElement programNameBox=driver1.findElement(By.xpath(prop.getProperty("programNameBox")));
			programNameBox.click();
			programNameBox.clear();
			Thread.sleep(2000);
			programNameBox.click();
			Thread.sleep(2000);
			programNameBox.sendKeys(programObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="output programName has been configured Successfully";
				driver1.navigate().back();
			}
		}

		else if(programObject.equals("videoDelay"))
		{
			if(invalidCheck==true)
			{
				WebElement videoDelayBox=driver1.findElement(By.xpath(prop.getProperty("videoDelayBox")));
				videoDelayBox.click();
				videoDelayBox.clear();
				Thread.sleep(1000);
				videoDelayBox.sendKeys(programObjectValue);
				Thread.sleep(2000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					/*WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();*/
					WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("videoDelayError")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						/*WebElement VideoDelayErrorClose=driver1.findElement(By.xpath(prop.getProperty("VideoDelayErrorClose")));
						VideoDelayErrorClose.click();*/
						//Thread.sleep(1000);
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();
						status="VideoDelay is not between 0 to 3";
					}
				}
				driver1.navigate().back();
			}
			else
			{
				WebElement videoDelayBox=driver1.findElement(By.xpath(prop.getProperty("videoDelayBox")));
				videoDelayBox.click();
				videoDelayBox.clear();
				Thread.sleep(1000);
				videoDelayBox.sendKeys(programObjectValue);
				Thread.sleep(2000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="VideoDelay has been configured successfully";
					driver1.navigate().back();
				}}}

		else if(programObject.equals("videoRepresentationId"))
		{
			((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement videoRepresentationId=driver1.findElement(By.xpath(prop.getProperty("videoRepresentationId")));
			if(videoRepresentationId.isDisplayed())
			{
				WebElement representationId=driver1.findElement(By.xpath(prop.getProperty("representationId")));
				if(representationId.isDisplayed())
				{
					
					status="Video RespresentationId is present";
				}
			}
			driver1.navigate().back();
		}

		else if(programObject.equals("audioRepresentationId"))
		{
			WebElement audioRepresentationId=driver1.findElement(By.xpath(prop.getProperty("audioRepresentationId")));
			if(audioRepresentationId.isDisplayed())
			{
				WebElement representationId=driver1.findElement(By.xpath(prop.getProperty("representationIdAudio")));
				if(representationId.isDisplayed())
				{
					WebElement audioEnable=driver1.findElement(By.xpath(prop.getProperty("audioEnableCheck")));
					Thread.sleep(1000);
					audioEnable.click();
					Thread.sleep(1000);
					WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
					Thread.sleep(1000);
					if(updateMsg.isDisplayed())
					{
						Thread.sleep(1000);
						WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
						Thread.sleep(1000);
						updateBt.click();
					}
					status="Audio RespresentationId is present";
				}
			}
			driver1.navigate().back();
		}

		else if(programObject.equals("mpdLatency"))
		{
			if(invalidCheck==true)
			{
				WebElement mpdLatencyBox=driver1.findElement(By.xpath(prop.getProperty("mpdLatencyBox")));
				mpdLatencyBox.click();
				mpdLatencyBox.clear();
				Thread.sleep(2000);
				mpdLatencyBox.click();
				Thread.sleep(2000);
				mpdLatencyBox.sendKeys(programObjectValue);
				Thread.sleep(2000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					Thread.sleep(1000);
					WebElement mpdLatencyError=driver1.findElement(By.xpath(prop.getProperty("mpdLatencyError")));
					if(mpdLatencyError.isDisplayed())
					{
						WebElement mpdLatencyClose=driver1.findElement(By.xpath(prop.getProperty("mpdLatencyClose")));
						mpdLatencyClose.click();
						Thread.sleep(1000);
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						Thread.sleep(1000);
						cancelUpdate.click();
						status="mpdLatency is not between 0 to 20";
						
					}
					driver1.navigate().back();
				}
				
			}
			else
			{
				WebElement mpdLatencyBox=driver1.findElement(By.xpath(prop.getProperty("mpdLatencyBox")));
				mpdLatencyBox.click();
				Thread.sleep(1000);
				mpdLatencyBox.clear();
				Thread.sleep(1000);
				mpdLatencyBox.click();
				mpdLatencyBox.sendKeys(programObjectValue);
				Thread.sleep(2000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="MpdLatency has been configured successfully";
					driver1.navigate().back();
				}}}

		else if(programObject.equals("programNumber"))
		{
			WebElement programNumberBox=driver1.findElement(By.xpath(prop.getProperty("programNumberBox")));
			programNumberBox.click();
			programNumberBox.clear();
			Thread.sleep(2000);
			programNumberBox.click();
			Thread.sleep(2000);
			programNumberBox.sendKeys(programObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="output programNumber has been configured Successfully";
				if(Integer.parseInt(programObjectValue)>65535 || Integer.parseInt(programObjectValue)<0)
				{
					WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("programNumberError")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();
						status="output programNumber is not between 1 to 65535";
					}
				}
				driver1.navigate().back();
			}}
		else if(programObject.equals("bufferDelay"))
		{
			
			if(invalidCheck==true)
			{
				WebElement audioDelayBox=driver1.findElement(By.xpath(prop.getProperty("audioDelayBox")));
				audioDelayBox.click();
				audioDelayBox.clear();
				Thread.sleep(2000);
				audioDelayBox.click();
				Thread.sleep(2000);
				audioDelayBox.sendKeys(programObjectValue);
				Thread.sleep(1000);
				/*WebElement audioEnable=driver1.findElement(By.xpath(prop.getProperty("audioEnableCheck")));
				Thread.sleep(1000);
				audioEnable.click();
				Thread.sleep(1000);*/
			/*	WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
				}*/
				WebElement updateMsg1=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg1.isDisplayed())
				{

					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					updateBt.click();
					Thread.sleep(1000);
					WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("audioDelayError")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						
						WebElement audioErrorClose=driver1.findElement(By.xpath(prop.getProperty("audioDelayClose")));
						audioErrorClose.click();
						Thread.sleep(1000);	
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();
						Thread.sleep(1000);
						status="audioDelay is not between 0 to 1";
						
					}
					driver1.navigate().back();}
				
			}
			else
			{
				WebElement audioDelayBox=driver1.findElement(By.xpath(prop.getProperty("audioDelayBox")));
				audioDelayBox.click();
				audioDelayBox.clear();
				Thread.sleep(2000);
				audioDelayBox.click();
				Thread.sleep(2000);
				audioDelayBox.sendKeys(programObjectValue);
				Thread.sleep(2000);
				WebElement audioEnable=driver1.findElement(By.xpath(prop.getProperty("audioEnableCheck")));
				Thread.sleep(1000);
				audioEnable.click();
				Thread.sleep(1000);
				WebElement updateMsg1=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg1.isDisplayed())
				{
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					Thread.sleep(1000);
					status="audioDelay has been configured Successfully";
				}
				driver1.navigate().back();
			}
		}
		else if(programObject.equals("decryptionKey"))
		{
			WebElement decryptionKeyBox=driver1.findElement(By.xpath(prop.getProperty("decryptionKeyBox")));
			decryptionKeyBox.click();
			Thread.sleep(2000);
			decryptionKeyBox.sendKeys(programObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="decryption key has been configured successfully";
				driver1.navigate().back();
			}
		}
		else if(programObject.equals("enable"))
		{
			if(programObjectValue.equals("true"))
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("decryptionEnableCheck")));
				enable.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="decryption object has been enabled successfully";
					driver1.navigate().back();
				}
			}
			else
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("decryptionEnableCheck")));
				enable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="decryption object has been disabled successfully";
					driver1.navigate().back();
				}
			}
		}
		return status;
	}

	public String adsConfiguration() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement programEdit=driver1.findElement(By.xpath(prop.getProperty("programEdit")));
		programEdit.click();
		Thread.sleep(2000);
		if(adsObject.equalsIgnoreCase("udpPort"))
		{
			WebElement udpPortBox=driver1.findElement(By.xpath(prop.getProperty("udpPortBox")));
			udpPortBox.click();
			udpPortBox.clear();
			Thread.sleep(2000);
			udpPortBox.click();
			Thread.sleep(2000);
			udpPortBox.sendKeys(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads udp Port has been configured Successfully";
				if(Integer.parseInt(adsObjectValue)>65535 || Integer.parseInt(adsObjectValue)<0)
				{
					WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("validationErrorMsg")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();
						status="ads udp Port value is not between 1 to 65535";
					}
				}
				driver1.navigate().back();
			}
		}
		else if(adsObject.equalsIgnoreCase("psnUrl"))
		{
			WebElement psnUrlBox=driver1.findElement(By.xpath(prop.getProperty("psnUrlBox")));
			psnUrlBox.click();
			psnUrlBox.clear();
			Thread.sleep(2000);
			psnUrlBox.click();
			Thread.sleep(2000);
			psnUrlBox.sendKeys(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads psnUrl has been configured Successfully";
			}
			driver1.navigate().back();
		}
		else if(adsObject.equals("enable"))
		{
			if(adsObjectValue.equals("true"))
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("adsEnable")));
				enable.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Ads object has been enabled successfully";
					driver1.navigate().back();
				}
			}
			else
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("adsEnable")));
				enable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="Ads object has been disabled successfully";
					driver1.navigate().back();
				}
			}
		}
		else if(adsObject.equalsIgnoreCase("channelName"))
		{
			WebElement channelNameBox=driver1.findElement(By.xpath(prop.getProperty("channelNameBox")));
			channelNameBox.click();
			channelNameBox.clear();
			Thread.sleep(2000);
			channelNameBox.click();
			Thread.sleep(2000);
			channelNameBox.sendKeys(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads Channel Name has been configured Successfully";
			}
			driver1.navigate().back();
		}
		else if(adsObject.equalsIgnoreCase("adzone"))
		{
			WebElement adzoneBox=driver1.findElement(By.xpath(prop.getProperty("adzoneBox")));
			adzoneBox.click();
			adzoneBox.clear();
			Thread.sleep(2000);
			adzoneBox.click();
			Thread.sleep(2000);
			adzoneBox.sendKeys(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads Adzone has been configured Successfully";
			}
			driver1.navigate().back();
		}
		else if(adsObject.equalsIgnoreCase("acrUrl"))
		{
			WebElement acrUrlBox=driver1.findElement(By.xpath(prop.getProperty("acrUrlBox")));
			acrUrlBox.click();
			acrUrlBox.clear();
			Thread.sleep(2000);
			acrUrlBox.click();
			Thread.sleep(2000);
			acrUrlBox.sendKeys(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads acrUrl has been configured Successfully";
			}
			driver1.navigate().back();
		}
		else if(adsObject.equals("adBreakWindow"))
		{
			WebElement adBreakWindowBox=driver1.findElement(By.xpath(prop.getProperty("adBreakWindowBox")));
			adBreakWindowBox.click();
			adBreakWindowBox.clear();
			Thread.sleep(1000);
			adBreakWindowBox.click();
			Thread.sleep(1000);
			adBreakWindowBox.sendKeys(adsObjectValue);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="ads adBreakWindow has been configured Successfully";
				if(invalidCheck==true)
				{
					WebElement validationError=driver1.findElement(By.xpath(prop.getProperty("validationErrorMsg")));
					Thread.sleep(1000);
					if(validationError.isDisplayed())
					{
						WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
						cancelUpdate.click();
						Thread.sleep(1000);
						status="ads adBreakWindow value is not between 0 to 20 minutes";
					}
				}
				driver1.navigate().back();
			}
		}
		else if(adsObject.equals("adType"))
		{
			WebElement adTypeSelect=driver1.findElement(By.xpath(prop.getProperty("adTypeSelect")));
			Select  select=new Select(adTypeSelect);
			select.selectByVisibleText(adsObjectValue);
			Thread.sleep(2000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="Ads adType has been configured Successfully";
			}
			driver1.navigate().back();
		}
		return status;
	}

	public String outputConfiguration() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		Thread.sleep(2000);
		if(outputObject.equals("sdtEnable"))
		{
			if(outputObjectValue.equals("true"))
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("sdtEnableCheck")));
				enable.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="SdtOutput has been enabled successfully";
					driver1.navigate().back();
				}
			}
			else
			{
				WebElement enable=driver1.findElement(By.xpath(prop.getProperty("sdtEnableCheck")));
				enable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="SdtOutput has been disabled successfully";
				}
				driver1.navigate().back();
			}
		}

		else if(outputObject.equalsIgnoreCase("destAddress"))
		{
			WebElement destAddressBox=driver1.findElement(By.xpath(prop.getProperty("destAddressBox")));
			destAddressBox.clear();
			Thread.sleep(1000);
			destAddressBox.click();
			destAddressBox.sendKeys(outputObjectValue);
			Thread.sleep(1000);
			WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
			Thread.sleep(1000);
			if(updateMsg.isDisplayed())
			{
				Thread.sleep(1000);
				WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
				Thread.sleep(1000);
				updateBt.click();
				status="destAddress has been configured successfully";
			}
			driver1.navigate().back();
		}

		else if(outputObject.equalsIgnoreCase("udpDstPort"))
		{
			if(Integer.parseInt(outputObjectValue)>65535 || Integer.parseInt(outputObjectValue)<1024)
			{
				WebElement udpDstPortBox=driver1.findElement(By.xpath(prop.getProperty("udpDstPortBox")));
				udpDstPortBox.clear();
				Thread.sleep(1000);
				udpDstPortBox.click();
				udpDstPortBox.sendKeys(outputObjectValue);
				Thread.sleep(1000);
				WebElement udpError=driver1.findElement(By.xpath(prop.getProperty("udpError")));
				status=udpError.getText().toString().toLowerCase();
				Thread.sleep(1000);
				/*WebElement close=driver1.findElement(By.xpath(prop.getProperty("closeErrorMsg")));
				close.click();*/
				WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
				cancelUpdate.click();
				driver1.navigate().back();
			}
			else
			{
				WebElement udpDstPortBox=driver1.findElement(By.xpath(prop.getProperty("udpDstPortBox")));
				udpDstPortBox.clear();
				Thread.sleep(1000);
				udpDstPortBox.click();
				udpDstPortBox.sendKeys(outputObjectValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="udpDstPort has been configured successfully";
				}
				driver1.navigate().back();
			}
		}

		else if(outputObject.equalsIgnoreCase("udpSrcPort"))
		{
			if(Integer.parseInt(outputObjectValue)>65535 || Integer.parseInt(outputObjectValue)<0)
			{
				WebElement udpSrcPortBox=driver1.findElement(By.xpath(prop.getProperty("udpSrcPortBox")));
				udpSrcPortBox.clear();
				Thread.sleep(1000);
				udpSrcPortBox.click();
				udpSrcPortBox.sendKeys(outputObjectValue);
				Thread.sleep(1000);
				WebElement udpSrcError=driver1.findElement(By.xpath(prop.getProperty("udpSrcError")));
				status=udpSrcError.getText().toString().toLowerCase();
				Thread.sleep(1000);
				/*WebElement close=driver1.findElement(By.xpath(prop.getProperty("closeErrorMsg")));
				close.click();*/
				WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
				cancelUpdate.click();
				driver1.navigate().back();
			}
			else
			{
				WebElement udpSrcPortBox=driver1.findElement(By.xpath(prop.getProperty("udpSrcPortBox")));
				udpSrcPortBox.clear();
				Thread.sleep(1000);
				udpSrcPortBox.click();
				udpSrcPortBox.sendKeys(outputObjectValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="udpSrcPort has been configured successfully";
				}
				driver1.navigate().back();
			}
		}

		else if(outputObject.equalsIgnoreCase("tsid"))
		{
			if(Integer.parseInt(outputObjectValue)>65535 || Integer.parseInt(outputObjectValue)<0)
			{
				WebElement tsidBox=driver1.findElement(By.xpath(prop.getProperty("tsidBox")));
				tsidBox.clear();
				Thread.sleep(1000);
				tsidBox.click();
				tsidBox.sendKeys(outputObjectValue);
				Thread.sleep(1000);
				WebElement tsidError=driver1.findElement(By.xpath(prop.getProperty("tsidError")));
				status=tsidError.getText().toString().toLowerCase();
				Thread.sleep(1000);
				/*WebElement close=driver1.findElement(By.xpath(prop.getProperty("closeErrorMsg")));
				close.click();*/
				WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
				cancelUpdate.click();
				driver1.navigate().back();
			}
			else
			{
				WebElement tsidBox=driver1.findElement(By.xpath(prop.getProperty("tsidBox")));
				tsidBox.clear();
				Thread.sleep(1000);
				tsidBox.click();
				tsidBox.sendKeys(outputObjectValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="tsid has been configured successfully";
				}
				driver1.navigate().back();
			}
		}

		else if(outputObject.equalsIgnoreCase("tsRate"))
		{
			if(Integer.parseInt(outputObjectValue)>50000000 || Integer.parseInt(outputObjectValue)<1000000)
			{
				WebElement tsRateBox=driver1.findElement(By.xpath(prop.getProperty("tsRateBox")));
				tsRateBox.clear();
				Thread.sleep(1000);
				tsRateBox.click();
				tsRateBox.sendKeys(outputObjectValue);
				Thread.sleep(1000);
				WebElement tsRateError=driver1.findElement(By.xpath(prop.getProperty("tsRateError")));
				status=tsRateError.getText().toString().toLowerCase();
				Thread.sleep(1000);
				/*WebElement close=driver1.findElement(By.xpath(prop.getProperty("closeErrorMsg")));
				close.click();*/
				WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
				cancelUpdate.click();
				driver1.navigate().back();
			}
			else
			{
				WebElement tsRateBox=driver1.findElement(By.xpath(prop.getProperty("tsRateBox")));
				tsRateBox.clear();
				Thread.sleep(1000);
				tsRateBox.click();
				tsRateBox.sendKeys(outputObjectValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="tsRate has been configured successfully";
				}
				driver1.navigate().back();
			}
		}
		else if(outputObject.equalsIgnoreCase("timeToLive"))
		{
			if(Integer.parseInt(outputObjectValue)>255 || Integer.parseInt(outputObjectValue)<0)
			{
				WebElement timeToLiveBox=driver1.findElement(By.xpath(prop.getProperty("timeToLiveBox")));
				timeToLiveBox.clear();
				Thread.sleep(1000);
				timeToLiveBox.click();
				timeToLiveBox.sendKeys(outputObjectValue);
				Thread.sleep(1000);
				WebElement udpError=driver1.findElement(By.xpath(prop.getProperty("timeToLiveErrorMsg")));
				Thread.sleep(1000);
				status=udpError.getText().toString().toLowerCase();
				/*WebElement close=driver1.findElement(By.xpath(prop.getProperty("closeErrorMsg")));
				close.click();*/
				WebElement cancelUpdate=driver1.findElement(By.xpath(prop.getProperty("cancelUpdateButton")));
				cancelUpdate.click();
				driver1.navigate().back();
			}
			else
			{
				WebElement timeToLiveBox=driver1.findElement(By.xpath(prop.getProperty("timeToLiveBox")));
				timeToLiveBox.clear();
				Thread.sleep(1000);
				timeToLiveBox.click();
				timeToLiveBox.sendKeys(outputObjectValue);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="timeToLive has been configured successfully";
				}
				driver1.navigate().back();
			}
		}
		return status;
	}

	public String muteUnmuteConfiguration() throws InterruptedException
	{
		driver1.findElement(By.linkText(streamerName1.toUpperCase())).click();
		WebElement programEdit=driver1.findElement(By.xpath(prop.getProperty("programEdit")));
		programEdit.click();
		Thread.sleep(2000);
		if(audioObject.equalsIgnoreCase("enable"))
		{
			if(audioObjectValue.equals("true"))
			{
				WebElement audioEnable=driver1.findElement(By.xpath(prop.getProperty("audioEnableCheck")));
				Thread.sleep(1000);
				audioEnable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="audio has been unmuted successfully";
				}
				driver1.navigate().back();
			}
			else
			{
				WebElement audioEnable=driver1.findElement(By.xpath(prop.getProperty("audioEnableCheck")));
				audioEnable.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="audio has been muted successfully";
				}
				driver1.navigate().back();
			}
		}
		else if(audioObject.equals("outputEnable"))
		{
			if(audioObjectValue.equals("true"))
			{
				WebElement outputEnableCheck=driver1.findElement(By.xpath(prop.getProperty("outputEnableCheck")));
				outputEnableCheck.click();
				Thread.sleep(1000);
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="output has been unmuted successfully";
					driver1.navigate().back();
				}
			}
			else
			{
				WebElement outputEnableCheck=driver1.findElement(By.xpath(prop.getProperty("outputEnableCheck")));
				outputEnableCheck.click();
				WebElement updateMsg=driver1.findElement(By.xpath(prop.getProperty("UpdateMsg")));
				Thread.sleep(1000);
				if(updateMsg.isDisplayed())
				{
					Thread.sleep(1000);
					WebElement updateBt=driver1.findElement(By.xpath(prop.getProperty("updateBt")));
					Thread.sleep(1000);
					updateBt.click();
					status="output has been muted successfully";
					driver1.navigate().back();
				}
			}}
		return status;
	}

	public String auditLogs() throws InterruptedException
	{
		Thread.sleep(3000);
		WebElement auditLogBt=driver1.findElement(By.xpath(prop.getProperty("auditLogBt")));
		auditLogBt.click();
		Thread.sleep(1000);
		/*((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");*/
		WebElement expandBt=driver1.findElement(By.xpath(prop.getProperty("expandBt1")));
		Thread.sleep(2000);
		expandBt.click();
		WebElement logs=driver1.findElement(By.xpath(prop.getProperty("logs")));
		String logsText=logs.getText().toString();
		return logsText;
	}

	public String auditCheck() throws InterruptedException
	{

		Thread.sleep(3000);
		WebElement auditLogBt=driver1.findElement(By.xpath(prop.getProperty("auditLogBt")));
		auditLogBt.click();
		Thread.sleep(1000);
		/*((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");*/
		WebElement expandBt=driver1.findElement(By.xpath(prop.getProperty("expandBt1")));
		Thread.sleep(2000);
		expandBt.click();
		WebElement logs=driver1.findElement(By.xpath(prop.getProperty("logs")));
		if(logs.isDisplayed())
		{
			status="Audit logs for the streamer is displayed";
		}
		driver1.navigate().back();
		return status;
	}


	public String checkLogsJson() throws InterruptedException, ParseException
	{
		Thread.sleep(3000);
		WebElement auditLogBt=driver1.findElement(By.xpath(prop.getProperty("auditLogBt")));
		auditLogBt.click();
		Thread.sleep(1000);
		/*((JavascriptExecutor) driver1).executeScript("window.scrollTo(0, document.body.scrollHeight)");*/
		WebElement expandBt=driver1.findElement(By.xpath(prop.getProperty("expandBt1")));
		Thread.sleep(2000);
		expandBt.click();
		WebElement logs=driver1.findElement(By.xpath(prop.getProperty("logs")));
		String logsText=logs.getText().toString();
		JSONParser responseParser = new JSONParser();
		JSONObject streamerConfigObj = (JSONObject) responseParser.parse(logsText);
		JSONObject mux = (JSONObject)streamerConfigObj.get("mux");
		JSONObject config = (JSONObject)mux.get("config");
		String[] configPathArr=null;
		if(null!=key){
			configPathArr = key.split("/");
			if(configPathArr[0].equalsIgnoreCase("stream"))
			{
				JSONArray streamArr = (JSONArray)config.get(configPathArr[0]);
				System.out.println("Array: "+streamArr.size());
				String inputProgNum = configPathArr[1];
				char[] arrChr = inputProgNum.toCharArray();
				boolean result = Character.isDigit(arrChr[0]);
				if(result)
				{
					int num=Integer.parseInt(inputProgNum);
					if(streamArr.size()==0)
					{
						status="The updated streamer is present in logs";
						//driver1.navigate().back();
						Thread.sleep(3000);
					}
					else if(streamArr.size()==streamCountValue)
					{
						status="The updated streamer is present in logs";
						//driver1.navigate().back();
						Thread.sleep(3000);
					}
					else{
						for(int j=0;j<streamArr.size();j++)
						{	
							JSONObject streams = (JSONObject)streamArr.get(num);

							if(updateValue.equalsIgnoreCase(streams.get(updateName).toString()))
							{
								status="The updated streamer is present in logs";
								//driver1.navigate().back();
							}}}}
			}
			else if(configPathArr[0].equalsIgnoreCase("program"))
			{
				JSONArray programArr = (JSONArray)config.get(configPathArr[0]);
				String inputProgNum = configPathArr[1];
				String prgObj=configPathArr[2];
				char[] arrChr = inputProgNum.toCharArray();
				boolean result = Character.isDigit(arrChr[0]);
				if(result){
					for(int i=0;i<programArr.size();i++){
						JSONObject progObj = (JSONObject) programArr.get(i);
						long progNum = (long)progObj.get("programIndex");
						String num = String.valueOf(progNum);
						if(inputProgNum.equals(num)){
							if(prgObj.equals("null"))
							{
								String objectValue=progObj.get(updateName).toString();
								if(updateValue.equalsIgnoreCase(objectValue))
								{
									status="The updated streamer is present in logs";
								}
							}
							else if(prgObj.equals("audio"))
							{
								System.out.println("audio array :"+progObj.get(prgObj));
								JSONArray audioArr=(JSONArray)progObj.get(prgObj);
								JSONObject progObj1;
								String inputProgNum1 = configPathArr[3];
								System.out.println(configPathArr[3]);
								if(audioArr.size()==0){
									
										status="The audio muted has been updated in logs";
								
								}
								else
								{
								progObj1 =(JSONObject) audioArr.get(Integer.parseInt(inputProgNum1));
								if(updateValue.equalsIgnoreCase(progObj1.get(updateName).toString()))
								{
									status="The updated streamer is present in logs";
								}
								}

							}
							else
							{
								JSONObject ebif=(JSONObject)progObj.get(prgObj);
								if(updateValue.equalsIgnoreCase(ebif.get(updateName).toString()))
								{
									status="The updated streamer is present in logs";
								}}}}}}
		}
		else
		{
			if(parentKey.equals("config"))
			{
				if(childKey==null){
					if(config.get(updateName).toString().equals(updateValue))
					{
						status="The updated streamer is present in logs";	
					}}
				else if(childKey.equals("output"))
				{
					JSONObject ouput=(JSONObject)config.get("output");
					if(ouput.get(updateName).toString().equalsIgnoreCase(updateValue))
					{
						status="The updated streamer is present in logs";

					}
				}
				else if(childKey.equals("scte30Server"))
				{
					JSONObject scte30=(JSONObject)config.get("scte30Server");

					if(scte30.get(updateName).toString().equalsIgnoreCase(updateValue))
					{
						status="The updated streamer is present in logs";
					}
				}
			}
			else if(parentKey.equals("streamer"))
			{
				if(childKey==null){
					if(streamerConfigObj.get(updateName).toString().equalsIgnoreCase(updateValue))
					{
						status="The updated streamer is present in logs";
					}
				}
			}
		}
		driver1.navigate().back();
		return status;
	}

	public String logoutVde()
	{
		status="Failed to log out";
		WebElement logout=driver1.findElement(By.xpath(prop.getProperty("logout")));
		if(logout.isDisplayed())
		{
			logout.click();
			status="Logged out successful";
			driver1.close();
		}
		return status;		
	}

	public static void main(String[] args) throws InterruptedException, ParseException, IOException {
		// TODO Auto-generated method stub
		VDECMImplSel sel=new VDECMImplSel();
		//String streamerName1="Streamer_test";
		sel.loadProperties();
		sel.loginVDECm();
		//sel.createStreamer(streamerName1);
		sel.deployStreamer();
		//sel.deployStreamerHome();
		//sel.addStream();
		//sel.deployStreamer();
		//sel.deleteStream();
		//sel.configureStream();
		//sel.deleteStreamer(streamerName1);
		//sel.checkLogsJson();
		//System.out.println("logs :"+sel.checkLogsJson());
		//System.out.println("deploy streamer: "+sel.deployStreamer());
		//System.out.println("deletion: "+sel.deleteStreamer());
	}
}
