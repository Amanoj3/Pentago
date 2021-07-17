package buttonFiles;

public interface Logic {

    default void printQuadrant(boardButton[][] quad) { // for debugging purposes
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(quad[i][j]);
            }
        }
    }

    default boardButton[][] obtainQuadrant(boardButton[][] slots, int startX, int startY, int lastX, int lastY) {
        boardButton[][] quadrantToReturn = new boardButton[3][3];
        int qx = 0, qy = 0;
        for (int i = startX; i <= lastX; i++) {
            for (int j = startY; j <= lastY; j++) {
                System.out.println(slots[i][j].getxCoordinate() + "," + slots[i][j].getyCoordinate());
                quadrantToReturn[qx][qy] = slots[i][j];
                qy++;
            }
            qy = 0;
            qx++;
        }
        return quadrantToReturn;
    }

    default void rotateQuadrant(String direction, boardButton[][] quadrant) {

    }

    default void disable_or_enable_turns(turnButton[] turnButtons, boolean flag) {
        for (int i = 0; i < 8; i++) {
            turnButtons[i].setEnabled(flag);
        }
    }
    default void disable_or_enable_slots(boardButton[][] slots,boolean flag) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                slots[i][j].setEnabled(flag);
            }
        }
    }

    default void setChip(String playerTurn, boardButton clickedButton) {
        //System.out.println("in setChip method..");
        System.out.println("you pressed the button on " + clickedButton.getxCoordinate() + "," + clickedButton.getyCoordinate());
        if (playerTurn.equals("white")) {
            clickedButton.setWhiteSlot();
        }
        else {
            clickedButton.setGraySlot();
        }
        clickedButton.setDisabledIcon(clickedButton.getIcon());
        clickedButton.setEnabled(false);
    }
}