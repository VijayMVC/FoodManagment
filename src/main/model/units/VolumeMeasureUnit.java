package main.model.units;

import java.io.Serializable;
/**
 *  Representation of volume measure units. Basic unit is a liter. If new measure is needed, ratio of new
 *  measure to liter is sufficient.
 */
public enum VolumeMeasureUnit implements IMeasurable, Serializable {
    Liter(1), CubicMetre(1000), CubicCentimetre(0.001), TeaSpoon(0.005),
    TableSpoon(0.015), FluidOunce(0.02841), Gallon(4.54), Cup(0.25);

    private double fractionOfLiter;

    /**
     *
     * @param fractionOfLiter ration of measure/liter
     */
    VolumeMeasureUnit(double fractionOfLiter){
        this.fractionOfLiter = fractionOfLiter;
    }

    /**
     * provides the ratio of new / current measure
     *
     * @param measurable new measure
     * @return new / current measure ratio
     * @throws NotConvertibleException it thrown were the operation is not supported (e.g. converting volume to mass)
     */
    @Override
    public double getValueIn(IMeasurable measurable) throws NotConvertibleException {
        if(measurable.getClass().equals(MassMeasureUnit.class))
            throw new NotConvertibleException("Conversion from Volume to mass in not supported");
        if(measurable.getClass().equals(OtherMeasureUnit.class))
            throw new NotConvertibleException("Conversion from Mass to simple measure units is not supported");
        return this.fractionOfLiter / measurable.getFractionOfBasicUnit();
    }

    /**
     *
     * @return fraction of basic unit (here liter)
     */
    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfLiter;
    }

    public String toString(){
        return this.name();
    }

    /**
     * Check if string is toString value of enum value
     *
     * @param str String representation of measure, or any other String.
     * @return true if String is representation of measure, otherwise false.
     */
    public static boolean isValueOf(String str){
        for(VolumeMeasureUnit measure :VolumeMeasureUnit.values()){
            if(str.equals(measure.toString()))
                return true;
        }
        return false;
    }

}
