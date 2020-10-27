package com.ecommerce.application.impl;

import com.ecommerce.application.IBookmarkService;
import com.ecommerce.application.IItemService;
import com.ecommerce.application.IRatingService;
import com.ecommerce.domain.Bookmark;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.IBookmarkRepository;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IRatingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkService implements IBookmarkService {
	public static final Logger logger = LoggerFactory.getLogger(BookmarkService.class);

	private final IBookmarkRepository bookmarkRepository;

	@Autowired
	public BookmarkService(final IBookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}

	@Override
	public List<Bookmark> list() {
		return this.bookmarkRepository.list();
	}

	@Override
	public List<Bookmark> list(long userId) {
		return this.bookmarkRepository.list(userId);
	}

	@Override
	public long register(final Bookmark bookmark) {
		return this.bookmarkRepository.create(bookmark);
	}

	@Override
	public int delete(final long id) {
		return this.bookmarkRepository.delete(id);
	}

	@Override
	public Bookmark get(long userId, long itemId) {
		return this.bookmarkRepository.get(userId, itemId);
	}

	@Override
	public int deleteById(long userId, long itemId) {
		return this.bookmarkRepository.deleteById(userId, itemId);
	}
}
