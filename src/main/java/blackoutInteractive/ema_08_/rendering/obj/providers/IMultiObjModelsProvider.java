package blackoutInteractive.ema_08_.rendering.obj.providers;

public interface IMultiObjModelsProvider {
	
	/*NOTE: implementations will share their own internal array for efficiency, thus IT IS NOT TO BE MODIFIED*/
	int[] getModelIds();

}
