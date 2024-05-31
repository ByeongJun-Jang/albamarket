package proj.albamarcketV2.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.author WHERE c.post.id = :postId AND c.commentStatus = 'ACTIVE' ORDER BY c.createdAt ASC")
    List<Comment> findByPostId(Long postId);

    @Modifying
    @Query("UPDATE Comment c SET c.commentStatus = 'DELETE' WHERE c.id = :id")
    void softDelete(Long id);

}
