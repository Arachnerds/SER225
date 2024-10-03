package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;

public class JumpPoint extends EnhancedMapTile {

    private float hitboxX;
    private float hitboxY;
    private int hitboxWidth;
    private int hitboxHeight;

    //Constructor for a jump point with a centered hitbox
    public JumpPoint(Point location){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxX = -50;
        hitboxY = -50;
        hitboxWidth = 100;
        hitboxHeight = 100;
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
    }

    //Constructor for a jump point with a "left" or "right" hitbox
    public JumpPoint(Point location, String side){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxWidth = 70;
        hitboxHeight = 100;
        hitboxY = -50;
        if(side.equals("left")){
            hitboxX = -50; 
        }
        else if(side.equals("right")){
            hitboxX = 0;
        }

        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
        
        
    }
    
    //Constructor for a jump point with a custom hitbox
    public JumpPoint(Point location, float hitboxX, float hitboxY, int hitboxWidth, int hitboxHeight){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        this.hitboxX = hitboxX;
        this.hitboxY = hitboxY;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
    }

    @Override
    public void update(Player player) {
        super.update(player);
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));

        //A test button to print hotbox position
        /* if(Keyboard.isKeyDown(Key.T)){
            System.out.println(hitboxX + " " + hitboxY + " " + hitboxWidth + " " + hitboxHeight);
        }
 */
        if (intersects(player)) {
            this.setCurrentAnimationName("inRange");

            if(Keyboard.isKeyDown(Key.E)){
                this.setCurrentAnimationName("Webbed");
                player.moveX(this.xDist(player.getX()));
                player.moveY(this.xDist(player.getY()));
                System.out.println(hitboxX + " " + hitboxY + " " + hitboxWidth + " " + hitboxHeight);
            }

        }  
        else{
            this.setCurrentAnimationName("DEFAULT");
        }

    }
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(hitboxX,hitboxY,hitboxWidth,hitboxHeight)
                        .build(),
            });

            put("inRange", new Frame[] {
                
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(hitboxX,hitboxY,hitboxWidth,hitboxHeight)
                        .build(),
            });

            put("Webbed", new Frame[] {
                
                new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
                        .withScale(3)
                        .withBounds(hitboxX,hitboxY,hitboxWidth,hitboxHeight)
                        .build(),
            });

        }};
    }

    public float xDist(float x){
        return this.getX() - x;
    }

    public float yDist(float y){
        return this.getY() - y;
    }

    //A testing method that shows the location of the hitbox
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }


}
