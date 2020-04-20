import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MultiThreadServer extends Application{
	
	private static TextArea ta = new TextArea();
	private int clientNo = 0;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("MultiThreadServer");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		new Thread( () ->  {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				ta.appendText("MultiThreadServer started at "+new Date()+"\n");
				while(true) {
					Socket socket = serverSocket.accept();
					clientNo++;
					
					Platform.runLater( () -> {
						ta.appendText("Starting thread for client "+clientNo+" at "+new Date()+"\n");
						
						InetAddress inetAddress = socket.getInetAddress();
						ta.appendText("Client "+clientNo+"'s IP Address is "+inetAddress.getHostAddress()+"\n");
					});
					new Thread(new HandleAClient(socket)).start();
				}
			}//end try
			catch(IOException e) {
				System.err.println(e);
			}
		}).start();//end thread
		
	}//end start
	
	public static void main(String args) {
		launch();
	}
	
	public static void setTa(String s) {
		ta.appendText(s);
	}
	

}//end class

class HandleAClient implements Runnable{
	
	private Socket socket;
	
	public HandleAClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			DataInputStream inputFromClient = new DataInputStream(
					socket.getInputStream()	
					);
			DataOutputStream outputToClient = new DataOutputStream(
					socket.getOutputStream());
			
			while(true) {
				String data = inputFromClient.readLine();
				outputToClient.writeChars(data);
				
				Platform.runLater(() -> {
					MultiThreadServer.setTa("Message received from client: "+data+"\n");
				});
			}//end while loop
		}//end try
		catch(IOException e) {
			e.printStackTrace();
		}
	}//end run
	
	

}//end class
