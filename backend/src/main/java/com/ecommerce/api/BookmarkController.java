package com.ecommerce.api;

import com.ecommerce.application.IBookmarkService;
import com.ecommerce.application.IItemService;
import com.ecommerce.application.IRatingService;
import com.ecommerce.domain.Bookmark;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.EmptyListException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class BookmarkController {
	public static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);

	private IBookmarkService bookmarkService;
	@Autowired
	private IItemService itemService;

	@Autowired
	public BookmarkController(IBookmarkService bookmarkService) {
		Assert.notNull(bookmarkService, "bookmarkService 개체가 반드시 필요!");
		this.bookmarkService = bookmarkService;
	}

	@ApiOperation(value = "Register Bookmark")
	@RequestMapping(value = "/bookmarks", method = RequestMethod.POST)
	public long register(@RequestBody Bookmark bookmark) {
		Bookmark b = bookmarkService.get(bookmark.getUserId(), bookmark.getItemId());
		if (b != null)
			return 0;

		return bookmarkService.register(bookmark);
	}

	@ApiOperation(value = "Fetch Bookmarks")
	@RequestMapping(value = "/bookmarks", method = RequestMethod.GET)
	public List<Bookmark> list() {
		List<Bookmark> list = bookmarkService.list();

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return list;
	}

	@ApiOperation(value = "Fetch Bookmarks with user id")
	@RequestMapping(value = "/bookmarks/{userId}", method = RequestMethod.GET)
	public List<Item> list(@PathVariable long userId) throws IOException, Exception {
		List<Bookmark> list = bookmarkService.list(userId);

		List<Item> iList = new ArrayList<Item>();

		for (Bookmark b : list) {
			iList.add(itemService.get(b.getItemId()));
		}

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return iList;
	}

	@ApiOperation(value = "Fetch Bookmarks with user id")
	@RequestMapping(value = "/bookmarkedId/{userId}", method = RequestMethod.GET)
	public List<Long> list2(@PathVariable long userId) throws IOException, Exception {
		List<Bookmark> list = bookmarkService.list(userId);

		List<Long> iList = new ArrayList<>();

		for (Bookmark b : list) {
			iList.add(b.getItemId());
		}

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return iList;
	}

	@ApiOperation(value = "Delete Bookmarks with id")
	@RequestMapping(value = "/bookmarks/{id}", method = RequestMethod.DELETE)
	public int delete(@PathVariable int id) {
		return bookmarkService.delete(id);
	}

	@RequestMapping(value = "/bookmarks/{userId}/{itemId}", method = RequestMethod.DELETE)
	public int deleteById(@PathVariable long userId, @PathVariable long itemId) {
		return bookmarkService.deleteById(userId, itemId);
	}
}
