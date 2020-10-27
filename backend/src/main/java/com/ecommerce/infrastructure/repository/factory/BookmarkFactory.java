package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Bookmark;
import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookmarkFactory {
	public static Bookmark create(ResultSet rs) throws SQLException {
		if (rs == null)
			return null;
		Bookmark bookmark = new Bookmark();
		bookmark.setId(rs.getLong("id"));
		bookmark.setUserId(rs.getLong("user_id"));
		bookmark.setItemId(rs.getLong("item_id"));

		return bookmark;
	}
}
