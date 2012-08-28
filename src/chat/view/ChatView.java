package chat.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridBagLayout;
import javax.swing.JButton;

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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

import chat.controller.IController;

public class ChatView extends JFrame {

	private IController controller;

	public void setController(IController controller) {
		this.controller = controller;
	}

	private JPanel contentPane;
	private JButton btnSend;
	private JTextArea messageTextArea;
	private JTextArea historyTextArea;
	private int historySize = 10;

	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					ChatView.this.setVisible(true);
					messageTextArea.requestFocus();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public void initialize() {
		controller.startChat();
		
		List<String> history = new LinkedList<String>();
		
		history = controller.getHistory();
			for (int i = historySize - 1; i >=0; i--) {
				if (history.size() >= i + 1){
					historyTextArea.append(history.get(history.size() - i - 1) + "\n");
				}
			}
	}
	
	public void update() {
		historyTextArea.setText("");
		List<String> history = controller.getHistory();
		for (int i = historySize - 1; i >=0; i--) {
			if (history.size() >= i + 1){
				historyTextArea.append(history.get(history.size() - i - 1) + "\n");
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
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = messageTextArea.getText();
				controller.addMessage(message);
//				historyTextArea.append(message + "\n");
				update();
				messageTextArea.setText("");
				messageTextArea.requestFocus();
			}
		});
		
		historyTextArea = new JTextArea();
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.gridheight = 5;
		gbc_textArea_1.gridwidth = 4;
		gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 1;
		gbc_textArea_1.gridy = 0;
		
		contentPane.add(historyTextArea, gbc_textArea_1);

		
		messageTextArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.gridwidth = 4;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 6;
		contentPane.add(messageTextArea, gbc_textArea);
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 8;
		contentPane.add(btnSend, gbc_btnSend);
	}

}
