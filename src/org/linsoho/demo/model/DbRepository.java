package org.linsoho.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.linsoho.demo.entity.Person;

/**
 *数据仓库
 */
public class DbRepository {
    
    private static List<Person> personList;
    
    public DbRepository() {
        personList = new ArrayList<Person>();
        // 初始化数据
        personList.add(new Person(true, "Jacob", "Smith", "jacob.smith@example.com"));
        personList.add(new Person(false, "Isabella", "Johnson", "isabella.johnson@example.com"));
        personList.add(new Person(true, "Ethan", "Williams", "ethan.williams@example.com"));
        personList.add(new Person(true, "Emma", "Jones", "Emma.Jones@example.com"));
        personList.add(new Person(false, "Aaron", "Brown", "Aaron.Brown@example.com"));
        personList.add(new Person(true, "Abbe", "Smith", "Abbe.Smith@example.com"));
        personList.add(new Person(false, "Abelard", "Johnson", "Abelard.Johnson@example.com"));
        personList.add(new Person(true, "Ethan", "Williams", "Ethan.Williams@example.com"));
        personList.add(new Person(true, "Marvin", "Pigou", "Marvin.Pigou@example.com"));
        personList.add(new Person(true, "Andrew", "Valentine", "Andrew.Valentine@example.com"));
        personList.add(new Person(true, "Ursula", "Philip", "Ursula.Philip@example.com"));
    }
    
    public List<Person> selectAll() {
        return personList;
    }
    
    public List<Person> deleteOne(String name) {
        // 删除指定数据
        return null;
    }
}
