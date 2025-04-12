package com.yhdc.account_server.data;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void defaultDataProcessor() {

        Optional<User> userOptional = userRepository.findByUsername("admin");
        if (userOptional.isPresent()) {
            log.info("Default data found!!!");
        } else {
            log.warn("Default data not found...");
            log.warn("Starting default data processor...");

            // Create role
            List<Role> initialRoles = new ArrayList<>();

            Role adminRole = new Role();

            adminRole.setRoleName(RoleType.ROLE_ADMIN.name());
            adminRole.setRoleType(RoleType.ROLE_ADMIN);
            initialRoles.add(adminRole);

            Role managerRole = new Role();
            managerRole.setRoleName(RoleType.ROLE_MANAGER.name());
            managerRole.setRoleType(RoleType.ROLE_MANAGER);
            initialRoles.add(managerRole);

            Role sellerRole = new Role();
            sellerRole.setRoleName(RoleType.ROLE_SELLER.name());
            sellerRole.setRoleType(RoleType.ROLE_SELLER);
            initialRoles.add(sellerRole);

            Role buyerRole = new Role();
            buyerRole.setRoleName(RoleType.ROLE_USER.name());
            buyerRole.setRoleType(RoleType.ROLE_USER);
            initialRoles.add(buyerRole);

            List<Role> roleList = roleRepository.saveAll(initialRoles);
            log.info("Default roles saved!!!");

            // Set role for user
            Set<Role> roleAdminSet = roleList.stream().filter(role -> role.getRoleName().equals(RoleType.ROLE_ADMIN.name())).collect(Collectors.toSet());
            log.info("Admin role: {}", roleAdminSet);
            Set<Role> roleManagerSet = roleList.stream().filter(role -> role.getRoleName().equals(RoleType.ROLE_MANAGER.name())).collect(Collectors.toSet());
            log.info("Manager role: {}", roleAdminSet);

            // Create user
            List<User> initalUserList = new ArrayList<>();

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin1q2w"));
            admin.setFirstName("Admin");
            admin.setLastName("DSL");
            admin.setAddress("26 Fairway street");
            admin.setEmail("admin@admin.com");
            admin.setPhone("010-1234-5678");
            admin.setStatus(UserStatus.ACTIVE);
            admin.setRoleSet(roleAdminSet);
            initalUserList.add(admin);

            User manager = new User();
            manager.setUsername("daniel");
            manager.setPassword(passwordEncoder.encode("daniel1q2w"));
            manager.setFirstName("Daniel");
            manager.setLastName("Choi");
            manager.setAddress("723 Fairway street");
            manager.setEmail("daniel@daniel.com");
            manager.setPhone("010-1234-5678");
            manager.setStatus(UserStatus.ACTIVE);
            manager.setRoleSet(roleManagerSet);
            initalUserList.add(manager);

            userRepository.saveAll(initalUserList);
            log.info("Default accounts saved!!!");
        }
        log.info("Exiting data initializer!!!");
    }

}
