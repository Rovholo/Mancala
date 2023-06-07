package com.example.demo.controller;

import com.example.demo.TestUtility;
import com.example.demo.persistance.Player;
import com.example.demo.service.player.PlayerService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    PlayerService playerService;

    @Test
    public void testGetAllPlayers() throws Exception {
        Player player1 = new Player();
        Player player2 = new Player();

        when(playerService.getAllPlayers()).thenReturn(List.of(player1,player2));

        MvcResult mvcResult = this.mvc.perform(get("/api/player/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(playerService, times(1)).getAllPlayers();

        String jsonStringResponse = mvcResult.getResponse().getContentAsString();
        List<Player> response = TestUtility.jsonStringToObject(jsonStringResponse, List.class);

        assertNotNull(response);
        assertEquals(response.size(), 2);
    }

}
