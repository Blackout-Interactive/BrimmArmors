package brimmArmors.common.items;


import java.util.Arrays;

import net.minecraft.ChatFormatting;

public enum ConcordRarity {

    COMMON(ChatFormatting.GREEN, "common"),
    UNCOMMON(ChatFormatting.BLUE, "uncommon"),
    RARE(ChatFormatting.LIGHT_PURPLE, "rare"),
    EPIC(ChatFormatting.GOLD, "epic");

    private final ChatFormatting color;
    private final String name;

    ConcordRarity(ChatFormatting color, String name) {
        this.color = color;
        this.name = name;
    }
    
    public String applyFormatting(String in) { return this.color+in; }   
    public static ConcordRarity fromName(String name) {
    	return Arrays.stream(values()).filter((v)->v.name.equals(name)).findFirst().orElse(null);
    }

}
