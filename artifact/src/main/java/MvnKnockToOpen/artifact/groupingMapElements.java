package MvnKnockToOpen.artifact;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class groupingMapElements {
	private List<SuspiciousClaimReport> formatReportList(List<SuspiciousClaimReport> reportList, SuspiciousReport reportInput) {
		List<SuspiciousClaimReport> res = new ArrayList<SuspiciousClaimReport>();
		Map<String, List<SuspiciousClaimReport>> refClaimMap = new LinkedHashMap<String, List<SuspiciousClaimReport>>();
		for(SuspiciousClaimReport report : reportList) {
			List<SuspiciousClaimReport> list = new ArrayList<SuspiciousClaimReport>();
			if(!report.getRefClaimId().isBlank()) {
				if(!refClaimMap.containsKey(report.getRefClaimId())) {
					list.add(report);
					refClaimMap.put(report.getRefClaimId(), list);	
				}
				else {
					list = refClaimMap.get(report.getRefClaimId());
					report.setGroupId(refClaimMap.get(report.getRefClaimId()).get(0).getGroupId());
					list.add(report);
					refClaimMap.put(report.getRefClaimId(), list);
				}
			}
			else {
				if(!refClaimMap.containsKey(report.getClaimId())) {
					list.add(report);
					refClaimMap.put(report.getClaimId(), list);
				}
				else {
					List<SuspiciousClaimReport> existingList = refClaimMap.get(report.getClaimId());
					SuspiciousClaimReport existingReport = refClaimMap.get(report.getClaimId()).get(0);
					if(!existingReport.getRefClaimId().isBlank() 
							|| (existingReport.getRefClaimId().isBlank() && 
									isRepeatedProdDescEntry(existingList, report))) {
						existingList.get(0).getGroupId();
						list.add(report);
						list.addAll(refClaimMap.get(report.getClaimId()));
						refClaimMap.remove(report.getClaimId());
						refClaimMap.put(report.getClaimId(), list);
					}
				}
			}
		}
		refClaimMap.values().removeIf(list -> list.get(0).getClaimStatus().equalsIgnoreCase("Rejected")
				|| list.get(0).getClaimStatus().equalsIgnoreCase("Drafted") 
				|| list.get(0).getClaimStatus().equalsIgnoreCase("Terminated"));
		
		if(reportInput.getSuspiciousCategory().equals("2")) {
			refClaimMap = groupRepeatedProducts(refClaimMap);
		}
		int groupId = 1;
		for(Map.Entry<String, List<SuspiciousClaimReport>> reportMap : refClaimMap.entrySet()) {
			int groupValue = (groupId);
			if(reportMap.getValue().size() > 0) {
				if(reportMap.getValue().stream().filter(r -> !StringUtils.isBlank(r.getProdQty()) && !r.getProdDesc().isBlank())
						.collect(Collectors.toList()).size() > 0) {
					List<SuspiciousClaimReport> repeatedProductValues = reportMap.getValue();
					Map<String, List<SuspiciousClaimReport>> productMap = new LinkedHashMap<>();
					int subGrpId = groupId;
					for(SuspiciousClaimReport row: repeatedProductValues) {
						if(!row.getProdDesc().isBlank()) {
							List<SuspiciousClaimReport> list = new ArrayList<>();
							if(productMap.containsKey(row.getProdDesc())) {
								list.addAll(productMap.get(row.getProdDesc()));
								list.add(row);
								productMap.put(row.getProdDesc(), list);
							}
							else {
								list.add(row);
								productMap.put(row.getProdDesc(), list);
							}
						}
						else {
							row.setGroupId(subGrpId);
						}
					}
					for(Map.Entry<String, List<SuspiciousClaimReport>> prodMap : productMap.entrySet()) {
						if(prodMap.getValue().stream().filter(r -> r.getGroupId() > -1).collect(Collectors.toList()).size() > 0) {
						int subGrpVal = (subGrpId++);
						prodMap.getValue().stream().filter(r -> r.getGroupId() > -1).forEach(r -> r.setGroupId(subGrpVal));
						}
					}
					groupId = subGrpId - 1;
				}
				else {
					reportMap.getValue().stream().filter(r -> r.getGroupId() > -1).forEach(r -> r.setGroupId(groupValue));
				}
				groupId++;
			}
		}
		for(Map.Entry<String, List<SuspiciousClaimReport>> entry : refClaimMap.entrySet()) {
			List<SuspiciousClaimReport> list = new ArrayList<SuspiciousClaimReport>();
			list = entry.getValue();
			res.addAll(list.stream().filter(r -> r.getGroupId() > -1).collect(Collectors.toList()));
		}
		res.sort(Comparator.nullsFirst(Comparator.comparing(SuspiciousClaimReport::getGroupId))
				.thenComparing(SuspiciousClaimReport::getRefClaimId));
		return res;
	}
	
	private Map<String, List<SuspiciousClaimReport>> groupRepeatedProducts(
			Map<String, List<SuspiciousClaimReport>> refClaimMap) {
		Set<String> refClaimKeySet = refClaimMap.keySet();
		for(Map.Entry<String, List<SuspiciousClaimReport>> reportEntry : refClaimMap.entrySet()) {
			List<SuspiciousClaimReport> reportList = reportEntry.getValue();
			String refClaimId = reportEntry.getKey();
			ListIterator<SuspiciousClaimReport> listItr = reportList.listIterator();
			while(listItr.hasNext()) {
				SuspiciousClaimReport report = listItr.next();
				if(!report.getRefClaimId().isBlank() && !report.getClaimId().equals(refClaimId)) {
					if(refClaimKeySet.contains(report.getClaimId())){
						String lastRefClaId = report.getClaimId();
						List<SuspiciousClaimReport> nestedReport = refClaimMap.get(report.getClaimId());
						List<SuspiciousClaimReport> headRefReportList = reportList.stream().filter(r ->r .getRefClaimId().isBlank())
								.collect(Collectors.toList());
						for(SuspiciousClaimReport refReport : headRefReportList) {
							List<SuspiciousClaimReport> similarReport = nestedReport.stream()
									.filter(r -> r.getProdDesc().equals(refReport.getProdDesc()) && r.getProdQty().equals(refReport.getProdQty())
											&& r.getGroupId() > -1)
									.collect(Collectors.toList());
							List<SuspiciousClaimReport> newSimilarReport = new ArrayList<>();
							newSimilarReport = getNewListMapping(similarReport);
							newSimilarReport.stream().filter(r -> !r.getRefClaimId().isBlank() && r.getGroupId() > -1)
							.forEach(t -> listItr.add(t));
							similarReport.stream().forEach(t -> t.setGroupId(-1));
							if(similarReport.size() > 0) {
								lastRefClaId = similarReport.get(similarReport.size() - 1).getClaimId();
								boolean isNested = !StringUtils.isBlank(lastRefClaId);
								while(isNested) {
									if(refClaimKeySet.contains(lastRefClaId)) {
										List<SuspiciousClaimReport> subNestReport = refClaimMap.get(lastRefClaId);
										subNestReport = subNestReport.stream().filter(r -> r.getGroupId() > -1 
												&& r.getProdDesc().equals(refReport.getProdDesc()) 
												&& r.getProdQty().equals(refReport.getProdQty()))
												.collect(Collectors.toList());
										if(subNestReport.size() > 0) {
											List<SuspiciousClaimReport> newSubNestReport = new ArrayList<>();
											newSubNestReport = getNewListMapping(subNestReport);
											newSubNestReport.stream().filter( r -> !StringUtils.isBlank(r.getRefClaimId()) && r.getGroupId() > -1).forEach(r -> listItr.add(r));
											subNestReport.forEach(r -> r.setGroupId(-1));
											lastRefClaId = subNestReport.get(subNestReport.size() - 1).getClaimId();
											isNested = !StringUtils.isBlank(lastRefClaId) || !lastRefClaId.equals(refClaimId);
										}
										else {
											isNested = false;
										}
									}
									else {
										isNested = false;
									}
								}
							}
						}
					}
				}
			}
		}
		return refClaimMap;
	}
	
	private List<SuspiciousClaimReport> getNewListMapping(List<SuspiciousClaimReport> list){
		List<SuspiciousClaimReport> reportsList = new ArrayList<SuspiciousClaimReport>();
		for(SuspiciousClaimReport report : list) {
			SuspiciousClaimReport susClaim = new SuspiciousClaimReport();
			susClaim.setClaimDate(report.getClaimDate());
			susClaim.setClaimId(report.getClaimId());
			susClaim.setClaimStatus(report.getClaimStatus());
			susClaim.setCountry(report.getCountry());
			susClaim.setCreatedDate(report.getCreatedDate());
			susClaim.setCustomer(report.getCustomer());
			susClaim.setGroupId(report.getGroupId());
			susClaim.setInvoiceDate(report.getInvoiceDate());
			susClaim.setInvoiceNumber(report.getInvoiceNumber());
			susClaim.setProdDesc(report.getProdDesc());
			susClaim.setProdQty(report.getProdQty());
			susClaim.setProgram(report.getProgram());
			susClaim.setRefClaimId(report.getRefClaimId());
			susClaim.setReseller(report.getReseller());
			susClaim.setSusCategory(report.getSusCategory());
			susClaim.setTotalRebate(report.getTotalRebate());
			reportsList.add(susClaim);
		}
		return reportsList;
	}
	
	private boolean isRepeatedProdDescEntry (List<SuspiciousClaimReport> list, SuspiciousClaimReport report) {
		boolean isRepeatedEntry = true;
		if(report.getProdQty() !=null && Integer.valueOf(report.getProdQty()) > 0)
			isRepeatedEntry = list.stream().map(SuspiciousClaimReport::getProdDesc).filter(report.getProdDesc()::equals).findFirst().isPresent();
		return !isRepeatedEntry;
	}

}
