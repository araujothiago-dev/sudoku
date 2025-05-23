package br.com.dio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.model.enums.GameStatusEnum;

public class BoardService{
    private final static int BOARD_LIMIT = 9;

    private final Board board;

    public BoardService(final Map<String, String> gameConfig) {
        this.board = new Board(initBoard(gameConfig));
    }

    public List<List<Space>> getSpaces() {
        return board.getSpaces();
    }

    public void resetSpaces() {
        board.reset();
    }

    public boolean hasErrors() {
        return board.hasErrors();
    }

    public GameStatusEnum getStatus() {
        return board.getStatus();
    }

    public boolean gameFinished() {
        if (board.getStatus() != GameStatusEnum.COMPLETE) {
            System.out.println("O jogo não pode ser finalizado, pois ainda há espaços não preenchidos.");
            return false;
        }

        if (board.hasErrors()) {
            System.out.println("O jogo não pode ser finalizado, pois há espaços preenchidos incorretamente.");
            return false;
        }
        
        return board.gameFinished();
    }

    private List<List<Space>> initBoard(final Map<String, String> gameConfig) {
        List<List<Space>> spaces = new ArrayList<>();
		for (int i = 0; i < BOARD_LIMIT; i++) {
			spaces.add(new ArrayList<>());

			for (int j = 0; j < BOARD_LIMIT; j++) {
				var positionConfig = gameConfig.get("%s,%s".formatted(i, j));
				var expectad = Integer.parseInt(positionConfig.split(",")[0]);
				var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
				var currentSpace = new Space(expectad, fixed);
				spaces.get(i).add(currentSpace);
			}
		}
		return spaces;
    }
}
