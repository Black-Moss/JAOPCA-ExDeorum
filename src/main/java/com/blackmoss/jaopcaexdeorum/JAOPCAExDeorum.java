package com.blackmoss.jaopcaexdeorum;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(JAOPCAExDeorum.MODID)
public class JAOPCAExDeorum {
    public static final String MODID = "jaopcaexdeorum";
    public static final Logger LOGGER = LogUtils.getLogger();

    public JAOPCAExDeorum(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static Identifier identifier(String path)
    {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
