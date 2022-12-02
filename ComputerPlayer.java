// CLASS: ComputerPlayer
//
// Author: Huayi Chen
//
// REMARKS: initialize computer player and what will be happen on them
//
//----------------------------------------
import java.util.ArrayList;

public class ComputerPlayer implements IPlayer{
    private int index;
    private int numUnknownCard;
    private boolean isActive;
    private boolean isAccusation;
    private ArrayList<Card> people;
    private ArrayList<Card> places;
    private ArrayList<Card> weapons;
    private ArrayList<Card> hand;
    private ArrayList<Card> peopleChoice;
    private ArrayList<Card> placeChoice;
    private ArrayList<Card> weaponChoice;
    private Guess guess;

    public ComputerPlayer(){
        isActive=true;
        isAccusation=false;
        hand=new ArrayList<>();
    }

    //------------------------------------------------------
    // Method: removed
    // PURPOSE: remove player from game for its turn
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void removed() {
        System.out.println("Player "+index+" made a bad accusation and was removed from the game.");
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
        System.out.println("Player "+index+" won the game.\nGAME OVER");
    }

    //------------------------------------------------------
    // Method: isAccusation
    // PURPOSE: return accusation if the unknown cards' number decrease and reach at 3
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void isAccusation(){
        if (numUnknownCard==3){
            isAccusation=true;
        }
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

    // ------------------------------------------------------
    // Method: removeUnknownCard
    // PURPOSE: remove the card from computer player memory avoid make guess from this card
    // PARAMETERS: a card
    // Returns: void
    //------------------------------------------------------
    public void removeUnknownCard(Card c){
        String cardType=c.getType();
        boolean isFound=false;
        int i=0;

        if (cardType.equals("suspect")){
            int choiceSize=peopleChoice.size();

            while (i < choiceSize && !isFound){
                if (compareName(c, peopleChoice.get(i))){
                    peopleChoice.remove(i);
                    isFound=true;
                }
                i++;
            }

        }else if (cardType.equals("location")){
            int choiceSize=placeChoice.size();

            while (i < choiceSize && !isFound){
                if (compareName(c, placeChoice.get(i))){
                    placeChoice.remove(i);
                    isFound=true;
                }
                i++;
            }

        }else {
            int choiceSize=weaponChoice.size();

            while (i < choiceSize && !isFound){
                if (compareName(c, weaponChoice.get(i))){
                    weaponChoice.remove(i);
                    isFound=true;
                }
                i++;
            }
        }

        if (isFound) {
            numUnknownCard--;
        }
    }

    //------------------------------------------------------
    // Method: compareName
    // PURPOSE: compare two cards' name, return true if they are same
    // PARAMETERS: two cards
    // Returns: boolean
    //------------------------------------------------------
    public boolean compareName(Card card1, Card card2){
        return card1.getName().equals(card2.getName());
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

        peopleChoice=new ArrayList<>(people);
        placeChoice=new ArrayList<>(places);
        weaponChoice=new ArrayList<>(weapons);
        numUnknownCard=people.size()+places.size()+weapons.size();
    }

    //------------------------------------------------------
    // Method: setCard
    // PURPOSE: deal a card for player
    // PARAMETERS: Card
    // Returns: void
    //------------------------------------------------------
    public void setCard(Card c){
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
        Card theCard;
        ArrayList<Integer> list = g.getChoice();
        ArrayList<Card> respondAnswer = new ArrayList<>();

        for (Card card : hand) {
            if (compareName(people.get(list.get(0)), card)) {
                respondAnswer.add(card);
            }
            if (compareName(places.get(list.get(1)), card)) {
                respondAnswer.add(card);
            }
            if (compareName(weapons.get(list.get(2)), card)) {
                respondAnswer.add(card);
            }
        }

        if (respondAnswer.size()==0){
            theCard=null;
        }else{
            if (respondAnswer.size()==1) {
                theCard=respondAnswer.get(0);
            } else {
                int choice = (int) (Math.random() * respondAnswer.size());
                theCard = respondAnswer.get(choice);
            }
            if (ip instanceof HumanPlayer){
                System.out.println("Player "+index+" refuted your suggestion by showing you "+theCard.getName());
            }
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
        int i=0;
        int size=hand.size();

        while (i<size) {
            removeUnknownCard(hand.get(i));
            i++;
        }

        int randomChoice1=(int) (Math.random()*peopleChoice.size());
        int choice1=people.indexOf(peopleChoice.get(randomChoice1));

        int randomChoice2=(int) (Math.random()*placeChoice.size());
        int choice2=places.indexOf(placeChoice.get(randomChoice2));

        int randomChoice3=(int) (Math.random()*weaponChoice.size());
        int choice3=weapons.indexOf(weaponChoice.get(randomChoice3));

        isAccusation();
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
        removeUnknownCard(c);
    }

}
