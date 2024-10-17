package com.example.monitoreoconsumodelhogar.data.Enums;

public enum LivingroomDevices {

    TELEVISION(1.0),
    LUZ(1.0),
    VENTILADOR(0.7),
    CARGADOR_MOVIL(0.05),
    AIRE_ACONDICIONADO(3.0),
    CARGADOR_ASPIRADORA(0.2),
    ORDENADOR(0.5),
    CARGADOR_PORTATIL(0.1),
    LAMPARA_DE_PIE(0.6),
    ROUTER(0.05);

    private final double kwh;

    LivingroomDevices(double kwh) {
        this.kwh = kwh;
    }

    public double getKwh() {
        return kwh;
    }
}
