package com.theironyard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeopleRepository {

    @Autowired
    JdbcTemplate template;

    public List<Person> listPeople(String search){
        return template.query("SELECT * FROM person WHERE " +
                        "LOWER (firstname) LIKE (?) OR " +
                        "LOWER (lastname) LIKE (?) " +
                        "limit 100",
                new Object[]{"%" + search.toLowerCase() + "%", "%" + search.toLowerCase() + "%"},
                (rs, i) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                )
        );
    }

    public Person getPersonId(Integer personId) {
        return template.queryForObject("SELECT * FROM person WHERE " +
                        "personid = ?",
                new Object[]{personId},
                (rs, i) -> new Person(
                        rs.getInt("personid"),
                        rs.getString("title"),
                        rs.getString("firstname"),
                        rs.getString("middlename"),
                        rs.getString("lastname"),
                        rs.getString("suffix")
                )
        );
    }


    public void savePerson(Person person) {
        if (person.getPersonId() == null) {
            template.update("INSERT INTO person (title, firstname, middlename, lastname, suffix) " +
                    "VALUES (?, ?, ?, ?, ?)",
                    new Object[]{person.getTitle(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getSuffix()}
            );
        } else {
            template.update("UPDATE person SET " +
                    "title = ?, " +
                    "firstname = ?, " +
                    "middlename = ?, " +
                    "lastname = ?, " +
                    "suffix = ? " +
                    "WHERE personid = ?",
                    new Object[]{person.getTitle(),
                    person.getFirstName(),
                    person.getMiddleName(),
                    person.getLastName(),
                    person.getSuffix(),
                            person.getPersonId()}
            );
        }
    }
}
