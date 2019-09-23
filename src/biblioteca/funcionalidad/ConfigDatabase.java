package biblioteca.funcionalidad;


public class ConfigDatabase {

	public static String USER_DATABASE =null;
	public static String PASSWORD_DATABASE =null;
	public static String DB_NAME=null;
	public static String DB_HOST=null;
	public static int DB_PORT=0;
	public static String DB_CONECTOR = null;
	public static String DRIVER=null;
	public static String URL=null;
	
	public static void setUserDB(String userdb){
		USER_DATABASE = userdb;
	}
	
	public static void setPasswordDB(String userdb){
		PASSWORD_DATABASE = userdb;
	}
	
	public static void setNameDB(String namedb){
		DB_NAME = namedb;
	}
	
	public static void setHostDB(String hostdb){
		DB_HOST = hostdb;
	}
	
	public static void setPortDB(int portdb){
		DB_PORT = portdb;
	}
	
	public static void setConectorDB(String conectordb){
		DB_CONECTOR = conectordb;
	}
	
	public static void setDriverDB(String driverdb){
		DRIVER = driverdb;
	}
	
	public static void setURL() {
		URL = "jdbc:"+DB_CONECTOR+"://"+DB_HOST+":"+DB_PORT+"/"+DB_NAME+"?autoReconnect=true&useSSL=false";
	}
}
