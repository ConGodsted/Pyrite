package cc.cassian.pyrite.functions.neoforge;

import net.neoforged.fml.ModList;

@SuppressWarnings("unused")
public class ModHelpersImpl {
    public static boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }
}
