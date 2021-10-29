package runManager;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.codoid.products.exception.FilloException;
import lib.Global;
import lib.ExcelUtility;

public class Controller {
	static XmlInclude xmlInclude;

	public static void main(String[] args) throws ClassNotFoundException, FilloException {

		XmlSuite xmlsuite = new XmlSuite();
		xmlsuite.setName("CarTrawler Automation project");

		XmlTest xmltest = new XmlTest(xmlsuite);
		xmltest.setName("Car");

		XmlClass xmlclass;

		List<XmlSuite> listXmlSuites = new ArrayList<XmlSuite>();
		List<XmlClass> listXmlClasses = new ArrayList<XmlClass>();
		List<XmlInclude> listXmlInclude = new ArrayList<XmlInclude>();

		String strQuery = "select * from ClassName where Run = 'Y'";

		List<?> lClasses = ExcelUtility.readGroups(Global.gstrGroupControlFile, strQuery);

		for (int i = 0; i < lClasses.size(); i++) {
			String strClassName = lClasses.get(i).toString();
			xmlclass = new XmlClass();
			xmlclass.setName(strClassName);

			String strClsName = strClassName.split("\\.")[3];
			String strQueryMethod = "select * from " + strClsName + " where Run = 'Y'";
			List<?> lMethods = ExcelUtility.readTestCase(Global.gstrGroupControlFile, strQueryMethod);
			XmlInclude xmlInclude;
			for (int j = 0; j < lMethods.size(); j++) {
				String strMethodName = lMethods.get(j).toString();
				xmlInclude = new XmlInclude(strMethodName);
				listXmlInclude.add(xmlInclude);
				xmlclass.setIncludedMethods(listXmlInclude);

			}

			listXmlClasses.add(xmlclass);
			xmltest.setXmlClasses(listXmlClasses);

		}

		// ----------------------------
		TestNG testng = new TestNG();
		listXmlSuites.add(xmlsuite);
		testng.setXmlSuites(listXmlSuites);
		testng.run();

	}

}
