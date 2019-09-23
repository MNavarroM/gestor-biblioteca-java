package biblioteca.funcionalidad;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigProperties {

	static Properties prop = new Properties();
	
	public static void load() throws FileNotFoundException, IOException {
		try(InputStream in = new FileInputStream("config.txt")) {
			prop.load(in);
			ConfigDatabase.setUserDB(prop.getProperty("dbuser"));
			ConfigDatabase.setPasswordDB(prop.getProperty("dbpassword"));
			ConfigDatabase.setNameDB(prop.getProperty("dbname"));
			ConfigDatabase.setHostDB(prop.getProperty("dbhost"));
			ConfigDatabase.setPortDB(Integer.parseInt(prop.getProperty("dbport")));
			ConfigDatabase.setConectorDB(prop.getProperty("dbconector"));
			ConfigDatabase.setDriverDB(prop.getProperty("dbdriver"));
			ConfigDatabase.setURL();
		}
	}
	
	public static void write(String dbuser, String dbpassword, String dbname, String dbhost, int dbport, String dbconector, String dbdriver)
			throws FileNotFoundException, IOException {
		try(OutputStream out = new FileOutputStream("config.txt")) {
			prop.load(new FileInputStream("config.txt"));
			prop.setProperty("dbuser", dbuser);
			prop.setProperty("dbpassword", dbpassword);
			prop.setProperty("dbname", dbname);
			prop.setProperty("dbhost", dbhost);
			prop.setProperty("dbport", String.valueOf(dbport));
			prop.setProperty("dbconector", dbconector);
			prop.setProperty("dbdriver", dbdriver);
			prop.store(out, "Última modificación");
		}
		
	}

}

