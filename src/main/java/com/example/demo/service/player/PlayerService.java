package com.example.demo.service.player;

import com.example.demo.persistance.Player;

import java.util.List;

public interface PlayerService {

    List<Player> getAllPlayers();

    Player createPlayerWithoutName();

}
