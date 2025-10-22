package brimmArmors.common.items;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import brimmArmors.BrimmArmors;
import brimmArmors.common.configurations.ArmorConfig;
import brimmArmors.common.configurations.ConfigMergers;
import brimmArmors.common.configurations.ConfigsManager;
import brimmArmors.common.workbench.CraftBuilder;
import brimmArmors.common.workbench.CraftsManager;
import brimmArmors.common.workbench.IngredientBuilder;
import ema_08_.geom.models.*;
import ema_08_.items.SimpleArmorMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

    public static final RegistryObject<BasicPlate> IRON_PLATE = registerItemAndExecute(
    		(res)->CraftsManager.register(new CraftBuilder(res::get)
    				.addIngredient(ig(Items.IRON_INGOT, 5))
    			),
    		"iron_plate", () -> new BasicPlate(ConcordRarity.COMMON));
    
    public static final RegistryObject<BasicPlate> DIAMOND_PLATE = registerItemAndExecute(
    		(res)->CraftsManager.register(new CraftBuilder(res::get)
    				.addIngredient(ig(Items.IRON_INGOT, 10))
    				.addIngredient(ig(Items.DIAMOND, 5))
    			),
    		"diamond_plate", () -> new BasicPlate(ConcordRarity.RARE));
    
    public static final RegistryObject<BasicPlate> NETHER_PLATE = registerItemAndExecute(
    		(res)->CraftsManager.register(new CraftBuilder(res::get)
    				.addIngredient(ig(Items.IRON_INGOT, 10))
    				.addIngredient(ig(Items.DIAMOND, 10))
    				.addIngredient(ig(Items.NETHERITE_INGOT, 1))
    			),
    		"nether_plate", () -> new BasicPlate(ConcordRarity.EPIC));
    
    public static final RegistryObject<BasicArmor> NATO = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "nato",
    	    generateArmorSupplier(
    	        "nato",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NATO_II = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "nato_ii",
    	    generateArmorSupplier(
    	        "nato_ii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MARINE = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "marine",
    	    generateArmorSupplier(
    	        "marine",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VANDERER = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "vanderer",
    	    generateArmorSupplier(
    	        "vanderer",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GUARD = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "guard",
    	    generateArmorSupplier(
    	        "guard",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "saper",
    	    generateArmorSupplier(
    	        "saper",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "defender",
    	    generateArmorSupplier(
    	        "defender",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_II = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "defender_ii",
    	    generateArmorSupplier(
    	        "defender_ii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> DEFENDER_III = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "defender_iii",
    	    generateArmorSupplier(
    	        "defender_iii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "concord",
    	    generateArmorSupplier(
    	        "concord",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "medic",
    	    generateArmorSupplier(
    	        "medic",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> PMC = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "pmc",
    	    generateArmorSupplier(
    	        "pmc",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "assault",
    	    generateArmorSupplier(
    	        "assault",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SPN = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "spn",
    	    generateArmorSupplier(
    	        "spn",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> HORSE = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "horse",
    	    generateArmorSupplier(
    	        "horse",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ATLETI = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "atleti",
    	    generateArmorSupplier(
    	        "atleti",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "ratnik",
    	    generateArmorSupplier(
    	        "ratnik",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK_ADVANCE = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "ratnik_advance",
    	    generateArmorSupplier(
    	        "ratnik_advance",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VETERAN = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "veteran",
    	    generateArmorSupplier(
    	        "veteran",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NYYYAAAA = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "nyyyaaaa",
    	    generateArmorSupplier(
    	        "nyyyaaaa",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-1f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> BASE_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
    				ig(Items.IRON_INGOT, 20),
    				ig(Items.LEATHER, 10)
    			),
    	    "base_h",
    	    generateArmorSupplier(
    	        "base_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GASMASK_H = registerItemAndExecute(
        	addArmorsTabAndSetCraft(
                	ig(BASE_H, 1),
                	ig(Items.IRON_INGOT, 30),
                	ig(Items.COAL, 30),
                	ig(Items.GLASS, 15)
                ),
    	    "gasmask_h",
    	    generateArmorSupplier(
    	        "gasmask_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
            		ig(BASE_H, 1),
            		ig(Items.IRON_INGOT, 30),
            		ig(Items.REDSTONE, 10),
            		ig(Items.LAPIS_LAZULI, 10)
            	),
    	    "assault_h",
    	    generateArmorSupplier(
    	        "assault_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
                    ig(ASSAULT_H, 1),
                    ig(Items.LEATHER, 5),
                    ig(Items.GOLDEN_APPLE, 1)
                ),
    	    "medic_h",
    	    generateArmorSupplier(
    	        "medic_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD_H = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "concord_h",
    	    generateArmorSupplier(
    	        "concord_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> INFANTRY_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
                    ig(BASE_H, 1),
                    ig(Items.IRON_INGOT, 30),
                    ig(Items.LEATHER, 10),
                    ig(Items.PAPER, 10)
                ),
    	    "infantry_h",
    	    generateArmorSupplier(
    	        "infantry_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GPNVG_H = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "gpnvg_h",
    	    generateArmorSupplier(
    	        "gpnvg_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GHOST_H = registerItemAndExecute(addArmorsTabAndSetCraft(),//TODO missing ingredients
    	    "ghost_h",
    	    generateArmorSupplier(
    	        "ghost_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZCH_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
        			ig(Items.IRON_INGOT, 15),
        			ig(Items.LEATHER, 15)
        		),
    	    "zch_h",
    	    generateArmorSupplier(
    	        "zch_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZABRALO_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
            		ig(ZCH_H, 1),
            		ig(Items.GLASS_PANE, 10)
            	),
    	    "zabralo_h",
    	    generateArmorSupplier(
    	        "zabralo_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    5f, 1f, 9, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> KILLA_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
                	ig(ZCH_H, 1),
                	ig(Items.IRON_INGOT, 30),
                	ig(Items.GLASS_PANE, 5),
                	ig(Items.INK_SAC, 5)
                ),
    	    "killa_h",
    	    generateArmorSupplier(
    	        "killa_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    10f, 1f, 10, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MK_II_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
            		ig(Items.IRON_INGOT, 15),
            		ig(Items.LEATHER, 5)
            	),
    	    "mk_ii_h",
    	    generateArmorSupplier(
    	        "mk_ii_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
    	    .build(),
    	    0f, 0f, 8, 240
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER_H = registerItemAndExecute(
    		addArmorsTabAndSetCraft(
                	ig(MK_II_H, 1),
                	ig(Items.REDSTONE, 10),
                	ig(Items.LEATHER, 5)
                ),
    	    "saper_h",
    	    generateArmorSupplier(
    	        "saper_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        new RTSMatricesCompoundBuilder()
    	    .set(RTSMatricesCompound.key_armor_render, newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f))
    	    .set(RTSMatricesCompound.key_workbench_render, newmatrix()
    	    .setTranslateY(-2f)
    	    .setScale(50f, -50f, 50f))
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
    
    private static MatrixRTSBuilder newmatrix() { return new MatrixRTSBuilder().identify(); }
    
    private static Consumer<RegistryObject<BasicArmor>> addArmorsTabAndSetCraft(IngredientBuilder... ingredients) {
    	return (obj)->{
    		armors_tab_content.add(obj);
    		if (ingredients.length > 0) {
    			CraftBuilder craft = new CraftBuilder(obj::get);
        		for (IngredientBuilder ingredient : ingredients) craft.addIngredient(ingredient);
        		CraftsManager.register(craft);
    		} else {
    			BrimmArmors.LOGGER.warn("Registering without workbench craft: "+obj.getKey().toString()+".");
    		}
    	};
    }
    
    private static IngredientBuilder ig(Supplier<Item> sup, int amt) {
    	return new IngredientBuilder(sup, amt);
    }
    
    private static IngredientBuilder ig(RegistryObject<? extends Item> ro, int amt) {
    	return ig(ro::get, amt);
    }
    
    private static IngredientBuilder ig(Item i, int amt) {
    	return ig(()->i, amt);
    }
    
    private static Supplier<BasicArmor> generateArmorSupplier(
    		final String unlocName, final ArmorItem.Type type, final ConcordRarity rarity,
    		final RTSMatricesCompound transform, final float toughness,
    		final float knockbackResistance, final int defenseValue, final int durabilityValue
    		) {
    	ArmorConfig cfg = Optional.ofNullable(ConfigsManager.getAndEvict(unlocName)).orElse(ArmorConfig.EMPTY);
    	final SimpleArmorMaterial material = ConfigMergers.mergeBasicMaterial("brimm_armor_material",
    			toughness, knockbackResistance, defenseValue, durabilityValue,
    			type, cfg.materialOverrides());
    	final ConcordRarity mergedRarity = ConfigMergers.mergeRarity(rarity, cfg.rarityOverride());
    	return ()->new BasicArmor(unlocName, type, mergedRarity, material, transform);
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
