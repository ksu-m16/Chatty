package chat.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;
import chat.controller.ChatController;
import chat.controller.IChatListener;

import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;

public class ChatView extends JFrame implements Runnable, IChatListener {

	private ChatController controller;
	private JPanel contentPane;
//	final JScrollPane historyScrollPane;
	private JScrollPane historyScrollPane;
	private JButton btnSend;
	private JTextArea msgTextArea;
	private JTextArea historyTextArea;
	private int historySize = 10;
	private JScrollPane msgScrollPane;
	private JButton btnEnter;
	private JButton btnCtrlEnter;
	private boolean enterMode = true;

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
//					controller.getNetClient().addChatListener(ChatView.this);
					controller.addChatListener(ChatView.this);
					msgTextArea.requestFocus();

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
				historyTextArea.append(history.get(history.size() - i - 1) + "\n");
			}
		}
	}

	public void getSettingsFromDialog() {
		ChatSettingsDialog dialog = new ChatSettingsDialog(ChatView.this, true);
		dialog.setLocationRelativeTo(ChatView.this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		
		if (!dialog.isOkbuttonPressed()) {
			ChatView.this.dispose();
		}
		
		controller.setNickname(dialog.getNickname());
		controller.setUdpPort(dialog.getUdpPort());
		controller.setUdpPortR(dialog.getUdpPortR());
		controller.setUdpPortS(dialog.getUdpPortS());
		controller.setIAddress(dialog.getAddress());
		
		ChatView.this.setTitle("xChaTTY: " + controller.getNickname());
	}

	/**
	 * Create the frame.
	 */
	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 244, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {72, 70, 70, 0};
		gbl_contentPane.rowHeights = new int[] { 54, 32, 0,
				0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
		gbl_contentPane.rowWeights = new double[] { 2.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		btnSend = new JButton("Send");

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = msgTextArea.getText();
				controller.sendMessage(message);
				historyTextArea.append(controller
						.generateMessageRecord(message) + "\n");
				msgTextArea.setText("");
				msgTextArea.requestFocus();
			}
		});

		historyTextArea = new JTextArea();
		historyTextArea.setLineWrap(true);
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.gridwidth = 4;
		gbc_textArea_1.insets = new Insets(0, 0, 0, 0);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 0;

		historyScrollPane = new JScrollPane(historyTextArea);
		historyScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(historyScrollPane, gbc_textArea_1);

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String message = msgTextArea.getText();
				controller.sendMessage(message);
				historyTextArea.append(controller
						.generateMessageRecord(message) + "\n");
				msgTextArea.setText("");
				msgTextArea.requestFocus();
			}
		});

		btnEnter = new JButton("Enter");
		btnEnter.setVerticalAlignment(SwingConstants.BOTTOM);
		btnEnter.setSize(new Dimension(65, 23));
		btnEnter.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnEnter.setHorizontalAlignment(SwingConstants.LEFT);
		btnEnter.setPreferredSize(new Dimension(65, 23));
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setEnterMode(true);
			}
		});
		
				msgTextArea = new JTextArea();
				msgTextArea.setLineWrap(true);
				// setWrapStyleWord(true);
				msgTextArea.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent arg0) {
						int key = arg0.getKeyCode();

						if (enterMode) {
							if (key == KeyEvent.VK_ENTER && !arg0.isControlDown()) {
								String message = msgTextArea.getText();
								controller.sendMessage(message);
								historyTextArea.append(controller
										.generateMessageRecord(message) + "\n");
								msgTextArea.setText("");
								msgTextArea.requestFocus();
								arg0.consume();
							}
							if (key == KeyEvent.VK_ENTER && arg0.isControlDown()) {
								msgTextArea.append("\n");
								msgTextArea.requestFocus();
							}
						} else {
							if (key == KeyEvent.VK_ENTER && arg0.isControlDown()) {
								String message = msgTextArea.getText();
								controller.sendMessage(message);
								historyTextArea.append(controller
										.generateMessageRecord(message) + "\n");
								msgTextArea.setText("");
								msgTextArea.requestFocus();
								arg0.consume();
							}
						}
					}
				});
				
						msgTextArea.setRows(3);
						GridBagConstraints gbc_textArea = new GridBagConstraints();
						gbc_textArea.insets = new Insets(10, 0, 10, 0);
						gbc_textArea.gridwidth = 4;
						gbc_textArea.fill = GridBagConstraints.BOTH;
						gbc_textArea.gridx = 0;
						gbc_textArea.gridy = 1;
						
						msgScrollPane = new JScrollPane(msgTextArea);
						msgScrollPane
								.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						contentPane.add(msgScrollPane, gbc_textArea);
		btnEnter.setEnabled(false);
		btnEnter.setMinimumSize(new Dimension(65, 23));
		btnEnter.setMaximumSize(new Dimension(65, 23));
		GridBagConstraints gbc_btnE = new GridBagConstraints();
		gbc_btnE.fill = GridBagConstraints.BOTH;
		gbc_btnE.insets = new Insets(0, 0, 0, 5);
		gbc_btnE.gridx = 0;
		gbc_btnE.gridy = 2;
		contentPane.add(btnEnter, gbc_btnE);

		btnCtrlEnter = new JButton("Ctrl+E");
		btnCtrlEnter.setVerticalAlignment(SwingConstants.BOTTOM);
		btnCtrlEnter.setSize(new Dimension(65, 23));
		btnCtrlEnter.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnCtrlEnter.setHorizontalAlignment(SwingConstants.LEFT);
		btnCtrlEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setEnterMode(false);
			}

		});
		GridBagConstraints gbc_btnCe = new GridBagConstraints();
		gbc_btnCe.fill = GridBagConstraints.BOTH;
		gbc_btnCe.insets = new Insets(0, 0, 0, 5);
		gbc_btnCe.gridx = 1;
		gbc_btnCe.gridy = 2;
		contentPane.add(btnCtrlEnter, gbc_btnCe);

		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSend.insets = new Insets(0, 20, 0, 5);
		gbc_btnSend.gridx = 2;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);

	}

	@Override
	public void update(String incomingMsg) {
		// historyTextArea.append(incomingMsg + "\n");
		historyTextArea.append(controller.getTextFromSerializedMsg(incomingMsg)
				+ "\n");
		historyTextArea.setCaretPosition(historyTextArea.getText().length());

	}

	private void setEnterMode(boolean b) {
		if (b) {
			btnEnter.setEnabled(false);
			btnCtrlEnter.setEnabled(true);
			enterMode = true;
		} else {
			btnCtrlEnter.setEnabled(false);
			btnEnter.setEnabled(true);
			enterMode = false;
		}
		msgTextArea.requestFocus();
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}
}
