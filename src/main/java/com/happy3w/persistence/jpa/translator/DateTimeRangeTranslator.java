package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.DateTimeRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

public class DateTimeRangeTranslator extends AbstractRangeTranslator<DateTimeRangeFilter> {

    protected DateTimeRangeTranslator() {
        super(DateTimeRangeFilter.class);
    }

    @Override
    protected Object adjustStartValue(DateTimeRangeFilter filter, ParameterContext<?, ?> context) {
        return filter.getStart();
    }

    @Override
    protected Object adjustEndValue(DateTimeRangeFilter filter, ParameterContext<?, ?> context) {
        return filter.getEnd();
    }
}
