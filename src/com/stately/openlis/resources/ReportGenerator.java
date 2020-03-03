/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.resources;

import com.stately.openlis.astm.LabReportDetail;
import com.google.common.base.Strings;
import com.stately.openlis.astm.AstmMessage;
import com.stately.openlis.astm.LabMessage;
import com.stately.openlis.resources.images.Images;
import com.stately.openlis.resources.reports.ReportFiles;
import com.stately.modules.jasperreporting.JasperReportManager;
import com.stately.modules.jasperreporting.ReportData;
import com.stately.modules.jasperreporting.ReportDesignFileType;
import com.stately.modules.jasperreporting.ReportOutputEnvironment;
import com.stately.modules.jasperreporting.ReportOutputFileType;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Edwin
 */

public class ReportGenerator extends JasperReportManager implements Serializable
{

    private static final Logger LOGGER = Logger.getLogger(ReportGenerator.class.getName());
    
    public ReportGenerator()
    {
        setReportEnvironment(ReportOutputEnvironment.DESKTOP_APPLICATION);
        setReportFileType(ReportDesignFileType.INPUTSTREAM);
        setReportOutput(ReportOutputFileType.PDF);
        
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        System.out.println(urls);
        for(URL url: urls){
        	System.out.println(url.getFile());
        }
    }
    
    
    public void init()
    {   
        
        
//        fund.setFundStakeholdersList(fundStakeholdersList);
//        
//        setFund(fund);
//        
//        UserData userdata = new UserData();
//        
//        userdata.setInstitutionId(fund.getTrustee().getId());
//        userdata.init();
//        
//        
//        addParam("statementDate", DateTimeUtils.formatDate(new Date(), "EEE dd, MMMM yyyy"));
//        
//        if(userdata.getUserCategory() == UserCategory.AMC)
//        {
//            addParam("reportCategory", "ASSETS MANAGEMENT REPORTS");
//        }
        
        
        
        
        try
        {
            LOGGER.info("Initialising Report Manager");
            
//            if (fund.getTrustee() != null)
//            {
//                try
//                {
//                    if (fund.isShowTrusteeLogo())
//                    {
//                        if (!Strings.isNullOrEmpty(fund.getTrustee().getLogo()))
//                        {
//                            String trusteeLogoName = userdata.getInstitutionLogoBaseFolder() + fund.getTrustee().getLogo();
//                            addImageParameter("trusteeLogo", trusteeLogoName);
//                        }
//                    }
//                    
//
//                    if (fund.isShowAdministratorLogo())
//                    {
//                        ServiceProvider fundAdministrator = fund.getAdminstrator();
//
//                        if (fundAdministrator != null)
//                        {
//                            String facLogoName = userdata.getInstitutionLogoBaseFolder() + fundAdministrator.getLogo();
//                            addImageParameter("facLogo", facLogoName);
//                        }
//                    }
//                    
//                    
//                } catch (Exception e)
//                {
//                    LOGGER.log(Level.SEVERE, "Error loading fac logo", e);
//                }
//
//                addParam("facName", fund.getTrustee().getStakeholderName());
//                addParam("mainTitle", "Fund Administration System");
//            }
//            
//            if(userStakolder != null)
//            {
//                String companyLogo = userdata.getInstitutionLogoBaseFolder() + userStakolder.getLogo();
//                addImageParameter("companyLogo", companyLogo);
////                System.out.println("compnay logo ..  " + companyLogo);
//            }
//            
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void addImageParameter(String paramName, String fileName)
    {
        try
            {                
                File logoFile = new File(fileName);
                String msg = paramName+" : " + fileName;
                System.out.println(msg);
                LOGGER.info(msg);
               
                Image image = null;
                
                
                if(logoFile.exists())
                {
                    image = ImageIO.read(logoFile);
                     addParam(paramName, image);
                }
                else
                {
                    LOGGER.warning("logo file does not exist");
                }
                
                
            } catch (Exception e)
            {
                LOGGER.log(Level.SEVERE, "Error loading "+paramName+" logo", e);
            }
        
    }
    
//    public void setFund(Fund fund)
//    {
//        try
//        {
//            LOGGER.log(Level.INFO, "User Fund changed to  {0}", fund);
//            FundDetail fundDetail = new FundDetail();
//            fundDetail.setFund(fund);
//            addMap(BeanUtilsBean2.getInstance().describe(fundDetail));
//            
//            
//        } catch (Exception ex)
//        {
//           LOGGER.log(Level.SEVERE, "Error in loading fund related parameters", ex);
//        }
//    }
    
    public void generateSampleLabReport()
    {
        LabReportDetail labReport = new LabReportDetail();
        
        labReport.setPatientName("Edwin Amoakwa");
        labReport.setTestName("Assay1");
        labReport.setResultValue("Result1");
        labReport.setReferenceRange("Reference 1");
        
        
        List<LabReportDetail> labReportsList = new LinkedList<>();
        labReportsList.add(labReport);
        
        
        labReportsList.add(labReport);
        
        ReportData reportData = new ReportData(labReportsList);
        reportData.setReportFile(ReportFiles.lab_report);
        reportData.addParam("healthcareEnterprise", "37 Military Hospital");
        reportData.addParam("healthFacility", "Accra");
        reportData.addParam("department", "Chemical Pathology Department");
//        reportData.addParam("department", "Chemical Pathology Department");
        reportData.addParam("enterpriseLogo", getClass().getResourceAsStream(Images.logo));
        
//        System.out.println("goint to show reoirt ");
        showReport(reportData);
        System.out.println("report shown ...");
    }
    
    
    public void generateLabReport(LabMessage labMessage)
    {
        AstmMessage astmMessage;
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        System.out.println(urls);
        for(URL url: urls){
        	System.out.println(url.getFile());
        }
        
        List<LabReportDetail> labReportsList = labMessage.labReports();
        
        ReportData reportData = new ReportData(labReportsList);
        reportData.setReportFile(ReportFiles.lab_report);
        reportData.addParam("healthcareEnterprise", "37 Military Hospital");
        reportData.addParam("healthFacility", "Accra");
        reportData.addParam("department", "Chemical Pathology Department");
//        reportData.addParam("department", "Chemical Pathology Department");
        reportData.addParam("enterpriseLogo", getClass().getResourceAsStream(Images.logo));
        
//        System.out.println("goint to show reoirt ");
        showReport(reportData);
        System.out.println("report shown ...");
    }
    
    
    
}
