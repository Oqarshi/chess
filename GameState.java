import java.util.*;
import java.util.ArrayList;


public class GameState {
    byte[] board = new byte[64];
    static byte[] pawnPosEval = new byte[]
    {//wqs           wks
      0,0,0,0,0,0,0,0,
      4,4,4,0,0,4,4,4,
      2,1,1,7,7,1,3,3,
      3,2,1,9,9,1,2,3,
      2,2,2,5,5,2,2,2,
      4,4,5,7,7,5,4,4,
      15,15,15,15,15,15,15,15,
      0,0,0,0,0,0,0,0
    };//bqs          bks
    static byte[] bishopPosEval = new byte[]
    {
      7,1,1,1,1,1,1,7,
      1,7,2,2,2,2,7,1,
      5,4,6,4,4,6,4,5,
      1,2,7,4,4,7,2,1,
      1,3,3,4,4,3,3,1,
      1,2,3,4,4,3,2,1,
      1,2,2,2,2,2,2,1,
      0,1,1,1,1,1,1,0
    };
    static byte[] knightPosEval = new byte[]
    {
      0,1,2,2,2,2,1,0,
      1,3,5,3,3,5,3,1,
      2,6,8,8,8,8,6,2,
      2,6,8,9,9,8,6,2,
      2,6,8,9,9,8,6,2,
      2,6,8,8,8,8,6,2,
      1,3,5,6,6,5,3,1,
      0,1,2,2,2,2,1,0
    };
    static byte[] rookPosEval = new byte[]
    {
      3,2,4,6,6,4,2,3,
      0,2,2,2,2,2,2,0,
      0,2,2,2,2,2,2,0,
      0,2,2,2,2,2,2,0,
      0,2,2,2,2,2,2,0,
      0,2,2,2,2,2,2,0,
      6,9,9,9,9,9,9,6,
      3,3,3,3,3,3,3,3
    };
    static byte[] queenPosEval = new byte[]
    {
      0,1,1,2,2,1,1,0,
      1,3,3,3,3,3,3,1,
      1,3,7,7,7,7,3,1,
      2,3,7,9,9,7,3,2,
      2,3,7,9,9,7,3,2,
      1,3,7,7,7,7,3,1,
      1,3,3,3,3,3,3,1,
      0,1,1,2,2,1,1,0
    };
    static byte[] kingPosEval = new byte[]
    {
      6,30,9,2,2,9,30,6,
      3,3,2,1,1,2,3,3,
      3,2,1,0,0,1,2,3,
      1,0,0,0,0,0,0,1,
      0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0,
      0,0,0,0,0,0,0,0
    };

    int turn; //1 and -1
    int aiMode; //0 = off, 1 = play for positive(white) (will be able to play for different colors in future, for now it plays as white only)
    int maxDepth;
    int minDepth = 3;
    int minCalc = 5; //minimum amount of moves calculated per depth
    int maxCalcTime;
    int collisions;
    int seed;
    int positionsViewed;
    long zobristBoard = 0;
  
    HashMap<Integer, String> boardPrintKey = new HashMap<Integer, String>();
    HashMap<String, Integer> fenKey = new HashMap<String, Integer>();
    HashMap<Byte, Integer> pieceToEval = new HashMap<Byte, Integer>();
    HashMap<Integer, ArrayList<Integer>> intToList = new HashMap<Integer, ArrayList<Integer>>();
    ArrayList<byte[]> pieceToPosEval = new ArrayList<byte[]>();
  
    ArrayList<Square> knightMoves = new ArrayList<Square>();
    //{
    //  private static final long serialVersionUID = 1L;
    //  {
    //    knightMoves.add(new Square(1, 2));
    //    knightMoves.add(new Square(1,2));
    //    knightMoves.add(new Square(1,-2));
    //    knightMoves.add(new Square(2,1));
    //    knightMoves.add(new Square(2,-1));
    //    knightMoves.add(new Square(-1,2));
    //    knightMoves.add(new Square(-1,-2));
    //    knightMoves.add(new Square(-2,1));
    //    knightMoves.add(new Square(-2,-1));
    //  }
    //};
    ArrayList<Square> bishopDirections = new ArrayList<Square>();
    //{
    //  private static final long serialVersionUID = 1L;
    //  {
    //    bishopDirections.add(new Square(1,1));
    //    bishopDirections.add(new Square(1,-1));
    //    bishopDirections.add(new Square(-1,1));
    //    bishopDirections.add(new Square(-1,-1));
    //  }
    //};
    ArrayList<Square> rookDirections = new ArrayList<Square>();
    //{
    //  private static final long serialVersionUID = 1L;
    //  {
    //    rookDirections.add(new Square(1,0));
    //    rookDirections.add(new Square(0,1));
    //    rookDirections.add(new Square(-1,0));
    //    rookDirections.add(new Square(0,-1));
    //  }
    //};
    ArrayList<Square> queenDirections = new ArrayList<Square>();
    //{
    //  private static final long serialVersionUID = 1L;
    //  {
    //    queenDirections.addAll(bishopDirections);
    //    queenDirections.addAll(rookDirections);
    //  }
    //};
    ArrayList<Square[]> pins = new ArrayList<Square[]>();
    ArrayList<Square[]> checks = new ArrayList<Square[]>();
    ArrayList<Move> moveLog = new ArrayList<Move>();
    ArrayList<Move> currentValidMoves = new ArrayList<Move>();
    ArrayList<CastleRights> castleRightsLog = new ArrayList<CastleRights>();
    ArrayList<Integer> enPassantLog = new ArrayList<Integer>();
    ArrayList<FoundEval> transpositionTable = new ArrayList<FoundEval>();


    ArrayList<Integer> Pieces = new ArrayList<Integer>();

    ArrayList<Integer> wPawns = new ArrayList<Integer>();
    ArrayList<Integer> wKnights = new ArrayList<Integer>();
    ArrayList<Integer> wBishops = new ArrayList<Integer>();
    ArrayList<Integer> wRooks = new ArrayList<Integer>();
    ArrayList<Integer> wQueens = new ArrayList<Integer>();

