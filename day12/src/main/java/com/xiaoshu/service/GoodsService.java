package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.entity.Goods;

@Service
@Transactional
public class GoodsService {

	@Autowired
	private GoodsMapper gm;
	
	public PageInfo<Goods> selectGoods(Goods goods, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Goods> list = gm.selectGoods(goods);
		PageInfo<Goods> page = new PageInfo<>(list);
		return page;
	}

	public Object existGoodsWithUserGname(String gname) {
		List<Goods> list  = gm.selectGname(gname);
		return list.size()==0?null:list.get(0);
	}

	public void addGoods(Goods goods) {
		gm.addGoods(goods);
	}

	public void updateGoods(Goods goods) {
		gm.updateGoods(goods);
	}

	public void deleteGoods(int parseInt) {
		gm.deleteGoods(parseInt);
	}

}
