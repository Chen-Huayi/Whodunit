// CLASS: Guess
//
// Author: Huayi Chen
//
// REMARKS: a guess made by a player
//
//----------------------------------------

import java.util.ArrayList;

public class Guess {
    private int index;
    private int suspectIndex;
    private int locationIndex;
    private int weaponIndex;
    private ArrayList<Card> suspect;
    private ArrayList<Card> location;
    private ArrayList<Card> weapon;
    private boolean isAccusation;

    public Guess(int index, int suspectIndex, int locationIndex, int weaponIndex, ArrayList<Card> suspect, ArrayList<Card> location, ArrayList<Card> weapon, boolean isAccusation){
        this.index=index;
        this.suspectIndex=suspectIndex;
        this.locationIndex=locationIndex;
        this.weaponIndex=weaponIndex;
        this.suspect=suspect;
        this.location=location;
        this.weapon=weapon;
        this.isAccusation=isAccusation;
    }

    //------------------------------------------------------
    // Method: getIndex
    // PURPOSE: return index
    // PARAMETERS: non
    // Returns: int
    //------------------------------------------------------
    public int getIndex() {
        return index;
    }

    //------------------------------------------------------
    // Method: isAccusation
    // PURPOSE: return whether is an accusation
    // PARAMETERS: non
    // Returns: boolean
    //------------------------------------------------------
    public boolean isAccusation() {
        return isAccusation;
    }

    //------------------------------------------------------
    // Method: getChoice
    // PURPOSE: return the guess selection list
    // PARAMETERS: non
    // Returns: ArrayList<Integer>
    //------------------------------------------------------
    public ArrayList<Integer> getChoice() {
        ArrayList<Integer> choice=new ArrayList<>();
        choice.add(suspectIndex);
        choice.add(locationIndex);
        choice.add(weaponIndex);
        return choice;
    }

    //------------------------------------------------------
    // Method: printResult
    // PURPOSE: return current guess result
    // PARAMETERS: non
    // Returns: String
    //------------------------------------------------------
    public String printResult(){
        String result;
        if (isAccusation){
            result="  (Player "+index+":  Accusation: "
                    +suspect.get(suspectIndex).getName() +" in "
                    +location.get(locationIndex).getName()+" with the "
                    +weapon.get(weaponIndex).getName()+")";
        }else {
            result="  (Player "+index+":  Suggestion: "
                    +suspect.get(suspectIndex).getName() +" in "
                    +location.get(locationIndex).getName()+" with the "
                    +weapon.get(weaponIndex).getName()+")";
        }
        return result;
    }

}
