package net.fireplacemode.mixin;

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
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper implements HudAccess {

    @Shadow
    @Final
    @Mutable
    private final MinecraftClient client;

    private int saveTicker;

    public InGameHudMixin(MinecraftClient client) {
        this.client = client;
    }

    @Inject(method = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/util/math/MatrixStack;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void renderMixin(MatrixStack matrixStack, float f, CallbackInfo info) {
        // Gets ticked 60 times per second
        PlayerEntity playerEntity = client.player;
        if (!playerEntity.isCreative() && !playerEntity.isSpectator() && saveTicker > 127) {
            int scaledWidth = client.getWindow().getScaledWidth();
            int scaledHeight = client.getWindow().getScaledHeight();
            int color = 16777215 + (saveTicker << 24);
            RenderSystem.enableBlend();
            this.getTextRenderer().drawWithShadow(matrixStack, Text.translatable("text.fireplacemode.save"), scaledWidth * 0.01F, scaledHeight * 0.95F, color < 0 ? color : -1);
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