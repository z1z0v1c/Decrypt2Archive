package logger;

import databases.Database;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Date;

public class CompositeLogger {
    private Logger logger;
    private Database database;

    public CompositeLogger(Class clazz, Database database) {
        this.logger = LogManager.getLogger(clazz);
        this.database = database;
    }

    public void log(Level level, String action) throws SQLException {
        logger.log(level, action);
        database.log(action, new Date(System.currentTimeMillis()).toString());
    }
}
