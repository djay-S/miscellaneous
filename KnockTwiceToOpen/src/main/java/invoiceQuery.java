import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;




public class invoiceQuery {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		String invoice ="INV/2020/4160%30Apr_test!";
		//INV/2020/4160%30Apr_test
		//INV_2020_4160
		String query = "";
		for(int i = 0; i < invoice.length(); i++) {
			if(invoice.charAt(i) == '%' || invoice.charAt(i) == '_') {
				query+="<" + invoice.charAt(i);
			}
			else{
				query += invoice.charAt(i);
			}
		}
		System.out.println("Sql stratement is: \n" +query);
		System.out.println(invoice);
		System.out.println(invoice.replaceAll("[^a-zA-Z0-9]", ""));
		String q = null;
		System.out.println(StringUtils.isBlank(q));
		System.out.println(WordUtils.capitalize("Part number repetition"));

	}
}
