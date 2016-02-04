package temp;

import java.time.LocalDate;
import java.util.ArrayList;
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
			for (int i = 0; i < 1; i++) {
				LocalDate day = LocalDate.of(2015, 1, 10);
			List xKeyList = new ArrayList();
			xKeyList.addAll(xData.getData().keySet());
			xKeyList.get(0);
				
			}
		}
		
		
		return new DataCollection(getTitle(), xData.getUnit(), yData.getUnit(), finalResult);
	}


}
