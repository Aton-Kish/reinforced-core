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
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");
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
        int horizontalDrawNum = this.cols / 9;
        int horizontalDrawFraction = (this.cols % 9) * 18;
        int verticalDrawCount;
        int verticalDrawNum = this.rows / 6;
        int verticalDrawFraction = (this.rows % 6) * 18;
        int offsetX = 0;
        int offsetY = 0;

        // Block Inventory Slots
        offsetY = containerInventoryPoint.getY();

        for (verticalDrawCount = 0; verticalDrawCount < verticalDrawNum; ++verticalDrawCount) {
            offsetX = containerInventoryPoint.getX();
            for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
                this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 7, 17, 9 * 18, 6 * 18);
                offsetX += 9 * 18;
            }
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 7, 17, horizontalDrawFraction, 6 * 18);
            offsetX += horizontalDrawFraction;

            offsetY += 6 * 18;
        }

        offsetX = containerInventoryPoint.getX();
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 7, 17, 9 * 18, verticalDrawFraction);
            offsetX += 9 * 18;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 7, 17, horizontalDrawFraction,
                verticalDrawFraction);
        offsetX += horizontalDrawFraction;

        offsetY += verticalDrawFraction;

        // Player Inventory Slots
        offsetX = playerInventoryPoint.getX();
        offsetY = playerInventoryPoint.getY();
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 7, 139, 9 * 18, 3 * 18 + 4 + 1 * 18);
    }

    private void drawBackgroundTexture(MatrixStack matrices) {
        int horizontalDrawCount;
        int horizontalDrawNum = (this.backgroundWidth - 8) / (176 - 8);
        int horizontalDrawFraction = (this.backgroundWidth - 8) % (176 - 8);
        int verticalDrawCount;
        int verticalDrawNum = (this.backgroundHeight - 8) / (222 - 8);
        int verticalDrawFraction = (this.backgroundHeight - 8) % (222 - 8);
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
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 0, 176 - 8, 4);
            offsetX += 176 - 8;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 0, horizontalDrawFraction, 4);
        offsetX += horizontalDrawFraction;
        // right
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 176 - 4, 0, 4, 4);
        offsetX += 4;

        offsetY += 4;

        //
        // top-bottom
        //

        for (verticalDrawCount = 0; verticalDrawCount < verticalDrawNum; ++verticalDrawCount) {
            offsetX = 0;
            // left
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 4, 4, 222 - 8);
            offsetX += 4;
            offsetX += this.backgroundWidth - 8;
            // right
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 176 - 4, 4, 4, 222 - 8);
            offsetX += 4;

            offsetY += 222 - 8;
        }

        offsetX = 0;
        // left
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 4, 4, verticalDrawFraction);
        offsetX += 4;
        offsetX += this.backgroundWidth - 8;
        // right
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 176 - 4, 4, 4, verticalDrawFraction);
        offsetX += 4;

        offsetY += verticalDrawFraction;

        // center
        offsetY = 4;
        verticalDrawNum = (this.backgroundHeight - 8) / 13;
        verticalDrawFraction = (this.backgroundHeight - 8) % 13;
        for (verticalDrawCount = 0; verticalDrawCount < verticalDrawNum; ++verticalDrawCount) {
            offsetX = 4;
            for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
                this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, 176 - 8, 13);
                offsetX += 176 - 8;
            }
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, horizontalDrawFraction, 13);
            offsetX += horizontalDrawFraction;

            offsetY += 13;
        }

        offsetX = 4;
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, 176 - 8, verticalDrawFraction);
            offsetX += 176 - 8;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 4, horizontalDrawFraction,
                verticalDrawFraction);
        offsetX += horizontalDrawFraction;

        offsetY += verticalDrawFraction;

        //
        // bottom
        //

        offsetX = 0;
        // left
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 0, 222 - 4, 4, 4);
        offsetX += 4;
        // left-right
        for (horizontalDrawCount = 0; horizontalDrawCount < horizontalDrawNum; ++horizontalDrawCount) {
            this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 222 - 4, 176 - 8, 4);
            offsetX += 176 - 8;
        }
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 4, 222 - 4, horizontalDrawFraction, 4);
        offsetX += horizontalDrawFraction;
        // right
        this.drawTexture(matrices, this.x + offsetX, this.y + offsetY, 176 - 4, 222 - 4, 4, 4);
        offsetX += 4;

        offsetY += 4;
    }
}