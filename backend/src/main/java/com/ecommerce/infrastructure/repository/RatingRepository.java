package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.Rating;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.domain.repository.IRatingRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
import com.ecommerce.infrastructure.repository.factory.RatingFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RatingRepository implements IRatingRepository {
	private static final Logger log = LoggerFactory.getLogger(RatingRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Rating> list() {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM ratings");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] {}, (rs, rowNum) -> RatingFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Rating> get(long userId) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM ratings where getter = ?");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { userId },
					(rs, rowNum) -> RatingFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Rating rating) {
		try {
			log.debug(rating.toString());
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("purchases_id", rating.getPurchasesId());
			paramMap.put("evaluator", rating.getEvaluator());
			paramMap.put("getter", rating.getGetter());
			paramMap.put("seller", true);
			paramMap.put("score", rating.getScore());

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("ratings")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(final long id) {
		StringBuilder sbSql = new StringBuilder("DELETE FROM ratings ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

}
