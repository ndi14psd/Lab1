package temp;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DataCollectionBuilderTest {
	DataCollectionBuilder dcBuilder;
	Map<String, MatchedDataPair> correctData;
	DataSource xData;
	DataSource yData;
	Resolution resolution;
	@Before
	public void setUp() throws Exception {
		resolution = Resolution.DAY;
		Map<LocalDate, Double> temperatureTable = new HashMap<>();
		Map<LocalDate, Double> wakeUpTimeTable = new HashMap<>();
		
		temperatureTable.put(LocalDate.of(2016, 1, 1), (double) -14);
		temperatureTable.put(LocalDate.of(2016, 1, 9), (double) 2);
		temperatureTable.put(LocalDate.of(2016, 2, 1), (double) 0);
		temperatureTable.put(LocalDate.of(2016, 2, 5), (double) -4);
		temperatureTable.put(LocalDate.of(2016, 2, 15), (double) -7);
		temperatureTable.put(LocalDate.of(2016, 3, 2), (double) 5);
		temperatureTable.put(LocalDate.of(2016, 3, 6), (double) 6);
		
		wakeUpTimeTable.put(LocalDate.of(2016, 1, 1), (double) 6);
		wakeUpTimeTable.put(LocalDate.of(2016, 2, 1), (double) 8);
		wakeUpTimeTable.put(LocalDate.of(2016, 2, 5), (double) 6);
		wakeUpTimeTable.put(LocalDate.of(2016, 3, 2), (double) 12);
		wakeUpTimeTable.put(LocalDate.of(2016, 3, 6), (double) 5);
		
		correctData = new HashMap<>();
		correctData.put("2016-01-01", new MatchedDataPair(-14.0, 6.0));	//4
		correctData.put("2016-02-01", new MatchedDataPair(0.0, 8.0));	//3
		correctData.put("2016-02-05", new MatchedDataPair(-4.0, 6.0));  //1
		correctData.put("2016-03-02", new MatchedDataPair(5.0, 12.0));	//5
		correctData.put("2016-03-06", new MatchedDataPair(6.0, 5.0));	//2
		xData = new FakeDataSource("Temperature", "Celcius", temperatureTable);
		yData = new FakeDataSource("Wake up time","Hour", wakeUpTimeTable);
		dcBuilder = new DataCollectionBuilder(xData, yData, resolution);
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testGetResult() {
	DataCollection data = dcBuilder.getResult();
	DataCollection dataCollection = new DataCollection("Temperature / Wake up time", "Celcius", "Hour", correctData);
	assertEquals("Celcius", data.getxUnit());
	assertEquals("Hour", data.getyUnit());
	assertTrue(dataCollection.equals(data));
	assertTrue((data.getData().get("2016-01-01").equals(new MatchedDataPair(-14.0, 6.0))));
	assertTrue((data.getData().get("2016-02-01").equals(new MatchedDataPair(0.0, 8.0))));
	assertTrue((data.getData().get("2016-02-05").equals(new MatchedDataPair(-4.0, 6.0))));
	assertTrue((data.getData().get("2016-03-02").equals(new MatchedDataPair(5.0, 12.0))));
	assertTrue((data.getData().get("2016-03-06").equals(new MatchedDataPair(6.0, 5.0))));
	
	System.out.println(data.getData());
	}
	
	@Test
	public void testGetResultMonth() {
		dcBuilder = new DataCollectionBuilder(xData, yData, Resolution.MONTH);
		DataCollection data = dcBuilder.getResult();
	}

}
