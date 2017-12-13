package main.model.recipe;

import main.model.food.Ingredient;
import main.model.units.IMeasurable;
import main.model.units.NotConvertableException;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {
    private Ingredient ingredient;
    private IMeasurable measurable;
    private double quantity;

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

    public void setQuantity(double quantity) throws IllegalQuantityValueException {
        if(quantity <= 0)
            throw new IllegalQuantityValueException();
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return Double.toString(quantity) + " " + measurable.toString() + " " + ingredient.toString();
    }

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

    public void convertToMeasureUnit(IMeasurable measurable) throws NotConvertableException {
        double fraction = this.measurable.getValueIn(measurable);
        quantity *= fraction;
        this.measurable = measurable;
    }

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
