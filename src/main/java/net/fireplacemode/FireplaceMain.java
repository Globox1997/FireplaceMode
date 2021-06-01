package net.fireplacemode;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class FireplaceMain implements ModInitializer {

    public static final Tag<Block> SAVE_BLOCK = TagRegistry.block(new Identifier("fireplacemode", "save_block"));
    public static final GameRules.Key<GameRules.BooleanRule> FIREPLACE_MODE = GameRuleRegistry.register("fireplaceMode",
            GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

    @Override
    public void onInitialize() {

    }

}