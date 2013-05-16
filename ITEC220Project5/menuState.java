

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 * menuState.java
 * @author Superman
 *The title screen menu consisting of an image and a start option
 */
public class menuState extends BasicGameState
{
	public menuState()
	{
		super();
	}
	
	/*the unique ID for this game state, required to move between the states*/
	public static final int ID = 1;
	/*the Slick Music variable for the menu music*/
	private Music menuMus;
	/*the background image for the menu*/
	private Image background;
	/*the input variable to receive input from user*/
	private Input input;

	/*the enter method is included in Slick's state based games and runs every time you enter the state*/
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		/*plays the music*/
		menuMus.play();
		/*tells the music to loop if it gets to the end of the track*/
		menuMus.loop();
	}
	
	/*the leave method is included in Slick's state based games and runs every time you exit the state*/
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		/*stops the music*/
		menuMus.stop();
	}
	
	/*init is included in Slick and simply initializes all variables*/
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		background = new Image("/res/Colosseum.png");
		menuMus = new Music("/res/title.wav");
		
	}
	
	/*render is a slick method that draws things on the screen; it runs every frame*/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		/*drawing the background image on the screen*/
		background.draw();
	}

	/*update is a slick method that changes things based on input or other variables; it runs every frame*/
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		// TODO Auto-generated method stub
		input = gc.getInput();
		/*this next conditional sends you into the game state if you click the start area*/
		if(input.isMousePressed(0) && input.getMouseX() > 300 && input.getMouseX() < 500 && 
				input.getMouseY() > 200 && input.getMouseY() < 250) 
		{
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}	
		
	}
	
	/*Slick method to return the state's ID*/
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
}
