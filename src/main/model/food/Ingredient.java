package main.model.food;

import main.model.units.IMeasureable;
import main.model.units.MassMeasureUnit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum Ingredient implements Serializable {
    Hazelnut(false, false, true, Arrays.asList(MassMeasureUnit.values()));
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
