import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * *Project5Driver.java
 * 
 * @author Stephen Wright
 *This is the driver that starts the program
 */


public class Project5Driver
{

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException
	{
		// TODO Auto-generated method stub
		/*These next lines are slick commands to initialize the window*/
		AppGameContainer app = 
				new AppGameContainer(new sbg());
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.start();
	}

}
