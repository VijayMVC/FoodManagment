package main.model.units;

import java.io.Serializable;
/**
 *  Representation of other measure units. Basic unit is a unit(1). If new measure is needed, ratio of new
 *  measure to unit is sufficient.
 */
public enum OtherMeasureUnit implements IMeasurable, Serializable {
    Unit(1), Dozen(12);

    private double fractionOfUnit;

    /**
     *
     * @param fractionOfUnit ration of measure/unit
     */
    OtherMeasureUnit(double fractionOfUnit) {
        this.fractionOfUnit = fractionOfUnit;
    }

    /**
     * provides the ratio of new / current measure
     *
     * @param measurable new measure
     * @return new / current measure ratio
     * @throws NotConvertibleException it thrown were the operation is not supported (e.g. converting unit to Volume)
     */
    @Override
    public double getValueIn(IMeasurable measurable) throws NotConvertibleException {
        if (measurable.getClass().equals(VolumeMeasureUnit.class))
            throw new NotConvertibleException("Conversion from" +
                    " Simple Unit to volume is not supported");
        if (measurable.getClass().equals(MassMeasureUnit.class))
            throw new NotConvertibleException("Conversion from" +
                    " Simple Unit to mass is not supported");
        return this.fractionOfUnit / measurable.getFractionOfBasicUnit();
    }

    /**
     *
     * @return fraction of basic unit (here unit)
     */
    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfUnit;
    }

    public String toString(){
        return this.name();
    }

    /**
     * Check if string is toString value of enum value
     *
     * @param str String representation of measure, or any other String
     * @return true if String is representation of measure, otherwise false.
     */
    public static boolean isValueOf(String str){
        for(OtherMeasureUnit measure :OtherMeasureUnit.values()){
            if(str.equals(measure.toString()))
                return true;
        }
        return false;
    }

}
