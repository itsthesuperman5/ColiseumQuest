import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * pauseState.java
 * @author Stephen Wright
 *The game state for when the user has paused the game
 */
public class pauseState extends BasicGameState
{
	/*The constructor*/
	public pauseState()
	{
		super();
	}
	/*unique ID for this game state*/
	public static final int ID = 6;
	/*the background image*/
	private Image backgroundImage;
	/*the music played while paused*/
	private Music mus;
	/*all the next variables are the stats for the players
	 * to be displayed while paused*/
	private String aradonName;
	private int aradonCurrentHP;
	private int aradonMaxHP;
	private int aradonLevel;
	private int aradonAttack;
	private int aradonDefense;
	private int aradonSpeed;
	private int aradonNextLevel;
	private String dirkName;
	private int dirkCurrentHP;
	private int dirkMaxHP;
	private int dirkLevel;
	private int dirkAttack;
	private int dirkDefense;
	private int dirkSpeed;
	private int dirkNextLevel;
	/*the facial images for the characters*/
	private Image aradonFace;
	private Image dirkFace;
	
	/*playing the music and grabbing all the data*/
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		mus.play();
		mus.loop();
		aradonName = InGameState.TEAM[0].getName();
		aradonCurrentHP = InGameState.TEAM[0].getCurrentHP();
		aradonMaxHP = InGameState.TEAM[0].getMaxHP();
		aradonLevel = InGameState.TEAM[0].getLevel();
		aradonAttack = InGameState.TEAM[0].getAttack();
		aradonDefense = InGameState.TEAM[0].getDefense();
		aradonSpeed = InGameState.TEAM[0].getSpeed();
		aradonNextLevel = InGameState.TEAM[0].getMaxXP() - InGameState.TEAM[0].getXP();
		dirkName = InGameState.TEAM[1].getName();
		dirkCurrentHP = InGameState.TEAM[1].getCurrentHP();
		dirkMaxHP = InGameState.TEAM[1].getMaxHP();
		dirkLevel = InGameState.TEAM[1].getLevel();
		dirkAttack = InGameState.TEAM[1].getAttack();
		dirkDefense = InGameState.TEAM[1].getDefense();
		dirkSpeed = InGameState.TEAM[1].getSpeed();
		dirkNextLevel = InGameState.TEAM[1].getMaxXP() - InGameState.TEAM[0].getXP();
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		mus.stop();
	}
	
	/*initializing the variables*/
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		backgroundImage = new Image("/res/pause.jpg");
		mus = new Music("/res/jeopardy.wav");
		aradonFace = new Image("/res/aradonFace.png");
		dirkFace = new Image("/res/dirkFace.png");
	}

	/*drawing all the graphics to the screen*/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		backgroundImage.draw(0, 0, 3);
		g.drawString("Potions - "+InGameState.TEAM[0].getPotions(), 650, 20);
		g.drawString("Gold - "+InGameState.TEAM[0].getGold()+"g", 650, 40);
		g.drawString(aradonName+" Lvl "+aradonLevel, 150, 100);
		g.drawString(dirkName+" Lvl "+dirkLevel, 550, 100);
		aradonFace.draw(150, 120);
		dirkFace.draw(550, 120);
		g.drawString("HP: "+aradonCurrentHP+"/"+aradonMaxHP, 150, 200);
		g.drawString("HP: "+dirkCurrentHP+"/"+dirkMaxHP, 550, 200);
		g.drawString("Attack: "+aradonAttack, 150, 220);
		g.drawString("Attack: "+dirkAttack, 550, 220);
		g.drawString("Defense: "+aradonDefense, 150, 240);
		g.drawString("Defense: "+dirkDefense, 550, 240);
		g.drawString("Speed: "+aradonSpeed, 150, 260);
		g.drawString("Speed: "+dirkSpeed, 550, 260);
		g.drawString("NxtLvl: "+aradonNextLevel, 150, 280);
		g.drawString("NxtLvl: "+dirkNextLevel, 550, 280);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		/*this conditional causes you to leave the pause state*/
		if(input.isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(2);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
