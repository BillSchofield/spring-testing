package example;

import example.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private final PersonRepository personRepository;

    @Autowired
    public ExampleController(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/hello/{lastName}")
    public String hello(@PathVariable final String lastName) {
        var foundPerson = personRepository.findByLastName(lastName);

        return foundPerson
                .map(person -> String.format("Hello %s %s!", person.getFirstName(), person.getLastName()))
                .orElse(String.format("Who is this '%s' you're talking about?", lastName));
    }

    @GetMapping("/change/")
    public String change(@RequestParam int cents) {
        int numberOfQuarters = 0;
        while (cents >= 25) {
            numberOfQuarters++;
            cents -=25;
        }
        int numberOfPennies = cents;
        int numberOfNickels = 0;
        if (cents >= 5) {
            numberOfNickels = 1;
        }
        int numberOfDimes = 0;
        if (cents >= 10) {
            numberOfDimes = 1;
        }
        return numberOfQuarters + " quarters, " + numberOfDimes + " dimes, " + numberOfNickels + " nickels, " + numberOfPennies + " pennies";
    }
}
