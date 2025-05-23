package br.com.dio.ui.custom.input;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.dio.model.Space;

public class NumberText extends JTextField {
    private final Space space;

    public NumberText(final Space space) {
        this.space = space;
        var dimension = new Dimension(50, 50);

        this.setSize(dimension);
        this.setPreferredSize(dimension);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixed());
        if (space.isFixed()) {
            this.setText(space.getActual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {
            
            private void changeSpace() {
                if (getText().isEmpty()) {
                    space.clearSpace();
                    return;
                }
                space.setActual(Integer.parseInt(getText()));

            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();

                // space.setActual(0);
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();

                // space.setActual(0);
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                changeSpace();
            }
        });
    }
}
