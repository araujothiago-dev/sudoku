// package br.com.dio;

// import static java.util.Objects.isNull;
// import static java.util.Objects.nonNull;

// import java.util.*;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// import br.com.dio.model.Board;
// import br.com.dio.model.Space;
// import br.com.dio.util.BoardTemplate;

// @SpringBootApplication
// public class SudokuApplication {
// 	static Scanner scanner = new Scanner(System.in);
// 	private static Board board;
// 	private final static int BOARD_LIMIT = 9;
// 	private static int BOARD_ERRORS = 3;

// 	public static void main(String[] args) {
// 		// // Verifica se os argumentos foram passados
// 		// if (args.length == 0) {
// 		// System.out.println("Por favor, forneça os argumentos do tabuleiro Sudoku.");
// 		// return;
// 		// }

// 		// // Processa os argumentos e armazena em uma lista
// 		// List<String> linhas = new ArrayList<>();
// 		// for (String arg : args) {
// 		// linhas.add(arg);
// 		// }

// 		// // Exibe os argumentos processados
// 		// System.out.println("Tabuleiro recebido:");
// 		// linhas.forEach(System.out::println);

// 		// Aqui você pode continuar o processamento do tabuleiro
// 		final var positions = Stream.of(args)
// 				.collect(Collectors.toMap(
// 						k -> k.split(";")[0],
// 						v -> v.split(";")[1]));

// 		var options = -1;

// 		while (true) {

// 			if (BOARD_ERRORS <= 0) {
// 				System.out.println("Você deve iniciar um novo jogo.");
// 			}

// 			System.out.println("Escolha uma opção:");
// 			System.out.println("1 - Novo jogo");
// 			System.out.println("2 - Novo número");
// 			System.out.println("3 - Remover número");
// 			System.out.println("4 - Jogo Atual");
// 			System.out.println("5 - Status do jogo");
// 			System.out.println("6 - limpar jogo");
// 			System.out.println("7 - Finalizar jogo");
// 			System.out.println("0 - Sair");
// 			System.out.println("==============================");
// 			System.out.println("Você terá " + BOARD_ERRORS + " chances de erros. ");

// 			options = scanner.nextInt();

// 			switch (options) {
// 				case 1:
// 					startGame(buildInitialPositions());
// 					break;
// 				case 2:
// 					inputNumber();
// 					break;
// 				case 3:
// 					removeNumber();
// 					break;
// 				case 4:
// 					showCurrentGame();
// 					break;
// 				case 5:
// 					showCurrentStatus();
// 					break;
// 				case 6:
// 					clearGame();
// 					break;
// 				case 7:
// 					finishGame();
// 					break;
// 				case 0:
// 					System.out.println("Saindo...");
// 					return;
// 				default:
// 					System.out.println("Opção inválida. Tente novamente.");
// 			}
// 		}
// 	}

// 	private static void startGame(final Map<String, String> positions) {
// 		if (nonNull(board)) {
// 			System.out.println("Jogo já iniciado...");
// 			return;
// 		}

// 		List<List<Space>> spaces = new ArrayList<>();
// 		for (int i = 0; i < BOARD_LIMIT; i++) {
// 			spaces.add(new ArrayList<>());

// 			for (int j = 0; j < BOARD_LIMIT; j++) {
// 				var positionConfig = positions.get("%s,%s".formatted(i, j));
// 				var expectad = Integer.parseInt(positionConfig.split(",")[0]);
// 				var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
// 				var currentSpace = new Space(expectad, fixed);
// 				spaces.get(i).add(currentSpace);
// 			}
// 		}
// 		board = new Board(spaces);
// 		System.out.println("Novo jogo iniciado com sucesso!");
// 		showCurrentGame();
// 	}

// 	private static void inputNumber() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		System.out.println("Informe a coluna: ");
// 		var column = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
// 		System.out.println("Informe a linha: ");
// 		var row = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
// 		System.out.printf("Informe o número para a posição [%s e %s]: ", column, row);
// 		var value = runUntilGetValidNumber(1, 9);

