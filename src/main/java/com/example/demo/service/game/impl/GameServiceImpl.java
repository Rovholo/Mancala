package com.example.demo.service.game.impl;

import com.example.demo.persistance.GameState;
import com.example.demo.repository.GameRepository;
import com.example.demo.service.game.GameService;
import com.example.demo.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
            String errorMessage = String.format("Unable to initialise game state. Caught a %s. %s.", ex.getClass().getCanonicalName(), ex.getMessage());
            throw new RuntimeException(errorMessage,ex);
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
    public GameState setGameState(GameState gameState) {
        return gameRepository.save(gameState);
    }

    @Override
    public GameState updateState(GameState gameState) {
        try {
            GameState currGame = getGameState(gameState.getId());
            List<Integer> stateArray = new ArrayList<>(currGame.getState());
            int index = -1;
            for (int i = 0; i < stateArray.size(); i++) {
                if (gameState.getState().get(i) != stateArray.get(i)) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                return gameState;
            }
            int value = stateArray.get(index);
            stateArray.set(index, 0);
            if (value <= 0) {
                throw new RuntimeException("Cannot alter an empty Mancala whole");
            }

            while (value > 0) {
                index = (index + 1) % stateArray.size();
                if (currGame.getActivePlayer() == 1 && index == 6) {
                    currGame.setPlayer1Score(currGame.getPlayer1Score() + 1);
                    value--;
                } else if (currGame.getActivePlayer() == 2 && index == 0) {
                    currGame.setPlayer2Score(currGame.getPlayer2Score() + 1);
                    value--;
                }

                stateArray.set(index, currGame.getState().get(index) + 1);
                value--;
            }
            currGame.setState(stateArray);

            if (currGame.getActivePlayer() == 1) {
                currGame.setActivePlayer(2);
            } else {
                currGame.setActivePlayer(1);
            }

            setGameState(currGame);
            return currGame;
        } catch (Exception ex) {
            String errorMessage = String.format("Unable to upload game state. Caught a %s. %s.", ex.getClass().getCanonicalName(), ex.getMessage());
            throw new RuntimeException(errorMessage,ex);
        }
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
