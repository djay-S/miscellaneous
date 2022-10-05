import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class leetcode {
	public static void main(String[] args) {
		try {
			String add = "RESELLER_COUNTRY_MAPPING_SEQ.nextval";
			String newstr = "";
			File inputFile = new File("/Users/DH20026552/Documents/SQL_SCRIPTS/RESELLER_COUNTRY_MAPPING-DATA.sql");
			FileWriter outputFile = new FileWriter("/Users/DH20026552/Documents/SQL_SCRIPTS/RESELLER_COUNTRY_MAPPING-DATA2.sql");
			Scanner sc = new Scanner(inputFile);
			while(sc.hasNext()) {
				String data = sc.nextLine();
				if(data.contains("Insert")) {
					String[] q = data.split("[^0-9]");
					for(String w : q) {
						if(!w.isBlank()) {
							Integer n = Integer.parseInt(w);
							if(n%2 != 0) {}
							newstr += data.replace(n.toString(), add) + "\n";
						}
					}
				}
			}
			sc.close();
			outputFile.write(newstr);
			outputFile.close();
			System.out.println(newstr);
		}catch(Exception e) {}

	}
}
