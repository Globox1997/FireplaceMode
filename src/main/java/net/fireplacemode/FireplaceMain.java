package net.fireplacemode;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fireplacemode.config.FireplaceConfig;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class FireplaceMain implements ModInitializer {

    public static final Identifier SAVING_PACKET = new Identifier("fireplacemode", "saving");
    public static final TagKey<Block> SAVE_BLOCK = TagKey.of(RegistryKeys.BLOCK, new Identifier("fireplacemode", "save_block"));
    public static final GameRules.Key<GameRules.BooleanRule> FIREPLACE_MODE = GameRuleRegistry.register("fireplaceMode", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));
    public static FireplaceConfig CONFIG = new FireplaceConfig();

    @Override
    public void onInitialize() {
        AutoConfig.register(FireplaceConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FireplaceConfig.class).getConfig();
    }

}