
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Simple Client class that receives user's input and sends it to the server for a response.
 * @author A.Elgiz
 * @version 1.0
 * @since April 20, 2020 
 *
 */
public class ClientControl {
	private PrintWriter socketOut;
	private Socket aSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private ObjectInputStream socketObjectIn;
	private ObjectOutputStream socketObjectOut;

	/**
	 * Constructs a Client with the given server name and port number
	 * @param serverName the name of the server
	 * @param portNumber the port number
	 */
	public ClientControl(String serverName, int portNumber) {
		try {
			aSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);
			
			setSocketObjectIn(new ObjectInputStream(aSocket.getInputStream()));
			setSocketObjectOut(new ObjectOutputStream(aSocket.getOutputStream()));
			
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Method communicate is responsible for creating communication between the user and the server by
	 * retrieving user's input and sending it to the server
	 */

	public void communicate()  {
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}

	public PrintWriter getSocketOut() {
		return socketOut;
	}
	
	public BufferedReader getSocketIn() {
		return socketIn;
	}

	public ObjectOutputStream getSocketObjectOut() {
		return socketObjectOut;
	}

	public void setSocketObjectOut(ObjectOutputStream socketObjectOut) {
		this.socketObjectOut = socketObjectOut;
	}

	public ObjectInputStream getSocketObjectIn() {
		return socketObjectIn;
	}

	public void setSocketObjectIn(ObjectInputStream socketObjectIn) {
		this.socketObjectIn = socketObjectIn;
	}
}
