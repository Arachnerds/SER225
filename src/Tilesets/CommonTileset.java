package Tilesets;

import Builders.FrameBuilder;
import Builders.MapTileBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import Level.TileType;
import Level.Tileset;
import Utils.SlopeTileLayoutUtils;

import java.util.ArrayList;

// This class represents a "common" tileset of standard tiles defined in the CommonTileset.png file
public class CommonTileset extends Tileset {

    public CommonTileset() {
        super(ImageLoader.load("CommonTileset.png"), 16, 16, 3);
    }

    @Override
    public ArrayList<MapTileBuilder> defineTiles() {
        ArrayList<MapTileBuilder> mapTiles = new ArrayList<>();

        // grass
        Frame grassFrame = new FrameBuilder(getSubImage(0, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassTile = new MapTileBuilder(grassFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(grassTile);

        // sky
        Frame skyFrame = new FrameBuilder(getSubImage(0, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder skyTile = new MapTileBuilder(skyFrame);

        mapTiles.add(skyTile);

        // dirt
        Frame dirtFrame = new FrameBuilder(getSubImage(0, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder dirtTile = new MapTileBuilder(dirtFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(dirtTile);

        // sun
        Frame[] sunFrames = new Frame[]{
                new FrameBuilder(getSubImage(2, 0), 50)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(2, 1), 50)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder sunTile = new MapTileBuilder(sunFrames);

        mapTiles.add(sunTile);

        // tree trunk with full hole
        Frame treeTrunkWithFullHoleFrame = new FrameBuilder(getSubImage(2, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkWithFullHoleTile = new MapTileBuilder(treeTrunkWithFullHoleFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkWithFullHoleTile);

        // left end branch
        Frame leftEndBranchFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder leftEndBranchTile = new MapTileBuilder(leftEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(leftEndBranchTile);

        // right end branch
        Frame rightEndBranchFrame = new FrameBuilder(getSubImage(1, 5))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .build();

        MapTileBuilder rightEndBranchTile = new MapTileBuilder(rightEndBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(rightEndBranchTile);

        // tree trunk
        Frame treeTrunkFrame = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkTile = new MapTileBuilder(treeTrunkFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkTile);

        // tree top leaves
        Frame treeTopLeavesFrame = new FrameBuilder(getSubImage(1, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTopLeavesTile = new MapTileBuilder(treeTopLeavesFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTopLeavesTile);

        // yellow flower
        Frame[] yellowFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(1, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 2), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(1, 4), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder yellowFlowerTile = new MapTileBuilder(yellowFlowerFrames);

        mapTiles.add(yellowFlowerTile);

        // purple flower
        Frame[] purpleFlowerFrames = new Frame[] {
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 4), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 3), 65)
                        .withScale(tileScale)
                        .build(),
                new FrameBuilder(getSubImage(0, 5), 65)
                        .withScale(tileScale)
                        .build()
        };

        MapTileBuilder purpleFlowerTile = new MapTileBuilder(purpleFlowerFrames);

        mapTiles.add(purpleFlowerTile);

        // middle branch
        Frame middleBranchFrame = new FrameBuilder(getSubImage(2, 3))
                .withScale(tileScale)
                .withBounds(0, 6, 16, 4)
                .build();

        MapTileBuilder middleBranchTile = new MapTileBuilder(middleBranchFrame)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(middleBranchTile);

        // tree trunk hole top
        Frame treeTrunkHoleTopFrame = new FrameBuilder(getSubImage(2, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleTopTile = new MapTileBuilder(treeTrunkHoleTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkHoleTopTile);

        // tree trunk hole bottom
        Frame treeTrunkHoleBottomFrame = new FrameBuilder(getSubImage(2, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder treeTrunkHoleBottomTile = new MapTileBuilder(treeTrunkHoleBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(treeTrunkHoleBottomTile);

        // top water
        Frame topWaterFrame = new FrameBuilder(getSubImage(3, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder topWaterTile = new MapTileBuilder(topWaterFrame);

        mapTiles.add(topWaterTile);

        // water
        Frame waterFrame = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder waterTile = new MapTileBuilder(waterFrame)
                .withTileType(TileType.WATER);

        mapTiles.add(waterTile);

        // grey rock
        Frame greyRockFrame = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTile = new MapTileBuilder(greyRockFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(greyRockTile);

        // left 45 degree slope
        Frame leftSlopeFrame = new FrameBuilder(getSubImage(3, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftSlopeTile = new MapTileBuilder(leftSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createLeft45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftSlopeTile);

        // right 45 degree slope
        Frame rightSlopeFrame = new FrameBuilder(getSubImage(3, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightSlopeTile = new MapTileBuilder(rightSlopeFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createRight45SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(rightSlopeTile);

        // left 30 degree slope bottom
        Frame leftStairsBottomFrame = new FrameBuilder(getSubImage(4, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftStairsBottomTile = new MapTileBuilder(leftStairsBottomFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createBottomLeft30SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftStairsBottomTile);

        // left 30 degree slope top
        Frame leftStairsTopFrame = new FrameBuilder(getSubImage(4, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftStairsTopTile = new MapTileBuilder(leftStairsTopFrame)
                .withTileType(TileType.SLOPE)
                .withTileLayout(SlopeTileLayoutUtils.createTopLeft30SlopeLayout(spriteWidth, (int) tileScale));

        mapTiles.add(leftStairsTopTile);

        // Left Top Window
        Frame leftTopWindowFrame = new FrameBuilder(getSubImage(4, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftTopWindowTile = new MapTileBuilder(leftTopWindowFrame);

        mapTiles.add(leftTopWindowTile);

        // Window Top Pane
        Frame windowTopPaneFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowTopPaneTile = new MapTileBuilder(windowTopPaneFrame);

        mapTiles.add(windowTopPaneTile);

        // Window Top Pane Bar
        Frame windowTopPaneBarFrame = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowTopPaneBarTile = new MapTileBuilder(windowTopPaneBarFrame);

        mapTiles.add(windowTopPaneBarTile);

        // Right Top Window
        Frame rightTopWindowFrame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightTopWindowTile = new MapTileBuilder(rightTopWindowFrame);

        mapTiles.add(rightTopWindowTile);

        // Left Basement Brick
        Frame leftBasementBrickFrame = new FrameBuilder(getSubImage(5, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBasementBrickTile = new MapTileBuilder(leftBasementBrickFrame);

        mapTiles.add(leftBasementBrickTile);

        // Middle Basement Brick 1
        Frame middleBasementBrickFrame1 = new FrameBuilder(getSubImage(5, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile1 = new MapTileBuilder(middleBasementBrickFrame1);

        mapTiles.add(middleBasementBrickTile1);

        // Middle Basement Brick 2
        Frame middleBasementBrickFrame2 = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile2 = new MapTileBuilder(middleBasementBrickFrame2);

        mapTiles.add(middleBasementBrickTile2);

        // Right Basement Brick
        Frame rightBasementBrickFrame = new FrameBuilder(getSubImage(5, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBasementBrickTile = new MapTileBuilder(rightBasementBrickFrame);

        mapTiles.add(rightBasementBrickTile);

        // Left Bottom Window
        Frame leftBottomWindowFrame = new FrameBuilder(getSubImage(5, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBottomWindowTile = new MapTileBuilder(leftBottomWindowFrame);

        mapTiles.add(leftBottomWindowTile);

        // Window Bottom Pane
        Frame windowBottomPaneFrame = new FrameBuilder(getSubImage(5, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowBottomPaneTile = new MapTileBuilder(windowBottomPaneFrame);

        mapTiles.add(windowBottomPaneTile);

        // Window Bottom Pane Bar
        Frame windowBottomPaneBarFrame = new FrameBuilder(getSubImage(6, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowBottomPaneBarTile = new MapTileBuilder(windowBottomPaneBarFrame);

        mapTiles.add(windowBottomPaneBarTile);

        // Right Bottom Window
        Frame rightBottomWindowFrame = new FrameBuilder(getSubImage(6, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBottomWindowTile = new MapTileBuilder(rightBottomWindowFrame);

        mapTiles.add(rightBottomWindowTile);

        // Middle Basement Brick Pillar 1
        Frame middleBasementBrickPillarFrame1 = new FrameBuilder(getSubImage(6, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickPillarTile1 = new MapTileBuilder(middleBasementBrickPillarFrame1);

        mapTiles.add(middleBasementBrickPillarTile1);

        // Middle Basement Brick Pillar 2
        Frame middleBasementBrickPillarFrame2 = new FrameBuilder(getSubImage(6, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickPillarTile2 = new MapTileBuilder(middleBasementBrickPillarFrame2);

        mapTiles.add(middleBasementBrickPillarTile2);

        // Middle Basement Brick Middle Shelf 1
        Frame middleBasementBrickMiddleShelfFrame1 = new FrameBuilder(getSubImage(6, 4))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickMiddleShelfTile1 = new MapTileBuilder(middleBasementBrickMiddleShelfFrame1);

        mapTiles.add(middleBasementBrickMiddleShelfTile1);

        // Middle Basement Brick Left Shelf 1
        Frame middleBasementBrickLeftShelfFrame1 = new FrameBuilder(getSubImage(6, 5))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickLeftShelfTile1 = new MapTileBuilder(middleBasementBrickLeftShelfFrame1);

        mapTiles.add(middleBasementBrickLeftShelfTile1);

        // Middle Basement Brick Right Shelf 1
        Frame middleBasementBrickRightShelfFrame1 = new FrameBuilder(getSubImage(7, 0))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickRightShelfTile1 = new MapTileBuilder(middleBasementBrickRightShelfFrame1);

        mapTiles.add(middleBasementBrickRightShelfTile1);

        // Middle Basement Brick Middle Shelf 2
        Frame middleBasementBrickMiddleShelfFrame2 = new FrameBuilder(getSubImage(7, 1))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickMiddleShelfTile2 = new MapTileBuilder(middleBasementBrickMiddleShelfFrame2);

        mapTiles.add(middleBasementBrickMiddleShelfTile2);

        // Middle Basement Brick Left Shelf 2
        Frame middleBasementBrickLeftShelfFrame2 = new FrameBuilder(getSubImage(7, 2))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickLeftShelfTile2 = new MapTileBuilder(middleBasementBrickLeftShelfFrame2);

        mapTiles.add(middleBasementBrickLeftShelfTile2);

        // Middle Basement Brick Right Shelf 2
        Frame middleBasementBrickRightShelfFrame2 = new FrameBuilder(getSubImage(7, 3))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickRightShelfTile2 = new MapTileBuilder(middleBasementBrickRightShelfFrame2);

        mapTiles.add(middleBasementBrickRightShelfTile2);

        // Wooden Planks
        Frame woodenPlanksFrame = new FrameBuilder(getSubImage(7, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder woodenPlanksTile = new MapTileBuilder(woodenPlanksFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(woodenPlanksTile);

        // Grass Blades
        Frame grassBladesFrame = new FrameBuilder(getSubImage(7, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder grassBladesTile = new MapTileBuilder(grassBladesFrame);

        mapTiles.add(grassBladesTile);

        // Left Basement Floor
        Frame leftBasementFloorFrame = new FrameBuilder(getSubImage(8, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBasementFloorTile = new MapTileBuilder(leftBasementFloorFrame);

        mapTiles.add(leftBasementFloorTile);

        // Middle Basement Floor 1
        Frame middleBasementFloorFrame1 = new FrameBuilder(getSubImage(8, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile1 = new MapTileBuilder(middleBasementFloorFrame1);

        mapTiles.add(middleBasementFloorTile1);

        // Middle Basement Floor 2
        Frame middleBasementFloorFrame2 = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile2 = new MapTileBuilder(middleBasementFloorFrame2);

        mapTiles.add(middleBasementFloorTile2);

        // Right Basement Floor
        Frame rightBasementFloorFrame = new FrameBuilder(getSubImage(8, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBasementFloorTile = new MapTileBuilder(rightBasementFloorFrame);

        mapTiles.add(rightBasementFloorTile);

        // Middle Basement Floor Pillar 1
        Frame middleBasementFloorPillarFrame1 = new FrameBuilder(getSubImage(8, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorPillarTile1 = new MapTileBuilder(middleBasementFloorPillarFrame1);

        mapTiles.add(middleBasementFloorPillarTile1);

        // Middle Basement Brick Pillar 2
        Frame middleBasementFloorPillarFrame2 = new FrameBuilder(getSubImage(8, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorPillarTile2 = new MapTileBuilder(middleBasementFloorPillarFrame2);

        mapTiles.add(middleBasementFloorPillarTile2);

        // Middle Basement Brick 2 (BARRIER)
        Frame middleBasementBrickFrame2Barrier = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile2Barrier = new MapTileBuilder(middleBasementBrickFrame2Barrier)
                .withTileType(TileType.NOT_PASSABLE);
        
        mapTiles.add(middleBasementBrickTile2Barrier);

        // Middle Basement Floor 2 (BARRIER)
        Frame middleBasementFloorFrame2Barrier = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile2Barrier = new MapTileBuilder(middleBasementFloorFrame2Barrier)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(middleBasementFloorTile2Barrier);

        // FILLER1
        Frame filler1Frame = new FrameBuilder(getSubImage(9, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler1Tile = new MapTileBuilder(filler1Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler1Tile);

        // FILLER2
        Frame filler2Frame = new FrameBuilder(getSubImage(9, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler2Tile = new MapTileBuilder(filler2Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler2Tile);

        // Stair Wood
        Frame stairWoodFrame = new FrameBuilder(getSubImage(10, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairWoodTile = new MapTileBuilder(stairWoodFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairWoodTile);

        // FILLER3
        Frame filler3Frame = new FrameBuilder(getSubImage(9, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler3Tile = new MapTileBuilder(filler3Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler3Tile);

        // FILLER4
        Frame filler4Frame = new FrameBuilder(getSubImage(9, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler4Tile = new MapTileBuilder(filler4Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler4Tile);

        // FILLER5
        Frame filler5Frame = new FrameBuilder(getSubImage(9, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler5Tile = new MapTileBuilder(filler5Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler5Tile);

        // FILLER6
        Frame filler6Frame = new FrameBuilder(getSubImage(9, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler6Tile = new MapTileBuilder(filler6Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler6Tile);

        // FILLER7
        Frame filler7Frame = new FrameBuilder(getSubImage(10, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler7Tile = new MapTileBuilder(filler7Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler7Tile);

        // FILLER8
        Frame filler8Frame = new FrameBuilder(getSubImage(10, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler8Tile = new MapTileBuilder(filler8Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler8Tile);

        // FILLER9
        Frame filler9Frame = new FrameBuilder(getSubImage(10, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler9Tile = new MapTileBuilder(filler9Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(filler9Tile);

        // FILLER10
        Frame filler10Frame = new FrameBuilder(getSubImage(10, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler10Tile = new MapTileBuilder(filler10Frame);

        mapTiles.add(filler10Tile);

        // FILLER11
        Frame filler11Frame = new FrameBuilder(getSubImage(10, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler11Tile = new MapTileBuilder(filler11Frame);

        mapTiles.add(filler11Tile);

        // FILLER12
        Frame filler12Frame = new FrameBuilder(getSubImage(11, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler12Tile = new MapTileBuilder(filler12Frame);

        mapTiles.add(filler12Tile);

        // FILLER13
        Frame filler13Frame = new FrameBuilder(getSubImage(11, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler13Tile = new MapTileBuilder(filler13Frame);

        mapTiles.add(filler13Tile);

        // FILLER14
        Frame filler14Frame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler14Tile = new MapTileBuilder(filler14Frame);

        mapTiles.add(filler14Tile);

        // FILLER15
        Frame filler15Frame = new FrameBuilder(getSubImage(11, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder filler15Tile = new MapTileBuilder(filler15Frame);

        mapTiles.add(filler15Tile);

        return mapTiles;
    }
}
