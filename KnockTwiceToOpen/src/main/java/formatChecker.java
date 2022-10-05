import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class formatChecker {
	public static void main(String[] args) throws Exception {
		String text = "";
		text = "1/234.gfd:2345fgf;' 1q2";
		String qwe = text.replaceAll("[^a-zA-Z0-9]", "");
		//		text = "1050/21.11.2019";
		//		text = "№115";
		File file = new File("/Users/DH20026552/Documents/testDataFormatCheck.txt");
		Scanner sc = new Scanner(file);

		//		Create format
		String format = getFormat(text);
		System.out.println();
		System.out.println(text + "  ==>  " + format);

		while (sc.hasNext()) {
			String dataCode = sc.nextLine();
			String dataFormat = getFormat(dataCode);
			//			System.out.println(dataCode+" ==> "+dataFormat);
			//			System.out.println("Matched data:\n\n");
			if(dataFormat.equals(format))
				System.out.println(dataCode);
			//			System.out.println(sc.nextLine());
		}

	}
	public static String getFormat(String inputText) {
		String format = "";
		String regex = "";
		String text = "a-zA-Z";
		String number = "0-9";
		int stringFlow = 0;
		String[] noSpecial = inputText.split("[^a-zA-Z0-9]");
		if(noSpecial.length > 0) {
			for(int i = 0; i < noSpecial.length; i++) {
				//			String[] numStr = noSpecial[i].split("[0-9]"); 
				//			System.out.println(numStr.length);
				//			Pattern.matches("[]", input)
				if(noSpecial[i].length() != 0) {
					if(noSpecial[i].matches("[0-9]*")) {			//pure number
						System.out.println("Number: "+ noSpecial[i]);
						stringFlow += noSpecial[i].length();
						System.out.println(inputText.charAt(stringFlow));
						regex += "[" + number + "]{" + noSpecial[i].length() + "}" + inputText.charAt(stringFlow++);
					}
					else if(noSpecial[i].matches("[a-zA-Z]*")){		//pure string
						System.out.println("String: "+ noSpecial[i]);
						stringFlow += noSpecial[i].length();
						System.out.println(inputText.charAt(stringFlow));
						regex += "[" + text + "]{" + noSpecial[i].length() + "}" + inputText.charAt(stringFlow++);
					}
					else {											//alphaNum
						int strCount = 0;
						int numCount = 0;
						for(int j = 0; j < noSpecial[i].length(); j++) {
							if(noSpecial[i].toLowerCase().charAt(j) >='a' && noSpecial[i].toLowerCase().charAt(j) <='z') {
								strCount ++;
							}
							else if (noSpecial[i].charAt(j) <='9' && noSpecial[i].charAt(j) >='0') {
								numCount ++;
							}
						}
						//12345ewq
//						String[] splitStr = noSpecial[i].split("[^0-9]");	//pure number
//						if(splitStr.length == 1) {
//							regex += "[" + number + "]{" + splitStr[0].length() + "}";
//							if(noSpecial[i].length() != splitStr[0].length())
//								regex += "[" + text + "]{" + (noSpecial[i].length() - splitStr[0].length()) + "}";
//						}
//						System.out.println(splitStr.length);
//						stringFlow += noSpecial[i].length();
//						System.out.println(inputText.charAt(stringFlow));
//						regex += inputText.charAt(stringFlow++);
					}
				}
				else
					regex += inputText.charAt(stringFlow++);
			}
		}
		for(int i = 0; i < inputText.length(); i++) {
			//			Matcher textMatcher = textPattern.matcher(text.charAt(i).t);
			//			if(inputText.toLowerCase().charAt(i) >= 'a' &&  inputText.toLowerCase().charAt(i) <= 'z')
			if(inputText.matches("[a-zA-Z]"))
				format += "X";
			else if(inputText.charAt(i) >= '0' && inputText.charAt(i) <= '9')
				//			else if(inputText.matches("[0-9]"))
				format += "0";
			else
				format += inputText.charAt(i);
		}
		return format;
	}
}
