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
import java.security.KeyStore;
import java.util.HashMap;

public class Anchor extends EnhancedMapTile{

private double radius;
private Double theta;
private int moveTimer;

    public Anchor(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("AnchorBox.png"), 16, 16), TileType.PASSABLE);
        radius = 0;
        moveTimer = 0;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        //Test key - L prints location
        if(Keyboard.isKeyDown(Key.L)){
            System.out.println("x: "+ player.getX() + " " + "y: "+ player.getY());
        }


        if (intersects(player)) {
            this.setCurrentAnimationName("inRange");

            if(Keyboard.isKeyDown(Key.E)){
                this.setCurrentAnimationName("Webbed");
                //NEED TO SET GRAVITY TO ZERO FOR THE PLAYER

                //player.moveXHandleCollision(30);
                if(radius == 0){
                   radius = this.distToAnchor(player.getX(), player.getY());
                }

                if(moveTimer == 0){
                    moveTimer = 20;
                }
                //Just do the theta calculation ONCE, then increment theta
                if(theta == null) {
                    theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                }
                //Theta is in radians. Incrementing it by about a degree each time
                if(moveTimer>1){
                    moveTimer--;
                }
                else{
                    theta = (theta + 0.002)%(2*Math.PI);
                    float xMagnitude = ((float)(radius*Math.cos(theta)));
                
                   // float yMagnitude = (float)(radius*Math.sin(theta));

                    player.moveX(xMagnitude);
                   // player.moveY(yMagnitude);
                    moveTimer = 20;
                }
                
                
                //Moving too fast. I think I need a timer to slow it

               // player.setX()+player.getX());
               // player.setY(((float)(radius*Math.sin(theta)))+player.getY());
                
                //May need some logic here to handle negative cases (ie left/right of anchor)
                //Right now its only handling left to right motion
                
                //If player is left of the anchor
                /* if(this.getX() > player.getX()){
                   //theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                }
                else{ //If player is right of the anchor
                    //theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                } */

                
                System.out.println(radius);
                //System.out.println(theta);
                //System.out.println(radius/(this.getX()-player.getX())%(2*Math.PI));

               //x = rcos(theta), y = rsin(theta)
               //Set location instead of moving
                /* player.moveXHandleCollision((float)(radius*Math.cos(theta)*.1));
                player.moveYHandleCollision((float)(radius*Math.sin(theta)*.1)); */
            }
            else{
                radius = 0;
                theta = null;
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
