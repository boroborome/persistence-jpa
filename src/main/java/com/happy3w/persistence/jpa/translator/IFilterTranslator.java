package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import lombok.Getter;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Getter
public abstract class IFilterTranslator<FT extends IFilter> {
    protected final Class<FT> filterType;

    protected IFilterTranslator(Class<FT> filterType) {
        this.filterType = filterType;
    }

    public abstract List<Predicate> toPredicate(
            FT filter,
            ParameterContext<?, ?> context);
}
