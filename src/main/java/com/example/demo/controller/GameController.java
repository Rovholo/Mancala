package com.example.demo.controller;

import com.example.demo.persistance.GameState;
import com.example.demo.persistance.Player;
import com.example.demo.service.game.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/initialise")
    public GameState initialiseGame(@RequestParam(value = "gameId") Long gameId,
                                    @RequestParam(value = "playerId") Long playerId) {
        return gameService.initialiseGame(gameId, playerId);
    }

    @PostMapping("/update")
    public GameState updateGame(@RequestBody GameState gameState) {
        return gameService.updateState(gameState);
    }

    @GetMapping("/state")
    public GameState gameState(@RequestParam(value = "gameId") Long gameId) {
        return gameService.getGameState(gameId);
    }

}
