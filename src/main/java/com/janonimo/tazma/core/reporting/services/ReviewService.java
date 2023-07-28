package com.janonimo.tazma.core.reporting.services;

import com.janonimo.tazma.core.appointment.Appointment;
import com.janonimo.tazma.core.reporting.Review;
import com.janonimo.tazma.core.services.AppointmentRepository;
import com.janonimo.tazma.token.Token;
import com.janonimo.tazma.token.TokenRepository;
import com.janonimo.tazma.user.RoleName;
import com.janonimo.tazma.user.RolePriority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final TokenRepository tokenRepository;
    private final AppointmentRepository appointmentRepository;
    private final ReviewRepository reviewRepository;

    public Review create(String jwt, Review review){
        if(validateUser(jwt, review)){
            if(review.getId() != null)
                review.setCreated(LocalDateTime.now());

            return reviewRepository.save(review);
        }
        return null;
    }

    public void delete(String jwt, Review review){
        if(validateUser(jwt, review))
            reviewRepository.delete(review);
    }

    public Review edit(String jwt, Review review){
        return create(jwt, review);
    }
    private boolean validateUser(String jwt, Review review){
        Token token = Objects.requireNonNull(tokenRepository.findByToken(jwt).orElse(null));
        Appointment appointment = Objects.requireNonNull(appointmentRepository.findById(review.getAppointment().getId())).orElse(null);
        if(appointment != null){
            return Objects.equals(token.getUser().getId(), appointment.getClient().getId());
        }
        return true;
    }

    public List<Review> reviews(String jwt){
        Token token = Objects.requireNonNull(tokenRepository.findByToken(jwt).orElse(null));

        if(token.getUser().getRoles().get(0).getRoleName() == RoleName.CLIENT && token.getUser().getRoles().get(0).getPriority() == RolePriority.MAIN){
            return reviewRepository.findAllByClient(token.getUser().getId());
        }else if(token.getUser().getRoles().get(0).getRoleName() == RoleName.STYLIST && token.getUser().getRoles().get(0).getPriority() == RolePriority.MAIN){
            return reviewRepository.findAllOnStylist(token.getUser().getId());
        }
        return null;
    }
}
