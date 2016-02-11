package temp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

		List<MatchedDataPair> matches = new ArrayList<>();
		
		for (int i = 0; i < xKeys.size(); i++) {
			String xKey = resolution.getKey(xKeys.get(i));
			for (int u = 0; u < yKeys.size(); u++) {
				String yKey = resolution.getKey(yKeys.get(u));
				if(xKey.equals(yKey)){
					
					if(resultData.containsKey(xKey)) {
						
					}
					Double xKeyData = xData.getData().get(xKeys.get(i));
					Double yKeyData = yData.getData().get(yKeys.get(u));
					matches.add(new MatchedDataPair(xKeyData, yKeyData));
					
				}
			}
			resultData.put(xKey, matches);
		}

		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}


}
