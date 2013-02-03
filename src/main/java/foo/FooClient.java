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
		if (arg.startsWith(ServerConstants.pushFileCmd)){
			response = pushFile(arg, inFromServer, outToServer);
		} else if (arg.startsWith(ServerConstants.pullFileCmd)){
			File savedFile = pullFile(arg, inFromServer, outToServer);
			response = savedFile.getName() + "|" + savedFile.length();
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
			String remoteCommand = ServerConstants.pushFileCmd + "|" + file.getName() + "|" + length;
			outToServer.writeUTF(remoteCommand);  // command == sendingFile-foo.jpg-6452
			outToServer.write(fileData);
			outToServer.flush();
			String response = inFromServer.readUTF();
			return response;
	}

	protected static File pullFile(String command, DataInputStream inFromServer, DataOutputStream outToServer) throws IOException {
		outToServer.writeUTF(command);  // command == requestingFile|foo.jpg
		String response = inFromServer.readUTF();
		String [] args = response.split("\\|");
		String fileName = args[0];
		Integer length = new Integer(args[1]);
		File fileToSave = new File(fileName);
		File savedFile = FileService.saveFile(inFromServer, fileToSave, length);
		return savedFile;
	}
	
	public static void close(Socket socket) throws IOException{
		if (socket != null) {
		 socket.close();
		}
	}

}

