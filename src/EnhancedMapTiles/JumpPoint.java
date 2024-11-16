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
import Players.WalrusPlayer;
import Utils.Direction;
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
    private Player player;
    

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
        this.player = player;
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
                //Speed can be controlled by divisor here. Originally it was 50. Trying to speed it up a bit
                if(dx == null){
                    dx = (this.getX()-player.getX())/38;                                        
                }
                
                if(dy == null){
                    dy = (this.getY()-player.getY())/38;
                }
                
                this.setCurrentAnimationName("Webbed");
                    
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


                //If the player presses jump, they kind of lower, hang, and bounce
                //This entire block could be removed if we don't like how it looks
                if(Keyboard.isKeyDown(Key.SPACE)){
                    dx = null;
                    dy = null;
                    startPosCode = "";
                    player.setGravity(.5f);
                    
                }
            }
            else{
                dx = null;
                dy = null;
                startPosCode = "";
                player.setGravity(.5f);
                
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

    // Overriding the hitbox draw method to just draw a line instead
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        int walrusXAdjust = 0;
        int WalrusYAdjust = 0;
        if(player instanceof WalrusPlayer){
            walrusXAdjust = 10;
            WalrusYAdjust = -10;
        }

        if(this.intersects(player) && Keyboard.isKeyDown(Key.E)){
            //The jump point's x and y, with a little adjustment so the line goes to the center of it
            int x1 = (int)this.getCalibratedXLocation()+25;
            int y1 = (int)this.getCalibratedYLocation()+25;

            //Need to an adjustment if the player is facing left so the web isn't coming out of the mouth
            int facingDirectionAdjustment = 0;
            if(player.getFacingDirection() == Direction.LEFT){
                facingDirectionAdjustment = (int)(player.getX2() - player.getX1());
                walrusXAdjust = walrusXAdjust *-1;
            }
            //The player x and y
            int x2 = (int)player.getCalibratedXLocation() + facingDirectionAdjustment + walrusXAdjust;                     
            //That 60 is an adjustment so the web doesn't come from the top corner of the hitbox. Hardcoding is not ideal but fine for now (until we add the walrus)
            int y2 = (int)player.getCalibratedYLocation() + 60 + WalrusYAdjust;
            
            graphicsHandler.drawLine(x1,y1,x2,y2,new Color(255, 255, 255, 100));
            
        }
        
    }

    
}
