package misc;

import dataModel.DataSourceSourceMySql;

import java.time.*;

public class DateTimeConversion {

    public static ZonedDateTime parseMysqlSQLTime(String time) {
        ZonedDateTime converted = LocalDateTime.parse(time, DataSourceSourceMySql.mysqlDateTimeFormatter)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault());
        return converted;
    }
    public static String convertToMysqlSqlTime(ZonedDateTime dateTimeObject) {
        ZonedDateTime utcZoned = dateTimeObject.withZoneSameInstant(ZoneOffset.UTC);
        String convertedTime = DataSourceSourceMySql.mysqlDateTimeFormatter.format(utcZoned);
        return convertedTime;
    }

}



