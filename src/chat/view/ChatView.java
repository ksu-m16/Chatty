package chat.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridBagLayout;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

import chat.controller.ChatController;
import chat.controller.IController;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.DropMode;

public class ChatView extends JFrame implements Runnable {

	private ChatController controller;

	public void setController(ChatController controller) {
		this.controller = controller;
	}

	private JPanel contentPane;
	final JScrollPane historyScrollPane;
	private JButton btnSend;
	private JTextArea msgTextArea;
	private JTextArea historyTextArea;
	private int historySize = 10;
	private JScrollPane msgScrollPane;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					
						ChatView.this.setVisible(true);
						getSettingsFromDialog();
					
						controller.startChat();
				
					
//					String val = JOptionPane.showInputDialog("Enter your name");
//					controller.setNickname(val);

					msgTextArea.requestFocus();
					Updater u = new Updater();
					u.start();
					
				
					
					

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		
	}

	public void initialize() {

		List<String> history = new LinkedList<String>();
		history = controller.getHistory();
		for (int i = historySize - 1; i >= 0; i--) {
			if (history.size() >= i + 1) {
				historyTextArea.append(history.get(history.size() - i - 1)
						+ "\n");
			}
		}
	}
	
	public void getSettingsFromDialog(){
		ChatSettings dialog = new ChatSettings(ChatView.this, true);
		dialog.setLocationRelativeTo(ChatView.this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		if (!dialog.isOkbuttonPressed()){
			ChatView.this.dispose();
			
		}
		controller.setNickname(dialog.getNickname());
		controller.setUdpPort(dialog.getUdpPort());
		controller.setUdpPortR(dialog.getUdpPortR());
		controller.setUdpPortS(dialog.getUdpPortS());
		controller.setIAddress(dialog.getIaddress());
//		System.out.println(dialog.getUdpPort() + " R:" + dialog.getUdpPortR() + " S:" +
//				dialog.getUdpPortS());
		
	}

	class Updater extends Thread {
		List<String> oldhistory = controller.getHistory();

		public void run() {
			while (true) {
				List<String> history = controller.getHistory();
				if (!oldhistory.equals(history)) {
					historyTextArea.setText("");
					for (int i = historySize - 1; i >= 0; i--) {
						if (history.size() >= i + 1) {
							historyTextArea.append(history.get(history.size()
									- i - 1)
									+ "\n");
						}
					}
					oldhistory = history;
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		btnSend = new JButton("Send");

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = msgTextArea.getText();
				controller.sendMessage(message);
				// update();
				msgTextArea.setText("");
				msgTextArea.requestFocus();
			}
		});

		historyTextArea = new JTextArea();
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.gridheight = 6;
		gbc_textArea_1.gridwidth = 3;
		gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 1;
		gbc_textArea_1.gridy = 0;

		historyScrollPane = new JScrollPane(historyTextArea);
		historyScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(historyScrollPane, gbc_textArea_1);

		// contentPane.add(historyTextArea, gbc_textArea_1);

		msgTextArea = new JTextArea();

		msgTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				int key = arg0.getKeyCode();
				// if (key == KeyEvent.VK_ENTER && arg0.isControlDown()) {
				if (key == KeyEvent.VK_ENTER) {
					String message = msgTextArea.getText();
					controller.sendMessage(message);
					// historyTextArea.append(message + "\n");
					// update();
					msgTextArea.setText("");
					msgTextArea.requestFocus();
				}
			}
		});

		msgTextArea.setRows(3);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 7;
		// contentPane.add(msgTextArea, gbc_textArea);

		msgScrollPane = new JScrollPane(msgTextArea);
		msgScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(msgScrollPane, gbc_textArea);

		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 9;
		contentPane.add(btnSend, gbc_btnSend);

	}
	
	

}
