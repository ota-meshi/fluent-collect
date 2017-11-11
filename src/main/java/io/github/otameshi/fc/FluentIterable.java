package io.github.otameshi.fc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import io.github.otameshi.fc.itr.FluentIterator;

public interface FluentIterable<T> extends Iterable<T> {

	@SuppressWarnings("unchecked")
	static <T> FluentIterable<T> from(Iterable<? extends T> iterable) {
		if (iterable instanceof FluentIterable) {
			return (FluentIterable<T>) iterable;
		}
		return new WrapedFluentIterable<>(iterable);
	}

	static <T> FluentIterable<T> asIterable(Supplier<? extends Iterator<T>> itarator) {
		return from(() -> itarator.get());
	}

	@SuppressWarnings("unchecked")
	static <T> FluentIterable<T> empty() {
		return (FluentIterable<T>) Internal.EMPTY_ITERABLE;
	}

	@SafeVarargs
	static <T> FluentIterable<T> of(T... values) {
		return from(Arrays.asList(values));
	}

	@Override
	FluentIterator<T> iterator();

	default FluentIterable<T> filter(Predicate<? super T> predicate) {
		return new AbstractFluentIterable<T>() {
			@Override
			public FluentIterator<T> iterator() {
				return FluentIterable.this.iterator().filter(predicate);
			}
		};
	}

	default <R> FluentIterable<R> map(Function<? super T, ? extends R> mapper) {
		return new AbstractFluentIterable<R>() {
			@Override
			public FluentIterator<R> iterator() {
				return FluentIterable.this.iterator().map(mapper);
			}
		};
	}

	default <R> FluentIterable<R> flatMap(Function<? super T, ? extends Iterable<? extends R>> mapper) {
		return new AbstractFluentIterable<R>() {
			@Override
			public FluentIterator<R> iterator() {
				return FluentIterator.from(FluentIterable.this.stream()
						.flatMap(e -> {
							Iterable<? extends R> i = mapper.apply(e);
							if (i instanceof Collection) {
								return ((Collection<? extends R>) i).stream();
							} else {
								return StreamSupport.stream(i.spliterator(), false);
							}
						})
						.iterator());
			}
		};
	}

	default FluentSet<T> distinct() {
		return new AbstractFluentSet<T>() {

			@Override
			public FluentIterator<T> iterator() {
				return FluentIterable.this.iterator().distinct();
			}

			@Override
			public int size() {
				return (int) iterator().stream().count();
			}
		};
	}

	default FluentIterable<T> sorted(Comparator<? super T> comparator) {
		return new AbstractFluentIterable<T>() {
			@Override
			public FluentIterator<T> iterator() {
				return FluentIterable.this.iterator().sorted(comparator);
			}
		};
	}

	default Stream<T> stream() {
		return StreamSupport.stream(spliterator(), false);
	}

	default <A> A[] toArray(IntFunction<A[]> generator) {
		return stream().toArray(generator);
	}

	default T reduce(T identity, BinaryOperator<T> accumulator) {
		return stream().reduce(identity, accumulator);
	}

	default Optional<T> reduce(BinaryOperator<T> accumulator) {
		return stream().reduce(accumulator);
	}

	default <U> U reduce(U identity,
			BiFunction<U, ? super T, U> accumulator,
			BinaryOperator<U> combiner) {
		return stream().reduce(identity, accumulator, combiner);
	}

	default <R> R collect(Supplier<R> supplier,
			BiConsumer<R, ? super T> accumulator,
			BiConsumer<R, R> combiner) {
		return stream().collect(supplier, accumulator, combiner);
	}

	default <R, A> R collect(Collector<? super T, A, R> collector) {
		return stream().collect(collector);
	}

	default Optional<T> min(Comparator<? super T> comparator) {
		Iterator<T> itr = iterator();
		if (!itr.hasNext()) {
			return Optional.empty();
		}
		T min = itr.next();
		while (itr.hasNext()) {
			T e = itr.next();
			if (comparator.compare(min, e) < 0) {
				min = e;
			}
		}
		return Optional.of(min);
	}

	default Optional<T> max(Comparator<? super T> comparator) {
		Iterator<T> itr = iterator();
		if (!itr.hasNext()) {
			return Optional.empty();
		}
		T max = itr.next();
		while (itr.hasNext()) {
			T e = itr.next();
			if (comparator.compare(max, e) > 0) {
				max = e;
			}
		}
		return Optional.of(max);
	}

	default long count() {
		Iterator<T> itr = iterator();
		int count = 0;
		while (itr.hasNext()) {
			itr.next();
			count++;
		}
		return count;
	}

	default boolean anyMatch(Predicate<? super T> predicate) {
		return stream().anyMatch(predicate);
	}

	default boolean allMatch(Predicate<? super T> predicate) {
		return stream().allMatch(predicate);
	}

	default boolean noneMatch(Predicate<? super T> predicate) {
		return stream().noneMatch(predicate);
	}

	default Optional<T> findFirst() {
		Iterator<T> itr = iterator();
		if (itr.hasNext()) {
			return Optional.of(itr.next());
		}
		return Optional.empty();
	}

	default Optional<T> findFirst(Predicate<? super T> predicate) {
		Iterator<T> itr = iterator();
		while (itr.hasNext()) {
			T e = itr.next();
			if (predicate.test(e)) {
				return Optional.of(e);
			}
		}
		return Optional.empty();
	}

	default OptionalInt findFirstIndex(Predicate<? super T> predicate) {
		Iterator<T> itr = iterator();
		int index = 0;
		while (itr.hasNext()) {
			if (predicate.test(itr.next())) {
				return OptionalInt.of(index);
			}
			index++;
		}
		return OptionalInt.empty();
	}

}