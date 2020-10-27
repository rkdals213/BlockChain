package com.ecommerce.application;

import java.util.List;

import com.ecommerce.domain.Cash;
import com.ecommerce.domain.Rating;

import org.springframework.transaction.annotation.Transactional;

public interface IRatingService {
	List<Rating> list();

	double get(long userId);

	@Transactional
	long register(Rating rating) throws Exception;

	@Transactional
	int delete(long id);
}