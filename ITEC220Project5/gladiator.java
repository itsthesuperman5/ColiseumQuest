import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * gladiator.java
 * @author Stephen Wright
 *The class for all the boss fighters in this game
 */
public class gladiator 
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
	/*monster's level*/
	private int level;
	/*whether or not a monster is alive*/
	private boolean alive;
	
	
	/**
	 * The constructor
	 * @throws SlickException
	 */
	public gladiator() throws SlickException
	{
		alive = true;
		this.create();
	}
	
	
	/**
	 * Creates the boss depending on how many of them you've defeated
	 * @throws SlickException
	 */
	public void create() throws SlickException
	{
		int x = InGameState.gladiatorsDefeated;
		switch(x){
		case 0: hp = 12;
				currentHP = 12;
				attack = 1;
				defense = 0;
				speed = 2;
				level = 1;
				image = new Image("/res/gladiator1.png");
				name = "Larry";
		break;
		case 1: hp = 38;
				currentHP = 38;
				attack = 8;
				defense = 2;
				speed = 4;
				level = 4;
				image = new Image("/res/gladiator2.png");
				name = "Steele";
		break;
		case 2: hp = 46;
				currentHP = 46;
				attack = 10;
				defense = 3;
				speed = 6;
				level = 6;
				image = new Image("/res/gladiator3.png");
				name = "Ryu";
		break;
		case 3: hp = 55;
				currentHP = 55;
				attack = 11;
				defense = 4;
				speed = 4;
				level = 8;
				image = new Image("/res/gladiator4.png");
				name = "Slice";
		break;
		case 4: hp = 70;
				currentHP = 70;
				attack = 16;
				defense = 2;
				speed = 6;
				level = 9;
				image = new Image("/res/caveMonster.png");
				name = "Mutant Bear";
		break;
		case 5: hp = 100;
			currentHP = 100;
			attack = 16;
			defense = 4;
			speed = 6;
			level = 10;
			image = new Image("/res/gladiator5.png");
			name = "Dymonicon";
		break;
		}
	}
	/*Getters and setters*/
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
