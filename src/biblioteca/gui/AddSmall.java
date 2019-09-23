package biblioteca.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biblioteca.funcionalidad.BibliotecaDB;
import biblioteca.funcionalidad.ConnectionDB;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSmall extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	protected JTextField textFieldId;
	protected JTextField textFieldName;
	private JLabel lblId;
	private JLabel lnlNombre;
	protected JPanel buttonPane;
	protected JButton okButton;
	protected JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public AddSmall(String tabla) {
		setBounds(100, 100, 311, 177);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblId = new JLabel("ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblId.setBounds(26, 26, 46, 14);
		contentPanel.add(lblId);
		
		textFieldId = new JTextField();
		textFieldId.setBounds(92, 25, 47, 20);
		contentPanel.add(textFieldId);
		textFieldId.setColumns(10);
		
		lnlNombre = new JLabel("Nombre");
		lnlNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lnlNombre.setBounds(26, 66, 63, 14);
		contentPanel.add(lnlNombre);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(92, 65, 181, 20);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);
		setId(tabla);
		setTitle("Añadir " + tabla);
		textFieldId.setEditable(false);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("A\u00F1adir");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						add(tabla);
						Main.loadDataBooks();
						Main.loadDataFk();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Salir");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void setId(String name) {
		try {
			switch (name) {
			case "Autores":
				textFieldId.setText(String.valueOf(BibliotecaDB.getLastIdAuthor()+1));
				break;
			case "Generos":
				textFieldId.setText(String.valueOf(BibliotecaDB.getLastIdGenre()+1));
				break;
			case "Editoriales":
				textFieldId.setText(String.valueOf(BibliotecaDB.getLastIdEditorial()+1));
				break;
			case "Localizaciones":
				textFieldId.setText(String.valueOf(BibliotecaDB.getLastIdLocationss()+1));
				break;
			case "Lugares de Compra":
				textFieldId.setText(String.valueOf(BibliotecaDB.getLastIdShopPlace()+1));
				break;
			}
			ConnectionDB.closeConnection();
		} catch (LoadDriverException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLErrorException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void add(String name) {
		try {
			switch (name) {
			case "Autores":
				BibliotecaDB.insertAuthor(textFieldName.getText());
				break;
			case "Generos":
				BibliotecaDB.insertGenre(textFieldName.getText());
				break;
			case "Editoriales":
				BibliotecaDB.insertEditorial(textFieldName.getText());
				break;
			case "Localizaciones":
				BibliotecaDB.insertLocation(textFieldName.getText());
				break;
			case "Lugares de Compra":
				BibliotecaDB.insertShopPlace(textFieldName.getText());
				break;
			}
			ConnectionDB.closeConnection();
		} catch (LoadDriverException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (SQLErrorException e) {
			JOptionPane.showConfirmDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
