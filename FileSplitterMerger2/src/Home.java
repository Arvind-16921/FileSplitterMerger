import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Home extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private CardLayout cardLayout;
	private JFileChooser jfc;
	private File files[];
	private FileInputStream fin2[];
	private FileOutputStream fout2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 858, 535);
		setTitle("File Splitter-Merger");
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel.add(panel_1, "card1");
		panel_1.setLayout(new GridLayout(0, 2, 20, 0));

		JButton btnNewButton = new JButton("FileSplitter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "card2");
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 128));
		btnNewButton.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 70));
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("FileMerger");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "card3");
			}
		});
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.setBackground(new Color(0, 0, 128));
		btnNewButton_1.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 70));
		panel_1.add(btnNewButton_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 255, 255));
		panel.add(panel_2, "card2");
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("File Splitter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 99));
		lblNewLabel.setBounds(10, 10, 814, 111);
		panel_2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("SelectedFileName");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 203, 814, 24);
		panel_2.add(lblNewLabel_1);
		
		JButton btnNewButton_2 = new JButton("Select the file");
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc = new JFileChooser();
				int r = jfc.showOpenDialog(btnNewButton_2);
				if(r == JFileChooser.APPROVE_OPTION) {
					lblNewLabel_1.setText(jfc.getSelectedFile().getPath());
				}
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_2.setBackground(new Color(0, 0, 128));
		btnNewButton_2.setBounds(270, 156, 303, 38);
		panel_2.add(btnNewButton_2);

		

		JLabel lblNewLabel_2 = new JLabel("Size of the file (MB)");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel_2.setBounds(79, 269, 313, 38);
		panel_2.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 26));
		textField.setBounds(436, 268, 308, 40);
		panel_2.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_3 = new JButton("Home");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "card1");
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_3.setBounds(79, 363, 303, 45);
		panel_2.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Submit");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fout[] = null;
				try {
					FileInputStream fin = new FileInputStream(lblNewLabel_1.getText());
					long size = Long.parseLong(textField.getText()) * 1024 * 1024;
					long available = fin.available();
					int parts = (int) Math.ceil((available) / (size * 1.0));
					fout = new FileOutputStream[parts];
					for(int i = 0; i < fout.length; i++)
						fout[i] = new FileOutputStream("FileParts_"+(i+1));
					int i = 0;
					for(i = 0; i < fout.length - 1; i++)
						for(int j = 0; j < size; j++)
							fout[i].write(fin.read());
					int d = 0;
					while((d = fin.read()) != -1)
						fout[i].write(d);
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				finally {
					if(fout != null)
						for(int j = 0; j < fout.length; j++)
							if(fout[j] != null)
								try {
								fout[j].close();
								}
					catch(Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
				JOptionPane.showMessageDialog(null, "File Splitted Successfully");
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_4.setBounds(436, 363, 308, 45);
		panel_2.add(btnNewButton_4);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 125, 814, 2);
		panel_2.add(separator_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 255, 255));
		panel.add(panel_3, "card3");
		panel_3.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("File Merger");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Edwardian Script ITC", Font.PLAIN, 99));
		lblNewLabel_3.setBounds(10, 10, 814, 118);
		panel_3.add(lblNewLabel_3);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 126, 814, 2);
		panel_3.add(separator);

		JButton btnNewButton_5 = new JButton("Select the files");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setMultiSelectionEnabled(true);
				int option = fileChooser.showOpenDialog(btnNewButton_5);
				if(option == JFileChooser.APPROVE_OPTION) {
					files = fileChooser.getSelectedFiles();
					fin2 = new FileInputStream[files.length];
					try {
						fout2 = new FileOutputStream("Output");
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
			}
		});
		btnNewButton_5.setForeground(new Color(255, 255, 255));
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_5.setBackground(new Color(0, 0, 128));
		btnNewButton_5.setBounds(84, 181, 674, 45);
		panel_3.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("Home");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "card1");
			}
		});
		btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_6.setBounds(84, 363, 301, 45);
		panel_3.add(btnNewButton_6);

		JButton btnNewButton_7 = new JButton("Submit");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int d = 0;
				for(int i = 0; i < files.length; i++) {
					try {
						fin2[i] = new FileInputStream(files[i]);
						while((d = fin2[i].read()) != -1)
							fout2.write(d);
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
				JOptionPane.showMessageDialog(null, "File merging completed");
			}
		});
		btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 26));
		btnNewButton_7.setBounds(457, 363, 301, 45);
		panel_3.add(btnNewButton_7);

		JLabel lblNewLabel_4 = new JLabel("Note: Select all the files in the same order in which need to merge");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(148, 249, 537, 34);
		panel_3.add(lblNewLabel_4);
	}
}
