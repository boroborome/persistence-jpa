package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.NumRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

public class NumRangeTranslator extends AbstractRangeTranslator<NumRangeFilter> {

    protected NumRangeTranslator() {
        super(NumRangeFilter.class);
    }

    @Override
    protected Object adjustStartValue(NumRangeFilter filter, ParameterContext<?, ?> context) {
        return filter.getStart();
    }

    @Override
    protected Object adjustEndValue(NumRangeFilter filter, ParameterContext<?, ?> context) {
        return filter.getEnd();
    }
}
