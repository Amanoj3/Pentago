package buttonFiles;
import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public interface Logic {

     default int numOccupiedSlots(boardButton[][] slots) {
        int counter = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (!slots[i][j].getCurrentIcon().equals("empty")) { // if the slot is occupied/not empty, then update the counter
                    counter++;
                }
            }
        }
        return counter;
    }

    default void invokeDatabase(String victor, boardButton[][] slots) { // invoke this method at the end of a game

         ArrayList<String> queryStrings = new ArrayList<>(); // each string is a row from game_table
        StringBuilder alertBoxMessage = new StringBuilder(); // StringBuilders are for large strings or multiple concatenations (like in a loop)
        // see https://stackoverflow.com/questions/1825781/when-to-use-stringbuilder
        Connection con;

        try {

            //number of occupied slots
            int numChipsPlaced = numOccupiedSlots(slots);

            //date and time
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String stringifyDate = dateFormat.format(date).substring(0,10); // contains the date in a "yyyy-MM-dd" format
            String stringifyTime = dateFormat.format(date).substring(11); // this substring contains the time in a "hh:mm:ss" format

            Class.forName("com.mysql.jdbc.Driver");
            //try to establish a connection
            con = DriverManager.getConnection("jdbc:mysql://localhost/pentago_db?characterEncoding=latin1","root","password");
            //the Statement variable facilitates the execution of MySQL queries
            Statement st = con.createStatement();

            //necessary queries listed as strings
            // the truncate query gets executed when there are 5 or more rows as we will see below
            String truncateQuery = "truncate game_table";
            //insert a row indicating details like the winner, the time, etc.
            String insertQuery = "insert into game_table(game_winner,slots_filled,game_date,game_time)" +
                    " values ('" + victor + "','" + numChipsPlaced +"','"+stringifyDate+"','"+stringifyTime+"')";
            // the selectQuery retrieves the ENTIRE table
            String selectQuery = "select * from game_table";

            //add a row to the table
            st.executeUpdate(insertQuery);
            //this stores the results of the selectQuery, which will be extracted in the while loop below
            ResultSet result = st.executeQuery(selectQuery);

            while (result.next()) {
                String currentString = "";
                currentString += result.getInt("game_id"); // append id
                currentString += "        ";
                currentString += result.getString("game_winner"); // append winner
                currentString += "        ";
                currentString += result.getString("slots_filled"); // append slots_filled
                currentString += "        ";
                currentString += result.getString("game_date"); //append today's date
                currentString += "        ";
                currentString += result.getString("game_time"); // append the time at which the game is finished
                queryStrings.add(currentString); // push the stringified row into the queryStrings arraylist so we can display them in the JOptionPane below
            }

            if (queryStrings.size() >= 5) { // this resets/truncates the scoreboard once it has 5 or more rows
                st.execute(truncateQuery);
            }
            con.close();
            alertBoxMessage.append("ID    |  WIN  |  SLOTS  |     DATE     |           TIME    ").append("\n");
            for (String queryString : queryStrings) {
                alertBoxMessage.append(queryString).append("\n"); // ensures there is a new line after each row
            }
            JOptionPane.showMessageDialog(null, alertBoxMessage,"Database Info/Scoreboard", JOptionPane.INFORMATION_MESSAGE); // this box shows all the rows from the table
        }

        // if an attempt to connect to a DB fails, we must handle unexpected interruptions gracefully
        catch(SQLException | ClassNotFoundException ex) {
            System.out.println("Exception handled gracefully.");
            System.out.println(ex.getMessage());
        }
    }

    default void resetSlots(boardButton[][] slots) { // makes all slots empty for a new game
        for (int i = 0; i<6; i++) {
            for (int j = 0; j<6; j++) {
                slots[i][j].setEmpty();
            }
        }
    }

    default void correspondingHighlight(boardButton btn, String whoseTurn) { // highlights pieces to help the program determine the winner
        if (whoseTurn.equals("white")) {
            btn.setWhite_Highlighted();
        }
        else {
            btn.setGray_Highlighted();
        }
    }

    default boolean winningSequenceFound(boardButton[][] slots, String whoseTurn) {
        //checks for winning combos horizontally, vertically and diagonally
        //horizontal checks
        for (int x = 0; x < 2; x++) { // I had to program this defensively by making sure that nothing went out of bounds
            for (int y = 0; y < 6; y++) { //hence x < 2
                if (slots[x][y].getCurrentIcon().equals(whoseTurn) &&
                        slots[x+1][y].getCurrentIcon().equals(whoseTurn)
                && slots[x+2][y].getCurrentIcon().equals(whoseTurn)
                && slots[x+3][y].getCurrentIcon().equals(whoseTurn)
                && slots[x+4][y].getCurrentIcon().equals(whoseTurn)) {
                    correspondingHighlight(slots[x][y],whoseTurn);
                    correspondingHighlight(slots[x+1][y],whoseTurn);
                    correspondingHighlight(slots[x+2][y],whoseTurn);
                    correspondingHighlight(slots[x+3][y],whoseTurn);
                    correspondingHighlight(slots[x+4][y],whoseTurn);
                    return true;
                }
            }
        }
        // vertical checks
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 2; y++) { // y < 2 to avoid going out of bounds..
                if (slots[x][y].getCurrentIcon().equals(whoseTurn)
                        && slots[x][y+1].getCurrentIcon().equals(whoseTurn)
                        && slots[x][y+2].getCurrentIcon().equals(whoseTurn)
                        && slots[x][y+3].getCurrentIcon().equals(whoseTurn)
                        && slots[x][y+4].getCurrentIcon().equals(whoseTurn)) {
                    correspondingHighlight(slots[x][y], whoseTurn);
                    correspondingHighlight(slots[x][y + 1], whoseTurn);
                    correspondingHighlight(slots[x][y + 2], whoseTurn);
                    correspondingHighlight(slots[x][y + 3], whoseTurn);
                    correspondingHighlight(slots[x][y + 4], whoseTurn);
                    return true;
                }
            }
        }

        //ascending diagonal checks
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                if ((slots[x][y].getCurrentIcon().equals(whoseTurn)
                        && slots[x+1][y+1].getCurrentIcon().equals(whoseTurn) &&
                        slots[x+2][y+2].getCurrentIcon().equals(whoseTurn)
                        && slots[x+3][y+3].getCurrentIcon().equals(whoseTurn)
                        && slots[x+4][y+4].getCurrentIcon().equals(whoseTurn))) {
                    correspondingHighlight(slots[x][y],whoseTurn);
                    correspondingHighlight(slots[x+1][y+1],whoseTurn);
                    correspondingHighlight(slots[x+2][y+2],whoseTurn);
                    correspondingHighlight(slots[x+3][y+3],whoseTurn);
                    correspondingHighlight(slots[x+4][y+4],whoseTurn);
                    return true;
                }
            }
        }
        //descending diagonal check
        for (int x = 0; x < 2; x++) {
            for (int y = 5; y >= 4; y--) {
                if (slots[x][y].getCurrentIcon().equals(whoseTurn)
                        && slots[x+1][y-1].getCurrentIcon().equals(whoseTurn)
                        && slots[x+2][y-2].getCurrentIcon().equals(whoseTurn)
                        && slots[x+3][y-3].getCurrentIcon().equals(whoseTurn)
                        && slots[x+4][y-4].getCurrentIcon().equals(whoseTurn)
                ) {
                    correspondingHighlight(slots[x][y],whoseTurn);
                    correspondingHighlight(slots[x+1][y-1],whoseTurn);
                    correspondingHighlight(slots[x+2][y-2],whoseTurn);
                    correspondingHighlight(slots[x+3][y-3],whoseTurn);
                    correspondingHighlight(slots[x+4][y-4],whoseTurn);
                    return true;
                }
            }
        }

        return false; // winning sequence not found
    }
    // this method changes icons when a quadrant gets rotated
    default void changeIcon(boardButton buttonToChange, boardButton changeSource) {
        switch (changeSource.getCurrentIcon()) {
            case "gray" -> buttonToChange.setGraySlot();
            case "white" -> buttonToChange.setWhiteSlot();
            case "empty" -> buttonToChange.setEmpty();
            case "whiteHighlighted" -> buttonToChange.setWhite_Highlighted();
            case "grayHighlighted" -> buttonToChange.setGray_Highlighted();
        }
    }
    // helps you obtain a portion (a quadrant) of the entire 6x6 board
    default boardButton[][] obtainQuadrant(boardButton[][] slots, int startX, int startY, int lastX, int lastY) {
        boardButton[][] quadrantToReturn = new boardButton[3][3];
        int qx = 0, qy = 0;
        for (int i = startX; i <= lastX; i++) {
            for (int j = startY; j <= lastY; j++) {
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
        if (direction.equals("left")) {
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
    // disables turnButtons when it's time to place a chip
    default void disable_or_enable_turns(turnButton[] turnButtons, boolean flag) {
        for (int i = 0; i < 8; i++) {
            turnButtons[i].setEnabled(flag);
        }
    }
    //disables slots when it's time to rotate a quadrant
    default void disable_or_enable_slots(boardButton[][] slots,boolean flag) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                slots[i][j].setEnabled(flag);
                slots[i][j].setDisabledIcon(slots[i][j].getIcon());
            }
        }
    }
    //makes all the slots with chips (non-empty slots) disabled
    default void disable_filled_slots(boardButton[][] slots) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                slots[i][j].setEnabled(slots[i][j].getCurrentIcon().equals("empty"));
                slots[i][j].setDisabledIcon(slots[i][j].getIcon());
            }
        }
    }
    // this method places a chip in a slot
    default void setChip(String playerTurn, boardButton clickedButton) {
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