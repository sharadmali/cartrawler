package lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelUtility {

	/**
	 * @funnctionName :- getRecordUsingFillo
	 * @throws FilloException
	 */
	public static Recordset getRecordUsingFillo(String fileName, String strQuery) throws FilloException {
		Connection con = null;
		Fillo fil = null;
		Recordset rs = null;

		try {
			fil = new Fillo();
			con = fil.getConnection(fileName);
			rs = con.executeQuery(strQuery);
		} catch (Exception e) {

		}

		return rs;

	}

	/**
	 * @funnctionName :- readGroups
	 * @throws FilloException
	 */
	public static List<String> readGroups(String fileName, String strQuery) throws FilloException {

		ArrayList<String> l = new ArrayList<String>();
		try {
			Recordset rs = ExcelUtility.getRecordUsingFillo(fileName, strQuery);

			while (rs.next()) {
				String grps = rs.getField("ClassName");
				l.add(grps);
			}
		} catch (Exception e) {

		}

		return l;

	}

	/**
	 * @funnctionName :- readGroups
	 * @throws FilloException
	 */
	public static List<String> readTestCase(String fileName, String strQuery) throws FilloException {

		ArrayList<String> l = new ArrayList<String>();
		try {
			Recordset rs = ExcelUtility.getRecordUsingFillo(fileName, strQuery);

			while (rs.next()) {
				String testCase = rs.getField("TestCase");
				l.add(testCase);
			}
		} catch (Exception e) {

		}

		return l;

	}

	public static HashMap<String, String> extractTestDataToMap(String InputContent) {
		HashMap<String, String> inputMap = new HashMap<String, String>();

		JSONObject json = new JSONObject(InputContent);

		if (null != json) {
			Set<String> keys = json.keySet();
			for (String key : keys) {
				try {
					inputMap.put(key, json.getString(key));
				} catch (Exception e) {
					inputMap.put(key, json.getJSONObject(key).toString());
				}
			}
		}
		return inputMap;
	}

}
