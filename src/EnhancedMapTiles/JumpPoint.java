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

    private float hitboxX=-50;
    private float hitboxY=-50;
    private int hitboxWidth=100;
    private int hitboxHeight=100;

    //Constructor for a jump point with a centered hitbox
    public JumpPoint(Point location){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxX = -50;
        hitboxY = -50;
        hitboxWidth = 100;
        hitboxHeight = 100;
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
    }

    /* //Constructor for a jump point with a "left" or "right" hitbox
    public JumpPoint(Point location, String side){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxWidth = 50;
        hitboxHeight = 50;
        if(side.equals("left")){
            hitboxX = -50;
            hitboxY = -50;
        }
        else if(side.equals("right")){
            hitboxX = 0;
            hitboxY = 0;
        }
        
        
    }
    
    //Constructor for a jump point with a custom hitbox
    public JumpPoint(Point location, float hitboxX, float hitboxY, float hitboxWidth, float hitboxHeight){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        this.hitboxX = hitboxX;
        this.hitboxY = hitboxY;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
    } */

    @Override
    public void update(Player player) {
        super.update(player);
        //This line seems to largely fix it, but it feels kind of cheap.
        //this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
        //A test button to print hotbox position
        if(Keyboard.isKeyDown(Key.T)){
            System.out.println(hitboxX + " " + hitboxY + " " + hitboxWidth + " " + hitboxHeight);
        }

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
                        //.withBounds(-50,-50,100,100)
                        .withBounds(-50,-50,100,100)
                        .build(),
            });
                      // .withBounds(hitboxX, hitboxY, (int)hitboxWidth, (int)hitboxHeight)
            put("inRange", new Frame[] {
                
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(hitboxX,hitboxY,100,100)
                        .build(),
            });

            put("Webbed", new Frame[] {
                
                new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
                        .withScale(3)
                        .withBounds(hitboxX,-50,100,100)
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
