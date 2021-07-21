package buttonFiles;

import javax.swing.*;

public class boardButton extends JButton implements Logic { // there are 36 (6x6) boardButtons in a board
    private final int xCoordinate;
    private final int yCoordinate;
    private final Icon graySlot; // a boardButton can have a white chip or a gray chip or it can be empty
    private final Icon gray_Highlighted; // highlighted buttons help you see who the winner is
    private final Icon whiteSlot;
    private final Icon white_Highlighted;
    private final Icon emptySlot;
    private String currentIcon;

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public String getCurrentIcon() {
        return currentIcon;
    }

    public void setGraySlot() {
        currentIcon = "gray";
        this.setIcon(graySlot);
    }

    public void setGray_Highlighted() {
        currentIcon = "grayHighlighted";
        this.setIcon(gray_Highlighted);
    }

    public void setWhiteSlot() {
        currentIcon = "white";
        this.setIcon(whiteSlot);
    }

    public void setWhite_Highlighted() {
        currentIcon = "whiteHighlighted";
        this.setIcon(white_Highlighted);
    }

    public void setEmpty() {
        currentIcon = "empty";
        this.setIcon(emptySlot);
    }

    public boardButton(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
        emptySlot = new ImageIcon("src/graphics/emptySlot.png");
        graySlot = new ImageIcon("src/graphics/graySlot.png");
        gray_Highlighted = new ImageIcon("src/graphics/graySlotHighlighted.png");
        whiteSlot = new ImageIcon("src/graphics/whiteSlot.png");
        white_Highlighted = new ImageIcon("src/graphics/whiteSlotHighlighted.png");
        this.setIcon(emptySlot);
        currentIcon = "empty";
    }

}