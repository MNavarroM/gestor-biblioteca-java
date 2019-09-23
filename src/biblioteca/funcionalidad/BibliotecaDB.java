package biblioteca.funcionalidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;


public class BibliotecaDB {
	
	public static ResultSet getBooks() throws LoadDriverException, SQLErrorException{
			try {
				PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT nref, titulo , autores.anombre as autor , isbn,"
						+ "g.gnombre as genero,sg.gnombre as subgenero, editoriales.enombre as editorial ,localizaciones.lnombre as localizacion,"
						+ "edicion, encuadernacion, coleccion, idioma, leido, puntuacion, comentario, fechapublicacion, precio,lugarcompra.lcnombre,"
						+ "anyocompra, portada from libros left JOIN autores on autor = autores.aid left JOIN editoriales on editorial = editoriales.eid "
						+ "left JOIN generos as g on genero = g.gid left JOIN generos as sg on subgenero = sg.gid "
						+ "left JOIN localizaciones on localizacion = localizaciones.lid left JOIN lugarcompra on lugarcompra = lugarcompra.lcid order by nref");
				ResultSet resultSet = statement.executeQuery();
				return resultSet;
			} catch (SQLException e) {
				throw new SQLErrorException(e.getMessage() + "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
			}
	}
	
	public static ResultSet getBook(int nref) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT nref, titulo , autores.anombre as autor , isbn,"
					+ "g.gnombre as genero,sg.gnombre as subgenero, editoriales.enombre as editorial ,localizaciones.lnombre as localizacion, edicion, encuadernacion,"
					+ "coleccion, idioma, leido, puntuacion, comentario, fechapublicacion, precio,lugarcompra.lcnombre as lugarcompra, anyocompra,"
					+ "portada from libros left JOIN autores on autor = autores.aid left JOIN editoriales on editorial = editoriales.eid "
					+ "left JOIN generos as g on genero = g.gid left JOIN generos as sg on subgenero = sg.gid left JOIN localizaciones on localizacion = localizaciones.lid "
					+ "left JOIN lugarcompra on lugarcompra = lugarcompra.lcid where nref=?");
			statement.setInt(1, nref);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() + "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
		
	public static void insertBook(Book book) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO libros(titulo,isbn,editorial,precio,anyocompra,"
					+ "puntuacion,edicion, idioma,leido,comentario,genero,subgenero,fechapublicacion,autor,"
					+ "localizacion,lugarcompra,encuadernacion,coleccion,portada,fechaintroduccion) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");		
			statement.setString(1,book.getTitle());
			statement.setString(2, book.getIsbn());
			if(book.getEditorial()==null)
				statement.setNull(3, Types.INTEGER);
			else
				statement.setInt(3, getIdEditorial(book.getEditorial()));
			if(book.getPrice()==null)
				statement.setNull(4, Types.DOUBLE);
			else				
				statement.setDouble(4, book.getPrice());
			statement.setDate(5, book.getDateBuy());
			statement.setInt(6, book.getPuntuation());
			statement.setString(7, book.getEdition());
			statement.setString(8, book.getLanguage());	
			statement.setString(9, book.getRead());
			statement.setString(10, book.getComment());
			if(book.getGenre()==null)
				statement.setNull(11, Types.INTEGER);
			else
				statement.setInt(11, getIdGenre(book.getGenre()));
			if(book.getSubgenre()==null)
				statement.setNull(12, Types.INTEGER);
			else
				statement.setInt(12, getIdGenre(book.getSubgenre()));
			if(book.getYearPublication()==null)
				statement.setNull(13, Types.INTEGER);
			else
				statement.setInt(13, book.getYearPublication());
			if(book.getAuthor()==null)
				statement.setNull(14, Types.INTEGER);
			else
				statement.setInt(14, getIdAuthor(book.getAuthor()));
			if(book.getLocation()==null)
				statement.setNull(15, Types.INTEGER);
			else
				statement.setInt(15, getIdLocation(book.getLocation()));
			if(book.getShopPlace()==null)
				statement.setNull(16, Types.INTEGER);
			else
				statement.setInt(16, getIdShopPlace(book.getShopPlace()));
			statement.setString(17, book.getBookBinding());
			statement.setString(18, book.getCollection());
			statement.setString(19, book.getUrlCover());
			statement.setDate(20, java.sql.Date.valueOf(LocalDate.now()));
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}	
	}
	
	public static void updateBook(int nref, Book book) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE bibliotecajuan.libros SET titulo = ?, isbn = ?,"
					+ "editorial = ?,precio = ?, anyocompra = ?,puntuacion = ?, edicion = ?, idioma = ?, leido = ?, comentario = ?, genero = ?, subgenero = ?,"
					+ "fechapublicacion = ?, autor = ?, localizacion = ?,  lugarcompra = ?,"
							+ " encuadernacion = ?, coleccion = ?, portada = ? WHERE libros.nref =?");		
			statement.setString(1,book.getTitle());
			statement.setString(2, book.getIsbn());
			if(book.getEditorial()==null)
				statement.setNull(3, Types.INTEGER);
			else
				statement.setInt(3, getIdEditorial(book.getEditorial()));
			if(book.getPrice()==null)
				statement.setNull(4, Types.DOUBLE);
			else				
				statement.setDouble(4, book.getPrice());
			statement.setDate(5, book.getDateBuy());
			statement.setInt(6, book.getPuntuation());
			statement.setString(7, book.getEdition());
			statement.setString(8, book.getLanguage());	
			statement.setString(9, book.getRead());
			statement.setString(10, book.getComment());
			if(book.getGenre()==null)
				statement.setNull(11, Types.INTEGER);
			else 
				statement.setInt(11, getIdGenre(book.getGenre()));			
			if(book.getSubgenre()==null)
				statement.setNull(12, Types.INTEGER);
			else
				statement.setInt(12, getIdGenre(book.getSubgenre()));
			if(book.getYearPublication()==null)
				statement.setNull(13, Types.INTEGER);
			else
				statement.setInt(13, book.getYearPublication());
			if(book.getAuthor()==null)
				statement.setNull(14, Types.INTEGER);
			else
				statement.setInt(14, getIdAuthor(book.getAuthor()));
			if(book.getLocation()==null)
				statement.setNull(15, Types.INTEGER);
			else
				statement.setInt(15, getIdLocation(book.getLocation()));
			if(book.getShopPlace()==null)
				statement.setNull(16, Types.INTEGER);
			else
				statement.setInt(16, getIdShopPlace(book.getShopPlace()));
			statement.setString(17, book.getBookBinding());
			statement.setString(18, book.getCollection());
			statement.setString(19, book.getUrlCover());
			statement.setInt(20, nref);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteBook(int nref) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM LIBROS where nref=?;");
			statement.setInt(1, nref);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteAuthor(int id) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM AUTORES where aid=?;");
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteGenre(int id) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM GENEROS where gid=?;");
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteEditorial(int id) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM EDITORIALES where eid=?;");
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteLocation(int id) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM LOCALIZACIONES where lid=?;");
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void deleteShopPlace(int id) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("DELETE FROM LUGARCOMPRA where lcid=?;");
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void insertAuthor(String name) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO AUTORES (anombre) values (?) ");
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	
	public static void insertGenre(String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO GENEROS (gnombre) values (?) ");
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	
	public static void insertEditorial(String name) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO EDITORIALES (enombre) values (?) ");
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	
	public static void insertLocation(String name) throws SQLErrorException, LoadDriverException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO LOCALIZACIONES (lnombre) values (?) ");
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	
	public static void insertShopPlace(String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("INSERT INTO LUGARCOMPRA (lcnombre) values (?) ");
			statement.setString(1, name);
			statement.execute();
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getGenres() throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from generos");
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getAuthors() throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from autores");
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getEditorials() throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from editoriales");
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getLocations() throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from localizaciones");
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getShopPlaces() throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from lugarcompra");
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getAuthor(int id) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("Select * from autores where aid=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static ResultSet getEditorial(int id) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("Select * from editoriales where eid=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}		
	}
	
	public static ResultSet getLocation(int id) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("Select * from localizaciones where lid=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}		
	}
	
	public static ResultSet getGenre(int id) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("Select * from generos where gid=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}		
	}
	
	public static ResultSet getShopPlace(int id) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("Select * from lugarcompra where lcid=?");
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}		
	}
	
	public static int getIdGenre(String name) throws LoadDriverException, SQLErrorException{
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("SELECT * from generos where gnombre=?;");
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt("gid");
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static int getIdAuthor(String name) throws LoadDriverException, SQLErrorException{
		try {
			Statement statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT aid from autores where anombre='"+name+"';");
			resultSet.next();
			return resultSet.getInt("aid");
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static int getIdEditorial(String name) throws LoadDriverException, SQLErrorException{
		try {
			Statement statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from editoriales where enombre='"+name+"';");
			resultSet.next();
			return resultSet.getInt("eid");
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static int getIdLocation(String name) throws LoadDriverException, SQLErrorException{
		try {
			Statement statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from localizaciones where lnombre='"+name+"';");
			resultSet.next();
			return resultSet.getInt("lid");
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static int getIdShopPlace(String name) throws LoadDriverException, SQLErrorException{
		try {
			Statement statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * from lugarcompra where lcnombre='"+name+"';");
			resultSet.next();
			return resultSet.getInt("lcid");
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}	
		
	public static boolean existsAuthor(String name) throws LoadDriverException, SQLErrorException {
		try {
			ResultSet autores= getAuthors();
			while(autores.next())
				if(autores.getString("anombre").equalsIgnoreCase(name))
					return true;
			return false;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static boolean existsGenre(String name) throws LoadDriverException, SQLErrorException {
		try {
			ResultSet autores= getGenres();
			while(autores.next())
				if(autores.getString("gnombre").equalsIgnoreCase(name))
					return true;
			return false;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static boolean existsEditorial(String name) throws LoadDriverException, SQLErrorException {
		try {
			ResultSet autores= getEditorials();
			while(autores.next())
				if(autores.getString("enombre").equalsIgnoreCase(name))
					return true;
			return false;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static boolean existsLocation(String name) throws LoadDriverException, SQLErrorException {
		try {
			ResultSet autores= getLocations();
			while(autores.next())
				if(autores.getString("lnombre").equalsIgnoreCase(name))
					return true;
			return false;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static boolean existsShopPlace(String name) throws LoadDriverException, SQLErrorException {
		try {
			ResultSet autores= getShopPlaces();
			while(autores.next())
				if(autores.getString("lcnombre").equalsIgnoreCase(name))
					return true;
			return false;
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
	}
	
	public static void updateAutor(int id, String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE AUTORES SET anombre=? WHERE aid=?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
	}
	
	public static void updateEditorial(int id, String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE EDITORIALES set enombre=? WHERE eid=?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
	}
	
	public static void updateGenre(int id, String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE GENEROS set gnombre=? WHERE gid=?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
	}
	
	public static void updateLocation(int id, String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE LOCALIZACIONES set lnombre=? WHERE lid=?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
	}
	
	public static void updateShopPlace(int id, String name) throws LoadDriverException, SQLErrorException {
		try {
			PreparedStatement statement = (PreparedStatement) ConnectionDB.getConnection().prepareStatement("UPDATE LUGARCOMPRA set lcnombre=? WHERE lcid=?");
			statement.setString(1, name);
			statement.setInt(2, id);
			statement.execute();
		} catch (SQLException e) {		
			e.printStackTrace();
		}		
	}
	
	public static int getLastIdAuthor() throws LoadDriverException, SQLErrorException {
		Statement statement;
		try {
			statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT max(aid) from autores;");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
}
	
	public static int getLastIdEditorial() throws LoadDriverException, SQLErrorException {
		Statement statement;
		try {
			statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT max(eid) from editoriales;");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
}
	
	public static int getLastIdGenre() throws LoadDriverException, SQLErrorException {
		Statement statement;
		try {
			statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT max(gid) from generos;");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
}
	
	public static int getLastIdLocationss() throws LoadDriverException, SQLErrorException {
		Statement statement;
		try {
			statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT max(lid) from localizaciones;");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
}
	
	public static int getLastIdShopPlace() throws LoadDriverException, SQLErrorException {
		Statement statement;
		try {
			statement = (Statement) ConnectionDB.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT max(lcid) from lugarcompra;");
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
		}
}
	
	public static int getLastIdBook() throws LoadDriverException, SQLErrorException {
			Statement statement;
			try {
				statement = (Statement) ConnectionDB.getConnection().createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT max(nref) from libros;");
				resultSet.next();
				return resultSet.getInt(1);
			} catch (SQLException e) {
				throw new SQLErrorException(e.getMessage() +  "\nError Code: " + e.getErrorCode() + "("+e.getSQLState()+")");
			}
	}
	
}
