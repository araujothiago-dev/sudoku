package br.com.dio.ui.custom.panel;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class MainPanel extends JPanel{
    public MainPanel(final Dimension dimension) {
        this.setSize(dimension);
        this.setPreferredSize(dimension);
    }
}
