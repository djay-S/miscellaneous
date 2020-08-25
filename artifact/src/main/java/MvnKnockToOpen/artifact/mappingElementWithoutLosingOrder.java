package MvnKnockToOpen.artifact;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class mappingElementWithoutLosingOrder {
	public HashMap<String, String> fetchResellerCountryMap(String resellerCountryId) {
//		LOGGER.info("Starting ClaimHistoryDaoImpl @ fetchResellerCountryMap");
		List<Map<String, Object>> list = null;
		LinkedHashMap<String, String> countryMap = new LinkedHashMap<>();
		String sqlQuery = "";//sql.getSQLStatement(GET_RESELLER_COUNTRY_LIST);
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("countryId", resellerCountryId);
		try {
			list = namedParameterJdbcTemplate.queryForList(sqlQuery, parameters);
			if(list.size() > 0 || list != null) {
				countryMap = list.stream().
						collect(Collectors.toMap(k ->k.get("ASSOCIATED_COUNTRY_ID").toString(), 
								v -> v.get("NAME").toString(), (c1, c2) -> c1, LinkedHashMap::new));
			}
		}catch (DataAccessException e) {
//			LOGGER.error("Error in ClaimHistoryDaoImpl @ fetchResellerCountryMap(): ", e);
		}
//		LOGGER.info("Exiting ClaimHistoryDaoImpl @ fetchResellerCountryMap");
		return countryMap;
	}
}
