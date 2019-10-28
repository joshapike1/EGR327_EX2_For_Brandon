package hellorestapi.hellorestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class HelloController {

    private static int id = 0;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File("./message.txt"), Greeting.class);
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8.name());
//            Greeting greeting = mapper.readValue(message, Greeting.class);
//            return greeting;
//        } catch (IOException e) {
//            throw new IOException("You FOOL! Why would you think you could get an empty or non existent file??");
//        }
    }

    @RequestMapping(value = "/createGreeting", method = RequestMethod.POST)
    public Greeting createGreeting(@RequestBody String name) throws IOException {
        Greeting newGreeting = new Greeting(id++, name);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("./message.txt"), newGreeting);
        return newGreeting;
//        try {
//            id = 1;
//            ObjectMapper mapper = new ObjectMapper();
//            Greeting greeting = new Greeting(id, name);
//            greeting.setContent(name);
//            mapper.writeValue(new File("./message.txt"), greeting);
//            return greeting;
//        } catch (IOException e) {
//            throw new IOException("Can't create or add to file!");
//        }
    }

    @RequestMapping(value = "/updateGreeting", method = RequestMethod.PUT)
    public Greeting updateGreeting(@RequestBody String newMessage) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8.name());
        Greeting greeting = mapper.readValue(message, Greeting.class);
        greeting.setContent(newMessage);
        mapper.writeValue(new File("./message.txt"), greeting);
        return greeting;
    }

    @RequestMapping(value = "/deleteGreeting", method = RequestMethod.DELETE)
    public void deleteGreeting(@RequestBody int id) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String message = FileUtils.readFileToString(new File("./message.txt"), StandardCharsets.UTF_8.name());
            Greeting greeting = mapper.readValue(message, Greeting.class);
            if (greeting.getId() == id) {
                FileUtils.writeStringToFile(new File("./message.txt"), "", StandardCharsets.UTF_8.name());
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @RequestMapping("/greetingByName")
    public String greetingByName(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @RequestMapping(value = "/getHighestGreeting", method = RequestMethod.POST)
    public Greeting getHighestGreeting(@RequestBody List<Greeting> list) {
        int index = 0;
        long highestGreeting = list.get(0).getId();
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getId() > highestGreeting) {
                index = i;
                highestGreeting = list.get(i).getId();
            }
        }
        return list.get(index);
    }
}