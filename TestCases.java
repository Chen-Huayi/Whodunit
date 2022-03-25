import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;


public class TestCases {
    private static ArrayList<Card> suspect;
    private static ArrayList<Card> location;
    private static ArrayList<Card> weapon;

    @BeforeEach
    public void initialize(){
        suspect=new ArrayList<>();
        location=new ArrayList<>();
        weapon=new ArrayList<>();

        suspect.add(new Card("suspect",  "Suspect A"));
        suspect.add(new Card("suspect",  "Suspect B"));
        suspect.add(new Card("suspect",  "Suspect C"));

        location.add(new Card("location",  "garden"));
        location.add(new Card("location",  "garage"));
        location.add(new Card("location",  "toilet"));

        weapon.add(new Card("weapon",  "axe"));
        weapon.add(new Card("weapon",  "gun"));
    }

    @Test
    public void test1() {
        ComputerPlayer cPlayer1=new ComputerPlayer();
        ComputerPlayer cPlayer2=new ComputerPlayer();

        cPlayer1.setUp(2, 0, suspect, location, weapon);
        cPlayer2.setUp(2, 1, suspect, location, weapon);
        Guess guess=cPlayer1.getGuess();

        // has no card, return null
        assert (cPlayer2.canAnswer(guess, cPlayer2)==null);
    }

    @Test
    public void test2() {
        ComputerPlayer cPlayer1=new ComputerPlayer();
        ComputerPlayer cPlayer2=new ComputerPlayer();

        cPlayer1.setUp(2, 0, suspect, location, weapon);
        cPlayer2.setUp(2, 1, suspect, location, weapon);
        Guess guess1=cPlayer1.getGuess();
        int suspectChoice=guess1.getChoice().get(0);

        cPlayer2.setCard(suspect.get(suspectChoice));

        // has one respond card, answer for that card
        Card theCard=cPlayer2.canAnswer(guess1, cPlayer1);

        assert (theCard==suspect.get(suspectChoice));
    }

    @Test
    public void test3() {
        ComputerPlayer cPlayer1=new ComputerPlayer();
        ComputerPlayer cPlayer2=new ComputerPlayer();

        cPlayer1.setUp(2, 0, suspect, location, weapon);
        cPlayer2.setUp(2, 1, suspect, location, weapon);
        Guess guess1=cPlayer1.getGuess();
        int suspectChoice=guess1.getChoice().get(0);
        int locationChoice=guess1.getChoice().get(1);
        int weaponChoice=guess1.getChoice().get(2);

        cPlayer2.setCard(suspect.get(suspectChoice));
        cPlayer2.setCard(location.get(locationChoice));
        cPlayer2.setCard(weapon.get(weaponChoice));

        // answer for on of these cards
        Card theCard=cPlayer2.canAnswer(guess1, cPlayer1);

        assert (theCard==suspect.get(suspectChoice) || theCard==location.get(locationChoice) || theCard==weapon.get(weaponChoice));
    }

    @Test
    public void test4() {
        ComputerPlayer cPlayer=new ComputerPlayer();
        Card card1=new Card("suspect",  "Suspect C");  // index 2 of suspect
        Card card2=new Card("location",  "garage");  // index 1 of location
        Card card3=new Card("weapon",  "axe");  // index 0 of weapon

        cPlayer.setUp(1, 0, suspect, location, weapon);

        cPlayer.setCard(card1);
        cPlayer.setCard(card2);
        cPlayer.setCard(card3);

        // the guess should be not from hand card index in card sets
        Guess guess=cPlayer.getGuess();
        int choice1=guess.getChoice().get(0);
        int choice2=guess.getChoice().get(1);
        int choice3=guess.getChoice().get(2);

        // avoid the index of choice of each type
        assert (choice1!=2 && choice2 !=1 && choice3!=0);
    }

