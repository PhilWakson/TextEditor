package TextEditor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ToolsOperations {

    private TextEditor textEditor;
    private JTextArea textArea;

    public ToolsOperations(TextEditor textEditor, JTextArea textArea) {
        this.textEditor = textEditor;
        this.textArea = textArea;
    }

    public void wordCount() {
        String text = textArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(textEditor, "0 words", "Word Count", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] words = text.split("\\s+");
        int count = words.length;
        JOptionPane.showMessageDialog(textEditor, count + " words", "Word Count", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void wordFrequency() {
        String text = textArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(textEditor, "No words to count.", "Word Frequency", JOptionPane.INFORMATION_MESSAGE);
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
        JDialog dialog = new JDialog(textEditor, "Word Frequency", true); // Mode modal
        dialog.getContentPane().add(scrollPane);
        dialog.setSize(400, 300); // Définir la taille de la fenêtre
        dialog.setLocationRelativeTo(textEditor); // Centrer la fenêtre par rapport à la fenêtre principale
        dialog.setVisible(true); // Afficher la fenêtre
    }
}
