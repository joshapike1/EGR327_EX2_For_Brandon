package hellorestapi.hellorestapi;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class ScheduledTasks {
    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "*/1 * * * * *")
    public void getGreeting() {
        String url = "http://localhost:8080/greeting";
        Greeting g = restTemplate.getForObject(url, Greeting.class);
        System.out.println(g.getContent());
    }

    @Scheduled(cron = "*/3 * * * * *")
    public void putGreeting() {
        String url = "http://localhost:8080/updateGreeting";
        restTemplate.put(url, "Hello World", Greeting.class);
    }

    @Scheduled(cron = "*/4 * * * * *")
    public void postGreeting() {
        String url = "http://localhost:8080/createGreeting";
        Greeting g = restTemplate.postForObject(url, "Bye World", Greeting.class);
        System.out.println(g.getContent());
    }
}