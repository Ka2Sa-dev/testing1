package comp.comp3800.controller;

import comp.comp3800.dao.LectureRepository;
import comp.comp3800.dao.PollRepository;
import comp.comp3800.model.Lecture;
import comp.comp3800.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("/")
    public String showIndex(Model model) {
        List<Lecture> lectures = lectureRepository.findAll();
        List<Poll> polls = pollRepository.findAll();

        model.addAttribute("courseName", "COMP-3800 Web Applications: Design and Development");
        model.addAttribute("courseDescription", "這是一個線上課程平台，提供講座資料、下載筆記、投票與討論功能。");
        model.addAttribute("lectures", lectures);
        model.addAttribute("polls", polls);

        return "list";
    }
}