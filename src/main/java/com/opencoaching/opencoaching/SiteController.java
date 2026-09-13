package com.opencoaching.opencoaching;

import com.opencoaching.opencoaching.modelos.CoachingSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class SiteController {

    private final CoachingSessionRepository sessionRepository;
    private final CoachesRepository coachesRepository;

    public SiteController(CoachingSessionRepository sessionRepository, CoachesRepository coachesRepository) {
        this.sessionRepository = sessionRepository;
        this.coachesRepository = coachesRepository;
    }
    private List<String> getCoachesList() {
    return coachesRepository.findAll().stream()
            .map(coach -> coach.getDisplayName())
            .toList();
}

    @GetMapping("/")
    public String home() {
        return "redirect:/coaching";
    }

    @GetMapping("/coaching")
    public String coaching(Model model) {
        model.addAttribute("coaches", getCoachesList());
        return "coaching";
    }

    @GetMapping("/prices")
    public String prices() {
        return "prices";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/schedule")
    public String schedule(Model model) {
        model.addAttribute("sessions", sessionRepository.findAll());
        return "schedule";
    }

    @GetMapping("/schedule-edit/{id}")
    public String editSchedule(@PathVariable Long id, Model model) {
        Optional<CoachingSession> existCoachingSession = sessionRepository.findById(id);

        if (existCoachingSession.isEmpty()) {
            return "redirect:/schedule";
        }

        model.addAttribute("sessions", sessionRepository.findAll());
        model.addAttribute("coaches", getCoachesList());
        model.addAttribute("coachingSession", existCoachingSession.get());
        
        return "schedule";
    }

    @PostMapping("/schedule-coaching")
    public String afterScheduleCoaching(@RequestParam String rank, @RequestParam int hours, @RequestParam String coach) {
        sessionRepository.save(new CoachingSession(rank, hours, coach));
        return "redirect:/schedule";
    }

    @PostMapping("/schedule-coaching/{id}")
    public String postAfterScheduleCoaching(@PathVariable Long id, @RequestParam String rank, @RequestParam int hours, @RequestParam String coach) {
        Optional<CoachingSession> sessionToEdit = sessionRepository.findById(id);

        if (sessionToEdit.isPresent()) {
            CoachingSession session = sessionToEdit.get();
            session.setRank(rank);
            session.setHours(hours);
            session.setCoach(coach);
            sessionRepository.save(session);
        }
        return "redirect:/schedule";
    }

    @PostMapping("/schedule-remove/{id}")
    public String postScheduleRemove(@PathVariable Long id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
        }
        return "redirect:/schedule";
    }

    @GetMapping("/coaches")
    public String manageCoaches(Model model) {
        model.addAttribute("coachesList", coachesRepository.findAll());
        return "coaches";
    }

    @PostMapping("/coaches-add")
    public String addCoach(@RequestParam String name, @RequestParam String rank) {
        coachesRepository.save(new Coaches(name, rank));
        return "redirect:/coaches";
    }

    @PostMapping("/coaches-delete/{id}")
    public String deleteCoach(@PathVariable Long id) {
        if (coachesRepository.existsById(id)) {
            coachesRepository.deleteById(id);
        }
        return "redirect:/coaches";
    }
}