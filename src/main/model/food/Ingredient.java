package main.model.food;

import main.model.units.IMeasureable;
import main.model.units.MassMeasureUnit;
import main.model.units.VolumeMeasureUnit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum Ingredient implements Serializable {
    Hazelnut(false, false, true, Arrays.asList(MassMeasureUnit.values())),
    ChickeBreast(true, false, false, Arrays.asList(MassMeasureUnit.values())),
    Flour(false, false, false, Arrays.asList(MassMeasureUnit.values())),
    Salt(false, false, false, Arrays.asList(MassMeasureUnit.values())),
    OliveOil(false, false, false, Arrays.asList(VolumeMeasureUnit.values())),
    DriedOregano(false, false, false, Arrays.asList(VolumeMeasureUnit.values())),
    Butter(false, true, false, Arrays.asList(VolumeMeasureUnit.values())),
    PortobelloMushrooms(false, false, false, Arrays.asList(VolumeMeasureUnit.values())),
    DriedTomatoes(false, false, false, Arrays.asList(VolumeMeasureUnit.values())),
    Spinach(false, false, false, Arrays.asList(VolumeMeasureUnit.values())),
    MarsalaVine(false, false, false, Arrays.asList(VolumeMeasureUnit.values()));

    private boolean meat;
    private boolean dairyProduct;
    private boolean nut;
    private List<Enum> properMeasures;

    Ingredient(boolean Meat, boolean dairyProduct, boolean nut,
                List<Enum> properArguments){
        this.meat=Meat;
        this.dairyProduct = dairyProduct;
        this.nut = nut;
        this.properMeasures = properArguments;
    }
    public boolean isMeat() {
        return meat;
    }

    public boolean isDairyProduct() {
        return dairyProduct;
    }

    public boolean isNut() {
        return nut;
    }

    public String toString(){
        return this.name().toLowerCase();
    }

    public boolean isProperMeasureUnit(Object o){
        if(!(o instanceof IMeasureable))
            return false;
        for(Enum properMeasure : properMeasures ){
            if(o.getClass().equals(properMeasure.getClass()))
                return true;
        }
        return false;
    }

}
