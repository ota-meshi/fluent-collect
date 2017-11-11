package io.github.otameshi.fc;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

import io.github.otameshi.fc.itr.FluentIterator;

public interface FluentCollection<E> extends Collection<E>, FluentIterable<E> {
	static <E> FluentCollection<E> from(Collection<E> collection) {
		if (collection instanceof FluentCollection) {
			return (FluentCollection<E>) collection;
		}
		return new WrapedFluentCollection<>(collection);
	}

	@SuppressWarnings("unchecked")
	static <E> FluentCollection<E> empty() {
		return (FluentCollection<E>) Internal.EMPTY_LIST;
	}

	@Override
	default Stream<E> stream() {
		return Collection.super.stream();
	}

	@Override
	default <R> FluentCollection<R> map(Function<? super E, ? extends R> mapper) {
		return new AbstractFluentCollection<R>() {

			@Override
			public FluentIterator<R> iterator() {
				return FluentCollection.this.iterator().map(mapper);
			}

			@Override
			public int size() {
				return FluentCollection.this.size();
			}
		};
	}

	@Override
	default long count() {
		return size();
	}

}