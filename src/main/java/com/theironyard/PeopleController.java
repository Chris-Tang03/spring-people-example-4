package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PeopleController {

    @Autowired
    PeopleRepository peopleRepository;

    @GetMapping ("/")
    public String listPeople(Model model, @RequestParam (defaultValue = "") String search){
        model.addAttribute("search", search);
        model.addAttribute("people", peopleRepository.listPeople(search));
        return "index";
    }

    @GetMapping ("/personForm")
    public String personForm (Model model, Integer personId){
        if (personId == null){
            model.addAttribute("person", new Person());
        } else {
            model.addAttribute("person", peopleRepository.getPersonId(personId));
        }
        return "personForm";
    }

    @PostMapping ("/savePerson")
    public String savePerson(Person person){
        peopleRepository.savePerson(person);
        return "redirect:/";
    }
}
