// CLASS: Card
//
// Author: Huayi Chen
//
// REMARKS: some features of a card
//
//----------------------------------------

public class Card {
    private String type;
    private String name;

    public Card(String type, String name){
        this.type=type;
        this.name=name;
    }

    //------------------------------------------------------
    // Method: getType
    // PURPOSE: return card types' name
    // PARAMETERS: non
    // Returns: String
    //------------------------------------------------------
    public String getType() {
        return type;
    }

    //------------------------------------------------------
    // Method: getName
    // PURPOSE: return card name
    // PARAMETERS: non
    // Returns: String
    //------------------------------------------------------
    public String getName() {
        return name;
    }

}
