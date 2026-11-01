package com.blackmoss.jaopcaexdeorum;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(JAOPCAExDeorum.MODID)
public class JAOPCAExDeorum {
    public static final String MODID = "jaopcaexdeorum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public JAOPCAExDeorum(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static Identifier identifier(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
