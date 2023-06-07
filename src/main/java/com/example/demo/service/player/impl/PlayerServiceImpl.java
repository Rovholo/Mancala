package com.example.demo.service.player.impl;

import com.example.demo.persistance.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.service.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }


    public Player createPlayerWithoutName() {
        return playerRepository.save(new Player());
    }
}
