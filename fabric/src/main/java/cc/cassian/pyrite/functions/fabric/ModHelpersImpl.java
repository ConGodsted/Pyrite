package cc.cassian.pyrite.functions.fabric;


import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class ModHelpersImpl {
    public static boolean isModLoaded(String modID) {
        return FabricLoader.getInstance().isModLoaded(modID);
    }
}
