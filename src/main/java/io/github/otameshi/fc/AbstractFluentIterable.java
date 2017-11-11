package io.github.otameshi.fc;

import java.util.stream.Collectors;

public abstract class AbstractFluentIterable<E> implements FluentIterable<E> {

	@Override
	public String toString() {
		return stream().map(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
	}

}