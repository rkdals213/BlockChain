package com.ecommerce.domain;

import lombok.Data;

@Data
public class Bookmark {
	private long id;
	private long userId;
	private long itemId;

	@Override
	public String toString() {
		return "{ id: " + id + "\n\tuser_id: " + userId + "\n\titem_id: " + itemId + " }";
	}
}
