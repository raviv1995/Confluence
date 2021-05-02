/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conf;

/**
 *
 * @author MAHE
 */
public class VectorData 
{
    String word="";
    int a=0;
    int b=0;
    
    public VectorData()
    {
        ;
    }
    public VectorData(String x, int y, int z)
    {
        word=x;
        a=y;
        b=z;
    }
    
    public void print()
    {
        System.out.println(word+"\t"+a+"\t"+b);
    }
}
