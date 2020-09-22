package spring101.cloudstorage.service;

import org.springframework.stereotype.Service;
import spring101.cloudstorage.mapper.CredentialMapper;
import spring101.cloudstorage.model.Credential;

import java.util.ArrayList;

@Service
public class CredentialService {

    private EncryptionService encryptionService;
    private CredentialMapper credentialMapper;

    public CredentialService(EncryptionService encryptionService, CredentialMapper credentialMapper) {
        this.encryptionService = encryptionService;
        this.credentialMapper = credentialMapper;
    }

    public ArrayList<Credential> getAllCredentials(Integer userId)
    {
        return credentialMapper.selectAll(userId);
    }

    //encrypt when upload
    public Integer uploadCredential(Credential credential){
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(),credential.getKey());
        credential.setPassword(encryptedPassword);
        return credentialMapper.insert(credential);
    }

//    //decrypt when display
//    public Credential editCredential(Credential editedCredential){
//        Credential decryptedCredential = credentialMapper.selectById(editedCredential.getUserid(),editedCredential.getCredentialid());
//        decryptedCredential.setPassword(encryptionService.decryptValue(decryptedCredential.getPassword(),decryptedCredential.getKey()));
//        return decryptedCredential;
//    }
    public Credential getCredential(Integer userId, Integer credentialId){
        return credentialMapper.selectById(userId,credentialId);
    }

    public Integer updateCredential(Credential newCredential){
        String encryptedPassword = encryptionService.encryptValue(newCredential.getPassword(),newCredential.getKey());
        newCredential.setPassword(encryptedPassword);
        return credentialMapper.update(newCredential);
    }

    public boolean deleteCredential(Integer userId, Integer credentialId){
        if(credentialMapper.selectById(userId,credentialId)==null){
            return false;
        }else{
            credentialMapper.delete(userId,credentialId);
            return true;
        }
    }

}
