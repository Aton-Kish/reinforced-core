package atonkish.reinfcore;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import atonkish.reinfcore.util.ReinforcedStorageScreenType;

@Config(name = ReinforcedCoreMod.MOD_ID)
public class ReinforcedCoreConfig implements ConfigData {
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public ReinforcedStorageScreenType scrollType = ReinforcedStorageScreenType.SINGLE;

    @ConfigEntry.Gui.CollapsibleObject
    public ScrollScreen scrollScreen = new ScrollScreen();

    public class ScrollScreen {
        @ConfigEntry.BoundedDiscrete(min = 6, max = 9)
        public int rows = 6;
    }
}
