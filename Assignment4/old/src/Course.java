import java.util.*;
public class Course {

	private String courseID;
	private String courseTitle;
	public  int availableSeats=0;
	public  ArrayList<Course> prereqCourses=new ArrayList<Course>();
	
	
	
	public Course(String ID, String Title) {
		this.courseID=ID;
		this.courseTitle=Title;
	}
	
	public void addSeats(int num){
		this.availableSeats+=num;
	}
	
	public void removeSeats(int num){
		this.availableSeats-=num;
	}
	
	public String getID() {
		return courseID;
	}
	
	public String getTitle() {
		return courseTitle;
	}
	
	public boolean isOffered(){
		boolean offered=false;
		CsvReader readCsv=new CsvReader();
		readCsv.addCourseData();
		for (Course c:readCsv.courses){
			if(c.getID().equals(this.courseID))
				{
				offered=true;
				break;
				}
	}
		return offered;

	}

	public void addPrereq(Course course) {
		prereqCourses.add(course);
	}
}