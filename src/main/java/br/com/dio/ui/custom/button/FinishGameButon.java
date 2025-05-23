package br.com.dio.ui.custom.button;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class FinishGameButon extends JButton {
    public FinishGameButon(final ActionListener actionListener) {
        this.setText("Concluir Jogo");
        this.addActionListener(actionListener);
    }
}
