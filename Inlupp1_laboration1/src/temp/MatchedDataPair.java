package temp;

public class MatchedDataPair {

	private Double xValue;
	private Double yValue;

	public MatchedDataPair(double xValue, double yValue) {
		this.xValue = xValue;
		this.yValue = yValue;
	}

	public double getXValue() {
		return xValue;
	}

	public double getYValue() {
		return yValue;
	}

	@Override
	public String toString() {
		return xValue + ", " + yValue;
	}
	@Override
	public boolean equals(Object m){
		return(xValue.equals(((MatchedDataPair) m).getXValue()) && yValue.equals(((MatchedDataPair) m).getYValue()));
	}

}
