package ch.uzh.ifi.hase.soprafs23;

import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class Application {
    @Autowired
    private ShipPlayerService shipPlayerService;
    @Autowired
    private GameService gameService;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String helloWorld() {
    return "The application is running.";
  }

    @GetMapping(value = "/test/{playerId}/{shipId}/{start}/{end}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ShipPlayer test( @PathVariable long playerId, @PathVariable long shipId, @PathVariable String start, @PathVariable String end) {
      return shipPlayerService.placeShip(playerId, shipId, start, end);
    }

    @GetMapping(value = "/test1/{attackerId}/{defenderId}/{posOfShot}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Shot testattack(@PathVariable long attackerId, @PathVariable long defenderId, @PathVariable String posOfShot) {
        System.out.println("testattck controller");
        return gameService.attack(attackerId, defenderId, posOfShot);
    }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
      }
    };
  }
}
