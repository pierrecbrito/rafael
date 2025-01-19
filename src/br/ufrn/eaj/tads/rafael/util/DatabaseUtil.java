package br.ufrn.eaj.tads.rafael.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DatabaseUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/rafael?characterEncoding=UTF-8";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            AlertUtil.showAlert("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            	 AlertUtil.showAlert("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}

