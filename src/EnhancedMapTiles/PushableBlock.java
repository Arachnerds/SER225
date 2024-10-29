package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntity;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;

public class PushableBlock extends EnhancedMapTile{
  
  private Player player;
  private PushableBlockHitbox hitbox;
  private boolean isBeingPulled;
  private float originalWalkSpeed;

  //Gravity related variables ripped from player class
  protected float gravity = .5f;
  protected float terminalVelocityY = 1f;
  protected float momentumYIncrease = .25f;
  protected float momentumY;
  protected float moveAmountX, moveAmountY;
  protected AirGroundState airGroundState;
  protected AirGroundState previousAirGroundState;
  
  //TO DO:
  //Parameterize the constuctor for fileName, width, scale, etc. so it is more generalized and can be used for different sprites
  //Clean up comments
  public PushableBlock(Point location, String fileName, int spriteWidth, int spriteHeight) {
    super(location.x, location.y, new SpriteSheet(ImageLoader.load(fileName), spriteWidth, spriteHeight), TileType.NOT_PASSABLE);
    momentumY = 0;   
    airGroundState = AirGroundState.AIR;
    previousAirGroundState = airGroundState;
    hitbox = new PushableBlockHitbox(location,this, fileName,spriteWidth,spriteHeight);
    isBeingPulled = false;
    originalWalkSpeed = 0;
  }
  
  @Override
  public void update(Player player) {
    super.update(player);
    this.player = player;
    if(originalWalkSpeed == 0){
      originalWalkSpeed = player.getWalkSpeed();
    }
    applyGravity();
    previousAirGroundState = airGroundState;
    this.moveYHandleCollision(moveAmountY);
    //The +3 is necessary so that standing on top does not move the object
    hitbox.setLocation(this.getX(), this.getY()+3);
    
    //Probably need to extend the hitbox slightly beyond the bounds to actually get it to intersect
    //The bounds are solid. So it hits the hitbox rather than just the part I want to be solid
    //Create a second object over it that is passable with a slightly larger hitbox?
    if(this.getXDist(player.getX())<100){
      if(Keyboard.isKeyDown(Key.Y)){
        isBeingPulled = true;
        player.setWalkSpeed(originalWalkSpeed/2);
        if(player.isWalking()){
            if(player.getX() < this.getX() && (Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A))){
              this.moveXHandleCollision(-player.getWalkSpeed());
            }
            else if((player.getX() > this.getX())&&(Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D))){
              this.moveXHandleCollision(player.getWalkSpeed());
            }
            
            //bugging a bit when you drag it all the way to the right - block, spider, crate, then spider glitches through
        }
        
      }
      else{
        isBeingPulled = false;
        player.setWalkSpeed(originalWalkSpeed);
      }
    }
    else{
      isBeingPulled = false;
      player.setWalkSpeed(originalWalkSpeed);
    }
  }

  @Override
  public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    return new HashMap<String, Frame[]>() {{
      put("DEFAULT", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
        .withScale(3)
        .withBounds(4,4,8,8)
        .build(),
      });
      
      put("inRange", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
        .withScale(3)
        .withBounds(4,4,8,8)
        .build(),
      });
      
      put("Webbed", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
        .withScale(3)
        .withBounds(4,4,8,8)
        .build(),
      });
      
      
    }};
  }
  
  public float getXDist(float x){
    return Math.abs((x-this.getX()));
  }

  protected void applyGravity() {
    if(airGroundState == AirGroundState.AIR){
      moveAmountY += gravity + momentumY;
      if (moveAmountY > 0) {
      increaseMomentum();
      }
    }
    else{
      momentumY = 0;
    }
    //System.out.println("MoveY:" + moveAmountY + " Momentum: " + momentumY + " AirState: " + airGroundState);
    
  }

  protected void increaseMomentum() {
    momentumY += momentumYIncrease;
    if (momentumY > terminalVelocityY) {
        momentumY = terminalVelocityY;
    }
  }


  @Override
  public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                airGroundState = AirGroundState.AIR;
            }
        }
    }

  

  public PushableBlockHitbox getExtraHitbox(){
    return hitbox;
  }

  // Overriding the hitbox draw method to just draw a line instead
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
      super.draw(graphicsHandler);
      if(isBeingPulled){
        //The jump point's x and y, with a little adjustment so the line goes to the center of it
          int x1 = (int)((this.getCalibratedXLocation() + this.getWidth()/2));
          int y1 = (int)((this.getCalibratedYLocation() + this.getHeight()/2));
  
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
