import java.util.*;
public class Course {

	
	public String courseID;
	public String courseTitle;
	public  ArrayList<Course> prereqCourses=new ArrayList<Course>();
	public ArrayList<String> offerTerm=new ArrayList<String>();
	
	
	public Course(String ID, String Title) {
		this.courseID=ID;
		this.courseTitle=Title;
	}
	

	
	public boolean isOffered(){
		boolean offered=false;
		CsvReader readCsv=new CsvReader();
		readCsv.addCourseData();
		for (Course c:readCsv.courses){
			if(c.courseID.equals(this.courseID))
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