package buttonFiles;

public interface Logic {

    default void changeIcon(boardButton buttonToChange, boardButton changeSource) {
        switch (changeSource.getCurrentIcon()) {
            case "gray" -> buttonToChange.setGraySlot();
            case "white" -> buttonToChange.setWhiteSlot();
            case "empty" -> buttonToChange.setEmpty();
            case "whiteHighlighted" -> buttonToChange.setWhite_Highlighted();
            case "grayHighlighted" -> buttonToChange.setGray_Highlighted();
        }
    }

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
                //System.out.println(slots[i][j].getxCoordinate() + "," + slots[i][j].getyCoordinate());
                quadrantToReturn[qx][qy] = slots[i][j];
                qy++;
            }
            qy = 0;
            qx++;
        }
        return quadrantToReturn;
    }

    default void rotateQuadrant(String direction, boardButton[][] quadrant) { // change the buttons' icons here
        boardButton[][] quadrantClone = new boardButton[3][3];
        boardButton[] buttonsToChange = {quadrant[0][0],quadrant[0][1], quadrant[0][2], quadrant[1][0],
                quadrant[1][2],quadrant[2][0],quadrant[2][1],quadrant[2][2]};
        //clone each button and add it to the quadrantClone array
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                int xCoord = quadrant[x][y].getxCoordinate();
                int yCoord = quadrant[x][y].getyCoordinate();
                boardButton cloneButton = new boardButton(xCoord,yCoord);
                changeIcon(cloneButton,quadrant[x][y]);
                quadrantClone[x][y] = cloneButton;
            }
        }
        // make a changeSource array
        if (direction.equals("right")) {
            boardButton[] changeSources = {quadrantClone[0][2], quadrantClone[1][2], quadrantClone[2][2], quadrantClone[0][1],
                    quadrantClone[2][1], quadrantClone[0][0], quadrantClone[1][0], quadrantClone[2][0]};

            for (int i = 0; i < buttonsToChange.length; i++) {
                changeIcon(buttonsToChange[i], changeSources[i]);
            }
        }
        else {
            boardButton[] changeSources = {quadrantClone[2][0],quadrantClone[1][0],quadrantClone[0][0],quadrantClone[2][1],
                    quadrantClone[0][1],quadrantClone[2][2],quadrantClone[1][2],quadrantClone[0][2]};
            for (int i = 0; i < buttonsToChange.length; i++) {
                changeIcon(buttonsToChange[i], changeSources[i]);
            }
        }
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
    default void disable_filled_slots(boardButton[][] slots) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                slots[i][j].setEnabled(slots[i][j].getCurrentIcon().equals("empty"));
                slots[i][j].setDisabledIcon(slots[i][j].getIcon());
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