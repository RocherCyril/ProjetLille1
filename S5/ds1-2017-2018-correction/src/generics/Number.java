package generics;

public class Number implements Cumulable<Number> {

	protected final int value;

	public Number(int value) {
		this.value = value;
	}

	public Number cumulate(Number other) {
		return new Number(this.value + other.value);
	}

}
