package com.bibhu.customer.service;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    public String buildTempPwdHtml(String email, String tempPwd) {

        String html = """   
                <html>
                <head>
                      <meta charset="UTF-8">
                      <meta http-equiv="X-UA-Compatible" content="IE=edge">
                      <meta name="viewport" content="width=device-width, initial-scale=1.0">
                 <style>
                 .container { font-family: Arial, sans-serif; padding: 20px; background: #f9f9f9; }
                 .box { background: #fff; padding: 20px; border-radius: 8px; border: 1px solid #ddd;max-width: 500px; margin: auto; }
                 .password { font: bold 20px Arial; color: #2c3e50; margin-top: 10px; }
                 </style>            
                </head>
                <body>
                 <div class="container">
                    <div class="box">
                      <h2>Your Temporary Password</h2>
                      <p>Hey %s,</p>
                      <p>As requested, here is your temporary password to access your account on <strong>Ecomm@Bibhu</strong>:</p>
                      <p class="password">%s</p>
                      <p>Please change this password through forgot password to ensure your account remains secure.</p>
                      <p>If you did not request this, please ignore this email or contact support immediately.</p>
                      <p>Best regards,<br>Ecomm@Bibhu Support Team</p>
                    </div>
                  </div>                    
                </body>
                </html>
                """.formatted(email, tempPwd);

        return html;
    }


}
