package generics;

public class HourMinuteDuration extends HourDuration {

	protected final int minutes;

	/**
	 * @return the minutes
	 */
	public int getMinutes() {
		return this.minutes;
	}

	public HourMinuteDuration(int hours, int minutes) {
		super(hours);
		this.minutes = minutes;
	}

	public HourMinuteDuration cumulate(HourMinuteDuration other) {
		return new HourMinuteDuration(this.hours + other.hours, this.minutes + other.minutes);
	}

}
