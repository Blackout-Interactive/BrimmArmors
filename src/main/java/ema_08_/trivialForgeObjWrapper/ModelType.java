package ema_08_.trivialForgeObjWrapper;

public enum ModelType {
	
	  ARMOR_HELMET("armors/helmets"),
	  ARMOR_CHESTPLATE("armors/chestplates"),
	  ARMOR_LEGGINGS("armors/leggings"),
	  ARMOR_BOOTS("armors/boots"),
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