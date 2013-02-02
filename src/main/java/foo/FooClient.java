package foo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;

public class FooClient {

	public static String remoteCommand(String arg) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 9000);
		DataInputStream inFromServer = new DataInputStream(socket.getInputStream());
		DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
		String response = "";
		if (arg.startsWith(ServerConstants.sendingFileCmd)){
			response = pushFile(arg, inFromServer, outToServer);
		} else if (arg.startsWith(ServerConstants.requestingFileCmd)){
			pullFile(arg, inFromServer, outToServer);
		}
		close(socket);
		return response;
	}

	protected static String pushFile(String arg, DataInputStream inFromServer, DataOutputStream outToServer) throws IOException {		
			String [] args = arg.split("\\|");
			String fileName = args[1];
			String length = args[2];
			File file = new File(fileName);
			byte [] fileData = FileUtils.readFileToByteArray(file);
			String remoteCommand = ServerConstants.sendingFileCmd + "|" + file.getName() + "|" + length;
			outToServer.writeUTF(remoteCommand);  // command == sendingFile-foo.jpg-6452
			writeToOutputStream(fileData, outToServer); // writing data
			String response = inFromServer.readUTF();
			return response;
	}

	protected static void pullFile(String command, DataInputStream inFromServer, DataOutputStream outToServer) {

	}
	
	public static void writeToOutputStream(byte [] data, DataOutputStream outToServer) throws IOException{
			outToServer.write(data);
			outToServer.flush();
	}
	
	protected static void readFromInputStream(DataInputStream inFromServer){
		
	}
	
	public static void close(Socket socket) throws IOException{
		if (socket != null) {
		 socket.close();
		}
	}

}

