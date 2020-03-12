package dataModel;

public class DataSource {
    private static IDataSource db;

    private DataSource() {
        db = new DataSourceSourceMySql();
        db.openConnection();
    }

    public static IDataSource getDb() {
        return db;
    }
}
