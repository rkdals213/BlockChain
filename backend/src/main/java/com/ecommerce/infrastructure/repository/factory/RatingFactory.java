package com.ecommerce.infrastructure.repository.factory;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingFactory {
	public static Rating create(ResultSet rs) throws SQLException {
		if (rs == null)
			return null;
		Rating rating = new Rating();
		rating.setId(rs.getLong("id"));
		rating.setPurchasesId(rs.getLong("purchases_id"));
		rating.setEvaluator(rs.getLong("evaluator"));
		rating.setGetter(rs.getLong("getter"));
		rating.setSeller(rs.getBoolean("seller"));
		rating.setScore(rs.getInt("score"));

		return rating;
	}
}
