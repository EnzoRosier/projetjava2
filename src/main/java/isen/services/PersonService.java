package isen.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import isen.App;
import isen.db.daos.DataSourceFactory;
import isen.db.entities.Person;

public class PersonService {

    public static List<Person> get_list_person() {
        ArrayList<Person> listPerson = new ArrayList<Person>();
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person")) {
                try (ResultSet res = statement.executeQuery()) {
                    while (res.next()) {
                        String firstname = res.getString("firstname");
                        String lastname = res.getString("lastname");
                        String nickname = res.getString("nickname");
                        String phoneNumber = res.getString("phone_number");
                        String address = res.getString("address");
                        String emailAddress = res.getString("email_address");
                        LocalDate birthDate = res.getDate("birth_date").toLocalDate();
                        Integer id = res.getInt("idperson");

                        Person personne = new Person(firstname, lastname, nickname, phoneNumber, address,
                                emailAddress, birthDate, id);
                        listPerson.add(personne);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listPerson;
    }

    public static Person add_person(Person newPerson) {
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, newPerson.getLastName());
                statement.setString(2, newPerson.getFirstName());
                statement.setString(3, newPerson.getNickname());
                statement.setString(4, newPerson.getPhoneNumber());
                statement.setString(5, newPerson.getAddress());
                statement.setString(6, newPerson.getEmailAddress());
                statement.setDate(7, Date.valueOf(newPerson.getBirthDate()));
                statement.executeUpdate();
                ResultSet ids = statement.getGeneratedKeys();
				if (ids.next()) {
					newPerson.setId(ids.getInt(1));
					return newPerson;
				}
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
