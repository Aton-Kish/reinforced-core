package atonkish.reinfcore.util;

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.util.math.Point2i;

public class ReinforcedStorageScreenModel {
    private static final int SLOT_SIZE = 18;
    private static final int CONTAINER_INVENTORY_X = 7;
    private static final int CONTAINER_INVENTORY_Y = 17;
    private static final int GAP_BETWEEN_CONTAINER_INVENTORY_AND_PLAYER_INVENTORY = 14;

    private static final int SINGLE_SCREEN_THRESHOLD_SIZE = 81;
    private static final int SINGLE_SCREEN_DEFAULT_COLS = 9;
    private static final int SCROLL_SCREEN_COLS = 9;
    private static final int SCROLL_SCREEN_ROWS = 5;

    private final ReinforcingMaterial material;
    private final boolean isDoubleBlock;
    private final Point2i containerInventoryPoint;
    private final Point2i playerInventoryPoint;

    public ReinforcedStorageScreenModel(ReinforcingMaterial material, boolean isDoubleBlock,
            Point2i containerInventoryPoint, Point2i playerInventoryPoint) {
        this.material = material;
        this.isDoubleBlock = isDoubleBlock;
        this.containerInventoryPoint = containerInventoryPoint;
        this.playerInventoryPoint = playerInventoryPoint;
    }

    public ReinforcedStorageScreenModel(ReinforcingMaterial material, boolean isDoubleBlock) {
        this(material, isDoubleBlock, calcContainerInventoryPoint(material, isDoubleBlock),
                calcPlayerInventoryPoint(material, isDoubleBlock));
    }

    private static Point2i calcContainerInventoryPoint(ReinforcingMaterial material, boolean isDoubleBlock) {
        int x = CONTAINER_INVENTORY_X;
        int y = CONTAINER_INVENTORY_Y;

        return new Point2i(x, y);
    }

    private static Point2i calcPlayerInventoryPoint(ReinforcingMaterial material, boolean isDoubleBlock) {
        int size = getContainerInventorySize(material, isDoubleBlock);
        int cols = getContainerInventoryColumns(size);
        int rows = getContainerInventoryRows(size, cols);

        int x = CONTAINER_INVENTORY_X + (cols - SINGLE_SCREEN_DEFAULT_COLS) * SLOT_SIZE / 2;
        int y = CONTAINER_INVENTORY_Y + rows * SLOT_SIZE + GAP_BETWEEN_CONTAINER_INVENTORY_AND_PLAYER_INVENTORY;

        return new Point2i(x, y);
    }

    public ReinforcingMaterial getMaterial() {
        return this.material;
    }

    public boolean getIsDoubleBlock() {
        return this.isDoubleBlock;
    }

    public Point2i getContainerInventoryPoint() {
        return this.containerInventoryPoint;
    }

    public Point2i getPlayerInventoryPoint() {
        return this.playerInventoryPoint;
    }

    public static int getContainerInventorySize(ReinforcingMaterial material, boolean isDoubleBlock) {
        return isDoubleBlock ? material.getSize() * 2 : material.getSize();
    }

    public static int getContainerInventoryColumns(int size) {
        if (ReinforcedCoreMod.CONFIG.scrollType == ReinforcedStorageScreenType.SCROLL) {
            return SCROLL_SCREEN_COLS;
        }

        return size <= SINGLE_SCREEN_THRESHOLD_SIZE ? SINGLE_SCREEN_DEFAULT_COLS : size / SINGLE_SCREEN_DEFAULT_COLS;
    }

    public static int getContainerInventoryRows(int size, int cols) {
        if (ReinforcedCoreMod.CONFIG.scrollType == ReinforcedStorageScreenType.SCROLL) {
            return SCROLL_SCREEN_ROWS;
        }

        return size / cols;
    }
}
