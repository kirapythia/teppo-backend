package fi.espoo.pythia.backend.mgrs;

import org.springframework.stereotype.Component;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import fi.espoo.pythia.backend.transfer.ProjectValue2;

@Component
public class SESManager {

	private String hostAddress = "52.214.196.107";

	public void newVersion(String project, String projectId, String pdfUrl, String xmlUrl) {

		// Replace sender@example.com with your "From" address.
		// This address must be verified with Amazon SES.
		final String FROM = "kakedigibot@gmail.com";

		// Replace recipient@example.com with a "To" address. If your account
		// is still in the sandbox, this address must be verified.
		final String TO = "kakedigi@gmail.com";

		// The configuration set to use for this email. If you do not want to
		// use a
		// configuration set, comment the next line and line 60.
		// static final String CONFIGSET = "ConfigSet";

		// The subject line for the email.
		final String SUBJECT = "New Version in Project " + project + " is waiting for approval ";

		final String HTMLBODY = "<h1>New version in project " + project + "</h1>" + "<p> New version for plan pdf: "
				+ pdfUrl + " </p>" + "<BR><p> xml: " + xmlUrl + "</p>" + " </p>" + "<BR><p> http://" + hostAddress
				+ ":3000/project/" + projectId + "</p>";

		// The email body for recipients with non-HTML email clients.
		final String TEXTBODY = "New version in project " + project + "\n  New version for plan pdf: " + pdfUrl
				+ " </p>" + "<BR><p> xml: " + xmlUrl + "</p>" + "\n  http://" + this.hostAddress + ":3000/project/"
				+ projectId;

		AmazonSimpleEmailService client = authenticate();

		sendEmailRequest(client, TO, FROM, SUBJECT, HTMLBODY, TEXTBODY);
	}

	public void planApproved(String project, String projectId, String plan) {
		// Replace sender@example.com with your "From" address.
		// This address must be verified with Amazon SES.
		final String FROM = "kakedigibot@gmail.com";

		// Replace recipient@example.com with a "To" address. If your account
		// is still in the sandbox, this address must be verified.
		final String TO = "kakedigi@gmail.com";

		// The configuration set to use for this email. If you do not want to
		// use a
		// configuration set, comment the next line and line 60.
		// static final String CONFIGSET = "ConfigSet";

		// The subject line for the email.
		final String SUBJECT = "New Version in Project " + project + " for plan " + plan;

		final String HTMLBODY = "<h1>New version in project " + project + "</h1>" + "<p> New version for plan " + plan
				+ " </p>" + "<BR><p> http://" + this.hostAddress + ":3000/project/" + projectId + "</p>";

		// The email body for recipients with non-HTML email clients.
		final String TEXTBODY = "New version in project " + project + "\n  New version for plan  " + plan
				+ "\n  http://" + this.hostAddress + ":3000/project/" + projectId;

		AmazonSimpleEmailService client = authenticate();

		sendEmailRequest(client, TO, FROM, SUBJECT, HTMLBODY, TEXTBODY);
	}

	public void maintenanceDecision(String project, String projectId) {
		// Replace sender@example.com with your "From" address.
		// This address must be verified with Amazon SES.
		final String FROM = "kakedigibot@gmail.com";

		// Replace recipient@example.com with a "To" address. If your account
		// is still in the sandbox, this address must be verified.
		final String TO = "kakedigi@gmail.com";

		// The configuration set to use for this email. If you do not want to
		// use a
		// configuration set, comment the next line and line 60.
		// static final String CONFIGSET = "ConfigSet";

		// The subject line for the email.
		final String SUBJECT = "The Project " + project + " is finished.";

		final String HTMLBODY = "<h1>The project " + project + "</h1>" + "<p> " + " </p>" + "<BR><p> http://"
				+ this.hostAddress + ":3000/project/" + projectId + "</p>";

		// The email body for recipients with non-HTML email clients.
		final String TEXTBODY = "The project " + project + "\n  " + "\n  http://" + this.hostAddress + ":3000/project/"
				+ projectId;

		AmazonSimpleEmailService client = authenticate();

		sendEmailRequest(client, TO, FROM, SUBJECT, HTMLBODY, TEXTBODY);

	}

	public void commentApproved() {

	}

	public void projectFinished() {

	}

	// -----------------------AUTHENTICATION WITH ENVIRONMENTAL VARIABLES

	public AmazonSimpleEmailService authenticate() {
		try {
			AWSCredentialsProvider awsCProv = new EnvironmentVariableCredentialsProvider();
			awsCProv.getCredentials();
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(awsCProv)
					// Replace US_WEST_2 with the AWS Region you're using for
					// Amazon SES.
					.withRegion(Regions.EU_WEST_1).build();

			return client;
		} catch (Exception ex) {
			System.out.println("Cannot authenticate: " + ex.getMessage());
			return null;
		}
	}

	public boolean sendEmailRequest(AmazonSimpleEmailService client, String TO, String FROM, String SUBJECT,
			String HTMLBODY, String TEXTBODY) {
		try {
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(
							new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM);
			client.sendEmail(request);
			System.out.println("Email sent!");
			return true;
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " + ex.getMessage());
			return false;
		}

	}

}
