package com.example.demo.service.game.impl;

import com.example.demo.persistance.GameState;
import com.example.demo.repository.GameRepository;
import com.example.demo.service.game.GameService;
import com.example.demo.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    private PlayerService playerService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    public GameState initialiseGame(Long gameId, Long playerId) {
        try {

            if (playerId == null) {
                playerId = playerService.createPlayerWithoutName().getId();
            }

            GameState gameState = getGameState(gameId);

            if (gameState == null) {
                gameState = createNewGame(playerId);
            }
            else {
                gameState.setPlayer2Id(playerId);
            }

            gameRepository.save(gameState);

            return gameState;

        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public GameState getGameState(Long gameId) {
        try {
            return gameRepository.findById(gameId).get();
        } catch (NullPointerException e) {
            return null;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public GameState updateState(GameState gameState) {
        return new GameState();  //TODO implement game move
    }

    private GameState createNewGame(Long playerId) {
        GameState gameState = new GameState();
        gameState.setPlayer1Id(playerId);
        gameState.setPlayer1Score(0);
        gameState.setPlayer2Score(0);
        gameState.setState(List.of(6,6,6,6,6,6,6,6,6,6,6,6));
        return gameState;
    }

}
