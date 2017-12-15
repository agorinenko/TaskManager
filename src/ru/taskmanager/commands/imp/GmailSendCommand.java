package ru.taskmanager.commands.imp;

import ru.taskmanager.args.params.KeyValueParam;
import ru.taskmanager.commands.CommandResult;
import ru.taskmanager.commands.SafetyCommand;
import ru.taskmanager.commands.SuccessResult;
import ru.taskmanager.errors.CommandException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class GmailSendCommand extends SafetyCommand {
    @Override
    public CommandResult safetyExecute(List<KeyValueParam> params) throws CommandException {

        String userName = GetStringParam(params, "from");
        String to = GetStringParam(params, "to");
        String password = GetStringParam(params, "pass");
        String subj = GetStringParam(params, "subj");
        String body = GetStringParam(params, "body");


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subj);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new CommandException(e.getMessage());
        }

        SuccessResult result = new SuccessResult();
        result.setMessage("The send mail from \"" + userName + "\"  was successful" + System.lineSeparator());
        return result;
    }
}
