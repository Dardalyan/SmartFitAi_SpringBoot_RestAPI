package com.SmartFitAI.controller.contact;


import com.SmartFitAI.dto.ContactDTO;
import com.SmartFitAI.model.Contact;
import com.SmartFitAI.model.User;
import com.SmartFitAI.service.Contact.IContactService;
import com.SmartFitAI.service.User.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user/contact")
public class ContactController {

    private IContactService contactService;
    private CurrentUserService currentUserService;

    @Autowired
    public ContactController(IContactService contactService, CurrentUserService currentUserService) {
        this.contactService = contactService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/current-user")
    public Contact getCurrentUser() {
        User currentUser = currentUserService.getCurrentUser();
        return contactService.getContactByUID(currentUser.getId());
    }


    @PostMapping("/create")
    public ResponseEntity<HashMap> createContact(@RequestBody ContactDTO contactDTO) {
        HashMap<String, Object> response = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();

        if(contactDTO.hasAnyNullField()){
            response.put("alert","All fields are required !");
            response.put("your request",contactDTO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Contact contact = new Contact(
                currentUser.getId(),
                contactDTO.getPhone(),
                contactDTO.getName(),
                contactDTO.getSurname()
        );

        boolean result = contactService.createContact(contact);
        if(!result){
            response.put("alert","Error creating user contact !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message","Contact has been created successfully !");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HashMap> updateContact(@RequestBody ContactDTO contactDTO) {
        HashMap<String, Object> response  = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        Contact contact = contactService.getContactByUID(currentUser.getId());
        try{
            if(contact == null){
                response.put("alert","User contact cannot be found !");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }else{
                // CHECK FIELDS , IF THERE IS NULL FIELDS THEN ...
                if(contactDTO.hasAnyNullField()){
                    // GET ALL PRIVATE FIELDS
                    for(Field contactDTOfield : contactDTO.getClass().getDeclaredFields()){
                        // SET THEM ACCESSIBLE
                        contactDTOfield.setAccessible(true);
                        // CHECK THEIR VALUES
                        if(contactDTOfield.get(contactDTO) != null){
                            // MAKE THEM EQUAL WITH ANOTHER CLASS WHICH HAS THE SAME FIELD NAMES
                            Field contactField = contact.getClass().getDeclaredField(contactDTOfield.getName());
                            contactField.setAccessible(true);
                            contactField.set(contact, contactDTOfield.get(contactDTO));
                        }
                    }
                }// END IF
                else{
                    for(Field contactDTOfield : contactDTO.getClass().getDeclaredFields()){
                        contactDTOfield.setAccessible(true);
                        Field contactField = contact.getClass().getDeclaredField(contactDTOfield.getName());
                        contactField.setAccessible(true);
                        contactField.set(contact, contactDTOfield.get(contactDTO));
                    }// END FOR
                }// END ELSE



                boolean result = contactService.updateContact(contact);
                if(result){
                    response.put("message","Contact has been updated successfully !");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else{
                    response.put("message","Contact cannot be updated !");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on user contact update ! ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<HashMap> updateContact() {
        HashMap<String, Object> response = new HashMap<>();

        User currentUser = currentUserService.getCurrentUser();
        boolean result = contactService.deleteContact(contactService.getContactByUID(currentUser.getId()));

        if(result){
            response.put("message", "Contact has been deleted successfully !");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.put("message", "Contact cannot be deleted !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }



}
