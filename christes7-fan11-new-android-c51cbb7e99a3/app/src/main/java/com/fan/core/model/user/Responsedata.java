
package com.fan.core.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mohit.soni @ 08-Oct-19.
 */
public class Responsedata {

    @SerializedName("current_balance")
    @Expose
    private String currentBalance;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("google_id")
    @Expose
    private String googleId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("postal_code")
    @Expose
    private String postalCode;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("refer_by")
    @Expose
    private String referBy;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("upload_id")
    @Expose
    private String uploadId;
    @SerializedName("upload_signature")
    @Expose
    private String uploadSignature;
    @SerializedName("my_balance")
    @Expose
    private String myBalance;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    private String profile_local_path;
    private String upload_id_local;
    private String upload_signature_local;
    private boolean rememberMe;

    public  String city_name;
    public  String state_name;
    public  String country_name;


    public String getMyBalance() {
        return myBalance;
    }
    public String getCurrentBalance() {
        return currentBalance;
    }
    public void setMyBalance(String myBalance) {
        this.myBalance = myBalance;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getUpload_id_local() {
        return upload_id_local;
    }

    public void setUpload_id_local(String upload_id_local) {
        this.upload_id_local = upload_id_local;
    }

    public String getUpload_signature_local() {
        return upload_signature_local;
    }

    public void setUpload_signature_local(String upload_signature_local) {
        this.upload_signature_local = upload_signature_local;
    }

    public String getProfile_local_path() {
        return profile_local_path;
    }

    public void setProfile_local_path(String profile_local_path) {
        this.profile_local_path = profile_local_path;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getUploadSignature() {
        return uploadSignature;
    }

    public void setUploadSignature(String uploadSignature) {
        this.uploadSignature = uploadSignature;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getReferBy() {
        return referBy;
    }

    public void setReferBy(String referBy) {
        this.referBy = referBy;
    }

}
