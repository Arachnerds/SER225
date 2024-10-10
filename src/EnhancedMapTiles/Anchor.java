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
private float rotationAdjustment;

    public Anchor(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("AnchorBox.png"), 16, 16), TileType.PASSABLE);
        radius = 0;
        rotationAdjustment = 1;
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        //Test key - L prints location (only works when an anchor is on screen)
        if(Keyboard.isKeyDown(Key.L)){
            System.out.println("x: "+ player.getX() + " " + "y: "+ player.getY());
        }

        if(player.getX()>this.getX()){
            rotationAdjustment = 1;
        }
        else{
            rotationAdjustment = 1;
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
                //DON'T LIKE THIS MATH.PI IN HERE - But it does smooth out the motion on the left side a bit, even though it still looks very wrong
                if(theta == null) {
                    theta = Math.atan(((this.getY()-player.getY())/(this.getX()-player.getX())+2*Math.PI)%(2*Math.PI));
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

                    //Change in x and y
                    
                    //Need to make sure it always goes clockwise/counterclockwise
                    /* float dx = 0;
                    float dy = 0;
                    if(player.getX() > this.getX()){
                        dx = prevRadX - newRadX;
                        dy = prevRadY - newRadY;
                    }
                    else{
                        
                    } */
                   //JUST CHEAT AND TURN THETA THE OTHER WAY WHEN YOU"RE STANDING ON THE LEFT?
                   
                    float dx = newRadX - prevRadX;
                    float dy = newRadY - prevRadY;
                    
                    player.moveXHandleCollision(rotationAdjustment*dx);
                    player.moveYHandleCollision(rotationAdjustment*dy);
                    
                    //dx seems to behave properly, dy seems to be the issue
                    //Not just a simple negative thing, the magnitude seems off as well
                    System.out.println("radius: "+ radius +", theta: " + theta +", dx: " + dx + ", dy: " + dy);
                }

                //System.out.println(radius);
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
