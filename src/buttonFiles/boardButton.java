package buttonFiles;

import javax.swing.*;

public class boardButton extends JButton implements Logic {
    private final int xCoordinate;
    private final int yCoordinate;
    private Icon emptySlot;
    private Icon graySlot;
    private Icon gray_Highlighted;
    private Icon whiteSlot;
    private Icon white_Highlighted;

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setGraySlot() {
        this.setIcon(graySlot);
    }

    public void setGray_Highlighted() {
        this.setIcon(gray_Highlighted);
    }

    public void setWhiteSlot() {
        this.setIcon(whiteSlot);
    }

    public void setWhite_Highlighted() {
        this.setIcon(white_Highlighted);
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
    }

}
