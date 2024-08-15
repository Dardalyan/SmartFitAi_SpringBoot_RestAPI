package com.SmartFitAI.service.Contact;

import com.SmartFitAI.model.Contact;
import com.SmartFitAI.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContactServiceImpl implements IContactService {

    private ContactRepository repo;

    @Autowired
    public ContactServiceImpl(ContactRepository repo) {
        this.repo = repo;
    }

    @Override
    public Contact getContactByUID(String uid) {
        return repo.findById(uid).orElse(null);
    }

    @Override
    public List<Contact> getAllContact() {
        return repo.findAll();
    }

    @Override
    public boolean createContact(Contact contact) {
        try{
            Contact newContact = repo.save(contact);
            if(newContact == null)
                return false;
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateContact(Contact contact) {
        try{
            if(getContactByUID(contact.getUid()) != null){
                repo.save(contact);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteContact(Contact contact) {
        try{
            if(getContactByUID(contact.getUid()) != null){
                repo.delete(contact);
                return true;
            }else return false;
        }catch (Exception e){
            return false;
        }
    }
}
