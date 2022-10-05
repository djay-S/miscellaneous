import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class stringSimilarity {
	public static void main(String[] args) {
		String inv1 = "AAabcde12345";
		String inv2 = "abcde12345";
		String str = "分销商选择电déclaraèختيار الموزعсведения子邮件公司我的详细信息`¡™£¢∞§¶•ªº–≠`⁄€‹›ﬁﬂ‡°·‚—±œ∑´®†¥¨ˆøπ“‘«Œ„ˇÁ¨ˆØ∏”’»åß∂ƒ©˙∆˚¬…æÅÍÎÏ˝ÔЧек ККМ KPNÒÚÆΩ≈ç√∫˜µ≤≥÷¸◊ı˜Â¯˘¿";
		char[] chArr = new char[str.length()];
		System.out.println(inv1 + " " + inv2);
		System.out.println("Common prefix:" + StringUtils.getCommonPrefix(inv1, inv2));
//		System.out.println("Common prefix: =" + StringUtils.getCommonPrefix("inv1", "qwerinvsdf"));
//		System.out.println("Common prefix: =" + StringUtils.getCommonPrefix("inv1", "inv2"));
//		System.out.println("Common prefix: =" + StringUtils.getCommonPrefix("234/INV", "678/INV"));
		System.out.println(StringUtils.getJaroWinklerDistance(inv1, inv2));
		System.out.println(StringUtils.getLevenshteinDistance(inv1, inv2));
		System.out.println("qwerty".compareTo("Qw?erty"));
		System.out.println(inv1);
//		StringBuilder inou = new StringBuilder();
//		for(int i=0; i<str.length(); i++)
//			chArr[i] = str.charAt(i);
//		str.length();
//		List<Character> list = new ArrayList<Character>(); 
//		list = str.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
//		list.size();
//		System.out.println("\n\n\n");
//		String validChar = "";
//		String invalidChar = "";
//		for(Character li : list) {
//			validChar += StringUtils.isAlpha(li.toString()) ? li + "," : "";
//			invalidChar += StringUtils.isAlpha(li.toString()) ? "" : li + ",";
//		}
//		System.out.println("Valid: " + validChar);
//		System.out.println("Invalid: " + invalidChar);
	}
}
