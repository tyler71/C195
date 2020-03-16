package misc;

import dataModel.DataSource;
import dataModel.DataSourceSourceMySql;

import java.time.*;

public class DateTimeConversion {

    public ZonedDateTime parseMysqlSQLTime(String time) {
        ZonedDateTime converted = LocalDateTime.parse(time, DataSource.getDb().getDateTimeFormatter())
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault());
        return converted;
    }
    public String convertToMysqlSqlTime(ZonedDateTime dateTimeObject) {
        ZonedDateTime utcZoned = dateTimeObject.withZoneSameInstant(ZoneOffset.UTC);
        String convertedTime = DataSource.getDb().getDateTimeFormatter().format(utcZoned);
        return convertedTime;
    }

}



