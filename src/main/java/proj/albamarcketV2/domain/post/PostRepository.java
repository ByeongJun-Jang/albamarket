package proj.albamarcketV2.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.author WHERE p.postStatus = 'ACTIVE' order by p.createdAt desc")
    List<Post> findAllWithAuthor();

    @Modifying
    @Query("UPDATE Post p SET p.postStatus = 'DELETE' WHERE p.id = :id")
    void softDelete(Long id);
}
