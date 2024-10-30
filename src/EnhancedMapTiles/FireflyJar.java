package EnhancedMapTiles;

import Builders.FrameBuilder;
import Enemies.FireflyEnemy;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.Player;
import Level.TileType;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.util.HashMap;

public class FireflyJar extends PushableBlock{
    public FireflyJar(Point location){
        super(location, "FireflyJar.png",30,12,4,4,8,8);
    }


  @Override
  public void update(Player player) {
    super.update(player);
    if(previousAirGroundState == AirGroundState.AIR && this.airGroundState == AirGroundState.GROUND){
        this.setCurrentAnimationName("Smashed");
        //Need to prevent it from being moved, set it to passable, and turn off gravity
        this.stopGravity();
        this.tileType = TileType.PASSABLE;
        this.canBeMoved = false;
        //Need to figure out how to spawn a firefly here
        Point fireflySpawnPoint = new Point(this.getLocation().x, this.getLocation().y-30);
        FireflyEnemy firefly = new FireflyEnemy(fireflySpawnPoint, Direction.LEFT, map);
        firefly.setCurrentAnimationName("WALK_UP");
        map.addEnemy(firefly);
    }
    
  }

  @Override
  public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
    return new HashMap<String, Frame[]>() {{
      put("DEFAULT", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
        .withScale(3)
        .withBounds(3,3,10,10)
        .build(),
      });
      
      put("Smashed", new Frame[] {
        new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
        .withScale(3)
        .withBounds(3,3,10,10)
        .build(),
      });  
    }};
  }
}
