package co.fanti.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MapperResult {


    default LocalDate extractLocalDate(ResultSet resultSet, String columnName) throws SQLException {

        var date = resultSet.getTimestamp(columnName);
        LocalDate localDate = null;

        if(!resultSet.wasNull()) {
            localDate = date.toLocalDateTime().toLocalDate();
        }

        return localDate;
    }

    default LocalDateTime extractLocalDateTime(ResultSet resultSet, String columnName) throws SQLException {

        var date = resultSet.getTimestamp(columnName);
        LocalDateTime localDate = null;

        if(!resultSet.wasNull()) {
            localDate = date.toLocalDateTime();
        }

        return localDate;
    }
}
