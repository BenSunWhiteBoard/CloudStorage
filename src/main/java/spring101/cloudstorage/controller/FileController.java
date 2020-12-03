package spring101.cloudstorage.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring101.cloudstorage.model.File;
import spring101.cloudstorage.service.FileService;
import spring101.cloudstorage.service.UserService;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

//    @GetMapping("")
//    public String getPage(Authentication authentication, Model model) {
//        String username = authentication.getName();
//        Integer curUserId = userService.getCurUserId(username);
//        model.addAttribute("uploadedFiles", fileService.selectAll(curUserId));
//        return "home";
//    }

    @PostMapping("upload")
    public String uploadFile(@RequestParam(name = "fileUpload") MultipartFile fileUpload, Authentication authentication) throws IOException {
        String username = authentication.getName();
        Integer curUserId = userService.getCurUserId(username);
        File uploadFile;
        try {
            uploadFile = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize(), curUserId, fileUpload.getBytes());
        } catch (IOException e){
            return "redirect:/result?inputError" + e.getMessage();
        }
        Integer fileId = fileService.uploadFile(uploadFile, curUserId);
        if (fileId == -1){
            return "redirect:/result?uploadError=file already exists! ";
        }
        return "redirect:/result?success";
    }

    @GetMapping("delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Authentication authentication){
        String username = authentication.getName();
        Integer curUserId = userService.getCurUserId(username);
        if(fileService.deleteFile(fileId,curUserId)){
            return "redirect:/result?success";
        }else{
            return "redirect:/result?inputError";
        }
    }

//    @GetMapping("/view/{fileId}")
//    public String viewFile(@PathVariable Integer fileId,Authentication authentication){
//        String username = authentication.getName();
//        Integer curUserId = userService.getCurUserId(username);
//        File viewedFile = fileService.viewFile(fileId,curUserId);
//        if(viewedFile!=null){
//
//            return "redirect:/result?success";
//        }else{
//            return "redirect:/result?uploadError=file not exists";
//        }
//    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId, Authentication authentication){
        String username = authentication.getName();
        Integer curUserId = userService.getCurUserId(username);
        File viewedFile = fileService.viewFile(fileId,curUserId);
        if(viewedFile!=null){

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(viewedFile.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + viewedFile.getFilename() + "\"")
                    .body(new ByteArrayResource(viewedFile.getFiledata()));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

//    @ModelAttribute("imageType")
//    public String imgType(){return "image/";}
//
//    @ModelAttribute("textType")
//    public String textType(){return "text/";}

}
