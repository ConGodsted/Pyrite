package cc.cassian.pyrite.fabric;

import cc.cassian.pyrite.Pyrite;
import net.fabricmc.api.ModInitializer;
import static cc.cassian.pyrite.functions.fabric.FabricHelpers.registerFuelBlocks;

public class PyriteFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Pyrite.init();
        registerFuelBlocks();
    }
}