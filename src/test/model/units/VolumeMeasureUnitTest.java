package test.model.units;

import main.model.units.MassMeasureUnit;
import main.model.units.NotConvertibleException;
import main.model.units.OtherMeasureUnit;
import main.model.units.VolumeMeasureUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
 * https://www.infoq.com/news/2009/07/junit-4.7-rules
 * https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests
 * https://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests/46514550#46514550
 * https://howtodoinjava.com/junit-5/expected-exception-example/ (most important)
 */
class VolumeMeasureUnitTest {


    @Test
    void getValueIn() throws NotConvertibleException {
        assertEquals(VolumeMeasureUnit.Gallon.getValueIn(VolumeMeasureUnit.Liter), 4.54);
        assertEquals(VolumeMeasureUnit.CubicMetre.getValueIn(VolumeMeasureUnit.Liter), 1000);
        assertEquals(VolumeMeasureUnit.Gallon.getValueIn(VolumeMeasureUnit.CubicCentimetre), 4540);
        assertEquals(VolumeMeasureUnit.Cup.getValueIn(VolumeMeasureUnit.TeaSpoon), 50);
    }

    @Test
    void getValueInThrowsNotConvertibleException(){
        assertThrows(NotConvertibleException.class, ()  -> VolumeMeasureUnit.Gallon.getValueIn(MassMeasureUnit.Kilogram) );
        assertThrows(NotConvertibleException.class, ()  -> VolumeMeasureUnit.Gallon.getValueIn(MassMeasureUnit.Gram) );
        assertThrows(NotConvertibleException.class, ()  -> VolumeMeasureUnit.Gallon.getValueIn(OtherMeasureUnit.Unit) );
    }


    @Test
    void isValueOf() {
        assertTrue(VolumeMeasureUnit.isValueOf("Gallon"));
        assertTrue(VolumeMeasureUnit.isValueOf("Liter"));
        assertTrue(VolumeMeasureUnit.isValueOf("CubicCentimetre"));
        assertTrue(VolumeMeasureUnit.isValueOf("TeaSpoon"));
        assertFalse(VolumeMeasureUnit.isValueOf("gallon"));
        assertFalse(VolumeMeasureUnit.isValueOf("xD"));
        assertFalse(VolumeMeasureUnit.isValueOf("no i pyk mu enter"));
        assertFalse(VolumeMeasureUnit.isValueOf("TeaSpooon"));
        assertFalse(VolumeMeasureUnit.isValueOf(""));
    }

}