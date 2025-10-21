package brimmArmors.common.items;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import brimmArmors.BrimmArmors;
import brimmArmors.common.configurations.ConfigMergers;
import brimmArmors.common.configurations.ConfigsManager;
import ema_08_.geom.models.MatrixRTSBuilder;
import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.geom.models.RTSMatricesCompoundBuilder;
import ema_08_.items.SimpleArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BrimmArmors.MOD_ID);
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
    		Registries.CREATIVE_MODE_TAB, BrimmArmors.MOD_ID);
    
    private static final ArrayList<RegistryObject<BasicArmor>> armors_tab_content = new ArrayList<>();

    public static RegistryObject<Item> getr(String id) {
        return ITEMS.getEntries().stream()
                .filter(entry -> entry.getId().getPath().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No armor found with id: " + id));
    }
    public static Item get(String id) {
        return ITEMS.getEntries().stream()
                .filter(entry -> entry.getId().getPath().equals(id))
                .map(RegistryObject::get)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No armor found with id: " + id));
    }

    public static final RegistryObject<BasicPlate> IRON_PLATE = ITEMS.register("iron_plate", () -> new BasicPlate(ConcordRarity.COMMON));
    public static final RegistryObject<BasicPlate> DIAMOND_PLATE = ITEMS.register("diamond_plate", () -> new BasicPlate(ConcordRarity.RARE));
    public static final RegistryObject<BasicPlate> NETHER_PLATE = ITEMS.register("nether_plate", () -> new BasicPlate(ConcordRarity.EPIC));
    
    public static final RegistryObject<BasicArmor> NATO = registerItemAndExecute(armors_tab_content::add,
    	    "nato",
    	    generateArmorSupplier(
    	        "nato",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NATO_II = registerItemAndExecute(armors_tab_content::add,
    	    "nato_ii",
    	    generateArmorSupplier(
    	        "nato_ii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MARINE = registerItemAndExecute(armors_tab_content::add,
    	    "marine",
    	    generateArmorSupplier(
    	        "marine",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VANDERER = registerItemAndExecute(armors_tab_content::add,
    	    "vanderer",
    	    generateArmorSupplier(
    	        "vanderer",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GUARD = registerItemAndExecute(armors_tab_content::add,
    	    "guard",
    	    generateArmorSupplier(
    	        "guard",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER = registerItemAndExecute(armors_tab_content::add,
    	    "saper",
    	    generateArmorSupplier(
    	        "saper",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER = registerItemAndExecute(armors_tab_content::add,
    	    "defender",
    	    generateArmorSupplier(
    	        "defender",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_II = registerItemAndExecute(armors_tab_content::add,
    	    "defender_ii",
    	    generateArmorSupplier(
    	        "defender_ii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_III = registerItemAndExecute(armors_tab_content::add,
    	    "defender_iii",
    	    generateArmorSupplier(
    	        "defender_iii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD = registerItemAndExecute(armors_tab_content::add,
    	    "concord",
    	    generateArmorSupplier(
    	        "concord",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC = registerItemAndExecute(armors_tab_content::add,
    	    "medic",
    	    generateArmorSupplier(
    	        "medic",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> PMC = registerItemAndExecute(armors_tab_content::add,
    	    "pmc",
    	    generateArmorSupplier(
    	        "pmc",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT = registerItemAndExecute(armors_tab_content::add,
    	    "assault",
    	    generateArmorSupplier(
    	        "assault",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SPN = registerItemAndExecute(armors_tab_content::add,
    	    "spn",
    	    generateArmorSupplier(
    	        "spn",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> HORSE = registerItemAndExecute(armors_tab_content::add,
    	    "horse",
    	    generateArmorSupplier(
    	        "horse",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ATLETI = registerItemAndExecute(armors_tab_content::add,
    	    "atleti",
    	    generateArmorSupplier(
    	        "atleti",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK = registerItemAndExecute(armors_tab_content::add,
    	    "ratnik",
    	    generateArmorSupplier(
    	        "ratnik",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK_ADVANCE = registerItemAndExecute(armors_tab_content::add,
    	    "ratnik_advance",
    	    generateArmorSupplier(
    	        "ratnik_advance",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VETERAN = registerItemAndExecute(armors_tab_content::add,
    	    "veteran",
    	    generateArmorSupplier(
    	        "veteran",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NYYYAAAA = registerItemAndExecute(armors_tab_content::add,
    	    "nyyyaaaa",
    	    generateArmorSupplier(
    	        "nyyyaaaa",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(-0.6f, -0.5f, 0f)
    	    .setRotate(0f, 180f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> BASE_H = registerItemAndExecute(armors_tab_content::add,
    	    "base_h",
    	    generateArmorSupplier(
    	        "base_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GASMASK_H = registerItemAndExecute(armors_tab_content::add,
    	    "gasmask_h",
    	    generateArmorSupplier(
    	        "gasmask_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT_H = registerItemAndExecute(armors_tab_content::add,
    	    "assault_h",
    	    generateArmorSupplier(
    	        "assault_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC_H = registerItemAndExecute(armors_tab_content::add,
    	    "medic_h",
    	    generateArmorSupplier(
    	        "medic_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD_H = registerItemAndExecute(armors_tab_content::add,
    	    "concord_h",
    	    generateArmorSupplier(
    	        "concord_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> INFANTRY_H = registerItemAndExecute(armors_tab_content::add,
    	    "infantry_h",
    	    generateArmorSupplier(
    	        "infantry_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GPNVG_H = registerItemAndExecute(armors_tab_content::add,
    	    "gpnvg_h",
    	    generateArmorSupplier(
    	        "gpnvg_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GHOST_H = registerItemAndExecute(armors_tab_content::add,
    	    "ghost_h",
    	    generateArmorSupplier(
    	        "ghost_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZCH_H = registerItemAndExecute(armors_tab_content::add,
    	    "zch_h",
    	    generateArmorSupplier(
    	        "zch_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZABRALO_H = registerItemAndExecute(armors_tab_content::add,
    	    "zabralo_h",
    	    generateArmorSupplier(
    	        "zabralo_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> KILLA_H = registerItemAndExecute(armors_tab_content::add,
    	    "killa_h",
    	    generateArmorSupplier(
    	        "killa_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MK_II_H = registerItemAndExecute(armors_tab_content::add,
    	    "mk_ii_h",
    	    generateArmorSupplier(
    	        "mk_ii_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER_H = registerItemAndExecute(armors_tab_content::add,
    	    "saper_h",
    	    generateArmorSupplier(
    	        "saper_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.54f, 0f)
    	    .setRotate(0f, 0f, 180f)
    	    .setScale(1f, 1f, 1f))
    	    .set(RTSMatricesCompound.key_workbench_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -2f, 0f)
    	    .setScale(50f, -50f, 50f))
    	    .set(RTSMatricesCompound.key_gui_render, new MatrixRTSBuilder()
    	    .setTranslate(0f, -1.3f, 0.7f)
    	    .setRotate(0f, 90f, 0f)
    	    .setScale(0.8f, 0.8f, 0.8f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);
    	
    public static final RegistryObject <CreativeModeTab> ARMORS_CREATIVE_TAB = CREATIVE_TABS.register(
    		"armors", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".armors"))
            .icon(() -> new ItemStack(ASSAULT.get()))
            .displayItems((params, output) -> {
            	armors_tab_content.stream().map(RegistryObject::get).forEach(output::accept);
            })
            .build()
        );
    
    private static Supplier<BasicArmor> generateArmorSupplier(
    		final String unlocName, final ArmorItem.Type type, final ConcordRarity rarity,
    		final RTSMatricesCompound transform, final float toughness,
    		final float knockbackResistance, final int defenseValue, final int durabilityValue
    		) {
    	final SimpleArmorMaterial material = ConfigMergers.mergeBasic("brimm_armor_material",
    			toughness, knockbackResistance, defenseValue, durabilityValue,
    			type, ConfigsManager.getAndEvictMaterial(unlocName));
    	return ()->new BasicArmor(unlocName, type, rarity, material, transform);
    }
    	
    private static <T extends Item> RegistryObject<T> registerItemAndExecute(Consumer<RegistryObject<T>> consumer,
    		String name, Supplier <? extends T> sup) {
    	RegistryObject<T> registered = ITEMS.register(name, sup);
    	consumer.accept(registered);
    	return registered;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
    }

}
