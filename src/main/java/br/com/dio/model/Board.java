package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import br.com.dio.model.enums.GameStatusEnum;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

public class Board {
    private final List<List<Space>> spaces;

    public Board(final List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getStatus() {
        if (spaces.stream().flatMap(Collection::stream)
                .noneMatch(space -> !space.isFixed() && nonNull(space.getActual()))) {
            return GameStatusEnum.NON_STARTED;
        }

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(space -> isNull(space.getActual())) ? GameStatusEnum.INCOMPLETE : GameStatusEnum.COMPLETE;

    }

    public boolean hasErrors() {
        if (getStatus() == GameStatusEnum.NON_STARTED) {
            return false;

        }
        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(space -> nonNull(space.getActual()) && !space.getActual().equals(space.getExpected()));
    }

    public boolean changeValue(final int column, final int row, final Integer value) {
        var space = spaces.get(column).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.setActual(value);
        System.out.println(space.getActual());
        return true;
    }

    public boolean clearValue(final int row, final int column) {
        var space = spaces.get(column).get(row);
        if (space.isFixed()) {
            return false;
        }

        space.clearSpace();
        return true;
    }

    public void reset() {
        spaces.forEach(c -> c.forEach(Space::clearSpace));
    }

    public boolean gameFinished() {
        return getStatus().equals(GameStatusEnum.COMPLETE) && !hasErrors();
    }
}
