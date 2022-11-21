package board;

import data_structure.*;

public class ChoicePiece {
    Pos choicePos = new Pos(-1, -1);
    int choiceIndex;

    public ChoicePiece() {
        this.choicePos.set(-1, -1);
        this.choiceIndex = -1;
    }

    public boolean isChoicePos() {
        return choicePos.getFirst() != -1 && choicePos.getSecond() != -1;
    }
 
    public Pos getChoicePos() {
        return choicePos;
    }

    public boolean isChoiceHand() {
        return choiceIndex != -1;
    }

    public int getChoiceHand() {
        return choiceIndex;
    }

    public void reset() {
        choicePos.set(-1, -1);
        choiceIndex = -1;
    }

    public void setChoicePos(Pos choicePos) {
        this.choicePos = choicePos;
        this.choiceIndex = -1;
    }

    public void setChoiceHand(int choiceIndex) {
        this.choicePos.set(-1, -1);
        this.choiceIndex = choiceIndex;
    }

}
