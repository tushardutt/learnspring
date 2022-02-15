package com.practice.learnspring.services;

import com.practice.learnspring.entities.User;
import com.practice.learnspring.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public List<User> findPaginatedUser(int pageNo, int pageSize){
        Pageable paging;
        if (pageSize == 0) {
            paging = Pageable.unpaged();
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by("uname").ascending());
        }
        Page<User> pagedResult = userRepo.findAll(paging);
        return pagedResult.toList();
    }
    public User getUser(Long id) {
        return userRepo.findById(id).get();
    }

    public User addUser(User user) {
        return userRepo.save(user);
    }
}
