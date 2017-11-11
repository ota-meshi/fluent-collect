package io.github.otameshi.fc;

import java.io.Serializable;
import java.util.Set;

class WrapedFluentSet<E> extends WrapedFluentCollection<E> implements FluentSet<E> {
	private static class MarkSerializable<E> extends WrapedFluentSet<E> implements java.io.Serializable {
		MarkSerializable(Set<E> original) {
			super(original);
		}
	}

	static <E> WrapedFluentSet<E> from(Set<E> o) {
		if (o instanceof Serializable) {
			return new MarkSerializable<>(o);
		}
		return new WrapedFluentSet<>(o);
	}

	private WrapedFluentSet(Set<E> original) {
		super(original);
	}
}