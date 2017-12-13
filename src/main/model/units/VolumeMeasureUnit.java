package main.model.units;

import java.io.Serializable;

public enum VolumeMeasureUnit implements IMeasurable, Serializable {
    Liter(1), CubicMetre(1000), CubicCentimetre(0.001), TeaSpoon(0.005),
    TableSpoon(0.015), FluidOunce(0.02841), Gallon(4.54), Cup(0.25);

    private double fractionOfLiter;

    VolumeMeasureUnit(double fractionOfLiter){
        this.fractionOfLiter = fractionOfLiter;
    }

    @Override
    public double getValueIn(IMeasurable measureable) throws NotConvertableException {
        if(measureable.getClass().equals(MassMeasureUnit.class))
            throw new NotConvertableException("Conversion from Volume to mass in not supported");
        if(measureable.getClass().equals(OtherMeasureUnit.class))
            throw new NotConvertableException("Conversion from Mass to simple measure units is not supported");
        return this.fractionOfLiter / measureable.getFractionOfBasicUnit();
    }

    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfLiter;
    }

    public String toString(){
        return this.name().toLowerCase();
    }

    public static boolean isValueOf(String str){
        for(VolumeMeasureUnit measure :VolumeMeasureUnit.values()){
            if(str.toLowerCase().equals(measure.toString()))
                return true;
        }
        return false;
    }

}
