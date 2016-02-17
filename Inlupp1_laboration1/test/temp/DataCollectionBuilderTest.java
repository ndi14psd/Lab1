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
	DataSource xData;
	DataSource yData;

	@Before
	public void setUp() throws Exception {
		Map<LocalDate, Double> temperature = new HashMap<>();
		Map<LocalDate, Double> wakeUpTime = new HashMap<>();

		temperature.put(LocalDate.of(2016, 1, 1), (double) -14);
		temperature.put(LocalDate.of(2016, 1, 9), (double) 2);
		temperature.put(LocalDate.of(2016, 2, 1), (double) 0);
		temperature.put(LocalDate.of(2016, 2, 5), (double) -4);
		temperature.put(LocalDate.of(2016, 2, 15), (double) -5);
		temperature.put(LocalDate.of(2016, 3, 2), (double) 5);
		temperature.put(LocalDate.of(2016, 3, 6), (double) 6);

		temperature.put(LocalDate.of(2005, 2, 6), (double) 6);
		temperature.put(LocalDate.of(2005, 3, 6), (double) -4);
		
		wakeUpTime.put(LocalDate.of(2005, 5, 1), (double) 6);
		wakeUpTime.put(LocalDate.of(2005, 1, 1), (double) 7);

		temperature.put(LocalDate.of(1989, 12, 30), (double) 6);
		temperature.put(LocalDate.of(1989, 12, 20), (double) -4);
		
		wakeUpTime.put(LocalDate.of(1989, 12, 29), (double) 6);
		wakeUpTime.put(LocalDate.of(1989, 12, 21), (double) 7);

		wakeUpTime.put(LocalDate.of(2016, 1, 1), (double) 6);
		wakeUpTime.put(LocalDate.of(2016, 2, 1), (double) 8);
		wakeUpTime.put(LocalDate.of(2016, 2, 5), (double) 6);
		wakeUpTime.put(LocalDate.of(2016, 3, 2), (double) 12);
		wakeUpTime.put(LocalDate.of(2016, 3, 6), (double) 5);
		xData = new FakeDataSource("Temperature", "Celcius", temperature);
		yData = new FakeDataSource("Wake up time","Hour", wakeUpTime);
	}

	private Map<String, MatchedDataPair> createCorrectDayData() {
		Map<String, MatchedDataPair> correctDataDay = new HashMap<>();
		correctDataDay.put("2016-01-01", new MatchedDataPair(-14.0, 6.0));
		correctDataDay.put("2016-02-01", new MatchedDataPair(0.0, 8.0));
		correctDataDay.put("2016-02-05", new MatchedDataPair(-4.0, 6.0));  
		correctDataDay.put("2016-03-02", new MatchedDataPair(5.0, 12.0));	
		correctDataDay.put("2016-03-06", new MatchedDataPair(6.0, 5.0));
		return correctDataDay;
	}
	private Map<String, MatchedDataPair> createCorrectWeekData() {
		Map<String, MatchedDataPair> correctDataWeek = new HashMap<>();
		correctDataWeek.put("2016 Week: 6", new MatchedDataPair(-4.0, 6.0));
		correctDataWeek.put("2016 Week: 5", new MatchedDataPair(0.0, 8.0));
		correctDataWeek.put("2016 Week: 1", new MatchedDataPair(-14.0, 6.0));
		correctDataWeek.put("1989 Week: 51", new MatchedDataPair(-4.0, 7.0));
		correctDataWeek.put("2016 Week: 10", new MatchedDataPair(6.0, 5.0));
		correctDataWeek.put("1989 Week: 52", new MatchedDataPair(6.0, 6.0));
		correctDataWeek.put("2016 Week: 9", new MatchedDataPair(5.0, 12.0));
		return correctDataWeek;
	}
	private Map<String, MatchedDataPair> createCorrectMonthData() {
		Map<String, MatchedDataPair> correctDataMonth = new HashMap<>();
		correctDataMonth.put("2016 JANUARY", new MatchedDataPair(-6.0, 6.0));
		correctDataMonth.put("2016 FEBRUARY", new MatchedDataPair(-3, 7.0));
		correctDataMonth.put("2016 MARCH", new MatchedDataPair(5.5, 8.5));
		correctDataMonth.put("1989 DECEMBER", new MatchedDataPair(1.0, 6.5));
		return correctDataMonth;
	}
	private Map<String, MatchedDataPair> createCorrectYearData() {
		Map<String, MatchedDataPair> correctDataYear = new HashMap<>();
		correctDataYear.put("2016", new MatchedDataPair(-1.43, 7.4));
		correctDataYear.put("2005", new MatchedDataPair(1.0, 6.5));
		correctDataYear.put("1989", new MatchedDataPair(1.0, 6.5));
		return correctDataYear;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetResultDay() {
		assertTrue(testGetResult(Resolution.DAY, createCorrectDayData()));
	}

	@Test
	public void testGetResultWeek() {
		assertTrue(testGetResult(Resolution.WEEK, createCorrectWeekData()));
	}

	@Test
	public void testGetResultMonth() {
		assertTrue(testGetResult(Resolution.MONTH, createCorrectMonthData()));
	}

	@Test
	public void testGetResultYear() {
		assertTrue(testGetResult(Resolution.YEAR, createCorrectYearData()));
	}
	public boolean testGetResult(Resolution resolution, Map<String, MatchedDataPair> correctResData) {
		dcBuilder = new DataCollectionBuilder(xData, yData, resolution);
		DataCollection matchedData = dcBuilder.getResult();
		DataCollection correctCollection = new DataCollection("Temperature / Wake up time", "Celcius", "Hour", correctResData);
		System.out.println("Comparing builder made data: \n" + matchedData.getData() + "\nWith correct data: \n" + correctCollection.getData() + "\nEND \n");
		return (correctCollection.equals(matchedData));
	}
}
