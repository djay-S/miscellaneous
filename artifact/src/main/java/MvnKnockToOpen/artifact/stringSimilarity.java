package MvnKnockToOpen.artifact;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Strings;

public class stringSimilarity {


	public static void main(String[] args) {
		String inv1 = "A$Vabcde12345";
		String inv2 = "abcde12345";
		String inn3 = "abcde12345A%Y";
		
		String str = "分销商选择电déclaraèختيار الموزعсведения子邮件公司我的详细信息`¡™£¢∞§¶•ªº–≠`⁄€‹›ﬁﬂ‡°·‚—±œ∑´®†¥¨ˆøπ“‘«Œ„ˇÁ¨ˆØ∏”’»åß∂ƒ©˙∆˚¬…æÅÍÎÏ˝ÔЧек ККМ KPNÒÚÆΩ≈ç√∫˜µ≤≥÷¸◊ı˜Â¯˘¿";
		char[] chArr = new char[str.length()];
		
		System.out.println(inv1 + " " + inv2 + " " + inn3);
		System.out.println("Common prefix:" + StringUtils.getCommonPrefix(inv1, inv2, inn3));
		System.out.println("<---Common Suffix--->");
		System.out.println(inv1 + " " + inv2 + " -> " + Strings.commonSuffix(inv1, inv2));
		System.out.println(inv1 + " " + inn3 + " -> " + Strings.commonSuffix(inn3, inv1));
		System.out.println(inv2 + " " + inn3 + " -> " + Strings.commonSuffix(inn3, inv2));
		System.out.println("<---JaroWinklerDistance--->");
		System.out.println(inv1 + " " + inv2 + " -> " + StringUtils.getJaroWinklerDistance(inv1, inv2));
		System.out.println(inv1 + " " + inn3 + " -> " + StringUtils.getJaroWinklerDistance(inn3, inv1));
		System.out.println(inv2 + " " + inn3 + " -> " + StringUtils.getJaroWinklerDistance(inv2, inn3));
		System.out.println("<---LevenshteinDistance--->");
		System.out.println(inv1 + " " + inv2 + " -> " + StringUtils.getLevenshteinDistance(inv1, inv2));
		System.out.println(inv1 + " " + inn3 + " -> " +  StringUtils.getLevenshteinDistance(inn3, inv1));
		System.out.println(inv2 + " " + inn3 + " -> " +  StringUtils.getLevenshteinDistance(inv2, inn3));
//		System.out.println("qwerty".compareTo("Qw?erty"));
//		System.out.println(inv1);
//		Normalised LD
//		1 - (edit distance / length of the larger of the two strings)
	}
}
