package foo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.FileUtils;

public class FooServer {

	static ServerSocket  serverSocket = null;
	static boolean listening = true;
	
	public static void main(String args[]) throws UnknownHostException, IOException {
    	setupTestData();
		serverSocket = new ServerSocket(9000);
		System.out.println("listening on 9000");
		while (listening){
	        new MultiServerThread(serverSocket.accept()).start();
		}
	    serverSocket.close();
	}
	
	public static void setupTestData() throws IOException{
    	File pushFolder = new File(ServerConstants.servePushStuff);
    	File pullFolder = new File(ServerConstants.serverPullStuff);
    	if (!pushFolder.exists()){
    		pushFolder.mkdirs();
    	}
    	if (!pullFolder.exists()){
    		pullFolder.mkdirs();
    		File testFiles = new File("src/test/resources/");
    		FileUtils.copyDirectory(testFiles, pullFolder);
    	}
    }
}

 class MultiServerThread extends Thread {
    private Socket socket = null;
 
    public MultiServerThread(Socket socket) {
    	this.socket = socket;
    }
 
    public void run() {
    	try {
    		System.out.println("accepting connection from client:  " + socket.getRemoteSocketAddress());
			DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
	    	DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
	    	String command = inFromClient.readUTF();// read request
	    	if(command.startsWith(ServerConstants.sendingFileCmd)){
	    		File savedFile = saveRemoteFile(command, inFromClient);
	    		sendResponse(savedFile, outToClient);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public File saveRemoteFile(String command, DataInputStream inFromClient) throws IOException{
    	String [] args = command.split("\\|"); // sendingFile-foo.jpg-6452
    	if (args.length != 3){
    		System.out.println("protocol error...");
    	}
    	String fileName = args[1];
    	Integer length = new Integer(args[2]);
    	File fileToSave = new File(fileName);
    	return saveFile(inFromClient, fileToSave, length);
    }
    
    public File saveFile(DataInputStream inFromClient, File fileToSave, Integer length) throws IOException{
    	FileOutputStream fileOutputStream = new FileOutputStream(ServerConstants.servePushStuff + "/" + fileToSave);
    	byte [] fileData = new byte[length];  // get the length to read from the client
    	inFromClient.readFully(fileData);  // read the bytes into the array
    	fileOutputStream.write(fileData);  // write the bytes to the file
    	fileOutputStream.flush();
    	fileOutputStream.close();
		File savedFile = new File(ServerConstants.servePushStuff + "/" + fileToSave);
		System.out.println(savedFile.getAbsolutePath());
		return savedFile;
    }
    
    public void sendResponse(File file, DataOutputStream outToClient) throws IOException{
    	String response = file.getName() + "|" + new Long(file.length()).toString();
    	outToClient.writeUTF(response);
    }
 }