    ArrayList<Integer> bPawns = new ArrayList<Integer>();
    ArrayList<Integer> bKnights = new ArrayList<Integer>();
    ArrayList<Integer> bBishops = new ArrayList<Integer>();
    ArrayList<Integer> bRooks = new ArrayList<Integer>();
    ArrayList<Integer> bQueens = new ArrayList<Integer>();

    Square pKloc; //positive king location
    Square nKloc; //negative king location
    int enPassant = -1; //possible location of enpassant capture (erow, ecol)
    CastleRights castleRights = new CastleRights(false, false, false, false);
  

  
    GameState(String fen, int iAiMode, int iMaxDepth, int iMaxCalcTime, int iSeed)
    {
      aiMode = iAiMode;
      maxDepth = iMaxDepth;
      maxCalcTime = iMaxCalcTime;
      seed = iSeed;
  
      //constructed here because it is needed for crreation of board
      fenKey.put("p", 7);
      fenKey.put("n", 8);
      fenKey.put("b", 9);
      fenKey.put("r", 10);
      fenKey.put("q", 11);
      fenKey.put("k", 12);
      fenKey.put("P", 1);
      fenKey.put("N", 2);
      fenKey.put("B", 3);
      fenKey.put("R", 4);
      fenKey.put("Q", 5);
      fenKey.put("K", 6);

      intToList.put(7, bPawns);
      intToList.put(8, bKnights);
      intToList.put(9, bBishops);
      intToList.put(10, bRooks);
      intToList.put(11, bQueens);
      intToList.put(1, wPawns);
      intToList.put(2, wKnights);
      intToList.put(3, wBishops);
      intToList.put(4, wRooks);
      intToList.put(5, wQueens);

      knightMoves.add(new Square(1, 2));
      knightMoves.add(new Square(1,-2));
      knightMoves.add(new Square(2,1));
      knightMoves.add(new Square(2,-1));
      knightMoves.add(new Square(-1,2));
      knightMoves.add(new Square(-1,-2));
      knightMoves.add(new Square(-2,1));
      knightMoves.add(new Square(-2,-1));

      bishopDirections.add(new Square(1,1));
      bishopDirections.add(new Square(1,-1));
      bishopDirections.add(new Square(-1,1));
      bishopDirections.add(new Square(-1,-1));

      rookDirections.add(new Square(1,0));
      rookDirections.add(new Square(0,1));
      rookDirections.add(new Square(-1,0));
      rookDirections.add(new Square(0,-1));

      queenDirections.addAll(bishopDirections);
      queenDirections.addAll(rookDirections);
  
      //contruct lists and maps
      int square = 0;
      for (int i = 0; i < fen.length(); i++)
      {
        String c = fen.substring(i, i+1);
        if (square <= 63)
        {
          if (isNumeric(c))
          {
            square += Integer.valueOf(c);
          }
          else if (!c.equals("/"))
          {
            int a = fenKey.get(c);
            int index = -square + square%8 + square%8 + 56;
            board[index] = (byte)a;
            addPieceToLists(a, index);

            square++;
          }        
        }
        else 
        {
          if (c.equals("w"))
          {
            turn = 1;
          }
          else if (c.equals("b"))
          {
            turn = -1;
          }
          else if (c.equals("K"))
          {
            castleRights.wKs = true;
          }
          else if (c.equals("k"))
          {
            castleRights.bKs = true;
          }
          else if (c.equals("Q"))
          {
            castleRights.wQs = true;
          }
          else if (c.equals("q"))
          {
            castleRights.bQs = true;
          }
        }
      }
      cgv();
      zobristBoard = (new FoundEval(board, turn, castleRights, enPassant)).board;
    }
    void cgv() //construct game variables
    {
      boardPrintKey.put(0,"--");
      boardPrintKey.put(1,"wP");
      boardPrintKey.put(2,"wN");
      boardPrintKey.put(3,"wB");
      boardPrintKey.put(4,"wR");
      boardPrintKey.put(5,"wQ");
      boardPrintKey.put(6,"wK");
      boardPrintKey.put(7,"bP");
      boardPrintKey.put(8,"bN");
      boardPrintKey.put(9,"bB");
      boardPrintKey.put(10,"bR");
      boardPrintKey.put(11,"bQ");
      boardPrintKey.put(12,"bK");
  
      pieceToEval.put((byte)0,0);
      pieceToEval.put((byte)1,100);
      pieceToEval.put((byte)2,320);
      pieceToEval.put((byte)3,330);
      pieceToEval.put((byte)4,500);
      pieceToEval.put((byte)5,900);
      pieceToEval.put((byte)6,20000);
      pieceToEval.put((byte)7,-100);
      pieceToEval.put((byte)8,-320);
      pieceToEval.put((byte)9,-330);
      pieceToEval.put((byte)10,-500);
      pieceToEval.put((byte)11,-900);
      pieceToEval.put((byte)12,-20000);

      pieceToPosEval.add(pawnPosEval);
      pieceToPosEval.add(knightPosEval);
      pieceToPosEval.add(bishopPosEval);
      pieceToPosEval.add(rookPosEval);
      pieceToPosEval.add(queenPosEval);
      pieceToPosEval.add(kingPosEval);
  
      for (int row = 0; row < 8; row++)
      {
        for (int col = 0; col < 8; col++)
        {
          if (board[row*8+col] == 6)
          {
            pKloc = new Square(row, col);
          }
          else if (board[row*8+col] == 12)
          {
            nKloc = new Square(row, col);
          }
        }
      }
      castleRightsLog.add(new CastleRights(castleRights.wKs, castleRights.wQs, castleRights.bKs, castleRights.bQs));
      enPassantLog.add(enPassant);
  
      Random rand = new Random(seed); // 421948721 
      long[] irngMap = new long[851]; //(OBJECTIVE IS TO CREATE KEYS THAT ALMOST NEVER OVERLAP) 1 number for each possible peice on each possible square + 1 for empty(13 x 64), 1 number for each castleRights boolean (8), 1 number for each possible enpassant column plus no enpassant column(9) + w turn b turn(2)
      for (int i = 0; i < 851; i++) //negative or positive for turn
      {
        irngMap[i] = rand.nextLong();
      }
      FoundEval.rngMap = irngMap;

      currentValidMoves = getValidMoves();
    }
  
