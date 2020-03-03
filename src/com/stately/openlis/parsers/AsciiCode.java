/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.openlis.parsers;

/**
 *
 * @author Edwin
 * @email edwin.amoakwa@gmail.com
 */
public class AsciiCode
{

    public static final String STX = "\2";
    public static final String ETX = "\3";
    public static final String EOT = "\4";
    public static final String ENQ = "\5";
    public static final String ACK = "\6";
//    public static final String LF   = "\10";

    public static final String ETB = "\0x17";
//    public static final String ETB   = "\23";
//    public static final String ETB   = "\u0002\u0003";

//    public static final String CR   = "\13";
    public static boolean hasETB(String data)
    {
        char[] chars = data.toCharArray();
        for (char aChar : chars)
        {

            if ((int) (aChar) == 23)
            {
                return true;
            }
//                boolean check = Character.isISOControl(aChar);
//                
//                System.out.println(aChar + " == " + check);
//                
//                System.out.println((int)(aChar));
        }

        return false;
    }

    public static void main(String[] args)
    {

        String var = "|R||||4";
        char[] chars = var.toCharArray();
        for (char aChar : chars)
        {
            boolean check = Character.isISOControl(aChar);

            System.out.println(aChar + " == " + check);

            System.out.println((int) (aChar));
        }

        System.out.println(AsciiCode.hasETB(var));
    }

}
