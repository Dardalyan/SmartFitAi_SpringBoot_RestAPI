package com.SmartFitAI.controller.info;

import com.SmartFitAI.dto.InfoDTO;
import com.SmartFitAI.model.Info;
import com.SmartFitAI.model.User;
import com.SmartFitAI.service.Info.IInfoService;
import com.SmartFitAI.service.User.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user/info")
public class InfoController {

    private CurrentUserService currentUserService;
    private IInfoService infoService;


    @Autowired
    public InfoController(CurrentUserService currentUserService, IInfoService infoService) {
        this.currentUserService = currentUserService;
        this.infoService = infoService;
    }

    @GetMapping("/current-user")
    public Info getCurrentUserInfo() {
        User user = currentUserService.getCurrentUser();
        return infoService.getInfoByUID(user.getId());
    }


    @PostMapping("/create")
    public ResponseEntity<HashMap> createUserInfo(@RequestBody InfoDTO infoDTO){
        User currentUser = currentUserService.getCurrentUser();

        HashMap<String, Object> response  = new HashMap<>();

        if(infoDTO.hasAnyNullField()){
            response.put("alert","All fields are required !");
            response.put("your request",infoDTO);
            response.put("required type",Info.fieldsLike());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        Info info = new Info(
                currentUser.getId(),
                infoDTO.getAge(),
                infoDTO.getGender(),
                infoDTO.getWeight(),
                infoDTO.getHeight(),
                infoDTO.getTarget(),
                infoDTO.getActivity_intensity(),
                infoDTO.getTarget_body_parts()
                );

        boolean result = infoService.createInfo(info);
        if(!result){
            response.put("alert","Error creating user info");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("message","Info has been created successfully !");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<HashMap> updateUserInfo(@RequestBody InfoDTO infoDTO){
        HashMap<String, Object> response  = new HashMap<>();
        User currentUser = currentUserService.getCurrentUser();
        Info info = infoService.getInfoByUID(currentUser.getId());
        try{
            if(info == null){
                response.put("alert","User info cannot be found !");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }else{
                // CHECK FIELDS , IF THERE IS NULL FIELDS THEN ...
                if(infoDTO.hasAnyNullField()){
                    // GET ALL PRIVATE FIELDS
                    for(Field infoDTOfield : infoDTO.getClass().getDeclaredFields()){
                        // SET THEM ACCESSIBLE
                        infoDTOfield.setAccessible(true);
                        // CHECK THEIR VALUES
                        if(infoDTOfield.get(infoDTO) != null && !infoDTOfield.get(infoDTO).equals(0.0) && !infoDTOfield.get(infoDTO).equals(0)){
                            // MAKE THEM EQUAL WITH ANOTHER CLASS WHICH HAS THE SAME FIELD NAMES
                            Field infoField = info.getClass().getDeclaredField(infoDTOfield.getName());
                            infoField.setAccessible(true);
                            infoField.set(info, infoDTOfield.get(infoDTO));
                        }
                        if(!infoDTOfield.getName().equals("target_body_parts") && !infoDTOfield.getName().equals("target")){
                            info.setBmr();
                            info.setCalorie_bound();
                        }
                    }
                }// END IF
                else{
                    for(Field infoDTOfield : infoDTO.getClass().getDeclaredFields()){
                        infoDTOfield.setAccessible(true);
                        Field infoField = info.getClass().getDeclaredField(infoDTOfield.getName());
                        infoField.setAccessible(true);
                        infoField.set(info, infoDTOfield.get(infoDTO));
                    }// END FOR
                        info.setBmr();
                        info.setCalorie_bound();
                }// END ELSE



                boolean result = infoService.updateInfo(info);
                if(result){
                    response.put("message","Info has been updated successfully !");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else{
                    response.put("message","Info cannot be updated !");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            response.put("alert","Error has occured on user info update ! ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<HashMap> deleteUserInfo()  {
        HashMap<String, Object> response = new HashMap<>();

        User currentUser = currentUserService.getCurrentUser();
        boolean result = infoService.deleteInfo(infoService.getInfoByUID(currentUser.getId()));

        if(result){
            response.put("message", "Info has been deleted successfully !");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            response.put("message", "Info cannot be deleted !");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }




 }

