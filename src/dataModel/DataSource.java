package dataModel;

public class DataSource {
    private static IDataSource db;
    private DataSource instance = new DataSource();

    private DataSource() {
        this.db = new DataSourceSourceMySql();
    }

    public static IDataSource getDb() {
        return db;
    }
}
