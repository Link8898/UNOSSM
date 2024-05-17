package noaharnavrobert.unossm;

import java.util.ArrayList;

public class Logic {
    private ArrayList<ArrayList<String>> hands; // The hands of each player
    private static final String[] colors = {"r", "g", "b", "y"};
    private String currentCard = "NONE"; // Top facing card
    private int turn = 0; // Which player's turn
    public Logic(int playerCount) {
        hands = new ArrayList<>(playerCount);

        // Initialize player hands
        for (int index = 0; index < playerCount; index++) {
            hands.add(new ArrayList());
            for (int count = 0; count < 7; count++) {
                int randomNumber = (int)(Math.random( ) * (9 - 1) + 1);
                String randomColor = colors[(int)(Math.random( ) * colors.length)];
                hands.get(index).add(randomColor + randomNumber);
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
        if (id == turn && (card.charAt(0) == currentCard.charAt(0) || card.charAt(1) == currentCard.charAt(1) || currentCard.equals("NONE"))) {
            currentCard = card;
            hands.get(id).remove(cardIndex);
            turn += 1;
            if (turn >= hands.size()) {
                turn = 0;
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
}