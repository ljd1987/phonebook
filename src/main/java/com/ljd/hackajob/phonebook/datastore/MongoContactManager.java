package com.ljd.hackajob.phonebook.datastore;

import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;

/**
 *
 * @author leodavison
 *
 */
class MongoContactManager implements ContactManager {

    private final Datastore datastore;

    public MongoContactManager(final Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public Contact createContact(Contact contactCreateRequest) {
        Contact newContact = new Contact.ContactBuilder(contactCreateRequest)
                .withId(UUID.randomUUID())
                .build();

        datastore.save(newContact);

        return newContact;
    }

    @Override
    public Contact getContact(UUID id) throws ContactNotFoundException {
        Contact contact = datastore.get(Contact.class, id);
        if (contact == null) {
            throw new ContactNotFoundException(id);
        }
        return contact;
    }

    @Override
    public Page<Contact> getContacts(int offset, int limit) {
        Query<Contact> q = datastore.createQuery(Contact.class);

        FindOptions findOptions = new FindOptions()
                .skip(offset)
                .limit(limit);

        List<Contact> contacts = q.asList(findOptions);
        return new Page<>(contacts, q.count());
    }

    @Override
    public Contact updateContact(UUID id, Contact contact) throws ContactNotFoundException {
        // this will check the we have a contact with the given id
        Contact current = getContact(id);

        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();

        // we will apply valid updates to the current model
        current.setFirstName(firstName);
        current.setLastName(lastName);

        UpdateOperations<Contact> updateOps = datastore.createUpdateOperations(Contact.class);
        updateOps.set(Contact.FIELD_FIRST_NAME, firstName);
        
        if (lastName != null) {
            updateOps.set(Contact.FIELD_LAST_NAME, lastName);
        } else {
            updateOps.unset(Contact.FIELD_LAST_NAME);
        }
        
        datastore.update(current, updateOps);
        
        return current;
    }

    @Override
    public void deleteContact(UUID id) throws ContactNotFoundException {
        Contact contactToDelete = getContact(id);
        datastore.delete(contactToDelete);
    }
}
