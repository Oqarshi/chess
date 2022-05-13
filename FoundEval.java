import java.util.ArrayList;

public class FoundEval
{
  public byte depth;
  public short eval;
  public byte uses;
  public long board;
  public static long[] rngMap;
  public ArrayList<Move> orderedMoves = new ArrayList<Move>();
  FoundEval(byte[] iboard, int iturn, CastleRights icastleRights, int ienPassant)
  {
    for (int i = 0; i < 64; i++)
    {
      board = board ^ rngMap[i*13+iboard[i]];
    }
    board = board ^ (icastleRights.wKs? rngMap[832] : rngMap[833]) ^ (icastleRights.wQs? rngMap[834] : rngMap[835]) ^ (icastleRights.bKs? rngMap[836] : rngMap[837]) ^ (icastleRights.bQs? rngMap[838] : rngMap[839]);
    board = board ^ rngMap[841+ienPassant];
    board = board ^ rngMap[iturn == 1 ? 849 : 850];
  }
  FoundEval(int idepth, int ieval, long zobristBoard, ArrayList<Move> iorderedMoves)
  {
    depth = (byte)idepth;
    eval = (short)ieval;
    orderedMoves = iorderedMoves;
    board = zobristBoard;
  }
}
