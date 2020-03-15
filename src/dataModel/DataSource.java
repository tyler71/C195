package dataModel;

public class DataSource {
    private static IDataSource db;

    public DataSource() {
        db = new DataSourceSourceMySql();
    }

    public static IDataSource getDb() {
        return db;
    }
}
