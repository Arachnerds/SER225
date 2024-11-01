package EnhancedMapTiles;

import Builders.FrameBuilder;
import Enemies.FireflyEnemy;
import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;



public class FireflyJar extends PushableBlock{

  //fallCounter lets the jar take one fall before breaking
  //Useful because the jar falls once when it is spawned in
  private int fallCounter;
  private DoorKey key;

    public FireflyJar(Point location){
        super(location, "FireflyJar.png",90,36,0,0,30,36);
        this.hitbox.setBounds(new Rectangle(-1,-1,42,48));
        fallCounter = 0;
        key = new DoorKey(location);
        dragRange = 200;
        webAttachmentAdjustmentX = -120;
    }


  @Override
  public void update(Player player) {
    super.update(player);
    if(!this.isSmashed()){
      key.setLocation(this.getLocation().x+60, this.getLocation().y+80);
    }
    if(previousAirGroundState == AirGroundState.AIR && this.airGroundState == AirGroundState.GROUND&& !this.isSmashed()){
      if(fallCounter == 0){
        fallCounter++;
      }
      else{
      this.setCurrentAnimationName("Smashed");
      //Need to prevent it from being moved, set it to passable, and turn off gravity
      this.stopGravity();
      this.tileType = TileType.PASSABLE;
      this.canBeMoved = false;
      //Need to spawn a firefly and key
      Point fireflySpawnPoint = new Point(this.getLocation().x, this.getLocation().y-30);
      FireflyEnemy firefly = new FireflyEnemy(fireflySpawnPoint, Direction.LEFT, map);
      firefly.setCurrentAnimationName("WALK_UP");
      map.addEnemy(firefly);
      //Need to adjust the key's loaction and bounds for its floating animation 
      key.setLocationMax(key.getY());
      key.setLocationMin(key.getY());
      key.setLocation(this.getLocation().x+60, this.getLocation().y+80);  
      }
          
        
    }

  }

  @Override
  public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    return new HashMap<String, Frame[]>() {{
      put("DEFAULT", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
        .withScale(4)
        .withBounds(0,0,90,160)
        .build(),
      });
      
      put("Smashed", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
        .withScale(4)
        .withBounds(3,3,10,10)
        .build(),
      });  
    }};
  }

  public boolean isSmashed(){
    return this.getCurrentAnimationName().equals("Smashed");
  }

  public DoorKey getDoorKey(){
    return key;
  }
}
