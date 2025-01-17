package main.java.conexao;

import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

public final class ConnectionFactory {

    private static final String url;
    private static final String username;
    private static final String password;

    private ConnectionFactory() {}

    static {
        Properties props = new Properties();
        try {
            props.load(ConnectionFactory.class.getResourceAsStream("/db.properties"));
            url = props.getProperty("spring.datasource.url");
            username = props.getProperty("spring.datasource.username");
            password = props.getProperty("spring.datasource.password");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConn getConnection() {
        try {
            return new DBConn(DriverManager.getConnection(url, username, password));
        } catch (SQLException e) {
            throw new DAOException("Erro ao conectar com o Banco de Dados", e);
        }
    }
}