    public static boolean isNumeric(String str)
    {
      try
      {
        Integer.parseInt(str);
        return true;
      }
      catch (Exception e)
      {
        return false;
      }
    }
    public int getColor(int piece)
    {
      return piece <= 6? (piece == 0? 0 : 1) : -1;
    }
    public int getType(int piece)
    {
      return piece <= 6? piece : piece - 6;
    }
  
    public void printBoard()
    {
      String str = "";
      for (int row = 7; row >= 0; row--)
      {
        str += String.valueOf(row+1) + " ";
        for (int col = 0; col < 8; col++)
        {
          str += boardPrintKey.get((int)board[row*8+col]) + " ";
        }
        str += "\n";
      }
      System.out.println(str + "  a  b  c  d  e  f  g  h");
      System.out.println("Zobrist        : " + String.valueOf(zobristBoard));
      System.out.println("Castle Rights  : " + String.valueOf(castleRights.wKs) + String.valueOf(castleRights.wQs) + String.valueOf(castleRights.bKs) + String.valueOf(castleRights.bQs));
      System.out.println("En Passant Col : " + String.valueOf(enPassant));
      printCastleRightsLog();
    }
    public void printLayout()
    {
      String str = "";
      for (int row = 7; row >= 0; row--)
      {
        str += String.valueOf(row) + " ";
        for (int col = 0; col < 8; col++)
        {
          str += String.valueOf(row*8+col) + " ";
        }
        str += "\n";
      }
      System.out.println(str + "  0  1  2  3  4  5  6  7");
    }
    public void printMoveLog()
    {
      System.out.print("\n");
      for (int i = 0; i < moveLog.size(); i++)
      {
        System.out.print(moveLog.get(i).getNotation() + " ");
      }
    }
    public void printCastleRightsLog()
    {
      System.out.print("\n");
      for (int i = 0; i < castleRightsLog.size(); i++)
      {
        var icastleRights = castleRightsLog.get(i);
        System.out.print(String.valueOf(icastleRights.wKs) + String.valueOf(icastleRights.wQs) + String.valueOf(icastleRights.bKs) + String.valueOf(icastleRights.bQs) + " ");
      }
      System.out.print("\n");
    }

    public void setPiece(int piece, int index)
    {

      removePieceFromList(board[index], index);
      addPieceToLists(piece, index);
      zobristSet(index, piece);
      board[index] = (byte)piece;
    }

