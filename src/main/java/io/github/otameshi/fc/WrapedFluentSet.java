package io.github.otameshi.fc;

import java.util.Set;

class WrapedFluentSet<E> extends WrapedFluentCollection<E> implements FluentSet<E> {
	WrapedFluentSet(Set<E> original) {
		super(original);
	}
}