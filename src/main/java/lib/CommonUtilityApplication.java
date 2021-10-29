package lib;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

public class CommonUtilityApplication {

	/*********************************************************************************************************************
	 * @functionName    	: getCheapestbyCarType
	 * @description     	: Return cheapest options of each car type
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static Map<String, Double> getCheapestCarByType(Object objDocument) {
		int numberOfVendors = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails.length()");

		Map<String, Double> baseMap = new HashMap<String, Double>();

		for (int i = 0; i < numberOfVendors; i++) {
			int numberOfCarType = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails.length()");
			for (int j = 0; j < numberOfCarType; j++) {
				String strCarType = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails[" + j + "].VehAvailCore.Vehicle['@Code']");
				String strTotalAmount = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails[" + j + "].VehAvailCore.TotalCharge['@RateTotalAmount']");

				double dblNewTotalAmount = Double.valueOf(strTotalAmount);

				if (baseMap.containsKey(strCarType)) {
					double dblExistingTotalAmount = baseMap.get(strCarType);
					if (dblNewTotalAmount < dblExistingTotalAmount) {
						baseMap.put(strCarType, dblNewTotalAmount);
					}
				} else {
					baseMap.put(strCarType, dblNewTotalAmount);
				}

			}
		}
		return baseMap;
	}

	/*********************************************************************************************************************
	 * @functionName    	: filterbyCarType
	 * @description     	: Allow user to filter the car details by car type
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static Map<String, List<JSONObject>> filterbyCarType(Object objDocument, String strCarType) {
		
		Map<String, List<JSONObject>> baseMap = new HashMap<String, List<JSONObject>>();
		
		List<JSONObject> dblExistingObj = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[*].VehAvails[*].VehAvailCore.Vehicle[?(@['@Code'] == '" + strCarType + "')]]");
		baseMap.put(strCarType, dblExistingObj);
		
		return baseMap;
	}
	
	/*********************************************************************************************************************
	 * @functionName    	: sortbyCorporate
	 * @description     	: Return the sorted data by corporates or venders
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static Map<String, List<Object>> sortbyCorporate(Object objDocument) {
		int numberOfVendors = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails.length()");

		Map<String, List<Object>> treeMap = new TreeMap<String, List<Object>>();
		List<Object> listOfExistingCars;
		
		for (int i = 0; i < numberOfVendors; i++) {
			String strVenderName = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].Vendor['@CompanyShortName']");
			List<Object> listOfAvailableCars = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails");

			if (treeMap.containsKey(strVenderName)) {
				listOfExistingCars = treeMap.get(strVenderName);
				listOfExistingCars.addAll(listOfAvailableCars);
				treeMap.put(strVenderName, listOfExistingCars);
			} else {
				treeMap.put(strVenderName, listOfAvailableCars);
			}
		}
		
		return treeMap;
	}
	
	/*********************************************************************************************************************
	 * @functionName    	: sortWithinGroupbyPrice
	 * @description     	: Sort low-to-high price within each group by passing vender name
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static List<JSONObject> sortWithinGroupbyPrice(Object objDocument, String strVendorName) {
		int intNumberOfVendors = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails.length()");
		List<JSONObject> listOfAvailableCars = new ArrayList<JSONObject>();
		
		for (int i = 0; i < intNumberOfVendors; i++) {

			String strCompanyShortName = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].Vendor['@CompanyShortName']");
			
			if (strCompanyShortName.contentEquals(strVendorName)) {
				listOfAvailableCars = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails");
			}
		}

		int nSize = listOfAvailableCars.size();
		for (int i = 0; i < nSize - 1; i++) {
			for (int j = 0; j < nSize - i - 1; j++) {
				
				String strTotalAmount = JsonPath.read(listOfAvailableCars.get(j), "$.VehAvailCore.TotalCharge['@RateTotalAmount']");
				double dblTotalAmount = Double.valueOf(strTotalAmount);
				String strTotalAmountNext = JsonPath.read(listOfAvailableCars.get(j + 1), "$.VehAvailCore.TotalCharge['@RateTotalAmount']");
				double dblTotalAmountNext = Double.valueOf(strTotalAmountNext);
				
				if (dblTotalAmount > dblTotalAmountNext) {
					Collections.swap(listOfAvailableCars, j, j + 1);
				}
			}
		}

		return listOfAvailableCars;
	}
	
	/*********************************************************************************************************************
	 * @functionName    	: readInputData
	 * @description     	: Read input data from Json File
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static Object readInputData() throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
		JSONObject data = (JSONObject) parser.parse(new FileReader("SampleAPICall.json"));
		String jsonString = data.toString();
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonString);
		
		return document;
	}
	
	/*********************************************************************************************************************
	 * @functionName    	: getListofAmountByCarType
	 * @description     	: Returns list of price of each Car by type
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static List<Double> getListofAmountByCarType(Object objDocument, String expectedCarType) {
		int numberOfVendors = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails.length()");

		List<Double> listOfAmount= new ArrayList<Double>();
		
		for (int i = 0; i < numberOfVendors; i++) {
			int numberOfCarType = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails.length()");
			for (int j = 0; j < numberOfCarType; j++) {
				String strCarType = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails[" + j + "].VehAvailCore.Vehicle['@Code']");
				String strTotalAmount = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[" + i + "].VehAvails[" + j + "].VehAvailCore.TotalCharge['@RateTotalAmount']");

				if(expectedCarType.contentEquals(strCarType)) {
					double dblNewTotalAmount = Double.valueOf(strTotalAmount);
					listOfAmount.add(dblNewTotalAmount);																
				}
			}
		}
		Collections.sort(listOfAmount);
		return listOfAmount;
	}
	
	/*********************************************************************************************************************
	 * @functionName    	: getListOfVenders
	 * @description     	: Return the List Of Venders
	 * @dateOfCreation		:
	 * @dateOfModification  :
	 * @author 				: Sharad Mali
	 * @return 				:
	 *********************************************************************************************************************
	 */
	
	public static List<String> getListOfVenders(Object objDocument) {
		
		List<String> listVenderName = JsonPath.read(objDocument, "$.VehAvailRSCore.VehVendorAvails[*].Vendor['@CompanyShortName']");		
		return listVenderName;
	}
	
}
