package atonkish.reinfcore.util;

import atonkish.reinfcore.util.math.Point2i;

public class ReinforcedStorageScreenModel {
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
        int x = 7;
        int y = 17;
        return new Point2i(x, y);
    }

    private static Point2i calcPlayerInventoryPoint(ReinforcingMaterial material, boolean isDoubleBlock) {
        int size = calcContainerInventorySize(material, isDoubleBlock);
        int cols = calcContainerInventoryColumns(size);
        int rows = size / cols;

        int x = 7 + (cols - 9) * 18 / 2;
        int y = 17 + rows * 18 + 14;
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

    public static int calcContainerInventorySize(ReinforcingMaterial material, boolean isDoubleBlock) {
        return isDoubleBlock ? material.getSize() * 2 : material.getSize();
    }

    public static int calcContainerInventoryColumns(int size) {
        return size <= 81 ? 9 : size / 9;
    }
}
