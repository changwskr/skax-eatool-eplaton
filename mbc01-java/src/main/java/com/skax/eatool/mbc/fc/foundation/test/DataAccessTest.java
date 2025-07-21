package com.skax.eatool.mbc.fc.foundation.test;

import com.skax.eatool.mbc.fc.foundation.config.DataAccessFactory;
import com.skax.eatool.mbc.fc.foundation.entity.User;
import com.skax.eatool.mbc.fc.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 데이터 접근 방식 테스트 클래스
 * 
 * 프로그램명: DataAccessTest.java
 * 설명: MyBatis와 JPA 데이터 접근 방식을 테스트하는 클래스
 * 작성일: 2024-01-01
 * 작성자: SKAX Project Team
 * 
 * 주요 기능:
 * - 데이터 접근 방식 확인
 * - 사용자 CRUD 테스트
 * - 프로파일별 동작 확인
 * 
 * @version 1.0
 */
@Component
@Profile("test")
public class DataAccessTest implements CommandLineRunner {

    @Autowired
    private DataAccessFactory dataAccessFactory;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== MBC01 Data Access Test ===");
        System.out.println(dataAccessFactory.getDataAccessInfo());

        // 데이터 접근 방식에 따른 테스트 실행
        if (dataAccessFactory.isJpaEnabled()) {
            testJpaOperations();
        } else if (dataAccessFactory.isMyBatisEnabled()) {
            testMyBatisOperations();
        }

        System.out.println("=== Test Completed ===");
    }

    /**
     * JPA 작업 테스트
     */
    private void testJpaOperations() {
        System.out.println("Testing JPA Operations...");

        // 사용자 생성
        User user1 = new User();
        user1.setUserName("JPA Test User 1");
        user1.setEmail("jpa1@test.com");
        user1.setPhone("010-1234-5678");
        user1.setStatus(User.UserStatus.ACTIVE);

        User savedUser1 = userService.saveUser(user1);
        System.out.println("Created User: " + savedUser1.getUserName() + " (ID: " + savedUser1.getUserId() + ")");

        // 사용자 생성 2
        User user2 = new User();
        user2.setUserName("JPA Test User 2");
        user2.setEmail("jpa2@test.com");
        user2.setPhone("010-8765-4321");
        user2.setStatus(User.UserStatus.ACTIVE);

        User savedUser2 = userService.saveUser(user2);
        System.out.println("Created User: " + savedUser2.getUserName() + " (ID: " + savedUser2.getUserId() + ")");

        // 모든 사용자 조회
        List<User> allUsers = userService.findAllUsers();
        System.out.println("Total Users: " + allUsers.size());

        // 이메일로 사용자 조회
        userService.findUserByEmail("jpa1@test.com")
                .ifPresent(user -> System.out.println("Found User by Email: " + user.getUserName()));

        // 사용자 상태 업데이트
        User updatedUser = userService.updateUserStatus(savedUser1.getUserId(), User.UserStatus.INACTIVE);
        System.out.println("Updated User Status: " + updatedUser.getStatus());

        // 활성 사용자 수 조회
        long activeCount = userService.countActiveUsers();
        System.out.println("Active Users Count: " + activeCount);
    }

    /**
     * MyBatis 작업 테스트
     */
    private void testMyBatisOperations() {
        System.out.println("Testing MyBatis Operations...");
        System.out.println("MyBatis operations would be implemented here...");

        // MyBatis 관련 테스트 코드는 별도로 구현
        // 현재는 JPA만 구현되어 있으므로 메시지만 출력
    }
}