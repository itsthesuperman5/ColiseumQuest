import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;




/**
 * fieldMonster.java
 * @author Stephen Wright
 *Class for monsters in the field area
 */
public class fieldMonster
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
	public fieldMonster() throws SlickException
	{
		alive = true;
		rand = new Random();
		randInt = rand.nextInt(4);
		level = rand.nextInt(3) + 4;
		this.create(randInt, level);
		
	}
	
	/**
	 *Creates the monster based on random numbers
	 * @throws SlickException
	 */
	public void create(int x, int y) throws SlickException
	{
		switch(y){
		case 4: hp = 26;
				currentHP = 26;
				attack = 9;
				defense = 3;
				speed = 8;
		break;
		case 5: hp = 32;
				currentHP = 32;
				attack = 11;
				defense = 4;
				speed = 10;
		break;
		case 6: hp = 35;
				currentHP = 35;
				attack = 12;
				defense = 5;
				speed = 12;
		break;
		}
		switch(x){
		case 0: image = new Image("/res/mantis.png");
		name = "Mantis";
		break;
		case 1: image = new Image("/res/hawk.png");
		name = "Falcor";
		break;
		case 2: image = new Image("/res/dragon.png");
		name = "Immolator";
		break;
		case 3: image = new Image("/res/beetle.png");
		name = "Buzzy";
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
