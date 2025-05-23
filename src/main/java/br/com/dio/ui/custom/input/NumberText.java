package br.com.dio.ui.custom.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import br.com.dio.model.Space;
import br.com.dio.service.EventsListener;
import br.com.dio.service.enums.EventEnum;

public class NumberText extends JTextField implements EventsListener {
    private final Space space;
    private static int errorCount = 0;
    private static final int MAX_ERRORS = 3;
    private static boolean gameBlocked = false;
    private static List<NumberText> allFields;
    private boolean isDraftMode = false; // Flag para o modo de rascunho

    public NumberText(final Space space, final List<NumberText> allFields) {
        this.space = space;
        NumberText.allFields = allFields;
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
                if (gameBlocked) {
                    return;
                }
                if (getText().isEmpty()) {
                    space.clearSpace();
                    setForeground(Color.BLACK);
                    return;
                }
                if (isDraftMode) {
                    // No modo de rascunho, não valida erros
                    setForeground(Color.BLUE); // Altera a cor para azul no modo de rascunho
                    return;
                }
                try {
                    int value = Integer.parseInt(getText());
                    if (value != space.getExpected()) {
                        setForeground(Color.RED);
                        errorCount++;
                        if (errorCount >= MAX_ERRORS) {
                            gameBlocked = true;
                            JOptionPane.showMessageDialog(null,
                                    "Você atingiu o limite de erros! Todos os campos não fixos serão bloqueados.");
                            blockAllNonFixedFields();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Valor incorreto! Erros restantes: " + (MAX_ERRORS - errorCount));
                        }
                    } else {
                        setForeground(Color.BLACK);
                        space.setActual(value);
                    }
                } catch (NumberFormatException e) {
                    setForeground(Color.RED);
                    JOptionPane.showMessageDialog(null, "Por favor, insira um número válido.");
                }
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                changeSpace();
            }
        });
    }

    private void blockAllNonFixedFields() {
        for (NumberText field : allFields) {
            if (!field.space.isFixed()) {
                field.setEnabled(false);
            }
        }
    }

    public void alterGameMode() {
        isDraftMode = !isDraftMode; // Alterna entre os modos
        setForeground(isDraftMode ? Color.BLUE : Color.BLACK); // Altera a cor para indicar o modo
    }

    @Override
    public void update(final EventEnum eventType) {
        if (eventType.equals(EventEnum.CLEAR_SPACE) && !space.isFixed()) {
            this.setText("");
            setForeground(Color.BLACK);
            this.setEnabled(!space.isFixed());
            errorCount = 0;
            gameBlocked = false;
        }
    }
}
