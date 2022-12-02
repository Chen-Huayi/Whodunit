// CLASS: HumanPlayer
//
// Author: Huayi Chen
//
// REMARKS: initialize human player and what will be happen on player
//
//----------------------------------------
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer implements IPlayer{
    private int index;
    private ArrayList<Card> people;
    private ArrayList<Card> places;
    private ArrayList<Card> weapons;
    private ArrayList<Card> hand;
    private Guess guess;
    private boolean isActive;

    public HumanPlayer(){
        isActive=true;
        hand=new ArrayList<>();
    }

    //------------------------------------------------------
    // Method: removed
    // PURPOSE: remove player from game for its turn
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void removed() {
        System.out.println("Player "+index+" (You) made a bad accusation and was removed from the game.");
        isActive = false;
    }

    //------------------------------------------------------
    // Method: isActive
    // PURPOSE: return whether player is active
    // PARAMETERS: non
    // Returns: boolean
    //------------------------------------------------------
    public boolean isActive(){
        return isActive;
    }

    //------------------------------------------------------
    // Method: toBeWinner
    // PURPOSE: print the winner information
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void toBeWinner(){
        System.out.println("Player "+index+" (You) won the game.\nGAME OVER");
    }

    //------------------------------------------------------
    // Method: selectOption
    // PURPOSE: show options details for human player, and get their selection
    // PARAMETERS: list of information
    // Returns: int
    //------------------------------------------------------
    public int selectOption(ArrayList<Card> arrayList){
        int choice=-1;
        boolean isValid=false;

        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(i+": "+arrayList.get(i).getName());
        }

        while (!isValid){
            Scanner keyBoard=new Scanner(System.in);

            if (keyBoard.hasNextInt()){
                choice=keyBoard.nextInt();
                if (choice>=0 && choice<arrayList.size()){
                    isValid=true;
                }else {
                    System.out.println("Invalid choice index. Try again.");
                }
            } else {
                System.out.println("Please enter a number. Try again.");
            }
        }
        return choice;
    }

    //------------------------------------------------------
    // Method: isCorrect
    // PURPOSE: check the player's guess is equal to answer
    // PARAMETERS: answer list of game
    // Returns: boolean
    //------------------------------------------------------
    public boolean isCorrect(ArrayList<Integer> answerList){
        boolean result=false;

        if (answerList.equals(guess.getChoice())){
            result=true;
        }
        return result;
    }

    //------------------------------------------------------
    // Method: compareName
    // PURPOSE: compare two cards' name, return true if they are same
    // PARAMETERS: two cards
    // Returns: boolean
    //------------------------------------------------------
    public boolean compareName(Card card1, Card card2){
        boolean isSame=false;
        if (card1.getName().equals(card2.getName())){
            isSame=true;
        }
        return isSame;
    }

    //------------------------------------------------------
    // Method: setUp
    // PURPOSE: set up player with some information
    // PARAMETERS:
    // Returns: void
    //------------------------------------------------------
    public void setUp(int numPlayers, int index, ArrayList<Card> people, ArrayList<Card> places, ArrayList<Card> weapons){
        this.index=index;
        this.people=people;
        this.places=places;
        this.weapons=weapons;
    }

    //------------------------------------------------------
    // Method: setCard
    // PURPOSE: deal a card for player
    // PARAMETERS: Card
    // Returns: void
    //------------------------------------------------------
    public void setCard (Card c){
        System.out.println("You received the card '"+c.getName()+"'");
        hand.add(c);
    }

    //------------------------------------------------------
    // Method: getIndex
    // PURPOSE: return index
    // PARAMETERS: non
    // Returns: int
    //------------------------------------------------------
    public int getIndex(){
        return index;
    }

    //------------------------------------------------------
    // Method: canAnswer
    // PURPOSE: answer a Card or null for different each follow game rules
    // PARAMETERS: a player's guess, the player who asked
    // Returns: Card
    //------------------------------------------------------
    public Card canAnswer(Guess g, IPlayer ip) {
        Card theCard = null;
        ArrayList<Integer> list = g.getChoice();
        ArrayList<Card> respondAnswer = new ArrayList<>();

        for (int i = 0; i < hand.size(); i++) {
            if (compareName(people.get(list.get(0)), hand.get(i))) {
                respondAnswer.add(hand.get(i));
            }
            if (compareName(places.get(list.get(1)), hand.get(i))) {
                respondAnswer.add(hand.get(i));
            }
            if (compareName(weapons.get(list.get(2)), hand.get(i))){
                respondAnswer.add(hand.get(i));
            }
        }

        System.out.print("Player " + g.getIndex() + " asked you about" + g.printResult());

        if (respondAnswer.size()==0){
            System.out.print(", but you couldn't answer.\n");
        }else if (respondAnswer.size()==1) {
            System.out.print(", you only have one card, " + respondAnswer.get(0).getName() + ", showed it to them.\n");
            theCard=respondAnswer.get(0);
        } else {
            System.out.print(". Which do you show?\n");
            int choice=selectOption(respondAnswer);
            theCard=respondAnswer.get(choice);
        }

        return theCard;
    }

    //------------------------------------------------------
    // Method: getGuess
    // PURPOSE: make a guess and return it
    // PARAMETERS: non
    // Returns: Guess
    //------------------------------------------------------
    public Guess getGuess(){
        System.out.println("It is your turn:\nWhich person do you want to suggest?");
        int choice1=selectOption(people);

        System.out.println("Which location do you want to suggest?");
        int choice2=selectOption(places);

        System.out.println("Which weapon do you want to suggest?");
        int choice3=selectOption(weapons);

        System.out.println("Is this an accusation (Y/[N])?");
        boolean isAccusation = false;
        Scanner input4=new Scanner(System.in);
        String decision=input4.next();

        if (decision.equals("Y") || decision.equals("y")){
            isAccusation=true;
        }

        guess = new Guess(index, choice1, choice2, choice3, people, places, weapons, isAccusation);

        return guess;
    }

    //------------------------------------------------------
    // Method: receiveInfo
    // PURPOSE: receive card from other player
    // PARAMETERS: the player who answer for you, a card
    // Returns: void
    //------------------------------------------------------
    public void receiveInfo(IPlayer ip, Card c){
        System.out.println("You receive the card "+c.getName()+"from Player "+ip.getIndex());
    }

}
