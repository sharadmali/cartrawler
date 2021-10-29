package lib;

import java.time.LocalDateTime;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Report {

	public static void createHTMLReport() {

		LocalDateTime dateTime = LocalDateTime.now();
		String dT = dateTime.toString().replaceAll(":", "");

		Global.htmlReporter = new ExtentHtmlReporter(Global.htmlFilePath + "_" + dT + ".html");
		Global.htmlReporter.config().setDocumentTitle("CarTrawler");
		Global.htmlReporter.config().setReportName("Car Details");
		Global.htmlReporter.config().setTheme(Theme.DARK);
		
		Global.report = new ExtentReports();
		Global.report.attachReporter(Global.htmlReporter);
		Global.report.setSystemInfo("Host Name", "CarTrawler");
		Global.report.setSystemInfo("Environment", "QA");
		Global.report.setSystemInfo("User Name", "Sharad");

	}

	public static void writeHtmlLogs(String strpassFail, String strDescription) {

		if (strpassFail.contentEquals("PASS")) {
			Global.logger.log(Status.PASS, strDescription);
		} else if (strpassFail.contentEquals("FAIL")) {
			Global.logger.log(Status.FAIL, strDescription);
		} else {
			Global.logger.log(Status.INFO, strDescription);
		}

	}

}
