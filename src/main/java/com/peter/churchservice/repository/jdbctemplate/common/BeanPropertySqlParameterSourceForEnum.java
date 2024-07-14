package com.peter.churchservice.repository.jdbctemplate.common;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

public class BeanPropertySqlParameterSourceForEnum extends BeanPropertySqlParameterSource {

    /**
     * Create a new BeanPropertySqlParameterSource for the given bean.
     *
     * @param object the bean instance to wrap
     */
    public BeanPropertySqlParameterSourceForEnum(Object object) {
        super(object);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        Object value = super.getValue(paramName);
        if(value instanceof Enum enumValue) {
            return enumValue.name();
        }
        return value;
    }
}
