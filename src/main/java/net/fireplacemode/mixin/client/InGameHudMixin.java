package net.fireplacemode.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.fireplacemode.access.HudAccess;
import net.fabricmc.api.EnvType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin implements HudAccess {

    @Shadow
    @Final
    @Mutable
    private final MinecraftClient client;

    private int saveTicker;

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/gui/DrawContext;)V"))
    private void renderMixin(DrawContext context, float f, CallbackInfo info) {
        // Gets ticked 60 times per second
        PlayerEntity playerEntity = client.player;
        if (!playerEntity.isCreative() && !playerEntity.isSpectator() && saveTicker > 127) {
            int scaledWidth = client.getWindow().getScaledWidth();
            int scaledHeight = client.getWindow().getScaledHeight();
            int color = 16777215 + (saveTicker << 24);
            RenderSystem.enableBlend();
            context.drawTextWithShadow(client.textRenderer, Text.translatable("text.fireplacemode.save"), (int) (scaledWidth * 0.01F), (int) (scaledHeight * 0.95F), color < 0 ? color : -1);
            RenderSystem.disableBlend();
            saveTicker--;
        }
    }

    @Override
    public void startSaving() {
        saveTicker = 360;
    }

    @Shadow
    public TextRenderer getTextRenderer() {
        return this.client.textRenderer;
    }
}