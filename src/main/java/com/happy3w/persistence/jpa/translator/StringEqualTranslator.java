package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.StringEqualFilter;

public class StringEqualTranslator extends AbstractEqualTranslator<String, StringEqualFilter> {
    protected StringEqualTranslator() {
        super(StringEqualFilter.class);
    }
}
