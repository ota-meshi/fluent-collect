package io.github.otameshi.fc;

import java.io.Serializable;
import java.util.function.Consumer;

import io.github.otameshi.fc.itr.FluentIterator;

class WrapedFluentIterable<T> extends AbstractFluentIterable<T> {
	private static class MarkSerializable<T> extends WrapedFluentIterable<T> implements java.io.Serializable {
		MarkSerializable(Iterable<? extends T> original) {
			super(original);
		}
	}

	static <T> WrapedFluentIterable<T> from(Iterable<? extends T> o) {
		if (o instanceof Serializable) {
			return new MarkSerializable<>(o);
		}
		return new WrapedFluentIterable<>(o);
	}

	private final Iterable<? extends T> original;

	protected WrapedFluentIterable(Iterable<? extends T> original) {
		this.original = original;
	}

	@Override
	public FluentIterator<T> iterator() {
		return FluentIterator.from(this.original.iterator());
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		original.forEach(action);
	}

}