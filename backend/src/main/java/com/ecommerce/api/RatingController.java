package com.ecommerce.api;

import com.ecommerce.application.IRatingService;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.EmptyListException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RatingController {
	public static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	private IRatingService ratingService;

	@Autowired
	public RatingController(IRatingService ratingService) {
		Assert.notNull(ratingService, "ratingService 개체가 반드시 필요!");
		this.ratingService = ratingService;
	}

	@ApiOperation(value = "Register Rating")
	@RequestMapping(value = "/ratings", method = RequestMethod.POST)
	public long register(@RequestBody Rating rating) throws Exception {
		return ratingService.register(rating);
	}

	@ApiOperation(value = "Fetch ratings")
	@RequestMapping(value = "/ratings", method = RequestMethod.GET)
	public List<Rating> list() {
		List<Rating> list = ratingService.list();

		if (list == null || list.isEmpty())
			throw new EmptyListException("NO DATA");

		return list;
	}

	@RequestMapping(value = "/ratings/{userId}", method = RequestMethod.GET)
	public double get(@PathVariable long userId) {
		double score = ratingService.get(userId);
		System.out.println("점수 : " + score);
		return score;
	}

	@ApiOperation(value = "Delete rating with id")
	@RequestMapping(value = "/ratings/{id}", method = RequestMethod.DELETE)
	public int delete(@PathVariable int id) {
		return ratingService.delete(id);
	}
}
