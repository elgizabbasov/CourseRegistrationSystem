/**
 * Class Registration is responsible for registering a student to a course
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
 
public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
	
	public Registration (Student aStudent) {
		theStudent = aStudent;
	}
	
	/**
	 * CompleteRegistration is responsible for registering a student to a course using a helper function addRegistration
	 * @param st is the student
	 * @param of is the course offering
	 */
	 
	void completeRegistration (Student st, CourseOffering of) {
		theStudent = st;
		theOffering = of;
		addRegistration ();
	}
	
	/**
	 * addRegistration is responsible for registering a student in a course
	 */
	 
	private void addRegistration () {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	public Student getTheStudent() {
		return theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString () {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
		
	}
}
