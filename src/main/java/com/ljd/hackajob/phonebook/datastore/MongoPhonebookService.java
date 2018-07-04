package com.ljd.hackajob.phonebook.datastore;

import org.mongodb.morphia.Datastore;

class MongoPhonebookService implements PhonebookService {
    private final ContactManager contactManager;
    
    protected MongoPhonebookService(Datastore datastore) {
        this.contactManager = new MongoContactManager(datastore);
    }

    @Override
    public ContactManager getContactManager() {
        return contactManager;
    }       
}
