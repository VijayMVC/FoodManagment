package test.model.food;

import main.model.food.Ingredient;
import main.model.units.MassMeasureUnit;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    @Test
    void isProperMeasureUnit() {
        assertTrue(Ingredient.ChickenBreast.isProperMeasureUnit(MassMeasureUnit.Gram));
        assertTrue(Ingredient.OliveOil.isProperMeasureUnit(VolumeMeasureUnit.TeaSpoon));
        assertTrue(Ingredient.Butter.isProperMeasureUnit(VolumeMeasureUnit.Cup));
        assertTrue(Ingredient.ChickenBreast.isProperMeasureUnit(MassMeasureUnit.Kilogram));
        assertFalse(Ingredient.ChickenBreast.isProperMeasureUnit(VolumeMeasureUnit.CubicMetre));
        assertFalse(Ingredient.Flour.isProperMeasureUnit(OtherMeasureUnit.Dozen));
        assertFalse(Ingredient.MarsalaVine.isProperMeasureUnit(MassMeasureUnit.Gram));
    }

}