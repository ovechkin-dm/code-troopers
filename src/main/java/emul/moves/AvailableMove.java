package emul.moves;

import emul.FutureWorld;
import model.Move;

import java.util.List;

public interface AvailableMove {

    public List<Move> getAvailableMoves(FutureWorld world);

}
