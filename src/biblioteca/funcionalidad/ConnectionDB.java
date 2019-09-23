package biblioteca.funcionalidad;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;

public class ConnectionDB {

	private static Connection conn;

	private static void loadDriver() throws LoadDriverException {
		try {
			Class.forName(ConfigDatabase.DRIVER);
		} catch (ClassNotFoundException e) {
			throw new LoadDriverException("Error al cargar el driver " + e.getMessage());
		}
	}

	private static void loadConnection() throws SQLErrorException {
		try {
			conn = (Connection) DriverManager.getConnection(ConfigDatabase.URL, ConfigDatabase.USER_DATABASE,
					ConfigDatabase.PASSWORD_DATABASE);
		} catch (SQLException e) {
			throw new SQLErrorException("Error al conectar: " + e.getMessage() + " - Error code: " + e.getErrorCode()
					+ "(" + e.getStackTrace() + ")");
		}
	}

	public static Connection getConnection() throws LoadDriverException, SQLErrorException {
		if (conn == null) {
			loadDriver();
			loadConnection();
		}
		return conn;
	}

	public static boolean testConnection() throws LoadDriverException, SQLErrorException {
		getConnection();
		return true;
	}

	public static void closeConnection() throws SQLErrorException {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				throw new SQLErrorException("Error al cerrar la conexión: " + e.getMessage() + " - Error code: "
						+ e.getErrorCode() + "(" + e.getStackTrace() + ")");
			}
		}
	}

}
