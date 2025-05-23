package br.com.dio;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import static java.util.stream.Collectors.toMap;

import br.com.dio.ui.custom.frame.MainFrame;
import br.com.dio.ui.custom.panel.MainPanel;
import br.com.dio.ui.custom.screen.MainScreen;

public class UiMain {
    public static void main(String[] args) {
        final var gameConfig = (args.length > 0)
                ? Stream.of(args).collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]))
                : buildInitialPositions();
        var mainsScreen = new MainScreen(gameConfig);
        mainsScreen.buildMainScreen();
    }

    private static Map<String, String> buildInitialPositions() {
        Map<String, String> positions = new HashMap<>();

        positions.put("0,0", "4,false");
        positions.put("1,0", "7,false");
        positions.put("2,0", "9,true");
        positions.put("3,0", "5,false");
        positions.put("4,0", "8,true");
        positions.put("5,0", "6,true");
        positions.put("6,0", "2,true");
        positions.put("7,0", "3,false");
        positions.put("8,0", "1,false");

        positions.put("0,1", "1,false");
        positions.put("1,1", "3,true");
        positions.put("2,1", "5,false");
        positions.put("3,1", "4,false");
        positions.put("4,1", "7,true");
        positions.put("5,1", "2,false");
        positions.put("6,1", "8,false");
        positions.put("7,1", "9,true");
        positions.put("8,1", "6,true");

        positions.put("0,2", "2,false");
        positions.put("1,2", "6,true");
        positions.put("2,2", "8,false");
        positions.put("3,2", "9,false");
        positions.put("4,2", "1,true");
        positions.put("5,2", "3,false");
        positions.put("6,2", "7,false");
        positions.put("7,2", "4,false");
        positions.put("8,2", "5,true");

        positions.put("0,3", "5,true");
        positions.put("1,3", "1,false");
        positions.put("2,3", "3,true");
        positions.put("3,3", "7,false");
        positions.put("4,3", "6,false");
        positions.put("5,3", "4,false");
        positions.put("6,3", "9,false");
        positions.put("7,3", "8,true");
        positions.put("8,3", "2,false");

        positions.put("0,4", "8,false");
        positions.put("1,4", "9,true");
        positions.put("2,4", "7,false");
        positions.put("3,4", "1,true");
        positions.put("4,4", "2,true");
        positions.put("5,4", "5,true");
        positions.put("6,4", "3,false");
        positions.put("7,4", "6,true");
        positions.put("8,4", "4,false");

        positions.put("0,5", "6,false");
        positions.put("1,5", "4,true");
        positions.put("2,5", "2,false");
        positions.put("3,5", "3,false");
        positions.put("4,5", "9,false");
        positions.put("5,5", "8,false");
        positions.put("6,5", "1,true");
        positions.put("7,5", "5,false");
        positions.put("8,5", "7,true");

        positions.put("0,6", "7,true");
        positions.put("1,6", "5,false");
        positions.put("2,6", "4,false");
        positions.put("3,6", "2,false");
        positions.put("4,6", "3,true");
        positions.put("5,6", "9,false");
        positions.put("6,6", "6,false");
        positions.put("7,6", "1,true");
        positions.put("8,6", "8,false");

        positions.put("0,7", "9,true");
        positions.put("1,7", "8,true");
        positions.put("2,7", "1,false");
        positions.put("3,7", "6,false");
        positions.put("4,7", "4,true");
        positions.put("5,7", "7,false");
        positions.put("6,7", "5,false");
        positions.put("7,7", "2,true");
        positions.put("8,7", "3,false");

        positions.put("0,8", "3,false");
        positions.put("1,8", "2,false");
        positions.put("2,8", "6,true");
        positions.put("3,8", "8,true");
        positions.put("4,8", "5,true");
        positions.put("5,8", "1,false");
        positions.put("6,8", "4,true");
        positions.put("7,8", "7,false");
        positions.put("8,8", "9,false");

        return positions;
    }
}
