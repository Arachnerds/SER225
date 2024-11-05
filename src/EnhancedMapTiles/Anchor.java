package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Direction;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;
import Engine.KeyLocker;

public class Anchor extends EnhancedMapTile{
  
  private double radius;
  private Double theta;
  private float rotationAdjustment;
  private Player player;
  private String startPosCode;
  private String currentPosCode;
  private String rotationDirection;
  /* private int extraMoveCounter;
  private float extraMoveX;
  private float extraMoveY; */

  
  public Anchor(Point location) {
    super(location.x, location.y, new SpriteSheet(ImageLoader.load("AnchorBox.png"), 16, 16), TileType.PASSABLE);
    radius = 0;
    rotationAdjustment = 1;
    startPosCode = "";
    currentPosCode = "";
  }
  
  @Override
  public void update(Player player) {
    super.update(player);
    this.player = player;


        //CurrentPosCode tells us where the spider currently is.
        String xCode = "";
        String yCode = "";

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
        currentPosCode = xCode + yCode;

        xCode = "";
        yCode = "";
    
    //Test key - L prints location (only works when an anchor is on screen)
    /* if(Keyboard.isKeyDown(Key.L)){
      System.out.println("x: "+ player.getX() + " " + "y: "+ player.getY());
      System.out.println(currentPosCode);
    } */
    
        
    if (intersects(player)) {
      this.setCurrentAnimationName("inRange");
      
      if(Keyboard.isKeyDown(Key.E)){
        //These 4 cases tell us where the spider started - L/R, Above/Below jump point
        xCode = "";
        yCode = "";

        if(startPosCode.equals("")){
            theta = null;
            radius = 0;
            
            
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

        //xyAdjustment is a positive/negative value applied to dx and dy at the end when you actually move the spider
        //rotationAdjustment determines whether theta is increasing or decreasing
        //Together, they determine direction of motion and make sure that motion works correctly in each quadrant
        int xyAdjustment = 1;
        if(startPosCode.equals("LA")){
            rotationAdjustment = 1;
            xyAdjustment = -1;
            rotationDirection = "clockwise";
        }
        else if(startPosCode.equals("LB")){
            rotationAdjustment = 1;
            xyAdjustment = -1;
            rotationDirection = "clockwise";
        }
        else if(startPosCode.equals("RB")){
            rotationAdjustment = -1;
            rotationDirection = "counterclockwise";
        }
        else {
            rotationAdjustment = -1;
            rotationDirection = "counterclockwise";
        }

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
          
          //Theta is in radians. Incrementing it by about a degree and a half each time.
          //Speed can be controlled by how much you increment theta by
          theta = (theta - rotationAdjustment*0.03)%(2*Math.PI);
          
          //This part is what makes the spider "bounce" back in the other direction when it hits an edge.
          //You treat is as starting a new swing, and all the logic of it is covered by that
          if((startPosCode.equals("LA")||startPosCode.equals("LB")) && currentPosCode.equals("RA")){
            startPosCode = "";
          }
          if((startPosCode.equals("RA")||startPosCode.equals("RB")) && currentPosCode.equals("LA")){
            startPosCode = "";
          }
            
        //X and Y components at the new theta
        float newRadX = ((float)(radius*Math.cos(theta)));
        float newRadY = ((float)(radius*Math.sin(theta)));
           
          //Change in x and y
          float dx = newRadX - prevRadX;
          float dy = newRadY - prevRadY;
          /* extraMoveX = Math.abs(dx);
          extraMoveY = Math.abs(dy); */
        
          player.moveXHandleCollision(xyAdjustment*dx);
          player.moveYHandleCollision(xyAdjustment*dy);

          //Printing various values for debugging
          //System.out.print(startPosCode);
          //System.out.println("radius: "+ radius +", theta: " + theta +", dx: " + dx + ", dy: " + dy);
        }
        //Check placement of this
        player.isSwinging = true;
        //System.out.println(Keyboard.isKeyDown(Key.SPACE));
        //The spider separates from the anchor point if you press space
        if(Keyboard.isKeyDown(Key.SPACE)){
          player.getKeyLocker().lockKey(Key.SPACE); //This doesn't seem to do it either
          radius = 0;
          theta = null;
          startPosCode = "";
          player.setGravity(.5f);
          //player.isSwinging = false;
          //Propelling the spider a little further
          //extraMoveCounter = 100;
        }
        //System.out.println(rotationDirection);

      }
      else{
        //Resetting when you let go of the button
        radius = 0;
        theta = null;
        startPosCode = "";
        player.setGravity(.5f);
        player.isSwinging = false;
      }
    }
    else{
      this.setCurrentAnimationName("DEFAULT");
      player.setGravity(.5f);
      player.isSwinging = false;
    }

    //I don't really know how this is going to behave if the player hits space when above the horizontal
    //But that should be pretty rare. You'd have to really try to do that.
    /* if(extraMoveCounter > 0){
      //Just need to do this once, not every time
      //Also, the logic in there is wrong somewhere
      if(extraMoveCounter == 100){
        if(player.getX() < this.getX() &&rotationDirection.equals("counterclockwise")){
          //extraMoveX = -extraMoveX;
          extraMoveY = -extraMoveY;
        }
        else if(player.getX()>this.getX() && rotationDirection.equals("clockwise")){
          extraMoveX = -extraMoveX;
          extraMoveY = -extraMoveY;
        }
        else if(player.getX()>this.getX() && rotationDirection.equals("counterclockwise")){
          extraMoveX = -extraMoveX;
          //extraMoveY = -extraMoveY;
        }
      }
      else if(extraMoveCounter == 0){
        player.setGravity(.5f);
      }

      player.moveXHandleCollision(extraMoveX);
      player.moveYHandleCollision(extraMoveY); //Should I wait to turn off gravity?
      System.out.println("counter: " + extraMoveCounter + " x: " + extraMoveX + " y: " + extraMoveY);
      extraMoveCounter--;
    } */
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
    if(this.intersects(player) && Keyboard.isKeyDown(Key.E) && !Keyboard.isKeyDown(Key.SPACE)){
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
