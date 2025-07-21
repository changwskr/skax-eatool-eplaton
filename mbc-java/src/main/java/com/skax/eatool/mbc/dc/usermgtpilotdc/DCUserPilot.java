package com.skax.eatool.mbc.dc.usermgtpilotdc;

import java.util.List;

import com.skax.eatool.ksa.das.ibatis.NewSqlMapper;
import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.util.NewObjectUtil;
import com.skax.eatool.mbc.dc.usermgtpilotdc.dto.UserPilotDDTO;

public class DCUserPilot implements IDCUserPilot {

	public List<UserPilot> getListUser(UserPilotDDTO userpilotDDTO) throws NewBusinessException {
		System.out.println("##########################In the getListUser process####################");

		try {
			List<UserPilot> UserList = (List<UserPilot>) NewSqlMapper.getSqlMapClient().queryForList(
					"userpilot.getListUser",
					NewObjectUtil.copyForClass(UserPilot.class, userpilotDDTO));
			return NewObjectUtil.copyForList(UserPilot.class, UserList);
		} catch (Exception e) {
			throw new NewBusinessException("B0000001", "processCode", e);
		}
	}

	public void crudGrid(UserPilotDDTO[] userpilotDDTOs) throws NewBusinessException {
		System.out.println("##########################In the crudGRID process####################");

		try {

			int i;

			for (i = 0; i < userpilotDDTOs.length; i++) {
				if (userpilotDDTOs[i].getCrud().equals("c")) {
					System.out.println("##########################In the create process####################");
					System.out.println(userpilotDDTOs[i].getCrud());
					NewSqlMapper.getSqlMapClient()
							.insert("userpilot.createUserList",
									NewObjectUtil.copyForClass(UserPilot.class, userpilotDDTOs[i]));

				} else if (userpilotDDTOs[i].getCrud().equals("u")) {
					System.out.println("##########################In the update process####################");
					System.out.println(userpilotDDTOs[i].getCrud());
					NewSqlMapper.getSqlMapClient()
							.update("userpilot.updateUser",
									NewObjectUtil.copyForClass(UserPilot.class, userpilotDDTOs[i]));

				} else if (userpilotDDTOs[i].getCrud().equals("d")) {
					System.out.println("##########################In the delete process####################");
					System.out.println(userpilotDDTOs[i].getCrud());
					NewSqlMapper.getSqlMapClient()
							.update("userpilot.deleteUser",
									NewObjectUtil.copyForClass(UserPilot.class, userpilotDDTOs[i]));

				}

			}

		} catch (Exception e) {
			throw new NewBusinessException("B0000002", "processCode", e);
		}
	}

	public List<UserPilot> getListDept(UserPilotDDTO userpilotDDTO) throws NewBusinessException {
		System.out.println("##########################In the getListDept process####################");

		try {
			return (List<UserPilot>) NewSqlMapper.getSqlMapClient().queryForList("userpilot.getListDept",
					NewObjectUtil.copyForClass(UserPilot.class, userpilotDDTO));
		} catch (Exception e) {
			throw new NewBusinessException("B0000001", "processCode", e);
		}
	}
}
