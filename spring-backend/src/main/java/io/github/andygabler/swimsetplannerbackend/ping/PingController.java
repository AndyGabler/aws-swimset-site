package io.github.andygabler.swimsetplannerbackend.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private static final String PING_RESPONSE = "pong";

    @GetMapping("/ping")
    public String ping() {
        return PING_RESPONSE;
    }
}
