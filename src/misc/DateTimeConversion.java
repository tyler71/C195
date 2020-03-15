package misc;

import dataModel.DataSourceSourceMySql;

import java.time.*;

public class DateTimeConversion {

    public ZonedDateTime parseMysqlSQLTime(String time) {
        ZonedDateTime converted = LocalDateTime.parse(time, DataSourceSourceMySql.dateTimeFormatter)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault());
        return converted;
    }
    public String convertToMysqlSqlTime(ZonedDateTime dateTimeObject) {
        ZonedDateTime utcZoned = dateTimeObject.withZoneSameInstant(ZoneOffset.UTC);
        String convertedTime = DataSourceSourceMySql.dateTimeFormatter.format(utcZoned);
        return convertedTime;
    }

}



