package biblioteca.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import biblioteca.funcionalidad.ConfigDatabase;
import biblioteca.funcionalidad.ConnectionDB;
import biblioteca.funcionalidad.ConfigProperties;
import biblioteca.funcionalidad.excepciones.LoadDriverException;
import biblioteca.funcionalidad.excepciones.SQLErrorException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OptionsDDBB extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();	
	private JTextField textFieldUserDB;
	private JTextField textFieldPasswordDB;
	private JTextField textFieldNameDB;
	private JTextField textFieldServerDB;
	private JTextField textFieldPortDB;
	private JTextField textFieldConectorDB;
	private JTextField textFieldDriverDB;
	private JLabel lblDBUSer;
	private JLabel lblDBPassword;
	private JLabel lblDBName;
	private JLabel lblDBHost;
	private JLabel lblDBPort;
	private JLabel lblDBConector;
	private JButton btnCheckConnection;
	private JLabel lblDBDriver;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;

	public OptionsDDBB() {
		setTitle("Opciones base de datos");
		setBounds(100, 100, 318, 419);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		lblDBUSer = new JLabel("Usuario");
		lblDBUSer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBUSer.setBounds(31, 35, 61, 14);
		contentPanel.add(lblDBUSer);

		textFieldUserDB = new JTextField(ConfigDatabase.USER_DATABASE);
		textFieldUserDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==2) {
					textFieldUserDB.setEditable(true);
				}
			}
		});
		textFieldUserDB.setEditable(false);
		textFieldUserDB.setBounds(130, 34, 113, 20);
		contentPanel.add(textFieldUserDB);
		textFieldUserDB.setColumns(10);

		lblDBPassword = new JLabel("Contrase\u00F1a");
		lblDBPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBPassword.setBounds(31, 75, 88, 14);
		contentPanel.add(lblDBPassword);

		lblDBName = new JLabel("Base de datos");
		lblDBName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBName.setBounds(31, 115, 98, 14);
		contentPanel.add(lblDBName);

		lblDBHost = new JLabel("Servidor");
		lblDBHost.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBHost.setBounds(31, 155, 88, 14);
		contentPanel.add(lblDBHost);

		lblDBPort = new JLabel("Puerto");
		lblDBPort.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBPort.setBounds(31, 195, 88, 14);
		contentPanel.add(lblDBPort);

		lblDBConector = new JLabel("Conector");
		lblDBConector.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBConector.setBounds(31, 235, 88, 14);
		contentPanel.add(lblDBConector);

		textFieldPasswordDB = new JTextField(ConfigDatabase.PASSWORD_DATABASE);
		textFieldPasswordDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldPasswordDB.setEditable(true);
			}
		});
		textFieldPasswordDB.setEditable(false);
		textFieldPasswordDB.setBounds(130, 74, 113, 20);
		contentPanel.add(textFieldPasswordDB);
		textFieldPasswordDB.setColumns(10);

		textFieldNameDB = new JTextField(ConfigDatabase.DB_NAME);
		textFieldNameDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldNameDB.setEditable(true);
			}
		});
		textFieldNameDB.setEditable(false);
		textFieldNameDB.setColumns(10);
		textFieldNameDB.setBounds(130, 114, 113, 20);
		contentPanel.add(textFieldNameDB);

		textFieldServerDB = new JTextField(ConfigDatabase.DB_HOST);
		textFieldServerDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldServerDB.setEditable(true);
			}
		});
		textFieldServerDB.setEditable(false);
		textFieldServerDB.setColumns(10);
		textFieldServerDB.setBounds(130, 154, 113, 20);
		contentPanel.add(textFieldServerDB);

		textFieldPortDB = new JTextField(String.valueOf(ConfigDatabase.DB_PORT));
		textFieldPortDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldPortDB.setEditable(true);
			}
		});
		textFieldPortDB.setEditable(false);
		textFieldPortDB.setColumns(10);
		textFieldPortDB.setBounds(130, 194, 113, 20);
		contentPanel.add(textFieldPortDB);

		textFieldConectorDB = new JTextField(ConfigDatabase.DB_CONECTOR);
		textFieldConectorDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldConectorDB.setEditable(true);
			}
		});
		textFieldConectorDB.setEditable(false);
		textFieldConectorDB.setColumns(10);
		textFieldConectorDB.setBounds(130, 234, 113, 20);
		contentPanel.add(textFieldConectorDB);

		btnCheckConnection = new JButton("Probar conexi\u00F3n");
		btnCheckConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ConfigProperties.write(textFieldUserDB.getText(), textFieldPasswordDB.getText(),
							textFieldNameDB.getText(), textFieldServerDB.getText(),
							Integer.valueOf(textFieldPortDB.getText()), textFieldConectorDB.getText(),
							textFieldDriverDB.getText());
					ConfigProperties.load();
					if (ConnectionDB.testConnection())
						JOptionPane.showMessageDialog(getContentPane(), "Conexión realizada con éxito",
								"Conexión realizada", JOptionPane.INFORMATION_MESSAGE);
					ConnectionDB.closeConnection();
				} catch (LoadDriverException | SQLErrorException e) {
					JOptionPane.showMessageDialog(getContentPane(), "No es posible realizar la conxión",
							"Error al conectar", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e) {
				} catch (FileNotFoundException e) {
				} catch (IOException | HeadlessException e) {
				}
			}
		});
		btnCheckConnection.setBounds(140, 313, 152, 23);
		contentPanel.add(btnCheckConnection);

		lblDBDriver = new JLabel("Driver");
		lblDBDriver.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDBDriver.setBounds(31, 275, 46, 14);
		contentPanel.add(lblDBDriver);

		textFieldDriverDB = new JTextField(ConfigDatabase.DRIVER);
		textFieldDriverDB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2)
					textFieldDriverDB.setEditable(true);
			}
		});
		textFieldDriverDB.setEditable(false);
		textFieldDriverDB.setBounds(130, 274, 113, 20);
		contentPanel.add(textFieldDriverDB);
		textFieldDriverDB.setColumns(10);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Aplicar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveOptionsDB();
						dispose();
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

	private void saveOptionsDB() {
		try {
			ConfigProperties.write(textFieldUserDB.getText(), textFieldPasswordDB.getText(), textFieldNameDB.getText(),
					textFieldServerDB.getText(), Integer.valueOf(textFieldPortDB.getText()),
					textFieldConectorDB.getText(), textFieldDriverDB.getText());
			ConfigProperties.load();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
