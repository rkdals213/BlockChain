package com.ecommerce.domain.repository;

import com.ecommerce.domain.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IItemRepository {
	List<Item> list();

	List<Item> pageList(final int page);

	List<Item> getByUser(final long userId);

	List<Item> getByName(final String category, final String name, final int page);

	List<Item> getByCategory(final String category, final int page);

	List<Item> getByUserName(final String category, final long id, final int page);

	List<Item> getByMainCategory(final String category);

	Item get(long id);

	@Transactional
	long create(Item item);

	@Transactional
	int viewCountUpdate(long id);

	@Transactional
	int update(Item item);

	@Transactional
	int imageUpdate(final long id, final int image);

	@Transactional
	int delete(long id);

	@Transactional
	int complete(long id);

	@Transactional
	int changeProgressTrue(long id);

	@Transactional
	int changeProgressFalse(long id);

	List<Item> getByOnlyName(String name, int page);

	int getLengthByUser(String category, long name);

	int getLengthByName(String category, String name);
}
