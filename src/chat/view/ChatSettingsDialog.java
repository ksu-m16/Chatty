package chat.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.swing.JLabel;

import chat.model.Settings;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatSettingsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPortR;
	private JTextField txtUser;
	private JTextField txtPortS;

	private Settings settings = new Settings();
	
	private JTextField txtAddr;
	private boolean okbuttonPressed = false;
	
	public void showSettingsDialog(){
//		ChatSettingsDialog dialog = new ChatSettingsDialog(parent, true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	// public ChatSettings(java.awt.Frame parent, boolean modal)
	// {
	// super(parent, modal);
	// // initComponents();
	// }

	/**
	 * Launch the application.
	 */
	// public void run(){
	// try {
	// ChatSettings dialog = new ChatSettings();
	// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	// dialog.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	public boolean isOkbuttonPressed() {
		return okbuttonPressed;
	}

	/**
	 * Create the dialog.
	 */
	public ChatSettingsDialog(java.awt.Frame parent, boolean modal) {

		super(parent, modal);

//	public ChatSettingsDialog() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}
		});
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblEnterTargetAddress = new JLabel("Enter target address");
			GridBagConstraints gbc_lblEnterTargetAddress = new GridBagConstraints();
			gbc_lblEnterTargetAddress.insets = new Insets(0, 0, 5, 5);
			gbc_lblEnterTargetAddress.gridx = 1;
			gbc_lblEnterTargetAddress.gridy = 1;
			contentPanel.add(lblEnterTargetAddress, gbc_lblEnterTargetAddress);
		}

		{
			txtAddr = new JTextField();
			txtAddr.setText("localhost");
			GridBagConstraints gbc_txtLocalhost = new GridBagConstraints();
			gbc_txtLocalhost.gridwidth = 3;
			gbc_txtLocalhost.insets = new Insets(0, 0, 5, 5);
			gbc_txtLocalhost.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtLocalhost.gridx = 4;
			gbc_txtLocalhost.gridy = 1;
			contentPanel.add(txtAddr, gbc_txtLocalhost);
			txtAddr.setColumns(10);
		}
		{
			JLabel lblEnterReceiverPort = new JLabel("Enter port to listen");
			GridBagConstraints gbc_lblEnterReceiverPort = new GridBagConstraints();
			gbc_lblEnterReceiverPort.anchor = GridBagConstraints.WEST;
			gbc_lblEnterReceiverPort.insets = new Insets(0, 0, 5, 5);
			gbc_lblEnterReceiverPort.gridx = 1;
			gbc_lblEnterReceiverPort.gridy = 3;
			contentPanel.add(lblEnterReceiverPort, gbc_lblEnterReceiverPort);
		}
		{
			txtPortR = new JTextField();
			txtPortR.setText("8889");
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridwidth = 3;
			gbc_textField_1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_1.gridx = 4;
			gbc_textField_1.gridy = 3;
			contentPanel.add(txtPortR, gbc_textField_1);
			txtPortR.setColumns(10);
		}
		{
			JLabel lblEnterWhereTo = new JLabel("Enter port where to send");
			GridBagConstraints gbc_lblEnterWhereTo = new GridBagConstraints();
			gbc_lblEnterWhereTo.insets = new Insets(0, 0, 5, 5);
			gbc_lblEnterWhereTo.gridx = 1;
			gbc_lblEnterWhereTo.gridy = 4;
			contentPanel.add(lblEnterWhereTo, gbc_lblEnterWhereTo);
		}
		{
			txtPortS = new JTextField();
			txtPortS.setText("8890");
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.gridwidth = 3;
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 4;
			gbc_textField.gridy = 4;
			contentPanel.add(txtPortS, gbc_textField);
			txtPortS.setColumns(10);
		}

		{
			JLabel lblEnterYourName = new JLabel("Enter your name");
			GridBagConstraints gbc_lblEnterYourName = new GridBagConstraints();
			gbc_lblEnterYourName.anchor = GridBagConstraints.WEST;
			gbc_lblEnterYourName.insets = new Insets(0, 0, 5, 5);
			gbc_lblEnterYourName.gridx = 1;
			gbc_lblEnterYourName.gridy = 5;
			contentPanel.add(lblEnterYourName, gbc_lblEnterYourName);
		}
		{
			txtUser = new JTextField();
			txtUser.setText("user");
			GridBagConstraints gbc_txtUser = new GridBagConstraints();
			gbc_txtUser.insets = new Insets(0, 0, 5, 5);
			gbc_txtUser.gridwidth = 3;
			gbc_txtUser.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUser.gridx = 4;
			gbc_txtUser.gridy = 5;
			contentPanel.add(txtUser, gbc_txtUser);
			txtUser.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						settings.setNickname(txtUser.getText());
						settings.setAddress(txtAddr.getText());

						try {
							settings.setUdpPortR(Integer.parseInt(txtPortR.getText()));
							settings.setUdpPortS(Integer.parseInt(txtPortS.getText()));
							okbuttonPressed = true;
							dispose();
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null,
									"Wrong port format", "",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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

	public Settings getSettings() {
//		Ssettings.toString();
		return settings;
	}

}
