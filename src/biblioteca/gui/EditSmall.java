package biblioteca.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import biblioteca.funcionalidad.BibliotecaDB;
import biblioteca.funcionalidad.ConnectionDB;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;

public class EditSmall extends AddSmall {

	private static final long serialVersionUID = 1L;
	private JButton btnUpdate;

	public EditSmall(int id, String title) {
		super(title);
		okButton.setEnabled(false);
		okButton.setVisible(false);		
		setDatos(title, id);
		setTitle("Editar " + title);
		btnUpdate = new JButton("Actualizar");
		btnUpdate.setActionCommand("Ok");
		buttonPane.add(btnUpdate, 0);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				update(title);
			}
		});

		
	}
	
	private void setDatos(String name, int id) {
		try {
			ResultSet result = null; 
			switch (name) {
			case "Autores":
				result = BibliotecaDB.getAuthor(id);
				result.next();
				textFieldId.setText(String.valueOf(result.getInt(1)));
				textFieldName.setText(result.getString(2));
				break;
			case "Generos":
				result = BibliotecaDB.getGenre(id);
				result.next();
				textFieldId.setText(String.valueOf(result.getInt(1)));
				textFieldName.setText(result.getString(2));
				break;
			case "Editoriales":
				result = BibliotecaDB.getEditorial(id);
				result.next();
				textFieldId.setText(String.valueOf(result.getInt(1)));
				textFieldName.setText(result.getString(2));
				break;
			case "Localizaciones":
				System.out.println("He llegao a loca");
				result = BibliotecaDB.getLocation(id);
				result.next();
				textFieldId.setText(String.valueOf(result.getInt(1)));
				textFieldName.setText(result.getString(2));
				break;
			case "Lugares de Compra":
				System.out.println("He llegao a l");
				result = BibliotecaDB.getShopPlace(id);
				result.next();
				textFieldId.setText(String.valueOf(result.getInt(1)));
				textFieldName.setText(result.getString(2));
				break;
			}
			ConnectionDB.closeConnection();
		} catch (LoadDriverException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLErrorException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void update(String name) {
		try {
			switch (name) {
			case "Autores":
				BibliotecaDB.updateAutor(Integer.valueOf(textFieldId.getText()), textFieldName.getText());
				break;
			case "Generos":
				BibliotecaDB.updateGenre(Integer.valueOf(textFieldId.getText()), textFieldName.getText());
				break;
			case "Editoriales":
				BibliotecaDB.updateEditorial(Integer.valueOf(textFieldId.getText()), textFieldName.getText());
				break;
			case "Localizaciones":
				BibliotecaDB.updateLocation(Integer.valueOf(textFieldId.getText()), textFieldName.getText());
				break;
			case "Lugares de Compra":
				BibliotecaDB.updateShopPlace(Integer.valueOf(textFieldId.getText()), textFieldName.getText());
				break;
			}
			Main.loadDataBooks();
			Main.loadDataFk();
			ConnectionDB.closeConnection();
		} catch (LoadDriverException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLErrorException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
