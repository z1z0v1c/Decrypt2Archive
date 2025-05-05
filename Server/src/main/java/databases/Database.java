package databases;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

/// @author Aleksandar Zizovic
public interface Database extends Closeable {
    String selectKey(String file) throws SQLException;

    void log(String action, String date) throws SQLException;

    @Override
    void close() throws IOException;
}
