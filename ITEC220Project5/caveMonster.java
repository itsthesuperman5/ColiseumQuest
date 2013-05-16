/**
 * 
 */
import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * caveMonster.java
 * @author Stephen Wright
 *The class for cave monsters
 */
public class caveMonster 
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
	
	/**
	 * Constructor
	 * @throws SlickException
	 */
	public caveMonster() throws SlickException
	{
		alive = true;
		rand = new Random();
		level = rand.nextInt(2) + 8;
		this.create(level);
	}
	
	/**
	 *Creates a cave monster based on randomly generated numbers
	 * @throws SlickException
	 */
	public void create(int y) throws SlickException
	{
		switch(y){
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
		image = new Image("/res/caveBear.png");
		name = "Cave Bear";
		
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
