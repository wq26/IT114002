import java.net.Socket;

public class Client {
	
	public static void main(String[] args) {
		int port = 3000;
		String IPAddress = "127.0.0.1";
		System.out.println("Connecting to server...");
		try {
			Socket s = new Socket(IPAddress,port);
			System.out.println("Connected");
		}catch(Exception e) {}
		
	}//end main

}//end Client class
