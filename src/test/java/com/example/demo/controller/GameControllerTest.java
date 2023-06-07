package com.example.demo.controller;

import com.example.demo.TestUtility;
import com.example.demo.persistance.GameState;
import com.example.demo.persistance.Player;
import com.example.demo.service.game.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    private static final Long TEST_GAME_ID = 123L;
    private static final Long TEST_PLAYER1_ID = 1L;
    private static final Long TEST_PLAYER2_ID = 2L;
    private static final List<Integer> TEST_GAME_STATE = List.of(6,5,6,6,0,6,6,6,6,6,6,6);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GameService gameService;

    @Test
    public void testInitialiseGame() throws Exception {

        when(gameService.initialiseGame(anyLong(),anyLong())).thenReturn(buidGetGameState());

        MvcResult mvcResult = this.mvc.perform(get("/api/game/initialise")
                        .param("gameId", String.valueOf(TEST_GAME_ID))
                        .param("playerId", String.valueOf(TEST_PLAYER1_ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(gameService, times(1)).initialiseGame(anyLong(),anyLong());

        String jsonStringResponse = mvcResult.getResponse().getContentAsString();
        GameState response = TestUtility.jsonStringToObject(jsonStringResponse, GameState.class);

        assertNotNull(response);
        assertEquals(response.getId(),TEST_GAME_ID);
    }

    @Test
    public void testGetGameState() throws Exception {

        when(gameService.getGameState(anyLong())).thenReturn(buidGetGameState());

        MvcResult mvcResult = this.mvc.perform(get("/api/game/state")
                        .param("gameId", String.valueOf(TEST_GAME_ID))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(gameService, times(1)).getGameState(anyLong());

        String jsonStringResponse = mvcResult.getResponse().getContentAsString();
        GameState response = TestUtility.jsonStringToObject(jsonStringResponse, GameState.class);

        assertNotNull(response);
        assertEquals(response.getId(),TEST_GAME_ID);
    }

    @Test
    public void testUpdateGame() throws Exception {

        when(gameService.updateState(any(GameState.class))).thenReturn(buidGetGameState());

        String updateGameRequest =  TestUtility.readFile("request/GameState.json");

        MvcResult mvcResult = this.mvc.perform(post("/api/game/update")
                        .content(updateGameRequest)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(gameService, times(1)).updateState(any(GameState.class));

        String jsonStringResponse = mvcResult.getResponse().getContentAsString();
        GameState response = TestUtility.jsonStringToObject(jsonStringResponse, GameState.class);

        assertNotNull(response);
        assertEquals(response.getId(),TEST_GAME_ID);
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


}
