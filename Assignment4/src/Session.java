import java.util.ArrayList;

public class Session {
	public Term term;
	public Course course;
	public Instructor instructor;
	public  int availableSeats=0;
	public ArrayList<Student> enrolledStudent=new ArrayList<Student>();
	
	public Session (Term term, Course course, Instructor instructor){
	this.term=term;
	this.course=course;
	this.instructor=instructor;
	}
}