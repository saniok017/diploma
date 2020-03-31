package org.arhor.diploma.repository;

import org.arhor.diploma.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.arhor.diploma.Constants.CACHE_USERS;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

  @Cacheable(cacheNames = CACHE_USERS, key = "#username")
  @Query("SELECT u FROM User u WHERE u.username = :username")
  Optional<User> findByUsername(String username);

  @Override
  @CacheEvict(value = CACHE_USERS, key = "#user.username")
  void delete(@NonNull User user);
}