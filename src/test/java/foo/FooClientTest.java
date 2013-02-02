package foo;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FooClientTest  {
	
	String folder = "src/test/resources/";
	
	@Test
	public void pushFileTest() throws UnknownHostException, IOException{
		File file = new File(folder + "005_5.JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("005_5.JPG|413371"));
	}
	
	@Test
	public void pushFileTest2() throws UnknownHostException, IOException{
		File file = new File(folder + "100_2791.JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("100_2791.JPG|2458981"));
	}
	
	@Test
	public void pushFileTest3() throws UnknownHostException, IOException{
		File file = new File(folder + "Andrew 017.jpg");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("Andrew 017.jpg|45785"));
	}
	
	@Test
	public void pushFileTest4() throws UnknownHostException, IOException{
		File file = new File(folder + "bass.jpg");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("bass.jpg|75245"));
	}
	
	@Test
	public void pushFileTest5() throws UnknownHostException, IOException{
		File file = new File(folder + "CollegeDays.jpg");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("CollegeDays.jpg|51253"));
	}
	
	@Test
	public void pushFileTest6() throws UnknownHostException, IOException{
		File file = new File(folder + "IMG_0148.JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("IMG_0148.JPG|882168"));
	}
	
	@Test
	public void pushFileTest7() throws UnknownHostException, IOException{
		File file = new File(folder + "photo(3).JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("photo(3).JPG|2240877"));
	}
	
	@Test
	public void pushFileTest8() throws UnknownHostException, IOException{
		File file = new File(folder + "SANY0362.JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("SANY0362.JPG|96745"));
	}
	
	@Test
	public void pushFileTest9() throws UnknownHostException, IOException{
		File file = new File(folder + "scotts run.JPG");
		assertTrue(file.exists());
		String response = FooClient.remoteCommand(ServerConstants.sendingFileCmd + "|" + file.getAbsolutePath() + "|" + file.length());
		assertTrue(response.equals("scotts run.JPG|232480"));
	}

}
