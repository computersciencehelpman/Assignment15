package com.coderscampus.Assignment15.controller;

import com.coderscampus.Assignment15.domain.CallOptionRecommendation;
import com.coderscampus.Assignment15.domain.Comment;
import com.coderscampus.Assignment15.repository.CallOptionRecommendationRepository;
import com.coderscampus.Assignment15.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/calls")
public class CallOptionController {

    @Autowired
    private CallOptionRecommendationRepository callRepo;
    
    @Autowired
    private CommentRepository commentRepo;

    @GetMapping
    public String listCalls(Model model) {
        List<CallOptionRecommendation> calls = callRepo.findAllByOrderBySubmittedAtDesc();
        model.addAttribute("callRecommendations", calls);
        return "calls"; 
    }
    
    @GetMapping("/{id}")
    public String getCallDetail(@PathVariable Long id, Model model) {
    	Optional<CallOptionRecommendation> callOpt = callRepo.findById(id);
        if (callOpt.isPresent()) {
            CallOptionRecommendation call = callOpt.get();
            List<Comment> comments = commentRepo.findByCallOptionIdOrderByCreatedAtDesc(id);  // ✅ Use findBy... not findTopBy...
            model.addAttribute("call", call);
            model.addAttribute("comments", comments);
            model.addAttribute("newComment", new Comment());
            return "callDetail";
        } else {
            return "redirect:/calls";
        }
    }

    
    @PostMapping("/{id}/comments")
    public String submitComment(@PathVariable Long id,
                                @ModelAttribute Comment newComment,
                                @AuthenticationPrincipal Object principal) {
        newComment.setCallOptionId(id);
        newComment.setCreatedAt(LocalDateTime.now());

        // Remove any unrelated foreign key fields to avoid cross-relation contamination
        newComment.setRecommendation(null); // from stock
        newComment.setPutOptionId(null);
        newComment.setBitcoinRecommendationId(null);
        newComment.setEthereumRecommendationId(null);
        newComment.setSolanaRecommendationId(null);
        newComment.setCardanoRecommendationId(null);
        newComment.setPolygonRecommendationId(null);
        newComment.setOtherBlockchainsRecommendationId(null);
        newComment.setBitcoinNftRecommendationId(null);
        newComment.setEthereumNftRecommendationId(null);
        newComment.setSolanaNftRecommendationId(null);
        newComment.setCardanoNftRecommendationId(null);
        newComment.setPolygonNftRecommendationId(null);
        newComment.setOtherNftBlockchainsRecommendationId(null);
        newComment.setResidentialRecommendationId(null);
        newComment.setCommercialRecommendationId(null);
        newComment.setReitRecommendationId(null);
        newComment.setLandRecommendationId(null);
        newComment.setCollectableRecommendationId(null);

        if (principal instanceof OAuth2User oauthUser) {
            newComment.setAuthor(oauthUser.getAttribute("email"));
        } else if (principal instanceof UserDetails userDetails) {
            newComment.setAuthor(userDetails.getUsername());
        }

        newComment.setId(null); 
        commentRepo.save(newComment);
        return "redirect:/calls/" + id;
    }


    @GetMapping("/new")
    public String showCallForm(Model model) {
        model.addAttribute("callOption", new CallOptionRecommendation());
        return "callForm"; 
    }

    @PostMapping("/submit")
    public String submitCall(@ModelAttribute CallOptionRecommendation callOption,
                             @AuthenticationPrincipal Object principal) {
        callOption.setSubmittedAt(LocalDateTime.now());
        callRepo.save(callOption);
        return "redirect:/calls";
    }

}
