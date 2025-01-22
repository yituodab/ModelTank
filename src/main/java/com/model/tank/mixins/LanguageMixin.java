package com.model.tank.mixins;

import com.model.tank.resource.LanguageLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.ClientLanguage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
@OnlyIn(Dist.CLIENT)
@Mixin(ClientLanguage.class)
public class LanguageMixin {
    @Inject(method = "getOrDefault(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", at = @At(value = "HEAD"), cancellable = true)
    public void getLanguage(String key, String defaultValue, CallbackInfoReturnable<String> call) {
        String code = Minecraft.getInstance().getLanguageManager().getSelected();
        Map<String, String> languages = LanguageLoader.getLanguages(code);
        Map<String, String> alternative = LanguageLoader.getLanguages("en_us");
        if (languages != null && languages.containsKey(key)) {
            call.setReturnValue(languages.get(key));
        } else if (alternative != null && alternative.containsKey(key)) {
            call.setReturnValue(alternative.get(key));
        }
    }

    @Inject(method = "has(Ljava/lang/String;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void hasLanguage(String key, CallbackInfoReturnable<Boolean> call) {
        String code = Minecraft.getInstance().getLanguageManager().getSelected();
        Map<String, String> languages = LanguageLoader.getLanguages(code);
        Map<String, String> alternative = LanguageLoader.getLanguages("en_us");
        if (languages != null && languages.containsKey(key)) {
            call.setReturnValue(true);
        } else if (alternative != null && alternative.containsKey(key)) {
            call.setReturnValue(true);
        }
    }
}
