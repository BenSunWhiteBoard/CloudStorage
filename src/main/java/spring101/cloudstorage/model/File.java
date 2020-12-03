package spring101.cloudstorage.model;

import javax.tools.FileObject;
import lombok.Data;

@Data
public class File {
    private Integer fileId;
    private String filename;
    private String contenttype;
    private Long filesize;
    private Integer userId;
    private byte[] filedata;

    public File(Integer fileId, String filename, String contenttype, Long filesize, Integer userId, byte[] filedata) {
        this.fileId = fileId;
        this.filename = filename;
        this.contenttype = contenttype;
        this.filesize = filesize;
        this.userId = userId;
        this.filedata = filedata;
    }
}
