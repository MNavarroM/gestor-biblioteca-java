package biblioteca.funcionalidad;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupsDB {

	public static boolean backup() throws IOException {
		try (FileOutputStream fos = new FileOutputStream("backups\\biblioteca" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm")) + ".sql")) {
			Process p = Runtime.getRuntime().exec("mysqldump -u " + ConfigDatabase.USER_DATABASE + " -p"
					+ ConfigDatabase.PASSWORD_DATABASE + " " + ConfigDatabase.DB_NAME);
			InputStream is = p.getInputStream();
			byte[] buffer = new byte[1000];
			int leido = is.read(buffer);
			while (leido > 0) {
				fos.write(buffer, 0, leido);
				leido = is.read(buffer);
			}
			fos.close();
			return true;
		}
	}

	public static boolean restore(String fileSql) throws IOException {
		try (FileInputStream fis = new FileInputStream(fileSql)) {
			Process p = Runtime.getRuntime().exec(
					"mysql -u " + ConfigDatabase.USER_DATABASE + " -p" + ConfigDatabase.PASSWORD_DATABASE + " bibliotecajuan");
			OutputStream os = p.getOutputStream();
			byte[] buffer = new byte[1000];
			int leido = fis.read(buffer);
			while (leido > 0) {
				os.write(buffer, 0, leido);
				leido = fis.read(buffer);
			}
			os.flush();
			os.close();
			fis.close();
			return true;
		}
	}
	
}
