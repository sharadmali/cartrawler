package lib;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Global {
	
	public static String gstrGroupControlFile = System.getProperty("user.dir") + "\\TestArtifacts\\GroupControlFile.xlsx";

	// Reporting variables
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static ExtentTest logger;
	
	public static String htmlFilePath = System.getProperty("user.dir") + "\\Reports\\TestResultLog\\Selenium";
	
	public static String testArtifactsPath = System.getProperty("user.dir") + "\\TestArtifacts\\";

	

}
