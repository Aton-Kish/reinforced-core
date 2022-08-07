package atonkish.reinfcore.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcedStorageScreenModel;
import atonkish.reinfcore.util.ReinforcedStorageScreenModels;
import atonkish.reinfcore.util.math.Point2i;

@Environment(EnvType.CLIENT)
public class ReinforcedStorageScreen extends HandledScreen<ReinforcedStorageScreenHandler> {
    private static final int SLOT_SIZE = 18;
    private static final int TEXT_LINE_HEIGHT = 11;
    private static final int PADDING_TOP = 17;
    private static final int PADDING_BOTTOM = 7;
    private static final int PADDING_LEFT = 7;
    private static final int PADDING_RIGHT = 7;
    private static final int GAP_BETWEEN_CONTAINER_INVENTORY_AND_PLAYER_INVENTORY = 14;
    private static final int GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR = 4;

    private static final int SINGLE_SCREEN_DEFAULT_COLS = 9;

    private static final Identifier BACKGROUND_TEXTURE = new Identifier("textures/gui/demo_background.png");
    private static final int BACKGROUND_CORNER = 4;
    private static final int BACKGROUND_X = 0;
    private static final int BACKGROUND_Y = 0;
    private static final int BACKGROUND_WIDTH = 248;
    private static final int BACKGROUND_HEIGHT = 166;

    private static final Identifier CONTAINER_TEXTURE = new Identifier("textures/gui/container/generic_54.png");
    private static final int CONTAINER_INVENTORY_X = 7;
    private static final int CONTAINER_INVENTORY_Y = 17;
    private static final int CONTAINER_INVENTORY_COLS = 9;
    private static final int CONTAINER_INVENTORY_ROWS = 6;
    private static final int PLAYER_INVENTORY_X = 7;
    private static final int PLAYER_INVENTORY_Y = 139;
    private static final int PLAYER_INVENTORY_WIDTH = 162;
    private static final int PLAYER_INVENTORY_HEIGHT = 76;

    private final ReinforcedStorageScreenModel screenModel;
    private final int cols;
    private final int rows;

    public ReinforcedStorageScreen(ReinforcedStorageScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;

        this.screenModel = handler.getIsDoubleBlock()
                ? ReinforcedStorageScreenModels.DOUBLE_MAP.get(handler.getMaterial())
                : ReinforcedStorageScreenModels.SINGLE_MAP.get(handler.getMaterial());

        this.cols = handler.getColumns();
        this.rows = handler.getRows();

        this.backgroundWidth = PADDING_LEFT + this.cols * SLOT_SIZE + PADDING_RIGHT;
        this.backgroundHeight = PADDING_TOP + this.rows * SLOT_SIZE
                + GAP_BETWEEN_CONTAINER_INVENTORY_AND_PLAYER_INVENTORY + 3 * SLOT_SIZE
                + GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR + 1 * SLOT_SIZE + PADDING_BOTTOM;
        this.titleX = PADDING_LEFT + 1;
        this.titleY = PADDING_TOP - TEXT_LINE_HEIGHT;
        this.playerInventoryTitleX = PADDING_LEFT + (this.cols - SINGLE_SCREEN_DEFAULT_COLS) * SLOT_SIZE / 2 + 1;
        this.playerInventoryTitleY = this.backgroundHeight - (TEXT_LINE_HEIGHT + 3 * SLOT_SIZE
                + GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR + 1 * SLOT_SIZE + PADDING_BOTTOM);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawBackgroundTexture(matrices);
        this.drawSlotTexture(matrices);
    }

