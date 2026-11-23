package esprit.canditature;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/candidature")
public class CandidatureRestApi {

    @Value("${server.port}")
    private String instancePort;

    @GetMapping ("/hello")
    public String sayHello() {

        return "Hello Im microservice 3!";
    }

    @GetMapping("/instance")
    public String instance() {
        return "CANDITATURE instance on port " + instancePort;
    }
}
