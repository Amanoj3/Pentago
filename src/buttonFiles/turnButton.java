package buttonFiles;

import javax.swing.*;

public class turnButton extends JButton implements Logic{

    private int quadrant;
    private String direction;

    public String getDirection() {
        return direction;
    }

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
