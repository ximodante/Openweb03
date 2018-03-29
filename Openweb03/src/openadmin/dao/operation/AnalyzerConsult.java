package openadmin.dao.operation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import openadmin.model.Base;
import openadmin.util.reflection.ReflectionField;

/**
 * To make the where clause of the sentence SQL
 * 
 * @version 0.1 Created 10-05-2009 Author Alfred Oliver
 *
 */
public class AnalyzerConsult {

	/**
	 * Pattern Type 1: || ...... || is the logical operator "Or"
	 */
	static final String patternOrBegin = "^\\|\\|.+";

	/**
	 * Pattern Type 2: text || text || text ..... || is the logical operator "Or"
	 */
	static final String patternOr = "((^[\\w\\%\\' ]+)\\|\\|)" + "(([\\w\\%\\' ]+)\\|\\|)*" + "([\\w\\%\\' ]+$)";

	/**
	 * Pattern Type 3: text || || is the logical operator "Or"
	 */
	static final String patternSingleOr = "(([\\w\\%\\' ]+)\\|\\|)";

	/**
	 * Make the where clause of the sentence SQL
	 * 
	 * @param obj
	 *            object type Base
	 * @return String. Where clause of the sentence SQL
	 */
	public String makeWhere(Base obj) {

		String whereClause = "";

		ReflectionField rf = new ReflectionField();

		// Get all the fields initialized of object
		for (String pPropertyField[] : rf.execute(obj)) {

			// if (pPropertyField[1].equals("sql")) continue;

			if (pPropertyField[1].trim().equals("n")
					&& (pPropertyField[2].trim().equals("0") || pPropertyField[2].trim().equals("0.0"))) {

				System.out.println("Expression RESULT: " + pPropertyField[1] + " " + pPropertyField[2]);
				continue;

			}

			whereClause = whereClause + analyzer(pPropertyField);

		}

		if (whereClause.startsWith(" and") || whereClause.startsWith(" or"))
			whereClause = whereClause.substring(4).trim();

		if (whereClause.length() > 0)
			whereClause = "where " + whereClause;

		System.out.println("Expression result: " + whereClause);
		return whereClause;

	}

	/**
	 * Make the sentence
	 * 
	 * @param obj
	 *            The properties to field (name, logical operator and value)
	 * @return String. sentence SQL of field
	 */
	private String analyzer(String pPropertyField[]) {

		String typeStringOpen = " '";
		String typeStringClose = "'";
		String operator = pPropertyField[1].trim();

		// Is numeric
		if (pPropertyField[1].trim().equals("n")) {

			operator = pPropertyField[1].trim().replace("n", "= ");
			typeStringOpen = "";
			typeStringClose = "";

		}

		// Is id
		if (pPropertyField[1].trim().equals("id")) {

			operator = pPropertyField[1].trim().replace("id", "= ");
			typeStringOpen = "";
			typeStringClose = "";

		}

		// Input information to evaluate
		String input = pPropertyField[2].trim().replace("'", "''");

		// Result: extract the information needed to where clause.
		String result = "";

		String andOr = " and";

		// System.out.println("Expression input: " + input);

		// Compile pattern type 1 and evaluate expression
		Pattern pat = Pattern.compile(patternOrBegin);
		Matcher mat = pat.matcher(input);

		// Replace and to or
		if (mat.matches()) {

			andOr = " or";
			input = input.substring(2);

		}

		// Compile pattern type 2 and evaluate expression
		pat = Pattern.compile(patternOr);
		mat = pat.matcher(input);

		if (mat.matches()) {

			String beginResult = andOr + " (";
			String endResult = pPropertyField[0] + " " + operator + typeStringOpen + mat.group(5).trim()
					+ typeStringClose + ")";

			// Compile pattern type 3 and evaluate expression
			pat = Pattern.compile(patternSingleOr);
			mat = pat.matcher(input);

			while (mat.find()) {

				result = result + pPropertyField[0] + " " + operator + typeStringOpen + mat.group(2).trim()
						+ typeStringClose + " or ";

			}

			return result = beginResult + result + endResult;
		}

		result = " and " + result + pPropertyField[0] + " " + operator + typeStringOpen + input + typeStringClose;

		return result;
	}

}
