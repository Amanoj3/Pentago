import buttonFiles.*;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame implements Logic {
    private final turnButton[] turnButtons; // these buttons enable you to turn a quadrant
    private int moveCounter; // this int value dictates whose turn it is
    private String whoseTurn; // a string that changes based on the evenness/oddness of the moveCounter variable
    private final boardButton[][] slots; // buttons/slots that a player can place a chip in
    private final JLabel turnLabel; // indicates whose turn it is within the JFrame

    public boardButton[][] getSlots() {
        return slots;
    }

    public turnButton[] getTurnButtons() {
        return turnButtons;
    }

    public JLabel getTurnLabel() {
        return turnLabel;
    }
    public int getMoveCounter() {
        return moveCounter;
    }
    public String getWhoseTurn() {
        return whoseTurn;
    }
    private void setTurnLabel() { // this method changes the JLabel based on whose turn it is
        if (whoseTurn.equals("white")) {
            turnLabel.setText("White's turn!");
        }
        else {
            turnLabel.setText("Gray's turn!");
        }
    }
    public void setWhoseTurn() { //setter function for the whoseTurn field member
        if (moveCounter % 2 == 0) {
            whoseTurn = "white";
        }
        else {
            whoseTurn = "gray";
        }
    }

    public void resetGame() { // click the reset button to start over..
        resetSlots(slots);
        whoseTurn = "white";
        moveCounter = 0;
        disable_or_enable_slots(slots,true);
        disable_or_enable_turns(turnButtons,false);
        setTurnLabel();
    }

    GameFrame() {
        int[] quadrantInfo = new int[]{1, 1, 2, 2, 3, 3, 4, 4};
        String[] directionInfo = new String[]{"left", "right", "left", "right", "left", "right", "left", "right"};
        int[] xBoundInfo = new int[]{185, 245, 485, 545, 185, 245, 485, 545};
        int[] yBoundInfo = new int[]{352, 352, 352, 352, 652, 652, 652, 652};
        // the variables above contain information (where they're located, their directions, etc) for the turnButtons
        boardButton[][] quadrant1; // these reference variables for the quadrants
        boardButton[][] quadrant2; // are supposed to make it easy to
        boardButton[][] quadrant3; // rotate quadrants when a player has to
        boardButton[][] quadrant4; // do so after placing a chip in a slot
        whoseTurn = "white"; // white moves first
        // the JLabel that indicates whose turn it is
        turnLabel = new JLabel("White's turn!");
        turnLabel.setBounds(350,20,75,30); // determines the physical location of the turnLabel
        this.add(turnLabel);
        moveCounter = 0; // zero moves made at the very beginning..
        int width = 6; // it is a 6x6 board..
        int height = 6;
        turnButtons = new turnButton[8]; // there are two turnButtons per quadrant
        ImageIcon boardImage = new ImageIcon("src/graphics/pentagoBoard.png"); // background image behind the slots
        JLabel boardLabel = new JLabel(boardImage);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(10,20,75,30); // where the reset button is located physically
        resetButton.addActionListener(e-> resetGame()); // if you click the reset button, the actionListener will call the resetGame() method
        this.add(resetButton);

        //the slots where players can put their respective pieces
        slots = new boardButton[width][height];

        int currentX = 120; // currentX and currentY determine the physical locations of the slots
        for (int x = 0; x < width; x++) {
            int currentY = 600;
            for (int y = 0; y < height; y++) {
                boardButton currentButton = new boardButton(x,y); // we add each slot/button one by one..
                currentButton.setBounds(currentX,currentY,50,50); // each slot is 50x50 size-wise
                currentY = currentY - 100;
                currentButton.addActionListener(e->{ // action listener for EACH slot
                    setChip(whoseTurn, currentButton); // place a chip based on whose turn it is
                    disable_filled_slots(slots); // once you place a chip in a slot, it will no longer be clickable
                    moveCounter++;
                    if (winningSequenceFound(slots,"white") || (winningSequenceFound(slots,"gray"))) {
                        // if a player wins, update the label to "Game over!" and disable ALL buttons
                        turnLabel.setText("Game over!");
                        disable_or_enable_slots(slots,false);
                        disable_or_enable_turns(turnButtons, false);
                    }
                    else {
                        //enable the appropriate buttons
                        disable_or_enable_turns(turnButtons, true);
                        disable_or_enable_slots(slots, false);
                    }
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

        // initialize all the turnButtons for each quadrant and add them to the frame
        for (int i = 0; i < 8; i++) {
            turnButton currentTurnButton = new turnButton(quadrantInfo[i], directionInfo[i]);
            currentTurnButton.setBounds(xBoundInfo[i], yBoundInfo[i],60,20);
            currentTurnButton.addActionListener(e->{
                System.out.println("You pressed the " +
                        currentTurnButton.getDirection() + " button on quadrant " + currentTurnButton.getQuadrant());
                if (currentTurnButton.getQuadrant() == 1) { // if a turnButton from quadrant 1 is pressed..
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant1);
                }
                else if (currentTurnButton.getQuadrant() == 2) { // else if a turnButton from quadrant 2 is pressed
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant2);
                }
                else if (currentTurnButton.getQuadrant() == 3) { //else if a turnButton from quadrant 3 is pressed
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant3);
                }
                else { // otherwise it's a turnButton from quadrant 4..
                    rotateQuadrant(currentTurnButton.getDirection(), quadrant4);
                }
                if (winningSequenceFound(slots,"white") || (winningSequenceFound(slots,"gray"))) {
                    //if a player wins, disable all the buttons and update the turnLabel
                    turnLabel.setText("Game over!");
                    disable_or_enable_slots(slots,false);
                    disable_or_enable_turns(turnButtons, false);
                }
                else {
                    //the game continues normally and it's the other player's turn
                    setWhoseTurn();
                    setTurnLabel();
                    disable_or_enable_turns(turnButtons, false);
                    disable_or_enable_slots(slots, true);
                    disable_filled_slots(slots);
                }
            });
            turnButtons[i] = currentTurnButton;
            this.add(currentTurnButton);
        }
        disable_or_enable_turns(turnButtons,false);
        Image frameLogo = new ImageIcon("src/graphics/pentagoLogo.png").getImage(); // logo for the window's frame
        this.setIconImage(frameLogo); // set the icon of the program
        this.add(boardLabel); // the image behind the slots a.k.a the board itself
        this.setTitle("Pentago"); // title of the frame
        this.setSize(800, 800); // 800x800 window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program ends when the frame is closed
        this.setVisible(true); // visible window
        this.setResizable(false); // cannot resize window
        this.setLayout(null);
        this.getContentPane().setBackground(Color.decode("#cff595")); // background color

    }

}