package com.stately.openlis.hl7.models.messages;

import com.stately.openlis.hl7.models.constant.RecordType;

import java.util.Date;

/**
 * Created by bryte on 3/24/14.
 */
public class Header
{
    private RecordType recordType = RecordType.H;
    private String delimiters;
    private String messageControlId;
    private String accessPassword;
    private String senderName;
    private String senderStreetAddress;
    private String senderTelephoneNo;
    private String senderCharacteristics;
    private String receiverId;
    private String comment;
    private String processingId;
    private String versionNo;
    private Date date;

    public Header()
    {
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }
}
