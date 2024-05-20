package noaharnavrobert.unossm;

import java.util.ArrayList;

public class Logic {
    private ArrayList<ArrayList<String>> hands; // The hands of each player
    private static final String[] colors = {"r", "g", "b", "y"};
    private String currentCard = "NE"; // Top facing card
    private int turn = 0; // Which player's turn
    public Logic(int playerCount) {
        hands = new ArrayList<>(playerCount);

        // Initialize player hands
        for (int index = 0; index < playerCount; index++) {
            hands.add(new ArrayList());
            for (int count = 0; count < 7; count++) {
                int typeChance = (int)(Math.random( ) * (101 - 1) + 1);
                if (typeChance >= 40) { // 60% chance of normal color + number card
                    int randomNumber = (int)(Math.random( ) * (9 - 1) + 1);
                    String randomColor = colors[(int)(Math.random( ) * colors.length)];
                    hands.get(index).add(randomColor + randomNumber);
                }
                else if (typeChance >= 0) { // 40% chance of +2 or +4
                    int randomNumber = (int)(Math.random( ) * (3 - 1) + 1);
                    String randomColor = colors[(int)(Math.random( ) * colors.length)];
                    String drawType = "T"; // "T" for draw Two
                    if (randomNumber == 2) { drawType = "F"; } // "F" for draw Four
                    hands.get(index).add(randomColor + drawType);
                }
            }
        }
    }

    public void DrawCard(int id) { // Give a random card to the user via their id
        if (id == turn) {
            int randomNumber = (int)(Math.random( ) * (9 - 1) + 1);
            String randomColor = colors[(int)(Math.random( ) * colors.length)];
            hands.get(id).add(randomColor + randomNumber);
            turn += 1;
            if (turn >= hands.size()) {
                turn = 0;
            }
        }
        else {
            System.out.println("Not your turn");
        }
    }

    public void PlayCard(int id, int cardIndex) { // Place a card via the id's hand and card index
        String card = hands.get(id).get(cardIndex);
        if (id == turn && (card.charAt(0) == currentCard.charAt(0) || card.charAt(1) == currentCard.charAt(1) || currentCard.equals("NE"))) {
            currentCard = card;
            hands.get(id).remove(cardIndex);
            turn += 1;
            if (turn >= hands.size()) {
                turn = 0;
            }

            int draws = 0;
            if (card.charAt(1) == 'Q') { draws = 2; }
            else if (card.charAt(1) == 'F') { draws = 4; }
            for (int count = 0; count < draws; count++) {
                int randomNumber = (int)(Math.random( ) * (9 - 1) + 1);
                String randomColor = colors[(int)(Math.random( ) * colors.length)];
                hands.get(turn).add(randomColor + randomNumber);
            }
        }
        else {
            System.out.println("Invalid Play");
        }
    }

    public ArrayList<String> GetHand(int id) { // Get the hand corresponding to the id
        return hands.get(id);
    }
    public String CurrentCard() { return currentCard; }
    public int getTurn() { return turn; }
}