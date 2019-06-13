/**
 * @package for this class
 */
import java.util.List;

/**
 * @author zhihuixie
 * @version 0.0.2
 * @class Student includes a constructor and the following methods:
 * @constructor Student
 * @method enroll to enroll a student to the program
 * @method disenroll to remove a student to the program
 * @method takeCourses to count the number of courses this student taking or took
 * @method viewAcademicRecord to show this student's academic records
 * @method showStudentInfo to show this student's basic information such as student ID and Name
 */
public class Student extends User{
	/**
	 * @variable boolean isCurrentStudent true for current student in the program, false for alumni
	 */
	boolean isCurrentStudent;
    /**
     * @constructor for Student class
     * @param isCurrentStudent
     * @param uuid
     * @param name
     * @param homeAddress
     * @param phoneNumber
     */
	public Student(boolean isCurrentStudent, int uuid, String name, String homeAddress, String phoneNumber){
		super(uuid, name, homeAddress, phoneNumber);
		this.isCurrentStudent = isCurrentStudent;
	}
	
	/**
	 * @method enroll
	 * @param meetCriteria - true for student meet criteria for enrollment and false for 
	 * not meet criteria for enrollment
	 * @return true to enroll student to the program and false not to enroll student to the program
	 */
	public boolean enroll(boolean meetCriteria){
		if (meetCriteria) {
			this.isCurrentStudent = true;
			return true;
		}
		else{
			this.isCurrentStudent = false;
			return false;
		}
	}
	/**
	 * @method disenroll to remove a student from the program
	 */
	public void disenroll(){
		this.isCurrentStudent = false;
		}
	
	/**
	 * @method to get the number of courses a student taking or took
	 * @param records
	 * @return a list of Record object
	 */
	public List<Record> currentCompletedCourses(List<AcademicRecord>records){
		for (AcademicRecord record: records){
			if (record.getStudentId() == this.uuid){
				return record.records;
			}
		}
		return null;
	}
	
	/**
	 * @method to view a student's record
	 * @param records 
	 */
	public void viewAcademicRecord(List<AcademicRecord>records){
		for (AcademicRecord record: records){
			if (record.getStudentId() == this.uuid){
				record.showInfo();
			}
		}
	}
	
	/**
	 * @method to show a student's basic information
	 */
	public void showStudentInfo(){
		System.out.println("The student's ID is: " + this.uuid);
		System.out.println("The student's name is: " + this.name);
		System.out.println("The student's home address is: " + this.homeAddress);
		System.out.println("The student's phone number is: " + this.phoneNumber);
	}

}
