package comp.comp3800.controller;

import comp.comp3800.dao.*;
import comp.comp3800.model.*;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/lecture")
public class LectureController {

    @Resource
    private lectureService lecService;

    @Autowired
    private LectureRepository lectureRepo;

    @Autowired
    private PollRepository pollRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private CourseMaterialRepository courseMaterialRepo;

    @Autowired
    private CommentService commentSer;

    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        List<Lecture> lectures = lectureRepo.findAll();
        List<Poll> polls = pollRepo.findAll();
        List<Comment> lectComments = commentSer.getCommentsForLect();

        model.addAttribute("lectureDatabase", lectures);
        model.addAttribute("pollDatabase", polls);
        model.addAttribute("commentDatabase", lectComments);
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("create", "lectureForm", new Form());
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("lectureForm") Form form) throws IOException {
        lecService.createLecture(
                form.getTitle(),
                form.getSummary(),
                form.getOrder(),
                form.getAttachments()
        );
        return "redirect:/lecture/list";
    }

    @GetMapping("/{id}")
    public String viewLecture(@PathVariable Long id, Model model, Principal principal) {
        Lecture lecture = lectureRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecture not found"));

        List<CourseMaterial> materials = courseMaterialRepo.findByLectureId(id);
        List<Comment> comments = commentRepo.findByTargetTypeAndTargetId("LECTURE", id);

        model.addAttribute("lecture", lecture);
        model.addAttribute("materials", materials);
        model.addAttribute("comments", comments);
        model.addAttribute("isLoggedIn", principal != null);

        return "coursematerial";
    }

    @GetMapping("/download/{materialId}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadMaterial(@PathVariable Long materialId) throws Exception {
        CourseMaterial material = courseMaterialRepo.findById(materialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Path filePath = Paths.get(material.getStoredFilePath());
        org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + material.getOriginalFileName() + "\"")
                .contentType(MediaType.parseMediaType(material.getContentType() != null ? material.getContentType() : "application/octet-stream"))
                .body(resource);
    }

    @PostMapping("/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam String content,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Lecture lecture = lectureRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lecture not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setTargetType(Comment.TargetType.LECTURE);
        comment.setTargetId(id);
        comment.setCreatedAt(java.time.Instant.now());   // ← 修正為 Instant

        User currentUser = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setAuthor(currentUser);

        commentRepo.save(comment);

        return "redirect:/lecture/" + id;
    }

    public static class Form {
        private String title;
        private String summary;
        private int order;
        private List<MultipartFile> attachments;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }

        public int getOrder() { return order; }
        public void setOrder(int order) { this.order = order; }

        public List<MultipartFile> getAttachments() { return attachments; }
        public void setAttachments(List<MultipartFile> attachments) { this.attachments = attachments; }
    }
}