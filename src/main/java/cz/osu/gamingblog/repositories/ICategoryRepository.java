package cz.osu.gamingblog.repositories;

import cz.osu.gamingblog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM Category c " +
            "WHERE c.name IN (:categoryNames)")
    List<Category> retrieveCategoriesByNames(@Param("categoryNames") List<String> categoryNames);
}
