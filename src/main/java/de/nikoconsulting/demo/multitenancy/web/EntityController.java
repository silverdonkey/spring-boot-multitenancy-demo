package de.nikoconsulting.demo.multitenancy.web;

import de.nikoconsulting.demo.multitenancy.repositories.GenericEntityRepository;
import de.nikoconsulting.demo.multitenancy.model.GenericEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class EntityController {

    @Autowired
    GenericEntityRepository entityRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello World!";
    }

    @RequestMapping(value = "/entity/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<List<GenericEntity>> findAll() {
        try {
        List<GenericEntity> entityList = new ArrayList<>();

        entityRepository.findAll().forEach(entityList::add);

        if (entityList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(entityList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/entity", method = RequestMethod.POST)
    public ResponseEntity<GenericEntity> addEntity(GenericEntity entity) {
        try {
            GenericEntity genericEntity = entityRepository.save(entity);
            return new ResponseEntity<>(genericEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/entity/{id}")
    public ResponseEntity<GenericEntity> findById(@PathVariable Long id) {

        Optional<GenericEntity> entityData = entityRepository.findById(id);

//        if (entityData.isPresent()) {
//            return  new ResponseEntity<>(entityData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return entityData.map(genericEntity -> new ResponseEntity<>(genericEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
