package main.model.units;

import java.io.Serializable;

public enum OtherMeasureUnit implements IMeasurable, Serializable {
    Unit(1), Dozen(12);

    private double fractionOfUnit;

    OtherMeasureUnit(double fractionOfUnit) {
        this.fractionOfUnit = fractionOfUnit;
    }

    @Override
    public double getValueIn(IMeasurable measureable) throws NotConvertableException {
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

    public static boolean isValueOf(String str){
        for(OtherMeasureUnit measure :OtherMeasureUnit.values()){
            if(str.toLowerCase().equals(measure.toString()))
                return true;
        }
        return false;
    }

}
