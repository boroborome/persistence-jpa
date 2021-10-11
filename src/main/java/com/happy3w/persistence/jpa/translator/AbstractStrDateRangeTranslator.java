package com.happy3w.persistence.jpa.translator;

import com.happy3w.java.ext.StringUtils;
import com.happy3w.persistence.core.filter.impl.AbstractStrDateRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.toolkits.utils.ZoneIdCache;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public abstract class AbstractStrDateRangeTranslator<FT extends AbstractStrDateRangeFilter> extends AbstractRangeTranslator<FT> {

    protected AbstractStrDateRangeTranslator(Class<FT> filterType) {
        super(filterType);
    }

    protected ZoneId chooseTimeZone(AbstractStrDateRangeFilter filter, ParameterContext<?, ?> context) {
        if (filter != null && StringUtils.hasText(filter.getZoneId())) {
            return ZoneIdCache.getZoneId(filter.getZoneId());
        }
        if (context != null && context.getZoneId() != null) {
            return context.getZoneId();
        }

        return ZoneId.systemDefault();
    }

    @Override
    protected Object adjustStartValue(FT filter, ParameterContext<?, ?> context) {
        String start = filter.getStart();
        if (StringUtils.isEmpty(start)) {
            return null;
        }

        ZoneId zoneId = chooseTimeZone(filter, context);
        ZonedDateTime localDate = convertStrToDateTime(start)
                .atZone(zoneId);
        if (!filter.isIncludeStart()) {
            localDate = localDate.plus(1, unit())
                    .minusNanos(1);
        }
        return Date.from(localDate.toInstant());
    }

    @Override
    protected Object adjustEndValue(FT filter, ParameterContext<?, ?> context) {
        String end = filter.getEnd();
        if (StringUtils.isEmpty(end)) {
            return null;
        }

        ZoneId zoneId = chooseTimeZone(filter, context);
        ZonedDateTime localDate = convertStrToDateTime(end)
                .atZone(zoneId);
        if (filter.isIncludeEnd()) {
            localDate = localDate.plus(1, unit())
                    .minusNanos(1);
        }
        return Date.from(localDate.toInstant());
    }

    protected abstract LocalDateTime convertStrToDateTime(String strDate);
    protected abstract ChronoUnit unit();
}
