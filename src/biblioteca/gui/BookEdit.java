package biblioteca.gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import biblioteca.funcionalidad.BibliotecaDB;
import biblioteca.funcionalidad.IconUtils;
import biblioteca.funcionalidad.Book;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.PriceException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;
import biblioteca.funcionalidad.excepciones.TituloVacioException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

public class BookEdit extends AddBook {

	private static final long serialVersionUID = 1L;
	protected ResultSet book;
	private JButton btnActualizar;
	private JButton btnEliminar;
	
	public BookEdit(int nref) {
		super();
		textFieldNRef.setText(String.valueOf(nref));
		okButton.setVisible(false);
		getBook(nref);
		
		setTitle("Editar libro");
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteBook();
			}
		});
		btnEliminar.setBounds(400, 11, 89, 23);
		contentPanel.add(btnEliminar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateBook();
			}
		});
		buttonPane.add(btnActualizar, 0);
		getRootPane().setDefaultButton(btnActualizar);
		setData();

	}

	private void setData() {
		try {
			if (book.getString("portada") != null) {
				lblPortada.setIcon(IconUtils.createIcon(book.getString("portada"), panelPortada));
				panelPortada.add(lblPortada);
			}
			textFieldTitulo.setText(book.getString("titulo"));
			textFieldISBN.setText(book.getString("isbn"));
			comboBoxGenero.setSelectedItem(book.getString("genero"));
			comboBoxSubgenero.setSelectedItem(book.getString("subgenero"));
			comboBoxAutor.setSelectedItem(book.getString("autor"));
			comboBoxEditorial.setSelectedItem(book.getString("editorial"));
			comboBoxLocalizacion.setSelectedItem(book.getString("localizacion"));
			comboBoxIdiomas.setSelectedItem(book.getString("idioma"));
			textFieldEdicion.setText(book.getString("edicion"));
			spinnerPuntuacion.setValue(book.getInt("puntuacion"));
			comboBoxLeido.setSelectedItem(book.getString("leido"));
			comboBoxEncuadernacion.setSelectedItem(book.getString("encuadernacion"));
			textAreaComentario.setText(book.getString("comentario"));
			comboBoxColeccion.setSelectedItem(book.getString("coleccion"));
			spinnerPrecio.setValue(book.getDouble("precio"));
			comboBoxLugarCompra.setSelectedItem(book.getString("lugarcompra"));
			if (book.getDate("anyocompra") == null)
				chckbxSinFecha.setSelected(true);
			else
				spinnerFechaCompra.setValue(book.getDate("anyocompra"));
			lblSelectedFile.setText(book.getString("portada"));
		} catch (SQLException e) {
			
		} catch (IOException e) {
		}
	}

	private void getBook(int nref) {
		try {
			book = BibliotecaDB.getBook(nref);
			book.next();
		} catch (LoadDriverException e) {
			e.printStackTrace();
		} catch (SQLErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteBook() {
		try {
			if (JOptionPane.showConfirmDialog(getContentPane(),
					"¿Desea eliminar el libro " + textFieldTitulo.getText() + " ?", "Eliminar libro?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				BibliotecaDB.deleteBook(Integer.valueOf(textFieldNRef.getText()));
				Main.loadDataBooks();
				dispose();
			}
		} catch (HeadlessException e) {
		} catch (NumberFormatException e) {
		} catch (SQLErrorException e) {
		} catch (LoadDriverException e) {
		}
	}

	private void updateBook() {
		try {
			String portada = lblSelectedFile.getText();
			if (lblSelectedFile.getText() != "" && file!=null) {
				portada = System.getProperty("user.dir") + "\\imagenes\\" + file.getName();
			}
			addNewsFK();			
			BibliotecaDB.updateBook(Integer.parseInt(textFieldNRef.getText()), new Book(textFieldTitulo.getText(), portada, textFieldISBN.getText(),
					(String) comboBoxGenero.getSelectedItem(), (String) comboBoxSubgenero.getSelectedItem(),
					(String) comboBoxAutor.getSelectedItem(), (String) comboBoxEditorial.getSelectedItem(),
					(String) comboBoxLocalizacion.getSelectedItem(), (String) comboBoxIdiomas.getSelectedItem(),
					textFieldEdicion.getText(), (Integer) spinnerPuntuacion.getValue(),
					(String) comboBoxLeido.getSelectedItem(), (String) comboBoxEncuadernacion.getSelectedItem(), null,
					textAreaComentario.getText(), (String) comboBoxColeccion.getSelectedItem(), null,
					(String) comboBoxLugarCompra.getSelectedItem(), null));
			copyPortada(portada);
			Main.loadDataBooks();
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		} catch (LoadDriverException e) {
			System.out.println(e.getMessage());
		} catch (SQLErrorException e) {
			System.out.println(e.getMessage());
		} catch (TituloVacioException|PriceException e) {
			System.out.println(e.getMessage());
		}
	}
}
