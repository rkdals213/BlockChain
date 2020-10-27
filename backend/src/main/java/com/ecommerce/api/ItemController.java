package com.ecommerce.api;

import com.ecommerce.application.IItemService;
import com.ecommerce.application.IPurchaseRecordContractService;
import com.ecommerce.domain.BasicResponse;
import com.ecommerce.domain.Cash;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.ItemJpa;
import com.ecommerce.domain.exception.EmptyListException;
import com.ecommerce.domain.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ItemController {
    public static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private IItemService itemService;

    @Autowired
    private IPurchaseRecordContractService purchaseRecordContractService;

    @Value("${testingvalue}")
    private String test;

    @Autowired
    public ItemController(IItemService itemService) {
        Assert.notNull(itemService, "itemService 개체가 반드시 필요!");
        this.itemService = itemService;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 상품 등록
     *
     * @param item
     * @return Item
     * @throws Exception
     * @throws IOException
     */
    @ApiOperation(value = "Register an item")
    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public Item register(@RequestBody Item item) throws IOException, Exception {
        System.out.println(item.toString());
        logger.info(item.toString());
        return itemService.register(item);
        // return null;
    }

    @ApiOperation(value = "Fetch all items")
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List<Item> list() {
        List<Item> list = itemService.list();
        System.out.println(test);

        if (list == null || list.isEmpty())
            throw new EmptyListException("NO DATA");

        return list;
    }

    @ApiOperation(value = "Fetch an item with id")
    @RequestMapping(value = "/items/{id}", method = RequestMethod.GET)
    public Item get(@PathVariable int id) throws Exception {
        itemService.viewCountUpdate(id);
        Item item = itemService.get(id);
        if (item == null) {
            logger.error("NOT FOUND ID: ", id);
            throw new NotFoundException(id + " 상품 정보를 찾을 수 없습니다.");
        }
        return item;
    }

    @ApiOperation(value = "Fetch an item with id")
    @RequestMapping(value = "/items/seller/{id}", method = RequestMethod.GET)
    public List<Item> getSellerItem(@PathVariable int id) {
        List<Item> item = itemService.getByUser(id);
        if (item == null) {
            logger.error("NOT FOUND ID: ", id);
            throw new NotFoundException(id + " 상품 정보를 찾을 수 없습니다.");
        }
        return item;
    }

    @RequestMapping(value = "/items/seller/{category}/{name}/{page}", method = RequestMethod.GET)
    public List<Item> getBySellerItem(@PathVariable String category, @PathVariable String name,
            @PathVariable int page) {
        List<Item> item = itemService.getByUser(category, name, page);
        if (item == null) {
            logger.error("NOT FOUND NAME: ", name);
            throw new NotFoundException(name + "의 상품 정보를 찾을 수 없습니다.");
        }
        return item;
    }

    @RequestMapping(value = "/items/sellerLength/{category}/{name}/{page}", method = RequestMethod.GET)
    public int getLengthBySellerItem(HttpServletResponse res, @PathVariable String category, @PathVariable String name,
            @PathVariable int page) {
        int length = itemService.getLengthByUser(category, name) / 12 + 1;
        return length;
    }

    @ApiOperation(value = "Fetch an item with name")
    @RequestMapping(value = "/items/name/{category}/{name}/{page}", method = RequestMethod.GET)
    public List<Item> getByName(@PathVariable String category, @PathVariable String name, @PathVariable int page) {
        System.out.println(category + ":" + name + ":" + page);
        List<Item> items = itemService.getByName(category, name, page);
        if (items == null || items.size() == 0) {
            logger.error("NOT FOUND LIST OF NAME: ", name);
            return null;
        }
        return items;
    }

    @RequestMapping(value = "/items/nameLength/{category}/{name}/{page}", method = RequestMethod.GET)
    public int getLengthByName(HttpServletResponse res, @PathVariable String category, @PathVariable String name,
            @PathVariable int page) {
        int length = itemService.getLengthByname(category, name) / 12 + 1;
        return length;
    }

    @ApiOperation(value = "Fetch an item with name")
    @RequestMapping(value = "/items/name/{name}/{page}", method = RequestMethod.GET)
    public List<Item> getByOnlyName(@PathVariable String name, @PathVariable int page) {
        System.out.println(name + ":" + page);
        List<Item> items = itemService.getByOnlyName(name, page);
        if (items == null || items.size() == 0) {
            logger.error("NOT FOUND LIST OF NAME: ", name);
            return null;
        }
        return items;
    }

    @ApiOperation(value = "Fetch an item with category")
    @RequestMapping(value = "/items/category/page/{category}/{page}", method = RequestMethod.GET)
    public List<Item> getByCategory(@PathVariable String category, @PathVariable int page) {
        System.out.println(category);
        List<Item> items = itemService.getByCategory(category, page);
        // System.out.println(items.toString());
        if (items == null || items.size() == 0) {
            logger.error("NOT FOUND LIST OF CATEGORY: ", category);
            return null;
        }
        return items;
    }

    @ApiOperation(value = "Fetch an item with main category")
    @RequestMapping(value = "/items/category/{category}", method = RequestMethod.GET)
    public List<Item> getByMainCategory(@PathVariable String category) {
        System.out.println(category);
        List<Item> items = itemService.getByMainCategory(category);
        if (items == null || items.size() == 0) {
            logger.error("NOT FOUND LIST OF CATEGORY: ", category);
            return null;
        }
        return items;
    }

    @ApiOperation(value = "Fetch an item with id")
    @RequestMapping(value = "/items/of/{uid}", method = RequestMethod.GET)
    public List<Item> getByUser(@PathVariable int uid) {
        List<Item> items = itemService.getByUser(uid);
        if (items == null || items.size() == 0) {
            logger.error("NOT FOUND LIST OF UID: ", uid);
            return null;
        }
        return items;
    }

    /**
     * TODO Sub PJT Ⅲ 과제 3 상품 판매 취소
     *
     * @param id 아이템 id
     * @return Item
     * @throws Exception
     */
    @ApiOperation(value = "Delete an item with id")
    @RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE)
    public int delete(@PathVariable long id, @RequestBody Cash cash) throws Exception {
        // return itemService.delete(id);
        return itemService.complete(id, cash.getWalletAddress(), cash.getPrivateKey());
    }

    @RequestMapping(value = "/items/purchaserecord", method = RequestMethod.GET)
    public String deployPurchseRecord() throws Exception {
        String answer = null;
        try {
            answer = purchaseRecordContractService.deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    @RequestMapping(value = "/items/deploy", method = RequestMethod.GET)
    public String deployItem() throws Exception {
        String answer = null;
        try {
            answer = itemService.deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }

    @ApiOperation(value = "Update an item")
    @RequestMapping(value = "/items", method = RequestMethod.PUT)
    public Item update(@RequestBody Item item) throws Exception {
        return itemService.update(item);
    }

    @RequestMapping(value = "/items/images/{id}", method = RequestMethod.POST)
    public void uploadFile(@PathVariable long id, @RequestParam("file") MultipartFile[] file)
            throws IllegalStateException, IOException {
        System.out.println("123");
        for (int i = 0; i < file.length; i++)
            System.out.println("파일이름: " + file[i].getOriginalFilename());

        String baseDir = this.getClass().getResource("/").getPath() + "static/upload/";
        baseDir = baseDir.substring(1);

        // 리눅스를 위한 세팅
        if (baseDir.substring(0, 1).equals("C")) {
            baseDir = "C:/Users/multicampus/images/";
        } else {
            baseDir = "/home/ubuntu/deploy/backend/itemImage/";
        }
        File folder = new File(baseDir);

        if (!folder.exists())
            folder.mkdirs();

        System.out.println(baseDir);

        int count = 1;
        for (MultipartFile temp : file) {
            String thisFile = Long.toString(id) + "_" + Integer.toString(count) + ".jpg";
            temp.transferTo(new File(baseDir + thisFile));
            count++;
        }
        itemService.imageUpdate(id, file.length);
    }

    @ApiOperation(value = "Select page list") // page에 대한거 db에서 쿼리로 넘기기
    @RequestMapping(value = "/items/page/{page}", method = RequestMethod.GET)
    public List<Item> getPage(@PathVariable int page) {
        System.out.println(page);
        List<Item> list = itemService.pageList(page);
        for (Item temp : list)
            System.out.println(temp);
        return list;
    }

    @GetMapping("/items/images/{id}")
    public Object downloadFile(@PathVariable String id, HttpServletRequest request) throws MalformedURLException {
        // Load file as Resource
        // Resource resource = service.loadFileAsResource(fileName);
        Resource resource;
        String baseDir = this.getClass().getResource("/").getPath() + "static/upload/";
        baseDir = baseDir.substring(1);

        // 리눅스를 위한 세팅
        if (baseDir.substring(0, 1).equals("C")) {
            baseDir = "C:/Users/multicampus/images/";
        } else {
            baseDir = "/home/ubuntu/deploy/backend/itemImage/";
        }
        String fileName = baseDir + id + ".jpg";

        Path path = Paths.get(baseDir).toAbsolutePath().normalize();
        Path fPath = path.resolve(fileName).normalize();

        resource = new UrlResource(fPath.toUri());
        System.out.println(fPath);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        System.out.println(contentType);
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        System.out.println("여기까진오냐");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    ///////////////////////////////////////////// jpa 구간
    ///////////////////////////////////////////// //////////////////////////////////////////////

    @ApiOperation(value = "Select page list") // page에 대한거 db에서 쿼리로 넘기기
    @RequestMapping(value = "/items/page/{page}/category/{category}/keyword/{keyword}/kind/{kind}", method = RequestMethod.GET)
    public Page<ItemJpa> getPage(@PathVariable int page, @PathVariable String category, @PathVariable String keyword,
            @PathVariable int kind) {
        System.out.println("jpa로 검색하자");
        System.out.println(category);
        System.out.println(keyword);
        if (category.equals("전체보기"))
            category = "";
        Page<ItemJpa> items = null;
        if (kind == 0) {
            items = itemService.getByNameContaining(page, keyword);
            System.out.println(kind);
        } else if (kind == 1) {
            items = itemService.getByNameContainingAndCategoryContaining(page, keyword, category);
            System.out.println(kind);
        } else if (kind == 2) {
            items = itemService.getBySeller(page, keyword);
            System.out.println(kind);
        } else if (kind == 3) {
            items = itemService.getBySellerAndCategoryContaining(page, keyword, category);
            System.out.println(kind);
        }
        System.out.println(items.getTotalElements());
        return items;
    }

    @ApiOperation(value = "Select page list") // page에 대한거 db에서 쿼리로 넘기기
    @RequestMapping(value = "/items/page/{page}/category/{category}/kind/{kind}", method = RequestMethod.GET)
    public Page<ItemJpa> getPageNoKeyword(@PathVariable int page, @PathVariable String category,
            @PathVariable int kind) {
        System.out.println("jpa로 검색하자 아무것도없는거 검색하자");
        System.out.println(category);

        Page<ItemJpa> items = null;
        if (kind == 0) {
            items = itemService.getByNameContaining(page, "");
            System.out.println(kind);
        } else if (kind == 1) {
            items = itemService.getByNameContainingAndCategoryContaining(page, "", category);
            System.out.println(kind);
        } else if (kind == 2) {
            items = itemService.getBySeller(page, "");
            System.out.println(kind);
        } else if (kind == 3) {
            items = itemService.getBySellerAndCategoryContaining(page, "", category);
            System.out.println(kind);
        }
        System.out.println(items.getTotalElements());
        return items;
    }

}
