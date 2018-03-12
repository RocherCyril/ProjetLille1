package generics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AggregatorTest {

	private static final int defaultListSize = 10;
	private Aggregator<HourDuration> hourAggregator;

	@Before
	public void init() {
		hourAggregator = new Aggregator<>();
	}

	@Test
	public void aggregateTwoHourDurationsShouldWork() {
		HourDuration hd1 = new HourDuration(3);
		HourDuration hd2 = new HourDuration(5);

		assertHourDurations(8, hourAggregator.aggregate(hd1, hd2));
	}

	private static final void assertHourDurations(int expected, HourDuration result) {
		assertEquals(expected, result.getHours());
	}

	@Test
	public void aggregateHourAndHourMinuteDurationsShouldWork() {
		HourDuration hd1 = new HourDuration(3);
		HourMinuteDuration hmd = new HourMinuteDuration(6, 3);

		assertHourDurations(9, hourAggregator.aggregate(hd1, hmd));
	}

	@Test
	public void iterAggregateWithSameTypeElementShouldWork() {
		List<HourDuration> lhd = buildListofHourDurations(defaultListSize);

		List<HourDuration> result = hourAggregator.iterAggregate(lhd, lhd);

		assertEquals(lhd.size(), result.size());
		for (int i = 0; i < result.size(); i++)
			assertHourDurations(2 * i, result.get(i));
	}

	private static final List<HourDuration> buildListofHourDurations(int size) {
		List<HourDuration> lhd = new ArrayList<>();
		for (int i = 0; i < size; i++)
			lhd.add(new HourDuration(i));
		return lhd;
	}

	@Test(expected = RuntimeException.class)
	public void iterAggregateWithEmptyListShouldFail() {
		List<HourDuration> lhd = buildListofHourDurations(1);

		hourAggregator.iterAggregate(lhd, buildListofHourDurations(2));
		fail("Aggregating different lists should fail");
	}

	@Test
	public void iterAggregateWithSubtypeElement() {
		List<HourDuration> lhd = buildListofHourDurations(defaultListSize);
		List<HourMinuteDuration> lhmd = new ArrayList<>();
		for (int i = 0; i < defaultListSize; i++)
			lhmd.add(new HourMinuteDuration(i + 1, i * 5));

		List<HourDuration> result = hourAggregator.iterAggregate(lhd, lhmd);

		assertEquals(lhd.size(), result.size());
		for (int i = 0; i < result.size(); i++)
			assertHourDurations(2 * i + 1, result.get(i));

	}

	@Test(expected = RuntimeException.class)
	public void aggregateAllWithEmptyListShouldFail() {
		List<HourDuration> lhd = buildListofHourDurations(0);

		hourAggregator.aggregateAll(lhd);
		fail("Aggregating empty lists should fail");
	}

	@Test
	public void aggregateAllWithListShouldWork() {
		List<HourDuration> lhd = buildListofHourDurations(11);

		assertHourDurations(55, hourAggregator.aggregateAll(lhd)); // (10*11)/2
	}
}
