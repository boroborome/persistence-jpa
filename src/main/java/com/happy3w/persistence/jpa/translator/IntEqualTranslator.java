package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.IntEqualFilter;

public class IntEqualTranslator extends AbstractEqualTranslator<Integer, IntEqualFilter> {
    protected IntEqualTranslator() {
        super(IntEqualFilter.class);
    }
}
