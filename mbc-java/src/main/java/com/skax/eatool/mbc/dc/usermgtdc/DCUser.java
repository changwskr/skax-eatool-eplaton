package com.skax.eatool.mbc.dc.usermgtdc;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.usermgtdc.dto.PageDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.TreeDDTO;
import com.skax.eatool.mbc.dc.usermgtdc.dto.UserDDTO;
import com.skax.eatool.mbc.as.bzcrudbus.transfer.ICommonDTO;

/**
 * 사용자 관리 도메인 컴포넌트
 * 
 * 프로그램명: DCUser.java
 * 설명: 사용자 관리 관련 데이터베이스 작업을 수행하는 도메인 컴포넌트
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 사용자 CRUD 작업
 * - 페이징 처리
 * - 트리 구조 데이터 조회
 * - JDBC Template 기반 데이터 접근
 * 
 * @version 1.0
 */
@Repository
public class DCUser implements IDCUser {

	private static final Logger logger = LoggerFactory.getLogger(DCUser.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
		User user = new User();
		user.setUserId(rs.getString("USER_ID"));
		user.setUserName(rs.getString("USER_NAME"));
		user.setEmail(rs.getString("EMAIL"));
		user.setPhone(rs.getString("PHONE"));
		user.setRole(rs.getString("ROLE"));
		user.setStatus(rs.getString("STATUS"));
		return user;
	};

	// IDCUser interface methods
	@Override
	public String getUserId() {
		logger.info("=== DCUser.getUserId START ===");
		logger.info("=== DCUser.getUserId END ===");
		return null; // Stub implementation
	}

	@Override
	public void setUserId(String userId) {
		logger.info("=== DCUser.setUserId START ===");
		logger.info("=== DCUser.setUserId END ===");
		// Stub implementation
	}

	@Override
	public String getUserName() {
		logger.info("=== DCUser.getUserName START ===");
		logger.info("=== DCUser.getUserName END ===");
		return null; // Stub implementation
	}

	@Override
	public void setUserName(String userName) {
		logger.info("=== DCUser.setUserName START ===");
		logger.info("=== DCUser.setUserName END ===");
		// Stub implementation
	}

	@Override
	public String getUserEmail() {
		logger.info("=== DCUser.getUserEmail START ===");
		logger.info("=== DCUser.getUserEmail END ===");
		return null; // Stub implementation
	}

	@Override
	public void setUserEmail(String userEmail) {
		logger.info("=== DCUser.setUserEmail START ===");
		logger.info("=== DCUser.setUserEmail END ===");
		// Stub implementation
	}

	@Override
	public List<HashMap> getUserList(ICommonDTO commonDto) throws NewBusinessException {
		logger.info("=== DCUser.getUserList START ===");
		logger.info("=== DCUser.getUserList END ===");
		// Stub implementation
		return new ArrayList<>();
	}

	@Override
	public User selectUser(String userId) throws Exception {
		logger.info("=== DCUser.selectUser START ===");
		try {
			String sql = "SELECT * FROM USER_INFO WHERE USER_ID = ?";
			List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
			logger.info("=== DCUser.selectUser END ===");
			return users.isEmpty() ? null : users.get(0);
		} catch (Exception e) {
			logger.error("Unexpected error selecting user with ID: " + userId, e);
			logger.info("=== DCUser.selectUser END ===");
			throw new NewBusinessException("B0000001", "selectUser", e);
		}
	}

	@Override
	public void crudUser(UserDDTO[] userDDTOs) throws NewBusinessException {
		logger.info("=== DCUser.crudUser START ===");
		logger.debug("crudUser method started");

		String crud = null;
		logger.debug("userDDTO count" + userDDTOs.length);
		try {

			for (int i = 0; i < userDDTOs.length; i++) {

				crud = (userDDTOs[i].getCrud()).toUpperCase();
				logger.debug("crud = " + crud);

				if (crud.equals("C")) {
					insertUser(userDDTOs[i]);
				} else if (crud.equals("U")) {
					updateUser(userDDTOs[i]);
				} else if (crud.equals("D")) {
					deleteUser(userDDTOs[i]);
				}
			}
		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
		}
		logger.info("=== DCUser.crudUser END ===");
	}

