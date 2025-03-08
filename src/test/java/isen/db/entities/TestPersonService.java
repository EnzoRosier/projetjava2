package isen.db.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import isen.db.daos.DataSourceFactory;
import isen.services.PersonService;

public class TestPersonService {

        @BeforeEach
        public void init() throws SQLException {
                DataSourceFactory.setConnectionUrl("jdbc:sqlite:sqlite.db");
                Connection connection = DataSourceFactory.getConnection();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(
                                "CREATE TABLE IF NOT EXISTS person (\r\n" + //
                                                "    idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \r\n" + //
                                                "    lastname VARCHAR(45) NOT NULL,  \r\n" + //
                                                "    firstname VARCHAR(45) NOT NULL,\r\n" + //
                                                "    nickname VARCHAR(45) NOT NULL,\r\n" + //
                                                "    phone_number VARCHAR(15) NULL,\r\n" + //
                                                "    address VARCHAR(200) NULL,\r\n" + //
                                                "    email_address VARCHAR(150) NULL,\r\n" + //
                                                "    birth_date DATE NULL);");

                stmt.executeUpdate("DELETE FROM person");
                stmt.executeUpdate(
                                "INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                                                + "VALUES (1, 'Bib', 'Bob', 'bobibob', '0612121212', 'Rue yahoo', 'EZEZ@2EZE.com', '2015-12-12 12:00:00.000')");
                stmt.executeUpdate(
                                "INSERT INTO person(idperson,lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
                                                + "VALUES (2, 'Bab', 'Bub', 'bobibob', '0612121212', 'Rue yahoo', 'EZEZ@2EZE.com', '2015-12-12 12:00:00.000')");

                stmt.close();
                connection.close();
        }

        @Test
        public void testGetListPerson() {
                List<Person> list_person = PersonService.getListPerson();
                assertThat(list_person).hasSize(2);
                assertThat(list_person)
                                .extracting("lastName", "FirstName", "nickname", "phoneNumber", "address",
                                                "emailAddress",
                                                "birthDate")
                                .contains(
                                                tuple("Bib", "Bob", "bobibob", "0612121212", "Rue yahoo",
                                                                "EZEZ@2EZE.com",
                                                                LocalDate.of(2015, 12, 12)),
                                                tuple("Bab", "Bub", "bobibob", "0612121212", "Rue yahoo",
                                                                "EZEZ@2EZE.com",
                                                                LocalDate.of(2015, 12, 12)));
        }

        @Test
        public void testAddPerson() {
                Person newPerson = new Person("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                LocalDate.of(1987, 1, 20));
                Person addedPerson = PersonService.addPerson(newPerson);
                List<Person> list_person = PersonService.getListPerson();
                assertThat(list_person)
                    .extracting("lastName", "firstName", "nickname", "phoneNumber", "address", "emailAddress", "birthDate")
                    .contains(tuple("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                    LocalDate.of(1987, 1, 20)));
                
                assertThat(addedPerson).usingRecursiveComparison().ignoringFields("id").isEqualTo(newPerson);
        }

        @Test
        public void testEditPerson() {
                Person newPerson = new Person("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                LocalDate.of(1987, 1, 20), 1);
                Person editedPerson = PersonService.editPerson(newPerson);
                assertThat(editedPerson).isNotNull();
                assertThat(editedPerson).usingRecursiveComparison().ignoringFields("id").isEqualTo(newPerson);
                
                //Vérification que newPerson est dans la base de donnée
                List<Person> list_person = PersonService.getListPerson();
                assertThat(list_person)
                    .extracting("lastName", "firstName", "nickname", "phoneNumber", "address", "emailAddress", "birthDate")
                    .contains(tuple("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                    LocalDate.of(1987, 1, 20)));
        }

        @Test
        public void testDeletePerson() {
                Person newPerson = new Person("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                LocalDate.of(1987, 1, 20), 1);
                Person addedPerson = PersonService.addPerson(newPerson);
                List<Person> list_person1 = PersonService.getListPerson();
                //Vérification que la personne est ajoutée
                assertThat(list_person1).extracting("lastName","firstName","nickname","phoneNumber","address","emailAddress","birthDate").contains(tuple("Super", "Mario", "64", "6464646464", "Chato Champi", "yahoo@gmail.gouv",
                                LocalDate.of(1987, 1, 20)));
                PersonService.deletePerson(newPerson);
                List<Person> list_person2 = PersonService.getListPerson();
                //vérification que la personne est supprimée
                assertThat(list_person2).doesNotContain(newPerson);
        }
}