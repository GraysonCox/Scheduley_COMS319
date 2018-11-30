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
		
//		FloorDAOSpringBoot fdaosp = new FloorDAOSpringBoot();
//		
//		Floor f = new Floor("floor 69", "myfiles/ilove69");
//		System.out.println(fdaosp.addFloor(f));
//		System.out.println(fdaosp.deleteFloor(677));
//		Thread.sleep(500);
//		Floor[] farr = fdaosp.getAllFloors();
//		for(Floor f1 : farr)
//		System.out.println(f1.getName()+" "+f1.getImageURL());
		
		EmailSender emailSender = new EmailSender("","");
		emailSender.setSubject("[Scheduley App] Meeting Notice: " + "TLA Arson Party");
//		emailSender.setBody((System.lineSeparator()
//				+ "Dear " + "Kim Jong Un" + "," + System.lineSeparator() + System.lineSeparator()
//				+ "A meeting has been scheduled." + System.lineSeparator() + System.lineSeparator()
//				+ "You are receiving this email because a meeting has been scheduled with you as an attendee." + System.lineSeparator()
//				+ "Details for the meeting are included below." + System.lineSeparator() + System.lineSeparator()
//				+ "Meeting: " + "Supreme Leader's Armpit Hair Style Discussion" + System.lineSeparator()
//				+ "Description: " + "Discussing the mandatory armpit hairstyle for every North Korean citizens" + System.lineSeparator()
//				+ "Date: " + "Golden Time" + System.lineSeparator()
//				+ "Time: " + "6" + ":" + "09 AM" + System.lineSeparator()
//				+ "Location: " + "Red Square" + System.lineSeparator() + System.lineSeparator()
//				+ "Contact your manager if you have any questions." + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
//				+ "NOTE: This is an automated message. DO NOT reply to this email."
//				));
		EmailHTMLBody body = new EmailHTMLBody("Thien Nguyen","TLA Arson Party","Burn this shit down to hell","2018-11-29","11:50 PM","TLA, Coover");
		emailSender.setBody(body.getHTMLBody());
		emailSender.send("thiennguyen209@gmail.com");
		
	}

}
