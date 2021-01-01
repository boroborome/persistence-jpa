package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import lombok.Getter;

import javax.persistence.criteria.Predicate;

@Getter
public abstract class AbstractFilterTranslator<FT extends IFilter> {
    protected final Class<FT> filterType;

    protected AbstractFilterTranslator(Class<FT> filterType) {
        this.filterType = filterType;
    }

    public abstract Predicate translate(FT filter, ParameterContext<?, ?> context);
}
