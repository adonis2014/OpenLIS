/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.astm;

import com.stately.openlis.entities.Model;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Edwin
 */
@MappedSuperclass
public abstract class Record extends Model
{
    @Column(name = "record_type")
    private RecordType recordType;
    
    @Column(name = "sequence_number")
    private String sequenceNumber;
    
    public static final String _astmMessage = "astmMessage";
    public static final String _astmMessage_sampleId = _astmMessage + "." + AstmMessage._sampleId;
    @ManyToOne
    @JoinColumn(name = "astm_message")
    private AstmMessage astmMessage;

    public RecordType getRecordType()
    {
        return recordType;
    }

    public void setRecordType(RecordType recordType)
    {
        this.recordType = recordType;
    }

    public String getSequenceNumber()
    {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber)
    {
        this.sequenceNumber = sequenceNumber;
    }

    public AstmMessage getAstmMessage()
    {
        return astmMessage;
    }

    public void setAstmMessage(AstmMessage astmMessage)
    {
        this.astmMessage = astmMessage;
    }
    
    
}
