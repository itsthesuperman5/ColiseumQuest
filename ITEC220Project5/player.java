import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;




/**
 * player.java
 * @author Stephen Wright
 *The class containing the moveable player on the exploration map
 */
public class player
{
	/*The sheet holding the images needed*/
	private SpriteSheet sheet = null;
	/*All the next images are used to make the animations for walking around the map*/
	Image img1 = null;
	Image img2 = null;
	Image img3 = null;
	Image img4 = null;
	Image img5 = null;
	Image img6 = null;
	Image img7 = null;
	Image img8 = null;
	Image img9 = null;
	Image img10 = null;
	Image img11 = null;
	Image img12 = null;
	/*the animations for moving around the map or standing idle*/
	Animation sprite = null;
	Animation idle = null;
	Animation left = null;
	Animation right = null;
	Animation down = null;
	Animation up = null;
	
	/**
	 * The Constructor which grabs all the images and makes all the animations
	 * @throws SlickException
	 */
	public player(String n) throws SlickException
	{
		sheet = new SpriteSheet("/res/movePlayer.png", 32, 32);
		img1 = sheet.getSprite(0,0);
		img2 = sheet.getSprite(1,0);
		img3 = sheet.getSprite(2,0);
		img4 = sheet.getSprite(3,0);
		img5 = sheet.getSprite(4,0);
		img6 = sheet.getSprite(5,0);
		img7 = sheet.getSprite(0,1);
		img8 = sheet.getSprite(1,1);
		img9 = sheet.getSprite(2,1);
		img10 = sheet.getSprite(3,1);
		img11 = sheet.getSprite(4,1);
		img12 = sheet.getSprite(5,1);
		idle = new Animation(new Image[]{img5, img6}, 550);
		right = new Animation(new Image[]{img10, img11, img12, img11}, 250, false);
		down = new Animation(new Image[]{img4, img5, img6, img5}, 250, false);
		left = new Animation(new Image[]{img7, img8, img9, img8}, 250, false);
		up = new Animation(new Image[]{img1, img2, img3, img2}, 250, false);
	}
	/*The Getters*/
	public Animation getIdle()
	{
		return idle;
	}
	
	public Animation getLeft()
	{
		return left;
	}
	
	public Animation getRight()
	{
		return right;
	}
	
	public Animation getDown()
	{
		return down;
	}
	
	public Animation getUp()
	{
		return up;
	}
}
