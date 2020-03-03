/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.openlis.parsers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Edwin
 */
public class StringUtil
{
    public static String straightenUp(String str)
    {
        return str.replaceAll("\r", " ").replaceAll("\r", " ").replaceAll("\n", " ");
    }
    
    
    public static String fill(String orig, int number)
    {
        StringBuilder builder = new StringBuilder(orig);

        int start = number - orig.length();

        for (int x = 0; x < start; x++)
        {
            builder.append(" ");

        }

        return builder.toString();
    }

    public static String fillUpEqually(List<String[]> input)
    {
        StringBuilder sb = new StringBuilder();

        if(input == null)
            return "";

        if(input.isEmpty())
            return "";

        int numberOfColumns = input.get(0).length;

        int[] maximumColumnLength = new int[numberOfColumns];


        for (String[] row : input)
        {

            for(int column = 0; column < numberOfColumns; column++)
            {
                int colLength = row[column].length();

                if(colLength > maximumColumnLength[column])
                {
                    maximumColumnLength[column] = colLength;
                }
            }
        }

        for (String[] row : input)
        {
            for(int column = 0; column < numberOfColumns; column++)
            {
                String colValue = row[column];

                sb.append(fill(colValue, maximumColumnLength[column]));
                sb.append("          ");

            }

            sb.append("\n");
        }



        

        return sb.toString();
    }

    public static String ecapeBackSlash(String path)
    {
        char pathChars[] = path.toCharArray();
        
        path = "";
        
        for (int i = 0; i < pathChars.length; i++)
        {
            char c = pathChars[i];
            
            if(c == '\\')
            {
                path = path +"\\";
            }
            
            path = path + c;
                
            
        }
        
        return path;
    }
    
    
    public static void printArray(Object arrays[])
    {
        String output = "";

        if(arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }
        for (int i = 0; i < arrays.length; i++)
        {
            output += (i + 1)+" = "+arrays[i] + "\n";

        }

        System.out.println(output);
    }
    
       public static void printArrayHorizontally(Object arrays[])
    {
        String output = "";

        if(arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }
        for (int i = 0; i < arrays.length; i++)
        {
            output += arrays[i] + "\t";

        }

        System.out.println(output);
    }



    public static void printArray(String arrays[][])
    {
        String output = "";
        if(arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }

        for (int i = 0; i < arrays.length; i++)
        {
            String[] inner = arrays[i];

            for (int j = 0; j < inner.length; j++)
            {
                String item = inner[j];

                output += item + " \t";
            }

            output += "\n";
        }

        System.out.println(output);
    }


    public static void printObjectListArray(List<Object[]> objs)
    {
        if(objs == null)
        {
            return;
        }

        for (Object[] objects : objs)
        {
            if(objects == null)
            {
                continue;
            }

            printArrayHorizontally(objects);

        }
    }
    
     public static void printStringListArray(List<String[]> objs)
    {
        if(objs == null)
            return;

        for (Object[] objects : objs)
        {
            if(objects == null)
                continue;

            printArrayHorizontally(objects);

        }
    }
     
     
	/**
	 * Splits the given composite string into an array of components using the
	 * given delimiter.
     *
     * @param composite encoded composite string
     * @param delim delimiter to split upon
     * @return split string
	 */
	public static String[] split(String composite, String delim) {
		ArrayList<String> components = new ArrayList<>();

		// defend against evil nulls
		if (composite == null)
			composite = "";
		if (delim == null)
			delim = "";

		StringTokenizer tok = new StringTokenizer(composite, delim, true);
//                System.out.println("tok.countTokens() : "+tok.countTokens());
		boolean previousTokenWasDelim = true;
		while (tok.hasMoreTokens()) {
			String thisTok = tok.nextToken();
			if (thisTok.equals(delim)) {
				if (previousTokenWasDelim)
					components.add(null);
				previousTokenWasDelim = true;
			} else {
				components.add(thisTok);
				previousTokenWasDelim = false;
			}
		}

		String[] ret = new String[components.size()];
		for (int i = 0; i < components.size(); i++) {
			ret[i] = components.get(i);
		}

		return ret;
	}

    
    
    public static void main(String[] args)
    {
        String[] records = new String[6];

        List<String[]> stringList = new LinkedList<String[]>();

        records = new String[6];
        records[0] = "vrstrwe trwe";
        records[1] = "dsgds gsd gsdg sdgd g";
        records[2] = "sdgfsd gdsg g ds";
        records[3] = "dsgs dg";
        records[4] = "";
        records[5] = "23265";

        stringList.add(records);

        records = new String[6];
        records[0] = "vrstrwe trwe";
        records[1] = "dsgds gsd gsdg sdgd g";
        records[2] = "sdgfsd gdsg g ds";
        records[3] = "dsgs dg";
        records[4] = "dd";
        records[5] = "23265";

        stringList.add(records);

        System.out.println(fillUpEqually(stringList));

    }

}
