import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * InGameState.java
 * @author Stephen Wright
 *This is a the game state for the overworld with map exploration and npcs and map collisions and random battles
 */
public class InGameState extends BasicGameState
{
	/*the array for the battle characters which can be accessed from several different game states;
	 * this was the only way I found to pass information between game states in slick. Works great.*/
	public static battler[] TEAM = new battler[2];
	/*a variable to hold which map you are in; required to make my battle backgrounds context sensitive to where you are in
	 * the world, i.e. having a cave background if the battle was generated in a cave*/
	public static String currentMap = "";
	/*a variable to track how many gladiator's you've defeated; used to keep track of your quest progress*/
	public static int gladiatorsDefeated = 0;
	/*unique ID for this game state*/
	public static final int ID = 2;
	
	public InGameState()
	{
		super();
	}
	/*the background music*/
	private Music mus;
	/*the sound when you buy a potion*/
	private Sound cash;
	/*the currently displayed tile map*/
	private TiledMap map;
	/*the base player variable*/
	private player aradon = null;
	/*the changeable player variable*/
	private player current = null;
	/*the movement animation variable*/
	private Animation sprite = null;
	/*the outline for displayed text*/
	private Image textBackground;
	/*an array to hold the locations of where tiles you can't move through are*/
	private boolean[][] blocked;
	/*an array to hold where non player characters are on the map*/
	private String[][] npcs;
	/*player's location from left to right*/
	private float playerX;
	/*player's location from north to south*/
	private float playerY;
	/*the randomly generated number of steps before the next random battle starts*/
	private int randInt;
	/*the counter for number of steps to reach the next random battle*/
	private int counter = 0;
	/*random number generator*/
	private Random rand = new Random();
	/*whether the intro screen has been cleared or not*/
	private boolean introCompleted;
	/*whether or not text should be displayed*/
	private boolean showMessage;
	/*whether or not an optional battle is imminent*/
	private boolean potentialBattle;
	/*whether or not an optional purchase is imminent*/
	private boolean potentialPurchase;
	/*whether or not a battle with the mutant cave bear is imminent*/
	private boolean bearBattle;
	/*the direction the player is facing*/
	private String orientation;
	/*the next 4 variables hold the potential dialog lines*/
	private String convoName;
	private String convo;
	private String convo2;
	private String convo3;
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		/*play the music and give values to variables*/
		mus.play();
		mus.loop();
		randInt = rand.nextInt(1000);
		counter = 0;
		potentialBattle = false;
		potentialPurchase = false;
		showMessage = false;
		bearBattle = false;
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		/*stop the music*/
		mus.stop();
	}
	
	/*initialiizing all variables*/
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException 
	{
		// TODO Auto-generated method stub
		gc.setMinimumLogicUpdateInterval(20);
		introCompleted = false;
		showMessage = false;
		TEAM[0]=new battler(15, 3, 0, 3, 1, "Aradon");
		TEAM[1]=new battler(13, 2, 0, 2, 1, "Dirk");
		mus = new Music("/res/world.wav");
		cash = new Sound("/res/cash.wav");
		textBackground = new Image("/res/textBackground.png");
		map = new TiledMap("/res/colosseum.tmx");
		currentMap = "colosseum";
		/*the next variables along with the for loops read tile properties from the tile map and puts then in the arrays*/
		blocked = new boolean[map.getWidth()][map.getHeight()];
		npcs = new String[map.getWidth()][map.getHeight()];
		for (int x=0;x<map.getWidth();x++) 
		{
			for (int y=0;y<map.getHeight();y++)
			{
				int tileID = map.getTileId(x, y, 2);
				int tileID2 = map.getTileId(x, y, 1);
				String otherValue = map.getTileProperty(tileID2, "name", "none");
				String value = map.getTileProperty(tileID, "blocked", "false");
				npcs[x][y] = otherValue;
				if ("true".equals(value))
				{
					blocked[x][y] = true;
				}
			}
		}
		aradon = new player("Aradon");
		sprite = aradon.getIdle();
		current = aradon;
		playerX=400;
		playerY=300;
		orientation = "down";
		randInt = rand.nextInt(3000);
	}
	/*drawing graphics to the screen*/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException 
	{
		// TODO Auto-generated method stub
		/*rendering only the bottom two layers of the tile map*/
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		/*drawing the player's animation*/
		sprite.draw(playerX, playerY);
		/*the intro containing the basic story plot*/
		if(!(introCompleted))
		{
			g.drawString("You are Aradon. You and your brother Dirk are prisoners taken captive by an", 20,200);
			g.drawString("unfriendly king. Your sentence, in lieu of death, is to fight for your freedom", 20, 220);
			g.drawString("in the deadly Colosseum. You are allowed to venture into the areas ", 20, 240);
			g.drawString("surrounding the Colosseum in order to increase your strength. Use 'SPACE' to talk to", 20, 260);
			g.drawString("the GameMaster in the Colosseum to challenge your next opponent. He will give you a ", 20, 280);
			g.drawString("suggestion as to whether he believes you are ready or not. You would", 20, 300);
			g.drawString("be wise to heed him. The merchant next to the lake located south of the Colosseum", 20, 320);
			g.drawString("can give you more information on the surrounding areas. Once you leave the walls", 20, 340);
			g.drawString("of the Colosseum you are in danger of attack from monsters of the world. Good luck!", 20, 360);
			g.drawString("PRESS ENTER", 400, 400);
		}
		/*the conditional for whether or not dialog should be displayed*/
		if(showMessage)
		{
			textBackground.draw(150, 50, 2);
			g.drawString(convoName, 200, 80);
			g.drawString(convo, 200, 100);
			g.drawString(convo2, 200, 120);
			g.drawString(convo3, 200, 160);
		}
		System.out.println(""+counter+" | "+randInt);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException 
	{
		// TODO Auto-generated method stub
		/*getting input from the user*/
		Input input = gc.getInput();
		/*the conditional for whether the game is beaten or not*/
		if(gladiatorsDefeated == 6 || input.isKeyPressed(Input.KEY_E))
			sbg.enterState(7);
		/*the conditional to go to the pause screen*/
		if(input.isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(6);
		/*cheat to advance level for showing the game in class*/
		if(input.isKeyPressed(Input.KEY_Q)){
			TEAM[0].advLevel();
			TEAM[1].advLevel();
		}
			
		/*the conditional to enter the bear battle and return you to the coliseum if you win*/
		if(input.isKeyPressed(Input.KEY_X) && bearBattle)
		{
			currentMap = "colosseum";
			map = new TiledMap("/res/colosseum.tmx");
			blocked = new boolean[map.getWidth()][map.getHeight()];
			for (int x=0;x<map.getWidth();x++) 
			{
				for (int y=0;y<map.getHeight();y++)
				{
					int tileID = map.getTileId(x, y, 2);
					int tileID2 = map.getTileId(x, y, 1);
					String otherValue = map.getTileProperty(tileID2, "name", "none");
					npcs[x][y] = otherValue;
					String value = map.getTileProperty(tileID, "blocked", "false");
					if ("true".equals(value))
					{
						blocked[x][y] = true;
					}
				}
			}
			playerY = 570;
			sbg.enterState(3);
		}
		/*conditional to enter a battle if one is possible*/
		if(input.isKeyPressed(Input.KEY_Y) && potentialBattle)
		{
			sbg.enterState(3);
		}
		/*delaying a battle if not wanting to enter it yet*/
		if(input.isKeyPressed(Input.KEY_N) && potentialBattle)
		{
			potentialBattle = false;
			showMessage = false;
		}
		/*conditional to not be able to afford to buy a potion if not possible to do so*/
		if(input.isKeyDown(Input.KEY_Y) && potentialPurchase && InGameState.TEAM[0].getGold() < 50)
		{
			convoName = "Merchant";
			convo2 = "";
			convo = "You can't afford it; they're 50 gold!";
			convo3 = "Press Space";
		}
		/*conditional to buy a potion if possible to do so*/
		if(input.isKeyDown(Input.KEY_Y) && potentialPurchase && InGameState.TEAM[0].getGold() >= 50)
		{
			cash.play();
			convoName = "Merchant";
			convo2 = "";
			convo = "Thank you! Come Again.";
			convo3 = "Press Space";
			InGameState.TEAM[0].buyPotions();
			InGameState.TEAM[0].spendGold();
			potentialPurchase = false;
		}
		/*condition to reject buying a potion*/
		if(input.isKeyDown(Input.KEY_N) && potentialPurchase)
		{
			potentialPurchase = false;
			showMessage = false;
		}
		/*condition to advance through dialog*/
		if(input.isKeyPressed(Input.KEY_SPACE) && introCompleted)
		{
			if(!showMessage)
				this.select(orientation, playerX, playerY);
			else
				showMessage = false;
		}
		/*clears the intro dialog*/
		else
		if(input.isKeyPressed(Input.KEY_ENTER))
		{
			introCompleted = true;
		}
		/*the next four conditionals move you up, down, left, or right; update the animation accordingly;
		 * and increase the counter variable to the next random battle unless you're in the coliseum*/
		if (input.isKeyDown(Input.KEY_UP) && introCompleted && !showMessage)
		{
			orientation = "up";			
			if(playerY > 0 && tryMove(playerX, playerY - delta * 0.1f))
			{
				sprite = current.getUp();
				sprite.update(delta);
			
				if(!(currentMap.equals("colosseum")))
				{
					counter++;
				}
			}
		}
		/*see above*/
		else if (input.isKeyDown(Input.KEY_DOWN) && introCompleted  && !showMessage)
		{
			orientation = "down";
	    	if(playerY < 580 && tryMove(playerX, playerY + delta * 0.1f))
	    	{
	    		sprite = current.getDown();
		    	sprite.update(delta);
	    	
	    		if(!(currentMap.equals("colosseum")))
				{
	    			counter++;
				}
	    	}
		}
		/*see above*/
		else if (input.isKeyDown(Input.KEY_LEFT) && introCompleted && !showMessage)
		{
			orientation = "left";
			if(playerX > 0 && tryMove(playerX - delta * 0.1f, playerY))
			{
				sprite = current.getLeft();
				sprite.update(delta);
				if(!(currentMap.equals("colosseum")))
				{
					counter++;
				}
			}
		}
		/*see above*/
		else if (input.isKeyDown(Input.KEY_RIGHT) && introCompleted && !showMessage)
		{
			orientation = "right";
			if(playerX < 780 && tryMove(playerX + delta * 0.1f, playerY))
			{
				sprite = current.getRight();
				sprite.update(delta);
				if(!(currentMap.equals("colosseum")))
				{
					counter++;
				}
			}
		}
		/*gives the sprite the idle animation if not moving*/
		else
		{
			sprite = current.getIdle();
			sprite.update(delta);
		}
		/*checks for a random battle*/
		this.checkRand(sbg);
		/*checks if you're in a zone to move into a different region*/
		this.checkArea();		
	}
	
	/*checks to enter a random battle or not and enters the battle state if it is*/
	private void checkRand(StateBasedGame sbg)
	{
		if(counter == randInt)
			sbg.enterState(3);
	}
	
	/*this next method checks the player's location based on which map you're on and if you're in the right location
	 * you move to the corresponding area. moving to the corresponding area causes the blocked array to be regenerated*/
	private void checkArea() throws SlickException
	{
		if(currentMap.equals("field"))
		{
			if(playerX > 775 && playerY > 250 && playerY < 290)
			{
				map = new TiledMap("/res/outsideCol.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "outsideCol";
				playerX = 3;
			}
		}
		if(currentMap.equals("outsideCol"))
		{
			if(playerX < 2 && playerY > 250 && playerY < 325)
			{
				map = new TiledMap("/res/field.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "field";
				playerX = 774;
				playerY = 275;
			}
			
			if(playerY < 5 && playerX > 335 && playerX < 400)
			{
				map = new TiledMap("/res/colosseum.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "colosseum";
				playerY = 574;
			}
			
			if(playerX > 775 && playerY > 150 && playerY < 230)
			{
				map = new TiledMap("/res/cliffs.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "cliffs";
				playerX = 3;
				playerY = 300;
			}
		}
		if(currentMap.equals("cliffs"))
		{
			if(playerX < 2 && playerY > 250 && playerY < 330)
			{
				map = new TiledMap("/res/outsideCol.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "outsideCol";
				playerX = 774;
				playerY = 200;
			}
			if(playerX < 80 && playerX > 60 && playerY < 460 && playerY > 440)
			{
				map = new TiledMap("/res/cave.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "cave";
				playerX = 400;
				playerY = 574;
			}
		}
		if(currentMap.equals("colosseum"))
		{
			if(playerY > 575)
			{
				map = new TiledMap("/res/outsideCol.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "outsideCol";
				playerX = 370;
				playerY = 6;
			}
		}
		if(currentMap.equals("cave"))
		{
			if(playerY > 575)
			{
				map = new TiledMap("/res/cliffs.tmx");
				blocked = new boolean[map.getWidth()][map.getHeight()];
				for (int x=0;x<map.getWidth();x++) 
				{
					for (int y=0;y<map.getHeight();y++)
					{
						int tileID = map.getTileId(x, y, 2);
						int tileID2 = map.getTileId(x, y, 1);
						String otherValue = map.getTileProperty(tileID2, "name", "none");
						npcs[x][y] = otherValue;
						String value = map.getTileProperty(tileID, "blocked", "false");
						if ("true".equals(value))
						{
							blocked[x][y] = true;
						}
					}
				}
				currentMap = "cliffs";
				playerX = 70;
				playerY = 480;
			}
		}
			
	}
	/*this next method checks to see if you're trying to move to a blocked tile*/
	private boolean tryMove(float x, float y)
	{
		float newx = x;
		float newy = y;
		boolean choice;
		if (blocked(newx/32,newy/32)) 
		{
			choice =  false;
		} 
		else 
		{
			playerX = newx;
			playerY = newy;
			choice = true;
		}
		return choice;
	}
	/*checks if the tile is blocked*/
	private boolean blocked(float x, float y) {
		return blocked[(int) x][(int) y];
	}
	
	/*this method checks to see if there's a non playable character in front of you and initiates a conversation
	 * with them if they are*/
	private void select(String orientation, float X, float Y)
	{
		String value;
		int x = (int)(X/32);
		int y = (int)(Y/32);
		if(x <= 0)
			x = 1;
		if(y <= 0)
			y = 1;
		if(x >= 24)
			x = 23;
		if(y >= 18)
			y = 17;
		if(orientation=="up")
		{
			y-=1;
			value = npcs[x][y];
			System.out.println(value);
			if (value.equals("none"))
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "There's nothing there.";
				convo3 = "Press Space";
			}
			if (value.equals("Guard"))
			{
				showMessage = true;
				convoName = "Guard:";
				convo = "I ought to run you through for looking at me ";
				convo2 = "like that!";
				convo3 = "Press Space";
			}
			if(value.equals("MutantBear"))
			{
				showMessage = true;
				bearBattle = true;
				convoName = "";
				convo = "The monster attacks you in a fury!";
				convo2 = "";
				convo3 = "Press X";
			}
			if(value.equals("Merchant"))
			{
				showMessage = true;
				potentialPurchase = true;
				convoName = "Merchant:";
				convo = "The field is west, don't go until Level 4.";
				convo2 = "The cliffs are east, don't go until Level 6.";
				convo3 = "Anyway, want to buy a potion? (Y or N)";
			}
			if(value.equalsIgnoreCase("GameMaster"))
			{
				showMessage = true;
				convoName = "GameMaster:";
				if(gladiatorsDefeated == 0)
				{
					potentialBattle = true;
					convo = "You are ready for your 1st battle.";
					convo2 = "Begin?";
					convo3 = "Y for yes/ N for no";
				}
				if(gladiatorsDefeated == 1)
				{
					if(TEAM[0].getLevel() >= 4)
					{
						potentialBattle = true;
						convo = "You are ready for your 2nd battle.";
						convo2 = "Begin?";
						convo3 = "Y for yes/ N for no";
					}
					else
					{
						potentialBattle = true;
						convo = "You are NOT ready for your 2nd battle.";
						convo2 = "Begin anyway?";
						convo3 = "Y for yes/ N for no";
					}
					
				}
				if(gladiatorsDefeated == 2)
				{
					if(TEAM[0].getLevel() >= 6)
					{
						potentialBattle = true;
						convo = "You are ready for your 3rd battle.";
						convo2 = "Begin?";
						convo3 = "Y for yes/ N for no";
					}
					else
					{
						potentialBattle = true;
						convo = "You are NOT ready for your 3rd battle.";
						convo2 = "Begin anyway?";
						convo3 = "Y for yes/ N for no";
					}
				}
				if(gladiatorsDefeated == 3)
				{
					if(TEAM[0].getLevel() >= 8)
					{
						potentialBattle = true;
						convo = "You are ready for your 4th battle.";
						convo2 = "Begin?";
						convo3 = "Y for yes/ N for no";
					}
					else
					{
						potentialBattle = true;
						convo = "You are NOT ready for your 4th battle.";
						convo2 = "Begin anyway?";
						convo3 = "Y for yes/ N for no";
					}
				}
				if(gladiatorsDefeated == 4)
				{
					convo = "To qualify for your final battle,";
					convo2 = "you must travel to the cave in the east";
					convo3 = "and slay the monster within. (Space)";	
				}
				if(gladiatorsDefeated == 5)
				{
					if(TEAM[0].getLevel() >= 10)
					{
						potentialBattle = true;
						convo = "You are ready for your Final battle.";
						convo2 = "Begin?";
						convo3 = "Y for yes/ N for no";
					}
					else
					{
						potentialBattle = true;
						convo = "You are NOT ready for your Final battle.";
						convo2 = "Begin anyway?";
						convo3 = "Y for yes/ N for no";
					}
				}
			}
		}
		if(orientation=="down")
		{
			y+=1;
			value = npcs[x][y];
			if (value.equals("none"))
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "There's nothing there.";
				convo3 = "Press Space";
			}
			else
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "Don't speak to me from this angle.";
				convo3 = "Press Space";
			}
			
		}
		if(orientation=="left")
		{
			x-=1;
			value = npcs[x][y];
			if (value.equals("none"))
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "There's nothing there.";
				convo3 = "Press Space";
			}
			else
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "Don't speak to me from this angle.";
				convo3 = "Press Space";
			}			
		}
		if(orientation=="right")
		{
			x+=1;
			value = npcs[x][y];
			if (value.equals("none"))
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "There's nothing there.";
				convo3 = "Press Space";
			}
			else
			{
				showMessage = true;
				convoName = "";
				convo2 = "";
				convo = "Don't speak to me from this angle.";
				convo3 = "Press Space";
			}
		}
	}
	/*returns this state's id*/
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
}
