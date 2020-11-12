import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 * Class ServerModel is responsible for making the connection between socket and the program
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
 
public class ServerModel implements Runnable {
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private CourseCatalogue catalogue;
	private Registration registration;
	private Student aStudent;
	
	private ObjectInputStream socketObjectIn;
	private ObjectOutputStream socketObjectOut;
	
	public ServerModel (BufferedReader in, PrintWriter out) {
		socketInput = in;
		socketOutput = out;
		catalogue = new CourseCatalogue();

	}
	
	public void setObjectStreams(ObjectInputStream objectIn, ObjectOutputStream objectOut)
	{
		socketObjectOut = objectOut;
		socketObjectIn  = objectIn;
	}
	
	/**
	 * Getting input from the client
	 * @throws IOException
	 */
	
	public void communicate () throws IOException {
		String line = null;
		String response = null;
		
		while (true) {
			line = socketInput.readLine();
			
			System.out.println("Server has recieved line: " + line);
			
			response = serverMenu(line.charAt(0),line);
			socketOutput.print(response + "\0");
			socketOutput.flush();
		}
	
	}
	
	/**
	 * ServerMenu is responsible for the back end of the implementations of each button
	 * @param instruction is the case #
	 * @param line is the coursename, coursenum and secnum 
	 * @return 
	 */
	
	public String serverMenu (char instruction, String line) {
		String [] split = line.split(":");
		
		int choice = Character.getNumericValue(instruction);
		
		System.out.println("Server has seen instruction: " + instruction);
		System.out.println("Server is doing choice: " + choice);
		
		
		while (true) {
			switch (choice) {
			
			case 1:
				return searchCourse (split[1], split[2]);
			case 2:
				String test = (addCourse (split[1], split[2], split [3]));
				System.out.println(test);
				return test;
			case 3:
				return removeCourse (split[1], split [2]);
			default:
				return null;
			
			}
			
		}
		
	}
	
	/**
	 * SearchCourse is going to search through the course list and return if the course is found
	 * @param courseName is the name of the course
	 * @param courseNum is the number of the course
	 * @return returns the course
	 */
	
	public String searchCourse (String courseName, String courseNum) {
		Course course = catalogue.searchCat(courseName, Integer.parseInt(courseNum));
		
		if (course == null) {
			return "Course " + courseName + courseNum + " was not found";
		}
		else {
			return course.toString();
		}	
	}
	
	/**
	 * AddCourse is responsible for adding the course to the students course list
	 * @param courseName is the name of the course
	 * @param courseNum is the number of the course
	 * @param secNum is the section number
	 * @return returns the student reg list
	 */
	 
	public String addCourse (String courseName, String courseNum, String secNum) {
		Course course = catalogue.searchCat(courseName, Integer.parseInt(courseNum));
		try {
			aStudent = (Student)socketObjectIn.readObject();
			registration = new Registration(aStudent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (course == null)
			return "Unable to add a course";
		else {
			CourseOffering courseOff = course.getCourseOfferingAt(Integer.parseInt(secNum)-1);
			if(courseOff !=null) {
				registration.completeRegistration(aStudent, courseOff);
				System.out.println(getaStudent().getStudentRegList().toString());
				return aStudent.getStudentRegList().toString();
			}
			else
				return "Unable to add a course";
		}
		
	}
	
	public Student updateStudent() {
		Student received=null;
		try{
			received = (Student)socketObjectIn.readObject();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return received;
	}
	
	/**
	 * RemoveCourse is responsible for removing a course
	 * @param courseName is the name of the course to be removed
	 * @param courseNum is the number of the course to be removed
	 * @return
	 */
	 
	public String removeCourse (String courseName, String courseNum) {
		Course course = catalogue.searchCat(courseName, Integer.parseInt(courseNum));
		if (course == null)
			return "Course " + courseName + courseNum + " was not found";
		else 
			return "\0";
	}
	
	public void setaStudent(Student theStudent) {
		 aStudent = theStudent;
	}
	
	public Student getaStudent() {
		return aStudent;
	}
	
	public String getCourseCatalogue() {
		return catalogue.toString();
	}
	
	@Override
	public void run() {
		try {
			communicate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
