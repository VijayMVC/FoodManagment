package main.model.units;

import java.io.Serializable;

public enum OtherMeasureUnit implements IMeasureable, Serializable {
    Unit(1), Dozen(12);

    private double fractionOfUnit;

    OtherMeasureUnit(double fractionOfUnit) {
        this.fractionOfUnit = fractionOfUnit;
    }

    @Override
    public double getValueIn(IMeasureable measureable) throws NotConvertableException {
        if (measureable.getClass().equals(VolumeMeasureUnit.class))
            throw new NotConvertableException("Conversion from" +
                    " Simple Unit to volume is not supported");
        if (measureable.getClass().equals(MassMeasureUnit.class))
            throw new NotConvertableException("Conversion from" +
                    " Simple Unit to mass is not supported");
        return this.fractionOfUnit / measureable.getFractionOfBasicUnit();
    }

    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfUnit;
    }

    public String toString(){
        return this.name().toLowerCase();
    }

}
