package com.example.demo.service;

import com.example.demo.persistance.GameState;
import com.example.demo.persistance.Player;
import com.example.demo.repository.GameRepository;
import com.example.demo.service.game.impl.GameServiceImpl;
import com.example.demo.service.player.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    private static final Long TEST_GAME_ID = 123L;
    private static final Long TEST_PLAYER1_ID = 1L;
    private static final Long TEST_PLAYER2_ID = 2L;
    private static final List<Integer> TEST_GAME_STATE = List.of(6,5,6,6,0,6,6,6,6,6,6,6);

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void testInitGame_whenCreatingNewGame_withPlayerID() {
        lenient().when(gameRepository.findById(anyLong())).thenReturn(null);

        GameState result = gameService.initialiseGame(null,TEST_PLAYER1_ID);

        assertNotNull(result);
        assertEquals(result.getPlayer1Id(),TEST_PLAYER1_ID);
    }

    @Test
    public void testInitGame_whenCreatingNewGame_withoutPlayerID() {
        Player player = new Player();
        player.setId(TEST_PLAYER1_ID);

        lenient().when(gameRepository.findById(anyLong())).thenReturn(null);
        when(playerService.createPlayerWithoutName()).thenReturn(player);

        GameState result = gameService.initialiseGame(null,null);

        assertNotNull(result);
        assertEquals(result.getPlayer1Id(),TEST_PLAYER1_ID);
    }

    @Test
    public void testInitGame_whenGameJoiningGame() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(buildNewGameState(TEST_PLAYER1_ID)));

        GameState result = gameService.initialiseGame(TEST_GAME_ID,TEST_PLAYER2_ID);

        assertNotNull(result);
    }

    @Test
    public void testUpdateGame_whenGameJoiningGame() {

        GameState result = gameService.updateState(buildNewGameState(TEST_PLAYER1_ID));

        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    public void testGetGameState() {
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(buidGetGameState()));

        GameState result = gameService.getGameState(TEST_GAME_ID);

        assertEquals(result.getId(), TEST_GAME_ID);
        assertEquals(result.getState(), TEST_GAME_STATE );

    }

    @Test
    public void testGetGameState_whenResultIsNull() {
        lenient().when(gameRepository.findById(anyLong())).thenReturn(null);

        GameState result = gameService.getGameState(TEST_GAME_ID);

        assertNull(result);

    }


    private GameState buidGetGameState() {
        GameState gameState = new GameState();
        gameState.setId(TEST_GAME_ID);
        gameState.setPlayer1Id(TEST_PLAYER1_ID);
        gameState.setPlayer2Id(TEST_PLAYER2_ID);
        gameState.setPlayer1Score(6);
        gameState.setPlayer2Score(1);
        gameState.setState(TEST_GAME_STATE);
        return gameState;
    }

    private GameState buildNewGameState(Long playerId) {
        GameState gameState = new GameState();
        gameState.setPlayer1Id(TEST_PLAYER1_ID);
        gameState.setPlayer1Score(0);
        gameState.setPlayer2Score(0);
        gameState.setState(TEST_GAME_STATE);
        return gameState;
    }
}
