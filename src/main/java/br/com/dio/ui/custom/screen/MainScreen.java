package br.com.dio.ui.custom.screen;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.dio.model.Space;
import br.com.dio.model.enums.GameStatusEnum;
import br.com.dio.service.BoardService;
import br.com.dio.service.NotifierService;
import br.com.dio.service.enums.EventEnum;
import br.com.dio.ui.custom.button.CheckGameStatusButton;
import br.com.dio.ui.custom.button.FinishGameButon;
import br.com.dio.ui.custom.button.ResetButton;
import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.input.NumberText;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.panel.SudokuSector;

public class MainScreen {
    private final static Dimension dimension = new Dimension(600, 600);
    private final BoardService boardService;
    private final NotifierService notifierService;

    private JButton checkGameStatusButton;
    private JButton finishGameButton;
    private JButton resetButton;
    private final List<NumberText> allFields = new ArrayList<>(); // Lista global para armazenar todos os campos

    public MainScreen(final Map<String, String> gameConfig) {
        this.boardService = new BoardService(gameConfig);
        this.notifierService = new NotifierService();
    }

    public void buildMainScreen() {
        JPanel mainPanel = new MainPanel(dimension);
        JFrame mainFrame = new MainFrame(dimension, mainPanel);
        for (int r = 0; r < 9; r += 3) {
            var endRow = r + 2;
            for (int c = 0; c < 9; c += 3) {
                var endcol = c + 2;
                var spaces = getSpacesFromSector(boardService.getSpaces(), c, endcol, r, endRow);
                var sector = generateSection(spaces);
                mainPanel.add(sector);
            }
        }
        addResutButton(mainPanel);
        addCheckGameStatusButton(mainPanel);
        addFinishGameButton(mainPanel);
        addAlterModeButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Space> getSpacesFromSector(final List<List<Space>> spaces,
            final int initCol, final int endCol,
            final int initRow, final int endRow) {
        List<Space> spaceSector = new ArrayList<>();
        for (int r = initRow; r <= endRow; r++) {
            for (int c = initCol; c <= endCol; c++) {
                spaceSector.add(spaces.get(c).get(r));
            }
        }
        return spaceSector;
    }

    private JPanel generateSection(final List<Space> spaces) {
        List<NumberText> fields = new ArrayList<>(spaces.stream()
                .map(space -> new NumberText(space, allFields)) // Passa a lista global como segundo parâmetro
                .toList());
        allFields.addAll(fields); // Adiciona os campos criados à lista global
        fields.forEach(t -> notifierService.subscribe(EventEnum.CLEAR_SPACE, t));
        return new SudokuSector(fields);
    }

    private void addFinishGameButton(final JPanel mainPanel) {
        finishGameButton = new FinishGameButon(e -> {
            if (boardService.gameFinished()) {
                showMessageDialog(null, "Parabéns você concluiu o jogo! ");
                resetButton.setEnabled(false);
                checkGameStatusButton.setEnabled(false);
                finishGameButton.setEnabled(false);
            } else {
                var message = "Seu jogo tem alguma inconsistência, ajuste e tente novamente. ";
                showMessageDialog(null, message);
            }
        });
        mainPanel.add(finishGameButton);
    }

    private void addCheckGameStatusButton(final JPanel mainPanel) {
        checkGameStatusButton = new CheckGameStatusButton(e -> {
            var hasErrors = boardService.hasErrors();
            var gameStatus = boardService.getStatus();
            var message = switch (gameStatus) {
                case NON_STARTED -> "O jogo não foi iniciado";
                case INCOMPLETE -> "O jogo está imcompleto";
                case COMPLETE -> "O jogo está completo";
            };
            message += hasErrors ? " e contém erros" : " e não contém erros";
            showMessageDialog(null, message);
        });
        mainPanel.add(MainScreen.this.checkGameStatusButton);
    }

    private void addResutButton(JPanel mainPanel) {
        resetButton = new ResetButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja realmente reiniciar o jogo?",
                    "Limpar o jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (dialogResult == 0) {
                boardService.resetSpaces();
                notifierService.notify(EventEnum.CLEAR_SPACE);
            }
        });
        mainPanel.add(resetButton);
    }

    private void addAlterModeButton(JPanel mainPanel) {
        JButton alterModeButton = new JButton("Alternar para Rascunho");
        alterModeButton.addActionListener(e -> {
            allFields.forEach(NumberText::alterGameMode);
        });
        if (boardService.getStatus() != GameStatusEnum.INCOMPLETE) {
            
        }
        mainPanel.add(alterModeButton);
    }
}
