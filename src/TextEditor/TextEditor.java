package TextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TextEditor extends JFrame {

	private JTextArea textArea;
	private JScrollPane scrollPane;

	public TextEditor() {
		setTitle("Tabbed Window");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout()); // Utiliser BorderLayout

		generateMenuBar();

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER); // Ajouter scrollPane au CENTRE
	}

	private void generateMenuBar() {
		// Créer la barre de menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Créer le menu "Fichier"
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolsMenu = new JMenu("Tools");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);

		JMenuItem newMenuItem = new JMenuItem("New");
		newMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFile();
			}
		});

		// Créer l'élément de menu "Ouvrir"
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
		});

		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		JMenuItem replaceMenuItem = new JMenuItem("Find & Replace");
		replaceMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				findAndReplace();
			}
		});

		JMenuItem countMenuItem = new JMenuItem("Word count");
		countMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	wordCount();
			}
		});

		JMenuItem frequencyMenuItem = new JMenuItem("Word Frequency");
		frequencyMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wordFrequency();
			}
		});

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		editMenu.add(replaceMenuItem);
		toolsMenu.add(countMenuItem);
		toolsMenu.add(frequencyMenuItem);

	}

	private void newFile() {
		if (!textArea.getText().isEmpty()) { // Vérifier si le texte a été modifié
			int result = JOptionPane.showConfirmDialog(this, "Save changes before creating a new file?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				saveFile(); // Appeler la fonction saveFile()
			} else if (result == JOptionPane.CANCEL_OPTION) {
				return; // Annuler la création du nouveau fichier
			}
		}
		textArea.setText(""); // Vider la zone de texte
	}

	private void loadFile() {
		JFileChooser fileChooser = new JFileChooser();
		// Obtenir le répertoire utilisateur
		String userHome = System.getenv("USERPROFILE");
		File initialDirectory = new File(userHome + "\\OneDrive\\Bureau"); // Chemin personnalisé

		fileChooser.setCurrentDirectory(initialDirectory);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
		fileChooser.setFileFilter(filter);
		int returnValue = fileChooser.showOpenDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), Charset.forName("Windows-1252")))) {
				StringBuilder content = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					content.append(line).append("\n");
				}
				textArea.setText(content.toString());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showSaveDialog(this);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
				writer.write(textArea.getText());
				JOptionPane.showMessageDialog(this, "File saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void findAndReplace() {
		String find = JOptionPane.showInputDialog(this, "Find:");
		if (find != null) {
			String replace = JOptionPane.showInputDialog(this, "Replace with:");
			if (replace != null) {
				String text = textArea.getText();
				text = text.replace(find, replace);
				textArea.setText(text);
			}
		}
	}

	private void wordCount() {
		String text = textArea.getText();
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "0 words", "Word Count", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String[] words = text.split("\\s+"); // Diviser le texte en mots
		int count = words.length;
		JOptionPane.showMessageDialog(this, count + " words", "Word Count", JOptionPane.INFORMATION_MESSAGE);
	}

	private void wordFrequency() {
		String text = textArea.getText();
		if (text.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No words to count.", "Word Frequency", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String[] words = text.toLowerCase().split("\\s+"); // Diviser en mots et convertir en minuscules
		Map<String, Integer> frequencyMap = new HashMap<>();
		for (String word : words) {
			frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
		}

		// Convertir la map en une liste de paires clé-valeur
		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(frequencyMap.entrySet());

		// Trier la liste par fréquence décroissante
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue()); // Tri décroissant
			}
		});

		// Construire la chaîne de résultat
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, Integer> entry : list) {
			result.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		}

		JTextArea frequencyTextArea = new JTextArea(result.toString());
		JScrollPane scrollPane = new JScrollPane(frequencyTextArea); // Ajout du scroll pane

		// Créer une nouvelle fenêtre JDialog
		JDialog dialog = new JDialog(this, "Word Frequency", true); // Mode modal
		dialog.getContentPane().add(scrollPane);
		dialog.setSize(400, 300); // Définir la taille de la fenêtre
		dialog.setLocationRelativeTo(this); // Centrer la fenêtre par rapport à la fenêtre principale
		dialog.setVisible(true); // Afficher la fenêtre
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			TextEditor frame = new TextEditor();
			frame.setVisible(true);
		});
	}
}