// 		if (!board.changeValue(column, row, value)) {
// 			System.out.printf("A posição [%s, %s] já está preenchida.", column, row);
// 		}

// 		showCurrentGame();

// 		if (board.hasErrors()) {
// 			BOARD_ERRORS--;
// 			System.out.printf("A posição [%s, %s] está errada. Você possui " + BOARD_ERRORS + " chance(s). ", column,
// 					row);
// 		}

// 	}

// 	private static void removeNumber() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		System.out.println("Informe a coluna: ");
// 		var column = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
// 		System.out.println("Informe a linha: ");
// 		var row = runUntilGetValidNumber(0, BOARD_LIMIT - 1);
// 		System.out.println("Número removido com sucesso!");
// 		// System.out.printf("Informe o número para a posição [%s e %s]: ", column,
// 		// row);
// 		// var value = runUntilGetValidNumber(1, 9);

// 		if (!board.clearValue(row, column)) {
// 			System.out.printf("A posição [%s, %s] não está preenchida.", column, row);
// 		}

// 		showCurrentGame();
// 	}

// 	private static void showCurrentGame() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		var args = new Object[81];
// 		var argsPos = 0;
// 		for (int i = 0; i < BOARD_LIMIT; i++) {
// 			for (var column : board.getSpaces()) {
// 				args[argsPos++] = " " + ((isNull(column.get(i).getActual())) ? " " : column.get(i).getActual());
// 			}
// 		}

// 		System.out.println("Seu jogo está assim: ");
// 		System.out.printf((BoardTemplate.BOARD_TEMPLATE) + "\n", args);

// 	}

// 	private static void showCurrentStatus() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		System.out.printf("Status do jogo: %s\n", board.getStatus().getLabel());
// 		if (board.hasErrors()) {
// 			System.out.println("O jogo possui erros.");
// 		} else {
// 			System.out.println("O jogo não possui erros.");
// 		}

// 	}

// 	private static void clearGame() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		System.out.println("Tem certeza que deseja limpar o jogo? (s/n)");

// 		var option = scanner.next().toLowerCase();

// 		while (!option.equals("s") && !option.equals("n")) {
// 			System.out.println("Opção inválida. Informe 's' para sim ou 'n' para não.");
// 			option = scanner.next().toLowerCase();
// 		}

// 		if (option.equals("s")) {
// 			System.out.println("Limpando o jogo...");
// 			board.reset();
// 		}
// 		showCurrentGame();
// 	}

// 	private static void finishGame() {
// 		if (isNull(board)) {
// 			System.out.println("Nenhum jogo iniciado.");
// 			return;
// 		}

// 		System.out.println("Tem certeza que deseja finalizar o jogo? (s/n)");
// 		var option = scanner.next().toLowerCase();
// 		while (!option.equals("s") && !option.equals("n")) {
// 			System.out.println("Opção inválida. Informe 's' para sim ou 'n' para não.");
// 			option = scanner.next().toLowerCase();
// 		}

// 		if (option.equals("n")) {
// 			System.out.println("Jogo não finalizado...");
// 			showCurrentGame();
// 			return;
// 		}

// 		if (board.gameFinished()) {
// 			System.out.println("Finalizando o jogo...");

// 			showCurrentGame();
// 			board = null;

// 			System.out.println("Jogo finalizado com sucesso!");
// 		} else if (board.hasErrors()) {
// 			System.out.println("O jogo possui erros. Não é possível finalizar.");
// 		} else {
// 			System.out.println("Voce ainda precisa preencher algum espaço.");
// 		}
// 		showCurrentGame();

// 	}

// 	private static int runUntilGetValidNumber(final int min, final int max) {
// 		var number = scanner.nextInt();
// 		while (number < min || number > max) {
// 			System.out.printf("Informe um número entre %s e %s\n: ", min, max);
// 			number = scanner.nextInt();
// 		}
// 		return number;
// 	}

