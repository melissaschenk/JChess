package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = new int[]{-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)
                        || (isSecondColumnExclusion(this.piecePosition,currentCandidateOffset))
                        || (isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset))
                        || (isEighthColumnExclusion(this.piecePosition,currentCandidateOffset))) {
                    continue;
                }

                final Tile candidateDistinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDistinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board,this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDistinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAllience();
                    if(this.pieceAllience != pieceAlliance) {
                        legalMoves.add(new  Move.AttackMove(board,this,candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15;
    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && candidateOffset == -10 || candidateOffset == 6;
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && candidateOffset == -6 || candidateOffset == 10;
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && candidateOffset == -15 || candidateOffset == -6
                || candidateOffset == 10 || candidateOffset == 17;
    }
}
