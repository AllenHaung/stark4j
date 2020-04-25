package com.github.stark4j.mybatis.handler;

import com.github.stark4j.beans.enums.Stark4jEnums;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Allen Created 2020/4/19
 */
public abstract class AbstractEnumTypeHandler<T extends Enum<T>, V> extends BaseTypeHandler<Stark4jEnums<T, V>> {

    private Stark4jEnums<T, V> instance;

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int index, Stark4jEnums<T, V> parameter, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(index, parameter.getValue());
    }

}
