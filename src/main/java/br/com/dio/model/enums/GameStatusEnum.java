package br.com.dio.model.enums;

public enum GameStatusEnum {
    NON_STARTED("Não iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private String label;

    GameStatusEnum(String string) {
        this.label = string;
    }

    public String getLabel() {
        return label;
    }
}
