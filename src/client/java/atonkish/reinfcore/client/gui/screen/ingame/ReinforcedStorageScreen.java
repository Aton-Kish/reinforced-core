package atonkish.reinfcore.client.gui.screen.ingame;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import atonkish.reinfcore.ReinforcedCoreMod;
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
  private static final int GAP_BETWEEN_CONTAINER_INVENTORY_AND_SCROLL_BAR = 4;

  private static final int SINGLE_SCREEN_DEFAULT_COLS = 9;
  private static final int SCROLL_SCREEN_COLS = 9;

  private static final Identifier BACKGROUND_TEXTURE =
      Identifier.ofVanilla("textures/gui/demo_background.png");
  private static final int BACKGROUND_CORNER = 4;
  private static final int BACKGROUND_X = 0;
  private static final int BACKGROUND_Y = 0;
  private static final int BACKGROUND_WIDTH = 248;
  private static final int BACKGROUND_HEIGHT = 166;

  private static final Identifier CONTAINER_TEXTURE =
      Identifier.ofVanilla("textures/gui/container/generic_54.png");
  private static final int CONTAINER_INVENTORY_X = 7;
  private static final int CONTAINER_INVENTORY_Y = 17;
  private static final int CONTAINER_INVENTORY_COLS = 9;
  private static final int CONTAINER_INVENTORY_ROWS = 6;
  private static final int PLAYER_INVENTORY_X = 7;
  private static final int PLAYER_INVENTORY_Y = 139;
  private static final int PLAYER_INVENTORY_WIDTH = 162;
  private static final int PLAYER_INVENTORY_HEIGHT = 76;

  private static final Identifier SCROLLBAR_BACKGROUND_TEXTURE =
      Identifier.ofVanilla("textures/gui/container/creative_inventory/tab_items.png");
  private static final int SCROLLBAR_BACKGROUND_X = 174;
  private static final int SCROLLBAR_BACKGROUND_Y = 17;
  private static final int SCROLLBAR_BACKGROUND_WIDTH = 14;
  private static final int SCROLLBAR_BACKGROUND_HEIGHT = 112;

  private static final Identifier SCROLLER_TEXTURE =
      Identifier.ofVanilla("container/creative_inventory/scroller");
  private static final Identifier SCROLLER_DISABLED_TEXTURE =
      Identifier.ofVanilla("container/creative_inventory/scroller_disabled");
  private static final int SCROLLER_WIDTH = 12;
  private static final int SCROLLER_HEIGHT = 15;

  private final ReinforcedStorageScreenModel screenModel;
  private final int cols;
  private final int rows;

  private float scrollPosition;
  private boolean scrolling;

  public ReinforcedStorageScreen(
      ReinforcedStorageScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);

    this.scrollPosition = 0.0f;
    this.scrolling = false;

    this.screenModel =
        handler.getIsDoubleBlock()
            ? ReinforcedStorageScreenModels.DOUBLE_MAP.get(handler.getMaterial())
            : ReinforcedStorageScreenModels.SINGLE_MAP.get(handler.getMaterial());

    this.cols = handler.getColumns();
    this.rows = handler.getRows();

    this.backgroundWidth = PADDING_LEFT + this.cols * SLOT_SIZE + PADDING_RIGHT;
    if (this.hasScrollbar()) {
      this.backgroundWidth +=
          GAP_BETWEEN_CONTAINER_INVENTORY_AND_SCROLL_BAR + SCROLLBAR_BACKGROUND_WIDTH;
    }
    this.backgroundHeight =
        PADDING_TOP
            + this.rows * SLOT_SIZE
            + GAP_BETWEEN_CONTAINER_INVENTORY_AND_PLAYER_INVENTORY
            + 3 * SLOT_SIZE
            + GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR
            + 1 * SLOT_SIZE
            + PADDING_BOTTOM;
    this.titleX = PADDING_LEFT + 1;
    this.titleY = PADDING_TOP - TEXT_LINE_HEIGHT;
    this.playerInventoryTitleX =
        PADDING_LEFT + (this.cols - SINGLE_SCREEN_DEFAULT_COLS) * SLOT_SIZE / 2 + 1;
    this.playerInventoryTitleY =
        this.backgroundHeight
            - (TEXT_LINE_HEIGHT
                + 3 * SLOT_SIZE
                + GAP_BETWEEN_PLAYER_INVENTORY_STORAGE_AND_PLAYER_INVENTORY_HOTBAR
                + 1 * SLOT_SIZE
                + PADDING_BOTTOM);
  }

  @Override
  public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    super.render(context, mouseX, mouseY, delta);
    this.drawMouseoverTooltip(context, mouseX, mouseY);
  }

  @Override
  protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    this.drawBackgroundTexture(context);
    this.drawSlotTexture(context);
    if (this.hasScrollbar()) {
      this.drawScrollbarTexture(context);
    }
  }

  private void drawBackgroundTexture(DrawContext context) {
    // CHECKSTYLE.SUPPRESS: VariableDeclarationUsageDistance
    int hnum =
        (this.backgroundWidth - BACKGROUND_CORNER * 2) / (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2);
    // CHECKSTYLE.SUPPRESS: VariableDeclarationUsageDistance
    int hrem =
        (this.backgroundWidth - BACKGROUND_CORNER * 2) % (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2);

    // CHECKSTYLE.SUPPRESS: VariableDeclarationUsageDistance
    int vnum =
        (this.backgroundHeight - BACKGROUND_CORNER * 2)
            / (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);
    // CHECKSTYLE.SUPPRESS: VariableDeclarationUsageDistance
    int vrem =
        (this.backgroundHeight - BACKGROUND_CORNER * 2)
            % (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2);

    //
    // corner
    //

    // left-top
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x,
        this.y,
        BACKGROUND_X,
        BACKGROUND_Y,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        256,
        256);

    // right-top
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - BACKGROUND_CORNER,
        this.y,
        BACKGROUND_WIDTH - BACKGROUND_CORNER,
        BACKGROUND_Y,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        256,
        256);

    // right-top
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x,
        this.y + this.backgroundHeight - BACKGROUND_CORNER,
        BACKGROUND_X,
        BACKGROUND_HEIGHT - BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        256,
        256);

    // right-bottom
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - BACKGROUND_CORNER,
        this.y + this.backgroundHeight - BACKGROUND_CORNER,
        BACKGROUND_WIDTH - BACKGROUND_CORNER,
        BACKGROUND_HEIGHT - BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        256,
        256);

    //
    // edge
    //

    for (int hcnt = 0; hcnt < hnum; ++hcnt) {
      // top
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
          this.y,
          BACKGROUND_CORNER,
          BACKGROUND_Y,
          BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
          BACKGROUND_CORNER,
          256,
          256);

      // bottom
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
          this.y + this.backgroundHeight - BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          BACKGROUND_HEIGHT - BACKGROUND_CORNER,
          BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
          BACKGROUND_CORNER,
          256,
          256);
    }

    for (int vcnt = 0; vcnt < vnum; ++vcnt) {
      // left
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x,
          this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
          BACKGROUND_X,
          BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2,
          256,
          256);

      // right
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x + this.backgroundWidth - BACKGROUND_CORNER,
          this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
          BACKGROUND_WIDTH - BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2,
          256,
          256);
    }

    // top
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
        this.y,
        BACKGROUND_CORNER,
        BACKGROUND_Y,
        hrem,
        BACKGROUND_CORNER,
        256,
        256);

    // bottom
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
        this.y + this.backgroundHeight - BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        BACKGROUND_HEIGHT - BACKGROUND_CORNER,
        hrem,
        BACKGROUND_CORNER,
        256,
        256);

    // left
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x,
        this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
        BACKGROUND_X,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        vrem,
        256,
        256);

    // right
    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - BACKGROUND_CORNER,
        this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
        BACKGROUND_WIDTH - BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        vrem,
        256,
        256);

    //
    // area
    //

    for (int vcnt = 0; vcnt < vnum; ++vcnt) {
      for (int hcnt = 0; hcnt < hnum; ++hcnt) {
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            BACKGROUND_TEXTURE,
            this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
            this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
            BACKGROUND_CORNER,
            BACKGROUND_CORNER,
            BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
            BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2,
            256,
            256);
      }

      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
          this.y + BACKGROUND_CORNER + vcnt * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
          BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          hrem,
          BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2,
          256,
          256);
    }

    for (int hcnt = 0; hcnt < hnum; ++hcnt) {
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          BACKGROUND_TEXTURE,
          this.x + BACKGROUND_CORNER + hcnt * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
          this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
          BACKGROUND_CORNER,
          BACKGROUND_CORNER,
          BACKGROUND_WIDTH - BACKGROUND_CORNER * 2,
          vrem,
          256,
          256);
    }

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        BACKGROUND_TEXTURE,
        this.x + BACKGROUND_CORNER + hnum * (BACKGROUND_WIDTH - BACKGROUND_CORNER * 2),
        this.y + BACKGROUND_CORNER + vnum * (BACKGROUND_HEIGHT - BACKGROUND_CORNER * 2),
        BACKGROUND_CORNER,
        BACKGROUND_CORNER,
        hrem,
        vrem,
        256,
        256);
  }

  private void drawSlotTexture(DrawContext context) {
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
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            CONTAINER_TEXTURE,
            this.x + containerInventoryPoint.getX() + hcnt * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
            this.y + containerInventoryPoint.getY() + vcnt * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
            CONTAINER_INVENTORY_X,
            CONTAINER_INVENTORY_Y,
            CONTAINER_INVENTORY_COLS * SLOT_SIZE,
            CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
            256,
            256);
      }

      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          CONTAINER_TEXTURE,
          this.x + containerInventoryPoint.getX() + hnum * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
          this.y + containerInventoryPoint.getY() + vcnt * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
          CONTAINER_INVENTORY_X,
          CONTAINER_INVENTORY_Y,
          hrem,
          CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
          256,
          256);
    }

    for (int hcnt = 0; hcnt < hnum; ++hcnt) {
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          CONTAINER_TEXTURE,
          this.x + containerInventoryPoint.getX() + hcnt * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
          this.y + containerInventoryPoint.getY() + vnum * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
          CONTAINER_INVENTORY_X,
          CONTAINER_INVENTORY_Y,
          CONTAINER_INVENTORY_COLS * SLOT_SIZE,
          vrem,
          256,
          256);
    }

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        CONTAINER_TEXTURE,
        this.x + containerInventoryPoint.getX() + hnum * CONTAINER_INVENTORY_COLS * SLOT_SIZE,
        this.y + containerInventoryPoint.getY() + vnum * CONTAINER_INVENTORY_ROWS * SLOT_SIZE,
        CONTAINER_INVENTORY_X,
        CONTAINER_INVENTORY_Y,
        hrem,
        vrem,
        256,
        256);

    //
    // player inventory
    //

    Point2i playerInventoryPoint = this.screenModel.getPlayerInventoryPoint();

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        CONTAINER_TEXTURE,
        this.x + playerInventoryPoint.getX(),
        this.y + playerInventoryPoint.getY(),
        PLAYER_INVENTORY_X,
        PLAYER_INVENTORY_Y,
        PLAYER_INVENTORY_WIDTH,
        PLAYER_INVENTORY_HEIGHT,
        256,
        256);
  }

  private void drawScrollbarTexture(DrawContext context) {
    //
    // backgraound
    //

    int vnum = (this.rows * SLOT_SIZE - 2) / (SCROLLBAR_BACKGROUND_HEIGHT - 2);
    int vrem = (this.rows * SLOT_SIZE - 2) % (SCROLLBAR_BACKGROUND_HEIGHT - 2);

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        SCROLLBAR_BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - (SCROLLBAR_BACKGROUND_WIDTH + PADDING_RIGHT),
        this.y + PADDING_TOP,
        SCROLLBAR_BACKGROUND_X,
        SCROLLBAR_BACKGROUND_Y,
        SCROLLBAR_BACKGROUND_WIDTH,
        1,
        256,
        256);

    for (int vcnt = 0; vcnt < vnum; ++vcnt) {
      context.drawTexture(
          RenderPipelines.GUI_TEXTURED,
          SCROLLBAR_BACKGROUND_TEXTURE,
          this.x + this.backgroundWidth - (SCROLLBAR_BACKGROUND_WIDTH + PADDING_RIGHT),
          this.y + PADDING_TOP + 1 + vcnt * (SCROLLBAR_BACKGROUND_HEIGHT - 2),
          SCROLLBAR_BACKGROUND_X,
          SCROLLBAR_BACKGROUND_Y + 1,
          SCROLLBAR_BACKGROUND_WIDTH,
          SCROLLBAR_BACKGROUND_HEIGHT - 2,
          256,
          256);
    }

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        SCROLLBAR_BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - (SCROLLBAR_BACKGROUND_WIDTH + PADDING_RIGHT),
        this.y + PADDING_TOP + 1 + vnum * (SCROLLBAR_BACKGROUND_HEIGHT - 2),
        SCROLLBAR_BACKGROUND_X,
        SCROLLBAR_BACKGROUND_Y + 1,
        SCROLLBAR_BACKGROUND_WIDTH,
        vrem,
        256,
        256);

    context.drawTexture(
        RenderPipelines.GUI_TEXTURED,
        SCROLLBAR_BACKGROUND_TEXTURE,
        this.x + this.backgroundWidth - (SCROLLBAR_BACKGROUND_WIDTH + PADDING_RIGHT),
        this.y + PADDING_TOP + this.rows * SLOT_SIZE - 1,
        SCROLLBAR_BACKGROUND_X,
        SCROLLBAR_BACKGROUND_Y + SCROLLBAR_BACKGROUND_HEIGHT - 1,
        SCROLLBAR_BACKGROUND_WIDTH,
        1,
        256,
        256);

    //
    // button
    //

    int ymin = this.y + PADDING_TOP + 1;
    int ymax = ymin + this.rows * SLOT_SIZE;

    Identifier identifier = this.hasScrollbar() ? SCROLLER_TEXTURE : SCROLLER_DISABLED_TEXTURE;
    context.drawGuiTexture(
        RenderPipelines.GUI_TEXTURED,
        identifier,
        this.x
            + PADDING_LEFT
            + this.cols * SLOT_SIZE
            + GAP_BETWEEN_CONTAINER_INVENTORY_AND_SCROLL_BAR
            + 1,
        ymin + (int) ((float) (ymax - ymin - (SCROLLER_HEIGHT + 2)) * this.scrollPosition),
        SCROLLER_WIDTH,
        SCROLLER_HEIGHT);
  }

  @Override
  public boolean mouseClicked(Click click, boolean doubled) {
    if (this.hasScrollbar() && click.button() == 0) {
      if (this.isClickInScrollbar(click.x(), click.y())) {
        this.scrolling = this.hasScrollbar();
        return true;
      }
    }

    return super.mouseClicked(click, doubled);
  }

  @Override
  public boolean mouseReleased(Click click) {
    if (this.hasScrollbar() && click.button() == 0) {
      this.scrolling = false;
    }

    return super.mouseReleased(click);
  }

  private boolean hasScrollbar() {
    return this.handler.shouldShowScrollbar();
  }

  @Override
  public boolean mouseScrolled(
      double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    if (!this.hasScrollbar()) {
      return false;
    }

    int i =
        MathHelper.ceilDiv(this.handler.getInventory().size(), SCROLL_SCREEN_COLS)
            - ReinforcedCoreMod.CONFIG.scrollScreen.rows;
    float f = (float) (verticalAmount / (double) i);
    this.scrollPosition = MathHelper.clamp(this.scrollPosition - f, 0.0f, 1.0f);
    this.handler.scrollItems(this.scrollPosition);
    return true;
  }

  protected boolean isClickInScrollbar(double mouseX, double mouseY) {
    int i =
        this.x
            + PADDING_LEFT
            + this.cols * SLOT_SIZE
            + GAP_BETWEEN_CONTAINER_INVENTORY_AND_SCROLL_BAR
            + 1;
    int j = this.y + PADDING_TOP + 1;
    int k = i + SCROLLER_WIDTH + 1;
    int l = j + this.rows * SLOT_SIZE;
    return mouseX >= (double) i
        && mouseY >= (double) j
        && mouseX < (double) k
        && mouseY < (double) l;
  }

  @Override
  public boolean mouseDragged(Click click, double offsetX, double offsetY) {
    if (this.hasScrollbar() && this.scrolling) {
      int i = this.y + PADDING_TOP + 1;
      int j = i + this.rows * SLOT_SIZE;
      this.scrollPosition =
          ((float) click.y() - (float) i - (float) SCROLLER_HEIGHT / 2.0f)
              / ((float) (j - i) - (float) SCROLLER_HEIGHT);
      this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0f, 1.0f);
      this.handler.scrollItems(this.scrollPosition);
      return true;
    }

    return super.mouseDragged(click, offsetX, offsetY);
  }
}
