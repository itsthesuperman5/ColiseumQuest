import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * battleState.java
 * @author Stephen Wright
 *This state is the battle state used to make the combat between monsters and the player
 */
public class battleState extends BasicGameState
{
	public battleState()
	{
		super();
	}
	/*unique ID for this game state*/
	public static final int ID = 3;
	/*the music playing during battle*/
	private Music battleMus;
	/*the background image which is context sensitive*/
	private Image background;
	/*Aradon's image*/
	private Image Aradon;
	/*Aradon's attack animation*/
	private Animation AradonHit;
	/*Dirk's image*/
	private Image Dirk;
	/*the frame for the battle text*/
	private Image textBackground;
	/*the sheet containing frames for the animations*/
	private SpriteSheet slash;
	/*the next images are for the slash animation*/
	private Image slash0;
	private Image slash1;
	private Image slash2;
	private Image slash3;
	private Image slash4;
	/*the animation for attack*/
	private Animation slashAnim;
	/*Dirk's attack animation*/
	private Animation DirkHit;
	/*the next array variables are for the monsters depending on where the battle is*/
	private fieldMonster[] fieldMon;
	private outMonster[] outMon;
	private cliffsMonster[] cliffMon;
	private caveMonster[] caveMon;
	private gladiator[] glads;
	/*random number generator*/
	private Random rand;
	/*variable for which monsters to render based on location*/
	private int switchCase;
	/*random variable for how many monsters are produced*/
	private int randInt;
	/*the next variables are for the possible battle dialog*/
	private String battleMessage1;
	private String battleMessage2;
	private String battleMessage3;
	private String battleMessage4;
	private String battleMessage5;
	private String battleMessage6;
	/*the type of monster fighting based on location*/
	private String monType;
	/*whether or not the battle is over*/
	private boolean battleOver;
	/*whether or not the players won*/
	private boolean playerWon;
	/*the number of monsters generated*/
	private int numMonsters;
	/*the number of monsters killed*/
	private int kills;
	/*whether or not it's Aradon's turn*/
	private boolean aradonTurn;
	/*whether or not it's dirk's turn*/
	private boolean dirkTurn;
	/*whether or not it's the monster's turn*/
	private boolean theirTurn;
	/*whether or not Aradon is attacking*/
	private boolean aradonAttack;
	/*whether or not Dirk is attacking*/
	private boolean dirkAttack;
	/*the victory music played after winning a battle*/
	private Sound victoryMusic;
	/*the sound of an attack*/
	private Sound attackSound;
	/*the number of turns*/
	private int numTurns;
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		battleMus.play();
		battleMus.loop();
		aradonTurn = true;
		dirkTurn = false;
		theirTurn = false;
		playerWon = false;
		aradonAttack = false;
		dirkAttack = false;
		if(InGameState.currentMap.equalsIgnoreCase("colosseum"))
		{
			randInt = 1;
		}
		else
			randInt = rand.nextInt(3) + 1;
		numMonsters = randInt;
		kills = 0;
		numTurns = 0;
		fieldMon = new fieldMonster[randInt];
		outMon = new outMonster[randInt];
		cliffMon = new cliffsMonster[randInt];
		caveMon = new caveMonster[randInt];
		glads = new gladiator[randInt];
		battleOver = false;
		this.formEnemy();
		battleMessage1 = "";
		battleMessage2 = "";
		battleMessage3 = "An enemy has appeared!";
		battleMessage4 = "";
		battleMessage5 = "Press 'Enter' to Advance Text";
		battleMessage6 = "";
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		battleMus.stop();
		victoryMusic.stop();
	}
	
	/*initializing all variables*/
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// TODO Auto-generated method stub
		battleMus = new Music("/res/battle.wav");
		victoryMusic = new Sound("/res/victory.wav");
		attackSound = new Sound("/res/attack.wav");
		slash = new SpriteSheet("/res/slash.png", 40, 40);
		slash0 = new Image("/res/blank.png");
		slash1 = slash.getSubImage(0, 0);
		slash2 = slash.getSubImage(1, 0);
		slash3 = slash.getSubImage(2, 0);
		slash4 = slash.getSubImage(3, 0);
		slashAnim = new Animation(new Image[]{slash0, slash1, slash2, slash3, slash4}, 100);
		rand = new Random();
		Aradon = InGameState.TEAM[0].getImage();
		AradonHit = InGameState.TEAM[0].getHit();
		AradonHit.setAutoUpdate(true);
		AradonHit.setLooping(false);
		Dirk = InGameState.TEAM[1].getImage();
		DirkHit = InGameState.TEAM[1].getHit();
		DirkHit.setAutoUpdate(true);
		DirkHit.setLooping(false);
		textBackground = new Image("/res/textBackground.png");
	}

	/*drawing all graphics to the screen*/
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		background.draw(0, 0);
		textBackground.draw(150, 50, 2);
		g.drawString("" + InGameState.TEAM[1].getCurrentHP() + "/" + InGameState.TEAM[1].getMaxHP(), 650, 390);
		g.drawString(InGameState.TEAM[1].getName()+" Lvl "+InGameState.TEAM[1].getLevel(), 650, 370);
		
		
		g.drawString("" + InGameState.TEAM[0].getCurrentHP() + "/" + InGameState.TEAM[0].getMaxHP(), 650, 290);
		g.drawString(InGameState.TEAM[0].getName()+" Lvl "+InGameState.TEAM[0].getLevel(), 650, 270);
		
		/*this switch statement determins which monsters and how many are drawn*/
		switch (switchCase)
		{
			case 0:
				if(glads[0].getAlive()){
					glads[0].getImage().draw(150, 300, 3);
					g.drawString(""+glads[0].getCurrentHP()+"/"+glads[0].getMaxHP(), 100, 300);
					g.drawString(glads[0].getName()+" Lvl "+glads[0].getLevel(), 100, 280);
				}
				if(randInt > 1)
				{
					if(glads[1].getAlive()){
						glads[1].getImage().draw(150, 400, 2);
						g.drawString(""+glads[1].getCurrentHP()+"/"+glads[1].getMaxHP(), 100, 400);
						g.drawString(glads[1].getName()+" Lvl "+glads[1].getLevel(), 100, 380);
					}
				}
				if(randInt > 2)
				{
					if(glads[2].getAlive()){
						glads[2].getImage().draw(150, 500, 2);
						g.drawString(""+glads[2].getCurrentHP()+"/"+glads[2].getMaxHP(), 100, 500);
						g.drawString(glads[2].getName()+" Lvl "+glads[2].getLevel(), 100, 480);
					}
				}
			break;
			case 1:
				if(outMon[0].getAlive()){
					outMon[0].getImage().draw(150, 300, 2);
					g.drawString(""+outMon[0].getCurrentHP()+"/"+outMon[0].getMaxHP(), 100, 300);
					g.drawString(outMon[0].getName()+" Lvl "+outMon[0].getLevel(), 100, 280);
				}
				if(randInt > 1)
				{
					if(outMon[1].getAlive()){
						outMon[1].getImage().draw(150, 400, 2);
						g.drawString(""+outMon[1].getCurrentHP()+"/"+outMon[1].getMaxHP(), 100, 400);
						g.drawString(outMon[1].getName()+" Lvl "+outMon[1].getLevel(), 100, 380);
					}
				}
				if(randInt > 2)
				{
					if(outMon[2].getAlive()){
						outMon[2].getImage().draw(150, 500, 2);
						g.drawString(""+outMon[2].getCurrentHP()+"/"+outMon[2].getMaxHP(), 100, 500);
						g.drawString(outMon[2].getName()+" Lvl "+outMon[2].getLevel(), 100, 480);
					}
				}
			break;
			case 2:
				if(fieldMon[0].getAlive()){
					fieldMon[0].getImage().draw(150, 300, 2);
					g.drawString(""+fieldMon[0].getCurrentHP()+"/"+fieldMon[0].getMaxHP(), 100, 300);
					g.drawString(fieldMon[0].getName()+" Lvl "+fieldMon[0].getLevel(), 100, 280);
				}
				if(randInt > 1)
				{
					if(fieldMon[1].getAlive()){
						fieldMon[1].getImage().draw(150, 400, 2);
						g.drawString(""+fieldMon[1].getCurrentHP()+"/"+fieldMon[1].getMaxHP(), 100, 400);
						g.drawString(fieldMon[1].getName()+" Lvl "+fieldMon[1].getLevel(), 100, 380);
					}
				}
				if(randInt > 2)
				{
					if(fieldMon[2].getAlive()){
						fieldMon[2].getImage().draw(150, 500, 2);
						g.drawString(""+fieldMon[2].getCurrentHP()+"/"+fieldMon[2].getMaxHP(), 100, 500);
						g.drawString(fieldMon[2].getName()+" Lvl "+fieldMon[2].getLevel(), 100, 480);
					}
				}
			break;
			case 3:
				if(cliffMon[0].getAlive()){
					cliffMon[0].getImage().draw(150, 300, 2);
					g.drawString(""+cliffMon[0].getCurrentHP()+"/"+cliffMon[0].getMaxHP(), 100, 300);
					g.drawString(cliffMon[0].getName()+" Lvl "+cliffMon[0].getLevel(), 100, 280);
				}
				if(randInt > 1)
				{
					if(cliffMon[1].getAlive()){
						cliffMon[1].getImage().draw(150, 400, 2);
						g.drawString(""+cliffMon[1].getCurrentHP()+"/"+cliffMon[1].getMaxHP(), 100, 400);
						g.drawString(cliffMon[1].getName()+" Lvl "+cliffMon[1].getLevel(), 100, 380);
					}
				}
				if(randInt > 2)
				{
					if(cliffMon[2].getAlive()){
						cliffMon[2].getImage().draw(150, 500, 2);
						g.drawString(""+cliffMon[2].getCurrentHP()+"/"+cliffMon[2].getMaxHP(), 100, 500);
						g.drawString(cliffMon[2].getName()+" Lvl "+cliffMon[2].getLevel(), 100, 480);
					}
				}
			break;
			case 4:
				if(caveMon[0].getAlive()){
					caveMon[0].getImage().draw(150, 300, 2);
					g.drawString(""+caveMon[0].getCurrentHP()+"/"+caveMon[0].getMaxHP(), 100, 300);
					g.drawString(caveMon[0].getName()+" Lvl "+caveMon[0].getLevel(), 100, 280);
				}
				if(randInt > 1)
				{
					if(caveMon[1].getAlive()){
						caveMon[1].getImage().draw(150, 400, 2);
						g.drawString(""+caveMon[1].getCurrentHP()+"/"+caveMon[1].getMaxHP(), 100, 400);
						g.drawString(caveMon[1].getName()+" Lvl "+caveMon[1].getLevel(), 100, 380);
					}
				}
				if(randInt > 2)
				{
					if(caveMon[2].getAlive()){
						caveMon[2].getImage().draw(150, 500, 2);
						g.drawString(""+caveMon[2].getCurrentHP()+"/"+caveMon[2].getMaxHP(), 100, 500);
						g.drawString(caveMon[2].getName()+" Lvl "+caveMon[2].getLevel(), 100, 480);
					}
				}
			break;
		}
		/*These conditionals determine which animations to play*/
		if(aradonAttack || dirkAttack)
		{
			slashAnim.setAutoUpdate(true);
			slashAnim.setLooping(false);
			slashAnim.setPingPong(true);
			slashAnim.draw(250, 350, 80, 80);			
		}
		if(aradonAttack)
		{
			
			AradonHit = InGameState.TEAM[0].getHit();
			AradonHit.setAutoUpdate(true);
			AradonHit.setLooping(false);
			AradonHit.draw(650, 300, 64, 64);
		}
		if(dirkAttack)
		{
			DirkHit = InGameState.TEAM[1].getHit();
			DirkHit.setAutoUpdate(true);
			DirkHit.setLooping(false);
			DirkHit.draw(650, 400, 64, 64);
		}
		if(!aradonAttack)
		{
			
			Aradon.draw(650, 300, 2);
			AradonHit.restart();
		}
		if(!dirkAttack)
		{
			Dirk.draw(650, 400, 2);
			DirkHit.restart();
		}
		/*these are all the strings that can be drawn*/
		g.drawString(battleMessage1, 200, 60);
		g.drawString(battleMessage2, 200, 80);
		g.drawString(battleMessage3, 200, 100);
		g.drawString(battleMessage4, 200, 120);
		g.drawString(battleMessage5, 200, 140);
		g.drawString(battleMessage6, 200, 160);
		g.drawString("Potions - "+InGameState.TEAM[0].getPotions(), 650, 20);
	}

	/*updating the battle messages based on the condition of the battle and using all user input*/
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		Input input = gc.getInput();
		if(battleOver != true && input.isKeyPressed(Input.KEY_ENTER))
		{
			if(aradonTurn == true)
			{
				battleMessage1 = "Aradon's Turn";
				battleMessage2 = "1 - Attack Monster 1";
				battleMessage3 = "2 - Attack Monster 2";
				battleMessage4 = "3 - Attack Monster 3";
				battleMessage5 = "4 - Use Potion";
				battleMessage6 = "5 - Escape";
			}
			else if(dirkTurn == true)
			{
				battleMessage1 = "Dirk's Turn";
				battleMessage2 = "1 - Attack Monster 1";
				battleMessage3 = "2 - Attack Monster 2";
				battleMessage4 = "3 - Attack Monster 3";
				battleMessage5 = "4 - Use Potion";
				battleMessage6 = "5 - Escape";
			}
		}
		else if(battleOver == true && playerWon == true && input.isKeyDown(Input.KEY_ENTER))
		{
			aradonTurn = false;
			dirkTurn = false;
			theirTurn = false;
			battleMus.stop();
			if(victoryMusic.playing() == false)
				victoryMusic.play();
			battleMessage1 = "";
			battleMessage2 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "PRESS SPACE BAR + ENTER KEY";
			battleMessage3 = "You are victorious!";
			if(input.isKeyDown(Input.KEY_SPACE))
			{
				this.escape(sbg);
			}
		}
		else if(battleOver == true && playerWon == false && input.isKeyDown(Input.KEY_ENTER))
		{
			aradonTurn = false;
			dirkTurn = false;
			theirTurn = false;
			battleMessage1 = "";
			battleMessage2 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "";
			battleMessage3 = "You have lost!";
			if(input.isKeyPressed(Input.KEY_ENTER))
			{
				sbg.enterState(5);
			}
			
		}
		else if(aradonTurn)
		{
			if(input.isKeyPressed(Input.KEY_1))
			{
				slashAnim.restart();
				aradonAttack = true;
				attackSound.play();
				this.attack(InGameState.TEAM[0], InGameState.TEAM[1], input, 0);
				aradonTurn = false;
				if(InGameState.TEAM[1].getAlive() == true)
					dirkTurn = true;
				else
					theirTurn = true;
			}
			if(input.isKeyPressed(Input.KEY_2) && numMonsters > 1)
			{
				{
					slashAnim.restart();
					aradonAttack = true;
					attackSound.play();
					this.attack(InGameState.TEAM[0], InGameState.TEAM[1], input, 1);
					aradonTurn = false;
					if(InGameState.TEAM[1].getAlive() == true)
						dirkTurn = true;
					else
						theirTurn = true;
				}
			}
			if(input.isKeyPressed(Input.KEY_3) && numMonsters > 2)
			{
				{
					slashAnim.restart();
					aradonAttack = true;
					attackSound.play();
					this.attack(InGameState.TEAM[0], InGameState.TEAM[1], input, 2);
					aradonTurn = false;
					if(InGameState.TEAM[1].getAlive() == true)
						dirkTurn = true;
					else
						theirTurn = true;
				}
			}
			if(input.isKeyPressed(Input.KEY_4))
			{
				if(InGameState.TEAM[0].getPotions() > 0)
				{
					InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP()+InGameState.TEAM[0].getLevel()*10);
					if(InGameState.TEAM[0].getCurrentHP() > InGameState.TEAM[0].getMaxHP())
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getMaxHP());
					battleMessage1 = "";
					battleMessage2 = "";
					battleMessage4 = "";
					battleMessage5 = "";
					battleMessage6 = "";
					battleMessage3 = InGameState.TEAM[0].getName()+" feels the life returning to him!";
					InGameState.TEAM[0].usePotion();
				}
				else
				{
					battleMessage1 = "";
					battleMessage2 = "";
					battleMessage4 = "";
					battleMessage5 = "";
					battleMessage6 = "";
					battleMessage3 = "You don't have any potions!";
					
				}
				aradonTurn = false;
				if(InGameState.TEAM[1].getAlive() == true)
					dirkTurn = true;
				else
					theirTurn = true;
			}
			if(input.isKeyPressed(Input.KEY_5))
			{
				this.escape(sbg);
			}
			if(kills == numMonsters)
			{
				playerWon = true;
				battleOver = true;
			}
				
		}
		else if(dirkTurn)
		{
			if(input.isKeyPressed(Input.KEY_1))
			{
				slashAnim.restart();
				dirkAttack = true;
				attackSound.play();
				this.attack(InGameState.TEAM[1], InGameState.TEAM[0], input, 0);
				theirTurn = true;
				dirkTurn = false;
			}
			if(input.isKeyPressed(Input.KEY_2) && numMonsters > 1)
			{
				{
					slashAnim.restart();
					dirkAttack = true;
					attackSound.play();
					this.attack(InGameState.TEAM[1], InGameState.TEAM[0], input, 1);
					theirTurn = true;
					dirkTurn = false;
				}
			}
			if(input.isKeyPressed(Input.KEY_3) && numMonsters > 2)
			{
				{
					slashAnim.restart();
					dirkAttack = true;
					attackSound.play();
					this.attack(InGameState.TEAM[1], InGameState.TEAM[0], input, 2);
					theirTurn = true;
					dirkTurn = false;
				}
			}
			if(input.isKeyPressed(Input.KEY_4))
			{
				if(InGameState.TEAM[0].getPotions() > 0)
				{
					InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP()+InGameState.TEAM[1].getLevel()*10);
					if(InGameState.TEAM[1].getCurrentHP() > InGameState.TEAM[1].getMaxHP())
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getMaxHP());
					battleMessage1 = "";
					battleMessage2 = "";
					battleMessage4 = "";
					battleMessage5 = "";
					battleMessage6 = "";
					battleMessage3 = InGameState.TEAM[1].getName()+" feels the life returning to him!";
					InGameState.TEAM[0].usePotion();
				}
				else
				{
					battleMessage1 = "";
					battleMessage2 = "";
					battleMessage4 = "";
					battleMessage5 = "";
					battleMessage6 = "";
					battleMessage3 = "You don't have any potions!";					
				}
				theirTurn = true;
				dirkTurn = false;
			}
			if(input.isKeyPressed(Input.KEY_5))
			{
				this.escape(sbg);
			}
			if(kills == numMonsters)
			{
				playerWon = true;
				battleOver = true;
			}
				
		}
		else if(theirTurn)
		{
			System.out.println("MonsterTurn");
			if(input.isKeyDown(Input.KEY_ENTER))
			{
				aradonAttack = false;
				dirkAttack = false;
				attackSound.play();
				this.monsterTurn();
			}
			if(InGameState.TEAM[0].getCurrentHP() <= 0 && InGameState.TEAM[1].getCurrentHP() <= 0)
				battleOver = true;
		}
	}
	
	/**
	 * this method forms all the enemies and places them in the proper array based on location
	 * @throws SlickException
	 */
	public void formEnemy() throws SlickException
	{	
		if(InGameState.currentMap.equals("colosseum"))
		{
			switchCase = 0;
			monType = "gladiator";
			background = new Image("/res/colosseumBackground.png");
			for(int i = 0; i < randInt; i++)
			{
				glads[i] = new gladiator();
			} 
		}
		else if(InGameState.currentMap.equals("outsideCol"))
		{
			switchCase = 1;
			monType = "out";
			background = new Image("/res/grassBattle.png");
			System.out.println("randInt is "+randInt);
			for(int i = 0; i < randInt; i++)
			{
				outMon[i] = new outMonster();
			} 
		}
		else if(InGameState.currentMap.equals("field"))
		{
			switchCase = 2;
			monType = "field";
			background = new Image("/res/grassBattle.png");
			for(int i = 0; i < randInt; i++)
			{
				fieldMon[i] = new fieldMonster();
			}
		}
		else if(InGameState.currentMap.equals("cliffs"))
		{
			switchCase = 3;
			monType = "cliffs";
			background = new Image("/res/cliffsBackground.png");
			for(int i = 0; i < randInt; i++)
			{
				cliffMon[i] = new cliffsMonster();
			}
		}
		else if(InGameState.currentMap.equals("cave"))
		{
			switchCase = 4;
			monType = "cave";
			background = new Image("/res/caveBackground.png");
			for(int i = 0; i < randInt; i++)
			{
				caveMon[i] = new caveMonster();
			}
		}
	}

	/*returns this game state's unique ID*/
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}
	
	/*
	 * this method causes a monster to randomly decide which player to attack first, but attacks the other
	 * if the first choice is dead. the method repeats itself for each possible type of monster.
	 */
	public void monsterTurn()
	{
		numTurns++;
		battleMessage1 = "";
		battleMessage2 = "";
		battleMessage4 = "";
		battleMessage5 = "";
		battleMessage6 = "Press Enter";
		battleMessage3 = "";
		int attack;
		int random;
		random = rand.nextInt(2);
		if(monType.equals("out")){
		switch(random)
		{
			case 0:
				if(InGameState.TEAM[0].getAlive() == true && outMon[0].getAlive() == true)
				{
					System.out.println("Attacking "+InGameState.TEAM[0].getName());
					attack = outMon[0].getAttack() - InGameState.TEAM[0].getDefense();
					if(attack < 0)
						attack = 0;
					battleMessage2 = outMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
					InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
					if(InGameState.TEAM[0].getCurrentHP() < 0)
						InGameState.TEAM[0].setCurrentHP(0);
					if(InGameState.TEAM[0].getCurrentHP() <= 0)
						InGameState.TEAM[0].setAlive(false);
				}
				else if(outMon[0].getAlive() == true)
				{
					System.out.println("Attacking "+InGameState.TEAM[1].getName());
					attack = outMon[0].getAttack() - InGameState.TEAM[1].getDefense();
					if(attack < 0)
						attack = 0;
					battleMessage2 = outMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
					InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
					if(InGameState.TEAM[1].getCurrentHP() < 0)
						InGameState.TEAM[1].setCurrentHP(0);
					if(InGameState.TEAM[1].getCurrentHP() <= 0)
						InGameState.TEAM[1].setAlive(false);
				}
				break;
			case 1:
				if(InGameState.TEAM[1].getAlive() == true && outMon[0].getAlive() == true)
				{
					System.out.println("Attacking "+InGameState.TEAM[1].getName());
					attack = outMon[0].getAttack() - InGameState.TEAM[1].getDefense();
					if(attack < 0)
						attack = 0;
					battleMessage2 = outMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
					InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
					if(InGameState.TEAM[1].getCurrentHP() < 0)
						InGameState.TEAM[1].setCurrentHP(0);
					if(InGameState.TEAM[1].getCurrentHP() <= 0)
						InGameState.TEAM[1].setAlive(false);
				}
				else if (outMon[0].getAlive() == true)
				{
					System.out.println("Attacking "+InGameState.TEAM[0].getName());
					attack = outMon[0].getAttack() - InGameState.TEAM[0].getDefense();
					if(attack < 0)
						attack = 0;
					battleMessage2 = outMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
					InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
					if(InGameState.TEAM[0].getCurrentHP() < 0)
						InGameState.TEAM[0].setCurrentHP(0);
					if(InGameState.TEAM[0].getCurrentHP() <= 0)
						InGameState.TEAM[0].setAlive(false);
				}
				break;
		}
		if(numMonsters > 1)
		{
			random = rand.nextInt(2);
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && outMon[1].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = outMon[1].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage3 = outMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(outMon[1].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = outMon[1].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage3 = outMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && outMon[1].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = outMon[1].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage3 = outMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(outMon[1].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = outMon[1].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage3 = outMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
		}
		if(numMonsters > 2)
		{
			random = rand.nextInt(2);
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && outMon[2].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = outMon[2].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage4 = outMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(outMon[2].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = outMon[2].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage4 = outMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && outMon[2].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = outMon[2].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage4 = outMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(outMon[2].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = outMon[2].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage4 = outMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
		}
		}
		if(monType.equals("field")){
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && fieldMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = fieldMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = fieldMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(fieldMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = fieldMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = fieldMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && fieldMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = fieldMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = fieldMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(fieldMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = fieldMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = fieldMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
			if(numMonsters > 1)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && fieldMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = fieldMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = fieldMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(fieldMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = fieldMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = fieldMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && fieldMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = fieldMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = fieldMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(fieldMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = fieldMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = fieldMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			if(numMonsters > 2)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && fieldMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = fieldMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = fieldMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(fieldMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = fieldMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = fieldMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && fieldMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = fieldMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = fieldMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(fieldMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = fieldMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = fieldMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			}
		if(monType.equals("cliffs")){
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && cliffMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = cliffMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = cliffMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(cliffMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = cliffMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = cliffMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && cliffMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = cliffMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = cliffMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(cliffMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = cliffMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = cliffMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
			if(numMonsters > 1)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && cliffMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = cliffMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = cliffMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(cliffMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = cliffMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = cliffMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && cliffMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = cliffMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = cliffMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(cliffMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = cliffMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = cliffMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			if(numMonsters > 2)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && cliffMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = cliffMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = cliffMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(cliffMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = cliffMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = cliffMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && cliffMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = cliffMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = cliffMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(cliffMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = cliffMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = cliffMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			}
		if(monType.equals("cave")){
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && caveMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = caveMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = caveMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(caveMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = caveMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = caveMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && caveMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = caveMon[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = caveMon[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(caveMon[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = caveMon[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = caveMon[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
			if(numMonsters > 1)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && caveMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = caveMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = caveMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(caveMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = caveMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = caveMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && caveMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = caveMon[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = caveMon[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(caveMon[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = caveMon[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = caveMon[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			if(numMonsters > 2)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && caveMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = caveMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = caveMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(caveMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = caveMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = caveMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && caveMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = caveMon[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = caveMon[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(caveMon[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = caveMon[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = caveMon[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			}
		if(monType.equals("gladiator")){
			switch(random)
			{
				case 0:
					if(InGameState.TEAM[0].getAlive() == true && glads[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = glads[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = glads[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					else if(glads[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = glads[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = glads[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					break;
				case 1:
					if(InGameState.TEAM[1].getAlive() == true && glads[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[1].getName());
						attack = glads[0].getAttack() - InGameState.TEAM[1].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = glads[0].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
						InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
						if(InGameState.TEAM[1].getCurrentHP() < 0)
							InGameState.TEAM[1].setCurrentHP(0);
						if(InGameState.TEAM[1].getCurrentHP() <= 0)
							InGameState.TEAM[1].setAlive(false);
					}
					else if(glads[0].getAlive() == true)
					{
						System.out.println("Attacking "+InGameState.TEAM[0].getName());
						attack = glads[0].getAttack() - InGameState.TEAM[0].getDefense();
						if(attack < 0)
							attack = 0;
						battleMessage2 = glads[0].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
						InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
						if(InGameState.TEAM[0].getCurrentHP() < 0)
							InGameState.TEAM[0].setCurrentHP(0);
						if(InGameState.TEAM[0].getCurrentHP() <= 0)
							InGameState.TEAM[0].setAlive(false);
					}
					break;
			}
			if(numMonsters > 1)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && glads[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = glads[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = glads[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(glads[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = glads[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = glads[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && glads[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = glads[1].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = glads[1].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(glads[1].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = glads[1].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage3 = glads[1].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
			if(numMonsters > 2)
			{
				random = rand.nextInt(2);
				switch(random)
				{
					case 0:
						if(InGameState.TEAM[0].getAlive() == true && glads[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = glads[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = glads[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						else if(glads[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = glads[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = glads[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						break;
					case 1:
						if(InGameState.TEAM[1].getAlive() == true && glads[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[1].getName());
							attack = glads[2].getAttack() - InGameState.TEAM[1].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = glads[2].getName()+" attacks "+InGameState.TEAM[1].getName()+" for "+attack+" damage.";
							InGameState.TEAM[1].setCurrentHP(InGameState.TEAM[1].getCurrentHP() - attack);
							if(InGameState.TEAM[1].getCurrentHP() < 0)
								InGameState.TEAM[1].setCurrentHP(0);
							if(InGameState.TEAM[1].getCurrentHP() <= 0)
								InGameState.TEAM[1].setAlive(false);
						}
						else if(glads[2].getAlive() == true)
						{
							System.out.println("Attacking "+InGameState.TEAM[0].getName());
							attack = glads[2].getAttack() - InGameState.TEAM[0].getDefense();
							if(attack < 0)
								attack = 0;
							battleMessage4 = glads[2].getName()+" attacks "+InGameState.TEAM[0].getName()+" for "+attack+" damage.";
							InGameState.TEAM[0].setCurrentHP(InGameState.TEAM[0].getCurrentHP() - attack);
							if(InGameState.TEAM[0].getCurrentHP() < 0)
								InGameState.TEAM[0].setCurrentHP(0);
							if(InGameState.TEAM[0].getCurrentHP() <= 0)
								InGameState.TEAM[0].setAlive(false);
						}
						break;
				}
			}
		}
		/*switches turns*/
		if(InGameState.TEAM[0].getAlive() == true)
			aradonTurn = true;
		else
			dirkTurn = true;
	}
	
	/*
	 * this method causes the player to attack the selected monster--updating all pertinent data accordingly
	 */
	public void attack(battler player, battler player2, Input input, int mon)
	{
		int attack;
		int monHP;
		if(monType.equals("out"))
		{
			if(outMon[mon].getAlive() == false)
			{
				battleMessage1 = "";
				battleMessage3 = "where a monster used to be.";
				battleMessage4 = "";
				battleMessage5 = "";
				battleMessage6 = "                      Press Enter";
				battleMessage2 = player.getName()+" swings violently at the place ";
			}
			else{
			System.out.println("Attacking monster "+(mon+1));
			System.out.println(outMon[mon].getName()+" Old HP - "+outMon[mon].getCurrentHP());
			attack = player.getAttack() - outMon[mon].getDefense();
			if(attack < 0)
				attack = 0;
			monHP = outMon[mon].getCurrentHP();
			monHP -= attack;
			outMon[mon].setCurrentHP(monHP);
			battleMessage1 = "";
			battleMessage3 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "                      Press Enter";
			battleMessage2 = ""+player.getName()+" attacks "+outMon[mon].getName()+" for "+attack+" damage.";
			System.out.println(outMon[mon].getName()+" New HP - "+outMon[mon].getCurrentHP());
			if(monHP <= 0)
			{
				battleMessage2 = outMon[mon].getName()+" killed";
				battleMessage3 = "Earned "+numTurns*10+" gold.";
				InGameState.TEAM[0].setGold(numTurns*10);
				battleMessage4 = player.getName()+" gains "+(outMon[mon].getLevel()*10)+" experience points.";
				outMon[mon].setAlive(false);
				kills++;
				player.addExperience(outMon[mon].getLevel());
				if(player.checkLevel())
				{
					player.advLevel();
					battleMessage5 = player.getName()+" leveled up!";
				}
				if(player2.getAlive()){
				player2.addExperience(outMon[mon].getLevel());
				if(player2.checkLevel())
				{
					player2.advLevel();
					battleMessage6 = player2.getName()+" leveled up!";
				}
				}
			}
			}
		}
		
		if(monType.equals("field"))
		{
			if(fieldMon[mon].getAlive() == false)
			{
				battleMessage1 = "";
				battleMessage3 = "where a monster used to be.";
				battleMessage4 = "";
				battleMessage5 = "";
				battleMessage6 = "                      Press Enter";
				battleMessage2 = player.getName()+" swings violently at the place";
			}
			else{
			System.out.println("Attacking monster "+(mon+1));
			System.out.println(fieldMon[mon].getName()+" Old HP - "+fieldMon[mon].getCurrentHP());
			attack = player.getAttack() - fieldMon[mon].getDefense();
			if(attack < 0)
				attack = 0;
			monHP = fieldMon[mon].getCurrentHP();
			monHP -= attack;
			fieldMon[mon].setCurrentHP(monHP);
			battleMessage1 = "";
			battleMessage3 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "                      Press Enter";
			battleMessage2 = ""+player.getName()+" attacks "+fieldMon[mon].getName()+" for "+attack+" damage.";
			System.out.println(fieldMon[mon].getName()+" New HP - "+fieldMon[mon].getCurrentHP());
			if(monHP <= 0)
			{
				battleMessage2 = fieldMon[mon].getName()+" killed";
				battleMessage3 = "Earned "+numTurns*10+" gold.";
				InGameState.TEAM[0].setGold(numTurns*10);
				battleMessage4 = player.getName()+" gains "+(fieldMon[mon].getLevel()*10)+" experience points.";
				fieldMon[mon].setAlive(false);
				kills++;
				player.addExperience(fieldMon[mon].getLevel());
				if(player.checkLevel())
				{
					player.advLevel();
					battleMessage5 = player.getName()+" leveled up!";
				}
				if(player2.getAlive()){
				player2.addExperience(fieldMon[mon].getLevel());
				if(player2.checkLevel())
				{
					player2.advLevel();
					battleMessage6 = player2.getName()+" leveled up!";
				}
				}
			}
			}
		}
		if(monType.equals("cliffs"))
		{
			if(cliffMon[mon].getAlive() == false)
			{
				battleMessage1 = "";
				battleMessage3 = "where a monster used to be.";
				battleMessage4 = "";
				battleMessage5 = "";
				battleMessage6 = "                      Press Enter";
				battleMessage2 = player.getName()+" swings violently at the place";
			}
			else{
			System.out.println("Attacking monster "+(mon+1));
			System.out.println(cliffMon[mon].getName()+" Old HP - "+cliffMon[mon].getCurrentHP());
			attack = player.getAttack() - cliffMon[mon].getDefense();
			if(attack < 0)
				attack = 0;
			monHP = cliffMon[mon].getCurrentHP();
			monHP -= attack;
			cliffMon[mon].setCurrentHP(monHP);
			battleMessage1 = "";
			battleMessage3 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "                      Press Enter";
			battleMessage2 = ""+player.getName()+" attacks "+cliffMon[mon].getName()+" for "+attack+" damage.";
			System.out.println(cliffMon[mon].getName()+" New HP - "+cliffMon[mon].getCurrentHP());
			if(monHP <= 0)
			{
				battleMessage2 = cliffMon[mon].getName()+" killed";
				battleMessage3 = "Earned "+numTurns*10+" gold.";
				InGameState.TEAM[0].setGold(numTurns*10);
				battleMessage4 = player.getName()+" gains "+(cliffMon[mon].getLevel()*10)+" experience points.";
				cliffMon[mon].setAlive(false);
				kills++;
				player.addExperience(cliffMon[mon].getLevel());
				if(player.checkLevel())
				{
					player.advLevel();
					battleMessage5 = player.getName()+" leveled up!";
				}
				if(player2.getAlive()){
				player2.addExperience(cliffMon[mon].getLevel());
				if(player2.checkLevel())
				{
					player2.advLevel();
					battleMessage6 = player2.getName()+" leveled up!";
				}
				}
			}
			}
		}
		if(monType.equals("cave"))
		{
			if(caveMon[mon].getAlive() == false)
			{
				battleMessage1 = "";
				battleMessage3 = "where a monster used to be.";
				battleMessage4 = "";
				battleMessage5 = "";
				battleMessage6 = "                      Press Enter";
				battleMessage2 = player.getName()+" swings violently at the place ";
			}
			else{
			System.out.println("Attacking monster "+(mon+1));
			System.out.println(caveMon[mon].getName()+" Old HP - "+caveMon[mon].getCurrentHP());
			attack = player.getAttack() - caveMon[mon].getDefense();
			if(attack < 0)
				attack = 0;
			monHP = caveMon[mon].getCurrentHP();
			monHP -= attack;
			caveMon[mon].setCurrentHP(monHP);
			battleMessage1 = "";
			battleMessage3 = "";
			battleMessage4 = "";
			battleMessage5 = "";
			battleMessage6 = "                      Press Enter";
			battleMessage2 = ""+player.getName()+" attacks "+caveMon[mon].getName()+" for "+attack+" damage.";
			System.out.println(caveMon[mon].getName()+" New HP - "+caveMon[mon].getCurrentHP());
			if(monHP <= 0)
			{
				battleMessage2 = caveMon[mon].getName()+" killed";
				battleMessage3 = "Earned "+numTurns*10+" gold.";
				InGameState.TEAM[0].setGold(numTurns*10);
				battleMessage4 = player.getName()+" gains "+(caveMon[mon].getLevel()*10)+" experience points.";
				caveMon[mon].setAlive(false);
				kills++;
				player.addExperience(caveMon[mon].getLevel());
				if(player.checkLevel())
				{
					player.advLevel();
					battleMessage5 = player.getName()+" leveled up!";
				}
				if(player2.getAlive()){
				player2.addExperience(caveMon[mon].getLevel());
				if(player2.checkLevel())
				{
					player2.advLevel();
					battleMessage6 = player2.getName()+" leveled up!";
				}
				}
			}
			}
		}
			if(monType.equals("gladiator"))
			{
				System.out.println("I said, In Attack Loop");
				System.out.println(glads[mon].getName()+" Old HP - "+glads[mon].getCurrentHP());
				attack = player.getAttack() - glads[mon].getDefense();
				if(attack < 0)
					attack = 0;
				monHP = glads[mon].getCurrentHP();
				monHP -= attack;
				glads[mon].setCurrentHP(monHP);
				battleMessage1 = "";
				battleMessage3 = "";
				battleMessage4 = "";
				battleMessage5 = "";
				battleMessage6 = "                      Press Enter";
				battleMessage2 = ""+player.getName()+" attacks "+glads[mon].getName()+" for "+attack+" damage.";
				System.out.println(glads[mon].getName()+" New HP - "+glads[mon].getCurrentHP());
				if(monHP <= 0)
				{
					battleMessage2 = glads[mon].getName()+" killed";
					battleMessage3 = "Earned "+numTurns*10+" gold.";
					InGameState.TEAM[0].setGold(numTurns*20);
					battleMessage4 = player.getName()+" gains "+(glads[mon].getLevel()*100)+" experience points.";
					glads[mon].setAlive(false);
					InGameState.gladiatorsDefeated += 1;
					kills++;
					player.addBossExperience(glads[mon].getLevel());
					if(player.checkLevel())
					{
						player.advLevel();
						battleMessage5 = player.getName()+" leveled up!";
					}
					if(player2.getAlive()){
					player2.addBossExperience(glads[mon].getLevel());
					if(player2.checkLevel())
					{
						player2.advLevel();
						battleMessage6 = player2.getName()+" leveled up!";
					}
					}
				}
			}
		
		
	}
	
	/*
	 * returns to the main game state
	 */
	public void escape(StateBasedGame sbg)
	{
		sbg.enterState(2);
	}
}
