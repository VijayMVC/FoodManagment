package main.model.food;

import main.model.units.IMeasurable;
import main.model.units.MassMeasureUnit;
import main.model.units.VolumeMeasureUnit;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Class represents Ingredient. It is Enum type because of safety. Adding new Ingredient is simple, just
 * add new enum, specify three boolean values, that answers if the product is meat, nut or dairy product.
 * These are important information for Vegetarians and allergy sufferers. Last value is the list of logically
 * appropriate measure units.
 */
//do przerobienia na klase i zmienic wyjatki na exception
    // stworz fabryke skladnikow i wyrzucaj tylko i wylacznie potrzebne nie istniejace jeszcze skaldniki
public enum Ingredient implements Serializable {
    Hazelnut(false, false, true, Arrays.asList(MassMeasureUnit.values())),
    ChickenBreast(true, false, false, Arrays.asList(MassMeasureUnit.values())),
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

    /**
     *
     * @param Meat true if is meat, otherwise false
     * @param dairyProduct true if is dairy product, otherwise false
     * @param nut true if is nut, otherwise false
     * @param properArguments List of proper measure units
     */
    Ingredient(boolean Meat, boolean dairyProduct, boolean nut,
                List<Enum> properArguments){
        this.meat=Meat;
        this.dairyProduct = dairyProduct;
        this.nut = nut;
        this.properMeasures = properArguments;
    }

    /**
     *
     * @return true if is Meat, otherwise false
     */
    public boolean isMeat() {
        return meat;
    }

    /**
     *
     * @return true if is Dairy product
     */
    public boolean isDairyProduct() {
        return dairyProduct;
    }

    /**
     *
     * @return true if is Nut
     */
    public boolean isNut() {
        return nut;
    }


    public String toString(){
        return this.name();
    }

    /**
     * describes if given measure is proper measure of the enum value.
     *
     * @param measure measure to be checked
     * @return
     */
    public boolean isProperMeasureUnit(Object measure){
        if(!(measure instanceof IMeasurable))
            return false;
        for(Enum properMeasure : properMeasures ){
            if(measure.getClass().equals(properMeasure.getClass()))
                return true;
        }
        return false;
    }


}
