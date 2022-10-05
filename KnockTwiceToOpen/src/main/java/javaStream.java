import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class javaStream {
	public static void main(String[] args) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		String k = "ASSOCIATED_COUNTRY_ID";
		String v = "NAME";
		String values = "[{ASSOCIATED_COUNTRY_ID=AT, NAME=Austria}, {ASSOCIATED_COUNTRY_ID=BL, NAME=Baltics}, {ASSOCIATED_COUNTRY_ID=BE, NAME=Belgium}, {ASSOCIATED_COUNTRY_ID=CK, NAME=Czech Republic}, {ASSOCIATED_COUNTRY_ID=DK, NAME=Denmark}, {ASSOCIATED_COUNTRY_ID=FI, NAME=Finland}, {ASSOCIATED_COUNTRY_ID=FR, NAME=France}, {ASSOCIATED_COUNTRY_ID=DE, NAME=Germany}, {ASSOCIATED_COUNTRY_ID=GR, NAME=Greece}, {ASSOCIATED_COUNTRY_ID=HF, NAME=Hungary}, {ASSOCIATED_COUNTRY_ID=IS, NAME=Iceland}, {ASSOCIATED_COUNTRY_ID=IE, NAME=Ireland}, {ASSOCIATED_COUNTRY_ID=IT, NAME=Italy}, {ASSOCIATED_COUNTRY_ID=NL, NAME=Netherlands}, {ASSOCIATED_COUNTRY_ID=NO, NAME=Norway}, {ASSOCIATED_COUNTRY_ID=PT, NAME=Portugal}, {ASSOCIATED_COUNTRY_ID=RO, NAME=Romania & Bulgaria}, {ASSOCIATED_COUNTRY_ID=CZ, NAME=Slovakia}, {ASSOCIATED_COUNTRY_ID=ES, NAME=Spain}, {ASSOCIATED_COUNTRY_ID=SE, NAME=Sweden}, {ASSOCIATED_COUNTRY_ID=CH, NAME=Switzerland}, {ASSOCIATED_COUNTRY_ID=GB, NAME=United Kingdom}]";
		String[] a = values.split("[{}]");
		for(String ele : a) {
			if(!ele.trim().isBlank()) {
				String[] q = ele.split(" ");
				if(q[0].contains(k)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put(k, q[0].split("=")[1].replace(",", ""));
					map.put(v, q[1].split("=")[1]);
					list.add(map);
				}
			}
		}

		System.out.println(list);
		
		Map<String, String> countryMap = new HashMap<String, String>();
		countryMap.putAll(list.stream().collect(Collectors.toMap(key -> key.get(k), val-> val.get(v))));
		System.out.println(countryMap);
		
		countryMap = new HashMap<String, String>();
		countryMap.putAll(list.parallelStream().
				collect(Collectors.toMap(key -> key.get(k), val-> val.get(v))));
		System.out.println(countryMap);
		
		LinkedHashMap<String, String> countryLMap = new LinkedHashMap<String, String>();
		for(Map<String, String> li : list) {
			String z = li.get(k);
			countryLMap.put(li.get(k), li.get(v));
		}
		System.out.println(countryLMap);
		countryLMap = new LinkedHashMap<String, String>();
		countryLMap.putAll(list.stream().collect(Collectors.toMap(kM ->kM.get(k), vM -> vM.get(v), (e1, e2) -> e1, LinkedHashMap::new)));
		System.out.println(countryLMap);
		
		countryLMap = new LinkedHashMap<String, String>();
		countryLMap.putAll(list.stream().collect(Collectors.toMap(kM ->kM.get(k), vM -> vM.get(v))));
		System.out.println(countryLMap);
	}
}
