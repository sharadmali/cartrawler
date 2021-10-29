package lib;

import java.lang.reflect.Method;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.codoid.products.exception.FilloException;

public class Base {

	@BeforeTest(alwaysRun = true)
	public void setUpSuite(ITestContext con) throws FilloException {
		Report.createHTMLReport();
	}

	@AfterTest(alwaysRun = true)
	public void tearDownSuite() {
		Global.report.flush();
	}

	@BeforeMethod(alwaysRun = true)
	public void setUpMethod(ITestContext con, Method testMethod) throws FilloException {
		String strMethodName = testMethod.getName();
		Global.logger = Global.report.createTest(strMethodName);
		Report.writeHtmlLogs("INFO", "Test Started");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownMethod() {
		Report.writeHtmlLogs("INFO", "Test Completed");
	}

	@DataProvider(name = "get-test-data-method-cartype")
	public Object[][] getTestDataMethod_cartype(Method testMethod , ITestContext con) throws Exception {
		
		return new Object[][] {
			{"CCAN"}

			// "CBMN", "CCAN", "CCMN", "CDAN", "CDAR", "CDMD", "CDMN", "CDMR", "CWMN", "CWMR", "DDAR", "DDMR", "EBAN", "EBMN", "ECAN", "ECMN", "EDMN", "FDAR", "FDMR", "FVAR", "FVMR", "ICMR", "IDAD", "IDAN", "IDAR", "IDMD", "IDMN", "IDMR", "IFAR", "IFMD", "IFMR", "IVAN", "IVAR", "IVMN", "IVMR", "IWMN", "JFAR", "LCAN", "MCMN", "MCMR" "MDMN", "MDMR", "PDAR", "PFAR", "SCMN", "SDAR", "SDMR", "SVAN", "SVMN"
		};
	} 
	
	@DataProvider(name = "get-test-data-method-vender")
	public Object[][] getTestDataMethod_vender(Method testMethod , ITestContext con) throws Exception {
		
		return new Object[][] {
			{"24 HOUR RENT A CAR"}
			
			// "CARHIRE", "EUROPCAR", "EUROPCAR", "SIXT", "BUDGET", "SIXT", "AVIS", "PAYLESS", "KEDDY BY EUROPCAR", "FLIZZR", "FLIZZR", "24 HOUR RENT A CAR"			
		};
	} 
}
