package temp;

import java.util.Map;

public class DataCollection {
	
	private Map<String, MatchedDataPair> data;
	private String title;
	private String xUnit;
	private String yUnit;
	
	public DataCollection(String title, String xUnit, String yUnit, Map<String, MatchedDataPair> data) {
		this.title = title;
		this.xUnit = xUnit;
		this.yUnit = yUnit;
		this.data = data;
		System.out.println("My 2nd edited syso print 10:27");
	}

	public Map<String, MatchedDataPair> getData() {
		return data;
	}

	public String getTitle() {
		return title;
	}

	public String getxUnit() {
		return xUnit;
	}

	public String getyUnit() {
		return yUnit;
	}
	
	@Override
	public String toString() {
		return "Title: " + title + ", xUnit: " + xUnit + ", yUnit: " + yUnit;
	}
	
	

}
