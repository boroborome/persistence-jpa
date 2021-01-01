package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.DateTimeRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import java.util.Date;

public class DateTimeRangeTranslator extends AbstractRangeTranslator<Date, DateTimeRangeFilter> {

    protected DateTimeRangeTranslator() {
        super(DateTimeRangeFilter.class);
    }

    @Override
    protected Object adjustStartValue(Date filterStart, ParameterContext<?, ?> context) {
        return filterStart;
    }

    @Override
    protected Object adjustEndValue(Date filterEnd, ParameterContext<?, ?> context) {
        return filterEnd;
    }
}
