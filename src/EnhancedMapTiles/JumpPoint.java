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
    private Float dx;
    private Float dy;
    private String startPosCode;

    // Constructor for a jump point with a centered hitbox
    public JumpPoint(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxX = -50;
        hitboxY = -50;
        hitboxWidth = 100;
        hitboxHeight = 100;
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
        startPosCode = "";
    }

    // Constructor for a jump point with a "left" or "right" hitbox
    public JumpPoint(Point location, String side) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        hitboxWidth = 70;
        hitboxHeight = 100;
        hitboxY = -50;
        if (side.equals("left")) {
            hitboxX = -50; 
        } else if (side.equals("right")) {
            hitboxX = 0;
        }
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
        startPosCode = "";
    }

    // Constructor for a jump point with a custom hitbox
    public JumpPoint(Point location, float hitboxX, float hitboxY, int hitboxWidth, int hitboxHeight) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("JumpPoint.png"), 16, 16), TileType.PASSABLE);
        this.hitboxX = hitboxX;
        this.hitboxY = hitboxY;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));
        startPosCode = "";
    }

    @Override
    public void update(Player player) {
        super.update(player);
        this.setBounds(new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight));

        if (intersects(player)) {
            this.setCurrentAnimationName("inRange");

            if (Keyboard.isKeyDown(Key.E)) {
                //These 4 cases tell us where the spider started - L/R, Above/Below jump point
                String xCode = "";
                String yCode = "";

                if(startPosCode.equals("")){
                    if(player.getX()<this.getX()){
                        xCode = "L";
                    }
                    else{
                        xCode = "R";
                    }
    
                    if(player.getY()<this.getY()){
                        yCode = "A";
                    }
                    else{
                        yCode = "B";
                    }
                    startPosCode = xCode + yCode;
                }
                

                player.setGravity(0f);
                if(dx == null){
                    dx = (this.getX()-player.getX())/50;                                        
                }
                
                if(dy == null){
                    dy = (this.getY()-player.getY())/50;
                }
                
                this.setCurrentAnimationName("Webbed");

                // Align player's position directly to the JumpPoint's location
                //Maybe just do this once you get close enough??
                /* player.setX(this.getX());
                player.setY(this.getY()); */
                    
                if(startPosCode.equals("LB")){
                    if(player.getX() < this.getX() && player.getY() > this.getY()){
                        player.moveX(dx);
                        player.moveY(dy);
                    }
                }
                else if(startPosCode.equals("LA")){
                    if(player.getX() < this.getX() && player.getY() < this.getY()){
                        player.moveX(dx);
                        player.moveY(dy);
                    } 
                   
                }
                else if(startPosCode.equals("RB")){
                    if(player.getX() > this.getX() && player.getY() > this.getY()){
                        player.moveX(dx);
                        player.moveY(dy);
                    }
                }
                else if (startPosCode.equals("RA")){
                    if(player.getX() > this.getX() && player.getY() < this.getY()){
                        player.moveX(dx);
                        player.moveY(dy);
                    }
                }
            }
            else{
                dx = null;
                dy = null;
                startPosCode = "";
                this.setCurrentAnimationName("DEFAULT");
                player.setGravity(.5f);
            }     
        }
    } 
    

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(hitboxX, hitboxY, hitboxWidth, hitboxHeight)
                        .build(),
            });

            put("inRange", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(hitboxX, hitboxY, hitboxWidth, hitboxHeight)
                        .build(),
            });

            put("Webbed", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
                        .withScale(3)
                        .withBounds(hitboxX, hitboxY, hitboxWidth, hitboxHeight)
                        .build(),
            });
        }};
    }

    public float xDist(float x) {
        return this.getX() - x;
    }

    public float yDist(float y) {
        return this.getY() - y;
    }

    // A testing method that shows the location of the hitbox
    /**@Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }**/
}
