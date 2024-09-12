package vn.edu.likelion.DemoAuthJWT.common.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import vn.edu.likelion.DemoAuthJWT.repositories.UserRepository;

/**
 * EmailUtil -
 *
 * @param
 * @return
 * @throws
 */
@Component
public class EmailUtil {


    private final JavaMailSender javaMailSender;

    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendNewPasswordEmail(String email, String newPassword) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Mật khẩu mới của bạn");

        String emailContent = """
    <!DOCTYPE html>
    <html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mật khẩu mới của bạn</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                line-height: 1.6;
                color: #333;
                max-width: 600px;
                margin: 0 auto;
                padding: 20px;
            }
            .container {
                background-color: #f9f9f9;
                border: 1px solid #ddd;
                border-radius: 5px;
                padding: 20px;
            }
            h1 {
                color: #0066cc;
            }
            .password {
                font-size: 18px;
                font-weight: bold;
                color: #0066cc;
                margin: 20px 0;
                padding: 10px;
                background-color: #e6f2ff;
                border: 1px solid #0066cc;
                border-radius: 5px;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Mật khẩu mới của bạn</h1>
            <p>Xin chào,</p>
            <p>Theo yêu cầu của bạn, chúng tôi đã tạo một mật khẩu mới cho tài khoản của bạn. Mật khẩu mới của bạn là:</p>
            <div class="password">%s</div>
            <p>Vui lòng đăng nhập và thay đổi mật khẩu này ngay lập tức.</p>
            <p><strong>Lưu ý quan trọng về bảo mật:</strong> Đừng chia sẻ mật khẩu này với bất kỳ ai. Nhân viên của chúng tôi sẽ không bao giờ yêu cầu mật khẩu của bạn.</p>
            <p>Nếu bạn không yêu cầu mật khẩu mới, vui lòng liên hệ với chúng tôi ngay lập tức.</p>
            <p>Trân trọng,<br>Đội ngũ hỗ trợ của chúng tôi</p>
        </div>
    </body>
    </html>
    """.formatted(newPassword);

        mimeMessageHelper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }
}
