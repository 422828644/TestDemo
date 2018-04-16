package org.linsoho.demo.controller;

import java.util.List;

import org.linsoho.demo.entity.Person;
import org.linsoho.demo.model.DbRepository;

public class PersonController {

    private static DbRepository db = new DbRepository();
    
    public static List<Person> getPersonList() {
        return db.selectAll();
    }
}
