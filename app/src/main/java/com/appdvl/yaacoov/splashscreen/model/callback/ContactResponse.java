
package com.appdvl.yaacoov.splashscreen.model.callback;

import com.appdvl.yaacoov.splashscreen.model.pojo.Contact;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactResponse
{

    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts ;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
