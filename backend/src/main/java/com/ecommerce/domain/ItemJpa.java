package com.ecommerce.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "items")
@Getter
@Setter
public class ItemJpa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	private String name;
	private String category;
	private String explanation;
	private Boolean available = true;
	private long seller;
	private LocalDateTime registeredAt;
	private int image;
	private int price;
	private boolean directDeal;
	private String dealRegion;
	private int viewCount;

	@Column(nullable=true)
	private boolean progress;

}
