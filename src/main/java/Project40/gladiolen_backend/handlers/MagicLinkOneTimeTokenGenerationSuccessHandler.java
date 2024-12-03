package Project40.gladiolen_backend.handlers;


import Project40.gladiolen_backend.services.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class MagicLinkOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

    private final EmailService emailService;

    public MagicLinkOneTimeTokenGenerationSuccessHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
                .replacePath(request.getContextPath())
                .replaceQuery(null)
                .fragment(null)
                .path("/login/ott")
                .queryParam("token", oneTimeToken.getTokenValue());
        String magicLink = builder.toUriString();
        String email = getUserEmail();
        emailService.sendEmail(email, "Your Spring Security One Time Token", "Use the following link to sign in into the application: " + magicLink);

        // Redirect to your custom Next.js page
        response.sendRedirect("http://localhost:3000/ott/sent");
    }

    private String getUserEmail() {
        // ...
        return "nicolas.van.dyck@telenet.be";
    }

}