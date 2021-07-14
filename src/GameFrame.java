import buttonFiles.*;

import javax.swing.*;

public class GameFrame extends JFrame implements Controller {

    GameFrame() {

        int width = 6;
        int height = 6;
        ImageIcon boardImage = new ImageIcon("src/graphics/pentagoBoard.png");
        JLabel boardLabel = new JLabel(boardImage);

        //the slots where players can put their respective pieces
        boardButton[][] slots = new boardButton[width][height];

        //turn buttons for quadrant 1
        turnButton turnButton1 = new turnButton(1, "left");
        turnButton1.setBounds(185,352,60,20);
        turnButton turnButton2 = new turnButton(1, "right");
        turnButton2.setBounds(245,352,60,20);
        //turn buttons for quadrant 2
        turnButton turnButton3 = new turnButton(2, "left");
        turnButton3.setBounds(485,352,60,20);
        turnButton turnButton4 = new turnButton(2, "right");
        turnButton4.setBounds(545,352,60,20);
        //turn buttons for quadrant 3
        turnButton turnButton5 = new turnButton(3, "left");
        turnButton5.setBounds(185,652,60,20);
        turnButton turnButton6 = new turnButton(3, "right");
        turnButton6.setBounds(245,652,60,20);
        // turn buttons for quadrant 4
        turnButton turnButton7 = new turnButton(4, "left");
        turnButton7.setBounds(485,652,60,20);
        turnButton turnButton8 = new turnButton(4, "right");
        turnButton8.setBounds(545,652,60,20);

        int currentY = 600;

        for (int x = 0; x < width; x++) {
            int currentX = 120;
            for (int y = 0; y < height; y++) {
                boardButton currentButton = new boardButton(x,y);
                slots[x][y] = currentButton;
                currentButton.setBounds(currentX,currentY,50,50);
                currentX = currentX + 100;
                this.add(currentButton);
            }
                currentY = currentY - 100;
        }
        this.add(turnButton1);
        this.add(turnButton2);
        this.add(turnButton3);
        this.add(turnButton4);
        this.add(turnButton5);
        this.add(turnButton6);
        this.add(turnButton7);
        this.add(turnButton8);
        this.add(boardLabel);
        this.setTitle("Pentago");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);

    }

}
