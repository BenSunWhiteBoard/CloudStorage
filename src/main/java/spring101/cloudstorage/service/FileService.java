package spring101.cloudstorage.service;

import org.springframework.stereotype.Service;
import spring101.cloudstorage.mapper.FileMapper;
import spring101.cloudstorage.mapper.UserMapper;
import spring101.cloudstorage.model.File;

import java.util.ArrayList;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public ArrayList<File> selectAll(Integer curUserId) {
        return fileMapper.selectAll(curUserId);
    }

    public Integer uploadFile(File file, Integer curUserId) {
        if (fileMapper.selectByName(file.getFilename(), curUserId) != null) {
            return -1;
        }else{
            return fileMapper.insert(new File(null, file.getFilename(), file.getContenttype(),
                    file.getFilesize(), curUserId, file.getFiledata()));
        }
    }

    public File viewFile(Integer fileId, Integer curUserId) {
        return fileMapper.selectById(fileId, curUserId);
    }

    public boolean deleteFile(Integer fileId, Integer curUserId) {
        if (fileMapper.selectById(fileId, curUserId) == null) {
            return false;
        } else {
            fileMapper.delete(fileId, curUserId);
            return true;
        }
    }
}
