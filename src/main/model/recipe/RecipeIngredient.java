package main.model.recipe;

import main.model.food.Ingredient;
import main.model.units.IMeasurable;
import main.model.units.NotConvertibleException;

import java.io.Serializable;

/**
 *  Class represents part of Recipe which is aggregate of three variables Ingredient unit of measure
 *  and quantity in this measure.
 */
public class RecipeIngredient implements Serializable {
    private Ingredient ingredient;
    private IMeasurable measurable;
    private double quantity;

    /**
     *
     * @param ingredient enum value representing ingredient
     * @param measurable proper unit of ingredient measure
     * @param quantity quantity in measurable unit
     * @throws IllegalQuantityValueException quantity is inappropriate
     * @throws IllegalMeasureArgumentException measurable and ingredient do not match
     */
    public RecipeIngredient(Ingredient ingredient, IMeasurable measurable, double quantity)
            throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        setQuantity(quantity);
        if(!ingredient.isProperMeasureUnit(measurable))
            throw new IllegalMeasureArgumentException(measurable, ingredient);
        this.ingredient = ingredient;
        this.measurable = measurable;
    }

    public RecipeIngredient(Ingredient ingredient, IMeasurable measurable)
            throws IllegalQuantityValueException, IllegalMeasureArgumentException{
        this(ingredient, measurable, 1);
    }

    /**
     *
     * @param quantity new quantity
     * @throws IllegalQuantityValueException
     */
    public void setQuantity(double quantity) throws IllegalQuantityValueException {
        if(quantity <= 0)
            throw new IllegalQuantityValueException();
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return Double.toString(quantity) + " " + measurable.toString() + " " + ingredient.toString();
    }

    /**
     * toString which tries to convert representation of fraction from "0.5" to 1/2 etc.
     *
     * @param tryToSimplifyFractions true if should be simplified, false corresponds to normal toString
     * @return
     */
    public String toString(boolean tryToSimplifyFractions){
        if(tryToSimplifyFractions){
            String quant = GetSimpleFractionOfQuantity();
            if(!quant.equals("")) {
                return quant + " " + measurable.toString() + " " + ingredient.toString();
            }
        }
        return this.toString();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * convert to new measure, rescaling quantity
     *
     * @param measurable new measure to convert to
     * @throws NotConvertibleException not corresponding measure and ingredient
     */
    public void convertToMeasureUnit(IMeasurable measurable) throws NotConvertibleException {
        double fraction = this.measurable.getValueIn(measurable);
        quantity *= fraction;
        this.measurable = measurable;
    }

    /**
     * Two RecipeIngredients are equal when they have same ingredients same quantity in basic unit
     * with precision to epsilon which is hard set to value od 1e-4.
     *
     * @param obj compared object
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        final double epsilon = 0.0001;
        if(obj == null)
            return false;
        if(!(obj instanceof RecipeIngredient))
            return false;
        return (this.ingredient == ((RecipeIngredient) obj).ingredient) &&
                (Math.abs(this.measurable.getFractionOfBasicUnit() * this.quantity -
                (((RecipeIngredient) obj).measurable).getFractionOfBasicUnit() * ((RecipeIngredient) obj).quantity) < epsilon);

    }

    /**
     * tries to simplify quantity e.g. to form "1/2" from double 0.5
     *
     * @return String with simplified fraction or "" if could not be simplified
     */
    private String GetSimpleFractionOfQuantity(){
        final double E = 0.0001;
        final double n = 64;
        int temp = (int)quantity;
        double fraction = quantity - temp;
        if(fraction < E)
            return String.valueOf(temp);
        for(int denominator=2;denominator<n; denominator++){
            for(int numerator  = 1; numerator <denominator; numerator ++){
                if(Math.abs(fraction-(numerator/(double)denominator)) <= E)
                    if(temp > 0)
                        return String.valueOf(temp) + String.valueOf(numerator) + "/" + String.valueOf(denominator);
                    else
                        return String.valueOf(numerator)+ "/" + String.valueOf(denominator);
            }

        }
        return "";
    }

}
