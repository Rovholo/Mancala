package com.example.demo.persistance;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "MANCALA", schema = "PUBLIC")
public class GameState {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PLAYER1_ID")
    private Long player1Id;

    @Column(name = "PLAYER2_ID")
    private Long player2Id;

    @Column(name = "PLAYER1_SCORE")
    private int player1Score;

    @Column(name = "PLAYER2_SCORE")
    private int player2Score;

    @Column(name = "STATE")
    private List<Integer> state;

    public GameState() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Long player1Id) {
        this.player1Id = player1Id;
    }

    public Long getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Long player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }

    public List<Integer> getState() {
        return state;
    }

    public void setState(List<Integer> state) {
        this.state = state;
    }
}
