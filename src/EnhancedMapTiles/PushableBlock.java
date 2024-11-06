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
import Level.MapEntity;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;


//A class for a pushable object
//Works by essentially making two copies of it and tying them together to always be at the same location
//One is solid, and its hitbox matches the object exactly so that when you jump on top, you walk on top of it
//The other is passable and has a hitbox that is slightly larger, that way when it intersects with the player, you can push it
//There may be an issue in the loadAnimations method - hard coded hitbox bounds - which is currently circumvented
//using the setBounds method in the constructor. It may be an issue if we need to animate any pushable objects,
//but it should work nicely for objects that don't need more than one animation.
//In that case, perhaps inheriting from this class and overriding that method would be the best course of action.
public class PushableBlock extends EnhancedMapTile{
  
  protected Player player;
  protected PushableBlockHitbox hitbox;
  protected boolean isBeingPulled;
  protected float originalWalkSpeed;
  protected boolean canBeMoved; //Useful for situations like the firefly jar, where we want it to stop moving at some point
  protected int dragRange;
  protected int webAttachmentAdjustmentX;

  //Gravity related variables ripped from player class
  protected float gravity = .5f;
  protected float terminalVelocityY = 1f;
  protected float momentumYIncrease = .25f;
  protected float momentumY;
  protected float moveAmountX, moveAmountY;
  protected AirGroundState airGroundState;
  protected AirGroundState previousAirGroundState;

  //Apologies for the very long constructor. The four integers at the end are the ones you would provide in the load animations method to set the hitbox.
  public PushableBlock(Point location, String fileName, int spriteWidth, int spriteHeight, int withBoundsX, int withBoundsY, int withBoundsWidth, int withBoundsHeight) {
    super(location.x, location.y, new SpriteSheet(ImageLoader.load(fileName), spriteWidth, spriteHeight), TileType.NOT_PASSABLE);
    momentumY = 0;   
    airGroundState = AirGroundState.GROUND;
    previousAirGroundState = airGroundState;
    hitbox = new PushableBlockHitbox(location,this, fileName,spriteWidth,spriteHeight,withBoundsX-1,withBoundsY-1,withBoundsWidth+2,withBoundsHeight+2);
    isBeingPulled = false;
    originalWalkSpeed = 0;
    System.out.println(this.getWidth());
    this.setBounds(new Rectangle(withBoundsX, withBoundsY, withBoundsWidth, withBoundsHeight));
    canBeMoved = true;
    dragRange = 100;
    webAttachmentAdjustmentX = 0;
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
    //The +3 is necessary so that standing on top does not push the object
    hitbox.setLocation(this.getX(), this.getY()+3);
    
    if(canBeMoved){
      if(this.getXDist(player.getX())<dragRange){
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
            
          }
          
        }
        else{
          isBeingPulled = false;
          player.setWalkSpeed(originalWalkSpeed);
        }
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
    }};
  }
  
  public float getXDist(float x){
    return Math.abs((x-this.getX()));
  }

  protected void applyGravity() {
    if(airGroundState == AirGroundState.AIR){
      moveAmountY += gravity + momentumY;
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
        // if object collides with a map tile below it, it is now on the ground
        // if object does not collide with a map tile below, it is in air
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
    //drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    if(isBeingPulled){
      //The center x and y of th epushable object
        int x1 = (int)((this.getCalibratedXLocation() + this.getWidth()/2 + webAttachmentAdjustmentX));
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

    public void stopGravity(){
      this.gravity = 0f;
    }
}
