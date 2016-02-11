package temp;

import java.time.LocalDate;

public enum Resolution {
	YEAR {
		@Override
		public String getKey(LocalDate date) {
			return date.getYear() + "";
		}
	}, 
	MONTH {
		@Override
		public String getKey(LocalDate date) {
			return date.getYear() + " " + date.getMonth().name();
		}
	}, 
	WEEK {
		@Override
		public String getKey(LocalDate date) {
			return date.getYear() + " Week: " + date.getDayOfYear() / 52;
		}
	}, 
	DAY {
		@Override
		public String getKey(LocalDate date) {
			return date.toString();
		}	
	};
	
	public abstract String getKey(LocalDate date);

}
