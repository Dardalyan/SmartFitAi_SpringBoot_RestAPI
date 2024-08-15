package com.SmartFitAI.service.Contact;

import com.SmartFitAI.model.Contact;

import java.util.List;

public interface IContactService {
    public Contact getContactByUID(String uid);
    public List<Contact> getAllContact();
    public boolean createContact(Contact contact);
    public boolean updateContact(Contact contact);
    public boolean deleteContact(Contact contact);


}
