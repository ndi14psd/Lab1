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
			return date.getYear() + " Week: " + calculateWeeks(date.getDayOfYear());
		}
	}, 
	DAY {
		@Override
		public String getKey(LocalDate date) {
			return date.toString();
		}	
	};

	public abstract String getKey(LocalDate date);

	protected int calculateWeeks(int dayOfYear) {
		int dayToCompare = 0;
		int weekNumber = 1;
		for (int i = 1; i < 53; i++) {
			dayToCompare += 7;
			if(dayOfYear <= dayToCompare){ 
				weekNumber = i;
				break;	
			}
			
		}
		return weekNumber;
	}
	
	public boolean areSame(LocalDate firstDate, LocalDate secondDate) {
		return getKey(firstDate).equals(getKey(secondDate));
	}

}