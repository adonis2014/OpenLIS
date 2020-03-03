/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.hl7.samples;

import ca.uhn.hl7v2.HL7Exception;
import java.io.IOException;



  import ca.uhn.hl7v2.HL7Exception;
  import ca.uhn.hl7v2.model.Varies;
  import ca.uhn.hl7v2.model.v25.datatype.CE;
  import ca.uhn.hl7v2.model.v25.datatype.ST;
  import ca.uhn.hl7v2.model.v25.datatype.TX;
  import ca.uhn.hl7v2.model.v25.group.ORU_R01_OBSERVATION;
  import ca.uhn.hl7v2.model.v25.group.ORU_R01_ORDER_OBSERVATION;
  import ca.uhn.hl7v2.model.v25.message.ORU_R01;
  import ca.uhn.hl7v2.model.v25.segment.OBR;
  import ca.uhn.hl7v2.model.v25.segment.OBX;

/**
 *
 * @author Edwin
 */
public class PopulateOBXSegment
{
     public static void main(String[] args) throws HL7Exception, IOException {
 
         // First, a message object is constructed
         ORU_R01 message = new ORU_R01();
 
         /*
          * The initQuickstart method populates all of the mandatory fields in the
          * MSH segment of the message, including the message type, the timestamp,
          * and the control ID.
          */
         message.initQuickstart("ORU", "R01", "T");
         
         /*
          * The OBR segment is contained within a group called ORDER_OBSERVATION, 
          * which is itself in a group called PATIENT_RESULT. These groups are
          * reached using named accessors.
          */
         ORU_R01_ORDER_OBSERVATION orderObservation = message.getPATIENT_RESULT().getORDER_OBSERVATION();
 
         // Populate the OBR
         OBR obr = orderObservation.getOBR();
         obr.getSetIDOBR().setValue("1");
         obr.getFillerOrderNumber().getEntityIdentifier().setValue("1234");
         obr.getFillerOrderNumber().getNamespaceID().setValue("LAB");
         obr.getUniversalServiceIdentifier().getIdentifier().setValue("88304");
         
         /*
          * The OBX segment is in a repeating group called OBSERVATION. You can 
          * use a named accessor which takes an index to access a specific 
          * repetition. You can ask for an index which is equal to the 
          * current number of repetitions,and a new repetition will be created.
          */
         ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(0);
 
         // Populate the first OBX
 		OBX obx = observation.getOBX();
         obx.getSetIDOBX().setValue("1");
         obx.getObservationIdentifier().getIdentifier().setValue("88304");
         obx.getObservationSubID().setValue("1");
 
         // The first OBX has a value type of CE. So first, we populate OBX-2 with "CE"...
         obx.getValueType().setValue("CE");
         
         // ... then we create a CE instance to put in OBX-5.
         CE ce = new CE(message);
         ce.getIdentifier().setValue("T57000");
         ce.getText().setValue("GALLBLADDER");
         ce.getNameOfCodingSystem().setValue("SNM");
         Varies value = obx.getObservationValue(0);
         value.setData(ce);
         
         // Now we populate the second OBX
         obx = orderObservation.getOBSERVATION(1).getOBX();
         obx.getSetIDOBX().setValue("2");
         obx.getObservationSubID().setValue("1");
 
         // The second OBX in the sample message has an extra subcomponent at
         // OBX-3-1. This component is actually an ST, but the HL7 specification allows
         // extra subcomponents to be tacked on to the end of a component. This is
         // uncommon, but HAPI nontheless allows it.
         ST observationIdentifier = obx.getObservationIdentifier().getIdentifier();
         observationIdentifier.setValue("88304");
         ST extraSubcomponent = new ST(message);
         extraSubcomponent.setValue("MDT");
         observationIdentifier.getExtraComponents().getComponent(0).setData(extraSubcomponent );
 
         // The first OBX has a value type of TX. So first, we populate OBX-2 with "TX"...
         obx.getValueType().setValue("TX");
         
         // ... then we create a CE instance to put in OBX-5.
         TX tx = new TX(message);
         tx.setValue("MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL GALLBLADDER TISSUE");
         value = obx.getObservationValue(0);
         value.setData(tx);
 
         // Print the message (remember, the MSH segment was not fully or correctly populated)
         System.out.println(message.encode());
 
         /*
          * MSH|^~\&|||||20111102082111.435-0500||ORU^R01^ORU_R01|305|T|2.5
          * OBR|1||1234^LAB|88304
          * OBX|1|CE|88304|1|T57000^GALLBLADDER^SNM
          * OBX|2|TX|88304&MDT|1|MICROSCOPIC EXAM SHOWS HISTOLOGICALLY NORMAL GALLBLADDER TISSUE
          */
     }
 
}
