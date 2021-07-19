import buttonFiles.*;

import javax.swing.*;
import java.util.Arrays;

public class GameFrame extends JFrame implements Logic {
    private int[] quadrantInfo;
    private String[] directionInfo;
    private int[] xBoundInfo;
    private int[] yBoundInfo;
    private turnButton[] turnButtons;
    private int moveCounter;
    private String whoseTurn;
    boolean chipMode; // disable slots when a player has to turn a quadrant
    public void setWhoseTurn() {
        if (moveCounter % 2 == 0) {
            whoseTurn = "white";
        }
        else {
            whoseTurn = "gray";
        }
    }

    GameFrame() {
        quadrantInfo = new int[]{1, 1, 2, 2, 3, 3, 4, 4};
        directionInfo = new String[]{"left","right","left","right","left","right","left","right"};
        xBoundInfo = new int[]{185,245,485,545,185,245,485,545};
        yBoundInfo = new int[]{352,352,352,352,652,652,652,652};
        boardButton[][] quadrant1; // these reference variables for the quadrants
        boardButton[][] quadrant2; // are supposed to make it easy to
        boardButton[][] quadrant3; // rotate quadrants when a player has to
        boardButton[][] quadrant4; // do so after placing a chip in a slot
        chipMode = true;
        whoseTurn = "white";
        moveCounter = 0;
        int width = 6;
        int height = 6;
        turnButtons = new turnButton[8];
        ImageIcon boardImage = new ImageIcon("src/graphics/pentagoBoard.png");
        JLabel boardLabel = new JLabel(boardImage);

        //the slots where players can put their respective pieces
        boardButton[][] slots = new boardButton[width][height];

        int currentX = 120;
        for (int x = 0; x < width; x++) {
            int currentY = 600;
            for (int y = 0; y < height; y++) {
                boardButton currentButton = new boardButton(x,y);
                currentButton.setBounds(currentX,currentY,50,50);
                currentY = currentY - 100;
                currentButton.addActionListener(e->{
                    setChip(whoseTurn, currentButton);
                    moveCounter++;
                    setWhoseTurn();
                });
                slots[x][y] = currentButton;
                this.add(currentButton);
            }
                currentX = currentX + 100;
        }

        quadrant1 = obtainQuadrant(slots,0,3,2,5);
        quadrant2 = obtainQuadrant(slots,3,3,5,5);
        quadrant3 = obtainQuadrant(slots,0,0,2,2);
        quadrant4 = obtainQuadrant(slots,3,0,5,2);
        //System.out.println(quadrant1[0][0].getxCoordinate() + "," + quadrant1[0][0].getyCoordinate());
        //printQuadrant(quadrant1);

        // initialize all the turnButtons for each quadrant and add them to the frame
        for (int i = 0; i < 8; i++) {
            turnButton currentTurnButton = new turnButton(quadrantInfo[i],directionInfo[i]);
            currentTurnButton.setBounds(xBoundInfo[i],yBoundInfo[i],60,20);
            currentTurnButton.addActionListener(e->{
                System.out.println("You pressed the " +
                        currentTurnButton.getDirection() + " button on quadrant " + currentTurnButton.getQuadrant());
                if (currentTurnButton.getQuadrant() == 1) {
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant1);
                }
                else if (currentTurnButton.getQuadrant() == 2) {
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant2);
                }
                else if (currentTurnButton.getQuadrant() == 3) {
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant3);
                }
                else {
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant4);
                }
                disable_filled_slots(slots);
            });
            this.add(currentTurnButton);
        }


        this.add(boardLabel);
        this.setTitle("Pentago");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);

    }

}
