package biblioteca.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import biblioteca.funcionalidad.BackupsDB;
import biblioteca.funcionalidad.BibliotecaDB;
import biblioteca.funcionalidad.ConnectionDB;
import biblioteca.funcionalidad.IconUtils;
import biblioteca.funcionalidad.ConfigProperties;
import biblioteca.funcionalidad.MyCustomRender;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTabbedPane;

public class Main {

	private static JFrame frmGestorBiblioteca;
	private JTextField textField;
	private static JTable tableBook;
	private static TableRowSorter<TableModel> sorter;
	private JMenuBar menuBar;
	private JMenu mnInicio;
	private JMenu mnOpciones;
	private JMenu mnAyuda;
	private JPanel panelBuscar;
	private JPanel panelButtons;
	private JButton btnAadirLibro;
	private JButton btnEliminar;
	private JButton btnActualizar;
	private JScrollPane scrollPane;
	private JLabel lblSearch;
	private JButton btnReload;
	private JMenuItem mntmPropiedadesDdbb;
	private JTabbedPane tabbedPane;
	private JPanel panelAutores;
	private JPanel panelGeneros;
	private JPanel panelEditoriales;
	private JPanel panelLocalizaciones;
	private JPanel panelLugaresCompra;
	private static JTable tableAutores;
	private JScrollPane scrollAutores;
	private static JTable tableGeneros;
	private JScrollPane scrollGeneros;
	private JScrollPane scrollEditoriales;
	private static JTable tableEditoriales;
	private JScrollPane scrollLocalizaciones;
	private static JTable tableLocalizaciones;
	private JScrollPane scrollLugaresCompra;
	private static JTable tableLugaresCompra;
	private JMenuItem mntmBackup;
	private JMenuItem mntmImportBackup;
	private JFileChooser fileChooser = new JFileChooser();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Main window = new Main();
					Main.frmGestorBiblioteca.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		loadProperties();
		initialize();
	}

	private void loadProperties() {
		try {
			ConfigProperties.load();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, "No se han podido cargar las propiedades, comprueba que existe el fichero config", "Error al cargar propiedades",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initialize() {
		frmGestorBiblioteca = new JFrame();
		frmGestorBiblioteca.setTitle("Gestor Biblioteca");
		frmGestorBiblioteca.setBounds(100, 100, 450, 300);
		frmGestorBiblioteca.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		frmGestorBiblioteca.setJMenuBar(menuBar);
		mnInicio = new JMenu("Inicio");
		menuBar.add(mnInicio);
		mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		mntmPropiedadesDdbb = new JMenuItem("Base de Datos");
		mnOpciones.add(mntmPropiedadesDdbb);
		mntmBackup = new JMenuItem("Realizar copia de seguridad");
		mnOpciones.add(mntmBackup);
		mntmImportBackup = new JMenuItem("Importar copia de seguridad....");
		mnOpciones.add(mntmImportBackup);
		mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		frmGestorBiblioteca.getContentPane().setLayout(new BorderLayout(0, 0));
		panelBuscar = new JPanel();
		panelBuscar.setBackground(Color.BLACK);
		frmGestorBiblioteca.getContentPane().add(panelBuscar, BorderLayout.NORTH);
		panelBuscar.setLayout(new BorderLayout(0, 0));
		panelButtons = new JPanel();
		panelButtons.setBackground(Color.BLACK);
		panelBuscar.add(panelButtons, BorderLayout.WEST);
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		btnReload = new JButton("");
		btnReload.setBackground(Color.WHITE);
		panelButtons.add(btnReload);
		btnAadirLibro = new JButton("A\u00F1adir");
		btnAadirLibro.setMnemonic('A');
		panelButtons.add(btnAadirLibro);
		btnAadirLibro.setBackground(new Color(242, 242, 242));
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setMnemonic('A');
		btnActualizar.setBackground(new Color(242, 242, 242));
		panelButtons.add(btnActualizar);
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setMnemonic('E');
		btnEliminar.setBackground(new Color(242, 242, 242));
		panelButtons.add(btnEliminar);
		lblSearch = new JLabel("Buscar");
		lblSearch.setForeground(Color.WHITE);
		panelButtons.add(lblSearch);
		textField = new JTextField();
		panelButtons.add(textField);
		textField.setColumns(20);
		tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frmGestorBiblioteca.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		tabbedPane.add(scrollPane, BorderLayout.CENTER);
		tabbedPane.setTitleAt(0, "Libros");
		tableBook = new JTable(fillTableBooks());
		scrollPane.setViewportView(tableBook);
		panelAutores = new JPanel();
		tabbedPane.addTab("Autores", null, panelAutores, null);
		panelAutores.setLayout(new BorderLayout(0, 0));
		scrollAutores = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelAutores.add(scrollAutores);
		panelGeneros = new JPanel();
		tabbedPane.addTab("Géneros", null, panelGeneros, null);
		panelGeneros.setLayout(new BorderLayout(0, 0));
		scrollGeneros = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelGeneros.add(scrollGeneros);
		panelEditoriales = new JPanel();
		tabbedPane.addTab("Editoriales", null, panelEditoriales, null);
		panelEditoriales.setLayout(new BorderLayout(0, 0));
		scrollEditoriales = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelEditoriales.add(scrollEditoriales);
		panelLocalizaciones = new JPanel();
		tabbedPane.addTab("Localizaciones", null, panelLocalizaciones, null);
		panelLocalizaciones.setLayout(new BorderLayout(0, 0));
		scrollLocalizaciones = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelLocalizaciones.add(scrollLocalizaciones);
		panelLugaresCompra = new JPanel();
		tabbedPane.addTab("Lugares de compra", null, panelLugaresCompra, null);
		panelLugaresCompra.setLayout(new BorderLayout(0, 0));
		scrollLugaresCompra = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelLugaresCompra.add(scrollLugaresCompra);
		fillTablesFk();
		scrollAutores.setViewportView(tableAutores);
		scrollGeneros.setViewportView(tableGeneros);
		scrollEditoriales.setViewportView(tableEditoriales);
		scrollLocalizaciones.setViewportView(tableLocalizaciones);
		scrollLugaresCompra.setViewportView(tableLugaresCompra);
		frmGestorBiblioteca.setExtendedState(Frame.MAXIMIZED_BOTH);
		eventsListeners();
		setIcons();
		confTables();

	}

	private void fillTablesFk() {
		try {
			tableAutores = new JTable(fillTablesFK(BibliotecaDB.getAuthors()));
			tableGeneros = new JTable(fillTablesFK(BibliotecaDB.getGenres()));
			tableEditoriales = new JTable(fillTablesFK(BibliotecaDB.getEditorials()));
			tableLocalizaciones = new JTable(fillTablesFK(BibliotecaDB.getLocations()));
			tableLugaresCompra = new JTable(fillTablesFK(BibliotecaDB.getShopPlaces()));
		} catch (LoadDriverException|SQLErrorException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void setWidthColumns() {
		tableBook.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableBook.getColumnModel().getColumn(1).setPreferredWidth(350);
		tableBook.getColumnModel().getColumn(2).setPreferredWidth(200);
		tableBook.getColumnModel().getColumn(3).setPreferredWidth(110);
		tableBook.getColumnModel().getColumn(4).setPreferredWidth(140);
		tableBook.getColumnModel().getColumn(5).setPreferredWidth(140);
		tableBook.getColumnModel().getColumn(6).setPreferredWidth(130);
		tableBook.getColumnModel().getColumn(7).setPreferredWidth(75);
		tableBook.getColumnModel().getColumn(8).setPreferredWidth(100);
		tableBook.getColumnModel().getColumn(9).setPreferredWidth(100);
		tableBook.getColumnModel().getColumn(10).setPreferredWidth(70);
		tableBook.getColumnModel().getColumn(11).setPreferredWidth(70);
		tableBook.getColumnModel().getColumn(12).setPreferredWidth(50);
		tableBook.getColumnModel().getColumn(13).setPreferredWidth(80);
		tableBook.getColumnModel().getColumn(14).setPreferredWidth(350);
		tableBook.getColumnModel().getColumn(15).setPreferredWidth(130);
		tableBook.getColumnModel().getColumn(16).setPreferredWidth(70);
		tableBook.getColumnModel().getColumn(17).setPreferredWidth(130);
		tableBook.getColumnModel().getColumn(18).setPreferredWidth(110);
	}

	protected void add() {
		JDialog dialogo = null;
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			dialogo = new AddBook();
			break;
		case 1:
			dialogo = new AddSmall("Autores");
			break;
		case 2:
			dialogo = new AddSmall("Generos");
			break;
		case 3:
			dialogo = new AddSmall("Editoriales");
			break;
		case 4:
			dialogo = new AddSmall("Localizaciones");
			break;
		case 5:
			dialogo = new AddSmall("Lugares de Compra");
			break;

		}
		dialogo.setVisible(true);
	}

	protected static void confTables() {
		setWidthColumns();
		tableBook.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sorter = new TableRowSorter<TableModel>(tableBook.getModel());
		tableBook.setRowSorter(sorter);
		tableAutores.setRowSorter(new TableRowSorter<TableModel>(tableAutores.getModel()));
		tableEditoriales.setRowSorter(new TableRowSorter<TableModel>(tableEditoriales.getModel()));
		tableGeneros.setRowSorter(new TableRowSorter<TableModel>(tableGeneros.getModel()));
		tableLugaresCompra.setRowSorter(new TableRowSorter<TableModel>(tableLugaresCompra.getModel()));
		tableLocalizaciones.setRowSorter(new TableRowSorter<TableModel>(tableLocalizaciones.getModel()));
		for (int i = 0; i < tableBook.getColumnCount(); i++) {
			tableBook.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
		}
		for (int i = 0; i < 2; i++) {
			tableAutores.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
			tableEditoriales.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
			tableLocalizaciones.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
			tableLugaresCompra.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
			tableGeneros.getColumnModel().getColumn(i).setCellRenderer(new MyCustomRender());
		}
	}

	private void eventsListeners() {
		// buttons
		btnReload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadDataFk();
				loadDataBooks();
			}
		});
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnActualizar.setBackground(new Color(204, 230, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizar.setBackground(Color.WHITE);
			}
		});
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnReload.setBackground(new Color(204, 230, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnReload.setBackground(Color.WHITE);
			}
		});
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnActualizar.setBackground(new Color(204, 230, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnActualizar.setBackground(Color.WHITE);
			}
		});
		btnAadirLibro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnAadirLibro.setBackground(new Color(204, 230, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAadirLibro.setBackground(Color.WHITE);
			}
		});
		btnActualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnEliminar.setBackground(new Color(204, 230, 255));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnEliminar.setBackground(Color.WHITE);
			}
		});
		btnAadirLibro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				add();
			}
		});
		// menuitems
		mntmImportBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadBackupDB();
			}
		});

		mntmBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				backupDB();
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});

		mntmPropiedadesDdbb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openDBOptions();
			}
		});
		// Panes
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				checkIcons();
			}
		});
		// tables
		tableBook.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		tableAutores.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		tableEditoriales.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		tableGeneros.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		tableLocalizaciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		tableLugaresCompra.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					update();
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = textField.getText();
				if (text.length() == 0)
					sorter.setRowFilter(null);
				else
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
			}
		});
	}

	private static DefaultTableModel fillTablesFK(ResultSet resultSet) {
		DefaultTableModel model = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0)
					return Integer.class;
				else
					return String.class;
			}
		};
		String[] columns = { "ID", "Nombre" };
		Object[] data = new Object[2];

		for (int i = 0; i < columns.length; i++) {
			model.addColumn(columns[i]);
		}
		try {
			while (resultSet.next()) {
				data[0] = resultSet.getInt(1);
				data[1] = resultSet.getString(2);
				model.addRow(data);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return model;
	}

	private static DefaultTableModel fillTableBooks() {
		DefaultTableModel model = new DefaultTableModel() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 0 || column == 15 || column == 13)
					return Integer.class;
				else if (column == 16)
					return Double.class;
				else if (column == 18)
					return Date.class;
				else
					return String.class;
			}

		};

		String[] columns = { "NºRef", "Título", "Autor", "ISBN", "Género", "Subgénero", "Editorial", "Localizacion",
				"Edición", "Encuadernación", "Colección", "Idioma", "Leido", "Puntuación", "Comentario",
				"Fecha publicación", "Precio", "Lugar de compra", "Fecha de compra" };
		for (int i = 0; i < columns.length; i++)
			model.addColumn(columns[i]);

		try {
			getDataToTable(model);
		} catch (LoadDriverException|SQLErrorException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		return model;

	}

	private static void getDataToTable(DefaultTableModel model) throws SQLErrorException, LoadDriverException {
		try {
			ResultSet rows = BibliotecaDB.getBooks();
			Object[] data = new Object[19];
			while (rows.next()) {
				data[0] = rows.getInt(1);
				data[1] = rows.getString(2);
				data[2] = rows.getString(3);
				data[3] = rows.getString(4);
				data[4] = rows.getString(5);
				data[5] = rows.getString(6);
				data[6] = rows.getString(7);
				data[7] = rows.getString(8);
				data[8] = rows.getString(9);
				data[9] = rows.getString(10);
				data[10] = rows.getString(11);
				data[11] = rows.getString(12);
				data[12] = rows.getString(13);
				data[13] = rows.getInt(14);
				data[14] = rows.getString(15);
				data[15] = rows.getInt(16);
				data[16] = rows.getDouble(17);
				data[17] = rows.getString(18);
				data[18] = rows.getDate(19);
				model.addRow(data);
			}
			ConnectionDB.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw new SQLErrorException("Error al obtener registros: \n" + e.getMessage() + "- Error Code: "
					+ e.getErrorCode() + "(" + e.getSQLState() + ")");
		}
	}

	private void setIcons() {
		try {
			btnAadirLibro.setIcon(IconUtils.createIcon("icon\\agenda.png"));
			frmGestorBiblioteca.setIconImage(IconUtils.createIcon("icon\\bookshelf.png").getImage());
			btnActualizar.setIcon(IconUtils.createIcon("icon\\notebook.png"));
			btnEliminar.setIcon(IconUtils.createIcon("icon\\delete-button.png"));
			btnReload.setIcon(IconUtils.createIcon("icon\\reload.png"));
		} catch (IOException e) {
		}
	}

	private void delete() {
		try {
			switch (tabbedPane.getSelectedIndex()) {
			case 0:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar el libro \"" + tableBook.getValueAt(tableBook.getSelectedRow(), 1)
								+ "\" con Nºref " + tableBook.getValueAt(tableBook.getSelectedRow(), 0) + " ?",
						"¿Eliminar libro?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB.deleteBook((int) tableBook.getValueAt(tableBook.getSelectedRow(), 0));
				break;
			case 1:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar el autor \"" + tableAutores.getValueAt(tableAutores.getSelectedRow(), 1)
								+ " ?",
						"¿Eliminar autor?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB.deleteAuthor((int) tableAutores.getValueAt(tableAutores.getSelectedRow(), 0));
				break;
			case 2:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar el género \"" + tableGeneros.getValueAt(tableGeneros.getSelectedRow(), 1)
								+ " ?",
						"¿Eliminar género?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB.deleteGenre((int) tableGeneros.getValueAt(tableGeneros.getSelectedRow(), 0));
				break;
			case 3:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar la editorial \""
								+ tableEditoriales.getValueAt(tableEditoriales.getSelectedRow(), 1) + " ?",
						"¿Eliminar editorial?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB
							.deleteEditorial((int) tableEditoriales.getValueAt(tableEditoriales.getSelectedRow(), 0));
				break;
			case 4:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar el libro \""
								+ tableLocalizaciones.getValueAt(tableLocalizaciones.getSelectedRow(), 1) + " ?",
						"ÇEliminar localización?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB.deleteLocation(
							(int) tableLocalizaciones.getValueAt(tableLocalizaciones.getSelectedRow(), 0));
				break;
			case 5:
				if (JOptionPane.showConfirmDialog(frmGestorBiblioteca,
						"¿Desea eliminar el libro \""
								+ tableLugaresCompra.getValueAt(tableLugaresCompra.getSelectedRow(), 1) + " ?",
						"¿Eliminar lugar de compra?", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					BibliotecaDB.deleteShopPlace(
							(int) tableLugaresCompra.getValueAt(tableLugaresCompra.getSelectedRow(), 0));
				break;
			}
			Main.loadDataFk();
			Main.loadDataBooks();
		} catch (IndexOutOfBoundsException e) {
		} catch (SQLErrorException|LoadDriverException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void openDBOptions() {
		OptionsDDBB dialogo = new OptionsDDBB();
		dialogo.setVisible(true);
	}

	private void checkIcons() {
		if (tabbedPane.getSelectedIndex() == 0)
			setIcons();
		else
			try {
				btnAadirLibro.setIcon(IconUtils.createIcon("icon\\plus.png"));
				btnAadirLibro.setText("Añadir");
				btnActualizar.setIcon(IconUtils.createIcon("icon\\edit.png"));
				btnEliminar.setIcon(IconUtils.createIcon("icon\\rubbish-bin.png"));
			} catch (IOException e) {
			}
	}

	private void update() {
		JDialog dialogo = new JDialog();
		switch (tabbedPane.getSelectedIndex()) {
		case 0:
			dialogo = new BookEdit((int) tableBook.getValueAt(tableBook.getSelectedRow(), 0));
			break;
		case 1:
			dialogo = new EditSmall((int) tableAutores.getValueAt(tableAutores.getSelectedRow(), 0), "Autores");
			break;
		case 2:
			dialogo = new EditSmall((int) tableGeneros.getValueAt(tableGeneros.getSelectedRow(), 0), "Generos");
			break;
		case 3:
			dialogo = new EditSmall((int) tableEditoriales.getValueAt(tableEditoriales.getSelectedRow(), 0),
					"Editoriales");
			break;
		case 4:
			dialogo = new EditSmall((int) tableLocalizaciones.getValueAt(tableLocalizaciones.getSelectedRow(), 0),
					"Localizaciones");
			break;
		case 5:
			dialogo = new EditSmall((int) tableLugaresCompra.getValueAt(tableLugaresCompra.getSelectedRow(), 0),
					"Lugares de Compra");
			break;
		}
		dialogo.setVisible(true);
	}

	protected static void loadDataFk() {
		try {
			tableAutores.setModel(fillTablesFK(BibliotecaDB.getAuthors()));
			tableGeneros.setModel(fillTablesFK(BibliotecaDB.getGenres()));
			tableEditoriales.setModel(fillTablesFK(BibliotecaDB.getEditorials()));
			tableLocalizaciones.setModel(fillTablesFK(BibliotecaDB.getLocations()));
			tableLugaresCompra.setModel(fillTablesFK(BibliotecaDB.getShopPlaces()));
			confTables();
		} catch (LoadDriverException|SQLErrorException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected static void loadDataBooks() {
		tableBook.setModel(fillTableBooks());
		confTables();
	}

	private void loadBackupDB() {
		try {
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("SQL files", "sql");
			fileChooser.removeChoosableFileFilter(fileChooser.getFileFilter());
			fileChooser.setFileFilter(fileFilter);
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")+"\\backups\\"));
			int opcion = fileChooser.showDialog(frmGestorBiblioteca, "Abrir copia");
			File file;
			if (opcion == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				BackupsDB.restore(file.getAbsolutePath());
			}
			TimeUnit.SECONDS.sleep(1);
			loadDataFk();
			loadDataBooks();
			JOptionPane.showMessageDialog(frmGestorBiblioteca, "Base de datos restaurada", "Restauración exitosa", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void backupDB() {
		try {			
			//DateTimeFormatter formatter= ;
			if (BackupsDB.backup())
				JOptionPane.showMessageDialog(frmGestorBiblioteca,
						"Copia de seguridad del día " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH.mm")) + " realizada con éxito", "Copia exitosa",
						JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frmGestorBiblioteca, "Error al cargar copia : " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
