package foo;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
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
			String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
			assertTrue(response.equals(assertion));
			assertTrue(savedFile.exists());
		}
	}

}
