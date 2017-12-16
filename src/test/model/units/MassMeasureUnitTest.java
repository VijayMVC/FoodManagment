package test.model.units;

import main.model.units.MassMeasureUnit;
import main.model.units.NotConvertibleException;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MassMeasureUnitTest {
    @Test
    void getValueIn() throws NotConvertibleException {
        assertEquals(MassMeasureUnit.Kilogram.getValueIn(MassMeasureUnit.Gram), 1000);
        assertEquals(MassMeasureUnit.Pound.getValueIn(MassMeasureUnit.Gram), 453.5);
        assertEquals(MassMeasureUnit.Pound.getValueIn(MassMeasureUnit.Kilogram), 0.4535);
        assertEquals(MassMeasureUnit.Kilogram.getValueIn(MassMeasureUnit.Kilogram), 1);
        assertEquals(MassMeasureUnit.Kilogram.getValueIn(MassMeasureUnit.Pound), 2.2050 , 0.0001);
    }

    @Test
    void getValueInThrowsNotConvertibleException(){
        assertThrows(NotConvertibleException.class, ()  -> MassMeasureUnit.Kilogram.getValueIn(OtherMeasureUnit.Dozen) );
        assertThrows(NotConvertibleException.class, ()  -> MassMeasureUnit.Kilogram.getValueIn(VolumeMeasureUnit.Gallon) );
        assertThrows(NotConvertibleException.class, ()  -> MassMeasureUnit.Kilogram.getValueIn(OtherMeasureUnit.Unit) );
    }


    @Test
    void isValueOf() {
        assertTrue(MassMeasureUnit.isValueOf("Kilogram"));
        assertTrue(MassMeasureUnit.isValueOf("Pound"));
        assertTrue(MassMeasureUnit.isValueOf("Gram"));
        assertFalse(MassMeasureUnit.isValueOf("TeaSpoon"));
        assertFalse(MassMeasureUnit.isValueOf("Kiloogram"));
        assertFalse(MassMeasureUnit.isValueOf("xD"));
        assertFalse(MassMeasureUnit.isValueOf("no i pyk mu enter"));
        assertFalse(MassMeasureUnit.isValueOf("kilogram"));
        assertFalse(MassMeasureUnit.isValueOf(""));
    }

}