package ch.uzh.ifi.hase.soprafs23.service;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.repository.LobbyRepository;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;


@Service
public class LobbyService {
    private final Logger log = LoggerFactory.getLogger(LobbyService.class);
    private final LobbyRepository lobbyRepository;
    private final UserRepository userRepository;

    @Autowired
    public LobbyService(LobbyRepository lobbyRepository, UserRepository userRepository) {
        this.lobbyRepository = lobbyRepository;
        this.userRepository = userRepository;
    }

    public Lobby createLobby(long hostId){
        Optional <User> optionalUser= userRepository.findById(hostId);
        if (optionalUser.isPresent()){
            User host= optionalUser.get();
            Lobby newLobby= new Lobby();
            newLobby.setHost(host);
            String lobbyCode;
            do {
                lobbyCode=UUID.randomUUID().toString();
            }while (lobbyRepository.findByLobbyCode(lobbyCode)!= null);
            newLobby.setLobbyCode(lobbyCode);
            Lobby lobby= lobbyRepository.save(newLobby);
            host.setLobbyForHost(lobby);
            userRepository.save(host);
            return lobby;
        }
        else{
            throw new EntityNotFoundExcep("User doesn't exist");
        }

    }

    public Lobby joinLobby(String lobbyCode, long userId) {
        System.out.println("service1");
        Lobby lobby= lobbyRepository.findByLobbyCode(lobbyCode);
        System.out.println("userId = " + userId);
        Optional <User> optionalUser= userRepository.findById(userId);
        if (lobby ==null)
            throw new EntityNotFoundExcep("lobby not found");
        long hostId= lobby.getHost().getId();
        System.out.println("service1.2");
        if (optionalUser.isEmpty())
            throw new EntityNotFoundExcep("joiner not found");
        System.out.println("service1.3");
        User user= optionalUser.get();
        if (hostId== user.getId())
            throw new PlayerExcep("players should differ");

        user.setLobbyForJoiner(lobby);
        User newuser= userRepository.save(user);
        lobby.setJoiner(newuser);
        lobbyRepository.save(lobby);
        System.out.println("service2");
        return lobby;

    }

    public Lobby findByLobbyCode(String lobbyCode){
        return lobbyRepository.findByLobbyCode(lobbyCode);
    }
}
