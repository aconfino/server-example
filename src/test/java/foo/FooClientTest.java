package foo;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
@Ignore
public class FooClientTest  {
	
	String folder = "src/test/resources/";
	int loopCount = 500;
	Map<String, String> map = new HashMap<String, String>();
	
	@Before
	public void createMap(){
		map.put("005_5.JPG", "005_5.JPG|413371");
		map.put("100_2791.JPG", "100_2791.JPG|2458981");
		map.put("Andrew 017.jpg", "Andrew 017.jpg|45785");
		map.put("bass.jpg", "bass.jpg|75245");
		map.put("CollegeDays.jpg", "CollegeDays.jpg|51253");
		map.put("IMG_0148.JPG", "IMG_0148.JPG|882168");
		map.put("photo(3).JPG", "photo(3).JPG|2240877");
		map.put("SANY0362.JPG", "SANY0362.JPG|96745");
		map.put("scotts run.JPG", "scotts run.JPG|232480");
		map.put("marsh_creek.pdf", "marsh_creek.pdf|353600");
		map.put("test.txt", "test.txt|24");
		map.put("IMG_0004.mp4", "IMG_0004.mp4|9708083");
	}
	
	public String getRandomKey(){
		List <String>keysAsArray = new ArrayList <String>(map.keySet());
		Random r = new Random();
		return keysAsArray.get(r.nextInt(keysAsArray.size()));
	}
	
	@Test
	public void pushRandomFileTest() throws UnknownHostException, IOException{
		for (int i = 0; i < loopCount; i++){
			String randomKey = getRandomKey();
			String fileName = randomKey;
			String assertion = map.get(randomKey);
			File file = new File(folder + fileName);
			assertTrue(file.exists());
			File savedFile = new File(ServerConstants.servePushStuff + "/" + fileName);
			String response = FooClient.remoteCommand(ServerConstants.pushFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
			assertTrue(response.equals(assertion));
			assertTrue(savedFile.exists());
		}
	}
	
	@Test
	public void pullRandomFileTest() throws UnknownHostException, IOException{
		for (int i = 0; i < loopCount; i++){
			String randomKey = getRandomKey();
			String fileName = randomKey;
			String assertion = map.get(randomKey);
			String response = FooClient.remoteCommand(ServerConstants.pullFileCmd + "|"+ fileName);
			assertTrue(response.equals(assertion));
			assertTrue(new File(fileName).exists());
		}
	}
	
	@After 
	public void cleanup(){
		for (String key : map.keySet()) {
	        File file = new File(key);
	        if (file.exists()){
	        	file.delete();
	        }
	    }
	}

}
