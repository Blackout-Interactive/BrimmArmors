package blackoutInteractive.ema_08_.rendering.obj;

public enum ModelType {
	
	  ARMOR_HELMET("armors/helmets"),
	  ARMOR_CHESTPLATE("armors/chestplates"),
	  ARMOR_LEGGINGS_RIGHT("armors/leggings"),
	  ARMOR_LEGGINGS_LEFT("armors/leggings"),
	  BLOCKS("blocks");
	    
	  public final String
	  		non_namespaced_resloc_obj_base,
	  		non_namespaced_resloc_mtl_base,
	  		non_namespaced_resloc_png_base;
	  
	  ModelType(String typeLoc) {
	    this.non_namespaced_resloc_obj_base = "models/obj/" + typeLoc + "/";
	    this.non_namespaced_resloc_mtl_base = "models/mtl/" + typeLoc + "/";
	    this.non_namespaced_resloc_png_base = "textures/models/obj/" + typeLoc + "/";
	  }
	  
}