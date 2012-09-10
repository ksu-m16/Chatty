package chat.view;

import javax.swing.ButtonGroup;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextArea;
import chat.controller.ChatController;
import chat.controller.IChatListener;
import chat.model.MessageRecord;
import javax.swing.ScrollPaneConstants;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class ChatView extends JFrame implements IChatListener {

	private ChatController controller;
	private JPanel contentPane;
	private JScrollPane historyScrollPane;
	private JButton btnSend;
	private JTextArea msgTextArea;
	private JTextArea historyTextArea;
	private int historySize = 10;
	private JScrollPane msgScrollPane;
	private boolean enterMode = true;
	private JRadioButton rdbtnSendByEnter;
	private JRadioButton rdbtnSendByCtrl;

	/**
	 * Launch the application.
	 */

	public void showChatView() {

		ChatView.this.setVisible(true);

		msgTextArea.requestFocus();

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
		historyTextArea.setCaretPosition(historyTextArea.getText().length());
		setTitle("xChaTTY: " + controller.getSettings().getNickname());
		controller.addChatListener(ChatView.this);
	}

	/**
	 * Create the frame.
	 */
	public ChatView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 72, 70, 53, 110 };
		gbl_contentPane.rowHeights = new int[] { 54, 32, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
		gbl_contentPane.rowWeights = new double[] { 2.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		historyTextArea = new JTextArea();
		historyTextArea.setEditable(false);
		historyTextArea.setLineWrap(true);
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.gridwidth = 4;
		gbc_textArea_1.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_1.fill = GridBagConstraints.BOTH;
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 0;

		historyScrollPane = new JScrollPane(historyTextArea);
		historyScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(historyScrollPane, gbc_textArea_1);


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
						historyTextArea.setCaretPosition(historyTextArea
								.getText().length());
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
						historyTextArea.setCaretPosition(historyTextArea
								.getText().length());
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
		
		
		btnSend = new JButton("Send");
		btnSend.setPreferredSize(new Dimension(50, 23));
		btnSend.setMinimumSize(new Dimension(50, 23));
		btnSend.setMaximumSize(new Dimension(50, 23));

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
		
		rdbtnSendByCtrl = new JRadioButton("Send by Ctrl+Enter");
		rdbtnSendByCtrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enterMode = false;
				msgTextArea.requestFocus();
			}
		});
		GridBagConstraints gbc_rdbtnSendBy = new GridBagConstraints();
		gbc_rdbtnSendBy.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnSendBy.gridx = 0;
		gbc_rdbtnSendBy.gridy = 2;
		contentPane.add(rdbtnSendByCtrl, gbc_rdbtnSendBy);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnSendByCtrl);
		
				rdbtnSendByEnter = new JRadioButton("Send by Enter");
				rdbtnSendByEnter.setHorizontalAlignment(SwingConstants.LEFT);
				rdbtnSendByEnter.addActionListener(new ActionListener() {
					

					public void actionPerformed(ActionEvent arg0) {
						enterMode = true;
						msgTextArea.requestFocus();
					}
				});
				rdbtnSendByEnter.setSelected(true);
				GridBagConstraints gbc_rdbtnSendByEnter = new GridBagConstraints();
				gbc_rdbtnSendByEnter.insets = new Insets(0, 0, 0, 5);
				gbc_rdbtnSendByEnter.gridx = 1;
				gbc_rdbtnSendByEnter.gridy = 2;
				contentPane.add(rdbtnSendByEnter, gbc_rdbtnSendByEnter);
				group.add(rdbtnSendByEnter);
		
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSend.insets = new Insets(0, 20, 0, 0);
		gbc_btnSend.gridx = 3;
		gbc_btnSend.gridy = 2;
		contentPane.add(btnSend, gbc_btnSend);

	}

	@Override
	public void update(MessageRecord incomingMsg) {
		historyTextArea.append(incomingMsg.toString() + "\n");
		historyTextArea.setCaretPosition(historyTextArea.getText().length());

	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}
}