	@Override
	public List<User> getListUser(UserDDTO userDDTO) throws NewBusinessException {
		logger.info("=== DCUser.getListUser START ===");
		List<User> UserList = null;

		try {
			String sql = "SELECT * FROM USER_INFO";
			UserList = jdbcTemplate.query(sql, userRowMapper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("=== DCUser.getListUser END ===");
		return NewObjectUtil.copyForList(User.class, UserList);
	}

	public List<Page> getListPage(PageDDTO pageDDTO) throws NewBusinessException {
		logger.info("=== DCUser.getListPage START ===");

		List<Page> pageList = null;
		String pageCount;
		String outptLineCnt;
		try {
			// Query page data that matches the request conditions
			String sql = "SELECT * FROM USER_INFO LIMIT ? OFFSET ?";
			int limit = pageDDTO.getPageSize() != null ? pageDDTO.getPageSize() : 10;
			int offset = pageDDTO.getOffset() != null ? pageDDTO.getOffset() : 0;
			pageList = jdbcTemplate.query(sql, (rs, rowNum) -> {
				Page page = new Page();
				page.setUserId(rs.getString("USER_ID"));
				page.setUserName(rs.getString("USER_NAME"));
				page.setEmail(rs.getString("EMAIL"));
				return page;
			}, limit, offset);

			// Get total count
			String countSql = "SELECT COUNT(*) FROM USER_INFO";
			pageCount = jdbcTemplate.queryForObject(countSql, String.class);
			// Get output count: may need to be calculated separately
			outptLineCnt = String.valueOf(pageList.size());
			// Set total count and output count in the first item of the List
			if (pageList.size() > 0 && pageList != null) {
				((Page) pageList.get(0)).setTotalLineCnt(Integer.parseInt(pageCount));
				((Page) pageList.get(0)).setOutptLineCnt(Integer.parseInt(outptLineCnt));
			}

		} catch (Exception e) {
			// TODO Add proper catch handling
			e.printStackTrace();
		}
		logger.info("=== DCUser.getListPage END ===");
		return pageList;

	}

	public List<Tree> getListTree(TreeDDTO treeDDTO) throws NewBusinessException {
		logger.info("=== DCUser.getListTree START ===");

		List<Tree> TreeList = null;

		try {
			String sql = "SELECT * FROM USER_INFO";
			TreeList = jdbcTemplate.query(sql, (rs, rowNum) -> {
				Tree tree = new Tree();
				tree.setUserId(rs.getString("USER_ID"));
				tree.setUserName(rs.getString("USER_NAME"));
				return tree;
			});
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		logger.info("=== DCUser.getListTree END ===");
		return NewObjectUtil.copyForList(Tree.class, TreeList);

	}

	// Helper methods for CRUD operations
	private void insertUser(UserDDTO userDDTO) {
		logger.info("=== DCUser.insertUser START ===");
		String sql = "INSERT INTO USER_INFO (USER_ID, USER_NAME, EMAIL, PHONE, ROLE, STATUS) VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				userDDTO.getUserId(),
				userDDTO.getUserName(),
				userDDTO.getEmail(),
				userDDTO.getPhone(),
				userDDTO.getRole(),
				userDDTO.getStatus());
		logger.info("=== DCUser.insertUser END ===");
	}

	private void updateUser(UserDDTO userDDTO) {
		logger.info("=== DCUser.updateUser START ===");
		String sql = "UPDATE USER_INFO SET USER_NAME = ?, EMAIL = ?, PHONE = ?, ROLE = ?, STATUS = ? WHERE USER_ID = ?";
		jdbcTemplate.update(sql,
				userDDTO.getUserName(),
				userDDTO.getEmail(),
				userDDTO.getPhone(),
				userDDTO.getRole(),
				userDDTO.getStatus(),
				userDDTO.getUserId());
		logger.info("=== DCUser.updateUser END ===");
	}

	private void deleteUser(UserDDTO userDDTO) {
		logger.info("=== DCUser.deleteUser START ===");
		String sql = "DELETE FROM USER_INFO WHERE USER_ID = ?";
		jdbcTemplate.update(sql, userDDTO.getUserId());
		logger.info("=== DCUser.deleteUser END ===");
	}
}
