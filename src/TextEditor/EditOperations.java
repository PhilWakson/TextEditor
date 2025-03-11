package TextEditor;

import javax.swing.*;

public class EditOperations {

    private JTextArea textArea;

    public EditOperations(JTextArea textArea) {
        this.textArea = textArea;
    }

    public void findAndReplace() {
        String find = JOptionPane.showInputDialog(null, "Find:");
        if (find != null) {
            String replace = JOptionPane.showInputDialog(null, "Replace with:");
            if (replace != null) {
                String text = textArea.getText();
                text = text.replace(find, replace);
                textArea.setText(text);
            }
        }
    }
}
