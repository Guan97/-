package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.entity.Goods;

public interface GoodsMapper {

	List<Goods> selectGoods(Goods goods);

	List<Goods> selectGname(String gname);

	void addGoods(Goods goods);

	void updateGoods(Goods goods);

	void deleteGoods(int parseInt);

}
