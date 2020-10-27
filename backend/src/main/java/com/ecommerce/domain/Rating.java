package com.ecommerce.domain;

import lombok.Data;

@Data
public class Rating {
	private long id;
	private long purchasesId;
	private long evaluator;
	private long getter;
	private boolean seller;
	private int score;
	private String privateKey;

	@Override
	public String toString() {
		return "{ id: " + id + "\n\tpurchasesId: " + purchasesId + "\n\tevaluator: " + evaluator + "\n\tgetter: "
				+ getter + "\n\tseller: " + seller + "\n\tscore: " + score + " }";
	}
}
