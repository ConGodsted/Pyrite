package cc.cassian.pyrite.functions.forge;


import net.minecraftforge.fml.ModList;

@SuppressWarnings("unused")
public class ModHelpersImpl {
    public static boolean isModLoaded(String modID) {
        return ModList.get().isLoaded(modID);
    }
}
