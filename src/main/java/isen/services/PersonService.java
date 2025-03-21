package isen.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import isen.db.daos.DataSourceFactory;
import isen.db.entities.Person;

public class PersonService {

    //getList of all persons
    public static List<Person> getListPerson() {
        ArrayList<Person> listPerson = new ArrayList<Person>();
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person")) {
                try (ResultSet res = statement.executeQuery()) {
                    while (res.next()) {
                        //Create person for each row
                        String firstname = res.getString("firstname");
                        String lastname = res.getString("lastname");
                        String nickname = res.getString("nickname");
                        String phoneNumber = res.getString("phone_number");
                        String address = res.getString("address");
                        String emailAddress = res.getString("email_address");
                        LocalDate birthDate = res.getDate("birth_date").toLocalDate();
                        Integer id = res.getInt("idperson");

                        Person personne = new Person(lastname, firstname, nickname, phoneNumber, address,
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

        return listPerson; //return result
    }

    //get list of persons with filter
    public static List<Person> getListPerson(String filter) {
        ArrayList<Person> listPerson = new ArrayList<Person>();
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE lastname LIKE '%' || ? || '%' OR firstname LIKE '%' || ? || '%'")) {
                statement.setString(1, filter);
                statement.setString(2, filter);
                try (ResultSet res = statement.executeQuery()) {
                    while (res.next()) {
                        //Create Person for each row
                        String firstname = res.getString("firstname");
                        String lastname = res.getString("lastname");
                        String nickname = res.getString("nickname");
                        String phoneNumber = res.getString("phone_number");
                        String address = res.getString("address");
                        String emailAddress = res.getString("email_address");
                        LocalDate birthDate = res.getDate("birth_date").toLocalDate();
                        Integer id = res.getInt("idperson");

                        Person personne = new Person(lastname, firstname, nickname, phoneNumber, address,
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

        return listPerson; //return result
    }

    //Add person to DB
    public static Person addPerson(Person newPerson) {
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO person(lastname, firstname, nickname, phone_number, address, email_address, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                //Set all parameters in statement
                statement.setString(1, newPerson.getLastName());
                statement.setString(2, newPerson.getFirstName());
                statement.setString(3, newPerson.getNickname());
                statement.setString(4, newPerson.getPhoneNumber());
                statement.setString(5, newPerson.getAddress());
                statement.setString(6, newPerson.getEmailAddress());
                statement.setDate(7, Date.valueOf(newPerson.getBirthDate()));
                statement.executeUpdate();
                ResultSet ids = statement.getGeneratedKeys();
                //Get id of the new person
                if (ids.next()) {
                    newPerson.setId(ids.getInt(1));
                    return newPerson; //return res
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //edit person in DB
    public static Person editPerson(Person person) {
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE person SET lastname = ?, firstname = ?, nickname = ?, phone_number = ?, address = ?, email_address = ?, birth_date = ? WHERE idperson = ?")) {
                
                //add parameters to statement
                statement.setString(1, person.getLastName());
                statement.setString(2, person.getFirstName());
                statement.setString(3, person.getNickname());
                statement.setString(4, person.getPhoneNumber());
                statement.setString(5, person.getAddress());
                statement.setString(6, person.getEmailAddress());
                statement.setDate(7, Date.valueOf(person.getBirthDate()));
                statement.setInt(8, person.getId());
                statement.executeUpdate();
                return person; //return res
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return null;
    }

    //remove person in DB
    public static void deletePerson(Person person) {
        try (Connection connection = DataSourceFactory.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE idperson = ?")) {
                statement.setInt(1, person.getId()); //add parameter to statement
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
