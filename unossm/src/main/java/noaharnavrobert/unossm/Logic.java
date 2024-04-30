package noaharnavrobert.unossm;

import java.util.ArrayList;

public class Logic {
    private ArrayList<ArrayList<String>> hands; // The hands of each player
    private static final String[] colors = {"r", "g", "b", "y"};
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
        int randomNumber = (int)(Math.random( ) * (9 - 1) + 1);
        String randomColor = colors[(int)(Math.random( ) * colors.length)];
        hands.get(id).add(randomColor + randomNumber);
        System.out.println(hands.get(0));
    }
}