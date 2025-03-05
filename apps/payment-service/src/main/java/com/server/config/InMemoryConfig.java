package com.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class InMemoryConfig {
    @Autowired
    private DataSource dataSource;

    public void initDatabaseUsingSpring() {
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().execute("CREATE TABLE IF NOT EXISTS payments (id INTEGER PRIMARY KEY AUTOINCREMENT, payer_email VARCHAR(255) NOT NULL, currency VARCHAR(3) NOT NULL, amount INTEGER NOT NULL, status VARCHAR(10) NOT NULL DEFAULT 'PENDING' CHECK(status IN ('PENDING', 'PAID', 'FAILED')), created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, paid_date TIMESTAMP);");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
