package Level;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.Keyboard;
import GameObject.SpriteSheet;
import SpriteFont.SpriteFont;
import java.util.ArrayList;

// This class is a base class for all npcs in the game -- all npcs should extend from it
public class NPC extends MapEntity {
    protected boolean isInteractable = false;
    protected boolean talkedTo = false;
    protected SpriteFont message;
    protected int talkedToTime;
    protected int timer;
    protected Textbox textbox;
    protected int textboxOffsetX = 0;
    protected int textboxOffsetY = 0;

    protected ArrayList<String> messages;
    protected ArrayList<Integer> offsetX;
    protected int currentMessageIndex = 0;
    protected boolean keyLocked = false;

    protected boolean arachnophobiaEnabled;

    public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation, ArrayList<String> messages) {
        super(x, y, spriteSheet, startingAnimation);
        this.messages = messages;
        this.textbox = new Textbox(messages.get(currentMessageIndex));
        this.message = createMessage();
        arachnophobiaEnabled = false;
    }
    
    public NPC(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
        this.message = createMessage();
    }

    /*public NPC(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
        this.message = createMessage();
    }

    public NPC(float x, float y, Frame[] frames) {
        super(x, y, frames);
        this.message = createMessage();
    }

    public NPC(float x, float y, Frame frame) {
        super(x, y, frame);
        this.message = createMessage();
    }

    public NPC(float x, float y) {
        super(x, y);
        this.message = createMessage();
    }*/

    protected SpriteFont createMessage() {
        return null;
    }

    public void update(Player player) {
        super.update();
        checkTalkedTo(player);
        textbox.setLocation((int) getCalibratedXLocation() + textboxOffsetX, (int) getCalibratedYLocation() + textboxOffsetY);
    }
    

    /*public void checkTalkedTo(Player player) {
        if (isInteractable && intersects(player) && Keyboard.isKeyDown(Key.T)) {
            talkedTo = true;
            if (talkedToTime >= 0) {
                timer = talkedToTime;
            }
            currentMessageIndex = (currentMessageIndex + 1) % messages.size(); // Cycle through messages
            textbox.setText(messages.get(currentMessageIndex)); // Update the textbox text
        }

        if (talkedTo && talkedToTime >= 0 && timer == 0) {
            talkedTo = false;
        }

        if (timer > 0) {
            timer--;
        }
    }*/

    public void checkTalkedTo(Player player) {
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
                    }
                }
                keyLocked = true;
            } else if (Keyboard.isKeyUp(Key.T)) {
                keyLocked = false;
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        if (talkedTo) {
            textbox.draw(graphicsHandler);
        }
    }

    public void setArachnophobiaEnabled(boolean bool){
        this.arachnophobiaEnabled = bool;
    }

    /* public String getPreviousAnimationName(){
        return previousAnimationName;
    } */

    public ArrayList<String> getMessages(){
        return messages;
    }

    public int getTextboxOffsetX(){
        return textboxOffsetX;
    }
}
