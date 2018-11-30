package main;

public class BSmain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
//		MeetingSpaceDAOSpringBoot msdaosp = new MeetingSpaceDAOSpringBoot();
//		MeetingSpace s = new MeetingSpace("Dean Office", 30, 50, 100, 40, 1);
//		System.out.println(msdaosp.addMeetingSpace(s));
//		System.out.println(msdaosp.deleteMeetingSpace(30, 50, 1));
//		MeetingSpace[] arr = msdaosp.getAllMeetingSpace();
//		
//		for(MeetingSpace ms : arr) {
//			System.out.println(ms.getName());
//			System.out.println(ms.getX());
//			System.out.println(ms.getY());
//			System.out.println(ms.getHeight());
//			System.out.println(ms.getWidth());
//			System.out.println(ms.getFloorID());
//		}
		
		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
		
		Floor f = new Floor("floor 69", "myfiles/ilove69");
//		System.out.println(fdaosp.addFloor(f));
		System.out.println(fdaosp.deleteFloor(677));
		Thread.sleep(500);
		Floor[] farr = fdaosp.getAllFloors();
		for(Floor f1 : farr)
		System.out.println(f1.getName()+" "+f1.getImageURL());
		
	}

}
