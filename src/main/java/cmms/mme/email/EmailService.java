package cmms.mme.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Configuration
@EnableAsync
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void send(String to, String intro, String subject, String body) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject(intro);
            helper.setFrom("rguig.medamine@gmail.com");
            mailSender.send(message);
        } catch (MessagingException me) {
            LOGGER.error("Email wasn't send", me);
            throw new IllegalStateException("Email couldn't be send");
        }

    }
}
