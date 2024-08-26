package zw.co.fasoft.utils;
/**
 * @author Fasoft
 * @date 2/May/2024
 */
public class Message {


    public static final String CONTRIBUTION_SUBMITTED_MESSAGE =
            "Thank you for contributing to the TCFL Online Resource Hub. \n We have received your submission, titled \"{resourceTitle}\", " +
                    "and it is currently under review by our administrators.\n" +
                    "We appreciate your effort in sharing valuable knowledge with our community. \n" +
                    "Once your resource has been reviewed and approved, it will be made available on the platform.\n" +
                    "You will be notified via email once the review process is complete. If you have any questions or need further assistance, please do not hesitate to contact our support team.\n" +
                    "\n" +
                    "Thank you for your contribution!\n" +
                    "\n" +
                    "Best Regards,\n" +
                    "TCFL Online Resource Hub Administration Team";
    public static final String CONTRIBUTION_REJECTED_MESSAGE =
    "We regret to inform you that your recent submission titled {resourceTitle} to the TCFL Online Resource Hub has not been approved.\n" +
            "Reason for Rejection: {reason}\n" +
            "\n" +
            "We appreciate the effort you put into your contribution, but unfortunately, it does not meet the current criteria for publication on our platform.\n" +
            "\n" +
            "If you would like to revise and resubmit your resource, we encourage you to address the feedback provided above. \n" +
            "Our goal is to maintain the highest quality of content for our users, and we hope to see a revised submission from you soon.\n" +
            "\n" +
            "Should you have any questions or need further clarification, please do not hesitate to reach out to our support team.\n" +
            "\n" +
            "Thank you for your understanding and continued contributions to our platform.\n" +
            "\n" +
            "Best Regards,\n" +
            "TCFL Online Resource Hub Review Team";

    public static final String CONTRIBUTION_APPROVED_MESSAGE =
            "We are pleased to inform you that your submission, titled \"{resourceTitle}\", \n" +
                    "has been successfully reviewed and approved for inclusion in the TCFL Online Resource Hub.\n" +
                    "Your contribution is now available for the entire TCFL community to access and benefit from. We greatly appreciate your effort in enhancing our knowledge base and sharing.\n" +
                    "\n" +
                    "If you have more resources to contribute or any questions, feel free to reach out to our support team. We look forward to your continued contributions!\n" +
                    "\n" +
                    "Thank you once again for your valuable contribution.\n" +
                    "\n" +
                    "Best Regards,\n" +
                    "TCFL Online Resource Hub Administration Team";
    public static final String ADMIN_NEW_ACADEMIC_RESOURCE_MESSAGE =
            "We would like to inform you that {count} new academic resource(s) has been added to the Online Resource Hub.\n" +
                    " This resource is currently pending your review and approval.\n";

    public static final String NEW_APPROVED_RESOURCE_MESSAGE =
            "We are excited to announce that a new academic resource has just been approved and added to the TCFL Online Resource Hub!\n" +
                    "New resource details:\n" +
                    "Title: {title}\n" +
                    "Description: {description}\n" +
                    "Access it here: {resource-link}\n" +
                    "Author: {contributor-name}\n" +
                    "This resource is now available for you to explore and utilize. Whether you’re looking to enhance your knowledge or seeking reference materials, this addition is sure to be a valuable asset.\n" +
                    "\n" +
                    "Stay tuned for more updates as we continue to expand our repository with high-quality academic resources.\n" +
                    "\n" +
                    "Happy learning!\n" +
                    "\n" +
                    "Best Regards,\n" +
                    "TCFL Online Resource Hub Team";
    public static final String USER_ACCOUNT_CREATION_MESSAGE =
            "We are excited to welcome you to our TCFL Online Resource Hub as a {role}.\n" +
                    "Your login credentials are provided below:\n\n" +
                    "Username: {username}, Password: {password}.\n\n" +
                    "Please use these credentials to access the online resource hub and explore the resources available to you.\n" +
                    "If you have any questions or need assistance, our support team is here to help.\n\n" +
                    "{login-message}\n" +
                    "Best Regards,\n" +
                    "The Online Resource Hub Team";


    public static final String SUCCESSFUL_UNSUBSCRIPTION_MESSAGE =
            "We're sorry to hear that you've decided to unsubscribe from INSTALIPA.\n" +
                    "Your feedback is important to us, and we'd like to understand how we can improve.\n" +
                    "If there's anything specific that led to this decision, please let us know.\n" +
                    "Thank you for being a part of INSTALIPA. We appreciate your time with us.\n" +
                    "If you ever decide to give us another chance, we'll be here to assist you.\n" +
                    "Best regards,\n" +
                    "INSTALIPA Administration Team";

    public static final String ACCOUNT_DEACTIVATION_MESSAGE = "We regret to inform you that your INSTALIPA account has been deactivated.\n" +
            "For any inquiries or assistance, please contact our support team.\n" +
            "We apologize for any inconvenience caused.";

    public static final String ACCOUNT_ACTIVATION_MESSAGE = "We are pleased to inform you that your INSTALIPA account has been successfully activated.\n" +
            "You can now enjoy all the features and benefits of our platform.\n" +
            "Should you have any questions or need assistance, feel free to contact our support team.\n" +
            "Thank you for choosing INSTALIPA!";

    public static final String PASSWORD_RESET_NOTIFICATION = "We are pleased to inform you that the password for your INSTALIPA account has been successfully reset.\n" +
            "You can now log in using your new password.\n" +
            "If you did not request a password reset, please contact our support team immediately.\n" +
            "Thank you for using INSTALIPA!";


    public static final String CLIENT_CREATION_MESSAGE =
            "We extend our warmest gratitude for choosing INSTALIPA and for creating your account. \n  "+
                    "The INSTALIPA account has been created successfully. \n" +
                    " Please log in using your credentials on the mobile app to start using INSTALIPA. \n" +
                    "Thank you once again for entrusting INSTALIPA with your financial needs."+
                    "Regards,\n" + "INSTALIPA Adminstration Team";
    public static String ACCOUNT_CREATION_MESSAGE =
            "We are pleased to inform you that your INSTALIPA account has been successfully created as a MERCHANT.\n\n" +
                    "Your login credentials are as follows:\n" +
                    "Username: {username}\n" + "Password: {password}\n\n"+
                    "Click here to login {messageLink}.\n\n"
                    + "Regards,\n" + "INSTALIPA Team";

    public static String ADMIN_LOGIN_LINK_MESSAGE =
            "Click here to login {epay-admin-login-link}.\n\n";

    public static String MERCHANT_LOGIN_LINK =
            "Click here to login {epay-merchant-login-link}";

    public static String merchantOnboardAction = "Merchant Onboard";
    public static String clientOnboardAction = "Client Onboard";

    public static String getAccountCreationMessage() {
        return ACCOUNT_CREATION_MESSAGE;
    }

    public static void setAccountCreationMessage(String accountCreationMessage) {
        ACCOUNT_CREATION_MESSAGE = accountCreationMessage;
    }
}