// 	private static Map<String, String> buildInitialPositions() {
// 		Map<String, String> positions = new HashMap<>();

// 		positions.put("0,0", "4,false");
// 		positions.put("1,0", "7,false");
// 		positions.put("2,0", "9,true");
// 		positions.put("3,0", "5,false");
// 		positions.put("4,0", "8,true");
// 		positions.put("5,0", "6,true");
// 		positions.put("6,0", "2,true");
// 		positions.put("7,0", "3,false");
// 		positions.put("8,0", "1,false");

// 		positions.put("0,1", "1,false");
// 		positions.put("1,1", "3,true");
// 		positions.put("2,1", "5,false");
// 		positions.put("3,1", "4,false");
// 		positions.put("4,1", "7,true");
// 		positions.put("5,1", "2,false");
// 		positions.put("6,1", "8,false");
// 		positions.put("7,1", "9,true");
// 		positions.put("8,1", "6,true");

// 		positions.put("0,2", "2,false");
// 		positions.put("1,2", "6,true");
// 		positions.put("2,2", "8,false");
// 		positions.put("3,2", "9,false");
// 		positions.put("4,2", "1,true");
// 		positions.put("5,2", "3,false");
// 		positions.put("6,2", "7,false");
// 		positions.put("7,2", "4,false");
// 		positions.put("8,2", "5,true");

// 		positions.put("0,3", "5,true");
// 		positions.put("1,3", "1,false");
// 		positions.put("2,3", "3,true");
// 		positions.put("3,3", "7,false");
// 		positions.put("4,3", "6,false");
// 		positions.put("5,3", "4,false");
// 		positions.put("6,3", "9,false");
// 		positions.put("7,3", "8,true");
// 		positions.put("8,3", "2,false");

// 		positions.put("0,4", "8,false");
// 		positions.put("1,4", "9,true");
// 		positions.put("2,4", "7,false");
// 		positions.put("3,4", "1,true");
// 		positions.put("4,4", "2,true");
// 		positions.put("5,4", "5,true");
// 		positions.put("6,4", "3,false");
// 		positions.put("7,4", "6,true");
// 		positions.put("8,4", "4,false");

// 		positions.put("0,5", "6,false");
// 		positions.put("1,5", "4,true");
// 		positions.put("2,5", "2,false");
// 		positions.put("3,5", "3,false");
// 		positions.put("4,5", "9,false");
// 		positions.put("5,5", "8,false");
// 		positions.put("6,5", "1,true");
// 		positions.put("7,5", "5,false");
// 		positions.put("8,5", "7,true");

// 		positions.put("0,6", "7,true");
// 		positions.put("1,6", "5,false");
// 		positions.put("2,6", "4,false");
// 		positions.put("3,6", "2,false");
// 		positions.put("4,6", "3,true");
// 		positions.put("5,6", "9,false");
// 		positions.put("6,6", "6,false");
// 		positions.put("7,6", "1,true");
// 		positions.put("8,6", "8,false");

// 		positions.put("0,7", "9,true");
// 		positions.put("1,7", "8,true");
// 		positions.put("2,7", "1,false");
// 		positions.put("3,7", "6,false");
// 		positions.put("4,7", "4,true");
// 		positions.put("5,7", "7,false");
// 		positions.put("6,7", "5,false");
// 		positions.put("7,7", "2,true");
// 		positions.put("8,7", "3,false");

// 		positions.put("0,8", "3,false");
// 		positions.put("1,8", "2,false");
// 		positions.put("2,8", "6,true");
// 		positions.put("3,8", "8,true");
// 		positions.put("4,8", "5,true");
// 		positions.put("5,8", "1,false");
// 		positions.put("6,8", "4,true");
// 		positions.put("7,8", "7,false");
// 		positions.put("8,8", "9,false");

// 		return positions;
// 	}

// }
