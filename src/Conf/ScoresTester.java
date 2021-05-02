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
public class ScoresTester 
{
    public static void main(String args[])
    {
        String str1="Julie loves me more than Linda loves me";
        String str2="Jane likes me more than Julie loves me";
        System.out.println(str1);
        System.out.println(str2);
        LinkedList<VectorData> matrix=Scores.constructVectorMatrix(str1.split("[ ]"), str2.split("[ ]"));
        System.out.println("Generated Vector:");
        System.out.println("Word\tA\tB");
        for(VectorData x:matrix)
        {
            x.print();
        }
        double dice=Scores.dice(matrix);
        double cosine=Scores.cosine(matrix);
        System.out.println();
        System.out.println("Dice: "+dice);  //Should be 76%
        System.out.println("Cosine: "+cosine); //Should be 82%
    }
}
