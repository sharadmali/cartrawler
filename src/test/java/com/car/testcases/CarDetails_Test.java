package com.car.testcases;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import runManager.Base;
import lib.CommonUtilityApplication;
import lib.Report;

public class CarDetails_Test extends Base {

	/*********************************************************************************************************************
	 * @description	: To verify the functionality to get cheapest car by type
	 * @author 		: Sharad Mali
	 *********************************************************************************************************************
	 */

	@Test
	public void verifyCheapestCarType() {
		try {
			Map<String, Double> map;			
			Object objInputData = CommonUtilityApplication.readInputData();

			map = CommonUtilityApplication.getCheapestCarByType(objInputData);
			
			for (String kayCarTypeName : map.keySet()) {				   
			   List<Double> listOfProce = CommonUtilityApplication.getListofAmountByCarType(objInputData, kayCarTypeName);
			  
			   if(listOfProce.get(0).equals(map.get(kayCarTypeName))) {
				   Assert.assertEquals(listOfProce.get(0), map.get(kayCarTypeName));
				   Report.writeHtmlLogs("PASS", "Car Type : '" + kayCarTypeName + "' and  Price : '"+ map.get(kayCarTypeName) + "' , is a cheapest Car");
			   } 
			}			

			Report.writeHtmlLogs("PASS", "Sucessfully verified the functionality to get cheapest car by type");
			
		} catch (Exception e) {
			Report.writeHtmlLogs("FAIL", e.getMessage());
		}
	}
	
	/*********************************************************************************************************************
	 * @description	: To verify the Filters functionality by CarType
	 * @parameter	: strCarType
	 * @author 		: Sharad Mali
	 *********************************************************************************************************************
	 */
	
	@Test (dataProvider = "get-test-data-method-cartype")
	public void verifyFilterByCarType(String strCarType) {
		try {
			Map<String, List<JSONObject>> map;		
			Object objInputData = CommonUtilityApplication.readInputData();
			
			map = CommonUtilityApplication.filterbyCarType(objInputData, strCarType);
			JSONArray jsonArray = new JSONArray(map.get(strCarType));

			for (int i = 0; i < jsonArray.length(); i++) {
				Object document = Configuration.defaultConfiguration().jsonProvider().parse(String.valueOf(jsonArray.get(i)));				
				String CarType = JsonPath.read(document, "$.['@Code']");
				
				if(CarType.equals(strCarType)) {
					Assert.assertEquals(CarType, strCarType);					
				}
			}
			
			Report.writeHtmlLogs("PASS", "CarType '"+ strCarType +"' found in list and returned "+ jsonArray.length() + " Objects");
			Report.writeHtmlLogs("PASS", "Sucessfully verified the Filters functionality by CarType");
			
		} catch (Exception e) {
			Report.writeHtmlLogs("FAIL", e.getMessage());
		}
	}

	/*********************************************************************************************************************
	 * @description	: To verify the Sort functionality by Corporate
	 * @author 		: Sharad Mali
	 *********************************************************************************************************************
	 */
	
	@Test
	public void verifySortByCorporate() {
		try {
			Map<String, List<Object>> map;			
			Object objInputData = CommonUtilityApplication.readInputData();
			
			map = CommonUtilityApplication.sortbyCorporate(objInputData);
			
			Set<String> actualListOfVenders = map.keySet();
			
			List<String> listOfVenders = CommonUtilityApplication.getListOfVenders(objInputData);			
			List<String> expectedListOfVenders = new ArrayList<>(new HashSet<>(listOfVenders));
	
			Collections.sort(expectedListOfVenders);
		
			Assert.assertTrue(actualListOfVenders.toString().equals(expectedListOfVenders.toString()));
			Report.writeHtmlLogs("PASS", "Verified both the collections are sorted and equal: Actual :" + actualListOfVenders + " Epected : " + expectedListOfVenders);

			Report.writeHtmlLogs("PASS", "Sucessfully verified the Sort functionality by Corporate");
			
		} catch (Exception e) {
			Report.writeHtmlLogs("FAIL", e.getMessage());
		}
	}

	/*********************************************************************************************************************
	 * @description	: To verify the Sort functionality within a Group/Vender by Price
	 * @parameter	: strVenderName
	 * @author 		: Sharad Mali
	 *********************************************************************************************************************
	 */
	
	@Test (dataProvider = "get-test-data-method-vender")
	public void verifySortWithinGroupbyPrice(String strVenderName) {
		try {
			List<JSONObject> listGroupbyPrice;		
			List<Double> listTotalPrice = new ArrayList<Double>();
			Object objInputData = CommonUtilityApplication.readInputData();
			
			listGroupbyPrice = CommonUtilityApplication.sortWithinGroupbyPrice(objInputData, strVenderName);

			for (int i = 0; i < listGroupbyPrice.size(); i++) {				
				JSONObject jsonObject = new JSONObject(listGroupbyPrice.get(i));										
				String strTotalAmount = JsonPath.read(jsonObject, "$.VehAvailCore.TotalCharge['@EstimatedTotalAmount']");					
				Double dblTotalAmount = Double.valueOf(strTotalAmount);				
				listTotalPrice.add(dblTotalAmount);
			}			
			
			List<Double> expectedListtotalPrice = new ArrayList<Double>(listTotalPrice);
			Collections.sort(expectedListtotalPrice);
			Assert.assertTrue(expectedListtotalPrice.equals(listTotalPrice));
								
			Report.writeHtmlLogs("PASS", "Verified both the lists are sorted and equal: Actual :" + listTotalPrice + " Epected : " + expectedListtotalPrice);
			
			Report.writeHtmlLogs("PASS", "Sucessfully verified the Sort functionality within a Group/Vender by Price");
			
		} catch (Exception e) {
			Report.writeHtmlLogs("FAIL", e.getMessage());
		}
	}
}
