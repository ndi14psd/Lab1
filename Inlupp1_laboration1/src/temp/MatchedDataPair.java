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
	public boolean equals(Object obj){
		return xValue.equals(((MatchedDataPair) obj).getXValue()) && yValue.equals(((MatchedDataPair) obj).getYValue());
	}

}
