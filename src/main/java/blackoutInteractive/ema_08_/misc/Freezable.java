package blackoutInteractive.ema_08_.misc;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Freezable<I, F> {
	
	/*
	 * All subclasses that instantiate with needsPreFreezingLock = true are expected to retrieve, interact with and modify the result
	 * of getPreFreezingData always under synchronization on the object returned by getPreFreezingLock.
	 * Similar rule applies to needsPostFreezingLock and the respective methods.
	 */
	
	private final Object l1, l2;
	private volatile I i;
	private volatile F f;
	private volatile boolean frozen = false;
	private final Function<I, F> freezer;
	
	protected Freezable(boolean needsPreFreezingLock, boolean needsPostFreezingLock, Function<I, F> f, Supplier<I> initialState) {
		if (needsPreFreezingLock) l1 = new Object();
		else l1 = null;
		if (needsPostFreezingLock) l2 = new Object();
		else l2 = null;
		freezer = Objects.requireNonNull(f, "A freezer function must be provided");
		i = Objects.requireNonNull(Objects.requireNonNull(initialState, "An initial state supplier must be provided")
				.get(), "Initial state cannot be null");
	}
	
	protected final Object getPreFreezingLock() {
		if (l1 == null) throw new IllegalStateException("This instance does not provide a pre-freezing lock");
		else return l1;
	}
	
	protected final Object getPostFreezingLock() {
		if (l2 == null) throw new IllegalStateException("This instance does not provide a post-freezing lock");
		else return l2;
	}
	
	private static void assertLockHeld(Object l, String lockName) {
		if (l != null && !Thread.holdsLock(l))
			throw new IllegalStateException(String.format("Lock %s must be held in order to perform this operation", lockName));
	}
	
	protected final I getPreFreezingData() {
		assertLockHeld(l1, "pre-freezing");
		if (frozen) throw new IllegalStateException("This instance is already frozen");
		else return i;
	}
	
	protected final F getPostFreezingData() {
		assertLockHeld(l2, "post-freezing");
		if (!frozen) throw new IllegalStateException("This instance is not yet frozen");
		else return f;
	}
	
	public final void freeze() {
		/*sync on new object is as much as no sync at all, despite a minor performance bottleneck */
		synchronized(l1 == null ? new Object() : l1) {
		synchronized(l2 == null ? new Object() : l2) {
			if (frozen) throw new IllegalStateException("This instance is already frozen");
			else {
				final F data = Objects.requireNonNull(freezer.apply(i), "Final state cannot be null");
				f = data;
				i = null;
				frozen = true;
			}
		} }
	}

}
