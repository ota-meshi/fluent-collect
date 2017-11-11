package io.github.otameshi.fc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.github.otameshi.fc.itr.FluentIterator;
import io.github.otameshi.fc.itr.FluentListIterator;

public interface FluentList<E> extends List<E>, FluentCollection<E> {
	static <E> FluentList<E> from(List<E> list) {
		if (list instanceof FluentList) {
			return (FluentList<E>) list;
		}
		return WrapedFluentList.from(list);
	}

	@SuppressWarnings("unchecked")
	static <E> FluentList<E> empty() {
		return (FluentList<E>) Internal.EMPTY_LIST;
	}

	@SafeVarargs
	static <E> FluentList<E> of(E... values) {
		E[] elements = Arrays.copyOf(values, values.length);
		return new AbstractFluentList<E>() {

			@Override
			public E get(int index) {
				return elements[index];
			}

			@Override
			public int size() {
				return elements.length;
			}
		};
	}

	@Override
	default FluentListIterator<E> listIterator() {
		return listIterator(0);
	}

	@Override
	FluentListIterator<E> listIterator(int index);

	@Override
	FluentList<E> subList(int fromIndex, int toIndex);

	default FluentIterator<E> reverseIterator() {
		ListIterator<E> listIterator = this.listIterator(size());
		return new FluentIterator<E>() {
			@Override
			public E next() {
				return listIterator.previous();
			}

			@Override
			public boolean hasNext() {
				return listIterator.hasPrevious();
			}
		};
	}

	@Override
	default Stream<E> stream() {
		return FluentCollection.super.stream();
	}

	@Override
	default <R> FluentList<R> map(Function<? super E, ? extends R> mapper) {
		return new AbstractFluentList<R>() {

			@Override
			public R get(int index) {
				return mapper.apply(FluentList.this.get(index));
			}

			@Override
			public int size() {
				return FluentList.this.size();
			}
		};
	}

	default FluentList<E> limit(long maxSize) {
		return new AbstractFluentList<E>() {

			@Override
			public E get(int index) {
				if (maxSize <= index) {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
				}
				return FluentList.this.get(index);
			}

			@Override
			public int size() {
				return Math.min((int) maxSize, FluentList.this.size());
			}
		};
	}

	default FluentList<E> skip(long n) {
		int i = (int) n;
		return new AbstractFluentList<E>() {

			@Override
			public E get(int index) {
				return FluentList.this.get(index + i);
			}

			@Override
			public int size() {
				return FluentList.this.size() - i;
			}
		};
	}

	default Optional<E> findLast() {
		Iterator<E> itr = reverseIterator();
		if (itr.hasNext()) {
			return Optional.of(itr.next());
		}
		return Optional.empty();
	}

	default Optional<E> findLast(Predicate<? super E> predicate) {
		Iterator<E> itr = reverseIterator();
		while (itr.hasNext()) {
			E e = itr.next();
			if (predicate.test(e)) {
				return Optional.of(e);
			}
		}
		return Optional.empty();
	}

	default OptionalInt findLastIndex(Predicate<? super E> predicate) {
		Iterator<E> itr = reverseIterator();
		int index = size() - 1;
		while (itr.hasNext()) {
			if (predicate.test(itr.next())) {
				return OptionalInt.of(index);
			}
			index--;
		}
		return OptionalInt.empty();
	}

}