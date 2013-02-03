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

import foo.FileService;
import foo.ServerConstants;

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
			DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
	    	DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
	    	String command = inFromClient.readUTF();// read request
	    	if(command.startsWith(ServerConstants.pushFileCmd)){
	    		pushFileCmd(command, inFromClient, outToClient);
	    	}
	    	if (command.startsWith(ServerConstants.pullFileCmd)){
	    		pullFileCmd(command, inFromClient, outToClient);
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void pushFileCmd(String command, DataInputStream inFromClient, DataOutputStream outToClient) throws IOException{
		File savedFile = saveRemoteFile(command, inFromClient);
		String response = savedFile.getName() + "|" + new Long(savedFile.length()).toString();
    	outToClient.writeUTF(response);  // send the response
    	System.out.println(Thread.currentThread().getId() + " pushed " + savedFile.getName());
    }
    
    public void pullFileCmd(String command, DataInputStream inFromClient, DataOutputStream outToClient) throws IOException{
    		String fileName = command.replace(ServerConstants.pullFileCmd + "|", "");  // command == requestingFile|foo.jpg
    		File file = new File(ServerConstants.serverPullStuff + "/" + fileName);
    		if (!file.exists()){
    			System.out.println("Rut-ro... " + fileName + " doesn't exist.");
    		}
    		byte [] fileData = FileUtils.readFileToByteArray(file);
    		outToClient.writeUTF(fileName + "|" + fileData.length);
    		outToClient.write(fileData);
    		outToClient.flush();
    		System.out.println(Thread.currentThread().getId() + " pulled " + file.getName());
    }
    
    public File saveRemoteFile(String command, DataInputStream inFromClient) throws IOException{
    	String [] args = command.split("\\|"); // sendingFile-foo.jpg-6452
    	if (args.length != 3){
    		System.out.println("protocol error...");
    	}
    	String fileName = args[1];
    	Integer length = new Integer(args[2]);
    	File fileToSave = new File(ServerConstants.servePushStuff + "/" + fileName);
    	return FileService.saveFile(inFromClient, fileToSave, length);
    }

 }
