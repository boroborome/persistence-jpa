package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.NumRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

public class NumRangeTranslator extends AbstractRangeTranslator<Number, NumRangeFilter<Number>> {

    protected NumRangeTranslator() {
        super((Class<NumRangeFilter<Number>>) (Class<?>) NumRangeFilter.class);
    }

    @Override
    protected Object adjustStartValue(Number filterStart, ParameterContext<?, ?> context) {
        return filterStart;
    }

    @Override
    protected Object adjustEndValue(Number filterEnd, ParameterContext<?, ?> context) {
        return filterEnd;
    }
}
