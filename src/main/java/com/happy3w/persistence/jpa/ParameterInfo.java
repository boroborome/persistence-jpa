package com.happy3w.persistence.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.ParameterExpression;

@Getter
@Setter
public class ParameterInfo {
    private ParameterExpression parameterExpression;
    private Object value;
    private Class fieldType;
}
