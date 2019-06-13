/**
 * @package for this class
 */
import java.util.*;
/**
 * @author zhihuixie
 * @version 0.0.1
 * @class Instructor includes a constructor and the following methods:
 * @method manageAcademicRecord to add new record for students
 * @method getTeachCourses to get courses this instructor taught
 */
public class Instructor extends User {
	/**
	 * @variable teachCourses a list of courseId this instructor taught
	 */
	public List<Integer> teachCourses;
	/**
	 * @construct for the Instructor class 
	 * @param uuid
	 * @param name
	 * @param homeAddress
	 * @param phoneNumber
	 */
	public Instructor(int uuid, String name, String homeAddress, 
			String phoneNumber) {
		// TODO Auto-generated constructor stub
		super(uuid, name, homeAddress, phoneNumber);
		this.teachCourses = new ArrayList<Integer>();
	}
	
	/**
	 * @method for instructor to add academic records for a student
	 * @param studentId
	 * @param instructorId
	 * @param courseId
	 * @param performance
	 * @param performanceComments
	 */
	public void manageAcademicRecord(int studentId, int instructorId, 
			                  int courseId, String performance, String performanceComments){
		AcademicRecord record = new AcademicRecord (studentId);
		record.enterNewAcademicRecord(instructorId, courseId, performance, performanceComments);
	}
	
    public void getTeachCourses(List<AcademicRecord> aRecords){
    	for (AcademicRecord record: aRecords){
    		for (Record re: record.records){
    			if (re.instructorId == this.uuid){
    				this.teachCourses.add(re.courseId);
    			}
    		}
    	}
    }
}
