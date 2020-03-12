package dataModel;

public class DataSource {
    private static IDataSource db;

    public DataSource() {
        db = new DataSourceSourceMySql();
        db.openConnection();
    }

    public static IDataSource getDb() {
        return db;
    }
}
