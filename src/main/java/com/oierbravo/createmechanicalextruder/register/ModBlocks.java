package com.oierbravo.createmechanicalextruder.register;

import com.oierbravo.createmechanicalextruder.components.extruder.ExtruderBlock;
import com.oierbravo.createmechanicalextruder.components.extruder.ExtruderConfig;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.material.MapColor;

import static com.oierbravo.createmechanicalextruder.CreateMechanicalExtruder.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class ModBlocks {


    static { REGISTRATE.setCreativeTab(ModCreativeTabs.MAIN_TAB); }



    public static void register() {

    }
    public static final BlockEntry<ExtruderBlock> MECHANICAL_EXTRUDER = REGISTRATE.block("mechanical_extruder", ExtruderBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.METAL))
            .transform(pickaxeOnly())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(BlockStressDefaults.setImpact(ExtruderConfig.STRESS_IMPACT.get()))
            .item()
            .transform(customItemModel())
            .register();
}
