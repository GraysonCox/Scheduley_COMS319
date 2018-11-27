package main;

public class BSmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MeetingSpaceDAOSpringBoot msdaosp = new MeetingSpaceDAOSpringBoot();
		MeetingSpace[] arr = msdaosp.getAllMeetingSpace();
		
		for(MeetingSpace ms : arr) {
			System.out.println(ms.getName());
			System.out.println(ms.getX());
			System.out.println(ms.getY());
			System.out.println(ms.getHeight());
			System.out.println(ms.getWidth());
			System.out.println(ms.getFloor());
		}
	}

}
