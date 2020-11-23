package com.system27.mongo.Controller;

import com.system27.mongo.Model.Person;
import com.system27.mongo.Service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAll(){
        return personService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person person){
         personService.add(person);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updatePerson(@RequestBody Person person){
         personService.update(person);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deletePerson(@RequestParam("id") String id){
         personService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/page")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAllPersonByPage(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                            @RequestParam(value = "pageSize", defaultValue = "1") int pageSize,
                                            @RequestParam(value = "sortBy", defaultValue = "name,salary") List<String> sortBy){
        return personService.findAllPersonByPage(pageNo, pageSize, sortBy);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/avgSalary")
    @ResponseStatus(HttpStatus.OK)
    public double findAvgSalary(){
        return personService.avgSalary();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/likes")
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findByLikes(@RequestParam(value = "likes", defaultValue = "Strawberry,Chocolate") String[] likes){
        return personService.findByLikes(likes);
    }
}
