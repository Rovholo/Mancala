package com.example.demo.service.game;

import com.example.demo.persistance.GameState;
import com.example.demo.persistance.Player;

import java.time.LocalDate;
import java.util.List;

public interface GameService {

    GameState initialiseGame(Long gameId, Long playerId);

    GameState getGameState(Long id);

    GameState updateState(GameState gameState);

    GameState setGameState(GameState gameState);

}
