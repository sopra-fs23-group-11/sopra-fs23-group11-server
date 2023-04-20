package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.LobbyPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LobbyController {
    private final LobbyService lobbyService;


    LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    //Create lobby
    @PostMapping("/host")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public LobbyGetDTO host(@RequestBody LobbyPostDTO lobbyPostDTO) {
        Lobby createdLobby= lobbyService.createLobby(lobbyPostDTO.getHostId());
        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }
    @PutMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public LobbyGetDTO join(@RequestBody LobbyPutDTO lobbyPutDTO){
        System.out.println("controller");
        Lobby createdLobby=lobbyService.joinLobby(lobbyPutDTO.getLobbyCode(), lobbyPutDTO.getJoinerId());

        return DTOMapper.INSTANCE.convertEntityToLobbyGetDTO(createdLobby);
    }

}
