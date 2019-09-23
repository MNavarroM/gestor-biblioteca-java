package biblioteca.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import biblioteca.funcionalidad.BibliotecaDB;
import biblioteca.funcionalidad.IconUtils;
import biblioteca.funcionalidad.Book;
import biblioteca.funcionalidad.ConnectionDB;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.PriceException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;
import biblioteca.funcionalidad.excepciones.TituloVacioException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

public class AddBook extends JDialog {

	private static final long serialVersionUID = 1L;
	protected final JPanel contentPanel = new JPanel();
	protected JTextField textFieldTitulo;
	protected JTextField textFieldISBN;
	protected JComboBox<String> comboBoxGenero, comboBoxSubgenero, comboBoxEditorial, comboBoxAutor;
	protected BibliotecaDB mysql;
	protected JTextField textFieldNRef;
	protected JComboBox<String> comboBoxLocalizacion;
	protected JTextField textFieldEdicion;
	protected JComboBox<String> comboBoxLugarCompra;
	protected JComboBox<String> comboBoxIdiomas;
	protected JComboBox<String> comboBoxEncuadernacion;
	protected JComboBox<String> comboBoxLeido;
	protected JComboBox<String> comboBoxColeccion;
	protected JTextArea textAreaComentario;
	protected JSpinner spinnerPuntuacion;
	protected JSpinner spinnerPublicacion;
	protected JSpinner spinnerPrecio;
	protected JSpinner spinnerFechaCompra;
	protected JPanel buttonPane;
	protected JButton okButton, cancelButton;
	protected JFileChooser fileChooser;
	protected JLabel lblSelectedFile;
	protected File file;
	protected JCheckBox chckbxSinFecha;
	protected JLabel chckAnoCompra;
	private JTextField textField;
	private JLabel lblTitulo;
	private JLabel lblIsbn;
	private JLabel lblGenero;
	private JLabel lblSubgenero;
	private JLabel lblAutor;
	private JLabel lblEditorial;
	private JLabel lblNReferencia;
	private JLabel lblEdicion;
	private JLabel lblIdioma;
	private JLabel lblLocalizacion;
	private JLabel lblPuntuacion;
	private JLabel lblEncuadernacion;
	private JLabel lblLeido;
	private JLabel lblColeccion;
	private JLabel lblComentario;
	private JScrollPane scrollArea;
	private JLabel lblPrecio;
	private JLabel lblLugarDeCompra;
	private JButton btnPortada;
	protected JPanel panelPortada;
	protected JLabel lblPortada;

	public AddBook() {
		setModal(true);
		setTitle("A\u00F1adir Libro");
		setResizable(false);
		contentPanel.setBackground(Color.WHITE);
		setBounds(100, 100, 667, 655);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lblTitulo = new JLabel("T\u00EDtulo");
		lblTitulo.setBounds(22, 82, 46, 14);
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblTitulo);

		textFieldTitulo = new JTextField();
		textFieldTitulo.setBounds(110, 80, 313, 20);
		contentPanel.add(textFieldTitulo);
		textFieldTitulo.setColumns(10);

		lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(224, 45, 68, 14);
		lblIsbn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblIsbn);

		textFieldISBN = new JTextField();
		textFieldISBN.setBounds(288, 43, 135, 20);
		contentPanel.add(textFieldISBN);
		textFieldISBN.setColumns(10);

		lblGenero = new JLabel("G\u00E9nero");
		lblGenero.setBounds(22, 120, 46, 14);
		lblGenero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblGenero);

		comboBoxGenero = new JComboBox<String>();
		comboBoxGenero.setBounds(150, 118, 200, 20);
		contentPanel.add(comboBoxGenero);

		lblSubgenero = new JLabel("Subg\u00E9nero");
		lblSubgenero.setBounds(22, 157, 76, 14);
		lblSubgenero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblSubgenero);

		comboBoxSubgenero = new JComboBox<String>();
		comboBoxSubgenero.setBounds(150, 156, 200, 20);
		contentPanel.add(comboBoxSubgenero);

		lblAutor = new JLabel("Autor");
		lblAutor.setBounds(22, 193, 58, 14);
		lblAutor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblAutor);

		comboBoxAutor = new JComboBox<String>();
		comboBoxAutor.setBounds(150, 194, 200, 20);
		contentPanel.add(comboBoxAutor);

		lblEditorial = new JLabel("Editorial");
		lblEditorial.setBounds(22, 234, 86, 14);
		lblEditorial.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblEditorial);

		comboBoxEditorial = new JComboBox<String>();
		comboBoxEditorial.setBounds(150, 232, 200, 20);
		contentPanel.add(comboBoxEditorial);

		lblNReferencia = new JLabel("N\u00BA Ref");
		lblNReferencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNReferencia.setBounds(22, 45, 86, 14);
		contentPanel.add(lblNReferencia);

		textFieldNRef = new JTextField();
		textFieldNRef.setEditable(false);
		textFieldNRef.setBounds(110, 45, 58, 20);
		contentPanel.add(textFieldNRef);
		textFieldNRef.setColumns(10);

		lblEdicion = new JLabel("Edici\u00F3n");
		lblEdicion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEdicion.setBounds(22, 310, 58, 14);
		contentPanel.add(lblEdicion);

		textFieldEdicion = new JTextField();
		textFieldEdicion.setBounds(150, 308, 200, 20);
		contentPanel.add(textFieldEdicion);
		textFieldEdicion.setColumns(10);

		lblIdioma = new JLabel("Idioma");
		lblIdioma.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIdioma.setBounds(22, 348, 68, 14);
		contentPanel.add(lblIdioma);

		comboBoxIdiomas = new JComboBox<String>();
		comboBoxIdiomas.setModel(new DefaultComboBoxModel<String>());
		comboBoxIdiomas.addItem(null);
		comboBoxIdiomas.addItem("Español");
		comboBoxIdiomas.addItem("Inglés");
		comboBoxIdiomas.addItem("Francés");
		comboBoxIdiomas.setSelectedItem(null);
		comboBoxIdiomas.setBounds(150, 346, 200, 20);
		contentPanel.add(comboBoxIdiomas);

		lblLocalizacion = new JLabel("Localizaci\u00F3n");
		lblLocalizacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLocalizacion.setBounds(22, 272, 86, 14);
		contentPanel.add(lblLocalizacion);

		comboBoxLocalizacion = new JComboBox<String>();
		comboBoxLocalizacion.setBounds(150, 270, 200, 20);
		contentPanel.add(comboBoxLocalizacion);

		lblPuntuacion = new JLabel("Puntuaci\u00F3n");
		lblPuntuacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPuntuacion.setBounds(453, 45, 68, 14);
		contentPanel.add(lblPuntuacion);

		spinnerPuntuacion = new JSpinner();
		spinnerPuntuacion.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		spinnerPuntuacion.setBounds(564, 43, 78, 20);
		contentPanel.add(spinnerPuntuacion);

		lblEncuadernacion = new JLabel("Encuadernaci\u00F3n");
		lblEncuadernacion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEncuadernacion.setBounds(22, 386, 101, 14);
		contentPanel.add(lblEncuadernacion);

		comboBoxEncuadernacion = new JComboBox<String>();
		comboBoxEncuadernacion.setModel(new DefaultComboBoxModel<String>());
		comboBoxEncuadernacion.addItem(null);
		comboBoxEncuadernacion.addItem("Tapa dura");
		comboBoxEncuadernacion.addItem("Tapa blanda");
		comboBoxEncuadernacion.setSelectedItem(null);
		comboBoxEncuadernacion.setBounds(150, 384, 102, 20);
		contentPanel.add(comboBoxEncuadernacion);

		chckAnoCompra = new JLabel("A\u00F1o publicaci\u00F3n");
		chckAnoCompra.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckAnoCompra.setBounds(453, 82, 101, 14);
		contentPanel.add(chckAnoCompra);

		lblLeido = new JLabel("Le\u00EDdo");
		lblLeido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLeido.setBounds(150, 424, 46, 14);
		contentPanel.add(lblLeido);

		comboBoxLeido = new JComboBox<String>();
		comboBoxLeido.addItem(null);
		comboBoxLeido.addItem("Si");
		comboBoxLeido.addItem("No");
		comboBoxLeido.setSelectedItem(null);
		comboBoxLeido.setBounds(91, 422, 52, 20);
		contentPanel.add(comboBoxLeido);

		lblColeccion = new JLabel("Colecci\u00F3n");
		lblColeccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblColeccion.setBounds(22, 424, 86, 14);
		contentPanel.add(lblColeccion);

		comboBoxColeccion = new JComboBox<String>();
		comboBoxColeccion.addItem(null);
		comboBoxColeccion.addItem("Si");
		comboBoxColeccion.addItem("No");
		comboBoxColeccion.setSelectedItem(null);
		comboBoxColeccion.setBounds(193, 422, 46, 20);
		contentPanel.add(comboBoxColeccion);

		lblComentario = new JLabel("Comentario");
		lblComentario.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblComentario.setBounds(388, 460, 86, 14);
		contentPanel.add(lblComentario);
		scrollArea = new JScrollPane();
		scrollArea.setBounds(388, 477, 244, 92);
		contentPanel.add(scrollArea);

		textAreaComentario = new JTextArea();
		scrollArea.setViewportView(textAreaComentario);
		textAreaComentario.setLineWrap(true);

		lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrecio.setBounds(249, 424, 58, 14);
		contentPanel.add(lblPrecio);

		spinnerPrecio = new JSpinner();
		spinnerPrecio.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		spinnerPrecio.setBounds(302, 422, 48, 20);
		contentPanel.add(spinnerPrecio);

		lblLugarDeCompra = new JLabel("Lugar de Compra");
		lblLugarDeCompra.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblLugarDeCompra.setBounds(22, 462, 127, 14);
		contentPanel.add(lblLugarDeCompra);

		comboBoxLugarCompra = new JComboBox<String>();
		comboBoxLugarCompra.setBounds(150, 460, 200, 20);
		contentPanel.add(comboBoxLugarCompra);

		spinnerFechaCompra = new JSpinner();
		spinnerFechaCompra.setModel(createSpinnerDateModel());
		spinnerFechaCompra.setEditor(new JSpinner.DateEditor(spinnerFechaCompra, "dd/MM/yyyy"));
		spinnerFechaCompra.setBounds(193, 498, 92, 20);
		contentPanel.add(spinnerFechaCompra);

		btnPortada = new JButton("Portada...");

		btnPortada.setBounds(360, 117, 89, 23);
		contentPanel.add(btnPortada);

		panelPortada = new JPanel();
		panelPortada.setBounds(399, 148, 229, 279);
		contentPanel.add(panelPortada);
		panelPortada.setLayout(new BorderLayout(0, 0));

		lblPortada = new JLabel("");
		panelPortada.add(lblPortada, BorderLayout.WEST);

		lblSelectedFile = new JLabel("");

		lblSelectedFile.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblSelectedFile.setBounds(360, 435, 291, 14);
		contentPanel.add(lblSelectedFile);

		chckbxSinFecha = new JCheckBox("Sin fecha de compra");
		chckbxSinFecha.setFont(new Font("Tahoma", Font.PLAIN, 12));

		chckbxSinFecha.setBounds(22, 498, 153, 23);
		contentPanel.add(chckbxSinFecha);

		textField = new JTextField();
		textField.setBounds(564, 80, 78, 20);
		contentPanel.add(textField);
		textField.setColumns(10);

		comboBoxGenero.setEditable(true);
		comboBoxSubgenero.setEditable(true);
		comboBoxAutor.setEditable(true);
		comboBoxLocalizacion.setEditable(true);
		comboBoxLugarCompra.setEditable(true);
		comboBoxEditorial.setEditable(true);
		
		JLabel lblFechaIntroduccion = new JLabel("Fecha introducci\u00F3n");
		lblFechaIntroduccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaIntroduccion.setBounds(22, 536, 121, 14);
		contentPanel.add(lblFechaIntroduccion);
		
		JLabel lblFIntroduccion = new JLabel(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		lblFIntroduccion.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFIntroduccion.setBounds(150, 536, 200, 14);
		contentPanel.add(lblFIntroduccion);

		fileChooser = new JFileChooser();
		FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("PNG, JPEG & JPG Images", "png", "jpg",
				"jpeg");
		fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
		fileChooser.setFileFilter(fileFilter);
		rellenarComboBox();
		setListeners();
		setNRef();

		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(Color.WHITE);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("");

				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						addBook();
					}
				});
				okButton.setActionCommand("Enviar");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");

				buttonPane.add(cancelButton);
			}
		}
		setIcons();
	}

	private void setIcons() {
		try {
			okButton.setIcon(IconUtils.createIcon("icon\\save.png"));
			cancelButton.setIcon(IconUtils.createIcon("icon\\back.png"));
		} catch (IOException e) {
		}

	}

	private void rellenarComboBox() {
		try {
			rellenarGeneros();
			rellenarLugaresCompra();
			rellenarAutores();
			rellenarLocalización();
			rellenarEditoriales();
			ConnectionDB.closeConnection();
		} catch (LoadDriverException|SQLException|SQLErrorException e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private SpinnerDateModel createSpinnerDateModel() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -100);
		Date start = calendar.getTime();
		return new SpinnerDateModel(Calendar.getInstance().getTime(), start, Calendar.getInstance().getTime(),
				Calendar.YEAR);
	}

	private void setListeners() {

		chckbxSinFecha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxSinFecha.isSelected()) {
					spinnerFechaCompra.setEnabled(false);
				} else {
					spinnerFechaCompra.setEnabled(true);
				}
			}
		});

		btnPortada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser();
			}
		});

		comboBoxGenero.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxGenero.getSelectedItem() != null)
					comboBoxGenero.setEditable(false);
				else
					comboBoxGenero.setEditable(true);
			}
		});

		comboBoxSubgenero.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxSubgenero.getSelectedItem() != null)
					comboBoxSubgenero.setEditable(false);
				else
					comboBoxSubgenero.setEditable(true);
			}
		});

		comboBoxAutor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxAutor.getSelectedItem() != null)
					comboBoxAutor.setEditable(false);
				else
					comboBoxAutor.setEditable(true);
			}
		});

		comboBoxLocalizacion.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxLocalizacion.getSelectedItem() != null)
					comboBoxLocalizacion.setEditable(false);
				else
					comboBoxLocalizacion.setEditable(true);
			}
		});

		comboBoxLugarCompra.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBoxLugarCompra.getSelectedItem() != null)
					comboBoxLugarCompra.setEditable(false);
				else
					comboBoxLugarCompra.setEditable(true);
			}
		});

	}

	private void setNRef() {
		try {
			textFieldNRef.setText(String.valueOf(BibliotecaDB.getLastIdBook() + 1));
		} catch (LoadDriverException e) {
		} catch (SQLErrorException e) {
		}
	}

	private void rellenarGeneros() throws LoadDriverException, SQLErrorException, SQLException {
		ResultSet data = BibliotecaDB.getGenres();
		comboBoxGenero.removeAllItems();
		comboBoxSubgenero.removeAllItems();
		comboBoxGenero.addItem(null);
		comboBoxSubgenero.addItem(null);
		while (data.next()) {
			comboBoxGenero.addItem(data.getString("gnombre"));
			comboBoxSubgenero.addItem(data.getString("gnombre"));
		}
		comboBoxGenero.setSelectedItem(null);
		comboBoxSubgenero.setSelectedItem(null);
	}

	private void rellenarLocalización() throws LoadDriverException, SQLErrorException, SQLException {
		ResultSet data = BibliotecaDB.getLocations();
		comboBoxLocalizacion.removeAllItems();
		comboBoxLocalizacion.addItem(null);
		while (data.next()) {
			comboBoxLocalizacion.addItem(data.getString("lnombre"));
		}
		comboBoxLocalizacion.setSelectedItem(null);

	}

	private void rellenarAutores() throws LoadDriverException, SQLErrorException, SQLException {
		ResultSet data = BibliotecaDB.getAuthors();
		comboBoxAutor.removeAllItems();
		comboBoxAutor.addItem(null);
		while (data.next())
			comboBoxAutor.addItem(data.getString("anombre"));
		comboBoxAutor.setSelectedItem(null);
	}

	private void rellenarEditoriales() throws LoadDriverException, SQLErrorException, SQLException {
		ResultSet data = BibliotecaDB.getEditorials();
		comboBoxEditorial.removeAllItems();
		comboBoxEditorial.addItem(null);
		while (data.next()) {
			comboBoxEditorial.addItem(data.getString("enombre"));
		}
		comboBoxEditorial.setSelectedItem(null);

	}

	private void rellenarLugaresCompra() throws LoadDriverException, SQLErrorException, SQLException {
		ResultSet data = BibliotecaDB.getShopPlaces();
		comboBoxLugarCompra.removeAllItems();
		comboBoxLugarCompra.addItem(null);
		while (data.next()) {
			comboBoxLugarCompra.addItem(data.getString("lcnombre"));
		}
		comboBoxLugarCompra.setSelectedItem(null);

	}

	protected void addNewsFK() {
		try {
			if (comboBoxAutor.getSelectedItem() != null
					&& !BibliotecaDB.existsAuthor(comboBoxAutor.getSelectedItem().toString())) {
				BibliotecaDB.insertAuthor(comboBoxAutor.getSelectedItem().toString());
			}
			if (comboBoxGenero.getSelectedItem() != null
					&& !BibliotecaDB.existsGenre(comboBoxGenero.getSelectedItem().toString())) {
				BibliotecaDB.insertGenre(comboBoxGenero.getSelectedItem().toString());
			}
			if (comboBoxSubgenero.getSelectedItem() != null
					&& !BibliotecaDB.existsGenre(comboBoxSubgenero.getSelectedItem().toString())) {
				BibliotecaDB.insertGenre(comboBoxSubgenero.getSelectedItem().toString());
			}
			if (comboBoxEditorial.getSelectedItem() != null
					&& !BibliotecaDB.existsEditorial(comboBoxEditorial.getSelectedItem().toString())) {
				BibliotecaDB.insertEditorial(comboBoxEditorial.getSelectedItem().toString());
			}
			if (comboBoxLocalizacion.getSelectedItem() != null
					&& !BibliotecaDB.existsLocation(comboBoxLocalizacion.getSelectedItem().toString())) {
				BibliotecaDB.insertLocation(comboBoxLocalizacion.getSelectedItem().toString());
			}

			if (comboBoxLugarCompra.getSelectedItem() != null
					&& !BibliotecaDB.existsShopPlace(comboBoxLugarCompra.getSelectedItem().toString())) {
				BibliotecaDB.insertShopPlace(comboBoxLugarCompra.getSelectedItem().toString());
			}
			ConnectionDB.closeConnection();
		} catch (LoadDriverException|SQLErrorException e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	private void addBook() {
		addNewsFK();
		java.sql.Date fechaCompra = null;
		String portada = null;

		if (!chckbxSinFecha.isSelected())
			fechaCompra = new java.sql.Date(((Date) spinnerFechaCompra.getValue()).getTime());
		else
			fechaCompra = null;
		if (file != null) {
			portada = System.getProperty("user.dir") + "\\imagenes\\" + file.getName();
		} else
			portada = null;

		try {
			BibliotecaDB.insertBook(new Book(textFieldTitulo.getText(), portada, textFieldISBN.getText(),
					(String) comboBoxGenero.getSelectedItem(), (String) comboBoxSubgenero.getSelectedItem(),
					(String) comboBoxAutor.getSelectedItem(), (String) comboBoxEditorial.getSelectedItem(),
					(String) comboBoxLocalizacion.getSelectedItem(), (String) comboBoxIdiomas.getSelectedItem(),
					textFieldEdicion.getText(), (Integer) spinnerPuntuacion.getValue(),
					(String) comboBoxLeido.getSelectedItem(), (String) comboBoxEncuadernacion.getSelectedItem(), null,
					textAreaComentario.getText(), (String) comboBoxColeccion.getSelectedItem(), null,
					(String) comboBoxLugarCompra.getSelectedItem(), fechaCompra));
			copyPortada(portada);
			setNRef();
			rellenarComboBox();
			clear();
			Main.loadDataBooks();
			ConnectionDB.closeConnection();
		} catch (SQLErrorException e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (TituloVacioException|PriceException e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error al introducir libro",
					JOptionPane.ERROR_MESSAGE);
		} catch (LoadDriverException e) {
			JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error al introducir libro",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	protected void copyPortada(String portada) {
		try {
			if (portada != null && file!=null)
				Files.copy(Paths.get(file.getPath()), Paths.get(portada), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(getContentPane(), "No ha sido posible guardar la portada :" + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openFileChooser() throws HeadlessException {
		int opcion = fileChooser.showDialog(getContentPane(), "Abrir concesionario");
		if (opcion == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if (file.exists()) {
				lblSelectedFile.setText(file.getPath());
				try {
					lblPortada.setIcon(IconUtils.createIcon(lblSelectedFile.getText(), panelPortada));
					panelPortada.add(lblPortada);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(getContentPane(), e.getMessage(), "Error al cargar imagen",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	private void clear() {
		lblSelectedFile.setText("");
		textFieldTitulo.setText("");
		textFieldISBN.setText("");
		comboBoxGenero.setSelectedItem(null);
		comboBoxSubgenero.setSelectedItem(null);
		comboBoxAutor.setSelectedItem(null);
		comboBoxEditorial.setSelectedItem(null);
		comboBoxLocalizacion.setSelectedItem(null);
		comboBoxIdiomas.setSelectedItem(null);
		textFieldEdicion.setText("");
		spinnerPuntuacion.setValue(0);
		comboBoxLeido.setSelectedItem(null);
		comboBoxEncuadernacion.setSelectedItem(null);
		textAreaComentario.setText("");
		comboBoxColeccion.setSelectedItem(null);
		spinnerPrecio.setValue(0);
		comboBoxLugarCompra.setSelectedItem(null);
	}
}
