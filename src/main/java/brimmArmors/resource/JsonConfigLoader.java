package brimmArmors.resource;

import com.google.gson.*;

import brimmArmors.BrimmArmors;
import brimmArmors.common.items.BasicArmor;
import brimmArmors.common.items.ConcordArmorMaterial;
import brimmArmors.common.items.ItemRegistry;
import brimmArmors.resource.dummies.Transform;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.RegistryObject;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class JsonConfigLoader {
    public static final Supplier<File> CONFIG_PATH = () -> FMLPaths.CONFIGDIR.get().resolve("concord_armors.json").toFile();
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ConcordArmorMaterial.class, new ConcordMaterialSerializer())
            .registerTypeAdapter(BasicArmor.class, new ConcordArmorSerializer())
            .registerTypeAdapter(Transform.Matrix.class, new brimmArmors.resource.MatrixSerializer())
            .registerTypeAdapter(Transform.class, new brimmArmors.resource.TransformSerializer())
            .setPrettyPrinting()
            .create();

    private static JsonObject createDefaultConfig() throws IOException {
        InputStream stream = BrimmArmors.class.getResourceAsStream("/default-config.json");
        String jsRaw = IOUtils.toString(stream);
        return GSON.fromJson(jsRaw, JsonObject.class);
    }

    public static void init() {
        File configFile = CONFIG_PATH.get();
        if(!configFile.exists())
            try {
                Files.createFile(configFile.toPath());
                Files.write(configFile.toPath(), GSON.toJson(createDefaultConfig()).getBytes());

                readConfig(configFile);
            } catch (IOException ignored) {
                BrimmArmors.LOGGER.error("Failed to write default config to {}, defaulting values without writing!", configFile.getAbsolutePath());
            }
        else {
            try {
                readConfig(configFile);
            } catch (FileNotFoundException e) {
                BrimmArmors.LOGGER.error("Failed to find config {}, this should not be happening! Contact Corrineduck on discord or Jeducklet on CurseForge!", configFile.getAbsolutePath());
            }
        }
    }

    private static void readConfig(File configFile) throws FileNotFoundException {
        JsonObject obj = GSON.fromJson(new FileReader(configFile), JsonObject.class);
        JsonArray armors = obj.getAsJsonArray("armors");
        List<RegistryObject<BasicArmor>> registered = new ArrayList<>();
        for (JsonElement armor : armors) {
            Supplier<BasicArmor> basic = ()-> GSON.fromJson(armor, BasicArmor.class);
            var reg = ItemRegistry.ITEMS.register(armor.getAsJsonObject().get("id").getAsString(), basic);
            registered.add(reg);
        }
        ItemRegistry.CREATIVE_TABS.register("concord_armors", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + BrimmArmors.MOD_ID + ".concord_armors"))
                .icon(() -> new ItemStack(registered.stream().map(RegistryObject::get).findFirst().orElseThrow()))
                .displayItems((params, output) -> {
                	registered.stream().map(RegistryObject::get).forEach(output::accept);
                })
                .build());
    }

}
