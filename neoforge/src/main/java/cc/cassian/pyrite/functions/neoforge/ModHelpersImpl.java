package cc.cassian.pyrite.functions.neoforge;

import net.neoforged.fml.ModList;

public class ModHelpersImpl {
    public static boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }
}