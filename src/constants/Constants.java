package constants;

public class Constants {
    private Constants() {}   

    public static final int SCREEN_HEIGHT = 1000;
    public static final int SCREEN_WIDTH = 1400;

    public static final int BOARD_CELL_HEIGHT = 4;
    public static final int BOARD_CELL_WIDTH = 3;
    public static final PieceEnum[][] BOARD = {
        {
            PieceEnum.GIRAFFE_ENEMY,
            PieceEnum.LION_ENEMY,
            PieceEnum.ELEPHANT_ENEMY
        }, {
            PieceEnum.EMPTY,
            PieceEnum.CHICK_ENEMY,
            PieceEnum.EMPTY,
        }, {
            PieceEnum.EMPTY,
            PieceEnum.CHICK_PLAYER,
            PieceEnum.EMPTY,
        }, {
            PieceEnum.ELEPHANT_PLAYER,
            PieceEnum.LION_PLAYER,
            PieceEnum.GIRAFFE_PLAYER
        }
    };
    
    public static final int BOARD_CELL_SIZE = 150;
    public static final int HAND_CELL_SIZE = 100;

    public static final int BOARD_MARGIN = 50;
    public static final int HAND_MARGIN = 20;
}
