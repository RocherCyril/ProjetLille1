package generics;

public class HourDuration implements Cumulable<HourDuration> {

	protected final int hours;

	public HourDuration(int hours) {
		this.hours = hours;
	}

	public HourDuration cumulate(HourDuration other) {
		return new HourDuration(this.hours + other.hours);
	}

	/**
	 * @return the hours
	 */
	public int getHours() {
		return this.hours;
	}

}
