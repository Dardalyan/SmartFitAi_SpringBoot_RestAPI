package com.SmartFitAI.service.Info;

import com.SmartFitAI.model.Info;

import java.util.List;

public interface IInfoService {
    public Info getInfoByUID(String uid);
    public boolean createInfo(Info info);
    public boolean updateInfo(Info info);
    public boolean deleteInfo(Info info);
    public List<Info> getAllInfo();
}
