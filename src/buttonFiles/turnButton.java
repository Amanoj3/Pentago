package buttonFiles;

import javax.swing.*;

public class turnButton extends JButton implements Logic{ // turnButtons allow you to rotate quadrants

    private final int quadrant; // the button's respective quadrant
    private final String direction; // a turnButton can make the quadrant go left or right

    public String getDirection() {
        return direction;
    } //getter methods

    public int getQuadrant() {
        return quadrant;
    }

    public turnButton(int quad, String dir) {
        quadrant = quad;
        direction = dir;

        if (dir.equals("left")) {
            this.setText("<<");
        }
        else {
            this.setText(">>");
        }

    }
}