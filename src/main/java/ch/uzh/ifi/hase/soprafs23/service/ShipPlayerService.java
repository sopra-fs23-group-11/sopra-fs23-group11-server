package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.repository.ShipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Transactional
public class ShipPlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ShipPlayerRepository shipPlayerRepository;
    private final ShipRepository shipRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public ShipPlayerService(ShipPlayerRepository shipPlayerRepository, ShipRepository shipRepository, PlayerRepository playerRepository) {
        this.shipPlayerRepository = shipPlayerRepository;
        this.shipRepository = shipRepository;
        this.playerRepository = playerRepository;
    }

    //ToDo
    // check if position of a shot is between the start and end position
    public boolean isContained(String shoot, String shipStart, String shipEnd) {
        Position shootPos = new Position(shoot);
        Position start = new Position(shipStart);
        Position end = new Position(shipEnd);

        if (shootPos.getX() >= start.getX() && shootPos.getX() <= end.getX() &&
                shootPos.getY() >= start.getY() && shootPos.getY() <= end.getY()) {
            return true;
        }
        else {
            return false;
        }
    }

    //ToDo: add all functions to validate a position first
    public ShipPlayer placeShip(long shipId, long playerId, String startPosition, String endPosition) {
        Optional<Ship> shipOptional = shipRepository.findById(shipId);
        Optional<Player> playerOptional = playerRepository.findById(playerId);
        if (shipOptional.isPresent() && playerOptional.isPresent()) {
            Ship ship = shipOptional.get();
            Player player = playerOptional.get();
            ShipPlayer shipPlayer= new ShipPlayer();
            shipPlayer.setStartPosition(startPosition);
            shipPlayer.setEndPosition(endPosition);
            shipPlayer.setShip(ship);
            shipPlayer.setPlayer(player);
            player.getShipPlayers().add(shipPlayer);
            playerRepository.save(player);
            ShipPlayer shipPlayerToSave = shipPlayerRepository.save(shipPlayer);
            return shipPlayerToSave;
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("one or more entities are missing"));
        }

    }
}
