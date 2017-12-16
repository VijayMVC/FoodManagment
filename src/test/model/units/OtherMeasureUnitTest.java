package test.model.units;

import main.model.units.MassMeasureUnit;
import main.model.units.NotConvertibleException;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtherMeasureUnitTest {
    @Test
    void getValueIn() throws NotConvertibleException {
        assertEquals(OtherMeasureUnit.Unit.getValueIn(OtherMeasureUnit.Unit), 1);
        assertEquals(OtherMeasureUnit.Dozen.getValueIn(OtherMeasureUnit.Unit), 12);
    }

    @Test
    void getValueInThrowsNotConvertibleException(){
        assertThrows(NotConvertibleException.class, ()  -> OtherMeasureUnit.Unit.getValueIn(MassMeasureUnit.Kilogram) );
        assertThrows(NotConvertibleException.class, ()  -> OtherMeasureUnit.Unit.getValueIn(MassMeasureUnit.Gram) );
        assertThrows(NotConvertibleException.class, ()  -> OtherMeasureUnit.Unit.getValueIn(VolumeMeasureUnit.Liter) );
    }


    @Test
    void isValueOf() {
        assertTrue(OtherMeasureUnit.isValueOf("Unit"));
        assertTrue(OtherMeasureUnit.isValueOf("Dozen"));
        assertFalse(OtherMeasureUnit.isValueOf("CubicCentimetre"));
        assertFalse(OtherMeasureUnit.isValueOf("TeaSpoon"));
        assertFalse(OtherMeasureUnit.isValueOf("gallon"));
        assertFalse(OtherMeasureUnit.isValueOf("xD"));
        assertFalse(OtherMeasureUnit.isValueOf("no i pyk mu enter"));
        assertFalse(OtherMeasureUnit.isValueOf("TeaSpooon"));
        assertFalse(OtherMeasureUnit.isValueOf(""));
    }
}