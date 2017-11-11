package io.github.otameshi.fc.itr;

import java.util.ListIterator;
import java.util.function.Function;

public interface FluentListIterator<E> extends FluentIterator<E>, ListIterator<E> {

	static <E> FluentListIterator<E> from(ListIterator<E> iterator) {
		if (iterator instanceof FluentListIterator) {
			return (FluentListIterator<E>) iterator;
		}
		return new FluentListIterator<E>() {

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

			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}

			@Override
			public E previous() {
				return iterator.previous();
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void set(E e) {
				iterator.set(e);
			}

			@Override
			public void add(E e) {
				iterator.add(e);
			}
		};
	}

	@SuppressWarnings("unchecked")
	static <E> FluentListIterator<E> empty() {
		return (FluentListIterator<E>) Internal.EMPTY_LITR;
	}

	@Override
	default <R> FluentListIterator<R> map(Function<? super E, ? extends R> mapper) {
		return new FluentListIterator<R>() {

			@Override
			public boolean hasNext() {
				return FluentListIterator.this.hasNext();
			}

			@Override
			public R next() {
				return mapper.apply(FluentListIterator.this.next());
			}

			@Override
			public void remove() {
				FluentListIterator.this.remove();
			}

			@Override
			public boolean hasPrevious() {
				return FluentListIterator.this.hasPrevious();
			}

			@Override
			public R previous() {
				return mapper.apply(FluentListIterator.this.previous());
			}

			@Override
			public int nextIndex() {
				return FluentListIterator.this.nextIndex();
			}

			@Override
			public int previousIndex() {
				return FluentListIterator.this.previousIndex();
			}

			@Override
			public void set(R e) {
				throw new UnsupportedOperationException();
			}

			@Override
			public void add(R e) {
				throw new UnsupportedOperationException();
			}
		};
	}
}