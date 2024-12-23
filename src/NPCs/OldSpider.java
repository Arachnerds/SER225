package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;


import java.util.ArrayList;
import java.util.HashMap;

public class OldSpider extends NPC {
    private ArrayList<Key> tutorialKeys;
    private int keyIndex;

    private String animationDirection;
    //This boolean makes sure the textbox is displayed automatically in the basement level so the player knows how to speak with it
    private boolean firstDisplay;

    public OldSpider(Point location, String animationDirection, ArrayList<String> messages, int offset) {
        
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("OldSpiderFinal.png"), 128, 128), animationDirection, messages);
        isInteractable = true;
        talkedToTime = 200;
        textboxOffsetX = offset;
        textboxOffsetY = -30;
        //Setting up a list of keys to use in the tutorial
        tutorialKeys = new ArrayList<Key>();
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.SPACE);
        tutorialKeys.add(Key.Q);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.Y);
        tutorialKeys.add(Key.E);
        tutorialKeys.add(Key.C);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(Key.T);
        tutorialKeys.add(null);
        keyIndex = 0;
        
        textbox.setText("Press T to talk to me!");
        this.animationDirection = animationDirection;
        
    }

    public void update(Player player) {
        if (player.getX() > this.getX()) {
            this.currentAnimationName = "RIGHT";
        } else {
            this.currentAnimationName = "LEFT";
        }

        if(this.map.getMapFileName().equals("basement.txt")){
            firstDisplay = true;
        }
        else{ //THE WRONG SPOT TO DO THIS - need to make it false after you talk to the spider
            firstDisplay = false;
        }

        /* if(firstDisplay == 0 && this.map.getMapFileName().equals("basement.txt")){
            firstDisplay = true;
        }
        else{ //THE WRONG SPOT TO DO THIS - need to make it false after you talk to the spider
            firstDisplay = false;
        } */
        super.update(player);
    }

    
    @Override
    public void checkTalkedTo(Player player) {
        //Special logic here for the tutorial in the basement
        if(this.map.getMapFileName().equals("basement.txt")){
            if (isInteractable && intersects(player)) {
                if (Keyboard.isKeyDown(tutorialKeys.get(keyIndex)) && !keyLocked) {
                    keyIndex++;
                    if(firstDisplay){
                        firstDisplay = false;
                    }
                    if (!talkedTo) {
                        talkedTo = true;
                        //LOCK PLAYER TO CURRENT SPOT - FIGURE OUT WHERE TO UNDO THIS
                        player.setInTutorial(true);
                        currentMessageIndex = 0;
                        textbox.setText(messages.get(currentMessageIndex));
                    } else {
                        currentMessageIndex++;
                        if (currentMessageIndex < messages.size()) {
                            textbox.setText(messages.get(currentMessageIndex));
                        } else {
                            talkedTo = false;
                            currentMessageIndex = 0;
                            textbox.setText("");
                            player.setInTutorial(false);
                           firstDisplay = false;
                        }
                    }
                    keyLocked = true;
                } else if (Keyboard.isKeyUp(tutorialKeys.get(keyIndex))) {
                    keyLocked = false;
                }
            }
        }
        else{
            if (isInteractable && intersects(player)) {
                if (Keyboard.isKeyDown(Key.T) && !keyLocked) {
                    if (!talkedTo) {
                        talkedTo = true;
                        currentMessageIndex = 0;
                        textbox.setText(messages.get(currentMessageIndex));
                    } else {
                        currentMessageIndex++;
                        if (currentMessageIndex < messages.size()) {
                            textbox.setText(messages.get(currentMessageIndex));
                        } else {
                            talkedTo = false;
                            currentMessageIndex = 0;
                            textbox.setText("");
                            firstDisplay = false;
                        }
                    }
                    keyLocked = true;
                } else if (Keyboard.isKeyUp(Key.T)) {
                    keyLocked = false;
                }
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
           put("RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(0.75f)
                    .withBounds(-32, -32, 192, 192)
                    .build()
           });
           put("LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(0.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(-32, -32, 192, 192)
                    .build()
    });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if(!arachnophobiaEnabled){
            super.draw(graphicsHandler);
            if ((talkedTo||firstDisplay) &&!textbox.getText().equals("")) {
                textbox.draw(graphicsHandler);
            }
        }
    }

}
