package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {

        return DriverManager.getConnection(System.getProperty("db.url"), System.getProperty("db.username"), System.getProperty("db.password"));

    }

    @SneakyThrows
    public static void clearPaymentTable() {
        var deletePaymentEntity = "DELETE FROM payment_entity";
        try (var conn = getConn()) {
            runner.update(conn, deletePaymentEntity);
        }
    }

    @SneakyThrows
    public static void clearCreditTable() {
        val deleteCreditEntity = "DELETE FROM credit_request_entity";
        try (val conn = getConn()) {
            runner.update(conn, deleteCreditEntity);
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        val statusSQL = "SELECT status FROM payment_entity LIMIT 1";
        return getStatus(statusSQL);
    }

    @SneakyThrows
    public static String getCreditRequestStatus() {
        String query = "SELECT status FROM credit_request_entity LIMIT 1";
        return getStatus(query);
    }

    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = getConn()) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        }
    }
}