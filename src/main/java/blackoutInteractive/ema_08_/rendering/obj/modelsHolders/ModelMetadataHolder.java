package blackoutInteractive.ema_08_.rendering.obj.modelsHolders;

/*
 * No point in keeping generic typing class-level as all loaded models and/or declarations go in a common collection.
 */
sealed class ModelMetadataHolder permits ModelDeclaration, LoadedModel {
	
	private final Object metadata;
	
	protected <T> ModelMetadataHolder(T m) {
		metadata = m;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMetadata() {
		return (T)metadata;
	}
	
	/*
	 * Slower, even if safer. Kept for testing purposes so that stack trace is readable.
	 */
	@Deprecated
	public <T> T getMetadata(Class<T> type) {
	    return type.cast(metadata);
	}

}
