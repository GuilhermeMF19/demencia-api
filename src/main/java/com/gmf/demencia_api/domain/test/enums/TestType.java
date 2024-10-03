package com.gmf.demencia_api.domain.test.enums;

public enum TestType {
    ESCALA_DE_KATZ("Escala de Katz"),
    MINI_EXAME_DO_ESTADO_MENTAL("Mini Exame do Estado Mental"),
    ESCALA_DE_LAWTON("Escala de Lawton"),
    INVENTARIO_DE_CUMMINGS("Inventário de Cummings");
    
    private String type;
    
    TestType(String type) {
        this.type = type;
    }
    
    public String getTestType() {
        return type;
    }
}