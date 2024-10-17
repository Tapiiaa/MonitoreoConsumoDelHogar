package com.example.monitoreoconsumodelhogar.data.Enums;

public enum HallDevices {

    AMBIENTADOR(0.04),
    LUZ(1.0),
    WIFI_EXTENDER(0.01),
    CARGADOR_ASPIRADORA(0.2);

    private final double kwh;

    HallDevices(double kwh) {
        this.kwh = kwh;
    }

    public double getKwh() {
        return kwh;
    }
}
