package com.example.demo.service;

import com.example.demo.persistance.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.player.impl.PlayerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    private static final Long TEST_PLAYER1_ID = 1L;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @Test
    public void testCreatePlayerWithoutName() {
        Player player = new Player();
        player.setId(TEST_PLAYER1_ID);

        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player result = playerService.createPlayerWithoutName();
        assertNotNull(result);
        assertEquals(result.getId(),TEST_PLAYER1_ID);
    }


    @Test
    public void testGetAllPlayers() {
        Player player1 = new Player();
        Player player2 = new Player();

        when(playerRepository.findAll()).thenReturn(List.of(player1,player2));

        List<Player> result = playerService.getAllPlayers();
        assertEquals(result.size(),2);
    }


    @Test
    public void testGetAllPlayers_whenThereIsNoPlayer() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Player> result = playerService.getAllPlayers();
        assertTrue((result.isEmpty()));
    }

}
