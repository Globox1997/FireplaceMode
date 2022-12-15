package net.fireplacemode.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "fireplacemode")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class FireplaceConfig implements ConfigData {
    public boolean hudSave = false;
    public int campfireRange = 3;

}