package fr.wildcodeschool.kelian.winstate.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable{
    private String name = "";
    private String pseudonyme = "";
    private String photoUrl = "";
    private String uid;
    private String male = "";

    public UserModel() {
    }

    public UserModel(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String isMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "name='" + name + '\'' +
                ", pseudonyme='" + pseudonyme + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", uid='" + uid + '\'' +
                ", male='" + male + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(pseudonyme);
        parcel.writeString(photoUrl);
        parcel.writeString(uid);
        parcel.writeString(male);
    }

    private UserModel(Parcel in) {
        name = in.readString();
        pseudonyme = in.readString();
        photoUrl = in.readString();
        uid = in.readString();
        male = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
