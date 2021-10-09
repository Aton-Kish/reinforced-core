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

import atonkish.reinfcore.ReinforcedCoreMod;
import atonkish.reinfcore.screen.ReinforcedStorageScreenHandler;
import atonkish.reinfcore.util.ReinforcedStorageScreenModel;
import atonkish.reinfcore.util.math.Point2i;

@Environment(EnvType.CLIENT)
public class ReinforcedStorageScreen extends HandledScreen<ReinforcedStorageScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(ReinforcedCoreMod.MOD_ID,
            "textures/gui/container/generic.png");
    private final ReinforcedStorageScreenModel screenModel;
    private final int cols;
    private final int rows;

    public ReinforcedStorageScreen(ReinforcedStorageScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;

        this.screenModel = ReinforcedStorageScreenModel.getScreenModel(handler.getMaterial(),
                handler.getIsDoubleBlock());

        this.cols = handler.getColumns();
        this.rows = handler.getRows();

        this.backgroundWidth = 7 + (this.cols * 18) + 7;
        this.backgroundHeight = 17 + (this.rows * 18) + 14 + (3 * 18) + 4 + (1 * 18) + 7;
        this.titleX = 8;
        this.titleY = 6;
        this.playerInventoryTitleX = 8 + (this.cols - 9) * 18 / 2;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        this.drawBackgroundTexture(matrices);
        this.drawSlotTexture(matrices);
    }

    private void drawSlotTexture(MatrixStack matrices) {
        Point2i containerInventoryPoint = this.screenModel.getContainerInventoryPoint();
        Point2i playerInventoryPoint = this.screenModel.getPlayerInventoryPoint();
        int horizontalDrawCount;
        int horizontalDrawNum = this.cols / 14;
        int horizontalDrawFraction = (this.cols % 14) * 18;
        int verticalDrawCount;
        int verticalDrawNum = this.rows / 3;
        int verticalDrawFraction = (this.rows % 3) * 18;
        int offsetX = 0;
        int offsetY = 0;

        // Block Inventory Slots
        offsetY = containerInventoryPoint.getY();

        for (verticalDrawCount = 0; verticalDrawCount < verticalDrawNum; ++verticalDrawCount) {
            offsetX = containerInventoryPoint.getX();
            for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
                this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, 14 * 18, 3 * 18);
                offsetX += 14 * 18;
            }
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, horizontalDrawFraction, 3 * 18);
            offsetX += horizontalDrawFraction;

            offsetY += 3 * 18;
        }

        offsetX = containerInventoryPoint.getX();
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, 14 * 18, verticalDrawFraction);
            offsetX += 14 * 18;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, horizontalDrawFraction,
                verticalDrawFraction);
        offsetX += horizontalDrawFraction;

        offsetY += verticalDrawFraction;

        // Player Inventory Slots
        offsetX = playerInventoryPoint.getX();
        offsetY = playerInventoryPoint.getY();
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, 9 * 18, 3 * 18);
        offsetY += 3 * 18 + 4;
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 198, 9 * 18, 1 * 18);
    }

    private void drawBackgroundTexture(MatrixStack matrices) {
        int horizontalDrawCount;
        int horizontalDrawNum = (this.backgroundWidth - 8) / 244;
        int horizontalDrawFraction = (this.backgroundWidth - 8) % 244;
        int verticalDrawCount;
        int verticalDrawNum = (this.backgroundHeight - 8) / 190;
        int verticalDrawFraction = (this.backgroundHeight - 8) % 190;
        int offsetX = 0;
        int offsetY = 0;

        //
        // top
        //

        offsetX = 0;
        // left
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 0, 4, 4);
        offsetX += 4;
        // left-right
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 0, 244, 4);
            offsetX += 244;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 0, horizontalDrawFraction, 4);
        offsetX += horizontalDrawFraction;
        // right
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 248, 0, 4, 4);
        offsetX += 4;

        offsetY += 4;

        //
        // top-bottom
        //

        for (verticalDrawCount = 0; verticalDrawCount < verticalDrawNum; ++verticalDrawCount) {
            offsetX = 0;
            // left
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 4, 4, 190);
            offsetX += 4;
            // left-right
            for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
                this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, 244, 190);
                offsetX += 244;
            }
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, horizontalDrawFraction, 190);
            offsetX += horizontalDrawFraction;
            // right
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 248, 4, 4, 190);
            offsetX += 4;

            offsetY += 190;
        }

        offsetX = 0;
        // left
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 4, 4, verticalDrawFraction);
        offsetX += 4;
        // left-right
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, 244, verticalDrawFraction);
            offsetX += 244;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, horizontalDrawFraction,
                verticalDrawFraction);
        offsetX += horizontalDrawFraction;
        // right

        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 248, 4, 4, verticalDrawFraction);
        offsetX += 4;

        offsetY += verticalDrawFraction;

        //
        // bottom
        //

        offsetX = 0;
        // left
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 194, 4, 4);
        offsetX += 4;
        // left-right
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 194, 244, 4);
            offsetX += 244;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 194, horizontalDrawFraction, 4);
        offsetX += horizontalDrawFraction;
        // right
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 248, 194, 4, 4);
        offsetX += 4;

        offsetY += 4;
    }
}