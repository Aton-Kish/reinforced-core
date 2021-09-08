package atonkish.reinfcore.util;

import org.jetbrains.annotations.Nullable;

import atonkish.reinfcore.util.math.Point2i;

public enum ReinforcedStorageScreenModel {
    COPPER_SCREEN(ReinforcingMaterial.COPPER, false), COPPER_DOUBLE_SCREEN(ReinforcingMaterial.COPPER, true),
    IRON_SCREEN(ReinforcingMaterial.IRON, false), IRON_DOUBLE_SCREEN(ReinforcingMaterial.IRON, true),
    GOLD_SCREEN(ReinforcingMaterial.GOLD, false), GOLD_DOUBLE_SCREEN(ReinforcingMaterial.GOLD, true),
    DIAMOND_SCREEN(ReinforcingMaterial.DIAMOND, false), DIAMOND_DOUBLE_SCREEN(ReinforcingMaterial.DIAMOND, true),
    NETHERITE_SCREEN(ReinforcingMaterial.NETHERITE, false),
    NETHERITE_DOUBLE_SCREEN(ReinforcingMaterial.NETHERITE, true);

    private final ReinforcingMaterial material;
    private final boolean isDoubleBlock;
    private final Point2i containerInventoryPoint;
    private final Point2i playerInventoryPoint;

    private ReinforcedStorageScreenModel(ReinforcingMaterial material, boolean isDoubleBlock,
            Point2i containerInventoryPoint, Point2i playerInventoryPoint) {
        this.material = material;
        this.isDoubleBlock = isDoubleBlock;
        this.containerInventoryPoint = containerInventoryPoint;
        this.playerInventoryPoint = playerInventoryPoint;
    }

    private ReinforcedStorageScreenModel(ReinforcingMaterial material, boolean isDoubleBook) {
        this(material, isDoubleBook, calcContainerInventoryPoint(material, isDoubleBook),
                calcPlayerInventoryPoint(material, isDoubleBook));
    }

    private static Point2i calcContainerInventoryPoint(ReinforcingMaterial material, boolean isDoubleBook) {
        int x = 7;
        int y = 17;
        return new Point2i(x, y);
    }

    private static Point2i calcPlayerInventoryPoint(ReinforcingMaterial material, boolean isDoubleBook) {
        int size = isDoubleBook ? material.getSize() * 2 : material.getSize();
        int cols = size <= 81 ? 9 : size / 9;
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

    @Nullable
    public static ReinforcedStorageScreenModel getScreenModel(ReinforcingMaterial material, boolean isDoubleBlock) {
        for (ReinforcedStorageScreenModel screenModel : values()) {
            if (screenModel.material.equals(material) && screenModel.isDoubleBlock == isDoubleBlock) {
                return screenModel;
            }
        }

        return (ReinforcedStorageScreenModel) null;
    }
}
