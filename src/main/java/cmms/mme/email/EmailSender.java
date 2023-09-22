package cmms.mme.email;

public interface EmailSender {

    void send(String to, String intro,String subject, String body);
}
