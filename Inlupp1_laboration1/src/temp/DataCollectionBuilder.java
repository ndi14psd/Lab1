package temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCollectionBuilder {

	private DataSource xData;
	private DataSource yData;
	private Resolution resolution;
	private Map<String, List<MatchedDataPair>> resultData;
	private Map<String, MatchedDataPair> finalResult;
	private String title;

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution) {
		this.xData = xData;
		this.yData = yData;
		this.resolution = resolution;
		finalResult = new HashMap<>();
		resultData = new HashMap<>();
	}

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution, String title) {
		this(xData, yData, resolution);
		this.title = title;
	}

	public String getTitle() {
		return (title != null) ? title : xData.getName() + " / " + yData.getName();
	}
	
	public DataCollection getResult() {
		
		List<LocalDate> xKeys = new ArrayList<>();
		List<LocalDate> yKeys = new ArrayList<>();

		xKeys.addAll(xData.getData().keySet());
		yKeys.addAll(yData.getData().keySet());
		

		for (int i = 0; i < xKeys.size(); i++) {
			String xKey = resolution.getKey(xKeys.get(i));
			for (int u = 0; u < yKeys.size(); u++) {
				String yKey = resolution.getKey(yKeys.get(u));
				if(xKey.equals(yKey)){
					Double xKeyData = xData.getData().get(xKeys.get(i));
					Double yKeyData = yData.getData().get(yKeys.get(u));
					
					addMatchToResultData(xKey, new MatchedDataPair(xKeyData, yKeyData));
				}
			}
		}
//		xData.getData().forEach((xKey, xValue) -> {
//			yData.getData().forEach((yKey, yValue) -> {
//				if(resolution.areEqual(xKey, yKey)) {
//					Double xKeyData = xData.getData().get(xKey);
//					Double yKeyData = yData.getData().get(yKey);
//					addMatchToResultData(resolution.getKey(xKey), new MatchedDataPair(xKeyData, yKeyData));
//				}
//				
//			}); 
//			
//		}); 
		
		calculateResultToFinal();

		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}

	private void addMatchToResultData(String xKey, MatchedDataPair match) {
		if(resultData.containsKey(xKey)) {
			List<MatchedDataPair> existing = resultData.get(xKey);
			existing.add(match);
		}
		else {
			List<MatchedDataPair> matches = new ArrayList<>();
			matches.add(match);
			resultData.put(xKey, matches);
		}
	}

	private void calculateResultToFinal() {
		resultData.forEach((key, list) -> {
			
			Double sumX = 0.0;
			Double sumY = 0.0;
			
			for (int i = 0; i < list.size(); i++) {
				sumX += list.get(i).getXValue();
				sumY += list.get(i).getYValue();
			}
			
			Double averageX = sumX / list.size();
			Double averageY = sumY / list.size();
			
			finalResult.put(key, new MatchedDataPair(averageX, averageY));
			
		});
	}


}
