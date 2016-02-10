package temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
	}

	public DataCollectionBuilder(DataSource xData, DataSource yData, Resolution resolution, String title) {
		this(xData, yData, resolution);
		this.title = title;
	}

	public String getTitle() {
		return (title != null) ? title : xData.getName() + " / " + yData.getName();
	}

	public DataCollection getResult() {
	

		if(resolution == Resolution.DAY) {
			matchDataByDays();
		}

		else if(resolution == Resolution.MONTH) {
			/* 1. */ matchDataByDays();
			
			/* 2. lägger in värden i samma månad i listor i mappen resultData, nyckel = [MÅNAD]*/
			Set<String> keys = finalResult.keySet();
			resultData = new HashMap<>();
			List<MatchedDataPair> monthPairs;
			
			for(String dayKey : keys) {
				String monthKey = LocalDate.parse(dayKey).getMonth().toString();
				if(!resultData.containsKey(monthKey)) {
					monthPairs = new ArrayList<>();
					monthPairs.add(finalResult.get(dayKey));
					resultData.put(monthKey, monthPairs);
				} else {
					monthPairs = resultData.get(monthKey);
					monthPairs.add(finalResult.get(dayKey));
				}
			}
			
			/* 3. måste lägga ihop värdena i varje lista (summa eller medelvärde?) */
		}
		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}

	private void matchDataByDays() {
		List<LocalDate> xKeys = new ArrayList<>();
		List<LocalDate> yKeys = new ArrayList<>();
		finalResult = new HashMap<>();
		xKeys.addAll(xData.getData().keySet());
		yKeys.addAll(yData.getData().keySet());

		for (int i = 0; i < xKeys.size(); i++) {
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

	}


}
