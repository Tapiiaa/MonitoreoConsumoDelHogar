package com.example.monitoreoconsumodelhogar.Enums;

public enum KitchenDevices {

    HORNO(2.0),
    LUZ(1.0),
    MICROONDAS(1.5),
    LAVAVAJILLAS(1.8),
    THERMOMIX(1.2),
    BATIDORA(1.0),
    FRIGORIFICO(2.5),
    CAFETERA(1.3),
    AIRE_ACONDICIONADO(3.0),
    EXTRACTOR(0.8);

    private final double kwh;

    KitchenDevices(double kwh) {
        this.kwh = kwh;
    }

    public double getKwh() {
        return kwh;
    }
}
