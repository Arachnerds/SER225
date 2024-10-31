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
                .withTileType(TileType.PASSABLE);

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

        MapTileBuilder leftTopWindowTile = new MapTileBuilder(leftTopWindowFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftTopWindowTile);

        // Window Top Pane
        Frame windowTopPaneFrame = new FrameBuilder(getSubImage(4, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowTopPaneTile = new MapTileBuilder(windowTopPaneFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowTopPaneTile);

        // Window Top Pane Bar
        Frame windowTopPaneBarFrame = new FrameBuilder(getSubImage(4, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowTopPaneBarTile = new MapTileBuilder(windowTopPaneBarFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowTopPaneBarTile);

        // Right Top Window
        Frame rightTopWindowFrame = new FrameBuilder(getSubImage(4, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightTopWindowTile = new MapTileBuilder(rightTopWindowFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightTopWindowTile);

        // Left Basement Brick
        Frame leftBasementBrickFrame = new FrameBuilder(getSubImage(5, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBasementBrickTile = new MapTileBuilder(leftBasementBrickFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftBasementBrickTile);

        // Middle Basement Brick 1
        Frame middleBasementBrickFrame1 = new FrameBuilder(getSubImage(5, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile1 = new MapTileBuilder(middleBasementBrickFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickTile1);

        // Middle Basement Brick 2
        Frame middleBasementBrickFrame2 = new FrameBuilder(getSubImage(5, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickTile2 = new MapTileBuilder(middleBasementBrickFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickTile2);

        // Right Basement Brick
        Frame rightBasementBrickFrame = new FrameBuilder(getSubImage(5, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBasementBrickTile = new MapTileBuilder(rightBasementBrickFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightBasementBrickTile);

        // Left Bottom Window
        Frame leftBottomWindowFrame = new FrameBuilder(getSubImage(5, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBottomWindowTile = new MapTileBuilder(leftBottomWindowFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftBottomWindowTile);

        // Window Bottom Pane
        Frame windowBottomPaneFrame = new FrameBuilder(getSubImage(5, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowBottomPaneTile = new MapTileBuilder(windowBottomPaneFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowBottomPaneTile);

        // Window Bottom Pane Bar
        Frame windowBottomPaneBarFrame = new FrameBuilder(getSubImage(6, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowBottomPaneBarTile = new MapTileBuilder(windowBottomPaneBarFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowBottomPaneBarTile);

        // Right Bottom Window
        Frame rightBottomWindowFrame = new FrameBuilder(getSubImage(6, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBottomWindowTile = new MapTileBuilder(rightBottomWindowFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightBottomWindowTile);

        // Middle Basement Brick Pillar 1
        Frame middleBasementBrickPillarFrame1 = new FrameBuilder(getSubImage(6, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickPillarTile1 = new MapTileBuilder(middleBasementBrickPillarFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickPillarTile1);

        // Middle Basement Brick Pillar 2
        Frame middleBasementBrickPillarFrame2 = new FrameBuilder(getSubImage(6, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementBrickPillarTile2 = new MapTileBuilder(middleBasementBrickPillarFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickPillarTile2);

        // Middle Basement Brick Middle Shelf 1
        Frame middleBasementBrickMiddleShelfFrame1 = new FrameBuilder(getSubImage(6, 4))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickMiddleShelfTile1 = new MapTileBuilder(middleBasementBrickMiddleShelfFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickMiddleShelfTile1);

        // Middle Basement Brick Left Shelf 1
        Frame middleBasementBrickLeftShelfFrame1 = new FrameBuilder(getSubImage(6, 5))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickLeftShelfTile1 = new MapTileBuilder(middleBasementBrickLeftShelfFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickLeftShelfTile1);

        // Middle Basement Brick Right Shelf 1
        Frame middleBasementBrickRightShelfFrame1 = new FrameBuilder(getSubImage(7, 0))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickRightShelfTile1 = new MapTileBuilder(middleBasementBrickRightShelfFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickRightShelfTile1);

        // Middle Basement Brick Middle Shelf 2
        Frame middleBasementBrickMiddleShelfFrame2 = new FrameBuilder(getSubImage(7, 1))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickMiddleShelfTile2 = new MapTileBuilder(middleBasementBrickMiddleShelfFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickMiddleShelfTile2);

        // Middle Basement Brick Left Shelf 2
        Frame middleBasementBrickLeftShelfFrame2 = new FrameBuilder(getSubImage(7, 2))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickLeftShelfTile2 = new MapTileBuilder(middleBasementBrickLeftShelfFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementBrickLeftShelfTile2);

        // Middle Basement Brick Right Shelf 2
        Frame middleBasementBrickRightShelfFrame2 = new FrameBuilder(getSubImage(7, 3))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 4)
                .build();

        MapTileBuilder middleBasementBrickRightShelfTile2 = new MapTileBuilder(middleBasementBrickRightShelfFrame2)
                .withTileType(TileType.PASSABLE);

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

        MapTileBuilder grassBladesTile = new MapTileBuilder(grassBladesFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(grassBladesTile);

        // Left Basement Floor
        Frame leftBasementFloorFrame = new FrameBuilder(getSubImage(8, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder leftBasementFloorTile = new MapTileBuilder(leftBasementFloorFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(leftBasementFloorTile);

        // Middle Basement Floor 1
        Frame middleBasementFloorFrame1 = new FrameBuilder(getSubImage(8, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile1 = new MapTileBuilder(middleBasementFloorFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementFloorTile1);

        // Middle Basement Floor 2
        Frame middleBasementFloorFrame2 = new FrameBuilder(getSubImage(8, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorTile2 = new MapTileBuilder(middleBasementFloorFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementFloorTile2);

        // Right Basement Floor
        Frame rightBasementFloorFrame = new FrameBuilder(getSubImage(8, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder rightBasementFloorTile = new MapTileBuilder(rightBasementFloorFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(rightBasementFloorTile);

        // Middle Basement Floor Pillar 1
        Frame middleBasementFloorPillarFrame1 = new FrameBuilder(getSubImage(8, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorPillarTile1 = new MapTileBuilder(middleBasementFloorPillarFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(middleBasementFloorPillarTile1);

        // Middle Basement Brick Pillar 2
        Frame middleBasementFloorPillarFrame2 = new FrameBuilder(getSubImage(8, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder middleBasementFloorPillarTile2 = new MapTileBuilder(middleBasementFloorPillarFrame2)
                .withTileType(TileType.PASSABLE);

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

        MapTileBuilder cellarOpeningUpperSlopeTile = new MapTileBuilder(cellarOpeningUpperSlopeFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(cellarOpeningUpperSlopeTile);

        // Cellar Opening Lower Slope
        Frame cellarOpeningLowerSlopeFrame = new FrameBuilder(getSubImage(10, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarOpeningLowerSlopeTile = new MapTileBuilder(cellarOpeningLowerSlopeFrame)
                .withTileType(TileType.PASSABLE);


        mapTiles.add(cellarOpeningLowerSlopeTile);

        // Cellar Opening Brick
        Frame cellarOpeningBrickFrame = new FrameBuilder(getSubImage(11, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarOpeningBrickTile = new MapTileBuilder(cellarOpeningBrickFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(cellarOpeningBrickTile);

        // Cellar Door Lower
        Frame cellarDoorLowerFrame = new FrameBuilder(getSubImage(11, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorLowerTile = new MapTileBuilder(cellarDoorLowerFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(cellarDoorLowerTile);

        // Cellar Door Upper
        Frame cellarDoorUpperFrame = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorUpperTile = new MapTileBuilder(cellarDoorUpperFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(cellarDoorUpperTile);

        // Cellar Door Filler
        Frame cellarDoorFillerFrame = new FrameBuilder(getSubImage(11, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder cellarDoorFillerTile = new MapTileBuilder(cellarDoorFillerFrame)
                .withTileType(TileType.PASSABLE);

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
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box00Tile);

        // Box 01
        Frame box01Frame = new FrameBuilder(getSubImage(12, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box01Tile = new MapTileBuilder(box01Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box01Tile);

        // Box 02
        Frame box02Frame = new FrameBuilder(getSubImage(12, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box02Tile = new MapTileBuilder(box02Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box02Tile);

        // Box 03
        Frame box03Frame = new FrameBuilder(getSubImage(12, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box03Tile = new MapTileBuilder(box03Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box03Tile);

        // Box 04
        Frame box04Frame = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box04Tile = new MapTileBuilder(box04Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box04Tile);

        // Box 10
        Frame box10Frame = new FrameBuilder(getSubImage(13, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box10Tile = new MapTileBuilder(box10Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box10Tile);

        // Box 11
        Frame box11Frame = new FrameBuilder(getSubImage(13, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box11Tile = new MapTileBuilder(box11Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box11Tile);

        // Box 12
        Frame box12Frame = new FrameBuilder(getSubImage(13, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box12Tile = new MapTileBuilder(box12Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box12Tile);

        // Box 13
        Frame box13Frame = new FrameBuilder(getSubImage(13, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box13Tile = new MapTileBuilder(box13Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box13Tile);

        // Box 14
        Frame box14Frame = new FrameBuilder(getSubImage(13, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box14Tile = new MapTileBuilder(box14Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box14Tile);

        // Box 20
        Frame box20Frame = new FrameBuilder(getSubImage(14, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box20Tile = new MapTileBuilder(box20Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box20Tile);

        // Box 21
        Frame box21Frame = new FrameBuilder(getSubImage(14, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box21Tile = new MapTileBuilder(box21Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box21Tile);

        // Box 22
        Frame box22Frame = new FrameBuilder(getSubImage(14, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box22Tile = new MapTileBuilder(box22Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box22Tile);

        // Box 23
        Frame box23Frame = new FrameBuilder(getSubImage(14, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box23Tile = new MapTileBuilder(box23Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box23Tile);

        // Box 24
        Frame box24Frame = new FrameBuilder(getSubImage(14, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box24Tile = new MapTileBuilder(box24Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box24Tile);

        // Box 30
        Frame box30Frame = new FrameBuilder(getSubImage(15, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box30Tile = new MapTileBuilder(box30Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box30Tile);

        // Box 31
        Frame box31Frame = new FrameBuilder(getSubImage(15, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box31Tile = new MapTileBuilder(box31Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box31Tile);

        // Box 32
        Frame box32Frame = new FrameBuilder(getSubImage(15, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box32Tile = new MapTileBuilder(box32Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box32Tile);

        // Box 33
        Frame box33Frame = new FrameBuilder(getSubImage(15, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box33Tile = new MapTileBuilder(box33Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box33Tile);

        // Box 34
        Frame box34Frame = new FrameBuilder(getSubImage(15, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder box34Tile = new MapTileBuilder(box34Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box34Tile);

        // Box 40
        Frame box40Frame = new FrameBuilder(getSubImage(16, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder box40Tile = new MapTileBuilder(box40Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box40Tile);

        // Box 41
        Frame box41Frame = new FrameBuilder(getSubImage(16, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder box41Tile = new MapTileBuilder(box41Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box41Tile);

        // Box 42
        Frame box42Frame = new FrameBuilder(getSubImage(16, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder box42Tile = new MapTileBuilder(box42Frame)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(box42Tile);

        // Box 43
        Frame box43Frame = new FrameBuilder(getSubImage(16, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder box43Tile = new MapTileBuilder(box43Frame)
                .withTileType(TileType.CLIMBABLE);

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

        MapTileBuilder darkWall1Tile = new MapTileBuilder(darkWall1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkWall1Tile);

        // Dark Wall 2
        Frame darkWall2Frame = new FrameBuilder(getSubImage(11, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkWall2Tile = new MapTileBuilder(darkWall2Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkWall2Tile);


        // Dark Base 1
        Frame darkBase1Frame = new FrameBuilder(getSubImage(11, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkBase1Tile = new MapTileBuilder(darkBase1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkBase1Tile);

        // Dark Base 2
        Frame darkBase2Frame = new FrameBuilder(getSubImage(12, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkBase2Tile = new MapTileBuilder(darkBase2Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkBase2Tile);

        // Dark Pillar 1
        Frame darkPillar1Frame = new FrameBuilder(getSubImage(12, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkPillar1Tile = new MapTileBuilder(darkPillar1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkPillar1Tile);

        // Dark Pillar 2
        Frame darkPillar2Frame = new FrameBuilder(getSubImage(13, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkPillar2Tile = new MapTileBuilder(darkPillar2Frame)
                .withTileType(TileType.PASSABLE);

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

        MapTileBuilder darkShelfMiddle1Tile = new MapTileBuilder(darkShelfMiddle1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfMiddle1Tile);

        // Dark Shelf Left 1
        Frame darkShelfLeft1Frame = new FrameBuilder(getSubImage(15, 4))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfLeft1Tile = new MapTileBuilder(darkShelfLeft1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfLeft1Tile);

        // Dark Shelf Right 1
        Frame darkShelfRight1Frame = new FrameBuilder(getSubImage(15, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfRight1Tile1 = new MapTileBuilder(darkShelfRight1Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfRight1Tile1);

        // Dark Shelf Middle 2
        Frame darkShelfMiddle2Frame = new FrameBuilder(getSubImage(16, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfMiddle2Tile = new MapTileBuilder(darkShelfMiddle2Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfMiddle2Tile);

        // Dark Shelf Left 2
        Frame darkShelfLeft2Frame = new FrameBuilder(getSubImage(16, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfLeft2Tile = new MapTileBuilder(darkShelfLeft2Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfLeft2Tile);

        // Dark Shelf Right 2
        Frame darkShelfRight2Frame = new FrameBuilder(getSubImage(16, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder darkShelfRight2Tile = new MapTileBuilder(darkShelfRight2Frame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(darkShelfRight2Tile);

        // Lighter Wall
        Frame ligherWallFrame = new FrameBuilder(getSubImage(16, 3))
                .withScale(tileScale)
                .build();

        MapTileBuilder ligherWallTile = new MapTileBuilder(ligherWallFrame)
                .withTileType(TileType.PASSABLE);

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

        // House Side
        Frame houseSideFrame = new FrameBuilder(getSubImage(0, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseSideTile = new MapTileBuilder(houseSideFrame)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseSideTile);

        // Window Frame Top
        Frame windowFrameTop = new FrameBuilder(getSubImage(0, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameTopT = new MapTileBuilder(windowFrameTop)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameTopT);

        // Window Frame Bottom
        Frame windowFrameBottom = new FrameBuilder(getSubImage(0, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameBottomT = new MapTileBuilder(windowFrameBottom)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameBottomT);

        // Window Frame Left
        Frame windowFrameLeft = new FrameBuilder(getSubImage(0, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameLeftT = new MapTileBuilder(windowFrameLeft)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameLeftT);

        // Window Frame Right
        Frame windowFrameRight = new FrameBuilder(getSubImage(0, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameRightT = new MapTileBuilder(windowFrameRight)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameRightT);

        // Window Frame Corner TL
        Frame windowFrameCornerTL = new FrameBuilder(getSubImage(0, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameCornerTLT = new MapTileBuilder(windowFrameCornerTL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameCornerTLT);

        // Window Frame Corner TR
        Frame windowFrameCornerTR = new FrameBuilder(getSubImage(0, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameCornerTRT = new MapTileBuilder(windowFrameCornerTR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameCornerTRT);

        // Window Frame Corner BL
        Frame windowFrameCornerBL = new FrameBuilder(getSubImage(0, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameCornerBLT = new MapTileBuilder(windowFrameCornerBL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameCornerBLT);

        // Window Frame Corner BR
        Frame windowFrameCornerBR = new FrameBuilder(getSubImage(0, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFrameCornerBRT = new MapTileBuilder(windowFrameCornerBR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFrameCornerBRT);

        // House Siding
        Frame houseSiding = new FrameBuilder(getSubImage(0, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder houseSidingT = new MapTileBuilder(houseSiding)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(houseSidingT);

        // Window Pane Top
        Frame windowPaneTop = new FrameBuilder(getSubImage(1, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneTopT = new MapTileBuilder(windowPaneTop)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneTopT);

        // Window Pane Bottom
        Frame windowPaneBottom = new FrameBuilder(getSubImage(1, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneBottomT = new MapTileBuilder(windowPaneBottom)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneBottomT);

        // Window Pane Middle
        Frame windowPaneMiddle = new FrameBuilder(getSubImage(1, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneMiddleT = new MapTileBuilder(windowPaneMiddle)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneMiddleT);

        // Window Pane Frame Left
        Frame windowPaneFrameLeft = new FrameBuilder(getSubImage(1, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneFrameLeftT = new MapTileBuilder(windowPaneFrameLeft)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneFrameLeftT);

        // Window Pane Frame Right
        Frame windowPaneFrameRight = new FrameBuilder(getSubImage(1, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneFrameRightT = new MapTileBuilder(windowPaneFrameRight)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneFrameRightT);

        // Window Pane Frame Right
        Frame windowAlt1 = new FrameBuilder(getSubImage(1, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt1T = new MapTileBuilder(windowAlt1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt1T);

        // Window Pane Frame Right
        Frame windowAlt2 = new FrameBuilder(getSubImage(1, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt2T = new MapTileBuilder(windowAlt2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt2T);

        // Window Pane Frame Right
        Frame windowAlt3 = new FrameBuilder(getSubImage(1, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt3T = new MapTileBuilder(windowAlt3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt3T);

        // Window Pane Frame Right
        Frame windowAlt4 = new FrameBuilder(getSubImage(1, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt4T = new MapTileBuilder(windowAlt4)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt4T);

        // Window Pane Frame Right
        Frame windowAlt5 = new FrameBuilder(getSubImage(1, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt5T = new MapTileBuilder(windowAlt5)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt5T);

        // Window Pane Frame Right
        Frame windowAlt6 = new FrameBuilder(getSubImage(1, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt6T = new MapTileBuilder(windowAlt6)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt6T);

        // Garden Wood
        Frame gardenWood = new FrameBuilder(getSubImage(2, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT = new MapTileBuilder(gardenWood)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT);

        // Tomato Top
        Frame tomatoTopTL = new FrameBuilder(getSubImage(2, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoTopTLT = new MapTileBuilder(tomatoTopTL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoTopTLT);

        // Tomato Top
        Frame tomatoTopTR = new FrameBuilder(getSubImage(2, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoTopTRT = new MapTileBuilder(tomatoTopTR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoTopTRT);

        // Tomato Top
        Frame tomatoTopBL = new FrameBuilder(getSubImage(2, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoTopBLT = new MapTileBuilder(tomatoTopBL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoTopBLT);

        // Tomato Top
        Frame tomatoTopBR = new FrameBuilder(getSubImage(2, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoTopBRT = new MapTileBuilder(tomatoTopBR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoTopBRT);

        // Tomato Top
        Frame tomatoBottomTL = new FrameBuilder(getSubImage(3, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoBottomTLT = new MapTileBuilder(tomatoBottomTL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoBottomTLT);

        // Tomato Top
        Frame tomatoBottomTR = new FrameBuilder(getSubImage(3, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoBottomTRT = new MapTileBuilder(tomatoBottomTR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoBottomTRT);

        // Tomato Top
        Frame tomatoBottomBL = new FrameBuilder(getSubImage(3, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoBottomBLT = new MapTileBuilder(tomatoBottomBL)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoBottomBLT);

        // Tomato Top
        Frame tomatoBottomBR = new FrameBuilder(getSubImage(3, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoBottomBRT = new MapTileBuilder(tomatoBottomBR)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoBottomBRT);

        // Bark Inside
        Frame barkInside = new FrameBuilder(getSubImage(3, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder barkInsideT = new MapTileBuilder(barkInside)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(barkInsideT);

        // tree trunk
        Frame trunkPass = new FrameBuilder(getSubImage(1, 0))
                .withScale(tileScale)
                .build();

        MapTileBuilder trunkPassT = new MapTileBuilder(trunkPass)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(trunkPassT);

        // Garden Wood
        Frame gardenWood1 = new FrameBuilder(getSubImage(4, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT1 = new MapTileBuilder(gardenWood1)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT1);

        // Garden Wood
        Frame gardenWood2 = new FrameBuilder(getSubImage(4, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT2 = new MapTileBuilder(gardenWood2)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT2);

        // Garden Wood
        Frame gardenWood3 = new FrameBuilder(getSubImage(4, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT3 = new MapTileBuilder(gardenWood3)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT3);

        // Garden Wood
        Frame gardenWood4 = new FrameBuilder(getSubImage(4, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT4 = new MapTileBuilder(gardenWood4)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT4);

        // Garden Wood
        Frame gardenWood5 = new FrameBuilder(getSubImage(5, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT5 = new MapTileBuilder(gardenWood5)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT5);

        // Garden Wood
        Frame gardenWood6 = new FrameBuilder(getSubImage(5, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT6 = new MapTileBuilder(gardenWood6)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT6);

        // Garden Wood
        Frame gardenWood7 = new FrameBuilder(getSubImage(5, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT7 = new MapTileBuilder(gardenWood7)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT7);

        // Garden Wood
        Frame gardenWood8 = new FrameBuilder(getSubImage(5, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT8 = new MapTileBuilder(gardenWood8)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT8);

        // Garden Wood
        Frame gardenWood9 = new FrameBuilder(getSubImage(4, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT9 = new MapTileBuilder(gardenWood9)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT9);

        // Garden Wood
        Frame gardenWood10 = new FrameBuilder(getSubImage(4, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT10 = new MapTileBuilder(gardenWood10)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT10);

        // Garden Wood
        Frame gardenWood11 = new FrameBuilder(getSubImage(4, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT11 = new MapTileBuilder(gardenWood11)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT11);

        // Garden Wood
        Frame gardenWood12 = new FrameBuilder(getSubImage(5, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT12 = new MapTileBuilder(gardenWood12)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT12);

        // Garden Wood
        Frame gardenWood13 = new FrameBuilder(getSubImage(5, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT13 = new MapTileBuilder(gardenWood13)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT13);

        // Garden Wood
        Frame gardenWood14 = new FrameBuilder(getSubImage(5, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder gardenWoodT14 = new MapTileBuilder(gardenWood14)
                .withTileType(TileType.CLIMBABLE);

        mapTiles.add(gardenWoodT14);

        // Tomato Post
        Frame tomatoPost1 = new FrameBuilder(getSubImage(3, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoPost1T = new MapTileBuilder(tomatoPost1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoPost1T);

        // Tomato Post
        Frame tomatoPost2 = new FrameBuilder(getSubImage(3, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder tomatoPost2T = new MapTileBuilder(tomatoPost2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(tomatoPost2T);

        // Bulkhead Body
        Frame bulkBody = new FrameBuilder(getSubImage(3, 5))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkBodyT = new MapTileBuilder(bulkBody)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkBodyT);

        // Bulkhead Door
        Frame bulkDoor1 = new FrameBuilder(getSubImage(2, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkDoor1T = new MapTileBuilder(bulkDoor1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkDoor1T);

        // Bulkhead Door
        Frame bulkDoor2 = new FrameBuilder(getSubImage(2, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkDoor2T = new MapTileBuilder(bulkDoor2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkDoor2T);

        // Bulkhead Wall
        Frame bulkFillWall = new FrameBuilder(getSubImage(2, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkFillWallT = new MapTileBuilder(bulkFillWall)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkFillWallT);

        // Bulkhead Top
        Frame bulkTop1 = new FrameBuilder(getSubImage(3, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTop1T = new MapTileBuilder(bulkTop1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTop1T);

        // Bulkhead Top
        Frame bulkTop2 = new FrameBuilder(getSubImage(4, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTop2T = new MapTileBuilder(bulkTop2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTop2T);

        // Bulkhead Top
        Frame bulkTop3 = new FrameBuilder(getSubImage(5, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTop3T = new MapTileBuilder(bulkTop3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTop3T);

        // Bulkhead Walll
        Frame bulkWalll = new FrameBuilder(getSubImage(2, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkWalllT = new MapTileBuilder(bulkWalll)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkWalllT);

        // Bulkhead Top
        Frame bulkTopA1 = new FrameBuilder(getSubImage(3, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTopA1T = new MapTileBuilder(bulkTopA1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTopA1T);

        // Bulkhead Top
        Frame bulkATop2 = new FrameBuilder(getSubImage(4, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTopA2T = new MapTileBuilder(bulkATop2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTopA2T);

        // Bulkhead Top
        Frame bulkTopA3 = new FrameBuilder(getSubImage(5, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkTopA3T = new MapTileBuilder(bulkTopA3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkTopA3T);

        // Bulkhead ?
        Frame bulkWhat = new FrameBuilder(getSubImage(2, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder bulkWhatT = new MapTileBuilder(bulkWhat)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(bulkWhatT);

        // Bulkhead Doorknob
        Frame bulkKnob = new FrameBuilder(getSubImage(3, 15))
                .withScale(tileScale)
                .withBounds(5, 5, 12, 12)
                .build();

        MapTileBuilder bulkKnobT = new MapTileBuilder(bulkKnob)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(bulkKnobT);

        // Window Pane Top
        Frame windowPaneTopB = new FrameBuilder(getSubImage(7, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneTopBT = new MapTileBuilder(windowPaneTopB)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneTopBT);

        // Window Pane Bottom
        Frame windowPaneBottomB = new FrameBuilder(getSubImage(7, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneBottomBT = new MapTileBuilder(windowPaneBottomB)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneBottomBT);

        // Window Pane Middle
        Frame windowPaneMiddleB = new FrameBuilder(getSubImage(7, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneMiddleBT = new MapTileBuilder(windowPaneMiddleB)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneMiddleBT);

        // Window Pane Frame Left
        Frame windowPaneFrameLeftB = new FrameBuilder(getSubImage(7, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneFrameLeftBT = new MapTileBuilder(windowPaneFrameLeftB)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneFrameLeftBT);

        // Window Pane Frame Right
        Frame windowPaneFrameRightB = new FrameBuilder(getSubImage(7, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowPaneFrameRightBT = new MapTileBuilder(windowPaneFrameRightB)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowPaneFrameRightBT);

        // Window Pane Frame Right
        Frame windowAlt1B = new FrameBuilder(getSubImage(7, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt1BT = new MapTileBuilder(windowAlt1B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt1BT);

        // Window Pane Frame Right
        Frame windowAlt2B = new FrameBuilder(getSubImage(7, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt2BT = new MapTileBuilder(windowAlt2B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt2BT);

        // Window Pane Frame Right
        Frame windowAlt3B = new FrameBuilder(getSubImage(7, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt3BT = new MapTileBuilder(windowAlt3B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt3BT);

        // Window Pane Frame Right
        Frame windowAlt4B = new FrameBuilder(getSubImage(7, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt4BT = new MapTileBuilder(windowAlt4B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt4BT);

        // Window Pane Frame Right
        Frame windowAlt5B = new FrameBuilder(getSubImage(7, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt5BT = new MapTileBuilder(windowAlt5B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt5BT);

        // Window Pane Frame Right
        Frame windowAlt6B = new FrameBuilder(getSubImage(7, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowAlt6BT = new MapTileBuilder(windowAlt6B)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowAlt6BT);

        // Window Pane Frame Right
        Frame windowOutsideBlue1 = new FrameBuilder(getSubImage(6, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue1T = new MapTileBuilder(windowOutsideBlue1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue1T);

        // Window Pane Frame Right
        Frame windowOutsideBlue2 = new FrameBuilder(getSubImage(6, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue2T = new MapTileBuilder(windowOutsideBlue2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue2T);

        // Window Pane Frame Right
        Frame windowOutsideBlue3 = new FrameBuilder(getSubImage(6, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue3T = new MapTileBuilder(windowOutsideBlue3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue3T);

        // Window Pane Frame Right
        Frame windowOutsideBlue4 = new FrameBuilder(getSubImage(6, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue4T = new MapTileBuilder(windowOutsideBlue4)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue4T);

        // Window Pane Frame Right
        Frame windowOutsideBlue4A = new FrameBuilder(getSubImage(6, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue4TA = new MapTileBuilder(windowOutsideBlue4A)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue4TA);

        // Window Pane Frame Right
        Frame windowOutsideBlue5A = new FrameBuilder(getSubImage(6, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue5TA = new MapTileBuilder(windowOutsideBlue5A)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue5TA);

        // Window Pane Frame Right
        Frame windowOutsideBlue6A = new FrameBuilder(getSubImage(6, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue6TA = new MapTileBuilder(windowOutsideBlue6A)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue6TA);

        // Window Pane Frame Right
        Frame windowOutsideBlue6AA = new FrameBuilder(getSubImage(6, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowOutsideBlue6TAA = new MapTileBuilder(windowOutsideBlue6AA)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowOutsideBlue6TAA);

        // Window Frame Top
        Frame windowLedge = new FrameBuilder(getSubImage(0, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowLedgeT = new MapTileBuilder(windowLedge)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(windowLedgeT);

        // grey rock
        Frame greyRockFramePASS = new FrameBuilder(getSubImage(3, 2))
                .withScale(tileScale)
                .build();

        MapTileBuilder greyRockTilePASS = new MapTileBuilder(greyRockFramePASS)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(greyRockTilePASS);
        
        // Floor Bedroom
        Frame bedroomFloor = new FrameBuilder(getSubImage(2, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder bedroomFloorT = new MapTileBuilder(bedroomFloor)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedroomFloorT);

        // End Blanket
        Frame blanketEnd = new FrameBuilder(getSubImage(4, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder blanketEndT = new MapTileBuilder(blanketEnd)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(blanketEndT);

        // Blanket
        Frame blanket = new FrameBuilder(getSubImage(3, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder blanketT = new MapTileBuilder(blanket)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(blanketT);

        // End Blanket
        Frame throwBlanket = new FrameBuilder(getSubImage(5, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder throwBlanketT = new MapTileBuilder(throwBlanket)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(throwBlanketT);

        // Blanket
        Frame throwTop = new FrameBuilder(getSubImage(4, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder throwTopT = new MapTileBuilder(throwTop)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(throwTopT);

        // Blanket
        Frame throwBottom = new FrameBuilder(getSubImage(5, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder throwBottomT = new MapTileBuilder(throwBottom)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(throwBottomT);

        // Blanket
        Frame blanketEnd1 = new FrameBuilder(getSubImage(6, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder blanketEnd1T = new MapTileBuilder(blanketEnd1)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(blanketEnd1T);

        // Blanket
        Frame blanketBottom = new FrameBuilder(getSubImage(6, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder blanketBottomT = new MapTileBuilder(blanketBottom)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(blanketBottomT);

        // Blanket
        Frame blanketBottomEnd = new FrameBuilder(getSubImage(6, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder blanketBottomEndT = new MapTileBuilder(blanketBottomEnd)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(blanketBottomEndT);

        // Blanket
        Frame bedLeg = new FrameBuilder(getSubImage(8, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder bedLegT = new MapTileBuilder(bedLeg)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedLegT);

        // Blanket
        Frame pillow1 = new FrameBuilder(getSubImage(8, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow1T = new MapTileBuilder(pillow1)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow1T);

        // Blanket
        Frame pillow2 = new FrameBuilder(getSubImage(8, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow2T = new MapTileBuilder(pillow2)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow2T);

        // Blanket
        Frame pillow3 = new FrameBuilder(getSubImage(8, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow3T = new MapTileBuilder(pillow3)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow3T);

        // Blanket
        Frame pillow4 = new FrameBuilder(getSubImage(8, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow4T = new MapTileBuilder(pillow4)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow4T);

        // Blanket
        Frame pillow5 = new FrameBuilder(getSubImage(8, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow5T = new MapTileBuilder(pillow5)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow5T);

        // Blanket
        Frame pillow6 = new FrameBuilder(getSubImage(8, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow6T = new MapTileBuilder(pillow6)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow6T);

        // Blanket
        Frame pillow7 = new FrameBuilder(getSubImage(8, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow7T = new MapTileBuilder(pillow7)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow7T);

        // Blanket
        Frame pillow8 = new FrameBuilder(getSubImage(8, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder pillow8T = new MapTileBuilder(pillow8)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(pillow8T);

        // Blanket
        Frame mattress = new FrameBuilder(getSubImage(8, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder mattressT = new MapTileBuilder(mattress)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(mattressT);

        // Blanket
        Frame mattressEnd = new FrameBuilder(getSubImage(8, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder mattressEndT = new MapTileBuilder(mattressEnd)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(mattressEndT);

        // Blanket
        Frame mattressEndBottom = new FrameBuilder(getSubImage(9, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder mattressEndBottomT = new MapTileBuilder(mattressEndBottom)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(mattressEndBottomT);

        // Blanket
        Frame windowFix1 = new FrameBuilder(getSubImage(9, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix1T = new MapTileBuilder(windowFix1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix1T);

        // Blanket
        Frame windowFix2 = new FrameBuilder(getSubImage(9, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix2T = new MapTileBuilder(windowFix2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix2T);

        // Blanket
        Frame windowFix3 = new FrameBuilder(getSubImage(9, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix3T = new MapTileBuilder(windowFix3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix3T);

        // Blanket
        Frame windowFix4 = new FrameBuilder(getSubImage(9, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix4T = new MapTileBuilder(windowFix4)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix4T);

        // Blanket
        Frame windowFix5 = new FrameBuilder(getSubImage(9, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix5T = new MapTileBuilder(windowFix5)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix5T);

        // Blanket
        Frame windowFix6 = new FrameBuilder(getSubImage(9, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix6T = new MapTileBuilder(windowFix6)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix6T);

        // Blanket
        Frame windowFix7 = new FrameBuilder(getSubImage(9, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix7T = new MapTileBuilder(windowFix7)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix7T);

        // Blanket
        Frame windowFix8 = new FrameBuilder(getSubImage(9, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix8T = new MapTileBuilder(windowFix8)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix8T);

        // Blanket
        Frame windowFix9 = new FrameBuilder(getSubImage(9, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix9T = new MapTileBuilder(windowFix9)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix9T);

        // Blanket
        Frame windowFix10 = new FrameBuilder(getSubImage(9, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix10T = new MapTileBuilder(windowFix10)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix10T);

        // Blanket
        Frame windowFix11 = new FrameBuilder(getSubImage(10, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix11T = new MapTileBuilder(windowFix11)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix11T);

        // Blanket
        Frame windowFix12 = new FrameBuilder(getSubImage(10, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix12T = new MapTileBuilder(windowFix12)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix12T);

        // Blanket
        Frame windowFix13 = new FrameBuilder(getSubImage(10, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix13T = new MapTileBuilder(windowFix13)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix13T);

        // Blanket
        Frame windowFix14 = new FrameBuilder(getSubImage(10, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix14T = new MapTileBuilder(windowFix14)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix14T);

        // Blanket
        Frame windowFix15 = new FrameBuilder(getSubImage(10, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix15T = new MapTileBuilder(windowFix15)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix15T);

        // Blanket
        Frame windowFix16 = new FrameBuilder(getSubImage(10, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix16T = new MapTileBuilder(windowFix16)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix16T);

        // Blanket
        Frame windowFix17 = new FrameBuilder(getSubImage(10, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix17T = new MapTileBuilder(windowFix17)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix17T);

        // Blanket
        Frame windowFix18 = new FrameBuilder(getSubImage(10, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix18T = new MapTileBuilder(windowFix18)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix18T);

        // Blanket
        Frame windowFix19 = new FrameBuilder(getSubImage(10, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix19T = new MapTileBuilder(windowFix19)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix19T);

        // Blanket
        Frame windowFix20 = new FrameBuilder(getSubImage(10, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix20T = new MapTileBuilder(windowFix20)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix20T);

        // Blanket
        Frame windowFix21 = new FrameBuilder(getSubImage(10, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix21T = new MapTileBuilder(windowFix21)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix21T);

        // Blanket
        Frame windowFix22 = new FrameBuilder(getSubImage(11, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix22T = new MapTileBuilder(windowFix22)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix22T);

        // Blanket
        Frame windowFix23 = new FrameBuilder(getSubImage(11, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix23T = new MapTileBuilder(windowFix23)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix23T);

        // Blanket
        Frame windowFix24 = new FrameBuilder(getSubImage(11, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix24T = new MapTileBuilder(windowFix24)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix24T);

        // Blanket
        Frame windowFix25 = new FrameBuilder(getSubImage(11, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix25T = new MapTileBuilder(windowFix25)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix25T);

        // Blanket
        Frame windowFix26 = new FrameBuilder(getSubImage(11, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix26T = new MapTileBuilder(windowFix26)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix26T);

        // Blanket
        Frame windowFix27 = new FrameBuilder(getSubImage(11, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix27T = new MapTileBuilder(windowFix27)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix27T);

        // Blanket
        Frame windowFix28 = new FrameBuilder(getSubImage(11, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix28T = new MapTileBuilder(windowFix28)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix28T);

        // Blanket
        Frame windowFix29 = new FrameBuilder(getSubImage(11, 13))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix29T = new MapTileBuilder(windowFix29)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix29T);

        // Blanket
        Frame windowFix30 = new FrameBuilder(getSubImage(11, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix30T = new MapTileBuilder(windowFix30)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix30T);

        // Blanket
        Frame windowFix31 = new FrameBuilder(getSubImage(11, 15))
                .withScale(tileScale)
                .build();

        MapTileBuilder windowFix31T = new MapTileBuilder(windowFix31)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(windowFix31T);

        // Blanket
        Frame floorWall = new FrameBuilder(getSubImage(11, 16))
                .withScale(tileScale)
                .build();

        MapTileBuilder floorWallT = new MapTileBuilder(floorWall)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(floorWallT);

        // Blanket
        Frame floorWallDoor = new FrameBuilder(getSubImage(12, 6))
                .withScale(tileScale)
                .build();

        MapTileBuilder floorWallTDoor = new MapTileBuilder(floorWallDoor)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(floorWallTDoor);

        // Blanket
        Frame doorFrame1 = new FrameBuilder(getSubImage(12, 7))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame1T = new MapTileBuilder(doorFrame1)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame1T);

        // Blanket
        Frame doorFrame2 = new FrameBuilder(getSubImage(12, 8))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame2T = new MapTileBuilder(doorFrame2)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame2T);

        // Blanket
        Frame doorFrame3 = new FrameBuilder(getSubImage(12, 9))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame3T = new MapTileBuilder(doorFrame3)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame3T);

        // Blanket
        Frame doorFrame4 = new FrameBuilder(getSubImage(12, 10))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame4T = new MapTileBuilder(doorFrame4)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame4T);

        // Blanket
        Frame doorFrame5 = new FrameBuilder(getSubImage(12, 11))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame5T = new MapTileBuilder(doorFrame5)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame5T);

        // Blanket
        Frame doorFrame6 = new FrameBuilder(getSubImage(12, 12))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame6T = new MapTileBuilder(doorFrame6)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame6T);

        // Blanket
        Frame doorFrame7 = new FrameBuilder(getSubImage(12, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder doorFrame7T = new MapTileBuilder(doorFrame7)
                .withTileType(TileType.PASSABLE);

        mapTiles.add(doorFrame7T);

        // Blanket
        Frame shelfBed1 = new FrameBuilder(getSubImage(13, 6))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 11)
                .build();

        MapTileBuilder shelfBed1T = new MapTileBuilder(shelfBed1)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(shelfBed1T);

        // Blanket
        Frame shelfBed2 = new FrameBuilder(getSubImage(13, 7))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 11)
                .build();

        MapTileBuilder shelfBed2T = new MapTileBuilder(shelfBed2)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(shelfBed2T);

        // Blanket
        Frame shelfBed3 = new FrameBuilder(getSubImage(13, 8))
                .withScale(tileScale)
                .withBounds(0, 8, 16, 11)
                .build();

        MapTileBuilder shelfBed3T = new MapTileBuilder(shelfBed3)
                .withTileType(TileType.JUMP_THROUGH_PLATFORM);

        mapTiles.add(shelfBed3T);
        
        // water
        Frame bedFrameNonPass = new FrameBuilder(getSubImage(3, 1))
                .withScale(tileScale)
                .build();

        MapTileBuilder bedFrameNonPassT = new MapTileBuilder(bedFrameNonPass)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(bedFrameNonPassT);

        // Bulkhead Walll
        Frame wallNotPassBed = new FrameBuilder(getSubImage(2, 14))
                .withScale(tileScale)
                .build();

        MapTileBuilder wallNotPassBedT = new MapTileBuilder(wallNotPassBed)
                .withTileType(TileType.NOT_PASSABLE);

        mapTiles.add(wallNotPassBedT);

        return mapTiles;
    }
}
