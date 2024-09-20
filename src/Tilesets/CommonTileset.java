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
        Frame middleBasementBrickFrame2Barrier = new FrameBuilder(getSubImage(11, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile2Barrier = new MapTileBuilder(middleBasementBrickFrame2Barrier)
                .withTileType(TileType.NOT_PASSABLE);
        
        mapTiles.add(middleBasementBrickTile2Barrier);

        // Middle Basement Floor 2 (BARRIER)
        Frame middleBasementFloorFrame2Barrier = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile2Barrier = new MapTileBuilder(middleBasementFloorFrame2Barrier)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(middleBasementFloorTile2Barrier);

        // Floor Stair Bottom
        Frame floorStairBottomFrame = new FrameBuilder(getSubImage(9, 0))
                .withScale(tileScale)
                .withBounds(0, 5, 16, 16)
                .build();

        MapTileBuilder floorStairBottomTile = new MapTileBuilder(floorStairBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(floorStairBottomTile);

        // Stair Top
        Frame stairTopFrame = new FrameBuilder(getSubImage(9, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairTopTile = new MapTileBuilder(stairTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairTopTile);

        // Stair Wood
        Frame stairWoodFrame = new FrameBuilder(getSubImage(10, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairWoodTile = new MapTileBuilder(stairWoodFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairWoodTile);

        // Stair Underhang 1
        Frame stairUnderhangFrame1 = new FrameBuilder(getSubImage(9, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairUnderhangTile1 = new MapTileBuilder(stairUnderhangFrame1)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairUnderhangTile1);

        // Wall Stair Bottom
        Frame wallStairBottomFrame = new FrameBuilder(getSubImage(9, 3))
                .withScale(tileScale)
                .withBounds(0, 5, 16, 16)
                .build();

        MapTileBuilder wallStairBottomTile = new MapTileBuilder(wallStairBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(wallStairBottomTile);

        // Stair Underhang 2
        Frame stairUnderhangFrame2 = new FrameBuilder(getSubImage(9, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairUnderhangTile2 = new MapTileBuilder(stairUnderhangFrame2)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairUnderhangTile2);

        // Window Stair Top
        Frame windowStairTopFrame = new FrameBuilder(getSubImage(9, 5))
                .withScale(tileScale)
                .withBounds(0, 5, 16, 16)
                .build();

        MapTileBuilder windowStairTopTile = new MapTileBuilder(windowStairTopFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(windowStairTopTile);

        // Wall with Shelf Stair Bottom
        Frame wallShelfStairBottomFrame = new FrameBuilder(getSubImage(10, 1))
                .withScale(tileScale)
                .withBounds(0, 5, 16, 16)
                .build();

        MapTileBuilder wallShelfStairBottomTile = new MapTileBuilder(wallShelfStairBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(wallShelfStairBottomTile);

        // Stair with Shelf Underhang 1
        Frame stairShelfUnderhangFrame1 = new FrameBuilder(getSubImage(10, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder stairShelfUnderhangTile1 = new MapTileBuilder(stairShelfUnderhangFrame1)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(stairShelfUnderhangTile1);

        // Window Stair Bottom
        Frame windowStairBottomFrame = new FrameBuilder(getSubImage(10, 3))
                .withScale(tileScale)
                .withBounds(0, 5, 16, 16)
                .build();

        MapTileBuilder windowStairBottomTile = new MapTileBuilder(windowStairBottomFrame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(windowStairBottomTile);

        // Cellar Opening Upper Slope
        Frame cellarOpeningUpperSlopeFrame = new FrameBuilder(getSubImage(10, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarOpeningUpperSlopeTile = new MapTileBuilder(cellarOpeningUpperSlopeFrame);

        mapTiles.add(cellarOpeningUpperSlopeTile);

        // Cellar Opening Lower Slope
        Frame cellarOpeningLowerSlopeFrame = new FrameBuilder(getSubImage(10, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarOpeningLowerSlopeTile = new MapTileBuilder(cellarOpeningLowerSlopeFrame);

        mapTiles.add(cellarOpeningLowerSlopeTile);

        // Cellar Opening Brick
        Frame cellarOpeningBrickFrame = new FrameBuilder(getSubImage(11, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarOpeningBrickTile = new MapTileBuilder(cellarOpeningBrickFrame);

        mapTiles.add(cellarOpeningBrickTile);

        // Cellar Door Lower
        Frame cellarDoorLowerFrame = new FrameBuilder(getSubImage(11, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorLowerTile = new MapTileBuilder(cellarDoorLowerFrame);

        mapTiles.add(cellarDoorLowerTile);

        // Cellar Door Upper
        Frame cellarDoorUpperFrame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorUpperTile = new MapTileBuilder(cellarDoorUpperFrame);

        mapTiles.add(cellarDoorUpperTile);

        // Cellar Door Filler
        Frame cellarDoorFillerFrame = new FrameBuilder(getSubImage(11, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorFillerTile = new MapTileBuilder(cellarDoorFillerFrame);

        mapTiles.add(cellarDoorFillerTile);

        /**
         * 
         * 
         * SECTION FOR CLIMBING BOX IN BASEMENT
         * 
         * 
         */

        // Box 00
        Frame box00Frame = new FrameBuilder(getSubImage(12, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box00Tile = new MapTileBuilder(box00Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box00Tile);

        // Box 01
        Frame box01Frame = new FrameBuilder(getSubImage(12, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box01Tile = new MapTileBuilder(box01Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box01Tile);

        // Box 02
        Frame box02Frame = new FrameBuilder(getSubImage(12, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box02Tile = new MapTileBuilder(box02Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box02Tile);

        // Box 03
        Frame box03Frame = new FrameBuilder(getSubImage(12, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box03Tile = new MapTileBuilder(box03Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box03Tile);

        // Box 04
        Frame box04Frame = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box04Tile = new MapTileBuilder(box04Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box04Tile);

        // Box 10
        Frame box10Frame = new FrameBuilder(getSubImage(13, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box10Tile = new MapTileBuilder(box10Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box10Tile);

        // Box 11
        Frame box11Frame = new FrameBuilder(getSubImage(13, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box11Tile = new MapTileBuilder(box11Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box11Tile);

        // Box 12
        Frame box12Frame = new FrameBuilder(getSubImage(13, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box12Tile = new MapTileBuilder(box12Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box12Tile);

        // Box 13
        Frame box13Frame = new FrameBuilder(getSubImage(13, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box13Tile = new MapTileBuilder(box13Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box13Tile);

        // Box 14
        Frame box14Frame = new FrameBuilder(getSubImage(13, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box14Tile = new MapTileBuilder(box14Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box14Tile);

        // Box 20
        Frame box20Frame = new FrameBuilder(getSubImage(14, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box20Tile = new MapTileBuilder(box20Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box20Tile);

        // Box 21
        Frame box21Frame = new FrameBuilder(getSubImage(14, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box21Tile = new MapTileBuilder(box21Frame);

        mapTiles.add(box21Tile);

        // Box 22
        Frame box22Frame = new FrameBuilder(getSubImage(14, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box22Tile = new MapTileBuilder(box22Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box22Tile);

        // Box 23
        Frame box23Frame = new FrameBuilder(getSubImage(14, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box23Tile = new MapTileBuilder(box23Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box23Tile);

        // Box 24
        Frame box24Frame = new FrameBuilder(getSubImage(14, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box24Tile = new MapTileBuilder(box24Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box24Tile);

        // Box 30
        Frame box30Frame = new FrameBuilder(getSubImage(15, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box30Tile = new MapTileBuilder(box30Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box30Tile);

        // Box 31
        Frame box31Frame = new FrameBuilder(getSubImage(15, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box31Tile = new MapTileBuilder(box31Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box31Tile);

        // Box 32
        Frame box32Frame = new FrameBuilder(getSubImage(15, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box32Tile = new MapTileBuilder(box32Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box32Tile);

        // Box 33
        Frame box33Frame = new FrameBuilder(getSubImage(15, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box33Tile = new MapTileBuilder(box33Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box33Tile);

        // Box 34
        Frame box34Frame = new FrameBuilder(getSubImage(15, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box34Tile = new MapTileBuilder(box34Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box34Tile);

        // Box 40
        Frame box40Frame = new FrameBuilder(getSubImage(16, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box40Tile = new MapTileBuilder(box40Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box40Tile);

        // Box 41
        Frame box41Frame = new FrameBuilder(getSubImage(16, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box41Tile = new MapTileBuilder(box41Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box41Tile);

        // Box 42
        Frame box42Frame = new FrameBuilder(getSubImage(16, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box42Tile = new MapTileBuilder(box42Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box42Tile);

        // Box 43
        Frame box43Frame = new FrameBuilder(getSubImage(16, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box43Tile = new MapTileBuilder(box43Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(box43Tile);

        /**
         * 
         * 
         * END BOX SECTION
         * 
         * 
         */

        // Dark Wall 1
        Frame darkWall1Frame = new FrameBuilder(getSubImage(11, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkWall1Tile = new MapTileBuilder(darkWall1Frame);

        mapTiles.add(darkWall1Tile);

        // Dark Wall 2
        Frame darkWall2Frame = new FrameBuilder(getSubImage(11, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkWall2Tile = new MapTileBuilder(darkWall2Frame);

        mapTiles.add(darkWall2Tile);


        // Dark Base 1
        Frame darkBase1Frame = new FrameBuilder(getSubImage(11, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkBase1Tile = new MapTileBuilder(darkBase1Frame);

        mapTiles.add(darkBase1Tile);

        // Dark Base 2
        Frame darkBase2Frame = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkBase2Tile = new MapTileBuilder(darkBase2Frame);

        mapTiles.add(darkBase2Tile);

        // Dark Pillar 1
        Frame darkPillar1Frame = new FrameBuilder(getSubImage(12, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkPillar1Tile = new MapTileBuilder(darkPillar1Frame);

        mapTiles.add(darkPillar1Tile);

        // Dark Pillar 2
        Frame darkPillar2Frame = new FrameBuilder(getSubImage(13, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkPillar2Tile = new MapTileBuilder(darkPillar2Frame);

        mapTiles.add(darkPillar2Tile);

        // Dark Wall Pillar 1
        Frame darkWallPillar1Frame = new FrameBuilder(getSubImage(13, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkWallPillar1Tile = new MapTileBuilder(darkWallPillar1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkWallPillar1Tile);

        // Dark Wall Pillar 2
        Frame darkWallPillar2Frame = new FrameBuilder(getSubImage(14, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkWallPillar2Tile = new MapTileBuilder(darkWallPillar2Frame)
                .withTileType(TileType.PASSABLE);


        mapTiles.add(darkWallPillar2Tile);

        // Dark Shelf Middle 1
        Frame darkShelfMiddle1Frame = new FrameBuilder(getSubImage(14, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfMiddle1Tile = new MapTileBuilder(darkShelfMiddle1Frame);

        mapTiles.add(darkShelfMiddle1Tile);

        // Dark Shelf Left 1
        Frame darkShelfLeft1Frame = new FrameBuilder(getSubImage(15, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfLeft1Tile = new MapTileBuilder(darkShelfLeft1Frame);

        mapTiles.add(darkShelfLeft1Tile);

        // Dark Shelf Right 1
        Frame darkShelfRight1Frame = new FrameBuilder(getSubImage(15, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfRight1Tile1 = new MapTileBuilder(darkShelfRight1Frame);

        mapTiles.add(darkShelfRight1Tile1);

        // Dark Shelf Middle 2
        Frame darkShelfMiddle2Frame = new FrameBuilder(getSubImage(16, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfMiddle2Tile = new MapTileBuilder(darkShelfMiddle2Frame);

        mapTiles.add(darkShelfMiddle2Tile);

        // Dark Shelf Left 2
        Frame darkShelfLeft2Frame = new FrameBuilder(getSubImage(16, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfLeft2Tile = new MapTileBuilder(darkShelfLeft2Frame);

        mapTiles.add(darkShelfLeft2Tile);

        // Dark Shelf Right 2
        Frame darkShelfRight2Frame = new FrameBuilder(getSubImage(16, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfRight2Tile = new MapTileBuilder(darkShelfRight2Frame);

        mapTiles.add(darkShelfRight2Tile);

        // Lighter Wall
        Frame ligherWallFrame = new FrameBuilder(getSubImage(16, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder ligherWallTile = new MapTileBuilder(ligherWallFrame);

        mapTiles.add(ligherWallTile);

        // Dark Stair 1
        Frame darkStair1Frame = new FrameBuilder(getSubImage(16, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkStair1Tile = new MapTileBuilder(darkStair1Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(darkStair1Tile);

        // Dark Stair 2
        Frame darkStair2Frame = new FrameBuilder(getSubImage(16, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkStair2Tile = new MapTileBuilder(darkStair2Frame)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(darkStair2Tile);

        return mapTiles;
    }
}
