import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * sbg.java
 * @author Stephen Wright
 *This class is my extension of Slick's State based games. It allows me to
 *have several different states and move between them to create a game with much
 *more depth.
 */
public class sbg extends StateBasedGame
{
	/**
	 * Constructor
	 */
	public sbg()
	{
		super("Escape the Colosseum");
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		// TODO Auto-generated method stub
		/*adds the state for the Main Menu (Title Screen)*/
		addState(new menuState());
		/*adds the state for the actual exploration part of the game*/
		addState(new InGameState());
		/*adds the state for the turn based rpg battles*/
		addState(new battleState());
		/*adds the state for game over when your party is destroyed*/
		addState(new GameOverState());
		/*adds the state for the pause screen*/
		addState(new pauseState());
		/*adds the state for the end game achieved when you win*/
		addState(new endGameState());
		/*this commands enters the state with ID 1, which is the title screen*/
		this.enterState(1);
	}
}
