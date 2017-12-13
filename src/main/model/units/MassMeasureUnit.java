package main.model.units;

import java.io.Serializable;

public enum MassMeasureUnit implements IMeasurable, Serializable {
    Kilogram(1), Gram(0.001), Pound(0.4535);

    private double fractionOfkilogram;

    MassMeasureUnit(double fractionOfkilogram){
        this.fractionOfkilogram = fractionOfkilogram;
    }

    @Override
    public double getValueIn(IMeasurable measureable) throws NotConvertableException {
        if(measureable.getClass().equals(VolumeMeasureUnit.class))
            throw new NotConvertableException("Conversion from" +
                    " Mass to Volume is not supported");
        if(measureable.getClass().equals(OtherMeasureUnit.class))
            throw new NotConvertableException("Conversion from" +
                    " Mass to simple measure units is not supported");
        return this.fractionOfkilogram / measureable.getFractionOfBasicUnit();
    }

    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfkilogram;
    }

    public String toString(){
        return this.name().toLowerCase();
    }

    public static boolean isValueOf(String str){
        for(MassMeasureUnit measure :MassMeasureUnit.values()){
            if(str.toLowerCase().equals(measure.toString()))
                return true;
        }
        return false;
    }
}
