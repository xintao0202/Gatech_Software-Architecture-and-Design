import java.util.ArrayList;
import java.util.List;

public class CourseRequest {
	Student student;
	Course course;
	Term term;
	
	public CourseRequest(Student student, Course course, Term term){
		this.student=student;
		this.course=course;
		this.term=term;
	}
	
	public boolean passPrereq(Student student, Course course){
		boolean pass=true;
		for(Course c: course.prereqCourses)
			if(student.passCourse(c)==false)
				pass=false;
		return pass;
	}
		
	
}
