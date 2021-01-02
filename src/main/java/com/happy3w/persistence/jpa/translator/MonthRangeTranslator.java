package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.MonthRangeFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MonthRangeTranslator extends AbstractStrDateRangeTranslator<MonthRangeFilter> {

    protected MonthRangeTranslator() {
        super(MonthRangeFilter.class);
    }

    @Override
    protected LocalDateTime convertStrToDateTime(String strDate) {
        return LocalDate.parse(strDate + "-01").atStartOfDay();
    }

    @Override
    protected ChronoUnit unit() {
        return ChronoUnit.MONTHS;
    }
}
