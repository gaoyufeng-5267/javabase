package jp.co.tlzs.weget.repository;

import jp.co.tlzs.weget.entity.Members;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembersRepository extends CrudRepository<Members, String> {
    @Query("select a from Members a " +
            "where a.email = :email and a.leave = false")
    List<Members> findByEmail(@Param("email")String email );
}
