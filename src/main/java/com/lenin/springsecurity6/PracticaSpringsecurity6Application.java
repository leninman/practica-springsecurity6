package com.lenin.springsecurity6;

import com.lenin.springsecurity6.security.persistence.entities.PermissionEntity;
import com.lenin.springsecurity6.security.persistence.entities.RoleEntity;
import com.lenin.springsecurity6.security.persistence.entities.UserEntity;
import com.lenin.springsecurity6.security.persistence.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PracticaSpringsecurity6Application {

	public static void main(String[] args) {

		SpringApplication.run(PracticaSpringsecurity6Application.class, args);

		//System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}


	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			/* Create PERMISSIONS */
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();

			/* Create ROLES */
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleName("ADMIN")
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleName("USER")
					.permissionList(Set.of(createPermission, readPermission))
					.build();

			RoleEntity roleInvited = RoleEntity.builder()
					.roleName("INVITED")
					.permissionList(Set.of(readPermission))
					.build();

			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleName("DEVELOPER")
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();

			/* CREATE USERS */
			UserEntity userLenin = UserEntity.builder()
					.username("lenin")
					.password("$2a$10$sJ5GY6SbcKGzGGQtV7KUvOi5vxMDrs0CjGQ7XyDXktlb0tnXU8uAu")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userErika = UserEntity.builder()
					.username("erika")
					.password("$2a$10$sJ5GY6SbcKGzGGQtV7KUvOi5vxMDrs0CjGQ7XyDXktlb0tnXU8uAu")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();

			UserEntity userSinai = UserEntity.builder()
					.username("sinai")
					.password("$2a$10$sJ5GY6SbcKGzGGQtV7KUvOi5vxMDrs0CjGQ7XyDXktlb0tnXU8uAu")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleInvited))
					.build();

			UserEntity userYasile = UserEntity.builder()
					.username("yasile")
					.password("$2a$10$sJ5GY6SbcKGzGGQtV7KUvOi5vxMDrs0CjGQ7XyDXktlb0tnXU8uAu")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
					.build();

			userRepository.saveAll(List.of(userLenin, userErika, userSinai, userYasile));
		};

	}



}

