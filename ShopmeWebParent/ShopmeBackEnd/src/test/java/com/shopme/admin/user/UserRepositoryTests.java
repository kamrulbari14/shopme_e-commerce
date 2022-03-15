package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreatUser() {
        Role role = entityManager.find(Role.class, 1);
        User user = new User("kamrulbpriyo0523@gmail.com", "hello123", "Kamrul", "Bari");
        user.addRole(role);
        User savedUser = repo.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreatNewUserWithTwoRoles() {
        User user = new User("hasan123@gmail.com", "hello123", "Jahid", "Hasan");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        user.addRole(roleEditor);
        user.addRole(roleAssistant);
        User savedUser = repo.save(user);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListOfAllUser() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User user = repo.findById(1).get();
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("helloo");
        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles() {
        User user = repo.findById(2).get();
        user.getRoles().remove(new Role(3));
        user.addRole(new Role(2));
        repo.save(user);
    }

    @Test
    public void testDeleteUserById() {
        Integer userId = 2;
        repo.deleteById(userId);

    }
}
