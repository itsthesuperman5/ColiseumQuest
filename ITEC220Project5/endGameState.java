import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * endGameState.java
 * @author Stephen Wright
 *This state is for when the game is beaten
 */
public class endGameState extends BasicGameState
{
	/*The constructor*/
	public endGameState()
	{
		super();
	}
	
	/*unique ID for this state*/
	public static final int ID = 7;
	
	/*music to be played while ending*/
	private Music mus;
	/*images to be displayed during ending*/
	private Image camel;
	private Image glider;
	private Image beach;
	private Image horse;
	private Image black;
	private Image me;
	private Image current;
	/*the text to be displayed while ending*/
	private String caption;
	/*the array of different lines to be displayed*/
	private String[] lines;
	/*the rate that the text moves*/
	private float rate;
	/*x and y locations of the text*/
	private float x;
	private float y;
	/*x and y location of the picture*/
	private float picX;
	private float picY;
	/*index of the lines array*/
	private int index;
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		/*playing music*/
		mus.setPosition(191);
		mus.play();
		gc.setMinimumLogicUpdateInterval(20);
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		/*stopping music*/
		mus.stop();
	}
	
	/*Initializing all variables*/
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		x = 250;
		y = 601;
		picX = 350;
		picY = -180;
		index = 0;
		rate = (float) 0.91;
		mus = new Music("/res/ending.wav");
		lines = new String[10];
		lines[0] = "Congratulations! You have won your freedom!";
		lines[1] = "Aradon would go on to travel the world.";
		lines[2] = "He did many wonderful and exciting things.";
		lines[3] = "He truly made the most of his freedom.";
		lines[4] = "Dirk, however, did not.";
		lines[5] = "He was an idiot who was happy mucking stables his whole life.";
		lines[6] = "Thanks ITEC 220 for an amazing semester!";
		lines[7] = "THE END";
		camel = new Image("/res/camel.jpg");
		glider = new Image("/res/hangglider.jpg");
		beach = new Image("/res/beach.jpg");
		horse = new Image("/res/horse.jpg");
		black = new Image("/res/black.jpg");
		me = new Image("/res/me.jpg");
		current = black;
		caption = lines[0];
	}

	/*drawing the graphics to the screen*/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		current.draw(0, 0);
		me.draw(picX, picY, 2);
		g.drawString(caption, x, y);
	}

	/*changing the graphics including moving the text and changing the images*/
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		y -= rate;
		if(y < -1 && index <= 7)
		{
			caption = nextLine();
			y = 601;
		}
		if(index == 1)
			current = camel;
		if(index == 2)
			current = glider;
		if(index == 3)
			current = beach;
		if(index == 4)
			current = black;
		if(index == 5)
			current = horse;
		if(index == 6)
			current = black;
		if(index == 7){
			rate = (float) 0.4;
			current = black;
			picY += rate;			
		}
		if(index == 7 && y < 300)
			sbg.setUpdatePaused(true);
	}

	/*returns the ID*/
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	/*grabbing the next text and returning it*/
	public String nextLine()
	{
		index++;
		return lines[index];
	}

}
