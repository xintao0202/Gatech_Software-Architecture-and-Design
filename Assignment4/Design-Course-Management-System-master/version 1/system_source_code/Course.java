/**
 * @package for this class
 */
import java.util.*;
/**
 * @author zhihuixie
 *@version 0.0.1
 *@class Course includes a construct and the following methods
 *@method createCourse creates a new course
 *@method removeCourse removes a course
 *@method getCourseAvailability check if this course is available for a certain semester
 *@method showCourseInfo shows information about this course such as course id, course title etc  
 */
public class Course {
	/**
	 * @variables
	 * courseCatalog String
	 * courseId integer
	 * courseTitle String
	 * courseDescription String
	 * semesters a list of string
	 */
	public String courseCatalog;
	public int courseId;
	public String courseTitle;
	public String courseDescription;
	public List<String> semesters;
    
	/**
	 * @constructor
	 * @param courseCatalog
	 * @param courseId
	 * @param courseTitle
	 * @param courseDescription
	 * @param semesters
	 */
	public Course(String courseCatalog, int courseId, 
			String courseTitle, String courseDescription, List<String> semesters) {
		// TODO Auto-generated constructor stub
		this.courseCatalog = courseCatalog;
		this.courseId = courseId;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.semesters = semesters;
	}

	/**
	 * @method to create a new course
	 * @param limitReach
	 * @param courseCatalog
	 * @param courseId
	 * @param courseTitle
	 * @param courseDescription
	 * @return true if course is created, false if not
	 */
	public Course createCourse(boolean limitReach, String courseCatalog, int courseId, 
			String courseTitle, String courseDescription, List<String> semesters){
		if (!limitReach){
			Course course = new Course(courseCatalog, courseId, courseTitle, 
					courseDescription, semesters);
			return course;
		}
		return null;
	}
	
	/**
	 * @method to remove a course object, not a focus in this assignment
	 * @return true if course set to null
	 */
	public boolean removeCourse(){
		this.courseCatalog = null;
		this.courseId = -1;
		this.courseTitle = null;
		this.courseDescription = null;
		return true;
	}
	
	/**
	 * @method to update course information
	 * @param courseCatalog
	 * @param courseId
	 * @param courseTitle
	 * @param courseDescription
	 * @param semester
	 * @return true if information updated, false if not
	 */
	public boolean updateCourse(String courseCatalog, int courseId, 
			String courseTitle, String courseDescription, List<String> semesters){
		boolean isUpdate = false;
		if (courseCatalog != null) {
			this.courseCatalog = courseCatalog;
			isUpdate = true;
		}
		if (courseId != -1) {
			this.courseId = courseId;
			isUpdate = true;
		}
		if (courseTitle != null) {
			this.courseTitle = courseTitle;
			isUpdate = true;
		}
		if (courseDescription != null) {
			this.courseDescription = courseDescription;
			isUpdate = true;
		}
		if (!this.semesters.isEmpty()) {
			this.semesters = semesters;
			isUpdate = true;
		}
		return isUpdate;
	}
	/**
	 * @method to find if a course available for a given semeter
	 * @param semester
	 * @return true if available, false if unavailable
	 */
	public boolean getCourseAvailability(String semester){
		if (this.semesters.contains(semester)){
			return true;
		}
		return false;
	}
	/**
	 * @method to show course information
	 */
	public void showCourseInfo(){
		try{
			System.out.println("The course catalog is " + this.courseCatalog);
			System.out.println("The course id is " + this.courseId);
			System.out.println("The course title is " + this.courseTitle);
			System.out.println("The course sescription is " + this.courseDescription);
			System.out.println("The avaliable semesters are course catalog ");
			for (String semester:this.semesters){
				System.out.println(semester);
			}
		} catch (Exception e){
			throw new Error();
		}
		System.out.println("Invalid course!!");
	}

}
