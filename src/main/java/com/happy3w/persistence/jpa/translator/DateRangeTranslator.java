package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.DateRangeFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateRangeTranslator extends AbstractStrDateRangeTranslator<DateRangeFilter> {

    protected DateRangeTranslator() {
        super(DateRangeFilter.class);
    }

    @Override
    protected LocalDateTime convertStrToDateTime(String strDate) {
        return LocalDate.parse(strDate).atStartOfDay();
    }

    @Override
    protected ChronoUnit unit() {
        return ChronoUnit.DAYS;
    }
}
