import org.junit.Test;

import static org.junit.Assert.*;

public class unitTests {
    GameFrame example = new GameFrame();
    @Test
    public void initialConditionsTest() { // this tests the starting conditions of the game
        assertEquals(0, example.getMoveCounter());
        assertEquals("white",example.getWhoseTurn());
        assertEquals("White's turn!",example.getTurnLabel().getText());
        assertEquals("Pentago",example.getTitle());
        assertEquals("There should be 8 turnButtons",8, example.getTurnButtons().length);
        int buttonCounter = 0;
        for (int i = 0; i < example.getSlots().length; i++) {
            for (int j = 0; j < example.getSlots()[i].length; j++) {
                buttonCounter++;
            }
        }
        assertEquals("There should be 36 buttons", 36, buttonCounter);
    }

}
