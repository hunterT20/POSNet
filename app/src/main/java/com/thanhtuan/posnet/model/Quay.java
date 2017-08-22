
package com.thanhtuan.posnet.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Quay {

    @SerializedName("Description")
    private String mDescription;
    @SerializedName("Note")
    private String mNote;
    @SerializedName("StandId")
    private String mStandId;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String Description) {
        mDescription = Description;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String Note) {
        mNote = Note;
    }

    public String getStandId() {
        return mStandId;
    }

    public void setStandId(String StandId) {
        mStandId = StandId;
    }

}
