import java.io.Serializable;
import java.util.ArrayList;
/**
 * Class Student is responsible for creating a user student
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private String studentName;
	private int studentId;
	private ArrayList<Registration> studentRegList;
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public ArrayList<Registration> getStudentRegList() {
		return studentRegList;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}
	
	/**
	 * Removes registration from students reg list
	 * @param registration is the registration that is going  to be removed
	 */
	 
	public void removeRegistration (Registration registration) {
		studentRegList.remove(registration);
	}
	
	/**
	 * Adds registration to the student reg list
	 * @param registration is the registration that is going to be added
	 */
	 
	public void addRegistration(Registration registration) {
		studentRegList.add(registration);
	}

}
