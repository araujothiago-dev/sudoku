package br.com.dio.ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CheckGameStatusButton extends JButton {
    public CheckGameStatusButton(final ActionListener actionListener) {
        this.setText("Verificar Jogo");
        this.addActionListener(actionListener);
    }
}
