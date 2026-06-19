package blackoutInteractive.brimmArmors.common.registries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import blackoutInteractive.brimmArmors.BrimmArmors;
import blackoutInteractive.brimmArmors.common.configurations.*;
import blackoutInteractive.brimmArmors.common.items.*;
import blackoutInteractive.brimmArmors.common.workbench.*;
import blackoutInteractive.ema_08_.items.SimpleArmorMaterial;
import blackoutInteractive.ema_08_.items.effectsProvidingArmors.*;
import blackoutInteractive.ema_08_.rendering.geom.*;
import blackoutInteractive.ema_08_.rendering.obj.ModelType;
import blackoutInteractive.ema_08_.rendering.obj.ObjModelReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayLocation;
import blackoutInteractive.ema_08_.rendering.overlay.OverlayPos;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BrimmArmors.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, BrimmArmors.MOD_ID);

    private static final ArrayList<RegistryObject<? extends Item>> armors_tab_content = new ArrayList<>();
    private static final ArrayList<RegistryObject<? extends Item>> misc_tab_content = new ArrayList<>();

    // --- ACCESSORS ---

    public static Optional<RegistryObject<Item>> getr(String id) {
        return ITEMS.getEntries().stream()
                .filter(entry -> entry.getId().getPath().equals(id))
                .findFirst();
    }

    public static Optional<Item> get(String id) {
        return getr(id).map(RegistryObject::get);
    }

    public static RegistryObject<Item> getrOrThrow(String id) {
        return getr(id).orElseThrow(() -> new IllegalArgumentException("No registered brimm item with name " + id));
    }

    public static Item getOrThrow(String id) {
        return get(id).orElseThrow(() -> new IllegalArgumentException("No registered brimm item with name " + id));
    }

    // --- PLATES ---

    public static final RegistryObject<BrimmPlate> IRON_PLATE = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PLATES, ig(Items.IRON_INGOT, 5)),
            "iron_plate", () -> new BrimmPlate(BrimmRarity.COMMON));

    public static final RegistryObject<BrimmPlate> DIAMOND_PLATE = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PLATES,
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.DIAMOND, 5)),
            "diamond_plate", () -> new BrimmPlate(BrimmRarity.RARE));

    public static final RegistryObject<BrimmPlate> NETHER_PLATE = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PLATES,
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.DIAMOND, 10),
                    ig(Items.NETHERITE_INGOT, 1)),
            "nether_plate", () -> new BrimmPlate(BrimmRarity.EPIC));

    // --- CHESTPLATES ---

    public static final RegistryObject<BrimmArmor> RATNIK = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(IRON_PLATE, 3),
                    ig(Items.LEATHER, 1)),
            "ratnik",
            generateArmorSupplierChestplate("ratnik", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    0f, 0f, 8, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> RATNIK_ADVANCE = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(Items.LEATHER, 5)),
            "ratnik_advance",
            generateArmorSupplierChestplate("ratnik_advance", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.07f, 0.11f, -0.162f).setRotate(-22, 0, 0).setScale(0.15f, 0.15f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> DEFENDER = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(IRON_PLATE, 2),
                    ig(Items.LEATHER, 5)),
            "defender",
            generateArmorSupplierChestplate("defender", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    0f, 0f, 8, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.07f, 0.34f, -0.228f).setRotate(0, 0, 0).setScale(0.14f, 0.136f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> DEFENDER_II = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(Items.LEATHER, 5)),
            "defender_ii",
            generateArmorSupplierChestplate("defender_ii", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.07f, 0.34f, -0.228f).setRotate(0, 0, 0).setScale(0.14f, 0.136f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> DEFENDER_III = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER_II, 1),
                    ig(Items.LEATHER, 5)),
            "defender_iii",
            generateArmorSupplierChestplate("defender_iii", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.07f, 0.34f, -0.228f).setRotate(0, 0, 0).setScale(0.14f, 0.136f, 1)),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> NATO = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.LEATHER, 5),
                    ig(Items.WHITE_DYE, 5)),
            "nato",
            generateArmorSupplierChestplate("nato", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    0f, 0f, 8, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.1f, 0.138f, -0.202f).setRotate(-12f, 0, 0).setScale(0.188f, 0.185f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> NATO_II = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(NATO, 1),
                    ig(Items.LEATHER, 5)),
            "nato_ii",
            generateArmorSupplierChestplate("nato_ii", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.1f, 0.138f, -0.202f).setRotate(-12f, 0, 0).setScale(0.188f, 0.185f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> PRESS = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(Items.LEATHER, 5),
                    ig(Items.BLUE_DYE, 5)),
            "press",
            generateArmorSupplierChestplate("press", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> MARINE = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.SALMON, 5)),
            "marine",
            generateArmorSupplierChestplate("marine", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(0.196f, 0, -0.139f).setRotate(-11.1102f, 4.5635f, 16.0559f).setScale(0.1f, 0.1f, 1)),
                    addEffects(
                    		stdEff(MobEffects.WATER_BREATHING),
                    		stdEff(MobEffects.DOLPHINS_GRACE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> VANDERER = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.CACTUS, 3)),
            "vanderer",
            generateArmorSupplierChestplate("vanderer", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.07f, 0.287f, -0.228f).setRotate(0, 0, 0).setScale(0.14f, 0.1373f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> GUARD = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.LEATHER, 10)),
            "guard",
            generateArmorSupplierChestplate("guard", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> SAPER = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.TNT, 3)),
            "saper",
            generateArmorSupplierChestplate("saper", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> CONCORD = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 3),
                    ig(Items.TNT, 3)),
            "concord",
            generateArmorSupplierChestplate("concord", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> MEDIC = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(Items.GOLDEN_APPLE, 2)),
            "medic",
            generateArmorSupplierChestplate("medic", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.REGENERATION, 3)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> PMC = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 2),
                    ig(IRON_PLATE, 1)),
            "pmc",
            generateArmorSupplierChestplate("pmc", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    0f, 0f, 8, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> ASSAULT = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)),
            "assault",
            generateArmorSupplierChestplate("assault", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.1435f, 0.2209f, -0.3f).setRotate(-6, 0, 0).setScale(0.14f, 0.136f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> SPN = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)),
            "spn",
            generateArmorSupplierChestplate("spn", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.134f, 0.224f, -0.2974f).setRotate(-5, 0, 0).setScale(0.127f, 0.1379f, 1)),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> HORSE = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(PMC, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.LEATHER, 15)),
            "horse",
            generateArmorSupplierChestplate("horse", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.062f, 0.132f, -0.199f).setRotate(-12, 0, 0).setScale(0.15f, 0.1385f, 1)),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> ATLETI = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK, 1),
                    ig(DIAMOND_PLATE, 1),
                    ig(IRON_PLATE, 3)),
            "atleti",
            generateArmorSupplierChestplate("atleti", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE),
                    		stdEff(MobEffects.MOVEMENT_SPEED, 3)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> VETERAN = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK_ADVANCE, 1),
                    ig(DIAMOND_PLATE, 2)),
            "veteran",
            generateArmorSupplierChestplate("veteran", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    patches(OverlayPos.HUMANOID_TORSO,
                    		newmatrix().setTranslate(-0.055f, 0.265f, -0.304f).setRotate(-8, 0, 0).setScale(0.11f, 0.1f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> GHOST = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(RATNIK_ADVANCE, 1),
                    ig(NETHER_PLATE, 2),
                    ig(Items.BLACK_DYE, 5)),
            "ghost",
            generateArmorSupplierChestplate("ghost", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> NYYYAAAA = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.CHESTPLATE,
                    ig(DEFENDER, 1),
                    ig(NETHER_PLATE, 5),
                    ig(Items.BLACK_DYE, 10),
                    ig(Items.WHITE_DYE, 5),
                    ig(Items.WITHER_ROSE, 1)),
            "nyyyaaaa",
            generateArmorSupplierChestplate("nyyyaaaa", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(50f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    // --- HELMETS ---

    public static final RegistryObject<BrimmArmor> BASE_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 20),
                    ig(Items.LEATHER, 10)),
            "base_h",
            generateArmorSupplierHelmet("base_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3065f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> TSHFOUR_GREEN_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 5),
                    ig(Items.LEATHER, 10),
                    ig(Items.GREEN_DYE, 5)),
            "tshfour_green_h",
            generateArmorSupplierHelmet("tshfour_green_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> TSHFOUR_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(TSHFOUR_GREEN_H, 1),
                    ig(Items.IRON_INGOT, 5),
                    ig(Items.LEATHER, 10),
                    ig(Items.BLACK_DYE, 5)),
            "tshfour_h",
            generateArmorSupplierHelmet("tshfour_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> RATNIK_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(BASE_H, 1),
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.GREEN_DYE, 5)),
            "ratnik_h",
            generateArmorSupplierHelmet("ratnik_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3065f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> GASMASK_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(BASE_H, 1),
                    ig(Items.IRON_INGOT, 30),
                    ig(Items.COAL, 30),
                    ig(Items.GLASS, 15)),
            "gasmask_h",
            generateArmorSupplierHelmet("gasmask_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    preventEffects(
                    		MobEffects.POISON,
                    		MobEffects.BLINDNESS,
                    		MobEffects.HUNGER,
                    		MobEffects.CONFUSION,
                    		MobEffects.WITHER,
                    		MobEffects.MOVEMENT_SLOWDOWN,
                    		MobEffects.WEAKNESS
                    		),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> INFANTRY_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(BASE_H, 1),
                    ig(Items.IRON_INGOT, 30),
                    ig(Items.LEATHER, 10),
                    ig(Items.PAPER, 10)),
            "infantry_h",
            generateArmorSupplierHelmet("infantry_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> ASSAULT_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(BASE_H, 1),
                    ig(Items.IRON_INGOT, 30),
                    ig(Items.REDSTONE, 10),
                    ig(Items.LAPIS_LAZULI, 10)),
            "assault_h",
            generateArmorSupplierHelmet("assault_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> PMC_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.RED_DYE, 5)),
            "pmc_h",
            generateArmorSupplierHelmet("pmc_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> NATO_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.LIGHT_GRAY_DYE, 5)),
            "nato_h",
            generateArmorSupplierHelmet("nato_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> PRESS_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 40),
                    ig(Items.LEATHER, 20),
                    ig(Items.BLUE_DYE, 5)),
            "press_h",
            generateArmorSupplierHelmet("press_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> MEDIC_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.LEATHER, 5),
                    ig(Items.GOLDEN_APPLE, 1)),
            "medic_h",
            generateArmorSupplierHelmet("medic_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    10f, 1f, 10, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    addEffects(
                    		stdEff(MobEffects.REGENERATION, 3)
                    		),
                    noPreventEffects(),
                    auraEffects(
                    		stdAura(MobEffects.REGENERATION, 5, 3)
                    		)
    ));

    public static final RegistryObject<BrimmArmor> VETERAN_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.DIAMOND, 10)),
            "veteran_h",
            generateArmorSupplierHelmet("veteran_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.301f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> SIX_B_FOUR_SEVEN_DARK_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 25),
                    ig(Items.LEATHER, 5)),
            "six_b_four_seven_dark_h",
            generateArmorSupplierHelmet("six_b_four_seven_dark_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.288f, -0.544f, -0.097f).setRotate(0, -90, -16).setScale(0.19f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> SIX_B_FOUR_SEVEN_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(SIX_B_FOUR_SEVEN_DARK_H, 1),
                    ig(Items.IRON_INGOT, 20),
                    ig(Items.DIAMOND, 10)),
            "six_b_four_seven_h",
            generateArmorSupplierHelmet("six_b_four_seven_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.288f, -0.544f, -0.097f).setRotate(0, -90, -16).setScale(0.19f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> AIRFRAME_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 25),
                    ig(Items.LEATHER, 5)),
            "airframe_h",
            generateArmorSupplierHelmet("airframe_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> CONCORD_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(INFANTRY_H, 1),
                    ig(Items.IRON_INGOT, 10),
                    ig(Items.DIAMOND, 10),
                    ig(Items.BLAZE_ROD, 50)),
            "concord_h",
            generateArmorSupplierHelmet("concord_h", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE),
                    		stdEff(MobEffects.REGENERATION, 2)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> GPNVG_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ASSAULT_H, 1),
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.LEATHER, 10),
                    ig(Items.SPIDER_EYE, 10),
                    ig(Items.DIAMOND, 10)),
            "gpnvg_h",
            generateArmorSupplierHelmet("gpnvg_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    patches(OverlayPos.HUMANOID_HEAD,
                    		newmatrix().setTranslate(0.3075f, -0.542f, -0.09f).setRotate(0, -90, -13).setScale(0.18f, 0.17f, 1)),
                    addEffects(
                    		stdEff(MobEffects.NIGHT_VISION)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> GHOST_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(GPNVG_H, 1),
                    ig(Items.SKELETON_SKULL, 1),
                    ig(Items.INK_SAC, 5)),
            "ghost_h",
            generateArmorSupplierHelmet("ghost_h", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE),
                    		stdEff(MobEffects.NIGHT_VISION)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> ZCH_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.LEATHER, 15)),
            "zch_h",
            generateArmorSupplierHelmet("zch_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> ZABRALO_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ZCH_H, 1),
                    ig(Items.GLASS_PANE, 10)),
            "zabralo_h",
            generateArmorSupplierHelmet("zabralo_h", BrimmRarity.RARE,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> KILLA_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(ZCH_H, 1),
                    ig(Items.IRON_INGOT, 30),
                    ig(Items.GLASS_PANE, 5),
                    ig(Items.INK_SAC, 5)),
            "killa_h",
            generateArmorSupplierHelmet("killa_h", BrimmRarity.EPIC,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    10f, 1f, 10, 240,
                    nopatches(),
                    addEffects(
                    		stdEff(MobEffects.FIRE_RESISTANCE)
                    		),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> MK_II_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(Items.IRON_INGOT, 15),
                    ig(Items.LEATHER, 5)),
            "mk_ii_h",
            generateArmorSupplierHelmet("mk_ii_h", BrimmRarity.COMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    0f, 0f, 8, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    public static final RegistryObject<BrimmArmor> SAPER_H = registerItemAndExecute(
            addTabAndSetCraft(armors_tab_content, ArmorItem.Type.HELMET,
                    ig(MK_II_H, 1),
                    ig(Items.REDSTONE, 10),
                    ig(Items.LEATHER, 5)),
            "saper_h",
            generateArmorSupplierHelmet("saper_h", BrimmRarity.UNCOMMON,
                    newRTSMComp()
                            .set(RTSMatricesCompound.key_armor_render, newStandardArmorRenderMatrix())
                            .set(RTSMatricesCompound.key_workbench_render, newStandardWorkbenchRenderMatrix(100f)),
                    5f, 1f, 9, 240,
                    nopatches(),
                    noAddEffects(),
                    noPreventEffects(),
                    noAuraEffects()
    ));

    // --- PATCHES ---

    public static final RegistryObject<ArmorPatch> DEBUG_PATCH = registerItemAndExecute((p, n) -> {},
            "patches_adjuster_debug_patch", () -> new ArmorPatch("patches_adjuster_debug_patch")
    );

    public static final RegistryObject<ArmorPatch> NEW_YORK_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "new_york_patch", () -> new ArmorPatch("new_york_patch")
    );

    public static final RegistryObject<ArmorPatch> AMERICAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "american_flag_patch", () -> new ArmorPatch("american_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> AUSTRIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "austrian_flag_patch", () -> new ArmorPatch("austrian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> BATTLE_SCARRED_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "battle_scarred_patch", () -> new ArmorPatch("battle_scarred_patch")
    );

    public static final RegistryObject<ArmorPatch> BELGIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "belgian_flag_patch", () -> new ArmorPatch("belgian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> BELORUSSIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "belorussian_flag_patch", () -> new ArmorPatch("belorussian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> BRITISH_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "british_flag_patch", () -> new ArmorPatch("british_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> CENTURION_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "centurion_patch", () -> new ArmorPatch("centurion_patch")
    );

    public static final RegistryObject<ArmorPatch> CHINESE_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "chinese_flag_patch", () -> new ArmorPatch("chinese_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> COVENANT_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "covenant_patch", () -> new ArmorPatch("covenant_patch")
    );

    public static final RegistryObject<ArmorPatch> CRIMSON_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "crimson_patch", () -> new ArmorPatch("crimson_patch")
    );

    public static final RegistryObject<ArmorPatch> CROWN_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "crown_patch", () -> new ArmorPatch("crown_patch")
    );

    public static final RegistryObject<ArmorPatch> EGYPTIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "egyptian_flag_patch", () -> new ArmorPatch("egyptian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> FRENCH_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "french_flag_patch", () -> new ArmorPatch("french_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> ITALIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "italian_flag_patch", () -> new ArmorPatch("italian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> GERMAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "german_flag_patch", () -> new ArmorPatch("german_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> JAPANESE_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "japanese_flag_patch", () -> new ArmorPatch("japanese_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> KILL_JOY_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "kill_joy_patch", () -> new ArmorPatch("kill_joy_patch")
    );

    public static final RegistryObject<ArmorPatch> OMICRON_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "omicron_patch", () -> new ArmorPatch("omicron_patch")
    );

    public static final RegistryObject<ArmorPatch> OPERATIVE_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "operative_patch", () -> new ArmorPatch("operative_patch")
    );

    public static final RegistryObject<ArmorPatch> PAKISTANI_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "pakistani_flag_patch", () -> new ArmorPatch("pakistani_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> POLISH_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "polish_flag_patch", () -> new ArmorPatch("polish_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> PRAETORIAN_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "praetorian_patch", () -> new ArmorPatch("praetorian_patch")
    );

    public static final RegistryObject<ArmorPatch> RUSSIAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "russian_flag_patch", () -> new ArmorPatch("russian_flag_patch")
    );

    public static final RegistryObject<ArmorPatch> STALWART_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "stalwart_patch", () -> new ArmorPatch("stalwart_patch")
    );

    public static final RegistryObject<ArmorPatch> TITAN_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "titan_patch", () -> new ArmorPatch("titan_patch")
    );

    public static final RegistryObject<ArmorPatch> TITAN_II_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "titan_ii_patch", () -> new ArmorPatch("titan_ii_patch")
    );

    public static final RegistryObject<ArmorPatch> URBAN_AMERICAN_FLAG_PATCH = registerItemAndExecute(
            addTabAndSetCraft(misc_tab_content, CraftSection.PATCHES,
            		ig(Items.LEATHER, 1),
            		ig(Items.PAPER, 5)),
            "urban_american_flag_patch", () -> new ArmorPatch("urban_american_flag_patch")
    );

    // --- CREATIVE TABS ---

    public static final RegistryObject<CreativeModeTab> ARMORS_CREATIVE_TAB = CREATIVE_TABS.register(
            "armors", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".armors"))
                    .icon(() -> new ItemStack(ASSAULT.get()))
                    .displayItems((params, output) -> {
                        armors_tab_content.stream().map(RegistryObject::get).forEach(output::accept);
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> MISC_CREATIVE_TAB = CREATIVE_TABS.register(
            "brimm_misc", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".brimm_misc"))
                    .icon(() -> new ItemStack(IRON_PLATE.get()))
                    .displayItems((params, output) -> {
                        misc_tab_content.stream().map(RegistryObject::get).forEach(output::accept);
                    })
                    .build());

    // --- MATRICES HELPERS ---

    private static RTSMatricesCompoundBuilder newRTSMComp() {
        return new RTSMatricesCompoundBuilder();
    }

    private static MatrixRTSBuilder newmatrix() {
        return new MatrixRTSBuilder().identify();
    }

    private static MatrixRTSBuilder newStandardArmorRenderMatrix() {
        return newmatrix().setTranslateY(1.54f).setRotateZ(180f);
    }

    private static MatrixRTSBuilder newStandardWorkbenchRenderMatrix(float trY) {
        return newmatrix().setTranslateY(trY).setRotateY(180f).setScale(50f, -50f, 50f);
    }

    // --- TABS & CRAFT HELPERS ---

    private static <T extends Item> BiConsumer<RegistryObject<T>, String> addTabAndSetCraft(
            Collection<RegistryObject<? extends Item>> tabHolder, ArmorItem.Type type, IngredientBuilder... ingredients) {
        return addTabAndSetCraft(tabHolder, CraftSection.ofArmor(type), ingredients);
    }

    private static <T extends Item> BiConsumer<RegistryObject<T>, String> addTabAndSetCraft(
            Collection<RegistryObject<? extends Item>> tabHolder, CraftSection section, IngredientBuilder... ingredients) {
        return (obj, name) -> {
            tabHolder.add(obj);
            if (ingredients.length > 0) {
                CraftBuilder craft = new CraftBuilder(obj::get);
                for (IngredientBuilder ingredient : ingredients) craft.addIngredient(ingredient);
                craft.setId(new ResourceLocation(BrimmArmors.MOD_ID, "wbcraft_" + name));
                CraftsManager.register(craft, section);
            } else {
                BrimmArmors.LOGGER.warn("Registering without workbench craft: " + obj.getKey().toString() + ".");
            }
        };
    }

    // --- CRAFT INGREDIENTS HELPERS ---

    private static IngredientBuilder ig(Supplier<Item> sup, int amt) {
        return new IngredientBuilder(sup, amt);
    }

    private static IngredientBuilder ig(RegistryObject<? extends Item> ro, int amt) {
        return ig(ro::get, amt);
    }

    private static IngredientBuilder ig(Item i, int amt) {
        return ig(() -> i, amt);
    }

    // --- PATCHES HELPERS ---

    private static ArrayList<OverlayLocation> patches(OverlayPos where, MatrixRTSBuilder... transforms) {
        ArrayList<OverlayLocation> list = new ArrayList<>();
        for (MatrixRTSBuilder transform : transforms)
            list.add(new OverlayLocation(where, transform.build()));
        return list;
    }

    private static ArrayList<OverlayLocation> nopatches() {
        return new ArrayList<>();
    }

    // --- ARMOR EFFECTS HELPERS ---

    private static ArrayList<IAmplifiableApplicableEffect> noAddEffects() {
        return new ArrayList<>();
    }

    private static ArrayList<MobEffect> noPreventEffects() {
        return new ArrayList<>();
    }

    private static ArrayList<IAuraEffect> noAuraEffects() {
        return new ArrayList<>();
    }

    private static List<IAmplifiableApplicableEffect> addEffects(IAmplifiableApplicableEffect... effects) {
        return Arrays.asList(effects);
    }

    private static List<MobEffect> preventEffects(MobEffect... effects) {
        return Arrays.asList(effects);
    }

    private static List<IAuraEffect> auraEffects(IAuraEffect... effects) {
        return Arrays.asList(effects);
    }

    private static IAuraEffect stdAura(MobEffect effect, int range, int amplifier) {
        return new StandardAuraEffect(range, amplifier, effect);
    }

    private static IAmplifiableApplicableEffect stdEff(MobEffect effect) {
        return new StandardApplicableEffect(0, effect);
    }
    
    private static IAmplifiableApplicableEffect stdEff(MobEffect effect, int amplifier) {
        return new StandardApplicableEffect(amplifier, effect);
    }

    // --- ARMOR SUPPLIERS HELPER ---
    
    private static Supplier<BrimmArmor> generateArmorSupplierHelmet(
            final String unlocName, final BrimmRarity rarity,
            final RTSMatricesCompoundBuilder transform, final float toughness,
            final float knockbackResistance, final int defenseValue, final int durabilityValue,
            final Collection<OverlayLocation> patchesPositions, final Collection<IAmplifiableApplicableEffect> onWearEffects,
            final Collection<MobEffect> preventOnWearEffects, final Collection<IAuraEffect> auraEffects) {
    	final ObjModelReference[] models = new ObjModelReference[] {
    			new ObjModelReference(ModelType.ARMOR_HELMET, unlocName, transform.build())
    	};
    	return generateArmorSupplier0(ArmorItem.Type.HELMET,
    			unlocName, rarity,
                models, toughness,
                knockbackResistance, defenseValue, durabilityValue,
                patchesPositions, onWearEffects,
                preventOnWearEffects, auraEffects
            );
    }
    
    private static Supplier<BrimmArmor> generateArmorSupplierChestplate(
            final String unlocName, final BrimmRarity rarity,
            final RTSMatricesCompoundBuilder transform, final float toughness,
            final float knockbackResistance, final int defenseValue, final int durabilityValue,
            final Collection<OverlayLocation> patchesPositions, final Collection<IAmplifiableApplicableEffect> onWearEffects,
            final Collection<MobEffect> preventOnWearEffects, final Collection<IAuraEffect> auraEffects) {
    	final ObjModelReference[] models = new ObjModelReference[] {
    			new ObjModelReference(ModelType.ARMOR_CHESTPLATE, unlocName, transform.build())
    	};
    	return generateArmorSupplier0(ArmorItem.Type.CHESTPLATE,
    			unlocName, rarity,
                models, toughness,
                knockbackResistance, defenseValue, durabilityValue,
                patchesPositions, onWearEffects,
                preventOnWearEffects, auraEffects
            );
    }

    private static Supplier<BrimmArmor> generateArmorSupplier0(
    		final ArmorItem.Type type, final String unlocName, final BrimmRarity rarity,
            final ObjModelReference[] models, final float toughness,
            final float knockbackResistance, final int defenseValue, final int durabilityValue,
            final Collection<OverlayLocation> patchesPositions, final Collection<IAmplifiableApplicableEffect> onWearEffects,
            final Collection<MobEffect> preventOnWearEffects, final Collection<IAuraEffect> auraEffects) {
        ArmorConfig cfg = Optional.ofNullable(ConfigsManager.getAndEvict(unlocName)).orElse(ArmorConfig.EMPTY);
        final SimpleArmorMaterial material = ConfigMergers.mergeBasicMaterial("brimm_armor_material",
                toughness, knockbackResistance, defenseValue, durabilityValue,
                type, cfg.materialOverrides());
        final BrimmRarity mergedRarity = ConfigMergers.mergeRarity(rarity, cfg.rarityOverride());
        final IAmplifiableApplicableEffect[] onWearArr = (onWearEffects == null || onWearEffects.isEmpty()) ? null :
                onWearEffects.toArray(IAmplifiableApplicableEffect[]::new);
        final MobEffect[] onWearPrevArr = (preventOnWearEffects == null || preventOnWearEffects.isEmpty()) ? null :
                preventOnWearEffects.toArray(MobEffect[]::new);
        final IAuraEffect[] auraEffectsArr = (auraEffects == null || auraEffects.isEmpty()) ? null :
                auraEffects.toArray(IAuraEffect[]::new);
        return () -> new BrimmArmor(unlocName, type, mergedRarity, material, models, patchesPositions, onWearArr, onWearPrevArr, auraEffectsArr);
    }

    private static <T extends Item> RegistryObject<T> registerItemAndExecute(
            BiConsumer<RegistryObject<T>, String> consumer, String name, Supplier<? extends T> sup) {
        RegistryObject<T> registered = ITEMS.register(name, sup);
        consumer.accept(registered, name);
        return registered;
    }

    // --- PUBLICS ---

    public static <T extends Item> RegistryObject<T> registerAndAddToMiscTab(String name, Supplier<? extends T> sup) {
        RegistryObject<T> reg = ITEMS.register(name, sup);
        misc_tab_content.add(reg);
        return reg;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_TABS.register(eventBus);
    }
}