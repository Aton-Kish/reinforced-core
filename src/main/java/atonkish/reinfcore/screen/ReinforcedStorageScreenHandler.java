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

import atonkish.reinfcore.util.ReinforcedStorageScreenModel;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfcore.util.math.Point2i;

public class ReinforcedStorageScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final ReinforcingMaterial material;
    private final boolean isDoubleBlock;
    private final boolean isShulkerBox;
    private final int cols;
    private final int rows;

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
        this.cols = ReinforcedStorageScreenModel.calcContainerInventoryColumns(size);
        this.rows = size / this.cols;

        this.addSlots(playerInventory);
    }

    public static ReinforcedStorageScreenHandler createSingleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory, Inventory inventory) {
        return new ReinforcedStorageScreenHandler(ModScreenHandlerType.REINFORCED_SINGLE_BLOCK_MAP.get(material),
                material, false, false, syncId, playerInventory, inventory);
    }

    public static ReinforcedStorageScreenHandler createSingleBlockScreen(ReinforcingMaterial material, int syncId,
            PlayerInventory playerInventory) {
        int size = ReinforcedStorageScreenModel.calcContainerInventorySize(material, false);
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
        int size = ReinforcedStorageScreenModel.calcContainerInventorySize(material, true);
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
        int size = ReinforcedStorageScreenModel.calcContainerInventorySize(material, false);
        Inventory inventory = new SimpleInventory(size);
        return createShulkerBoxScreen(material, syncId, playerInventory, inventory);
    }

    private void addSlots(PlayerInventory playerInventory) {
        ReinforcedStorageScreenModel screenModel = ReinforcedStorageScreenModel.getScreenModel(this.material,
                this.isDoubleBlock);
        Point2i containerInventoryPoint = screenModel.getContainerInventoryPoint();
        Point2i playerInventoryPoint = screenModel.getPlayerInventoryPoint();

        int size = this.inventory.size();
        int cols = this.cols;

        int slot;

        if (this.isShulkerBox) {
            for (slot = 0; slot < size; ++slot) {
                int col = slot % cols;
                int row = (slot - col) / cols;
                this.addSlot(new ShulkerBoxSlot(this.inventory, slot, containerInventoryPoint.getX() + col * 18 + 1,
                        containerInventoryPoint.getY() + row * 18 + 1));
            }
        } else {
            for (slot = 0; slot < size; ++slot) {
                int col = slot % cols;
                int row = (slot - col) / cols;
                this.addSlot(new Slot(this.inventory, slot, containerInventoryPoint.getX() + col * 18 + 1,
                        containerInventoryPoint.getY() + row * 18 + 1));
            }
        }

        for (slot = 9; slot < 36; ++slot) {
            int col = (slot - 9) % 9;
            int row = (slot - col - 9) / 9;
            this.addSlot(new Slot(playerInventory, slot, playerInventoryPoint.getX() + col * 18 + 1,
                    playerInventoryPoint.getY() + row * 18 + 1));
        }

        for (slot = 0; slot < 9; ++slot) {
            int col = slot;
            this.addSlot(new Slot(playerInventory, slot, playerInventoryPoint.getX() + col * 18 + 1,
                    playerInventoryPoint.getY() + 3 * 18 + 4 + 1));
        }
    }

    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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
