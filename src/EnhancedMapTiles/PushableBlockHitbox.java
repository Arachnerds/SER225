package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.Rectangle;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;


//This class is an additional hitbox for pushable objects
//It is simply overlayed on to a pushable object, but its bounds are a little larger and it is passable
//This allows for collision detection while still having the object be solid (i.e. you can stand on it)
//We should never have to instantiate this class because it is handled in the PushableBlock class
public class PushableBlockHitbox extends EnhancedMapTile{
  
    protected Player player;
    private PushableBlock block;
    
    
    public PushableBlockHitbox(Point location, PushableBlock block, String fileName, int spriteWidth, int spriteHeight, int x, int y, int width, int height) {
      //Needs a little adjustment on y so that it doesn't move when you stand on top of it
      super(location.x, location.y+5, new SpriteSheet(ImageLoader.load(fileName), spriteWidth, spriteHeight), TileType.PASSABLE);
      this.setVisible(false);
      this.block = block;
      this.setBounds(new Rectangle(x,y,width,height));
    }
    
    @Override
    public void update(Player player) {
      super.update(player);
      this.player = player;
      
      
      //Probably need to extend the hitbox slightly beyond the bounds to actually get it to instersect
      //The bounds are solid. So it hits the hitbox rather than just the part I want to be solid
      //Create a second object over it that is passable with a slightly larger hitbox?
      if(block.canBeMoved){

        if(intersects(player)){
          //System.out.println("They're intersecting!");
          //This is a rate to move a pushed object at
          int dx = 1;
          if(player.getX() < this.getX()){
            block.moveXHandleCollision(dx);
          }
          else{
            block.moveXHandleCollision(-dx);
          }
      }
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
        
        put("inRange", new Frame[] {
          new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
          .withScale(3)
          .withBounds(3,3,10,10)
          .build(),
        });
  
      }};
    }
    
    public float getXDist(float x, float y){
      return Math.abs((x-this.getX()));
    }
  

    
    /* @Override
    public void draw(GraphicsHandler graphicsHandler) {
      super.draw(graphicsHandler);
      drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
      } */
  
  }
  
