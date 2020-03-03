/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.parsers;

/**
 *
 * @author Edwin
 */
public class Samples
{
    public static String msg = "H|\\^&|||Quanta Link|||||DMS||P|1.0|19990213134530\r"
            + "P|1|00095020|28269|123456^1234567^12345678|SURNAME^NAME||19701010120000|M||ADRESS^CITY^STATE||6297471 71|Doctor 1|ICU||||d1||||comments|||Location|||||||Service 1||\r"
            + "O|1|90015041||^^^AFOL|R|20071127111010|20071127120000||||A|||||Doctor1||Collection center|||||||F||||Service1|\r";


    public static String sample2 = "H|\\^&|||Quanta Link|||||DMS||P|1.0|20091126165508\r"
            + "P|1|12345677|10007|10007^10007^10007|10007 SMITH^JOHN||19610716120000|M||10007^City10007^CHICAGO||10007|||AE|||0||||1212\r"
            + "O|1|12345677||^^^HEP2|R|20091125120000|||||A|||||||99|||||||F|||||\r"
            + "R|1|^^^HEP2|dilution_result2|Interpretation||||F|20091126155243|admin|\r"
            + "O|2|12345677^1^||^^^HEP2|R|20091125120000|||||A|||||||99|||||||F|||||\r"
            + "R|1|^^^Dillution^^^1:80|2^dilution_result2|||||F|20091126155243|admin|||\r"
            + "R|2|^^^HOM^Homogeneous^^^||||||F|20091126155243|admin|||\r"
            + "R|3|^^^SPE^Speckled^^^||||||F|20091126155243|admin|||\r"
            + "O|3|12345677^2^||^^^HEP2|R|20091125120000|||||A|||||||99|||||||F|||||\r"
            + "R|1|^^^Dillution^^^1:160|4^dilution_result4|||||F|20091126155243|admin|||\r"
            + "R|2|^^^FSPE^Fine Speckled^^^||||||F|20091126155243|admin|||\r"
            + "O|4|12345677||^^^UR|R|20091125120000|||||A|||||||99|||||||F|||||\r"
            + "R|1|^^^UR|123|mg/dL|10.0 - 50.0|HH||F|20091126155154|admin|||\r"
            + "L|1|N";

    
}


