import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple Server class that receives user's input and sends it to the server for a response.
 * @author A. Elgiz
 * @version 1.0
 * @since April 20, 2020
 *
 */
 
public class ServerControl {
	private BufferedReader socketInput;
	private PrintWriter socketOutput;
	private ServerSocket serverSocket;
	private Socket aSocket;
	private ExecutorService pool;
	
	/**
	 * Construct a Server with Port 9090
	 */
	 
	public ServerControl() {
		try {
			System.out.println("Creating server.");
			serverSocket = new ServerSocket(9090);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void runServer() {
		try {
			while (true) {
				aSocket = serverSocket.accept();
				System.out.println("Client accepted.");
				socketInput = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
				socketOutput = new PrintWriter(aSocket.getOutputStream(), true);
				ServerModel sm = new ServerModel(socketInput, socketOutput);
				
				ObjectOutputStream objectOut = new ObjectOutputStream(aSocket.getOutputStream());
				ObjectInputStream  objectIn = new ObjectInputStream(aSocket.getInputStream());
				sm.setObjectStreams(objectIn, objectOut);
				pool.execute(sm);
			}
		}catch (IOException e) {
			e.getStackTrace();
		}
		closeConnection();
	}

	public void closeConnection() {
		try {
			socketInput.close();
			socketOutput.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Run the Server.
	 * 
	 * @param args
	 * @throws IOException
	 */
	 
	public static void main(String[] args) throws IOException {
		ServerControl serverControl = new ServerControl();
		serverControl.runServer();
	}

}
