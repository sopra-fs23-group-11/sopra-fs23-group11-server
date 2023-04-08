package ch.uzh.ifi.hase.soprafs23.entity.ships;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int length;
    @Column(nullable = false, unique = true)
    private String type;

    @OneToMany(mappedBy = "ship")
    private List<ShipsPlayer> shipPlayers;

    //private Position[] position;
    public Ship(String type, int length) {
        this.type = type;
        this.length=length;
    }
    /*
    private boolean isSunk;

    @ManyToOne
    public Player player;


    public boolean isSunk() {
        return isSunk;
    }

    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    } */

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<ShipsPlayer> getShipPlayers() {
        return shipPlayers;
    }

    public void setShipPlayers(List<ShipsPlayer> shipPlayers) {
        this.shipPlayers = shipPlayers;
    }
    /*
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean hit(){
        return false;
    }
    public boolean IsSunk(){
        return false;
    }*/

    public void decrementSize() {this.length -= 1;}

    /*
    public Position[] getPosition() {
        return position;
    }

    public void setPosition(Position[] position) {
        this.position = position;
    }

     */
}
