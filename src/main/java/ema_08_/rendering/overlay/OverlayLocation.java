package ema_08_.rendering.overlay;

import java.util.Objects;

import ema_08_.rendering.geom.MatrixRTS;

public record OverlayLocation(
		OverlayPos pos,
		MatrixRTS localTransform
		) {
	
	public OverlayLocation {
		Objects.requireNonNull(pos, "Pos cannot be null");
		Objects.requireNonNull(localTransform, "Local transform cannot be null");
	}

}
