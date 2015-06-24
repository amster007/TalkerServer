package hibernate.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author amster
 */

@Entity
@Table( name = "users",
        uniqueConstraints =
        {
            @UniqueConstraint( columnNames =
                    {
                        "ID"
            } )
        } )
public class User
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, length=11)
    Integer id;
    @Column( name = "firstName", length = 30, nullable = true )
    String firstName;
    @Column( name = "lastName", length = 30, nullable = true )
    String lastName;
    //String displayedName;
    @Column( name = "password", length = 255, nullable = false )
    String password;
    @Column( name = "salt", length = 255, nullable = false )
    String salt;
    @Column( name = "email", length = 30, nullable = false, unique = true )
    String email;
    @Column( name = "age", length = 11, nullable = true )
    Integer age;
    @Column( name = "birthDate", nullable = true )
    Date birthDate;
    @Column( name = "talkerNumber", length = 11, nullable = false )
    Integer talkerNumber;
    @Column( name = "friends_list", nullable = true )
    String friends_list;
    @Column( name = "status", length = 11, nullable = true )
    Integer status;
    @Column( name = "created", nullable = true )
    Date created;
    @Column( name = "last_login", nullable = true )
    Date last_login;
    @Column( name = "last_modified", nullable = true )
    Date last_modified;
    @Column( name = "active", nullable = true )
    Boolean active;
    @Column( name = "phoneNumber", length = 11, nullable = true )
    Long phoneNumber;

}
