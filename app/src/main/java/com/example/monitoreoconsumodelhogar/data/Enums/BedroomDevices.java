package com.example.monitoreoconsumodelhogar.data.Enums;

public enum BedroomDevices {

    TELEVISION(1.0),
    LUZ(1.0),
    VENTILADOR(0.7),
    CARGADOR_MOVIL(0.05),
    AIRE_ACONDICIONADO(3.0),
    PLANCHA(1.0);

    private final double kwh;

    BedroomDevices(double kwh) {
        this.kwh = kwh;
    }

    public double getKwh() {
        return kwh;
    }
}
