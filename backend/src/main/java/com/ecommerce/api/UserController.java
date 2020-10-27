package com.ecommerce.api;

import com.ecommerce.api.handler.FileUploadDownloadHandler;
import com.ecommerce.application.IUserService;
import com.ecommerce.domain.User;
import com.ecommerce.domain.exception.DomainException;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${profileUrl}")
    private String profileUrl;

    private IUserService userService;

    @Autowired
    FileUploadDownloadHandler fileUploadDownloadHandler;

    @Autowired
    public UserController(IUserService userService) {
        Assert.notNull(userService, "userService 개체가 반드시 필요!");
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> list() {
        List<User> userList = userService.list();

        if (userList == null || userList.isEmpty())
            throw new EmptyListException("NO DATA");

        return userList;
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User get(@PathVariable int id) {

        User user = userService.get(id);
        if (user == null) {
            logger.error("NOT FOUND ID: ", id);
            throw new NotFoundException(id + " 회원 정보를 찾을 수 없습니다.");
        }

        return user;
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        User userFetched = userService.get(user.getEmail());
        if (!userFetched.getPassword().equals(user.getPassword()))
            throw new DomainException("비밀번호가 일치하지 않습니다.");
        userFetched.setPassword("");
        return userFetched;
    }

    @PostMapping("/users/upload/{id}")
    @ApiOperation(value = "파일 업로드")
    public ResponseEntity<Map<String, Object>> getArticle(HttpServletResponse res, HttpServletRequest req,
            @RequestParam("file") MultipartFile[] file, @PathVariable long id)
            throws JsonProcessingException, IOException {
        ResponseEntity<Map<String, Object>> entity = null;
        try {
            String filename = id + "";
            String realPath = System.getProperty("user.dir") + profileUrl + filename;
            System.out.println("업로드 경로 " + realPath);
            file[0].transferTo(new File(realPath + ".jpg"));
        } catch (RuntimeException e) {
        }
        return entity;
    }

    @GetMapping("/users/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(HttpServletResponse res, HttpServletRequest req,
            @PathVariable String fileName) throws MalformedURLException {
        // Load file as Resource
        Resource resource = fileUploadDownloadHandler.loadFileAsResource(fileName + ".jpg");

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = req.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        return userService.add(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

}
