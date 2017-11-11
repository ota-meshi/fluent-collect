package io.github.otameshi.fc.itr;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface FluentIterator<E> extends Iterator<E> {

	@SuppressWarnings("unchecked")
	static <E> FluentIterator<E> from(Iterator<? extends E> iterator) {
		if (iterator instanceof FluentIterator) {
			return (FluentIterator<E>) iterator;
		}

		return new FluentIterator<E>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public E next() {
				return iterator.next();
			}

			@Override
			public void remove() {
				iterator.remove();
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <E> FluentIterator<E> empty() {
		return (FluentIterator<E>) Internal.EMPTY_ITR;
	}

	default FluentIterator<E> filter(Predicate<? super E> predicate) {
		return FluentIterator.from(stream().filter(predicate).iterator());
	}

	default <R> FluentIterator<R> map(Function<? super E, ? extends R> mapper) {
		return new FluentIterator<R>() {

			@Override
			public boolean hasNext() {
				return FluentIterator.this.hasNext();
			}

			@Override
			public R next() {
				return mapper.apply(FluentIterator.this.next());
			}

			@Override
			public void remove() {
				FluentIterator.this.remove();
			}
		};
	}

	default <R> FluentIterator<R> flatMap(Function<? super E, ? extends Iterator<? extends R>> mapper) {
		return FluentIterator.from(stream()
				.flatMap(e -> {
					Iterator<? extends R> i = mapper.apply(e);
					Spliterator<R> spliterator = Spliterators.spliteratorUnknownSize(i, 0);
					return StreamSupport.stream(spliterator, false);
				})
				.iterator());
	}

	default FluentIterator<E> distinct() {
		return FluentIterator.from(stream()
				.distinct()
				.iterator());
	}

	default FluentIterator<E> sorted(Comparator<? super E> comparator) {
		return FluentIterator.from(stream()
				.sorted(comparator)
				.iterator());
	}

	default Stream<E> stream() {
		Spliterator<E> spliterator = Spliterators.spliteratorUnknownSize(this, 0);
		return StreamSupport.stream(spliterator, false);
	}

}