package generics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Aggregator<T extends Cumulable<T>> {

	// McCabe = 1
	public T aggregate(T first, T second) {
		return first.cumulate(second);
	}

	// McCabe = 3
	public T aggregateAll(List<T> elements) throws RuntimeException {
		if (elements.isEmpty())
			throw new RuntimeException();
		Iterator<T> it_elements = elements.iterator();
		T result = it_elements.next();
		while (it_elements.hasNext()) {
			result = aggregate(result, it_elements.next());
		}
		return result;
	}

	// McCabe = 3
	public List<T> iterAggregate(List<T> firsts, List<? extends T> seconds) {
		if (firsts.size() != seconds.size())
			throw new RuntimeException();
		List<T> result = new ArrayList<>();
		Iterator<? extends T> it_seconds = seconds.iterator();
		for (T e : firsts) {
			result.add(e.cumulate(it_seconds.next()));
		}
		return result;
	}

}
