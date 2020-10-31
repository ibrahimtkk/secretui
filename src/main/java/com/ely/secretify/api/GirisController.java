package com.ely.secretify.api;

import com.ely.secretify.entity.UserImageEntity;
import com.ely.secretify.repository.UserImageRepository;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.*;
import org.brunocvcunha.instagram4j.requests.payload.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

@CrossOrigin(origins = { "http://localhost:8081" })
@RestController
@RequestMapping("/api/")
public class GirisController {

    private static Logger log = LoggerFactory.getLogger(GirisController.class);
    private List followersUsers;
    private List followingUsers;
    private Instagram4j instagram4j;
    private Instagram4j instagram;
    private InstagramLoginResult instagramLoginResult;
    private List<UserImageEntity> userList;

    @Autowired
    UserImageRepository userImageRepository;

    private UserImageEntity userImageEntity;

    File file;
    private String followingURL;
    private URL url;
    private byte[] buf;

    private InstagramGetUserFollowersResult followersResult;
    private InstagramGetUserFollowersResult followingRequest;
    private InstagramUser instagramUser;


    private void deneme(Instagram4j instagram4j, InstagramLoginResult instagramLoginResult) throws IOException {
        followersResult = new InstagramGetUserFollowersResult();
        instagramUser = new InstagramUser();
        String username;
        long pk;

        InstagramSearchUsernameResult followingResult;
        InstagramSearchUsernameResult userResult = instagram4j.sendRequest(new InstagramSearchUsernameRequest(instagram4j.getUsername()));
        followersResult = instagram4j.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
        followingRequest = instagram4j.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
        followingUsers = followingRequest.getUsers();
        followersUsers = followersResult.getUsers();
        log.info("followerList: {}", followersResult);
        log.info("followingList: {}", followingUsers.get(0));
        username = followersResult.getUsers().get(1).username;
        instagramUser.setUsername(username);

        for (int i=0; i<followingUsers.size(); i++) {
        //for (Object user : followingUsers){
            pk = followingRequest.getUsers().get(i).getPk();
            InstagramFeedResult instagramFeedResult = instagram4j.sendRequest(new InstagramUserFeedRequest(pk));
            userImageEntity = new UserImageEntity();

            for (InstagramFeedItem item : instagramFeedResult.getItems()) {
                try {
                    log.info("item.gettah: {}", item.getImage_versions2().getCandidates());
                    userImageEntity.setUsername(item.getUser().username);
                    userImageEntity.setImageFormat("JPEG");
                    for (int j = 0; j < item.getImage_versions2().candidates.size(); j++) {
                        userImageEntity.setImageURL(item.getImage_versions2().candidates.get(j).url);
                        log.info(":::{}, {}, {}, {}, {}", i, j, userImageEntity.getUsername().length(), userImageEntity.getImageURL().length(), userImageEntity.getImageFormat().length());
                        userImageRepository.save(userImageEntity);
                    }
                } catch (NullPointerException e) {
                }
            }
        }
    }

