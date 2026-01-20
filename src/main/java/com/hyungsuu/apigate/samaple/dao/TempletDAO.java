//package com.hyungsuu.apigate.samaple.dao;
//
//import java.util.HashMap;
//
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.hyungsuu.apigate.samaple.vo.SelectSampleReqVo;
//
//@Repository
//public class TempletDAO {
//
//	@Autowired
//	private SqlSessionTemplate sqlSession;
//	
//	public HashMap<String, Object> selectSample(SelectSampleReqVo selectSampleReqVo) {
//			return sqlSession.selectOne("Common.selectCommon", selectSampleReqVo);
//	}
//	
//}