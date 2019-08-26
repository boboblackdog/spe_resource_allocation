package com.example.spera_2;

import com.example.spera_2.utils_config.TestConnection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Spera2Application {

    public static void main(String[] args) throws SQLException {
        Logger logger = LoggerFactory.getLogger(Spera2Application.class);
        TestConnection tc = new TestConnection();
        try {
            logger.info("Testing Databases");
            logger.info("testing start...");
            logger.info("MySQL");
            if (!tc.test().getBoolean("SQL Connected")) {
                logger.error("MySQL not connected");
                System.exit(0);
            }
            logger.info("MySQL Ok");
            logger.info("MongoDB");
            if (!tc.test().getBoolean("MongoDB Connected")) {
                logger.error("MongoDB not connected");
                System.exit(0);
            }
            logger.info("MongoDB Ok");
            SpringApplication.run(Spera2Application.class, args);
        } catch (SQLException e) {
            logger.error("error: " + e.getMessage());
            if (!tc.ccExists()) {
                logger.error("MySQL config file DNE");
            }
            if (!tc.mcExists()) {
                logger.error("MongoDB config file DNE");
            }
        }
        
    }

}
