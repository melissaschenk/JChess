package com.chess.engine.board;

import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public class Board {

    private final List<Tile> gameBoard;

    private Board(Builder builder) {
        this.gameBoard = createGamboard(builder);
    }

    private static List<Tile> createGamboard(final Builder builder) {
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i,builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public Tile getTile(final int tileCoordinate) {
        return null;
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {

        }
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance alliance) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }


        public Board build() {
            return new Board(this);
        }

    }
}

