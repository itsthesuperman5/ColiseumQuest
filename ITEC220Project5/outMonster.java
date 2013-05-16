/**
 * 
 */
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * outMonster.java
 * @author Stephen Wright
 *Class for the monsters just outside of the coliseum
 */
public class outMonster 
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
	public outMonster() throws SlickException
	{
		alive = true;
		rand = new Random();
		randInt = rand.nextInt(4);
		level = rand.nextInt(3) + 1;
		this.create(randInt, level);
	}
	
	/**
	 * Creates the monster based on random numbers
	 * @throws SlickException
	 */
	public void create(int x, int y) throws SlickException
	{
		switch(y){
		case 1: hp = 12;
				currentHP = 12;
				attack = 2;
				defense = 0;
				speed = 2;
		break;
		case 2: hp = 18;
				currentHP = 18;
				attack = 4;
				defense = 1;
				speed = 4;
		break;
		case 3: hp = 24;
				currentHP = 24;
				attack = 6;
				defense = 2;
				speed = 6;
		break;
		}
		switch(x){
		case 0: image = new Image("/res/mouse.png");
		name = "Ratz";
		break;
		case 1: image = new Image("/res/raven.png");
		name = "Blackheart";
		break;
		case 2: image = new Image("/res/wolf.png");
		name = "Howler";
		break;
		case 3: image = new Image("/res/wyvern.png");
		name = "Sparken";
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
	
	public int getLevel()
	{
		return level;
	}

}
