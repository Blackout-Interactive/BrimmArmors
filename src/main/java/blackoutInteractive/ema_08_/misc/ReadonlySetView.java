package blackoutInteractive.ema_08_.misc;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

//TODO eventually optimize this better

public interface ReadonlySetView<T> extends Iterable<T> {
	
	boolean contains(T obj);
	
	static <T> ReadonlySetView<T> of(T o) {
		return new ReadonlySetView<>() {

			@Override
			public Iterator<T> iterator() {
				return new Iterator<>() {
					
					boolean done;

					@Override
					public boolean hasNext() {
						return !done;
					}

					@Override
					public T next() {
						if (done) throw new NoSuchElementException();
						done = true;
						return o;
					}
					
				};
			}

			@Override
			public boolean contains(T obj) {
				return Objects.equals(o, obj);
			}
			
		};
	}
	
	static <T> ReadonlySetView<T> of(Set<T> set) {
		Objects.requireNonNull(set, "set");
		return new ReadonlySetView<>() {

			@Override
			public Iterator<T> iterator() {
				final var iter0 = set.iterator();
				return new Iterator<>() {

					@Override
					public boolean hasNext() {
						return iter0.hasNext();
					}

					@Override
					public T next() {
						return iter0.next();
					}
					
				};
			}

			@Override
			public boolean contains(T obj) {
				return set.contains(obj);
			}
			
		};
	}

}