    @RequestMapping(value = "download/{loginUsername}", method = RequestMethod.POST)
    public ResponseEntity<Void> downloadPhotos(@PathVariable String loginUsername) throws IOException {
        followersResult = new InstagramGetUserFollowersResult();
        instagramUser = new InstagramUser();
        String username;
        long pk;
        log.info("girdi");

        InstagramSearchUsernameResult followingResult;
        InstagramSearchUsernameResult userResult = instagram4j.sendRequest(new InstagramSearchUsernameRequest(instagram4j.getUsername()));
        followersResult = instagram4j.sendRequest(new InstagramGetUserFollowersRequest(userResult.getUser().getPk()));
        followingRequest = instagram4j.sendRequest(new InstagramGetUserFollowingRequest(userResult.getUser().getPk()));
        followingUsers = followingRequest.getUsers();
        followersUsers = followersResult.getUsers();
        username = followersResult.getUsers().get(1).username;
        instagramUser.setUsername(username);

        for (int i=0; i<followingUsers.size(); i++) {
            String followingUsername = followingRequest.getUsers().get(i).username;
            pk = followingRequest.getUsers().get(i).getPk();
            InstagramFeedResult instagramFeedResult = instagram4j.sendRequest(new InstagramUserFeedRequest(pk));
            userImageEntity = new UserImageEntity();
            /* check following user directory is present */
            file = new File(followingRequest.getUsers().get(i).username);
            if (!file.exists()){
                file.mkdir();
            }
            int k=0;
            for (InstagramFeedItem item : instagramFeedResult.getItems()) {
                try {
                    k++;
//                  userImageEntity.setUsername(item.getUser().username);
//                  userImageEntity.setImageFormat("jpg");
                    if (item.getImage_versions2().candidates.size() < 20) {
                        for (int j = 0; j < item.getImage_versions2().candidates.size(); j++) {
//                      userImageEntity.setImageURL(item.getImage_versions2().candidates.get(j).url);
//                      log.info(":::{}, {}, {}, {}, {}", i, j, userImageEntity.getUsername().length(), userImageEntity.getImageURL().length(), userImageEntity.getImageFormat().length());
//                      userImageRepository.save(userImageEntity);
                            followingURL = item.getImage_versions2().candidates.get(j).url;
                            this.downloadImageFromURL(followingURL, followingUsername, k);
                        }
                    }
                } catch (NullPointerException e) {
                }
            }
        }




//        List<String> photoURLs = userImageRepository.getPhotosByUsername(username);
//
//        for (String photoURL: photoURLs) {
//
//            url = new URL(photoURL);
//            InputStream in = new BufferedInputStream(url.openStream());
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            byte[] buf = new byte[1024];
//            int n = 0;
//            while (-1 != (n = in.read(buf))) {
//                out.write(buf, 0, n);
//            }
//            out.close();
//            in.close();
//            byte[] response = out.toByteArray();
//
//            FileOutputStream fos = new FileOutputStream(username+ "/" + photoURL + ".jpg");
//            fos.write(response);
//            fos.close();
//        }

//        log.info("usernamee: {}", username);
//        userList = (List<UserImageEntity>) userImageRepository.findAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void downloadImageFromURL(String photoURL, String followingUsername, int i) throws IOException {
        URL url = new URL(photoURL);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = new FileOutputStream( "images/"+ followingUsername + "/" + i + ".jpg");
        fos.write(response);
        fos.close();
    }


