/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

import java.util.LinkedList;

/**
 *
 * @author MAHE
 */
public class Scores 
{
    static String matchedOn="";
    public static double matches(LinkedList<VectorData> vectorMatrix)
    {
        double score=0;
        matchedOn="";
        for(VectorData x:vectorMatrix)
        {
            if(x.a!=0 && x.b!=0)     
            {
                int matches=Math.min(x.a, x.b);
                score+=matches;
                for(int i=1; i<=matches; i++) //Usually matches=1 anyway
                    matchedOn+=x.word+",";
            }
        }
        return score;
    }
    
    public static double dice(LinkedList<VectorData> vectorMatrix)
    {
        double score=0;
        int len_a=0;
        int len_b=0;
        int matches=0;
        for(VectorData x:vectorMatrix)
        {
            len_a+=x.a;
            len_b+=x.b;
            if(x.a!=0 && x.b!=0)             
                matches+=Math.min(x.a, x.b);
            
        }
        score=2*matches;
        score/=(len_a+len_b);
        return score;
    }
    
    public static double cosine(LinkedList<VectorData> vectorMatrix)
    {
        double score=0;
        int dot=0;
        double modA=0;
        double modB=0;
        for(VectorData x:vectorMatrix)
        {
            dot+=x.a*x.b;
            modA+=x.a*x.a;
            modB+=x.b*x.b;
        }
        modA=Math.sqrt(modA);
        modB=Math.sqrt(modB);
        score=dot/(modA*modB);
        return score;
    }
    
    public static LinkedList<VectorData> constructVectorMatrix(String a[], String b[])
    {
        LinkedList<VectorData> vectorMatrix=new LinkedList();
        //Add first word (a[0]) unconditionally
        vectorMatrix.add(new VectorData(a[0],1,0));
        
        //Check and add all remaining words of a[]
        for(int i=1; i<a.length; i++)
        {
            String x=a[i];
            boolean add=true;            
            for(VectorData y:vectorMatrix)
            {
                //If matrix already has a matching entry
                if(y.word.equals(x))
                {
                    y.a++;
                    add=false;
                    break;
                }
            }
            if(add)
            {
                VectorData entry=new VectorData(x,1,0);
                vectorMatrix.add(entry);
            }
        }
       
        //Check and add all words of b[]
        for(String x:b)
        {
            boolean add=true;            
            for(VectorData y:vectorMatrix)
            {
                //If matrix already has a matching entry
                if(y.word.equals(x))
                {
                    y.b++;
                    add=false;
                    break;
                }
            }
            if(add)
            {
                VectorData entry=new VectorData(x,0,1);
                vectorMatrix.add(entry);
            }
        }
        return vectorMatrix;
    }
    
    private static boolean alreadyInMatrix(String x, LinkedList<VectorData> vectorMatrix)
    {
        boolean notPresent=true;
        for(VectorData y:vectorMatrix)
        {
            //If matrix already has a matching entry
            if(y.word.equals(x))
            {                
                notPresent=false;
                break;
            }
        }
        return notPresent;
    }
}
