package io.github.otameshi.fc;

import java.util.function.Consumer;

import io.github.otameshi.fc.itr.FluentIterator;

class WrapedFluentIterable<T> extends AbstractFluentIterable<T> {
	private final Iterable<? extends T> original;

	WrapedFluentIterable(Iterable<? extends T> original) {
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