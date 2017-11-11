package io.github.otameshi.fc;

import java.util.Collections;

import io.github.otameshi.fc.itr.FluentIterator;

class Internal {
	static final FluentIterable<?> EMPTY_ITERABLE = () -> FluentIterator.empty();
	static final FluentList<?> EMPTY_LIST = FluentList.from(Collections.emptyList());
}