    @RequestMapping(value = "photos/{username}", method = RequestMethod.GET)
    public ResponseEntity<List<UserImageEntity>> getPhotos(@PathVariable String username){
        log.info("usernamee: {}", username);
        userList = (List<UserImageEntity>) userImageRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    @RequestMapping(value = "logins", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> login(@RequestBody LoginWrapper data) throws IOException {
        instagram = Instagram4j.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .build();
        instagram.setup();

        // Check login response
        this.instagramLoginResult = instagram.login();

        try {
            String result = checkInstagramLoginResult(instagram, instagramLoginResult, true);
            if (result.compareTo("success") == 0){
                log.info("state: {}", result);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                log.info("errör: {}", result);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Check login response.
     *
     * @param instagram4j
     * @param instagramLoginResult
     * @param doReAuthentication
     * @throws Exception
     */
    public String checkInstagramLoginResult(Instagram4j instagram4j, InstagramLoginResult instagramLoginResult,
                                                 boolean doReAuthentication) throws Exception {
        if (Objects.equals(instagramLoginResult.getStatus(), "ok")
                && instagramLoginResult.getLogged_in_user() != null) {
            // Login success
            System.out.println("■Login success.");
            this.instagram4j = instagram4j;
            this.instagramLoginResult = instagramLoginResult;
            //deneme(instagram4j, instagramLoginResult);
            return "success";
        } else if (Objects.equals(instagramLoginResult.getStatus(), "ok")) {
            // Logged in user not exists
            System.out.println("■Logged in user not exists.");

            // TODO
        } else if (Objects.equals(instagramLoginResult.getStatus(), "fail")) {
            // Login failed

            // Check error type
            if (Objects.equals(instagramLoginResult.getError_type(), "checkpoint_challenge_required")) {
                System.out.println("■Challenge URL : " + instagramLoginResult.getChallenge().getUrl());

                // If do re-authentication
                if (doReAuthentication) {
                    // Get challenge URL
                    String challengeUrl = instagramLoginResult.getChallenge().getApi_path().substring(1);

                    // Reset
                    String resetUrl = challengeUrl.replace("challenge", "challenge/reset");
                    InstagramGetChallengeResult getChallengeResult = instagram4j
                            .sendRequest(new InstagramResetChallengeRequest(resetUrl));
                    System.out.println("■Reset result : " + getChallengeResult);

                    // if "close"
                    if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Get challenge response
                        getChallengeResult = instagram4j
                                .sendRequest(new InstagramGetChallengeRequest(challengeUrl));
                        System.out.println("■Challenge response : " + getChallengeResult);
                    }

                    // Check step name
                    if (Objects.equals(getChallengeResult.getStep_name(), "select_verify_method")) {
                        // Select verify method

                        // Get select verify method result
                        InstagramSelectVerifyMethodResult postChallengeResult = instagram4j
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));

                        // If "close"
                        if (Objects.equals(postChallengeResult.getAction(), "close")) {
                            // Challenge was closed
                            System.out.println("■Challenge was closed : " + postChallengeResult);

                            // End
                            return "fail";
                        }

                        // Security code has been sent
                        System.out.println("■Security code has been sent : " + postChallengeResult);

                        // Please input Security code
                        System.out.println("Please input Security code");
                        String securityCode = null;
                        try (Scanner scanner = new Scanner(System.in)) {
                                securityCode = scanner.nextLine();
                        }

                        // Send security code
                        InstagramLoginResult securityCodeInstagramLoginResult = instagram4j
                                .sendRequest(new InstagramSendSecurityCodeRequest(challengeUrl, securityCode));

                        // Check login response
                        checkInstagramLoginResult(instagram4j, securityCodeInstagramLoginResult, false);
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_email")) {
                        // Security code has been sent to E-mail
                        System.out.println("■Security code has been sent to E-mail");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "verify_code")) {
                        // Security code has been sent to phone
                        System.out.println("■Security code has been sent to phone");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "submit_phone")) {
                        // Unknown
                        System.out.println("■Unknown.");

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "delta_login_review")) {
                        // Maybe showing security confirmation view?
                        System.out.println("■Maybe showing security confirmation view?");

                        // FIXME Send request with choice
                        InstagramSelectVerifyMethodResult instagramSelectVerifyMethodResult = instagram4j
                                .sendRequest(new InstagramSelectVerifyMethodRequest(challengeUrl,
                                        getChallengeResult.getStep_data().getChoice()));
                        System.out.println(instagramSelectVerifyMethodResult);

                        // TODO
                    } else if (Objects.equals(getChallengeResult.getStep_name(), "change_password")) {
                        // Change password needed
                        System.out.println("■Change password needed.");
                    } else if (Objects.equals(getChallengeResult.getAction(), "close")) {
                        // Maybe already challenge closed at other device
                        System.out.println("■Maybe already challenge closed at other device.");

                        // TODO
                    } else {
                        // TODO Other
                        System.out.println("■Other.");
                    }
                }
            } else if (Objects.equals(instagramLoginResult.getError_type(), "bad_password")) {
                System.out.println("■Bad password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "rate_limit_error")) {
                System.out.println("■Too many request.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "invalid_parameters")) {
                System.out.println("■Invalid parameter.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "needs_upgrade")) {
                System.out.println("■App upgrade needed.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "sentry_block")) {
                System.out.println("■Sentry block.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "inactive user")) {
                System.out.println("■Inactive user.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getError_type(), "unusable_password")) {
                System.out.println("■Unusable password.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getTwo_factor_info() != null) {
                System.out.println("■Two factor authentication needed.");
                System.out.println(instagramLoginResult.getMessage());

                // If do re-authentication
                if (doReAuthentication) {
                    // Two factor authentication
                    InstagramLoginResult twoFactorInstagramLoginResult = instagram4j.login("351896");

                    // Check login result
                    checkInstagramLoginResult(instagram4j, twoFactorInstagramLoginResult, false);
                }
            } else if (Objects.equals(instagramLoginResult.getMessage(),
                    "Please check the code we sent you and try again.")) {
                System.out.println("■Invalid security code.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (Objects.equals(instagramLoginResult.getMessage(),
                    "This code has expired. Go back to request a new one.")) {
                System.out.println("■Security code has expired.");
                System.out.println(instagramLoginResult.getMessage());
            } else if (instagramLoginResult.getChallenge() != null) {
                System.out.println("■Challenge : " + instagramLoginResult.getChallenge());
                System.out.println(instagramLoginResult.getMessage());
                if (instagramLoginResult.getChallenge().getLock() != null
                        && instagramLoginResult.getChallenge().getLock()) {
                    // Login locked
                    System.out.println("■Login locked.");
                    return "fail";
                } else {
                    // Logged in user exists, or maybe showing security code
                    // view on other device
                    System.out.println("■Logged in user exists, or maybe showing security code view on other device.");
                    return "fail";
                }
            } else {
                System.out.println("■Unknown error : " + instagramLoginResult.getError_type());
                System.out.println(instagramLoginResult.getMessage());
                return "fail";
            }
        } else {
            System.out.println("■Unknown status : " + instagramLoginResult.getStatus());
            System.out.println(instagramLoginResult.getMessage());
            return "fail";
        }
        return "fail";
    }


    private static class UsernameJSON{
        private String username;

        public UsernameJSON() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    private static class LoginWrapper {
        private String username;
        private String password;

        public LoginWrapper(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public LoginWrapper() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
