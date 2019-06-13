import java.util.ArrayList;

public class Instructor {
	private String UUID;
	private String Name;
	private String Address;
	private String Phone_number;
	public boolean active=false; 
	/*eligibleCourese are Courses that the instructor is eligible to teach*/
	public ArrayList<String> eligibleCourses=new ArrayList<String>();
	public Course currentTeaching=null;
	
	public Instructor (String uuid, String name, String homeAddress, String phoneNum){
		this.UUID=uuid;
		this.Name=name;
		this.Address=homeAddress;
		this.Phone_number=phoneNum;
	}
	
	public void setTeaching(Course course){
		this.currentTeaching=course;
	}
	public void addCourse(String courseID){
		eligibleCourses.add(courseID);
	}

	public String getID() {
		return UUID;
	}
	
	public String getName(){
		return Name;
	}

}
