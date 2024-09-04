package com.oierbravo.createmechanicalextruder;

import com.oierbravo.createmechanicalextruder.infrastructure.data.ModDataGen;
import com.oierbravo.createmechanicalextruder.register.*;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("create_mechanical_extruder")
public class CreateMechanicalExtruder
{
    public static final String MODID = "create_mechanical_extruder";
    public static final String DISPLAY_NAME = "Create Mechanical Extruder";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    public static IEventBus modEventBus;

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> {
            return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                    .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
        });
    }
    public CreateMechanicalExtruder()
    {
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        ModConfigs.register();

        ModBlocks.register();
        ModBlockEntities.register();
        ModCreativeTabs.register(modEventBus);

        ModRecipes.register(modEventBus);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(ModDataGen::gatherData);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> ModPartials::load);
        generateLangEntries();
    }
    private void generateLangEntries(){
        registrate().addRawLang("create_mechanical_extruder.recipe.extruding", "Extruding recipe");
        registrate().addRawLang("itemGroup.create_mechanical_extruder:main", DISPLAY_NAME);

        registrate().addRawLang("create_mechanical_extruder.goggles.bonks", "%1$s bonks");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.header", "Block generation");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.text_1", "The Extruder uses rotational force to generate blocks");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.text_2", "Generation depends on side & below blocks.");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.text_3", "When the process is done, the result can be obtained via Right-click");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.text_4", "The outputs can also be extracted by automation");
        registrate().addRawLang("create_mechanical_extruder.ponder.extruder.text_5", "When a conbination of ingredient has multiple possible outcomes, the filter slot can specify it");

        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.ingredients", "Missing ingredients");
        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.output", "Output full or incompatible");
        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.biome", "Incorrect biome");
        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.min_height", "Too low");
        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.max_height", "Too high");
        registrate().addRawLang("create_mechanical_extruder.goggles.recipe_requirement.min_speed", "Not enough speed");

        registrate().addRawLang("create_mechanical_extruder.ui.recipe_requirement.min_height", "Min Y: %s");
        registrate().addRawLang("create_mechanical_extruder.ui.recipe_requirement.max_height", "Max Y: %s");
        registrate().addRawLang("create_mechanical_extruder.ui.recipe_requirement.min_speed", "Min Speed: %s");


    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        event.enqueueWork(ModPonders::register);
    }
    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }



    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
    public static Logger logger(){
        return LOGGER;
    }
}
