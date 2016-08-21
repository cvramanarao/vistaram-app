package com.vistaram.batch.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.vistaram.batch.gmail.utils.FileDataStoreFactories;

public class GmailUtils {
	
	private static Logger logger = LoggerFactory.getLogger(GmailUtils.class);
	
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Gmail API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/vistaram-batch-credentials.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart.json
     */
    private static final Set<String> SCOPES = GmailScopes.all();
        //Arrays.asList(GmailScopes.GMAIL_LABELS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize(String clientSecretFileName) throws IOException {
        // Load client secrets.
        String path = System.getProperty("user.home")+ "/.credentials/"+clientSecretFileName+"-client_secret.json";
        logger.debug(path);
		InputStream in =
            new FileInputStream(path);
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(FileDataStoreFactories.getFileDataStoreFactory(clientSecretFileName))
                .setApprovalPrompt("force")
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static Gmail getGmailService(String clientSecretFileName) throws IOException {
        Credential credential = authorize(clientSecretFileName);
        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    /**
     * List all Messages of the user's mailbox matching the query.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param query String used to filter the Messages listed.
     * @throws IOException
     */
    public static List<Message> listMessagesMatchingQuery(Gmail service, String userId,
        String query) throws IOException {
      ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

      List<Message> messages = new ArrayList<Message>();
      while (response.getMessages() != null) {
        messages.addAll(response.getMessages());
        if (response.getNextPageToken() != null) {
          String pageToken = response.getNextPageToken();
          logger.debug("retreiving pageToken: "+pageToken);
          response = service.users().messages().list(userId).setQ(query)
              .setPageToken(pageToken).execute();
        } else {
          break;
        }
      }

      /*for (Message message : messages) {
        logger.debug(message.toPrettyString());
      }*/

      return messages;
    }

    /**
     * List all Messages of the user's mailbox with labelIds applied.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param labelIds Only return Messages with these labelIds applied.
     * @throws IOException
     */
    public static List<Message> listMessagesWithLabels(Gmail service, String userId,
        List<String> labelIds) throws IOException {
      ListMessagesResponse response = service.users().messages().list(userId)
          .setLabelIds(labelIds).execute();

      List<Message> messages = new ArrayList<Message>();
      while (response.getMessages() != null) {
        messages.addAll(response.getMessages());
        if (response.getNextPageToken() != null) {
          String pageToken = response.getNextPageToken();
          response = service.users().messages().list(userId).setLabelIds(labelIds)
              .setPageToken(pageToken).execute();
        } else {
          break;
        }
      }

      for (Message message : messages) {
        logger.debug(message.toPrettyString());
      }

      return messages;
    }
    
    
    public static String getContent(Gmail service, String userId, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
        	Message msg = service.users().messages().get(userId, message.getId()).execute();
            getPlainTextFromMessageParts(msg.getPayload().getParts(), stringBuilder);
            byte[] bodyBytes = Base64.decodeBase64(stringBuilder.toString());
            String text = new String(bodyBytes, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
           System.err.println("UnsupportedEncoding: " + e.toString());
            return message.getSnippet();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return message.getSnippet();
		}
    }

    private static void getPlainTextFromMessageParts(List<MessagePart> messageParts, StringBuilder stringBuilder) {
        for (MessagePart messagePart : messageParts) {
            if (messagePart.getMimeType().equals("text/plain")) {
                stringBuilder.append(messagePart.getBody().getData());
            }

            if (messagePart.getParts() != null) {
                getPlainTextFromMessageParts(messagePart.getParts(), stringBuilder);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
    	String clientSecretFileName = "vistaramrooms";
        Gmail service = getGmailService(clientSecretFileName);

        // Print the labels in the user's account.
        String user = "me";
        String query = "label:inbox-goibibo from:hotelpartners@goibibo.com after:2016/06/01";
        List<Message> messages = listMessagesMatchingQuery(service, user, query);
        logger.debug("Total messages : "+messages.size());
       
    }

}