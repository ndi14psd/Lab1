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
		matchDataByDays();
		transformToOtherResolution();
		compressListsToSingleValues();
		
		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}

	private void matchDataByDays() {
		List<LocalDate> xKeys = new ArrayList<>();
		List<LocalDate> yKeys = new ArrayList<>();

		xKeys.addAll(xData.getData().keySet());
		yKeys.addAll(yData.getData().keySet());

		for (int i = 0; i < xKeys.size(); i++) 
			for (int u = 0; u < yKeys.size(); u++) {
				if(xKeys.get(i).equals(yKeys.get(u))){
					String key = xKeys.get(i).toString();
					Double xKeyData = xData.getData().get(xKeys.get(i));
					Double yKeyData = yData.getData().get(yKeys.get(u));
					MatchedDataPair match = new MatchedDataPair(xKeyData, yKeyData);
					finalResult.put(key, match);
				}
			}
	}

	private void transformToOtherResolution() {
		Set<String> keys = finalResult.keySet();

		for(String dayKey : keys) {
			String newKey = getDesiredKey(LocalDate.parse(dayKey));
			if(!resultData.containsKey(newKey)) {
				List<MatchedDataPair> monthPairs = new ArrayList<>();
				monthPairs.add(finalResult.get(dayKey));
				resultData.put(newKey, monthPairs);
			} else {
				List<MatchedDataPair> monthPairs = resultData.get(newKey);
				monthPairs.add(finalResult.get(dayKey));
			}
		}
	}

	private String getDesiredKey(LocalDate date) {
		switch(resolution) {

		case DAY: return date.toString();

		case WEEK: 

		case MONTH: return date.getMonth().name();
		
		case YEAR: return "" + date.getYear(); 

		default: throw new RuntimeException();

		}
	}

	private void compressListsToSingleValues() {
		finalResult.clear();

		resultData.forEach((key, entries) -> {
			Double sumX = 0.0;
			Double sumY = 0.0;

			for( MatchedDataPair pair : entries) {
				sumX += pair.getXValue();
				sumY += pair.getYValue();
			}

			Double averageX = sumX / entries.size();
			Double averageY = sumY / entries.size();

			finalResult.put(key, new MatchedDataPair(averageX, averageY));
		});
	}


}
