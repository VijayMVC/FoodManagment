package main.model.units;

import java.io.Serializable;

/**
 *  Representation of mass measure units. Basic unit is a kilogram. If new measure is needed, ratio of new
 *  measure to kilogram is sufficient.
 */
public enum MassMeasureUnit implements IMeasurable, Serializable {
    Kilogram(1), Gram(0.001), Pound(0.4535);

    private double fractionOfkilogram;

    /**
     *
     * @param fractionOfkilogram - ratio of unit/kilogram
     */
    MassMeasureUnit(double fractionOfkilogram){
        this.fractionOfkilogram = fractionOfkilogram;
    }

    /**
     * provides the ratio of new / current measure
     *
     * @param measurable new measure
     * @return new / current measure ratio
     * @throws NotConvertibleException it thrown were the operation is not supported (e.g. converting mass to Volume)
     */
    @Override
    public double getValueIn(IMeasurable measurable) throws NotConvertibleException {
        if(measurable.getClass().equals(VolumeMeasureUnit.class))
            throw new NotConvertibleException("Conversion from" +
                    " Mass to Volume is not supported");
        if(measurable.getClass().equals(OtherMeasureUnit.class))
            throw new NotConvertibleException("Conversion from" +
                    " Mass to simple measure units is not supported");
        return this.fractionOfkilogram / measurable.getFractionOfBasicUnit();
    }

    /**
     *
     * @return fraction of basic unit (here kilogram)
     */
    @Override
    public double getFractionOfBasicUnit() {
        return fractionOfkilogram;
    }

    public String toString(){
        return this.name();
    }

    /**
     *Check if string is toString value of enum value
     *
     * @param str String representation of measure, or any other String
     * @return true if String is representation of measure, otherwise false.
     */
    public static boolean isValueOf(String str){
        for(MassMeasureUnit measure :MassMeasureUnit.values()){
            if(str.equals(measure.toString()))
                return true;
        }
        return false;
    }
}
