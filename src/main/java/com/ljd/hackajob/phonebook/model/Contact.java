package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.Utils.safeEquals;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 *
 * @author leodavison
 *
 */
@Entity(value=Contact.COLLECTION)
public class Contact {
    public static final String COLLECTION = "contacts";
    
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";

    @Id
    private UUID id;

    @NotNull
    private String firstName;
    private String lastName;

    private Contact() {
        // required for deserialisation
        firstName = null; //NOSONAR
    }

    private Contact(ContactBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static class ContactBuilder {
        private UUID id;
        private final String firstName;
        private String lastName;

        public ContactBuilder(String firstName) {
            this.firstName = firstName;
        }
        
        public ContactBuilder(Contact other) {
            this.firstName = other.firstName;
            this.lastName = other.lastName;
        }

        public ContactBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public ContactBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
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

        if (!safeEquals(id, other.id)) {
            return false;
        }
        
        if (!safeEquals(firstName, other.firstName)) {
            return false;
        }
       
        return safeEquals(lastName, other.lastName);     
    }
}
