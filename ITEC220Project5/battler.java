import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * battler.java
 * @author Stephen Wright
 *The class for the player's fighting characters
 */
public class battler 
{
	/*player's current hit points*/
	private int currentHP;
	/*player's maximum hit points*/
	private int maxHP;
	/*player's attack power*/
	private int attack;
	/*player's defense power*/
	private int defense;
	/*player's speed*/
	private int speed;
	/*number of potions the player has*/
	private int potions;
	/*amount of gold the player has*/
	private int gold;
	/*player's current level*/
	private int level;
	/*player's current experience points*/
	private int xp;
	/*number of experience points needed to advance in level*/
	private int maxXP;
	/*whether or not the player is alive*/
	private boolean alive;
	/*the player's name*/
	private String name;
	/*the player's appearance on the screen*/
	private Image image;
	/*the next 5 images are frames for the battle animation*/
	private Image anim1;
	private Image anim2;
	private Image anim3;
	private Image anim4;
	private Image anim5;
	/*the battle animation*/
	private Animation hit;
	/*the sheet holding the images for the animation*/
	private SpriteSheet sheet;
	
	/**
	 * Constructor for the battler
	 * @throws SlickException
	 */
	public battler(int h, int a, int d, int s, int l, String n) throws SlickException
	{
		maxHP = h;
		currentHP = maxHP;
		attack = a;
		defense = d;
		speed = s;
		level = l;
		name = n;
		xp = 0;
		maxXP = 10;
		alive = true;
		/*this conditional insures that the right images end up with the right characters*/
		if(name.equals("Aradon")){
			sheet = new SpriteSheet("/res/aradonBattle.png", 32, 32);
			potions = 5;
			gold = 100;
		}
		else{
			sheet = new SpriteSheet("/res/dirkBattle.png", 32, 32);
			potions = 0;
			gold = 0;
		}
		image = sheet.getSprite(0, 0);
		anim1 = sheet.getSprite(1, 0);
		anim2 = sheet.getSprite(2, 0);
		anim3 = sheet.getSprite(0, 1);
		anim4 = sheet.getSprite(1, 1);
		anim5 = sheet.getSprite(2, 1);
		hit = new Animation(new Image[]{anim1, anim2, anim3, anim4, anim5}, 100, false);
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
	
	public void buyPotions()
	{
		potions += 1;
	}
	
	public int getPotions()
	{
		return potions;
	}
	
	public void usePotion()
	{
		potions -= 1;
	}
	
	public int getGold()
	{
		return gold;
	}
	
	public void setGold(int x)
	{
		gold += x;
	}
	
	public void spendGold()
	{
		gold -= 50;
	}
	
	public int getMaxHP()
	{
		return maxHP;
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
	
	public int getLevel()
	{
		return level;
	}
	
	
	public int getExperience()
	{
		return xp;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	public Animation getHit()
	{
		return hit;
	}
	
	public boolean getAlive()
	{
		return alive;
	}
	
	public void setAlive(boolean setter)
	{
		alive = setter;
	}
	
	public int getXP()
	{
		return xp;
	}
	
	public int getMaxXP()
	{
		return maxXP;
	}
	
	public void addExperience(int xprnc)
	{
		xp += xprnc*10;
		
	}
	
	public void addBossExperience(int xprnc)
	{
		xp += xprnc*100;
		
	}
	/*checks to see if the player is ready to level up*/
	public boolean checkLevel()
	{
		boolean lvlup;
		if(xp >= maxXP)
			lvlup = true;
		else
			lvlup = false;
		return lvlup;
	}
	/*advances the player a level increasing their stats*/
	public void advLevel()
	{
		level += 1;
		maxHP += 5;
		if(level <= 5)
			maxXP += 40;
		else
			maxXP += 80;
		currentHP = maxHP;
		attack += 3;
		defense += 1;
		speed += 2;
		xp = 0;
	}
	
	
	
}
