package brimmArmors.common.items;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import brimmArmors.BrimmArmors;
import ema_08_.geom.models.MatrixRTSBuilder;
import ema_08_.geom.models.RTSMatricesCompound;
import ema_08_.geom.models.RTSMatricesCompoundBuilder;
import ema_08_.items.SimpleArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
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
    	    () -> new BasicArmor(
    	        "nato",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.CHESTPLATE),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NATO_II = registerItemAndExecute(armors_tab_content::add,
    	    "nato_ii",
    	    () -> new BasicArmor(
    	        "nato_ii",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MARINE = registerItemAndExecute(armors_tab_content::add,
    	    "marine",
    	    () -> new BasicArmor(
    	        "marine",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VANDERER = registerItemAndExecute(armors_tab_content::add,
    	    "vanderer",
    	    () -> new BasicArmor(
    	        "vanderer",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GUARD = registerItemAndExecute(armors_tab_content::add,
    	    "guard",
    	    () -> new BasicArmor(
    	        "guard",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER = registerItemAndExecute(armors_tab_content::add,
    	    "saper",
    	    () -> new BasicArmor(
    	        "saper",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER = registerItemAndExecute(armors_tab_content::add,
    	    "defender",
    	    () -> new BasicArmor(
    	        "defender",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.CHESTPLATE),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_II = registerItemAndExecute(armors_tab_content::add,
    	    "defender_ii",
    	    () -> new BasicArmor(
    	        "defender_ii",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_III = registerItemAndExecute(armors_tab_content::add,
    	    "defender_iii",
    	    () -> new BasicArmor(
    	        "defender_iii",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD = registerItemAndExecute(armors_tab_content::add,
    	    "concord",
    	    () -> new BasicArmor(
    	        "concord",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC = registerItemAndExecute(armors_tab_content::add,
    	    "medic",
    	    () -> new BasicArmor(
    	        "medic",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> PMC = registerItemAndExecute(armors_tab_content::add,
    	    "pmc",
    	    () -> new BasicArmor(
    	        "pmc",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.CHESTPLATE),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT = registerItemAndExecute(armors_tab_content::add,
    	    "assault",
    	    () -> new BasicArmor(
    	        "assault",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SPN = registerItemAndExecute(armors_tab_content::add,
    	    "spn",
    	    () -> new BasicArmor(
    	        "spn",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> HORSE = registerItemAndExecute(armors_tab_content::add,
    	    "horse",
    	    () -> new BasicArmor(
    	        "horse",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ATLETI = registerItemAndExecute(armors_tab_content::add,
    	    "atleti",
    	    () -> new BasicArmor(
    	        "atleti",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK = registerItemAndExecute(armors_tab_content::add,
    	    "ratnik",
    	    () -> new BasicArmor(
    	        "ratnik",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.CHESTPLATE),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK_ADVANCE = registerItemAndExecute(armors_tab_content::add,
    	    "ratnik_advance",
    	    () -> new BasicArmor(
    	        "ratnik_advance",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.CHESTPLATE),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VETERAN = registerItemAndExecute(armors_tab_content::add,
    	    "veteran",
    	    () -> new BasicArmor(
    	        "veteran",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NYYYAAAA = registerItemAndExecute(armors_tab_content::add,
    	    "nyyyaaaa",
    	    () -> new BasicArmor(
    	        "nyyyaaaa",
    	        EquipmentSlot.CHEST,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.CHESTPLATE),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> BASE_H = registerItemAndExecute(armors_tab_content::add,
    	    "base_h",
    	    () -> new BasicArmor(
    	        "base_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.HELMET),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GASMASK_H = registerItemAndExecute(armors_tab_content::add,
    	    "gasmask_h",
    	    () -> new BasicArmor(
    	        "gasmask_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.HELMET),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT_H = registerItemAndExecute(armors_tab_content::add,
    	    "assault_h",
    	    () -> new BasicArmor(
    	        "assault_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.HELMET),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC_H = registerItemAndExecute(armors_tab_content::add,
    	    "medic_h",
    	    () -> new BasicArmor(
    	        "medic_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.HELMET),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD_H = registerItemAndExecute(armors_tab_content::add,
    	    "concord_h",
    	    () -> new BasicArmor(
    	        "concord_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.HELMET),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> INFANTRY_H = registerItemAndExecute(armors_tab_content::add,
    	    "infantry_h",
    	    () -> new BasicArmor(
    	        "infantry_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.HELMET),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GPNVG_H = registerItemAndExecute(armors_tab_content::add,
    	    "gpnvg_h",
    	    () -> new BasicArmor(
    	        "gpnvg_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.HELMET),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GHOST_H = registerItemAndExecute(armors_tab_content::add,
    	    "ghost_h",
    	    () -> new BasicArmor(
    	        "ghost_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.HELMET),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZCH_H = registerItemAndExecute(armors_tab_content::add,
    	    "zch_h",
    	    () -> new BasicArmor(
    	        "zch_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.HELMET),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZABRALO_H = registerItemAndExecute(armors_tab_content::add,
    	    "zabralo_h",
    	    () -> new BasicArmor(
    	        "zabralo_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.RARE,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.HELMET),
    	        9,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> KILLA_H = registerItemAndExecute(armors_tab_content::add,
    	    "killa_h",
    	    () -> new BasicArmor(
    	        "killa_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.EPIC,
    	        SimpleArmorMaterial.basic("hard", 0, 10f, 1f, 10, 240, ArmorItem.Type.HELMET),
    	        10,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MK_II_H = registerItemAndExecute(armors_tab_content::add,
    	    "mk_ii_h",
    	    () -> new BasicArmor(
    	        "mk_ii_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.COMMON,
    	        SimpleArmorMaterial.basic("common", 0, 0f, 0f, 8, 240, ArmorItem.Type.HELMET),
    	        8,
    	        240,
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
    	    .build()
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER_H = registerItemAndExecute(armors_tab_content::add,
    	    "saper_h",
    	    () -> new BasicArmor(
    	        "saper_h",
    	        EquipmentSlot.HEAD,
    	        ConcordRarity.UNCOMMON,
    	        SimpleArmorMaterial.basic("heavy", 0, 5f, 1f, 9, 240, ArmorItem.Type.HELMET),
    	        9,
    	        240,
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
    	    .build()
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
