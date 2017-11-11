package io.github.otameshi.fc.itr;

import java.util.Collections;

class Internal {
	static final FluentIterator<?> EMPTY_ITR = FluentIterator.from(Collections.emptyIterator());
	static final FluentListIterator<?> EMPTY_LITR = FluentListIterator.from(Collections.emptyListIterator());
}
