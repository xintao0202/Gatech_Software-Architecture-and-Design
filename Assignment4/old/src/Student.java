import java.util.ArrayList;

public class Student {
	public String UUID;
	public String Name;
	public String Address;
	public String PhoneNumber;
	public ArrayList<Grade> academicRecord=new ArrayList<Grade>();
	public ArrayList<String> enrolledCourses=new ArrayList<String>();
	
	public Student (String uuid, String name, String homeAddress, String phoneNum){
		this.UUID=uuid;
		this.Name=name;
		this.Address=homeAddress;
		this.PhoneNumber=phoneNum;
	}
	
	public String getID(){
		return UUID;
	}
	
	public void addGrade(Grade grade){
		academicRecord.add(grade);
	}

	public boolean passCourse(Course course){
		boolean pass=false;
		for (Grade g:academicRecord)
			if(g.course.equals(course))
				if (g.isPassing()==true)
					pass=true;
		return pass;
	}
	
	public void addEnrollment(String courseID){
		enrolledCourses.add(courseID);
	}
}
