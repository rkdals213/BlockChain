package com.ecommerce.infrastructure.repository;

import com.ecommerce.domain.Item;
import com.ecommerce.domain.exception.RepositoryException;
import com.ecommerce.domain.repository.IItemRepository;
import com.ecommerce.infrastructure.repository.factory.ItemFactory;
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
public class ItemRepository implements IItemRepository {
	private static final Logger log = LoggerFactory.getLogger(ItemRepository.class);

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Item> list() {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE available=? "); // where
																							// available
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { true },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> pageList(final int page) {
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE available=1 and progress=0 ORDER BY registered_at DESC limit 12 offset ?"); // where
		// available
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { (page - 1) * 12 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByUser(final long userId) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE seller=? ");
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { userId },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByName(final String category, final String name, final int page) {
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE name like ? and category like ? and progress=0 and available=1 ORDER BY registered_at DESC limit 12 offset ?");
		String tname = "%" + name + "%";
		String tcategory = "%" + category + "%";
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { tname, tcategory, (page - 1) * 12 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByCategory(final String category, final int page) {
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE category like ? and progress=0 and available=1 ORDER BY registered_at DESC limit 12 offset ?");
		String tempCategory = "%" + category + "%";
		System.out.println(tempCategory);
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { tempCategory, (page - 1) * 12 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByUserName(final String category, final long id, final int page) {
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE seller=? and category like ? and progress=0 and available=1 ORDER BY registered_at DESC limit 12 offset ?");
		String tcategory = "%" + category + "%";
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { id, tcategory, (page - 1) * 12 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByMainCategory(final String category) { // 조회수 순으로 5개 보여주기
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE category like ? and progress=0 and available=1 order by view_count desc limit 9");
		String tempCategory = "%" + category + "%";
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { tempCategory },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public Item get(final long id) {
		StringBuilder sbSql = new StringBuilder("SELECT * FROM items WHERE id=?");
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(), new Object[] { id },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public long create(final Item item) {
		try {
			log.debug(item.toString());
			ZonedDateTime utc = ZonedDateTime.now(ZoneId.of("UTC"));
			LocalDateTime now = utc.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime();
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("name", item.getName());
			paramMap.put("category", item.getCategory());
			paramMap.put("explanation", item.getExplanation());
			paramMap.put("available", true);
			paramMap.put("seller", item.getSeller());
			paramMap.put("registered_at", now);
			paramMap.put("image", 0);
			paramMap.put("price", item.getPrice());
			paramMap.put("direct_deal", item.isDirectDeal());
			if (item.isDirectDeal()) {
				paramMap.put("deal_region", item.getDealRegion());
			}
			paramMap.put("view_count", 0);
			paramMap.put("progress", false);

			log.debug("registered_at: " + paramMap.get("registered_at"));

			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("items")
					.usingGeneratedKeyColumns("id");

			Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
			log.debug("INSERTED: " + newId.longValue());
			return newId.longValue();

		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int update(final Item item) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET price=? ");
		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { item.getPrice(), item.getId() });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int imageUpdate(final long id, final int image) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET image=? ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { image, id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int delete(final long id) {
		StringBuilder sbSql = new StringBuilder("DELETE FROM items ");
		sbSql.append("where id=?");

		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}

	}

	@Override
	public int viewCountUpdate(long id) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET view_count = view_count + 1 ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int complete(long id) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET available = ? ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { false, id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public List<Item> getByOnlyName(String name, int page) {
		StringBuilder sbSql = new StringBuilder(
				"SELECT * FROM items WHERE name like ? ORDER BY registered_at DESC limit 12 offset ?");
		String tname = "%" + name + "%";
		try {
			return this.jdbcTemplate.query(sbSql.toString(), new Object[] { tname, (page - 1) * 12 },
					(rs, rowNum) -> ItemFactory.create(rs));
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int getLengthByUser(String category, long id) {
		StringBuilder sbSql = new StringBuilder("SELECT count(*) FROM items WHERE seller=? and category like ?");
		String tcategory = "%" + category + "%";
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(), new Object[] { id, tcategory }, int.class);
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int getLengthByName(String category, String name) {
		StringBuilder sbSql = new StringBuilder("SELECT count(*) FROM items WHERE name like ? and category like ?");
		String tcategory = "%" + category + "%";
		String tname = "%" + name + "%";
		try {
			return this.jdbcTemplate.queryForObject(sbSql.toString(), new Object[] { tname, tcategory }, int.class);
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int changeProgressTrue(long id) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET progress = true ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

	@Override
	public int changeProgressFalse(long id) {
		StringBuilder sbSql = new StringBuilder("UPDATE items ");
		sbSql.append("SET progress = false ");

		sbSql.append("where id=?");
		try {
			return this.jdbcTemplate.update(sbSql.toString(), new Object[] { id });
		} catch (Exception e) {
			throw new RepositoryException(e, e.getMessage());
		}
	}

}
