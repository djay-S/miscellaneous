import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

public class regexMaker {

	public static void main(String[] args) throws Exception {
		final String master = "\\d{1,2}/\\d{1,2}/(\\d{4}|\\d{2})";
		String text = "";
//		text = "1/234.gfd:2345fgf;' 1q2 A";
//		String qwe = text.replaceAll("[^a-zA-Z0-9]", "");
//		text = "1050/21.11.2019";
//		text = "№115";
//		text = "!@#123qwe_1@2_w#e_$4%_s5D_^F&_4H7 -+|:?><()*~`";
		text = "INV/2020/4160";
		File file = new File("/Users/DH20026552/Documents/testDataFormatCheck.txt");
		Scanner sc = new Scanner(file);
		char q;

		//		Create regex
		String regex = "^";
		String[] onlyNos = text.split("\\D"); 
		String[] onlySpecial = text.split("\\w");
		String[] onlyText = text.split("[^a-zA-Z]");
		System.out.println(text);
//		System.out.println(text.replaceAll("\\d", "+"));
//		System.out.println(text.replaceAll("\\D", "+"));
//		System.out.println(text.replaceAll("\\w", "+"));
//		System.out.println(text.replaceAll("\\W", "+"));
//		System.out.println(text.replaceAll("[a-zA-Z]", "+"));
//		System.out.println(text.replaceAll("[0-9]", "+"));
//		System.out.println(text.replaceAll("[^a-zA-Z0-9]", "+"));
//		System.out.println(text.replaceAll("[^0-9]", "+"));

		for(int i = 0; i < text.length(); i++) {
			int count = 0;
			if(text.charAt(i) >= '0' && text.charAt(i) <= '9') {
				while(i < text.length() && text.charAt(i) >= '0' && text.charAt(i) <= '9') {
					q=text.charAt(i);
					count++;
					i++;
				}
				regex += "[0-9]{" + count + "}";
				i--;
			}
			else if(i < text.length() && text.toLowerCase().charAt(i) >= 'a' && text.toLowerCase().charAt(i) <= 'z') {
				while(i < text.length() && 
						text.toLowerCase().charAt(i) >= 'a' && text.toLowerCase().charAt(i) <= 'z' 
						) {
					q=text.charAt(i);
					count++;
					i++;
				}
				regex += "[a-zA-Z]{" + count + "}";
				i--;
			}
			else{
				q=text.charAt(i);
				regex += text.charAt(i);
			}
		}
		regex += "$";
		System.out.println("Regex is: " +regex);

		String as = text.replaceAll("[^a-zA-Z0-9]", "");
		String[] aray = as.split("");
		String asdf = String.join("%", aray);
		asdf += "%";
		System.out.println(asdf);
		System.out.println(String.join("%", (as.split(""))));
		as = String.join("--", (as.split("")));
		System.out.println(as);
//		while(sc.hasNext()) {
//			String invoice = sc.nextLine();
//			if(Pattern.matches(regex, invoice))
//				System.out.println(invoice);
//		}
	}
}
