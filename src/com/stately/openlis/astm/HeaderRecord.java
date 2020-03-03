package com.stately.openlis.astm;



import java.util.Date;

/**
 *
 * @author Edwin
 */
public class HeaderRecord extends Record
{
    
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
    
    private Date dateTime;

    public HeaderRecord()
    {
        setRecordType(RecordType.H);
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public Date getDateTime()
    {
        return dateTime;
    }

    @Override
    public String toString()
    {
        return "HeaderRecord{" + "delimiters=" + delimiters + ", messageControlId=" + messageControlId + ", accessPassword=" + accessPassword + ", senderName=" + senderName + ", senderStreetAddress=" + senderStreetAddress + ", senderTelephoneNo=" + senderTelephoneNo + ", senderCharacteristics=" + senderCharacteristics + ", receiverId=" + receiverId + ", comment=" + comment + ", processingId=" + processingId + ", versionNo=" + versionNo + ", dateTime=" + dateTime + '}';
    }

    
}
