import java.util.ArrayList;

public class Term {

	private String semester;
	private int year;
	public ArrayList<String> HiredInstructor=new ArrayList<String>();
	
	public Term(String semester, int year){
		this.semester=semester;
		this.year=year;
	}
	
	public void hire(String ID){
		HiredInstructor.add(ID);
	}
	
	public void leave(String ID){
		HiredInstructor.remove(ID);
	}
	
	public String getSemester(){
		return semester;
	}
	
	public int getYear(){
		return year;
	}
	
	public Term next(){
		int next_year=year;
		String next_sem=semester;
		if(semester.equals("Fall")){
			 next_sem="Winter";							
		}
		else if (semester.equals("Winter")){
			 next_sem="Spring";
		}
		else if (semester.equals("Spring")){
			 next_sem="Summer";
		}
		else if (semester.equals("Summer")){
			 next_sem="Fall";
			 next_year=year+1;
		}
		Term next_term=new Term(next_sem, next_year);
		next_term.HiredInstructor=this.HiredInstructor;
		return next_term;
	}
	
}
