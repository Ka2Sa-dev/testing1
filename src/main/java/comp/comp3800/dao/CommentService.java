package comp.comp3800.dao;

import comp.comp3800.model.Comment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> getCommentsForLect() {
        return commentRepository.findByTargetTypeAndTargetId("LECTURE", null);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsForLecture(Long lectureId) {
        return commentRepository.findByTargetTypeAndTargetId("LECTURE", lectureId);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsForPoll(Long pollId) {
        return commentRepository.findByTargetTypeAndTargetId("POLL", pollId);
    }
}