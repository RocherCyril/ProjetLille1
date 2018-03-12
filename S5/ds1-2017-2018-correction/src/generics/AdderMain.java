package generics;

import java.util.ArrayList;
import java.util.List;

public class AdderMain {

	public static void main(String[] args) {

		Aggregator<HourDuration> aghd = new Aggregator<>();
		
		HourDuration hd1 = new HourDuration(3);
		HourDuration hd2 = new HourDuration(5);
		
		HourDuration result = aghd.aggregate(hd1, hd2);
		System.out.println("-->"+result.getHours());
		
		List<HourDuration> lhd = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			lhd.add(new HourDuration(i));
		}
		List<HourMinuteDuration> lhmd = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			lhmd.add(new HourMinuteDuration(i+1,i*5));
		}
		System.out.println("***************");
		List<HourDuration> l1 = aghd.iterAggregate(lhd,lhd);
		for(HourDuration e : l1) {
			System.out.println("-->"+e.getHours());			
		}
		System.out.println("***************");
		List<HourDuration> l2 = aghd.iterAggregate(lhd,lhmd);
		for(HourDuration e : l2) {
			System.out.println("-->"+e.getHours());			
		}
		System.out.println("***************");
		
		
		
	}

}
