package TextEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.Charset;

public class FileOperations {

    private TextEditor textEditor;
    private JTextArea textArea;

    public FileOperations(TextEditor textEditor, JTextArea textArea) {
        this.textEditor = textEditor;
        this.textArea = textArea;
    }

    public void newFile() {
        if (!textArea.getText().isEmpty()) {
            int result = JOptionPane.showConfirmDialog(textEditor, "Save changes before creating a new file?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        textArea.setText("");
    }

    public void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        String userHome = System.getenv("USERPROFILE");
        File initialDirectory = new File(userHome + "\\OneDrive\\Bureau");
        fileChooser.setCurrentDirectory(initialDirectory);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(textEditor);
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
                JOptionPane.showMessageDialog(textEditor, "Error loading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(textEditor);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(textEditor, "File saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(textEditor, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
