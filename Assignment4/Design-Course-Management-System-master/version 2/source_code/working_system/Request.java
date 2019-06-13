import java.util.ArrayList;
import java.util.List;


/**
 * @author zhihuixie
 * @version 0.0.1
 */
public class Request {
	int studentId;
	int courseId;
	Student student = new Student(true, this.studentId, null, null, null);
	Course course = new Course(null, this.courseId, null, null, null);
	List<Student> validRequestStudent = new ArrayList<>();
	List<Course> validRequestCourse = new ArrayList<>();
	/**
	 * constructor
	 * @param studentId
	 * @param courseId
	 */
	public Request(int studentId, int courseId){
		this.studentId = studentId;
		this.courseId = courseId;
	}
	/**
	 * @method check if the request meets the criteria for enrolling a course
	 * @param courses
	 * @param students
	 * @param records
	 * @param prereqs
	 * @return
	 */
	public int checkQualify(List<Course> courses, List<Student> students,
			                List<AcademicRecord> records, List<Prereqs> prereqs){
		AcademicRecord record = new AcademicRecord(studentId);
		Prereqs prep = new Prereqs(courseId);
		//find student
		student = searchStudent(students);
		
		// find record for this student in academic records database
		for (AcademicRecord rec: records){
			if (rec.getStudentId() == this.studentId){
				record = rec;
			}
		}
		// find course in courses database
		course = searchCourse(courses);
		// find precourse in prereqss database
		for (Prereqs pre: prereqs){
			if (pre.courseId == this.courseId){
				prep = pre;
			}
		}

		List<String> performances = new ArrayList<>();
		performances.add("A");
		performances.add("B");
		performances.add("C");
		// check prereqs
		if (!prep.getPreps().isEmpty() && !record.getCourses().containsAll(prep.getPreps())){
			return -1;
		}
		// check courses that have been taken already
		if (record.getCourses().contains(this.courseId)){
			for(Record courseRecord : record.records){
				if (performances.contains(courseRecord.getPerformance())){
					return -2;
				}
			}
		}
		// check seats available
		if (course.courseSeats < 1){
			return -3;
		}
		// if the request is valid, return 0 and reduce courseSeats number
		course.courseSeats = course.courseSeats - 1;
		validRequestStudent.add(student);
		validRequestCourse.add(course);
		return 0;
			
	}
	
	/**
	 * @method search student in the system database
	 * @param students
	 * @return
	 */
	public Student searchStudent(List<Student> students){
		// find student in students database
		for (Student stu: students){
			if (stu.uuid == this.studentId){
				student = stu;
			}
		}
		return student;
	}
	
	/**
	 * search student course in the course database
	 * @param courses
	 * @return
	 */
	public Course searchCourse(List<Course> courses){
		// find course in courses database
		for (Course cou: courses){
			if (cou.courseId == this.courseId){
				course = cou;
			}
		}
		return course;
	}
	
	/**
	 * @method display request information
	 */
	public void displayRequests(){
		for (int i = 0; i < validRequestStudent.size(); i++){
			student = validRequestStudent.get(i);
			course = validRequestCourse.get(i);
			try {
				System.out.println(this.studentId + ", " + student.name + ", " + 
		                   this.courseId + ", "+course.courseTitle);
			}
			catch (Exception e){
				System.out.println("No valid requests!!");
				throw new Error();
			}
		}
	}

}
