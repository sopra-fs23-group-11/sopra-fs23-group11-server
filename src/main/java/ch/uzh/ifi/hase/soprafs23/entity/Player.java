package ch.uzh.ifi.hase.soprafs23.entity;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import javax.persistence.*;

// We may not need all getter & setter. We also may not need to save all the attributes in the DB
//
@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    public Long getId() {
        return id;
    }
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;

    private int shipsRemaining;
    public boolean isAlive;
    @Column
    public int nrTotalWins;
    @OneToMany(mappedBy = "player")
    public Ship[] ships;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getNrTotalWins() {
        return nrTotalWins;
    }

    public void setNrTotalWins(int nrTotalWins) {
        this.nrTotalWins = nrTotalWins;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getShipsRemaining() {
        return shipsRemaining;
    }

    public void setShipsRemaining(int shipsRemaining) {
        this.shipsRemaining = shipsRemaining;
    }


}
