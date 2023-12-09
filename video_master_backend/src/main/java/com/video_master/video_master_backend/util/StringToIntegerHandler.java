package com.video_master.video_master_backend.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class StringToIntegerHandler extends BaseTypeHandler<String> {
    // 使用类型转换器，将需要转换的String类型转换成Integer类型
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, Integer.parseInt(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return String.valueOf(resultSet.getInt(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return String.valueOf(resultSet.getInt(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return String.valueOf(callableStatement.getInt(i));
    }
}
