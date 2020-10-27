package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemFactory {
	public static Item create(ResultSet rs) throws SQLException {
		if (rs == null)
			return null;
		Item item = new Item();
		item.setId(rs.getLong("id"));
		item.setName(rs.getString("name"));
		item.setCategory(rs.getString("category"));
		item.setExplanation(rs.getString("explanation"));
		item.setAvailable(rs.getBoolean("available"));
		item.setSeller(rs.getLong("seller"));
		item.setRegisteredAt(rs.getTimestamp("registered_at").toLocalDateTime());
		item.setImage(rs.getInt("image"));
		item.setPrice(rs.getInt("price"));
		item.setDirectDeal(rs.getBoolean("direct_deal"));
		if (item.isDirectDeal()) {
			item.setDealRegion(rs.getString("deal_region"));
		}
		item.setViewCount(rs.getInt("view_count"));

		return item;
	}
}
