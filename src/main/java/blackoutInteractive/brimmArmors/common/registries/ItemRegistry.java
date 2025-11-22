package blackoutInteractive.brimmArmors.common.registries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.configurations.ArmorConfig;
import blackoutInteractive.brimmArmors.common.configurations.ConfigMergers;
import blackoutInteractive.brimmArmors.common.configurations.ConfigsManager;
import blackoutInteractive.brimmArmors.common.items.ArmorPatch;
import blackoutInteractive.brimmArmors.common.items.BasicArmor;
import blackoutInteractive.brimmArmors.common.items.BasicPlate;
import blackoutInteractive.brimmArmors.common.items.ConcordRarity;
import blackoutInteractive.brimmArmors.common.workbench.CraftBuilder;
import blackoutInteractive.brimmArmors.common.workbench.CraftSection;
import blackoutInteractive.brimmArmors.common.workbench.CraftsManager;
import blackoutInteractive.brimmArmors.common.workbench.IngredientBuilder;
import blackoutInteractive.ema_08_.items.SimpleArmorMaterial;
import blackoutInteractive.ema_08_.rendering.geom.MatrixRTSBuilder;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompound;
import blackoutInteractive.ema_08_.rendering.geom.RTSMatricesCompoundBuilder;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayLocation;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;
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
    
    private static final ArrayList<RegistryObject<? extends Item>> armors_tab_content = new ArrayList<>();
    private static final ArrayList<RegistryObject<? extends Item>> misc_tab_content = new ArrayList<>();

    public static Optional<RegistryObject<Item>> getr(String id) {
        return ITEMS.getEntries().stream()
                .filter(entry -> entry.getId().getPath().equals(id))
                .findFirst();
    }
    public static Optional<Item> get(String id) {
    	return getr(id).map(RegistryObject::get);
    }
    
    @Deprecated
    public static RegistryObject<Item> getrOrThrow(String id) {
    	return getr(id).orElseThrow(()->new IllegalArgumentException("No registered brimm item with name "+id));
    }
    
    @Deprecated
    public static Item getOrThrow(String id) { 
    	return get(id).orElseThrow(()->new IllegalArgumentException("No registered brimm item with name "+id));
    }

    	public static final RegistryObject<BasicPlate> IRON_PLATE = registerItemAndExecute(
    		addTabAndSetCraft(misc_tab_content, CraftSection.PLATES,
    			ig(Items.IRON_INGOT, 5)
    		),
    		"iron_plate", () -> new BasicPlate(ConcordRarity.COMMON));
    
    	public static final RegistryObject<BasicPlate> DIAMOND_PLATE = registerItemAndExecute(
    		addTabAndSetCraft(misc_tab_content, CraftSection.PLATES,
    			ig(Items.IRON_INGOT, 10),
    			ig(Items.DIAMOND, 5)
    		),
    		"diamond_plate", () -> new BasicPlate(ConcordRarity.RARE));
    
    	public static final RegistryObject<BasicPlate> NETHER_PLATE = registerItemAndExecute(
    		addTabAndSetCraft(misc_tab_content, CraftSection.PLATES,
    			ig(Items.IRON_INGOT, 10),
    			ig(Items.DIAMOND, 10),
    			ig(Items.NETHERITE_INGOT, 1)
    		),
    		"nether_plate", () -> new BasicPlate(ConcordRarity.EPIC));
    
		public static final RegistryObject<BasicArmor> RATNIK = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
        			ig(IRON_PLATE, 3),
        			ig(Items.LEATHER, 1)
        		),
    	    "ratnik",
    	    generateArmorSupplier(
    	        "ratnik",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    0f, 0f, 8, 240, patches(OverlayPos.HUMANOID_TORSO,
    	    	newStandardFrontTorsoPatchMatrix((float)(3.7247*0.06295809)))
    	    )
    	);

    	public static final RegistryObject<BasicArmor> RATNIK_ADVANCE = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
            		ig(RATNIK, 1),
            		ig(Items.LEATHER, 5)
            	),
    	    "ratnik_advance",
    	    generateArmorSupplier(
    	        "ratnik_advance",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);
    	
    	public static final RegistryObject<BasicArmor> DEFENDER = registerItemAndExecute(
        	addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                	ig(IRON_PLATE, 2),
                	ig(Items.LEATHER, 5)
                ),
        	"defender",
        	generateArmorSupplier(
        	    "defender",
        	    ArmorItem.Type.CHESTPLATE,
        	    ConcordRarity.COMMON,
        	    newRTSMComp()
        	.set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
        	.set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
        	0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
        	)
        );

        public static final RegistryObject<BasicArmor> DEFENDER_II = registerItemAndExecute(
        	addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                	ig(DEFENDER, 1),
                	ig(Items.LEATHER, 5)
                ),
        	"defender_ii",
        	generateArmorSupplier(
        	    "defender_ii",
        	    ArmorItem.Type.CHESTPLATE,
        	    ConcordRarity.UNCOMMON,
        	    newRTSMComp()
        	.set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
        	.set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
        	5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
        	)
        );

        public static final RegistryObject<BasicArmor> DEFENDER_III = registerItemAndExecute(
        	addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER_II, 1),
                    ig(Items.LEATHER, 5)
                ),
        	"defender_iii",
        	generateArmorSupplier(
        	    "defender_iii",
        	    ArmorItem.Type.CHESTPLATE,
        	    ConcordRarity.RARE,
        	    newRTSMComp()
        	.set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
        	.set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
        	10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
        	)
        );
    
        public static final RegistryObject<BasicArmor> NATO = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
            		ig(RATNIK, 1),
            		ig(DIAMOND_PLATE, 2),
            		ig(Items.LEATHER, 5),
            		ig(Items.WHITE_DYE, 5)
            	),
    	    "nato",
    	    generateArmorSupplier(
    	        "nato",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NATO_II = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                	ig(NATO, 1),
                	ig(Items.LEATHER, 5)
                ),
    	    "nato_ii",
    	    generateArmorSupplier(
    	        "nato_ii",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MARINE = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.SALMON, 5)
                ),
    	    "marine",
    	    generateArmorSupplier(
    	        "marine",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VANDERER = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.CACTUS, 3)
                ),
    	    "vanderer",
    	    generateArmorSupplier(
    	        "vanderer",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GUARD = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.LEATHER, 10)
                ),
    	    "guard",
    	    generateArmorSupplier(
    	        "guard",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.TNT, 3)
                ),
    	    "saper",
    	    generateArmorSupplier(
    	        "saper",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> CONCORD = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.TNT, 3)
                ),
    	    "concord",
    	    generateArmorSupplier(
    	        "concord",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.GOLDEN_APPLE, 2)
               ),
    	    "medic",
    	    generateArmorSupplier(
    	        "medic",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> PMC = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                	ig(RATNIK, 1),
                	ig(DIAMOND_PLATE, 2),
                	ig(IRON_PLATE, 1)
                ),
    	    "pmc",
    	    generateArmorSupplier(
    	        "pmc",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)
                ),
    	    "assault",
    	    generateArmorSupplier(
    	        "assault",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.UNCOMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SPN = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)
                ),
    	    "spn",
    	    generateArmorSupplier(
    	        "spn",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> HORSE = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)
                ),
    	    "horse",
    	    generateArmorSupplier(
    	        "horse",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ATLETI = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 1),
                    ig(IRON_PLATE, 3)
                ),
    	    "atleti",
    	    generateArmorSupplier(
    	        "atleti",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> VETERAN = registerItemAndExecute(
    	    addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK_ADVANCE, 1),
                    ig(DIAMOND_PLATE, 2)
                ),
    	    "veteran",
    	    generateArmorSupplier(
    	        "veteran",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> NYYYAAAA = registerItemAndExecute(
    	    addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 5),
                    ig(Items.BLACK_DYE, 10),
                    ig(Items.WHITE_DYE, 5),
                    ig(Items.WITHER_ROSE, 1)
                ),
    	    "nyyyaaaa",
    	    generateArmorSupplier(
    	        "nyyyaaaa",
    	        ArmorItem.Type.CHESTPLATE,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-1f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> BASE_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
    				ig(Items.IRON_INGOT, 20),
    				ig(Items.LEATHER, 10)
    			),
    	    "base_h",
    	    generateArmorSupplier(
    	        "base_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GASMASK_H = registerItemAndExecute(
        	addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
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
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ASSAULT_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
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
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MEDIC_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.LEATHER, 5),
                    ig(Items.GOLDEN_APPLE, 1)
                ),
    	    "medic_h",
    	    generateArmorSupplier(
    	        "medic_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);
    	
    	public static final RegistryObject<BasicArmor> INFANTRY_H = registerItemAndExecute(
        	addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
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
        	    newRTSMComp()
        	.set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
        	0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
        	)
        );

    	public static final RegistryObject<BasicArmor> CONCORD_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(INFANTRY_H, 1),
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.DIAMOND, 10),
                    ig(Items.BLAZE_ROD, 50)
                ),
    	    "concord_h",
    	    generateArmorSupplier(
    	        "concord_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GPNVG_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.LEATHER, 10),
                    ig(Items.SPIDER_EYE, 10),
                    ig(Items.DIAMOND, 10)
                ),
    	    "gpnvg_h",
    	    generateArmorSupplier(
    	        "gpnvg_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> GHOST_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(GPNVG_H, 1),
                    ig(Items.SKELETON_SKULL, 1),
                    ig(Items.INK_SAC, 5)
                ),
    	    "ghost_h",
    	    generateArmorSupplier(
    	        "ghost_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.EPIC,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZCH_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
        			ig(Items.IRON_INGOT, 15),
        			ig(Items.LEATHER, 15)
        		),
    	    "zch_h",
    	    generateArmorSupplier(
    	        "zch_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> ZABRALO_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
            		ig(ZCH_H, 1),
            		ig(Items.GLASS_PANE, 10)
            	),
    	    "zabralo_h",
    	    generateArmorSupplier(
    	        "zabralo_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.RARE,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> KILLA_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
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
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    10f, 1f, 10, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> MK_II_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
            		ig(Items.IRON_INGOT, 15),
            		ig(Items.LEATHER, 5)
            	),
    	    "mk_ii_h",
    	    generateArmorSupplier(
    	        "mk_ii_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.COMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    0f, 0f, 8, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);

    	public static final RegistryObject<BasicArmor> SAPER_H = registerItemAndExecute(
    		addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                	ig(MK_II_H, 1),
                	ig(Items.REDSTONE, 10),
                	ig(Items.LEATHER, 5)
                ),
    	    "saper_h",
    	    generateArmorSupplier(
    	        "saper_h",
    	        ArmorItem.Type.HELMET,
    	        ConcordRarity.UNCOMMON,
    	        newRTSMComp()
    	    .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
    	    .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(-2f)),
    	    5f, 1f, 9, 240, new ArrayList<>() //TODO missing patches
    	    )
    	);
    	
    	public static final RegistryObject<ArmorPatch> DEBUG_PATCH = registerItemAndExecute(
    		addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
    					ig(Items.ENCHANTED_GOLDEN_APPLE, 64)
    			),
    		"patches_adjuster_debug_patch",
    			()->new ArmorPatch("patches_adjuster_debug_patch"));
    	
    public static final RegistryObject<CreativeModeTab> ARMORS_CREATIVE_TAB = CREATIVE_TABS.register(
    		"armors", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".armors"))
            .icon(() -> new ItemStack(ASSAULT.get()))
            .displayItems((params, output) -> {
            	armors_tab_content.stream().map(RegistryObject::get).forEach(output::accept);
            })
            .build()
        );
    
    public static final RegistryObject<CreativeModeTab> MISC_CREATIVE_TAB = CREATIVE_TABS.register(
    		"brimm_misc", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".brimm_misc"))
            .icon(() -> new ItemStack(IRON_PLATE.get()))
            .displayItems((params, output) -> {
            	misc_tab_content.stream().map(RegistryObject::get).forEach(output::accept);
            })
            .build()
        );
    
    private static RTSMatricesCompoundBuilder newRTSMComp() {
    	return new RTSMatricesCompoundBuilder();
    }
    
    private static MatrixRTSBuilder newmatrix() { return new MatrixRTSBuilder().identify(); }
    
    private static MatrixRTSBuilder newStandardArmorRenderMatrix() {
    	return newmatrix()
    	    .setTranslateY(-1.54f)
    	    .setRotateZ(180f);
    }
    
    private static MatrixRTSBuilder newStandardWorkbenchRenderMatrix(float trY) {
    	return newmatrix()
        	.setTranslateY(trY)
        	.setScale(50f, -50f, 50f)
        	.setRotateY(180);
    }
    
    private static MatrixRTSBuilder newStandardFrontTorsoPatchMatrix(float absoluteTRZ) {
    	return newmatrix()
    			.setTranslate(-0.5f, 1.45f, -absoluteTRZ)
    			.setScale(0.2f, 0.2f, 1f);
    }
    
    private static <T extends Item> Consumer<RegistryObject<T>>
			addTabAndSetCraft(Collection<RegistryObject<? extends Item>> tabHolder, ArmorItem.Type type, IngredientBuilder... ingredients) {
    	return addTabAndSetCraft(tabHolder, CraftSection.ofArmor(type), ingredients);
    }
        
    private static <T extends Item> Consumer<RegistryObject<T>>
    		addTabAndSetCraft(Collection<RegistryObject<? extends Item>> tabHolder, CraftSection section, IngredientBuilder... ingredients) {
    	return (obj)->{
    		tabHolder.add(obj);
    		if (ingredients.length > 0) {
    			CraftBuilder craft = new CraftBuilder(obj::get);
        		for (IngredientBuilder ingredient : ingredients) craft.addIngredient(ingredient);
        		CraftsManager.register(craft, section);
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
    
    private static ArrayList<OverlayLocation> patches(OverlayPos where, MatrixRTSBuilder... transforms) {
		ArrayList<OverlayLocation> list = new ArrayList<>();
    	for (MatrixRTSBuilder transform : transforms)
    		list.add(new OverlayLocation(where, transform.build()));
    	return list;
    }
    
    private static Supplier<BasicArmor> generateArmorSupplier(
    		final String unlocName, final ArmorItem.Type type, final ConcordRarity rarity,
    		final RTSMatricesCompoundBuilder transformB, final float toughness,
    		final float knockbackResistance, final int defenseValue, final int durabilityValue,
    		final Collection<OverlayLocation> patchesPositions
    		) {
    	ArmorConfig cfg = Optional.ofNullable(ConfigsManager.getAndEvict(unlocName)).orElse(ArmorConfig.EMPTY);
    	final SimpleArmorMaterial material = ConfigMergers.mergeBasicMaterial("brimm_armor_material",
    			toughness, knockbackResistance, defenseValue, durabilityValue,
    			type, cfg.materialOverrides());
    	final ConcordRarity mergedRarity = ConfigMergers.mergeRarity(rarity, cfg.rarityOverride());
    	final RTSMatricesCompound transform = transformB.build();
    	return ()->new BasicArmor(unlocName, type, mergedRarity, material, transform, patchesPositions);
    }
    	
    private static <T extends Item> RegistryObject<T> registerItemAndExecute(Consumer<RegistryObject<T>> consumer,
    		String name, Supplier <? extends T> sup) {
    	RegistryObject<T> registered = ITEMS.register(name, sup);
    	consumer.accept(registered);
    	return registered;
    }
    
    public static <T extends Item> RegistryObject<T> registerAndAddToMiscTab(String name, Supplier <? extends T> sup) {
    	RegistryObject<T> reg = ITEMS.register(name, sup);
    	misc_tab_content.add(reg);
    	return reg;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
    }

}
