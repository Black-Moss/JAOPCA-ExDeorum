package com.blackmoss.jaopcaexdeorum.compat.exdeorum;

import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.JAOPCAModule;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@JAOPCAModule(modDependencies = "exdeorum")
public class ExDeorumModule implements IModule {
    private static final Set<String> BLACKLIST = new TreeSet<>(List.of(
            "amethyst",
            "coal",
            "copper",
            "diamond",
            "emerald",
            "gold",
            "iron",
            "lapis",
            "netherite_scrap",
            "quartz",
            "aluminum",
            "boron",
            "cobalt",
            "iridium",
            "lead",
            "lithium",
            "magnesium",
            "nickel",
            "osmium",
            "platinum",
            "silver",
            "thorium",
            "tin",
            "uranium",
            "zinc"));

    @Override
    public String getName() {
        return "exdeorum";
    }
}
