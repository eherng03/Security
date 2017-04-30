package graphic;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.Machine;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class MainWindow {

	private JFrame frame;
	private File originalFile;
	private File cifratedFile;
	private String cifratedMessage;
	private Machine enigma;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(102, 55, 0));
		frame.setBounds(100, 100, 450, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel image = new EnigmaImage(414, 239);
		image.setBounds(10, 11, 414, 239);
		frame.getContentPane().add(image);
		
		JButton btnCifrarMensaje = new JButton("Cifrar mensaje");
		btnCifrarMensaje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectFile();
				cifrar();
				saveFile();
			}
		});
		btnCifrarMensaje.setBackground(SystemColor.activeCaptionBorder);
		btnCifrarMensaje.setFont(new Font("Candara", Font.BOLD, 18));
		btnCifrarMensaje.setBounds(10, 261, 414, 47);
		frame.getContentPane().add(btnCifrarMensaje);
	}

	/**
	 * 
	 */
	protected void saveFile() {
		String path = null;
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			path = file.getAbsolutePath();
		}
		try{
			FileWriter fileWritter = new FileWriter(path);
		    PrintWriter writer = new PrintWriter(fileWritter);
		    writer.println(cifratedMessage);
		    writer.close();
		    fileWritter.close();
		} catch (IOException e) {
		}
		
	}

	/**
	 * 
	 */
	protected void cifrar() {
		SelectRotorsWindow selectRotors = new SelectRotorsWindow(this);
		selectRotors.setVisible(true);
		
		try {
			cifratedMessage = enigma.cifrate(originalFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMachine(Machine machine){
		this.enigma = machine;
	}

	/**
	 * 
	 */
	protected void selectFile() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("./"));
		int result = fileChooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    originalFile = fileChooser.getSelectedFile();
		}
	}
}
