package com.example.monitoreoconsumodelhogar.Enums;

public enum BathroomDevices {

    SECADOR(1.5),
    LUZ(1.0),
    PLANCHA_PELO(1.0);

    private final double kwh;

    BathroomDevices(double kwh) {
        this.kwh = kwh;
    }

    public double getKwh() {
        return kwh;
    }
}
