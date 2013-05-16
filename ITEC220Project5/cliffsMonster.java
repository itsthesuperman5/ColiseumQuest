/**
 * 
 */
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * cliffsMonster.java
 * @author Stephen Wright
 *Class for cliff monsters
 */
public class cliffsMonster 
{
	/*maximum hit points*/
	private int hp;
	/*current hit points*/
	private int currentHP;
	/*attack power*/
	private int attack;
	/*defense power*/
	private int defense;
	/*speed*/
	private int speed;
	/*Monster's name*/
	private String name;
	/*variable for monster's image*/
	private Image image = null;
	/*random number generator*/
	private Random rand;
	/*monster's level*/
	private int level;
	/*whether or not a monster is alive*/
	private boolean alive;
	/*random number for monster creation*/
	private int randInt;
	
	/**
	 * Constructor
	 * @throws SlickException
	 */
	public cliffsMonster() throws SlickException
	{
		alive = true;
		rand = new Random();
		randInt = rand.nextInt(4);
		level = rand.nextInt(3) + 7;
		this.create(randInt, level);
	}
	
	/**
	 * Creates the monster based on random numbers
	 * @throws SlickException
	 */
	public void create(int x, int y) throws SlickException
	{
		switch(y){
		case 7: hp = 43;
				currentHP = 43;
				attack = 14;
				defense = 6;
				speed = 14;
		break;
		case 8: hp = 47;
				currentHP = 47;
				attack = 16;
				defense = 7;
				speed = 16;
		break;
		case 9: hp = 51;
				currentHP = 51;
				attack = 18;
				defense = 8;
				speed = 18;
		break;
		}
		switch(x){
		case 0: image = new Image("/res/bronto.png");
		name = "Walker";
		break;
		case 1: image = new Image("/res/mammoth.png");
		name = "Mammoth";
		break;
		case 2: image = new Image("/res/rockTurtle.png");
		name = "Craggy";
		break;
		case 3: image = new Image("/res/trex.png");
		name = "Lizard King";
		break;
		}
	}
	/*Getters and Setters*/
	public int getCurrentHP()
	{
		return currentHP;
	}
		
	public void setCurrentHP(int x)
	{
		currentHP = x;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public int getMaxHP()
	{
		return hp;
	}
	
	public int getAttack()
	{
		return attack;
	}
	
	public int getDefense()
	{
		return defense;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public boolean getAlive()
	{
		return alive;
	}
	
	public void setAlive(boolean setter)
	{
		alive = setter;
	}

}