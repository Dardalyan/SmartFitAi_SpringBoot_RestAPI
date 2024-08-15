package com.SmartFitAI.service.Info;

import com.SmartFitAI.model.Info;
import com.SmartFitAI.repository.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements IInfoService{

    private InfoRepository repo;

    @Autowired
    public InfoServiceImpl(InfoRepository infoRepo) {
        this.repo = infoRepo;
    }

    @Override
    public Info getInfoByUID(String uid) {
        return repo.findById(uid).orElse(null);
    }

    @Override
    public boolean createInfo(Info info) {
        try{
            Info newInfo = repo.save(info);
            if(newInfo == null)
                return false;
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateInfo(Info info) {
        try{
            if(getInfoByUID(info.getUid()) != null){
                repo.save(info);
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
    public boolean deleteInfo(Info info) {
        try{
            if(getInfoByUID(info.getUid()) != null){
                repo.delete(info);
                return true;
            }else return false;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<Info> getAllInfo() {
        return repo.findAll();
    }
}
