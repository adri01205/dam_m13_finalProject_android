package com.m13.dam.dam_m13_finalproject_android.model.pojo;

/**
 * Created by adri on 04/05/2016.
 */
/**
 * Modified by jesus on 10/05/2016
 *
 * Added 'password' Field
 *
 * Removed 'id' Field -> Now 'nif' is old 'id'
 */
public class Employee {
    String nif;
    String name;
    String surname;
    String phone;
    String email;
    String adress;
    String username;
    String password;

    public Employee() {
    }

    /*  Modified by Jesus on 10/05/2016
    public Employee(int id, String nif, String name, String surname, String phone, String email, String adress, String username, String password) {
        this.id = id
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adress = adress;
        this.username = username;
        this.password = password;
    }
    */

    public Employee(String nif, String name, String surname, String phone, String email, String adress, String username, String password) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.adress = adress;
        this.username = username;
        this.password = password;
    }

    /* Modified by Jesus on 10/05/2016
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    */

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
             /*   Modified by Jesus on 10/05/2016
             "id=" + id +
             */
                ", nif='" + nif + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", adress='" + adress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}