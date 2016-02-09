package temp;

import java.time.LocalDate;
import java.util.Map;

public class FakeDataSource implements DataSource {
	
	private String name;
	private String unit;
	private Map<LocalDate, Double> data;
	
	public FakeDataSource(String name, String unit, Map<LocalDate, Double> data) {
		this.name = name;
		this.unit = unit;
		this.data = data;
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getUnit() {
		return unit;
	}

	@Override
	public Map<LocalDate, Double> getData() {
		return data;
	}

}
