/**
 * @package for this class
 */
import java.util.*;
/**
 * @author zhihuixie
 * @version 0.0.2
 * @class AcademicRecord includes the following methods
 * @method showInfo
 * @method enterNewAcademicRecord
 * @method getCourses
 * @method getStudentId
 */
public class AcademicRecord {
	/**
	 * @variables
	 * uuid - student's id
	 * records - student's records
	 */
	public int uuid;
	public List<Record> records;

    /**
     * @construct for AcademicRecord class
     * @param uuid
     */
	public AcademicRecord(int uuid) {
		// TODO Auto-generated constructor stub
		this.uuid = uuid;
		this.records = new ArrayList<Record>();
	}

	/**
	 * @method showInfo shows the information of a student's records
	 */
	public void showInfo() {
		// TODO Auto-generated method stub
		try {
			
			for (Record record: records){
				System.out.println(this.uuid + ", " + record.courseId + ", " + record.instructorId 
						+ ", " + record.performanceComments + ", " + record.performance);
				//record.describe();
			}
			
		}catch(Exception e){
			    System.out.println("No record");
				throw new Error();
			}
	}

   /**
    * @method to add a new record to student
    * @param instructorID
    * @param courseID
    * @param performance
    * @param performanceComments
    */
	public void enterNewAcademicRecord(int instructorId, int courseId, 
			String performance, String performanceComments) {
		// TODO Auto-generated method stub
		Record record = new Record(instructorId, courseId, 
				            performance, performanceComments);
		this.records.add(record);
	}
    
	/**
	 * @method getCourses to get number of courses a student taking or took
	 * @return integer number of courses
	 */
	public List<Integer> getCourses() {
		// TODO Auto-generated method stub
		List<Integer> courseIds = new ArrayList<>();
		for (Record record: this.records){
			courseIds.add(record.courseId);
		}
		return courseIds;
	}
   /** 
    * @method getStudentId
    * @return this student's ID
    */
	public int getStudentId() {
		// TODO Auto-generated method stub
		return this.uuid;
	}

}
