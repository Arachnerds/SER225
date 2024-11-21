package Maps;

import Enemies.Fire;
import Enemies.FireflyEnemy;
import Enemies.FlyEnemy;
import Enemies.GrasshopperEnemy;
import Enemies.RoachEnemy;
import EnhancedMapTiles.Anchor;
import EnhancedMapTiles.BasementEndLevelTile;
import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.PushableBlock;
import GameObject.Rectangle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import NPCs.OldSpider;
import Tilesets.CommonTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Basement extends Map {

    /* private Fire fire; */

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(4, 11).getLocation();
    }

   
     @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        /* FlyEnemy flyEnemy = new FlyEnemy(getMapTile(5, 1).getLocation().addY(5), Direction.LEFT);
        enemies.add(flyEnemy); */

        FlyEnemy flyEnemy = new FlyEnemy(getMapTile(16, 11).getLocation().subtractY(25), Direction.RIGHT, this);
        enemies.add(flyEnemy);

        RoachEnemy roachEnemy = new RoachEnemy(getMapTile(10, 14).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(roachEnemy); 

        /*GrasshopperEnemy gEnemy = new GrasshopperEnemy(getMapTile(10, 9).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(gEnemy);*/

        FireflyEnemy fireflyEnemy = new FireflyEnemy(getMapTile(45, 9).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(fireflyEnemy);

       // PillbugEnemy pillEnemy = new PillbugEnemy(getMapTile(10, 13).getLocation().subtractY(5), Direction.RIGHT, this);
       // enemies.add(pillEnemy);
         
        /* BugEnemy bugEnemy = new BugEnemy(getMapTile(5, 14).getLocation().subtractY(5), Direction.RIGHT);
        enemies.add(bugEnemy); */

        /* fire = new Fire(getMapTile(7, 11).getLocation().subtractY(5), Direction.RIGHT);
        enemies.add(fire); */

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        BasementEndLevelTile basementEndLevelTile = new BasementEndLevelTile(getMapTile(68, 2).getLocation());
        enhancedMapTiles.add(basementEndLevelTile);

        //Going to want to switch this back to an anchor, it is a jumppoint now for testing
        JumpPoint testJumpPoint = new JumpPoint(getMapTile(12, 10).getLocation(),"left");
        enhancedMapTiles.add(testJumpPoint);

        Anchor testAnchor = new Anchor(getMapTile(20, 9).getLocation());
        enhancedMapTiles.add(testAnchor);

        /* PushableBlock block = new PushableBlock(getMapTile(10, 0).getLocation().addY(10), "AnchorBox.png",16,16,4,4,8,8);
        enhancedMapTiles.add(block);
        enhancedMapTiles.add(block.getExtraHitbox()); */

        /* PushableBlock fryingPan = new PushableBlock(getMapTile(9, 11).getLocation().subtractY(5), "FryingPan.png", 22, 10, 0, 5, 22, 5);
        enhancedMapTiles.add(fryingPan);
        enhancedMapTiles.add(fryingPan.getExtraHitbox());
        fryingPan.setScale(9);
        fryingPan.getExtraHitbox().setScale(9);
        fryingPan.setWebAttachmentAdjustmentX(-100);
        fryingPan.setDragRange(200);
        
        
        fire.setPan(fryingPan); */

        

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // List of messeges that will be displayed in text boxes, IN ORDER
        
        ArrayList<String> messeges = new ArrayList<String>();
        messeges.add("No grandchild of mine is going on a vengeful quest without learning a thing or two first! (T)");
        messeges.add("You can walk using WASD or the arrow keys! (T)");
        messeges.add("Good! Now, practice jumping! (SPACE)");
        messeges.add("Use your natural web shooting abilities! (Q)");
        messeges.add("Well done! To fight other bugs, you'll have to hit them with that web and stomp on 'em! (T)");
        messeges.add("But you'll have to be quick, and do it before they break free! (T)");
        messeges.add("A few final tips! Some objects can be pushed by walking into them, or pulled with Y. (Y)");
        messeges.add("There are boxes you can swing from or jump to using E. (E)");
        messeges.add("Those crates up ahead can be climbed by holding C and W or UP. (C)");
        messeges.add("That's all! You can review these controls at any time from the pause menu by pressing P. (T)");
        messeges.add("It looks like there is a light at the far end of the basement. I'll meet you out there. (T)");

        
        OldSpider spiderNPC = new OldSpider(getMapTile(5, 12).getLocation().addY(13), "LEFT", messeges, -20);
        npcs.add(spiderNPC);

        return npcs;
    }
}