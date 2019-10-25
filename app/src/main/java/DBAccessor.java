import java.sql.Connection;
import java.sql.ResultSet;

public class DBAccessor
{
    private String db_username;
    private String db_password;
    private String db_url;
    private String jdbcDriverPath;
    private Connection db_connection;

    /**The constructor will open the connection to the database and store the
     * connection in the DB_Connection variable
     * */
    public DBAccessor()
    {

    }
    public ResultSet executeQuery(String query)
    {
        return null;
    }
}


