package blackoutInteractive.ema_08_.rendering.obj;

public interface IMultiObjModelProvider {
	
	/*NOTE: implementations will share their own internal array for efficiency, thus IT IS NOT TO BE MODIFIED*/
	ObjModelReference[] getModelRefs();
}
