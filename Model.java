// CLASS: Model
//
// Author: Huayi Chen
//
// REMARKS: initialize players and process game
//
//----------------------------------------
import java.util.ArrayList;
import java.util.Collections;


public class Model {
    private IPlayer iPlayer;
    private ArrayList<IPlayer> players;
    private ArrayList<Card> people;
    private ArrayList<Card> places;
    private ArrayList<Card> weapons;
    private ArrayList<Card> allCards;
    private ArrayList<Integer> answerList;

    public Model(ArrayList<IPlayer> players, ArrayList<Card> people, ArrayList<Card> places, ArrayList<Card> weapons){
        this.players=players;
        this.people=people;
        this.places=places;
        this.weapons=weapons;
        allCards=new ArrayList<>();
        answerList=new ArrayList<>();
    }

    //------------------------------------------------------
    // Method: startPlay
    // PURPOSE: run the game
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void startPlay(){
        setUpGame();
        setAnswerAndDeal();
        runProcess();
    }

    //------------------------------------------------------
    // Method: showList
    // PURPOSE: show you all cards the game has
    // PARAMETERS: kinds of cards list
    // Returns: void
    //------------------------------------------------------
    public void showList(ArrayList<Card> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.print(arrayList.get(i).getName());

            if (i!=arrayList.size()-1){
                System.out.print(", ");
            }
        }
    }

    //------------------------------------------------------
    // Method: setUpGame
    // PURPOSE: initialize the game and set up players
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void setUpGame(){
        System.out.println("Setting up players...\nHere are the name of suspects:");
        showList(people);

        System.out.println("\nHere are all the locations:");
        showList(places);

        System.out.println("\nHere are all the weapons:");
        showList(weapons);

        for (int i = 0; i < players.size(); i++) {
            iPlayer=players.get(i);
            iPlayer.setUp(players.size(), i, people, places, weapons);
        }
    }

    //------------------------------------------------------
    // Method: setAnswerAndDeal
    // PURPOSE: pick the answer, and then shuffle remaining cards to deal them
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void setAnswerAndDeal(){
        int suspectChoice=(int)(Math.random()*(people.size()));
        int locationChoice=(int)(Math.random()*(places.size()));
        int weaponChoice=(int)(Math.random()*(weapons.size()));
        ArrayList<Card> newPeople = new ArrayList<>(people);
        ArrayList<Card> newPlaces = new ArrayList<>(places);
        ArrayList<Card> newWeapons = new ArrayList<>(weapons);

        answerList.add(suspectChoice);
        answerList.add(locationChoice);
        answerList.add(weaponChoice);

        newPeople.remove(suspectChoice);
        newPlaces.remove(locationChoice);
        newWeapons.remove(weaponChoice);

        System.out.println("\nDealing cards...");
        allCards.addAll(newPeople);
        allCards.addAll(newPlaces);
        allCards.addAll(newWeapons);
        Collections.shuffle(allCards);

        for (int i = 0, playerIndex=0; i < allCards.size(); i++, playerIndex++) {
            if (playerIndex==players.size()){
                playerIndex=0;
            }
            iPlayer=players.get(playerIndex);
            iPlayer.setCard(allCards.get(i));
        }
    }

    //------------------------------------------------------
    // Method: runProcess
    // PURPOSE: process the logic of game
    // PARAMETERS: non
    // Returns: void
    //------------------------------------------------------
    public void runProcess(){
        System.out.println("Playing...");
        int currTurn=0;
        int numActivePlayer=players.size();
        boolean isGameOver=false;
        boolean isGetNextPlayer=false;

        while (!isGameOver) {
            iPlayer=players.get(currTurn);

            if (numActivePlayer == 1) {      // if there is only one active player, this player win the game
                if (iPlayer instanceof HumanPlayer) {
                    ((HumanPlayer) iPlayer).toBeWinner();
                } else {
                    ((ComputerPlayer) iPlayer).toBeWinner();
                }
                isGameOver = true;
            } else {
                // ask player for their guess
                Guess playerGuess = iPlayer.getGuess();
                System.out.println(playerGuess.printResult());

                if (playerGuess.isAccusation()) {  // Accusation
                    // check answer, correct to win, wrong to be removed
                    if (iPlayer instanceof HumanPlayer) {
                        HumanPlayer hPlayer = (HumanPlayer) iPlayer;
                        if (hPlayer.isCorrect(answerList)) {
                            hPlayer.toBeWinner();
                            isGameOver = true;
                        } else {
                            hPlayer.removed();
                            numActivePlayer--;
                        }
                    } else {
                        ComputerPlayer cPlayer = (ComputerPlayer) iPlayer;
                        if (cPlayer.isCorrect(answerList)) {
                            cPlayer.toBeWinner();
                            isGameOver = true;
                        } else {
                            cPlayer.removed();
                            numActivePlayer--;
                        }
                    }
                } else {  // Suggestion
                    boolean isAnswered=false;
                    int i=currTurn;
                    int askingTimes=0;
                    IPlayer currPlayer=iPlayer;
                    Card theCard;

                    // ask next player for their helpful tips
                    while (!isAnswered && askingTimes<players.size()-1){
                        i=(i+1)%players.size();

                        iPlayer = players.get(i);
                        System.out.println("Asking player "+iPlayer.getIndex());
                        theCard = iPlayer.canAnswer(playerGuess, currPlayer);

                        if (theCard != null){
                            currPlayer.receiveInfo(iPlayer, theCard);
                            isAnswered=true;
                            System.out.println("Player "+iPlayer.getIndex()+" answered.");
                        }
                        askingTimes++;
                    }

                    // no one answered
                    if (!isAnswered){
                        if (currPlayer instanceof HumanPlayer){
                            System.out.println("No one could refute your suggestion.");
                        }
                        System.out.println("No one could answer");
                    }
                }

                // more to next player's turn if game is not over
                if (!isGameOver) {
                    while (!isGetNextPlayer) {
                        currTurn=(currTurn+1)%players.size();
                        iPlayer=players.get(currTurn);

                        if (iPlayer instanceof HumanPlayer) {
                            if (((HumanPlayer) iPlayer).isActive()) {
                                isGetNextPlayer = true;
                            }
                        } else {
                            if (((ComputerPlayer) iPlayer).isActive()) {
                                isGetNextPlayer = true;
                            }
                        }
                    }
                    isGetNextPlayer = false;
                }

            }
        }  // end while

        // Game is Over here
    }

}
