package org.arhor.diploma.repository;

import org.arhor.diploma.domain.Account;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.arhor.diploma.Constants.CACHE_ACCOUNT_BY_ID;
import static org.arhor.diploma.Constants.CACHE_ACCOUNT_BY_USERNAME;

@Repository
public interface AccountRepository extends BaseRepository<Account, Long> {

  @Transactional(readOnly = true)
  @Query("SELECT a FROM Account a WHERE a.deleted = false AND a.username = :username")
  @Cacheable(cacheNames = CACHE_ACCOUNT_BY_USERNAME, key = "#username")
  Optional<Account> findByUsername(String username);

  @Override
  @Modifying
  @Transactional
  @Query("UPDATE Account a SET a.deleted = true WHERE a.id = :id")
  @CacheEvict(cacheNames = CACHE_ACCOUNT_BY_ID, key = "#id")
  void deleteById(@NonNull Long id);

  @Override
  @Transactional
  @CacheEvict(cacheNames = CACHE_ACCOUNT_BY_USERNAME, key = "#account.username")
  default void delete(Account account) {
    deleteById(account.getId());
  }
}