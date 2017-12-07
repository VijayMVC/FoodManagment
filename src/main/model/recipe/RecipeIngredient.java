package main.model.recipe;

import main.model.food.Ingredient;
import main.model.units.IMeasureable;
import main.model.units.NotConvertableException;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {
    private Ingredient ingredient;
    private IMeasureable measureable;
    private double quantity;

    public RecipeIngredient(Ingredient ingredient, IMeasureable measureable, double quantity)
            throws IllegalQuantityValueException, IllegalMeasureArgumentException {
        setQuantity(quantity);
        if(!ingredient.isProperMeasureUnit(measureable))
            throw new IllegalMeasureArgumentException(measureable, ingredient);
        this.ingredient = ingredient;
        this.measureable = measureable;
    }

    public RecipeIngredient(Ingredient ingredient, IMeasureable measureable)
            throws IllegalQuantityValueException, IllegalMeasureArgumentException{
        this(ingredient, measureable, 1);
    }

    public void setQuantity(double quantity) throws IllegalQuantityValueException {
        if(quantity <= 0)
            throw new IllegalQuantityValueException();
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return Double.toString(quantity) + " " + measureable.toString() + " " + ingredient.toString();
    }

    public String toString(boolean tryToSimplifyFractions){
        if(tryToSimplifyFractions){
            String quant = GetSimpleFractionOfQuantity();
            if(!quant.equals("")) {
                return quant + " " + measureable.toString() + " " + ingredient.toString();
            }
        }
        return this.toString();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void convertToMeasureUnit(IMeasureable measureable) throws NotConvertableException {
        double fraction = this.measureable.getValueOf(measureable);
        quantity *= fraction;
        this.measureable = measureable;
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
