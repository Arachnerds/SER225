package EnhancedMapTiles;

import Builders.FrameBuilder;
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
import Utils.Point;
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
                //Turning off gravity while swinging
                player.setGravity(0f);
                if(radius == 0){
                   radius = this.distToAnchor(player.getX(), player.getY());
                }

                if(moveTimer == 0){
                    moveTimer = 10;
                }
                //Just do the theta calculation ONCE, then increment theta
                if(theta == null) {
                    theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX()))%(2*Math.PI));
                }
                
                if(moveTimer>1){
                    moveTimer--;
                }
                else{
                    //Theta is in radians. Incrementing it by about a degree each time
                    theta = (theta + 0.002)%(2*Math.PI);
                    float dx = ((float)(radius*Math.cos(theta)));
                    float dy = -((float)(radius*Math.sin(theta)));
                
                    player.moveXHandleCollision(dx);
                    player.moveYHandleCollision(dy);
                    moveTimer = 10;
                }

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

}
