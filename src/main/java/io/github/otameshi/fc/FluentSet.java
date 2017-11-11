package io.github.otameshi.fc;

import java.util.Set;
import java.util.stream.Stream;

import io.github.otameshi.fc.itr.FluentIterator;

public interface FluentSet<E> extends Set<E>, FluentCollection<E> {
	static <E> FluentSet<E> from(Set<E> set) {
		if (set instanceof FluentSet) {
			return (FluentSet<E>) set;
		}
		return new WrapedFluentSet<>(set);
	}

	@SuppressWarnings("unchecked")
	static <E> FluentSet<E> empty() {
		return (FluentSet<E>) Internal.EMPTY_LIST;
	}

	@SafeVarargs
	static <E> FluentSet<E> of(E... values) {
		//TODO check
		FluentIterable<E> iterable = FluentIterable.of(values);
		return new AbstractFluentSet<E>() {

			@Override
			public FluentIterator<E> iterator() {
				return iterable.iterator();
			}

			@Override
			public int size() {
				return values.length;
			}
		};
	}

	@Override
	default Stream<E> stream() {
		return FluentCollection.super.stream();
	}
}