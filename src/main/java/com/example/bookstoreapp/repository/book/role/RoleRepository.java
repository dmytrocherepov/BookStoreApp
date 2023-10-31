package com.example.bookstoreapp.repository.book.role;

import com.example.bookstoreapp.model.Role;
import com.example.bookstoreapp.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(RoleName name);
}