    @Test
    public void test5() {
        ComputerPlayer cPlayer=new ComputerPlayer();
        Card card1=new Card("suspect",  "Suspect A");  // index 0 of suspect
        Card card2=new Card("suspect",  "Suspect B");  // index 1 of suspect
        Card card3=new Card("location",  "toilet");  // index 2 of location
        Card card4=new Card("location",  "garage");  // index 1 of location
        Card card5=new Card("weapon",  "axe");  // index 0 of weapon

        cPlayer.setUp(1, 0, suspect, location, weapon);

        cPlayer.setCard(card1);
        cPlayer.setCard(card2);
        cPlayer.setCard(card3);
        cPlayer.setCard(card4);
        cPlayer.setCard(card5);

        int choice1;
        int choice2;
        int choice3;

        Guess guess=cPlayer.getGuess();
        choice1=guess.getChoice().get(0);
        choice2=guess.getChoice().get(1);
        choice3=guess.getChoice().get(2);

        // the index of choice of each type should be the remaining card
        assert (choice1==2 && choice2 ==0 && choice3==1);
        assert (guess.isAccusation());
    }

    @Test
    public void test6() {
        ComputerPlayer cPlayer1=new ComputerPlayer();
        ComputerPlayer cPlayer2=new ComputerPlayer();
        Card card1=new Card("suspect",  "Suspect A");  // index 0 of suspect
        Card card2=new Card("suspect",  "Suspect B");  // index 1 of suspect
        Card card3=new Card("location",  "garage");  // index 1 of location
        Card card4=new Card("location",  "toilet");  // index 2 of location
        Card card5=new Card("weapon",  "axe");  // index 0 of weapon

        cPlayer1.setUp(2, 0, suspect, location, weapon);
        cPlayer2.setUp(2, 1, suspect, location, weapon);
        cPlayer1.setCard(card1);
        cPlayer1.setCard(card2);
        cPlayer1.setCard(card3);
        cPlayer1.setCard(card4);

        cPlayer2.setCard(card5);

        // first guess
        Guess firstGuess=cPlayer1.getGuess();
        int choice1=firstGuess.getChoice().get(0);
        int choice2=firstGuess.getChoice().get(1);
        int choice3=firstGuess.getChoice().get(2);

        // the index of choice of each type should be the remaining card
        assert (choice1==2 && choice2 ==0 && (choice3==0 || choice3==1));
        assert (!firstGuess.isAccusation());  // has one unknown card, return suggestion

        Card theCard=null;

        // if the first guess is incorrect, then answer for player 1
        if (choice3==0) {
            theCard = cPlayer2.canAnswer(firstGuess, cPlayer1);
            assert (theCard!=null);
        }

        if (theCard != null) {
            // receive from player 2
            cPlayer1.receiveInfo(cPlayer2, theCard);

            // second guess
            Guess secondGuess=cPlayer1.getGuess();
            int secondChoice1=secondGuess.getChoice().get(0);
            int secondChoice2=secondGuess.getChoice().get(1);
            int secondChoice3=secondGuess.getChoice().get(2);

            // the index of choice of each type should be the remaining card
            assert (secondChoice1==2 && secondChoice2 ==0 && secondChoice3==1);
            assert (secondGuess.isAccusation());
        }

    }

    @Test
    public void test7() {
        HumanPlayer hPlayer=new HumanPlayer();
        ComputerPlayer cPlayer=new ComputerPlayer();
        hPlayer.setUp(2, 0, suspect, location, weapon);
        cPlayer.setUp(2, 1, suspect, location, weapon);

        Guess compGuess=cPlayer.getGuess();
        int choice1=compGuess.getChoice().get(0);
        int choice2=compGuess.getChoice().get(1);
        int choice3=compGuess.getChoice().get(2);

        Card theCard;

        // cannot return card
        theCard=hPlayer.canAnswer(compGuess, cPlayer);
        assert (theCard==null);

        // has one respond card
        hPlayer.setCard(suspect.get(choice1));
        theCard=hPlayer.canAnswer(compGuess, cPlayer);
        assert (theCard==suspect.get(choice1));

        // has more than one respond cards
        hPlayer.setCard(location.get(choice2));
        hPlayer.setCard(weapon.get(choice3));
        theCard=hPlayer.canAnswer(compGuess, cPlayer);

        assert (theCard==suspect.get(choice1) || theCard==location.get(choice2) || theCard==weapon.get(choice3));
    }

}
