package atonkish.reinfcore.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.ShulkerBoxSlot;
import net.minecraft.screen.slot.Slot;

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.mixin.SlotAccessor;
import atonkish.reinfcore.util.ReinforcedStorageScreenModel;
import atonkish.reinfcore.util.ReinforcedStorageScreenModels;
import atonkish.reinfcore.util.ReinforcedStorageScreenType;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfcore.util.math.Point2i;

public class ReinforcedStorageScreenHandler extends ScreenHandler {
    private static final int SLOT_SIZE = 18;
    private static final int GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR = 4;

    private static final int SCROLL_SCREEN_COLS = 9;
    private static final int SCROLL_SCREEN_ROWS = 5;

    private final Inventory inventory;
    private final ReinforcingMaterial material;
    private final boolean isDoubleBlock;
    private final boolean isShulkerBox;
    private final int cols;
    private final int rows;
    private final ReinforcedStorageScreenModel screenModel;

    public ReinforcedStorageScreenHandler(ScreenHandlerType<?> type, ReinforcingMaterial material,
            boolean isDoubleBlock, boolean isShulkerBox, int syncId, PlayerInventory playerInventory,
            Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
        this.material = material;
        this.isDoubleBlock = isDoubleBlock;
        this.isShulkerBox = isShulkerBox;
        inventory.onOpen(playerInventory.player);

        int size = inventory.size();
        this.cols = ReinforcedStorageScreenModel.getContainerInventoryColumns(size);
        this.rows = ReinforcedStorageScreenModel.getContainerInventoryRows(size, this.cols);

        this.screenModel = this.isDoubleBlock
                ? ReinforcedStorageScreenModels.DOUBLE_MAP.get(this.material)
                : ReinforcedStorageScreenModels.SINGLE_MAP.get(this.material);

        this.addSlots(playerInventory);
    }

    public static ReinforcedStorageScreenHandler createSingleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory, Inventory inventory) {
        return new ReinforcedStorageScreenHandler(ModScreenHandlerType.REINFORCED_SINGLE_BLOCK_MAP.get(material),
                material, false, false, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createSingleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory) {
        int size = ReinforcedStorageScreenModel.getContainerInventorySize(material, false);
        Inventory inventory = new SimpleInventory(size);
        return createSingleBlockScreen(material, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createDoubleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory, Inventory inventory) {
        return new ReinforcedStorageScreenHandler(ModScreenHandlerType.REINFORCED_DOUBLE_BLOCK_MAP.get(material),
                material, true, false, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createDoubleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory) {
        int size = ReinforcedStorageScreenModel.getContainerInventorySize(material, true);
        Inventory inventory = new SimpleInventory(size);
        return createDoubleBlockScreen(material, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createShulkerBoxScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory, Inventory inventory) {
        return new ReinforcedStorageScreenHandler(ModScreenHandlerType.REINFORCED_SHULKER_BOX_MAP.get(material),
                material, false, true, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createShulkerBoxScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory) {
        int size = ReinforcedStorageScreenModel.getContainerInventorySize(material, false);
        Inventory inventory = new SimpleInventory(size);
        return createShulkerBoxScreen(material, syncId, playerInventory, inventory);
    }

    private void addSlots(PlayerInventory playerInventory) {
        Point2i containerInventoryPoint = this.screenModel.getContainerInventoryPoint();
        Point2i playerInventoryPoint = this.screenModel.getPlayerInventoryPoint();

        for (int index = 0; index < this.inventory.size(); ++index) {
            int col = index % this.cols;
            int row = (index - col) / this.cols;

            int x = containerInventoryPoint.getX() + col * SLOT_SIZE + 1;
            int y = containerInventoryPoint.getY() + row * SLOT_SIZE + 1;

            if (ReinforcedCoreMod.CONFIG.scrollType == ReinforcedStorageScreenType.SCROLL
                    && row >= SCROLL_SCREEN_ROWS) {
                // HACK: slot position far away outside if scroll screen type
                x = Integer.MIN_VALUE;
                y = Integer.MIN_VALUE;
            }

            Slot slot = this.isShulkerBox
                    ? new ShulkerBoxSlot(this.inventory, index, x, y)
                    : new Slot(this.inventory, index, x, y);
            this.addSlot(slot);
        }

        for (int index = 9; index < 36; ++index) {
            int col = (index - 9) % 9;
            int row = (index - col - 9) / 9;

            int x = playerInventoryPoint.getX() + col * SLOT_SIZE + 1;
            int y = playerInventoryPoint.getY() + row * SLOT_SIZE + 1;

            Slot slot = new Slot(playerInventory, index, x, y);
            this.addSlot(slot);
        }

        for (int index = 0; index < 9; ++index) {
            int col = index;

            int x = playerInventoryPoint.getX() + col * SLOT_SIZE + 1;
            int y = playerInventoryPoint.getY() + 3 * SLOT_SIZE
                    + GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR + 1;

            Slot slot = new Slot(playerInventory, index, x, y);
            this.addSlot(slot);
        }
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public void scrollItems(float position) {
        if (ReinforcedCoreMod.CONFIG.scrollType != ReinforcedStorageScreenType.SCROLL) {
            return;
        }

        int i = (this.inventory.size() + SCROLL_SCREEN_COLS - 1) / SCROLL_SCREEN_COLS - SCROLL_SCREEN_ROWS;
        int srow = (int) ((double) (position * (float) i) + 0.5);
        if (srow < 0) {
            srow = 0;
        }

        Point2i containerInventoryPoint = this.screenModel.getContainerInventoryPoint();

        for (int index = 0; index < this.inventory.size(); ++index) {
            int col = index % this.cols;
            int row = (index - col) / this.cols;

            int x = containerInventoryPoint.getX() + col * SLOT_SIZE + 1;
            int y = containerInventoryPoint.getY() + (row - srow) * SLOT_SIZE + 1;

            if (row < srow || row >= srow + SCROLL_SCREEN_ROWS) {
                // HACK: slot position far away outside if scroll screen type
                x = Integer.MIN_VALUE;
                y = Integer.MIN_VALUE;
            }

            Slot slot = this.getSlot(index);
            ((SlotAccessor) slot).setX(x);
            ((SlotAccessor) slot).setY(y);
        }
    }

    public boolean shouldShowScrollbar() {
        return ReinforcedCoreMod.CONFIG.scrollType == ReinforcedStorageScreenType.SCROLL
                && this.inventory.size() > SCROLL_SCREEN_COLS * SCROLL_SCREEN_ROWS;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot) this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index < this.inventory.size()) {
                if (!this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return itemStack;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public ReinforcingMaterial getMaterial() {
        return this.material;
    }

    public boolean getIsDoubleBlock() {
        return this.isDoubleBlock;
    }

    public boolean getIsShulkerBox() {
        return this.isShulkerBox;
    }

    public int getColumns() {
        return this.cols;
    }

    public int getRows() {
        return this.rows;
    }
}
