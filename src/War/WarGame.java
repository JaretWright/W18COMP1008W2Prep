package War;

import java.util.ArrayList;

/**
 *
 * @author JWright
 */
public class WarGame {
    private ArrayList<Card> player1Hand;
    private ArrayList<Card> player2Hand;
    
    public WarGame()
    {
        player1Hand = new ArrayList<>();
        player2Hand = new ArrayList<>();
        
        DeckOfCards deck = new DeckOfCards();
        deck.shuffleDeck();
        
        for (int cardNum=1; cardNum <= 26; cardNum++)
        {
            player1Hand.add(deck.dealTopCard());
            player2Hand.add(deck.dealTopCard());
        }
    }
    
    public void playGame()
    {
        while (!gameOver())
        {
            ArrayList<Card> warPile = new ArrayList<>();
            playHand(warPile);
        }
        
        System.out.printf("%n%nPlayer 1 cards: %d   Player 2 cards: %d", player1Hand.size(), player2Hand.size());
        if (player1Hand.size()>player2Hand.size())
            System.out.printf("%nPlayer 1 wins!");
        else
            System.out.printf("%nPlayer 2 wins!");
    }
    
    public void playHand(ArrayList<Card> warPile)
    {
        
        //check if it is a "war" hand
        if (warPile.size() > 0)
        {
            //check if player 1 has enough cards to play a war hand
            if (player1Hand.size() < 4)
            {
                player2Hand.addAll(player2Hand.size(), warPile); //give all cards to player2
                return;
            }

            //check if player 2 has enough cards to play a war hand
            if (player2Hand.size() < 4)
            {
                player1Hand.addAll(player1Hand.size(), warPile);
                return;
            }

            //play a war hand - both players add 3 cards to the war pile
            for (int count=1; count <=3 ; count++)
            {
                System.out.printf("**WAR - P1Hand size: %2d %-20s", player1Hand.size(), player1Hand.get(0));
                System.out.printf("P2Hand size: %2d %-20s%n", player2Hand.size(), player2Hand.get(0));
                warPile.add(player1Hand.remove(0));
                warPile.add(player2Hand.remove(0));
            }
        }
        System.out.printf("P1Hand size: %2d %-20s", player1Hand.size(), player1Hand.get(0));
        System.out.printf("P2Hand size: %2d %-20s%n", player2Hand.size(), player2Hand.get(0));
        
        //take the top card from each player
        Card p1Card = player1Hand.remove(0);
        Card p2Card = player2Hand.remove(0);
        
        //player 1 wins the hand
        if (p1Card.getFaceValue() > p2Card.getFaceValue())
        {
            player1Hand.add(player1Hand.size(),p1Card);
            player1Hand.add(player1Hand.size(),p2Card);
            player1Hand.addAll(player1Hand.size(),warPile);
        }
        //player 2 wins the hand
        else if (p1Card.getFaceValue() < p2Card.getFaceValue())
        {
            player2Hand.add(player2Hand.size(),p1Card);
            player2Hand.add(player2Hand.size(),p2Card);
            player2Hand.addAll(player2Hand.size(),warPile);
        }
        //else it must be war
        else
        {
            System.out.println("---------------- WAR --------------");
            warPile.add(p1Card);
            warPile.add(p2Card);
            playHand(warPile);
        }
    }
    
    
    public boolean gameOver()
    {
        return player1Hand.size()==0 || player2Hand.size()==0;            
    }
}
