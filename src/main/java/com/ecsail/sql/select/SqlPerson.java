package com.ecsail.sql.select;

import com.ecsail.BaseApplication;
import com.ecsail.views.dialogues.Dialogue_ErrorSQL;
import com.ecsail.dto.MembershipDTO;
import com.ecsail.dto.PersonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SqlPerson {

    /**
     *
     * @param ms_id each membership has an ID that never changes
     * @return ObservableList<PersonDTO>
     */
    public static ObservableList<PersonDTO> getPeople(int ms_id) {
        String query = "SELECT * FROM person WHERE ms_id=" + ms_id;
        ObservableList<PersonDTO> thesePeople = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesePeople.add(new PersonDTO(
                    rs.getInt("p_id"),
                    rs.getInt("MS_ID"),
                    rs.getInt("member_type"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("birthday"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUSINESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLD_MSID")
            ));
            }
        }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesePeople;
    }

    /**
     * Gets an arraylist of PersonDTO objects
     *
     * @param m a MembershipDTO object
     * @return arraylist of PersonDTO objects
     */
    public static ArrayList<PersonDTO> getDependants(MembershipDTO m) {
        String query = "SELECT * FROM person WHERE ms_id= '" + m.getMsId() + "' and member_type=3";
        ArrayList<PersonDTO> thesepeople = new ArrayList<>();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
        while (rs.next()) {
            if(rs.getBoolean("IS_ACTIVE")) {  // only add active people
            thesepeople.add(new PersonDTO(
                    rs.getInt("p_id"),
                    rs.getInt("MS_ID"),
                    rs.getInt("member_type"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("birthday"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUSINESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLD_MSID")));
            }
        }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesepeople;
    }

    /**
     *
     * @return observable list of PersonDTO objects
     */
    public static ObservableList<PersonDTO> getPeople() {
        String query = "SELECT * FROM person";
        ObservableList<PersonDTO> thesePeople = FXCollections.observableArrayList();
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
        while (rs.next()) {
            thesePeople.add(new PersonDTO(
                    rs.getInt("p_id"),
                    rs.getInt("MS_ID"),
                    rs.getInt("member_type"),
                    rs.getString("F_NAME"),
                    rs.getString("L_NAME"),
                    rs.getString("birthday"),
                    rs.getString("OCCUPATION"),
                    rs.getString("BUSINESS"),
                    rs.getBoolean("IS_ACTIVE"),
                    rs.getString("NICK_NAME"),
                    rs.getInt("OLD_MSID")));
        }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return thesePeople;
    }

    /**
     * Returns a PersonDTO using a pid
     *
     * @param pid Each person has a unique primary key known as pid
     * @return PersonDTO
     */
    public static PersonDTO getPersonByPid(int pid) {
        PersonDTO person = null;
        String query = "SELECT * FROM person WHERE p_id=" + pid;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("p_id"), rs.getInt("MS_ID"), rs.getInt("member_type"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("birthday"),
                        rs.getString("OCCUPATION"), rs.getString("BUSINESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLD_MSID")));
            } else {
                System.out.println("There were no results for SqlPerson.getPersonByPid(int pid)");
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     * Returns a PersonDTO using a ms_id and member_type as parameters
     *
     * @param ms_id each membership has an ID that never changes
     * @param member_type denotes the type of member, 1 for primary, 2 for secondary, 3 for dependent
     * @return returns a PersonDTO object
     */
    public static PersonDTO getPerson(int ms_id, int member_type) {
        PersonDTO person = null;
        String query = "SELECT * FROM person where MS_ID=" + ms_id + " and member_type=" + member_type;
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("p_id"), rs.getInt("MS_ID"), rs.getInt("member_type"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("birthday"),
                        rs.getString("OCCUPATION"), rs.getString("BUSINESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLD_MSID")));
            } else {
                System.out.println("There were no results for getPerson(int ms_id, int member_type)");
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     * This method first gets the MS_ID from a MEMBERSHIP_ID tuple selected by ID and Year.
     * It then selects all people associated with that MS_ID and picks the primary member
     *
     * @param membershipId The number for a membership that changes every few years
     * @param year The year we want to select from
     * @return PersonDTO for the primary member of a selected membership
     */
    public static PersonDTO getPersonFromMembershipID(String membershipId, String year) {
        PersonDTO person = null;
        String query = "SELECT * FROM person where MS_ID=(SELECT ms_id FROM membership_id where MEMBERSHIP_ID="+membershipId+" and fiscal_year="+year+") and member_type=1";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            if(rs.next()) {
                person = (new PersonDTO(rs.getInt("p_id"), rs.getInt("MS_ID"), rs.getInt("member_type"),
                        rs.getString("F_NAME"), rs.getString("L_NAME"), rs.getString("birthday"),
                        rs.getString("OCCUPATION"), rs.getString("BUSINESS"), rs.getBoolean("IS_ACTIVE"),rs.getString("NICK_NAME"),rs.getInt("OLD_MSID")));
            } else {
                System.out.println("There were no results for getPersonFromMembershipID(String membershipId, String year)");
            }
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return person;
    }

    /**
     *
     * @param person a PersonDTO
     * @return an integer representing the age of the person in the personDTO
     */
    public static int getPersonAge(PersonDTO person)  {
        int age = 0;
        String query = "SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(NOW(),(SELECT birthday FROM person where p_id=" + person.getP_id() + "))), '%Y')+0 AS AGE;";
        try {
            ResultSet rs = BaseApplication.connect.executeSelectQuery(query);
            rs.next();
            age = rs.getInt("AGE");
            BaseApplication.connect.closeResultSet(rs);
        } catch (SQLException e) {
            new Dialogue_ErrorSQL(e,"Unable to retrieve information","See below for details");
        }
        return age;
    }
}
