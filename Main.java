// CLASS: Main
//
// Author: Huayi Chen
//
// REMARKS: initialize game and call Model to run game
//
//----------------------------------------
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    private static HumanPlayer hPlayer;
    private static ComputerPlayer cPlayer;
    private static ArrayList<IPlayer> players=new ArrayList<>();
    private static ArrayList<Card> suspect=new ArrayList<>();
    private static ArrayList<Card> location=new ArrayList<>();
    private static ArrayList<Card> weapon=new ArrayList<>();

    public static void main(String[] args) {
        initializeGame();

        Model newGame=new Model(players, suspect, location, weapon);
        newGame.startPlay();
    }

    public static void initializeGame(){
        System.out.println("Welcome to \"whodunnit?\"");
        System.out.println("How many computer opponents would you like?");

        suspect.add(new Card("suspect",  "<Suspect A>"));
        suspect.add(new Card("suspect",  "<Suspect B>"));
        suspect.add(new Card("suspect",  "<Suspect C>"));
        suspect.add(new Card("suspect",  "<Suspect D>"));
        suspect.add(new Card("suspect",  "<Suspect E>"));
        suspect.add(new Card("suspect",  "<Suspect F>"));
        suspect.add(new Card("suspect",  "<Suspect G>"));

        location.add(new Card("location",  "<garden>"));
        location.add(new Card("location",  "<roof>"));
        location.add(new Card("location",  "<balcony>"));
        location.add(new Card("location",  "<basement>"));
        location.add(new Card("location",  "<garage>"));
        location.add(new Card("location",  "<toilet>"));

        weapon.add(new Card("weapon",  "<axe>"));
        weapon.add(new Card("weapon",  "<gun>"));
        weapon.add(new Card("weapon",  "<knife>"));
        weapon.add(new Card("weapon",  "<bomb>"));
        weapon.add(new Card("weapon",  "<poison>"));

        // get how many computer players
        boolean isValid=false;
        int numCompPlayer=-1;

        while (!isValid){
            Scanner keyBoard=new Scanner(System.in);

            if (keyBoard.hasNextInt()){
                numCompPlayer=keyBoard.nextInt();

                if (numCompPlayer>0){
                    isValid=true;
                }else {
                    System.out.println("Invalid number of computer players. Try again.");
                }
            } else {
                System.out.println("Please enter a number. Try again.");
            }
        }

        hPlayer=new HumanPlayer();
        players.add(hPlayer);

        for (int i = 0; i < numCompPlayer; i++) {
            cPlayer=new ComputerPlayer();
            players.add(cPlayer);
        }

        // make players random order to sit around table
        Collections.shuffle(players);
    }

}
