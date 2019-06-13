import java.util.ArrayList;

public class Instructor {
	public String UUID;
	public String Name;
	private String Address;
	private String Phone_number;
	public boolean active=false; 
	/*eligibleCourese are Courses that the instructor is eligible to teach*/
	public ArrayList<String> eligibleCourses=new ArrayList<String>();
	
	public Instructor (String uuid, String name, String homeAddress, String phoneNum){
		this.UUID=uuid;
		this.Name=name;
		this.Address=homeAddress;
		this.Phone_number=phoneNum;
	}
	
	
	public void addCourse(String courseID){
		eligibleCourses.add(courseID);
	}



}