    private void drawBackgroundTexture(MatrixStack matrices) {
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

        int hnum = (this.backgroundWidth - BACKGROUND_CORNER * 2)
                / (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2);
        int hrem = (this.backgroundWidth - BACKGROUND_CORNER * 2)
                % (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2);

        int vnum = (this.backgroundHeight - BACKGROUND_CORNER * 2)
                / (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);
        int vrem = (this.backgroundHeight - BACKGROUND_CORNER * 2)
                % (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);

        //
        // corner
        //

        // left-top
        this.drawTexture(matrices,
                this.x,
                this.y,
                BACKGROUND_X,
                BACKGROUND_Y,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER);

        // right-top
        this.drawTexture(matrices,
                this.x + this.backgroundWidth - BACKGROUND_CORNER,
                this.y,
                BACKGROUND_WIDTH - BACKGROUND_CORNER,
                BACKGROUND_Y,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER);

        // right-top
        this.drawTexture(
                matrices,
                this.x,
                this.y + this.backgroundHeight - BACKGROUND_CORNER,
                BACKGROUND_X,
                BACKGROUND_HEIGHT - BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER);

        // right-bottom
        this.drawTexture(matrices,
                this.x + this.backgroundWidth - BACKGROUND_CORNER,
                this.y + this.backgroundHeight - BACKGROUND_CORNER,
                BACKGROUND_WIDTH - BACKGROUND_CORNER,
                BACKGROUND_HEIGHT - BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER);

        //
        // edge
        //

        for (int hcnt = 0; hcnt < hnum; ++hcnt) {
            // top
            this.drawTexture(matrices,
                    this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                    this.y,
                    BACKGROUND_CORNER,
                    BACKGROUND_Y,
                    BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
                    BACKGROUND_CORNER);

            // bottom
            this.drawTexture(matrices,
                    this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                    this.y + this.backgroundHeight - BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    BACKGROUND_HEIGHT - BACKGROUND_CORNER,
                    BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
                    BACKGROUND_CORNER);
        }

        for (int vcnt = 0; vcnt < vnum; ++vcnt) {
            // left
            this.drawTexture(matrices,
                    this.x,
                    this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                    BACKGROUND_X,
                    BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);

            // right
            this.drawTexture(matrices,
                    this.x + this.backgroundWidth - BACKGROUND_CORNER,
                    this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                    BACKGROUND_WIDTH - BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);
        }

        // top
        this.drawTexture(matrices,
                this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                this.y,
                BACKGROUND_CORNER,
                BACKGROUND_Y,
                hrem,
                BACKGROUND_CORNER);

        // bottom
        this.drawTexture(matrices,
                this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                this.y + this.backgroundHeight - BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                BACKGROUND_HEIGHT - BACKGROUND_CORNER,
                hrem,
                BACKGROUND_CORNER);

        // left
        this.drawTexture(matrices,
                this.x,
                this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                BACKGROUND_X,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                vrem);

        // right
        this.drawTexture(matrices,
                this.x + this.backgroundWidth - BACKGROUND_CORNER,
                this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                BACKGROUND_WIDTH - BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                vrem);

        //
        // area
        //

        for (int vcnt = 0; vcnt < vnum; ++vcnt) {
            for (int hcnt = 0; hcnt < hnum; ++hcnt) {
                this.drawTexture(matrices,
                        this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                        this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                        BACKGROUND_CORNER,
                        BACKGROUND_CORNER,
                        BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
                        BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);
            }

            this.drawTexture(matrices,
                    this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                    this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                    BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    hrem,
                    BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);
        }

        for (int hcnt = 0; hcnt < hnum; ++hcnt) {
            this.drawTexture(matrices,
                    this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                    this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                    BACKGROUND_CORNER,
                    BACKGROUND_CORNER,
                    BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
                    vrem);
        }

        this.drawTexture(matrices,
                this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
                this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
                BACKGROUND_CORNER,
                BACKGROUND_CORNER,
                hrem,
                vrem);
    }

    private void drawSlotTexture(MatrixStack matrices) {
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);

        //
        // container inventory
        //

        Point2i containerInventoryPoint = this.screenModel.getContainerInventoryPoint();

        int hnum = this.cols / CONTAINER_INVENTORY_COLS;
        int hrem = (this.cols % CONTAINER_INVENTORY_COLS) * SLOT_SIZE;

        int vnum = this.rows / CONTAINER_INVENTORY_ROWS;
        int vrem = (this.rows % CONTAINER_INVENTORY_ROWS) * SLOT_SIZE;

        for (int vcnt = 0; vcnt < vnum; ++vcnt) {
            for (int hcnt = 0; hcnt < hnum; ++hcnt) {
                this.drawTexture(matrices,
                        this.x + containerInventoryPoint.getX() + hcnt * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                        this.y + containerInventoryPoint.getY() + vcnt * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
                        CONTAINER_INVENTORY_X,
                        CONTAINER_INVENTORY_Y,
                        CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                        CONTAINER_INVENTORY_ROWS * SLOT_SIZE);
            }

            this.drawTexture(matrices,
                    this.x + containerInventoryPoint.getX() + hnum * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                    this.y + containerInventoryPoint.getY() + vcnt * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
                    CONTAINER_INVENTORY_X,
                    CONTAINER_INVENTORY_Y,
                    hrem,
                    CONTAINER_INVENTORY_ROWS * SLOT_SIZE);
        }

        for (int hcnt = 0; hcnt < hnum; ++hcnt) {
            this.drawTexture(matrices,
                    this.x + containerInventoryPoint.getX() + hcnt * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                    this.y + containerInventoryPoint.getY() + vnum * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
                    CONTAINER_INVENTORY_X,
                    CONTAINER_INVENTORY_Y,
                    CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                    vrem);
        }

        this.drawTexture(matrices,
                this.x + containerInventoryPoint.getX() + hnum * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
                this.y + containerInventoryPoint.getY() + vnum * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
                CONTAINER_INVENTORY_X,
                CONTAINER_INVENTORY_Y,
                hrem,
                vrem);

        //
        // player inventory
        //

        Point2i playerInventoryPoint = this.screenModel.getPlayerInventoryPoint();

        this.drawTexture(matrices,
                this.x + playerInventoryPoint.getX(),
                this.y + playerInventoryPoint.getY(),
                PLAYER_INVENTORY_X,
                PLAYER_INVENTORY_Y,
                PLAYER_INVENTORY_WIDTH,
                PLAYER_INVENTORY_HEIGHT);
    }
}
