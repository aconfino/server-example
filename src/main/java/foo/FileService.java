package foo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileService {
	
    public static File saveFile(DataInputStream inputDataStream, File fileToSave, Integer length) throws IOException{
    	FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
    	byte [] fileData = new byte[length];  // get the length to read from input stream
    	inputDataStream.readFully(fileData);  // read the bytes into the array
    	fileOutputStream.write(fileData);  // write the bytes to the file
    	fileOutputStream.flush();
    	fileOutputStream.close();
		return fileToSave;
    }

}
