/*
 * Copyright (c) 2012 Noah McCarn
 */
package edu.elon.contact;

import java.io.Serializable;

/**
 * 
 * Class that represents and holds all data for an individual contact Contains
 * simple getters and setters and equals method
 * 
 * @author Noah
 * @version 1.0
 * 
 */
public class Contact implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String major;
    private String middleName;

    public Contact() {
        firstName = "";
        middleName = "";
        lastName = "";
        email = "";
        major = "";
    }

    public Contact(String aFirstName, String aMiddleName,
                                    String aLastName, String aEmail,
                                    String aMajor) {
        firstName = aFirstName;
        middleName = aMiddleName;
        lastName = aLastName;
        email = aEmail;
        major = aMajor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Contact other = (Contact) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (major == null) {
            if (other.major != null) {
                return false;
            }
        } else if (!major.equals(other.major)) {
            return false;
        }
        if (middleName == null) {
            if (other.middleName != null) {
                return false;
            }
        } else if (!middleName.equals(other.middleName)) {
            return false;
        }
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMajor() {
        return major;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setEmail(String aEmail) {
        email = aEmail;
    }

    public void setFirstName(String aFirstName) {
        firstName = aFirstName;
    }

    public void setLastName(String aLastName) {
        lastName = aLastName;
    }

    public void setMajor(String aMajor) {
        major = aMajor;
    }

    public void setMiddleName(String aMiddleName) {
        middleName = aMiddleName;
    }

    @Override
    public String toString() {
        return firstName + "\n" + middleName + "\n" + lastName + "\n"
                                        + email + "\n" + major;
    }
}
