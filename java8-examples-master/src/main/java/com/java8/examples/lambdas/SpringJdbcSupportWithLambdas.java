package com.java8.examples.lambdas;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.java8.examples.Person;

/**
 * A class that uses spring JdbcSupport and implements {@link RowMapper RowMappers} using lambda expressions.
 */
public class SpringJdbcSupportWithLambdas extends JdbcDaoSupport {

    public Person findPersonById(String id) {
        return getJdbcTemplate().queryForObject(
                // not secured against SQL injections, don't do this in production code!
                "SELECT * FROM persons WHERE id = " + id,
                new RowMapper<Person>() {
                    @Override
                    public Person mapRow(ResultSet rs, int i) throws SQLException {
                        return new Person(rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getDate(3), Person.Gender.MALE);
                    }
                });
    }

    public Person findPersonByIdWithLambdas(String id) {
        return getJdbcTemplate().queryForObject(
                "SELECT * FROM persons WHERE id = " + id,
                (rs, i) -> new Person(rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getDate(3), Person.Gender.MALE));
    }

    // if things get messy, use a method reference
    // overall this is more verbose but maybe better for readability?
    public Person findPersonByIfWithMethodReference(String id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM persons WHERE id = " + id, this::mapPerson);
    }

    private Person mapPerson(ResultSet rs, int i) throws SQLException {
        return new Person(
                rs.getString("FIRST_NAME"),
                rs.getString("LAST_NAME"),
                rs.getDate(3),
                Person.Gender.MALE);
    }
}
