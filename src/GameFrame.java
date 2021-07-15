import buttonFiles.*;

import javax.swing.*;

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
        boardButton[][] quadrant1 = new boardButton[3][3];
        boardButton[][] quadrant2 = new boardButton[3][3];
        boardButton[][] quadrant3 = new boardButton[3][3];
        boardButton[][] quadrant4 = new boardButton[3][3];
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
        // initialize all the turnButtons for each quadrant and add them to the frame
        for (int i = 0; i < 8; i++) {
            turnButton currentTurnButton = new turnButton(quadrantInfo[i],directionInfo[i]);
            currentTurnButton.setBounds(xBoundInfo[i],yBoundInfo[i],60,20);
            currentTurnButton.addActionListener(e->{
                System.out.println("You pressed the " +
                        currentTurnButton.getDirection() + " button on quadrant " + currentTurnButton.getQuadrant());
            });
            this.add(currentTurnButton);
        }

        int currentY = 600;

        for (int x = 0; x < width; x++) {
            int currentX = 120;
            for (int y = 0; y < height; y++) {
                boardButton currentButton = new boardButton(x,y);
                slots[x][y] = currentButton;
                currentButton.setBounds(currentX,currentY,50,50);
                currentX = currentX + 100;
                currentButton.addActionListener(e->{
                    setChip(whoseTurn, currentButton);
                    moveCounter++;
                    setWhoseTurn();
                });
                this.add(currentButton);
            }
                currentY = currentY - 100;
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
