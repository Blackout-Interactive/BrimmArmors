package blackoutInteractive.ema_08_.rendering.obj;

public enum ArmorModelType {
	
	HELMET("helmets/"),
	CHESTPLATE("chestplates/"),
	LEGGINGS_R("leggings/"),
	LEGGINGS_L("leggings/"),
	BOOTS_R("boots/"),
	BOOTS_L("boots/");
	
	protected final String locFolder;
	
	ArmorModelType(String lf) { locFolder = lf; }

}
