package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.util.HashMap;

public class Anchor extends EnhancedMapTile{

private double radius;
private double theta;

    public Anchor(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("AnchorBox.png"), 16, 16), TileType.PASSABLE);
        radius = 0;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        if (intersects(player)) {
            this.setCurrentAnimationName("inRange");

            if(Keyboard.isKeyDown(Key.E)){
                this.setCurrentAnimationName("Webbed");
                //player.moveXHandleCollision(30);
                if(radius == 0){
                    //PRETTY SURE THAT IS AN EXTRA SQRT - but it might be helpful to keep the movement from being too extreme
                   //radius = Math.sqrt(Math.hypot(player.getX()-this.getX(), player.getY()-this.getY()));
                   
                   //radius = Math.hypot(player.getX()-this.getX(), player.getY()-this.getY());
                   radius = Math.sqrt(this.distToAnchor(player.getX(), player.getY()));
                   //radius = this.distToAnchor(player.getX(), player.getY());
                }

                //May need some logic here to handle negative cases (ie left/right of anchor)
                //Right now its only handling left to right motion
                
                //If player is left of the anchor
                if(this.getX() > player.getX()){
                   theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                }
                else{ //If player is right of the anchor
                    //theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                }

                
                ////System.out.println(radius);
                ////System.out.println(theta);
            System.out.println(radius/(this.getX()-player.getX())%(2*Math.PI));

               //x = rcos(theta), y = rsin(theta)
                player.moveXHandleCollision((float)(radius*Math.cos(theta)));
                player.moveYHandleCollision((float)(radius*Math.sin(theta)));
            }
            else{
                radius = 0;
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

}
