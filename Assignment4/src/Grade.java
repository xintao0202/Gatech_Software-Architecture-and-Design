
public class Grade {

	public Student student;
	public Course course;
	public String grade;
	public Instructor instructor;
	public String comments;
	public Term term;

	
	public Grade (Student student, Course course,String grade, Instructor instructor, String comments, Term term){
		this.student=student;
		this.course=course;
		this.instructor=instructor;
		this.grade=grade;
		this.comments=comments;
		this.term=term;
	}
	
	 
	
	public boolean isPassing(){
		return (grade.equals("A") ||grade.equals("B")|| grade.equals("C") );
	}
	
}
