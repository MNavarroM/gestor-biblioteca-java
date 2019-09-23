package biblioteca.funcionalidad.excepciones;

public class SQLErrorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SQLErrorException(String mensaje) {
		super(mensaje);
	}
}
