package uk.ac.bris.cs.oxo.standard;

import uk.ac.bris.cs.gamekit.matrix.Matrix;
import uk.ac.bris.cs.gamekit.matrix.SquareMatrix;
import uk.ac.bris.cs.gamekit.matrix.ImmutableMatrix;
import uk.ac.bris.cs.oxo.Cell;
import uk.ac.bris.cs.oxo.Player;
import uk.ac.bris.cs.oxo.Side;
import uk.ac.bris.cs.oxo.Spectator;
import static java.util.Objects.requireNonNull;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Consumer;

public class OXO implements OXOGame, Consumer<Move> {
        
    private Player noughtSide, crossSide;
    private Side currentSide;
    private int size;
    private SquareMatrix<Cell> matrix;

    public OXO(int size, Side startSide, Player nought, Player cross) {
            // TODO
            if(size <= 0) throw new IllegalArgumentException("size invalid");

            this.noughtSide = requireNonNull(nought);
            this.crossSide = requireNonNull(cross);
            this.currentSide = requireNonNull(startSide);
            this.size = size;
            this.matrix = new SquareMatrix<Cell>(size, new Cell());

    }

    @Override
    public void registerSpectators(Spectator... spectators) {
            // TODO
            throw new RuntimeException("Implement me");
    }

    @Override
    public void unregisterSpectators(Spectator... spectators) {
            // TODO
            throw new RuntimeException("Implement me");
    }

    @Override
    public void start() {
            // TODO
            Player player = (currentSide == Side.CROSS) ? crossSide : noughtSide;
            player.makeMove(this, validMoves(), this);
            Side win = winner();
    }
    
    private Side winner(){
        
        for (int i = 0; i < size; i++) {
            Cell previousC = matrix.row(i).get(0);
            for(Cell c : matrix.row(i)) {
                if(previousC == c) {
                    
                    return c.side().orElse(null);
                    
                }
                
                else break;
                previousC = c;
            }
        }
        
        for (int i = 0; i < size; i++) {
            Cell previousC = matrix.column(i).get(0);
            for(Cell c : matrix.column(i)) {
                if(previousC == c) {
                    if (i == size - 1) return c.side().orElse(null);
                    else previousC = c;
                }
                else break;
            }
        }
        
        Cell previousC = matrix.mainDiagonal().get(0);
        for (Cell c : matrix.mainDiagonal()) {
            if(previousC == c) {
                    if (c.) return c.side().orElse(null);
                }
                else break;
        }
        
        return null;
    }

    @Override
    public Matrix<Cell> board() {
            // TODO
            return new ImmutableMatrix<>(matrix);
    }

    @Override
    public Side currentSide() {
            // TODO
            return currentSide;
    }
    private Set<Move> validMoves() {
        Set<Move> moves = new HashSet<>();
        for (int row = 0; row < matrix.rowSize(); row++) {
            for (int col = 0; col < matrix.columnSize(); col++) {
            //add moves here via moves.add(new Move(row, col)) if the matrix is empty at this location
            if(matrix.get(row, col).isEmpty())
                moves.add(new Move(row, col));
            } 
        }
        //...
        //return the moves created
        return moves;
    }

    @Override
    public void accept(Move t) {      
        if(!validMoves().contains(t)) throw new IllegalArgumentException("not valid move");
        else {
            matrix.put(t.row, t.column, new Cell(currentSide));
        }
    }
}