    public void makeMove(Move move)
    {
      setPiece(move.pieceMoved, move.erow*8+move.ecol);
      setPiece(0, move.srow*8+move.scol);

      if (turn == 1)
      {
        if (move.isCastle)
        {
          if (move.ecol == 6)
          {
            setPiece(4, 5);
            setPiece(0, 7);
          }
          else
          {
            setPiece(4, 3);
            setPiece(0, 0);
          }
        }
        else if (move.isEnPassent)
        {
          setPiece(0, 32+move.ecol);
        }
        else if (move.isPawnPromotion)
        {
          setPiece(5, 56+move.ecol);
        }
        if (move.pieceMoved == 6)
        {
          pKloc = new Square(move.erow, move.ecol);//positive king location
  
          zobristToggleWKS(false);
          zobristToggleWQS(false);
          castleRights.wKs = false;
          castleRights.wQs = false;
  
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        else if (move.pieceMoved == 4)
        {
          if (castleRights.wKs || castleRights.wQs)
          {
            if (move.scol == 7)
            {
              zobristToggleWKS(false);
              castleRights.wKs = false;
            }
            else if (move.scol == 0)
            {
              zobristToggleWQS(false);
              castleRights.wQs = false;
            }
          }
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        else if (move.pieceMoved == 1 && move.srow == 1 && move.erow == 3)
        {
          zobristSetEnPassant(move.ecol);
          enPassant = move.ecol;
        }
        else
        {
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        if (move.pieceCaptured == 10)
        {
          if (castleRights.bKs && move.ecol == 7)
          {
            zobristToggleBKS(false);
            castleRights.bKs = false;
          }
          else if (castleRights.bQs && move.ecol == 0)
          {
            zobristToggleBQS(false);
            castleRights.bQs = false;
          }
        }
      }
      else
      {
        if (move.isCastle)
        {
          if (move.ecol == 6)
          {
            setPiece(10, 61);
            setPiece(0, 63);
          }
          else
          {
            setPiece(10, 59);
            setPiece(0, 56);
          }
        }
        else if (move.isEnPassent)
        {
          setPiece(0, 24+move.ecol);
        }
        else if (move.isPawnPromotion)
        {
          setPiece(11, move.ecol);
        }
        if (move.pieceMoved == 12)
        {
          nKloc = new Square(move.erow, move.ecol);
  
          zobristToggleBKS(false);
          zobristToggleBQS(false);
          castleRights.bKs = false;
          castleRights.bQs = false;
  
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        else if (move.pieceMoved == 10)
        {
          if (castleRights.bKs || castleRights.bQs)
          {
            if (move.scol == 7)
            {
              zobristToggleBKS(false);
              castleRights.bKs = false;
            }
            else if (move.scol == 0)
            {
              zobristToggleBQS(false);
              castleRights.bQs = false;
            }
          }
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        else if (move.pieceMoved == 7 && move.srow == 6 && move.erow == 4)
        {
          zobristSetEnPassant(move.ecol);
          enPassant = move.ecol;
        }
        else
        {
          zobristSetEnPassant(-1);
          enPassant = -1;
        }
        if (move.pieceCaptured == 4)
        {
          if (castleRights.wKs && move.ecol == 7)
          {
            zobristToggleWKS(false);
            castleRights.wKs = false;
          }
          else if (castleRights.wQs && move.ecol == 0)
          {
            zobristToggleWQS(false);
            castleRights.wQs = false;
          }
        }
      }
      zobristToggleTurn();
      turn = -turn;
      moveLog.add(move);
      castleRightsLog.add(new CastleRights(castleRights.wKs, castleRights.wQs, castleRights.bKs, castleRights.bQs));
      enPassantLog.add(enPassant);
    }


    public void undoMove()
    {
      var move = moveLog.get(moveLog.size()-1);
      moveLog.remove(moveLog.size()-1);
  
      var cr = castleRightsLog.get(castleRightsLog.size()-2);
      zobristToggleWKS(cr.wKs);
      zobristToggleWQS(cr.wQs);
      zobristToggleBKS(cr.bKs);
      zobristToggleBQS(cr.bQs);
      castleRights = new CastleRights(cr.wKs, cr.wQs, cr.bKs, cr.bQs);
      castleRightsLog.remove(castleRightsLog.size()-1);
  
      zobristSetEnPassant(enPassantLog.get(enPassantLog.size()-2));
      enPassant = enPassantLog.get(enPassantLog.size()-2);
      enPassantLog.remove(enPassantLog.size()-1);
  
      setPiece(move.pieceMoved, move.srow*8+move.scol);
      setPiece(move.pieceCaptured, move.erow*8+move.ecol);
      if (turn == -1)
      {
        if (move.isCastle)
        {
          if (move.ecol == 6)
          {
            setPiece(0, 5);
            setPiece(4, 7);
          }
          else
          {
            setPiece(0, 3);
            setPiece(4, 0);
          }
        }
        else if (move.isEnPassent)
        {
          setPiece(7, 32+move.ecol);
        }
        if (move.pieceMoved == 6)
        {
          pKloc = new Square(move.srow, move.scol);//positive king location
        }
      }
      else
      {
        if (move.isCastle)
        {
          if (move.ecol == 6)
          {
            setPiece(0, 61);
            setPiece(10, 63);
          }
          else
          {
            setPiece(0, 59);
            setPiece(10, 56);
          }
        }
        else if (move.isEnPassent)
        {
          zobristSet(24+move.ecol, 1);
          board[24+move.ecol] = 1;
          setPiece(1, 24+move.ecol);
        }
        if (move.pieceMoved == 12)
        {
          nKloc = new Square(move.srow, move.scol);
        }
      }
      zobristToggleTurn();
      turn = -turn;
    }
  
    public boolean isInValid(Move move)
    {
      int size = currentValidMoves.size();
      System.out.println("Possible moves: ");
      for (int i = 0; i < size; i++)
      {
        System.out.println("-"+currentValidMoves.get(i).getNotation());
        if (move.getNotation().equals(currentValidMoves.get(i).getNotation()))
        {
          return true;
        }
      }
      return false;
    }
    ArrayList<Move> getValidMoves()
    {
      getPinsAndChecks();
      var validMoves = getPossibleMoves();
      if (checks.size() == 1) //if there is only one check, eliminate all moves but those that move king, block check, or eliminate piece giving check
      {
        Square[] check = checks.get(0);
        for (int i = validMoves.size()-1; i >= 0; i--)
        {
          Move move = validMoves.get(i);
          if (move.pieceMoved != 6 && move.pieceMoved != 12)
          {
            if (!isInSquares(new Square(move.erow, move.ecol), check))
            {
              validMoves.remove(i);
            }
          }
        }
        return validMoves;
      }
      else if (checks.size() > 1) //if there is more than one check, eliminate all moves but king moves
      {
        return getKingMoves();
      }
      else 
      {
        return validMoves;
      }
      //afterwards, remove all moves of pinned pieces
    }
    boolean isInSquares(Square square, Square[] arr)
    {
      for (int i = 0; i < arr.length; i++)
      {
        if (arr[i] == null)
        {
          break;
        }
        else if (square.row == arr[i].row && square.col == arr[i].col)
        {
          return true;
        }
      }
      return false;
    }
    ArrayList<Move> getPossibleMoves()
    {
      var validMoves = new ArrayList<Move>();
      if (turn == 1)
      {
        int size = wPawns.size();
        for (int i = 0; i < size; i++)
        {
          int s = wPawns.get(i);
          validMoves.addAll(getPawnMoves(s));
        }
        size = wKnights.size();
        for (int i = 0; i < size; i++)
        {
          int s = wKnights.get(i);
          validMoves.addAll(getKnightMoves(s));
        }
        size = wBishops.size();
        for (int i = 0; i < size; i++)
        {
          int s = wBishops.get(i);
          validMoves.addAll(getDirectionalMoves(s, bishopDirections));
        }
        size = wRooks.size();
        for (int i = 0; i < size; i++)
        {
          int s = wRooks.get(i);
          validMoves.addAll(getDirectionalMoves(s, rookDirections));
        }
        size = wQueens.size();
        for (int i = 0; i < size; i++)
        {
          int s = wQueens.get(i);
          validMoves.addAll(getDirectionalMoves(s, queenDirections));
        }
        validMoves.addAll(getKingMoves());
      }
      else
      {
        int size = bPawns.size();
        for (int i = 0; i < size; i++)
        {
          int s = bPawns.get(i);
          validMoves.addAll(getPawnMoves(s));
        }
        size = bKnights.size();
        for (int i = 0; i < size; i++)
        {
          int s = bKnights.get(i);
          validMoves.addAll(getKnightMoves(s));
        }
        size = bBishops.size();
        for (int i = 0; i < size; i++)
        {
          int s = bBishops.get(i);
          validMoves.addAll(getDirectionalMoves(s, bishopDirections));
        }
        size = bRooks.size();
        for (int i = 0; i < size; i++)
        {
          int s = bRooks.get(i);
          validMoves.addAll(getDirectionalMoves(s, rookDirections));
        }
        size = bQueens.size();
        for (int i = 0; i < size; i++)
        {
          int s = bQueens.get(i);
          validMoves.addAll(getDirectionalMoves(s, queenDirections));
        }
        validMoves.addAll(getKingMoves());
      }

      return validMoves;
    }
    void getPinsAndChecks()
    {
      pins = new ArrayList<Square[]>();
      checks = new ArrayList<Square[]>();
      Square kingloc = turn == 1 ? pKloc : nKloc;
      for (int i = 0; i < 8; i++)
      {
        Square direction = queenDirections.get(i);
        var foundSquares = new Square[7];
        Square possiblePin = null;
        for (int m = 1; m < 8; m++)
        {
          var sSquare = new Square(direction.row*m+kingloc.row, direction.col*m+kingloc.col);
          foundSquares[m-1] = sSquare;
          if (sSquare.row >= 0 && sSquare.row <= 7 && sSquare.col >= 0 && sSquare.col <= 7)
          {
            int spiece = board[sSquare.row*8+sSquare.col]; //scanner piece
            int type = getType(spiece);
            if (type != 0)
            {
              if (getColor(spiece) == turn)     //0(1,1)    1(1,-1)    2(-1,1)    3(-1,-1)    4(1,0)    5(0,1)    6(-1,0)    7(0,-1)
              {
                if (possiblePin == null) //if its the first run in with a ally piece continue exploring to look for a pin
                {
                  possiblePin = sSquare;          
                }
                else if(!(getType(board[possiblePin.row*8+possiblePin.col]) == 1 && getColor(board[possiblePin.row*8+possiblePin.col]) != turn))//if its the second, stop exploring direction for pins and checks
                {
                  break;
                }
              }
              // BECAUSE OF PREVIOUS IF, ALL PAST THIS POINT ARE ENEMY OR EMPTY
              else if (type == 1) //if there is a pawn, magnitude of 1, in front and diagonal
              {
                if (m == 1 && (i >= (turn == 1? 0 : 2) && (i <= (turn == 1? 1 : 3))))
                {
                  checks.add(foundSquares);
                  break;                  
                }
                else if (enPassant == sSquare.col && (turn == 1? sSquare.row == 4 : sSquare.row == 3)) //this is in order to deal with situation where enpassant would cause check, it is tho only situation where and enemy piece can "pinned" to your king
                {
                  possiblePin = sSquare;
                }
                else
                {
                  break;
                }
              }
              /*
              the following if statement checks for the following scenarios, in which it would mean that the king is in line of check (not including knights)
              1. there is a queen
              2. there is a bishop, diagonally away
              3. there is a rook horizontally or vertically away
              */
              else if ((type == 5) || (type == 3 && i <= 3) || (type == 4 && i >= 4))
              {
                if (possiblePin == null) //if we have not collided yet
                {
                  checks.add(foundSquares);
                }
                else //if we have run into a piece before (we know its an ally piece because otherwise we break)
                {
                  pins.add(new Square[]{possiblePin, direction});
                }
                break;
              }
              else 
              {
                break;
              }
            }
          }
          else
          {
            break;
          }
        }
      }
      for (int i = 0; i < 8; i++) //check for knight checks
      {
        int srow = knightMoves.get(i).row + kingloc.row; //scanner row
        int scol = knightMoves.get(i).col + kingloc.col; //scanner col
        if (srow >= 0 && srow <= 7 && scol >= 0 && scol <= 7)
        {
          int spiece = board[srow*8 + scol];
          if (getColor(spiece) == -turn && getType(spiece) == 2)
          {
            checks.add(new Square[]{new Square (srow, scol)});
          }
        }
      }
    }
  
    ArrayList<Move> getPawnMoves(int index)
    {
      int row = (int)Math.floor(index/8);
      int col = index - row*8;

      boolean piecepinned = false;
      boolean enPassantPinned = false;
      Square pindirection = new Square(10,10);
      for (int i = pins.size()-1; i >= 0; i--)
      {
        var pin = pins.get(i);
        if (pin[0].row == row && pin[0].col == col)
        {
          piecepinned = true;
          pindirection = pin[1];
        }
        if (pin[0].col == enPassant && pin[0].row == (turn == 1? 4 : 3))
        {
          enPassantPinned = true;
        }
      }
      var validMoves = new ArrayList<Move>();
      if (board[(row+turn)*8+col] == 0 && (!piecepinned || (pindirection.row == turn && pindirection.col == 0))) //1 forward
      {
        validMoves.add(new Move(row, col, row+turn, col, board));
        if (((row == 1 && turn == 1)||(row == 6 && turn == -1)) && board[(row+(turn*2))*8+col] == 0)
        {
          validMoves.add(new Move(row, col, row+(turn*2), col, board));
        }
      }
  
      if ((!piecepinned || (pindirection.row == turn && pindirection.col == 1)) && col <= 6) //forward right capture
      {
        if (getColor(board[(row+turn)*8+col+1]) == -turn)//regular capture
          validMoves.add(new Move(row, col, row+turn, col+1, board));
        if (!enPassantPinned && col+1 == enPassant && ((row == 4 && turn == 1) || (row == 3 && turn == -1)))//en passant
        {
          validMoves.add(new Move(row, col, row+turn, col+1, board, false));
        }
      }
      if ((!piecepinned || (pindirection.row == turn && pindirection.col == -1)) && col >= 1) //forward left capture
      {
        if (getColor(board[(row+turn)*8+col-1]) == -turn)//regular capture
          validMoves.add(new Move(row, col, row+turn, col-1, board));
        if (!enPassantPinned && col-1 == enPassant && ((row == 4 && turn == 1) || (row == 3 && turn == -1)))//en passant
        {
          validMoves.add(new Move(row, col, row+turn, col-1, board, false));
        }
      }
      return validMoves;
    }
    ArrayList<Move> getKnightMoves(int index)
    {
      int row = (int)Math.floor(index/8);;
      int col = index - row*8;
      var validMoves = new ArrayList<Move>();
      for (int i = pins.size()-1; i >= 0; i--)
      {
        var pin = pins.get(i);
        if (pin[0].row == row && pin[0].col == col)
        {
          return validMoves;
        }
      }
      for (int i = 0; i < 8; i++)
      {
        Square square = knightMoves.get(i);
        int drow = square.row;
        int dcol = square.col;
        if (row+drow >= 0 && row+drow <= 7 && col+dcol >= 0 && col+dcol <= 7)
        {
          int epiece = board[(row+drow)*8+col+dcol];
          if (epiece == 0 || getColor(epiece) == -turn)
          {
            validMoves.add(new Move(row, col, row+drow, col+dcol, board));
          }        
        } 
      }
      return validMoves;
    }
    ArrayList<Move> getKingMoves()
    {
      var validMoves = new ArrayList<Move>();
      Square kingloc = turn == 1 ? pKloc : nKloc;
      board[kingloc.row*8+kingloc.col] = 0;
      for (int i = 0; i < 8; i++)//loop through different thetas, aka directions that we are exploring
      {
        int drow = queenDirections.get(i).row + kingloc.row;
        int dcol = queenDirections.get(i).col + kingloc.col;
        if (drow >= 0 && drow <= 7 && dcol >= 0 && dcol <= 7)
        {
          int epiece = board[(drow)*8+dcol];
          if ((epiece == 0 || getColor(epiece) == -turn) && !isAttacked(drow, dcol))
          {
            board[kingloc.row*8+kingloc.col] = (byte)(turn == 1? 6 : 12);
            var move = new Move(kingloc.row, kingloc.col, drow, dcol, board);
            board[kingloc.row*8+kingloc.col] = 0;
            validMoves.add(move);
          }
        }
      }
      board[kingloc.row*8+kingloc.col] = (byte)(turn == 1? 6 : 12);
      validMoves.addAll(getCastleMoves());
      return validMoves;
    }
    ArrayList<Move> getDirectionalMoves(int index, ArrayList<Square> directions)
    {
      int row = (int)Math.floor(index/8);;
      int col = index - row*8;
      boolean piecepinned = false;
      Square pindirection = new Square(10,10);
      for (int i = pins.size()-1; i >= 0; i--)
      {
        var pin = pins.get(i);
        if (pin[0].row == row && pin[0].col == col)
        {
          piecepinned = true;
          pindirection = pin[1];
        }
      }
    
      var validMoves = new ArrayList<Move>();
      for (int i = 0; i < directions.size(); i++)//theta, aka direction that we are exploring (just saying theta bc im cool)
      {
        for (int m = 1; m < 8; m++)//m for magnitude (again precalc terminology hehe)
        {
          int drow = directions.get(i).row*m;
          int dcol = directions.get(i).col*m;
          if (row+drow >= 0 && row+drow <= 7 && col+dcol >= 0 && col+dcol <= 7 && (!piecepinned || (pindirection.row == directions.get(i).row && pindirection.col == directions.get(i).col)))
          {
            int epiece = board[(row+drow)*8+col+dcol];
            if (epiece == 0)
            {
              validMoves.add(new Move(row, col, row+drow, col+dcol, board));
            }
            else if (getColor(epiece) == -turn)
            {
              validMoves.add(new Move(row, col, row+drow, col+dcol, board));
              break;
            }
            else
            {
              break;
            }
          }
          else
          {
            break;
          }
        }
      }
      return validMoves;
    }
    ArrayList<Move> getCastleMoves()
    {
      Square kingloc = turn == 1 ? pKloc : nKloc;
      var validMoves = new ArrayList<Move>();
      if (checks.size() == 0)
      {
        if (turn == 1)
        {
          if (castleRights.wKs)
          {
            if (board[5] == 0 && board[6] == 0 && !isAttacked(0, 5) && !isAttacked(0, 6))
            {
              validMoves.add(new Move(kingloc.row, kingloc.col, 0, 6, board, true));
            }
          }
          if (castleRights.wQs)
          {
            if (board[1] == 0 && board[2] == 0 && board[3] == 0 && !isAttacked(0, 1) && !isAttacked(0, 2) && !isAttacked(0, 3))
            {
              validMoves.add(new Move(kingloc.row, kingloc.col, 0, 2, board, true));
            }
          }
        }
        else
        {
          if (castleRights.bKs)
          {
            if (board[61] == 0 && board[62] == 0 && !isAttacked(7, 5) && !isAttacked(7, 6))
            {
              validMoves.add(new Move(kingloc.row, kingloc.col, 7, 6, board, true));
            }
          }
          if (castleRights.bQs)
          {
            if (board[57] == 0 && board[58] == 0 && board[59] == 0 && !isAttacked(7, 2) && !isAttacked(7, 3))
            {
              validMoves.add(new Move(kingloc.row, kingloc.col, 7, 2, board, true));
            }
          }
        }
      }
      return validMoves;
    }
    boolean isAttacked(int row, int col)
    {
      for (int i = 0; i < 8; i++)
      {
        Square direction = queenDirections.get(i);
        for (int m = 1; m < 8; m++)
        {
          var sSquare = new Square(direction.row*m+row, direction.col*m+col);
          if (sSquare.row >= 0 && sSquare.row <= 7 && sSquare.col >= 0 && sSquare.col <= 7)
          {
            int spiece = board[sSquare.row*8+sSquare.col]; //scanner piece
            int type = getType(spiece);
            if (type != 0)
            {
              if (getColor(spiece) == turn)
              {
                break;
              }
              else if ((type == 1 && m == 1 && (i >= (turn == 1? 0 : 2) && (i <= (turn == 1? 1 : 3)))) || (type == 5) || (type == 3 && i <= 3) || (type == 4 && i >= 4) || (type == 6 && m == 1))
              {
                return true;
              }
              else
              {
                break;
              }
            }
          }
          else
          {
            break;
          }
        }
      }
      for (int i = 0; i < 8; i++)
      {
        int srow = knightMoves.get(i).row+row;
        int scol = knightMoves.get(i).col+col;
        if (srow >= 0 && srow <= 7 && scol >= 0 && scol <= 7)
        {
          int spiece = board[srow*8+scol];
          if (getColor(spiece) == -turn && getType(spiece) == 2)
          {
            return true;          
          }
        }
      }
      return false;
    }
  
    public Move getBestMove()
    {
      long start = System.currentTimeMillis();
      int bestEval = -100000;
      Move bestMove = new Move();
  
      for (int depth = 1; (depth<=maxDepth && ((System.currentTimeMillis() - start)/1000 < maxCalcTime) && bestEval != Short.MAX_VALUE); depth++)
      {
        int count = 0;
        bestEval = -100000;

//move generation
        var orderedMoves = new ArrayList<Move>();
        var validMoves = new ArrayList<Move>();
        int r = isFoundEval(zobristBoard);
        if (r != -1)
        {
          validMoves = transpositionTable.get(r).orderedMoves;
        }
        else 
        {
          validMoves = getValidMoves();
        }

//position exploration
        if (validMoves.size() == 0)
        {
          System.out.println("Game over, no valid moves found");
        }
        else
        {
          System.out.println("Move | Eval | Calculation time | Depth");
          System.out.println("—————————————————————————————————————————");
          for (int ii = 0; ii < transpositionTable.size(); ii++)
          {
            transpositionTable.get(ii).uses = 0;
          }
          for (int i = 0; i < validMoves.size(); i++)
          {
            count++;
            long startt = System.currentTimeMillis();
            Move move = validMoves.get(i);
            makeMove(move);
            int eval = -negaMax(depth-1, -Short.MAX_VALUE, Short.MAX_VALUE);
            move.eval = eval;
            insertOrderedMove(move, orderedMoves);
            undoMove();
            System.out.println(move.getNotation() + " | " + (eval < 0? "" : " ") + (Math.abs(eval) > 9? "" : " ") + (Math.abs(eval) > 99? "" : " ") + String.valueOf(eval) + " |    " + String.valueOf(((double)System.currentTimeMillis()-startt)/1000) + " seconds |     " + String.valueOf(depth));
            if (eval > bestEval)
            {
              bestMove = move;
              bestEval = eval;
              if (bestEval == Short.MAX_VALUE)
              {
                break;
              }
            }
            if ((System.currentTimeMillis() - start)/1000 >= maxCalcTime && count >= minCalc)
            {
              break;
            }
          }
          for (int ii = 0; ii < transpositionTable.size(); ii++)
          {
            if (transpositionTable.get(ii).uses == 0)
            {
              transpositionTable.remove(ii);
            }
          }
          insertFoundEval(new FoundEval(depth, bestEval, zobristBoard, orderedMoves));
        }
      }
      System.out.println("Best Move                | " + bestMove.getNotation());
      System.out.println("Best Evaluation          | " + bestEval);
      System.out.println("Transposition table size | " + String.valueOf(transpositionTable.size()));
      System.out.println("Transposition collisions | " + String.valueOf(collisions));
      System.out.println("Collision rate(% of pos) | " + String.valueOf((double)collisions/transpositionTable.size()));
      System.out.println("Time elapsed             | " + String.valueOf(((double)System.currentTimeMillis()-start)/1000));
      System.out.println("Nodes viewed             | " + String.valueOf(positionsViewed));
      System.out.println("Nodes viewed per second  | " + String.valueOf(positionsViewed/(((double)System.currentTimeMillis()-start)/1000)));
  
  
      collisions = 0;
      positionsViewed = 0;
      return bestMove;
    }
    public int negaMax(int depth, int alpha, int beta)
    {
      positionsViewed++;
      var orderedMoves = new ArrayList<Move>();
      var validMoves = new ArrayList<Move>();
      var boardSave = board.clone();
      if (depth <= 0)
      {
        return captureNegaMax(alpha, beta);
      }
      else
      {
        long zobristBoardSave = zobristBoard;
        int r = isFoundEval(zobristBoard);
        if (r != -1)
        {
          if (transpositionTable.get(r).depth >= depth)
          {
            return transpositionTable.get(r).eval;          
          }
          else 
          {
            FoundEval transposition = transpositionTable.get(r);
            validMoves = transposition.orderedMoves;
          }
        }
        else
        {
          validMoves = getValidMoves();
        }
        if (validMoves.size() == 0)
        {
          if (checks.size() == 0)
          {
            return 0;    
          }
          else
          {
            return -Short.MAX_VALUE;
          }
        }
        for (int i = 0; i < validMoves.size(); i++)
        {
          Move move = validMoves.get(i);
          makeMove(move);
          int eval = -negaMax(depth-1, -beta, -alpha);
          move.eval = eval;
          insertOrderedMove(move, orderedMoves);
          undoMove();
          if (zobristBoardSave != zobristBoard) //in case of collision reset variables an d try again with new validMoves set
          {
            printBoard();
            i = 0;
            zobristBoard = zobristBoardSave;
            board = boardSave.clone();
            orderedMoves.clear();
            validMoves = getValidMoves();
            collisions++;
            System.out.println("COLLISION | " + move.getNotation());
            printMoveLog();
            if (collisions > 10)
            {
              System.exit(0);
            }
          }
          if (eval > alpha)
          {
            alpha = eval;
          }
          if (alpha >= beta)
          {
            orderedMoves.addAll(validMoves.subList(i+1, validMoves.size()));
            break;
          }
        }
      }
      insertFoundEval(new FoundEval(depth, alpha, zobristBoard, orderedMoves)); //insert to transposition table
      return alpha;
    }
    int evalPos()
    {
      int eval = 0;
      int size = Pieces.size();
      for (int i = 0; i < size; i++)
      {
        int index = Pieces.get(i);
        byte piece = board[index];
        eval += pieceToEval.get(piece);
        if (piece <= 6)
        {
          eval += pieceToPosEval.get(piece-1)[index];            
        }
        else
        {
          int row = (int)Math.floor(index/8f);
          eval -= pieceToPosEval.get(getType(piece)-1)[(7-row)*8 + index-(row*8)];
        }
      }
      eval += (castleRights.wKs? 10 : 0) + (castleRights.wQs? 10 : 0) + (castleRights.bKs? -10 : 0) + (castleRights.bQs? -10 : 0);
      return eval*turn;      
    }
    void insertFoundEval(FoundEval foundEval)
    {
      int size = transpositionTable.size();
      for (int i = 0; i < size; i++)
      {
        if (transpositionTable.get(i).board > foundEval.board)
        {
          transpositionTable.add(i,foundEval);
          return;
        }
        else if (transpositionTable.get(i).board == foundEval.board)
        {
          transpositionTable.get(i).orderedMoves = foundEval.orderedMoves;
          transpositionTable.get(i).depth = foundEval.depth;
          transpositionTable.get(i).eval = foundEval.eval;

          return;
        }
      }
      transpositionTable.add(foundEval);
    }
    int isFoundEval(long izobrist) //returns -1 if false, else returns index of found eval in transposition table
    {
      int left = 0;
      int right = transpositionTable.size()-1;
      long prevBoard = 0;
      if (right != -1)
      {
        while (true)
        {
          long scan = transpositionTable.get(Math.round((left+right)/2f)).board;
          if (scan == izobrist)
          {
            transpositionTable.get(Math.round((left+right)/2f)).uses++;
            return Math.round((left+right)/2f);
          }
          else if (scan < izobrist)
          {
            left = Math.round((left+right)/2f);
          }
          else if (scan > izobrist)
          {
            right = Math.round((left+right)/2f);
          }
          if  (scan != prevBoard)
          {
            prevBoard = scan;
          }
          else
          {
            return -1;
          }
        }
      }
      return -1;
    }
    void insertOrderedMove(Move move, ArrayList<Move> list)
    {
      int size = list.size();
      for (int i = 0; i < size; i++)
      {
        if (list.get(i).eval < move.eval)
        {
          list.add(i,move);
          return;
        }
      }
      list.add(move);
    }

    public int captureNegaMax(int alpha, int beta)
    {
      positionsViewed++;
      var validMoves = getValidMoves();
      int stand_pat = evalPos();
      if( stand_pat >= beta )
        return beta;
      if( alpha < stand_pat )
        alpha = stand_pat;
      int eval;
      for (int i = 0; i < validMoves.size(); i++)
      {
        Move move = validMoves.get(i);
        if (move.pieceCaptured != 0)
        {
          makeMove(move);
          eval = -captureNegaMax( -beta, -alpha);
          undoMove();
          if (eval > alpha)
          {
            alpha = eval;
          }
          if (alpha >= beta)
          {
            break;
          }
        }
      }
      return alpha;
    }

    public int perftTest(int depth)
    {
      var validMoves = getValidMoves();
      int size = validMoves.size();
      int score = 0;
      for (int i = 0; i < size; i++)
      {
        makeMove(validMoves.get(i));
        int thisScore = getPossibilities(depth-1);
        score += thisScore;
        System.out.println(validMoves.get(i).getNotation() + " " + thisScore);
        undoMove();
      }
      return score;
    }

    public int getPossibilities(int depth)
    {
      if (depth == 0)
      {
        return 1;
      }
      var validMoves = getValidMoves();
      int size = validMoves.size();
      int score = 0;
      for (int i = 0; i < size; i++)
      {
        makeMove(validMoves.get(i));
        score += getPossibilities(depth-1);
        undoMove();
      }
      return score;
    }
    
    public void zobristSet(int i, int piece) //MUST BE CALLED BEFORE MAKING CHANGES
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[i*13+board[i]];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[i*13+piece];
    }
    public void zobristSetEnPassant(int col) //MUST BE CALLED BEFORE CHANGING EN PASSANT
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[841+enPassant];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[841+col];
    }
    public void zobristToggleWKS(boolean bool) //MUST BE CALLED BEFORE CHANGING WKS
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[castleRights.wKs ? 832 : 833];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[bool ? 832 : 833];
    }
    public void zobristToggleWQS(boolean bool) //MUST BE CALLED BEFORE CHANGING WQS
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[castleRights.wQs ? 834 : 835];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[bool ? 834 : 835];
    }
    public void zobristToggleBKS(boolean bool) //MUST BE CALLED BEFORE CHANGING BKS
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[castleRights.bKs ? 836 : 837];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[bool ? 836 : 837];
    }
    public void zobristToggleBQS(boolean bool) //MUST BE CALLED BEFORE CHANGING BQS
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[castleRights.bQs ? 838 : 839];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[bool ? 838 : 839];
    }
    public void zobristToggleTurn() //MUST BE CALLED BEFORE CHANGE
    {
      zobristBoard = zobristBoard ^ FoundEval.rngMap[turn == 1 ? 849 : 850];
      zobristBoard = zobristBoard ^ FoundEval.rngMap[turn == 1 ? 850 : 849];
    }

    public Move getMoveFromValid(Move move)
    {
      int size = currentValidMoves.size();
      for (int i = 0; i < size; i++)
      {
        if (move.getNotation().equals(currentValidMoves.get(i).getNotation()))
        {
          return currentValidMoves.get(i);
        }
      }
      System.out.println("REQUESTED INVALID");
      System.exit(0);
      return new Move();
    }

    void addPieceToLists(int piece, int index)
    {
      if (getType(piece) != 6 && piece != 0)
      {
        intToList.get(piece).add(index);
      }
      if (piece != 0)
      {
        Pieces.add(index);
      }
    }

    void removePieceFromList(int piece, int index)
    {
      if (getType(piece) != 6 && piece != 0)
      {
        var list = intToList.get(piece);
        int size = list.size();
        for (int i = 0; i < size; i++)
        {
          if (list.get(i) == index)
          {
            list.remove(i);
            break;
          }
        }        
      }
      Pieces.remove(Integer.valueOf(index));
    }
  }
