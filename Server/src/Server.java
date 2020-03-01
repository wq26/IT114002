import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
	
	public static void main(String[] args) {
		int port = 3000;
		ServerSocket server;
		Socket s;
		try {
			System.out.println("Server running...");
			server = new ServerSocket(port);
			
			while(true) {
				s = server.accept();//server waits for client request
				System.out.println("Connected to client: "+s);
				
				Thread t = new Thread() {
					public void run() {
						try {
							//temporarily keeps other clients from connecting to server
							for(int i = 0; i < 20; i++) {
								Thread.sleep(1000);//This thread sleeps for a second
							}
						}

						catch(InterruptedException e) {e.printStackTrace();}
					}
				};//end thread
				t.start();
				
				
				s.close();
			}//end while
				
		} catch (IOException e) {
			e.printStackTrace();
		}//end catch
		
	}//end main

}//end Server class
