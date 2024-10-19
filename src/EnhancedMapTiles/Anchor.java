package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import Game.Game;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Players.Spider;
import Utils.Direction;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;

public class Anchor extends EnhancedMapTile{
  
  private double radius;
  private Double theta;
  private float rotationAdjustment;
  private Player player;
  
  public Anchor(Point location) {
    super(location.x, location.y, new SpriteSheet(ImageLoader.load("AnchorBox.png"), 16, 16), TileType.PASSABLE);
    radius = 0;
    rotationAdjustment = 1;
  }
  
  @Override
  public void update(Player player) {
    super.update(player);
    this.player = player;
    
    //Test key - L prints location (only works when an anchor is on screen)
    if(Keyboard.isKeyDown(Key.L)){
      System.out.println("x: "+ player.getX() + " " + "y: "+ player.getY());
    }
    
    if(player.getX()>this.getX()){
      rotationAdjustment = 1;
    }
    else if (player.getX()<this.getX()&&player.getY()<this.getY()){
      rotationAdjustment = -1;
    }
    
    
    if (intersects(player)) {
      this.setCurrentAnimationName("inRange");
      
      if(Keyboard.isKeyDown(Key.E)){
        this.setCurrentAnimationName("Webbed");
        //Turning off gravity while swinging
        player.setGravity(0f);
        if(radius == 0){
          radius = this.distToAnchor(player.getX(), player.getY());
        }
        
        //Just do the theta calculation ONCE, then increment theta
        if(theta == null) {
          theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
        }
        else{                   
          //X and Y components at the previous theta (x = rcos(theta), y = rsin(theta))
          float prevRadX = ((float)(radius*Math.cos(theta)));
          float prevRadY = ((float)(radius*Math.sin(theta)));
          
          //Theta is in radians. Incrementing it by about a degree each time.
          theta = (theta - 0.02)%(2*Math.PI);
          
          
          
          //This part is trying to make sure the player always moves in one direction (clockwise or counterclockwise) - Not working
          /* if(player.getX()<this.getX()){
            theta = (theta + 0.02)%(2*Math.PI);
          }
          else{
            theta = (theta - 0.02)%(2*Math.PI);
          } */
            
            //This part is trying to limit theta to be between 0 and pi radians, so we only swing underneath
          /* if(theta<Math.PI){
            theta = (theta + 0.02)%(2*Math.PI);
          }
          else{
            theta = (theta - 0.02)%(2*Math.PI);
          } */
            
            
            //X and Y components at the new theta
          float newRadX = ((float)(radius*Math.cos(theta)));
          float newRadY = ((float)(radius*Math.sin(theta)));
          
          
          
          //Need to make sure it always goes clockwise/counterclockwise
          /* float dx = 0;
          float dy = 0;
          if(player.getX() > this.getX()){
            dx = prevRadX - newRadX;
            dy = prevRadY - newRadY;
          }
          else{
            
          } */
            
            //Change in x and y
          float dx = newRadX - prevRadX;
          float dy = newRadY - prevRadY;
          
          player.moveXHandleCollision(rotationAdjustment*dx);
          player.moveYHandleCollision(rotationAdjustment*dy);
          
          //Printing various values for debugging
          System.out.println("radius: "+ radius +", theta: " + theta +", dx: " + dx + ", dy: " + dy);
        }
        
      }
      else{
        //Resetting when you let go of the button
        radius = 0;
        theta = null;
        player.setGravity(.5f);
      }
    }
    else{
      this.setCurrentAnimationName("DEFAULT");
      player.setGravity(.5f);
    }
  }
  
  @Override
  public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    return new HashMap<String, Frame[]>() {{
      put("DEFAULT", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
        .withScale(3)
        .withBounds(-50, -50, 100, 100)
        .build(),
      });
      
      put("inRange", new Frame[] {
        
        new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
        .withScale(3)
        .withBounds(-50, -50, 100, 100)
        .build(),
      });
      
      put("Webbed", new Frame[] {
        
        new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
        .withScale(3)
        .withBounds(-50, -50, 100, 100)
        .build(),
      });
      
      
    }};
  }
  
  public float distToAnchor(float x, float y){
    return (float)(Math.hypot(x-this.getX(), y-this.getY()));
  }
  
  // Overriding the hitbox draw method to just draw a line instead
  @Override
  public void draw(GraphicsHandler graphicsHandler) {
    super.draw(graphicsHandler);
    if(this.intersects(player) && Keyboard.isKeyDown(Key.E)){
      //The jump point's x and y, with a little adjustment so the line goes to the center of it
            int x1 = (int)this.getCalibratedXLocation()+25;
            int y1 = (int)this.getCalibratedYLocation()+25;

            //Need to an adjustment if the player is facing left so the web isn't coming out of the mouth
      int facingDirectionAdjustment = 0;
      if(player.getFacingDirection() == Direction.LEFT){
        facingDirectionAdjustment = (int)(player.getX2() - player.getX1());
      }
      //The player x and y
      int x2 = (int)player.getCalibratedXLocation() + facingDirectionAdjustment;                     
      //That 60 is an adjustment so the web doesn't come from the top corner of the hitbox. Hardcoding is not ideal but fine for now (until we add the walrus)
            int y2 = (int)player.getCalibratedYLocation() + 60;
            
            graphicsHandler.drawLine(x1,y1,x2,y2,new Color(255, 255, 255, 100));
            
        }
        
    }

}
