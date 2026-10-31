package com.blackmoss.jaopcaexdeorum;

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
}
